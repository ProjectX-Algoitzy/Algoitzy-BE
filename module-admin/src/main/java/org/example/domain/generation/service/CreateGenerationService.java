package org.example.domain.generation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.application.Application;
import org.example.domain.application.repository.ApplicationRepository;
import org.example.domain.application.service.CreateApplicationService;
import org.example.domain.attendance.Attendance;
import org.example.domain.curriculum.Curriculum;
import org.example.domain.curriculum.repository.CurriculumRepository;
import org.example.domain.generation.Generation;
import org.example.domain.generation.controller.request.RenewGenerationRequest;
import org.example.domain.generation.repository.GenerationRepository;
import org.example.domain.member.repository.MemberRepository;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.ListStudyRepository;
import org.example.domain.study.repository.StudyRepository;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.domain.week.Week;
import org.example.domain.week.controller.request.WeekDto;
import org.example.domain.week.repository.WeekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CreateGenerationService {

  private final CreateApplicationService createApplicationService;
  private final StudyMemberRepository studyMemberRepository;
  private final GenerationRepository generationRepository;
  private final WeekRepository weekRepository;
  private final ListStudyRepository listStudyRepository;
  private final StudyRepository studyRepository;
  private final CurriculumRepository curriculumRepository;
  private final ApplicationRepository applicationRepository;
  private final MemberRepository memberRepository;

  private static final int ALL_FIELD_COUNT = 3; // 필드 수(문제 할당량, 블로그 포스팅, 모의테스트 참여 여부)
  private static final int HALF_ATTENDANCE_FIELD_COUNT = 12; // (필드 3개 X 8주) / 2
  private static final int MAX_ABSENT_WEEK_COUNT = 3; // 블랙리스트 삽입 미참여 주차 기준

  /**
   * 🚫기수 갱신🚫
   */
  public void renewGeneration(RenewGenerationRequest request) {

    validate(request);

    // 새 기수 생성
    Generation oldGeneration = generationRepository.findTopByOrderByValueDesc();
    Generation newGeneration = generationRepository.save(
      Generation.builder()
        .value(oldGeneration.getValue() + 1)
        .build()
    );

    // 주차별 일정 생성
    for (WeekDto weekDto : request.getWeekList()) {
      weekRepository.save(
        Week.builder()
          .generation(newGeneration)
          .value(weekDto.getWeek())
          .startTime(weekDto.getStartTime().atTime(6, 0))
          .endTime(weekDto.getStartTime().plusDays(7).atTime(6, 0))
          .build()
      );
    }

    // 자율 스터디 기수 갱신
    List<Study> tempStudyList = listStudyRepository.getOldGenerationStudyList(StudyType.TEMP);
    for (Study study : tempStudyList) {
      study.renewGeneration(newGeneration);
    }

    // 정규 스터디 복사
    memberRepository.initBlockYN();
    List<Study> regularStudyList = listStudyRepository.getOldGenerationStudyList(StudyType.REGULAR);
    for (Study oldStudy : regularStudyList) {
      oldStudy.end();
      Study newStudy = Study.builder()
        .profileUrl(oldStudy.getProfileUrl())
        .name(oldStudy.getName())
        .content(oldStudy.getContent())
        .type(oldStudy.getType())
        .generation(newGeneration)
        .build();
      studyRepository.save(newStudy);

      // 커리큘럼 마이그레이션
      List<Curriculum> oldCurriculumList = curriculumRepository.findAllByStudy(oldStudy);
      for (Curriculum oldCurriculum : oldCurriculumList) {
        curriculumRepository.save(
          Curriculum.builder()
            .study(newStudy)
            .title(oldCurriculum.getTitle())
            .week(oldCurriculum.getWeek())
            .content(oldCurriculum.getContent())
            .orderNumber(oldCurriculum.getOrderNumber())
            .build()
        );
      }

      // 지원서 양식 마이그레이션
      Optional<Application> optionalApplication = applicationRepository.findByStudy(oldStudy);
      optionalApplication.ifPresent(application -> createApplicationService.renewApplication(newStudy, application));

      // 출석률 50% 미만 or 3주 연속 미출석 시 기수 참여 제한
      List<StudyMember> studyMemberList = studyMemberRepository.findAllByStudyIdAndStatus(oldStudy.getId(), StudyMemberStatus.PASS);
      for (StudyMember studyMember : studyMemberList) {

        int absentWeekCount = 0; // 주차 연속 미참여 수
        int absentFieldCount = 0; // 특정 활동 미참여 수
        for (Attendance attendance : studyMember.getAttendanceList()) {
          int absentCount = attendance.getAbsentCount();
          absentWeekCount = (absentCount == ALL_FIELD_COUNT) ? absentWeekCount + 1 : 0;
          absentFieldCount += absentCount;
          if (absentWeekCount >= MAX_ABSENT_WEEK_COUNT || absentFieldCount > HALF_ATTENDANCE_FIELD_COUNT) {
            log.info("{} : {}(연속 미참여 주차 수 : {}, 미참여 활동 수 : {}) 기수 참여 제한",
              studyMember.getStudy().getName(), studyMember.getMember().getName(),
              absentWeekCount, absentFieldCount);
            studyMember.getMember().updateBlockYN(true);
            break;
          }
        }
      }

      oldStudy.markOldGeneration(oldGeneration);
    }


  }

  private void validate(RenewGenerationRequest request) {

    Week oldGenerationLastWeek = weekRepository.findTopByOrderByEndTimeDesc();
    if (!LocalDateTime.now().isAfter(oldGenerationLastWeek.getEndTime())) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "기존 기수가 마감되지 않아 갱신할 수 없습니다.");
    }

    WeekDto firstWeek = request.getWeekList().get(0);
    if (!LocalDate.now().plusDays(4).isBefore(firstWeek.getStartTime())) {
      // 서류 + 면접 전형 고려
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "1주차 시작 일자는 기수 갱신일로부터 5일 이후여야 합니다.");
    }

    List<WeekDto> weekDtoList = new ArrayList<>(request.getWeekList());
    for (int i = 1; i < weekDtoList.size(); i++) {
      WeekDto lastWeek = weekDtoList.get(i - 1);
      WeekDto thisWeek = weekDtoList.get(i);
      if (lastWeek.getWeek() + 1 != thisWeek.getWeek()) {
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "요청 순서가 올바르지 않습니다.");
      }
      if (lastWeek.getStartTime().plusDays(7).isAfter(thisWeek.getStartTime())) {
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, (i + 1) + "주차 시작 일자가 올바르지 않습니다.");
      }
    }

  }
}
