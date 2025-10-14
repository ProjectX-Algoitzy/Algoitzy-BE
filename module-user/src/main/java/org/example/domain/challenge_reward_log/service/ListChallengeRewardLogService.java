package org.example.domain.challenge_reward_log.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_reward_log.controller.request.SearchChallengeRewardLogRequest;
import org.example.domain.challenge_reward_log.controller.response.ListChallengeRewardLogDto;
import org.example.domain.challenge_reward_log.controller.response.ListChallengeRewardLogResponse;
import org.example.domain.challenge_reward_log.enums.ChallengeRewardLogType;
import org.example.domain.challenge_reward_log.repository.ListChallengeRewardLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListChallengeRewardLogService {

  private final ListChallengeRewardLogRepository listChallengeRewardLogRepository;

  /**
   * 내 챌린지 보상 이력 조회
   */
  public ListChallengeRewardLogResponse getChallengeRewardLogList(SearchChallengeRewardLogRequest request) {
    List<ListChallengeRewardLogDto> rewardLogList = listChallengeRewardLogRepository.getChallengeRewardLogList(request);
    for (ListChallengeRewardLogDto dto : rewardLogList) {

      // 사용 이력
      if (dto.getLogType().equals(ChallengeRewardLogType.USED.name())) {
        dto.setContent(
          dto.getGeneration() + "기 "
            + dto.getStudyName() + " "
            + dto.getWeek() + "주차 "
            + dto.getAttendanceType().getValue()
        );
      }

      dto.updateLogType();
    }

    return ListChallengeRewardLogResponse.builder()
      .rewardLogList(rewardLogList)
      .totalCount(rewardLogList.size())
      .build();
  }
}
