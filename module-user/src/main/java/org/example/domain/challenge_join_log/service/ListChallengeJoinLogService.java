package org.example.domain.challenge_join_log.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_join_log.controller.response.ListChallengeJoinLogDto;
import org.example.domain.challenge_join_log.controller.response.ListChallengeJoinLogResponse;
import org.example.domain.challenge_join_log.enums.LanguageType;
import org.example.domain.challenge_join_log.repository.DetailChallengeJoinLogRepository;
import org.example.domain.challenge_join_log.repository.ListChallengeJoinLogRepository;
import org.example.domain.member.service.CoreMemberService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListChallengeJoinLogService {

  private final ListChallengeJoinLogRepository listChallengeJoinLogRepository;
  private final DetailChallengeJoinLogRepository detailChallengeJoinLogRepository;
  private final CoreMemberService coreMemberService;

  /**
   * 챌린지 이력 목록 조회
   */
  public ListChallengeJoinLogResponse getChallengeJoinLogList() {
    boolean loginYn = SecurityUtils.isLoggedIn();
    List<ListChallengeJoinLogDto> joinLogList = listChallengeJoinLogRepository.getChallengeJoinLogList();

    long totalCount = joinLogList.stream()
      .filter(log -> log.getDate().equals(LocalDate.now()))
      .toList().size();

    // 최근 일주일 내 참여 안 한 요일은 노출 X
    if (loginYn) {
      for (int i = 6; i >= 0; i--) {
        boolean joinYn = detailChallengeJoinLogRepository.isJoinedMember(
          coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()),
          LocalDate.now().minusDays(i)
        );

        if (!joinYn) {
          int minusDay = i;
          joinLogList = joinLogList.stream()
            .filter(log -> !log.getDate().equals(LocalDate.now().minusDays(minusDay)))
            .toList();
        }
      }

      // 날짜와 언어별로 순위 책정
      int rank = 1;
      LanguageType type = null;
      LocalDate date = null;
      for (ListChallengeJoinLogDto dto : joinLogList) {
        if (!dto.getDate().equals(date)) rank = 1;
        else if (dto.getLanguageType() != type) rank = 1;
        date = dto.getDate();
        type = dto.getLanguageType();
        dto.setRank(rank++);
      }
    }

    return ListChallengeJoinLogResponse.builder()
      .joinLogList(loginYn ? joinLogList : new ArrayList<>())
      .totalCount(totalCount)
      .build();
  }
}
