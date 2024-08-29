package org.example.domain.select_answer.service;

import static org.example.util.ValueUtils.INTERVIEW_QUESTION;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.answer.Answer;
import org.example.domain.field.Field;
import org.example.domain.field.repository.FieldRepository;
import org.example.domain.interview.Interview;
import org.example.domain.interview.repository.InterviewRepository;
import org.example.domain.interview.repository.ListInterviewRepository;
import org.example.domain.select_answer.SelectAnswer;
import org.example.domain.select_answer.controller.request.CreateSelectAnswerRequest;
import org.example.domain.select_answer.repository.SelectAnswerRepository;
import org.example.domain.select_answer_field.service.CreateSelectAnswerFieldService;
import org.example.domain.select_question.SelectQuestion;
import org.example.domain.select_question.repository.SelectQuestionRepository;
import org.example.domain.select_question.service.CoreSelectQuestionService;
import org.example.domain.study_member.StudyMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateSelectAnswerService {

  private final CoreSelectQuestionService coreSelectQuestionService;
  private final ListInterviewRepository listInterviewRepository;
  private final SelectAnswerRepository selectAnswerRepository;
  private final CreateSelectAnswerFieldService createSelectAnswerFieldService;
  private final SelectQuestionRepository selectQuestionRepository;
  private final InterviewRepository interviewRepository;
  private final FieldRepository fieldRepository;

  /**
   * 객관식 문항 생성
   */
  public void createSelectAnswer(Answer answer, List<CreateSelectAnswerRequest> requestList, StudyMember studyMember) {

    // 모든 필수 문항 응답 여부 확인
    if (answer.getConfirmYN()) {
      List<Long> requiredSelectQuestionIdList =
        selectQuestionRepository.findAllByApplicationAndIsRequiredIsTrue(answer.getApplication())
          .stream().map(SelectQuestion::getId).toList();
      List<Long> requestSelectQuestionIdList = requestList.stream().map(CreateSelectAnswerRequest::selectQuestionId).toList();
      boolean allRequiredAnswered = new HashSet<>(requestSelectQuestionIdList).containsAll(requiredSelectQuestionIdList);
      if (!allRequiredAnswered) {
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "객관식 필수 문항에 응답해주세요.");
      }
    }

    List<SelectAnswer> selectAnswerList = new ArrayList<>();
    for (CreateSelectAnswerRequest request : requestList) {
      SelectQuestion selectQuestion = coreSelectQuestionService.findById(request.selectQuestionId());
      if (!selectQuestion.getIsMultiSelect() && request.fieldIdList().size() > 1) {
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "다중 선택이 불가능한 문항입니다.");
      }

      // 면접 일정 생성
      if (answer.getConfirmYN()) {
        createInterview(studyMember, request, selectQuestion);
      }

      SelectAnswer selectAnswer = selectAnswerRepository.save(
        SelectAnswer.builder()
          .answer(answer)
          .selectQuestion(selectQuestion)
          .build()
      );
      selectAnswerList.add(selectAnswer);
      createSelectAnswerFieldService.createSelectAnswerField(selectAnswer, request.fieldIdList());
    }

    answer.setSelectAnswerList(selectAnswerList);
  }

  private void createInterview(StudyMember studyMember, CreateSelectAnswerRequest request, SelectQuestion selectQuestion) {
    // 면접 일정 문항 로직
    if (selectQuestion.getQuestion().equals(INTERVIEW_QUESTION)) {
      List<Field> fieldList = fieldRepository.findAllById(request.fieldIdList());

      // 선택한 날짜 중 면접자가 최소인 날짜로 배정
      long minCount = Long.MAX_VALUE;
      String time = "";
      for (Field field : fieldList) {
        long count = listInterviewRepository.countByStudyAndTime(studyMember.getStudy(), field);
        if (count < minCount) {
          minCount = count;
          time = field.getContext();
        }
      }
      if (!StringUtils.hasText(time)) {
        throw new GeneralException(ErrorStatus.BAD_REQUEST, "면접 일자 할당 중 오류가 발생했습니다.");
      }

      interviewRepository.save(
        Interview.builder()
          .studyMember(studyMember)
          .time(time)
          .build()
      );
    }
  }

}
