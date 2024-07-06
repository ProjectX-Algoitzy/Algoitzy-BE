package org.example.domain.generation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.generation.Generation;
import org.example.domain.generation.controller.request.RenewGenerationRequest;
import org.example.domain.generation.repository.GenerationRepository;
import org.example.domain.week.Week;
import org.example.domain.week.controller.request.WeekDto;
import org.example.domain.week.repository.WeekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateGenerationService {

  private final GenerationRepository generationRepository;
  private final WeekRepository weekRepository;

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
  }

  private void validate(RenewGenerationRequest request) {

    Week oldGenerationLastWeek = weekRepository.findTopByOrderByEndTimeDesc();
    if (!LocalDateTime.now().isAfter(oldGenerationLastWeek.getEndTime())) {
      throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "기존 기수가 마감되지 않아 갱신할 수 없습니다.");
    }

    WeekDto firstWeek = request.getWeekList().get(0);
    if (!LocalDate.now().plusDays(4).isBefore(firstWeek.getStartTime())) {
      // 서류 + 면접 전형 고려
      throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "1주차 시작 일자는 기수 갱신일로부터 5일 이후여야 합니다.");
    }

    List<WeekDto> weekDtoList = request.getWeekList();
    for (int i = 1; i < weekDtoList.size(); i++) {
      WeekDto lastWeek = weekDtoList.get(i- 1);
      WeekDto thisWeek = weekDtoList.get(i);
      if (lastWeek.getWeek() + 1 != thisWeek.getWeek()) {
        throw new GeneralException(ErrorStatus.BAD_REQUEST, "요청 순서가 올바르지 않습니다.");
      }
      if (lastWeek.getStartTime().plusDays(7).isAfter(thisWeek.getStartTime())) {
        throw new GeneralException(ErrorStatus.VALIDATION_ERROR, (i + 1) + "주차 시작 일자가 올바르지 않습니다.");
      }
    }

  }
}
