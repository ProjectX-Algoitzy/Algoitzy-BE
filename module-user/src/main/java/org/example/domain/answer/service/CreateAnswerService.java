package org.example.domain.answer.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.answer.Answer;
import org.example.domain.answer.controller.request.CreateAnswerRequest;
import org.example.domain.answer.repository.AnswerRepository;
import org.example.domain.answer.repository.DetailAnswerRepository;
import org.example.domain.application.service.CoreApplicationService;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.select_answer.service.CreateSelectAnswerService;
import org.example.domain.study.Study;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.domain.study_member.service.CreateStudyMemberService;
import org.example.domain.text_answer.service.CreateTextAnswerService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateAnswerService {

  private final CoreMemberService coreMemberService;
  private final CoreApplicationService coreApplicationService;
  private final CreateTextAnswerService createTextAnswerService;
  private final CreateSelectAnswerService createSelectAnswerService;
  private final CreateStudyMemberService createStudyMemberService;
  private final DetailAnswerRepository detailAnswerRepository;
  private final AnswerRepository answerRepository;
  private final StudyMemberRepository studyMemberRepository;

  /**
   * 지원서 작성
   */
  public void createAnswer(Long applicationId, CreateAnswerRequest request) {
    // 기존 지원서 삭제 후 저장
    Optional<Answer> optionalAnswer = detailAnswerRepository.getAnswer(applicationId, SecurityUtils.getCurrentMemberEmail());
    optionalAnswer.ifPresent(answerRepository::delete);

    Answer answer = answerRepository.save(
      Answer.builder()
        .application(coreApplicationService.findById(applicationId))
        .confirmYN(request.confirmYN())
        .build()
    );

    Study study = answer.getApplication().getStudy();
    Member member = coreMemberService.findByEmail(answer.getCreatedBy());
    if (studyMemberRepository.findByStudyAndMember(study, member).isPresent()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "이미 지원한 스터디입니다.");
    }

    StudyMember studyMember = null;
    if (request.confirmYN()) {
      studyMember = createStudyMemberService.createStudyMember(study, member);
    }
    createTextAnswerService.createTextAnswer(answer, request.createTextAnswerRequestList());
    createSelectAnswerService.createSelectAnswer(answer, request.createSelectAnswerRequestList(), studyMember);

  }

  public void deleteAnswer(Long answerId) {
    answerRepository.deleteById(answerId);
  }
}
