package org.example.domain.challenge_reward.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.attendance.Attendance;
import org.example.domain.attendance.repository.DetailAttendanceRepository;
import org.example.domain.challenge_join_log.ChallengeJoinLog;
import org.example.domain.challenge_join_log.enums.LanguageType;
import org.example.domain.challenge_join_log.repository.DetailChallengeJoinLogRepository;
import org.example.domain.challenge_problem.ChallengeProblem;
import org.example.domain.challenge_problem.repository.CoreChallengeProblemService;
import org.example.domain.challenge_reward.ChallengeReward;
import org.example.domain.challenge_reward.controller.request.UseChallengeRewardDto;
import org.example.domain.challenge_reward.controller.request.UseChallengeRewardRequest;
import org.example.domain.challenge_reward.repository.ChallengeRewardRepository;
import org.example.domain.challenge_reward.repository.CreateChallengeRewardRepository;
import org.example.domain.challenge_reward_log.ChallengeRewardLog;
import org.example.domain.challenge_reward_log.repository.ChallengeRewardLogRepository;
import org.example.domain.challenge_winner.ChallengeWinner;
import org.example.domain.challenge_winner.repository.ChallengeWinnerRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateChallengeRewardService {

  private final DetailChallengeJoinLogRepository detailChallengeJoinLogRepository;
  private final ChallengeRewardLogRepository challengeRewardLogRepository;
  private final ChallengeRewardRepository challengeRewardRepository;
  private final CoreChallengeProblemService coreChallengeProblemService;
  private final ChallengeWinnerRepository challengeWinnerRepository;
  private final CoreMemberService coreMemberService;
  private final DetailAttendanceRepository detailAttendanceRepository;
  private final CreateChallengeRewardRepository createChallengeRewardRepository;

  /**
   * 챌린지 보상 생성
   */
  public void createChallengeReward() {
    List<ChallengeJoinLog> challengeJoinLogList = new ArrayList<>();
    challengeJoinLogList.add(detailChallengeJoinLogRepository.getRewardTarget(LanguageType.CPP));
    challengeJoinLogList.add(detailChallengeJoinLogRepository.getRewardTarget(LanguageType.JAVA));
    challengeJoinLogList.add(detailChallengeJoinLogRepository.getRewardTarget(LanguageType.PYTHON));

    // 보상 중복 획득 불가능
    Set<Member> rewardTargetMemberSet = new HashSet<>();
    for (ChallengeJoinLog challengeJoinLog : challengeJoinLogList) {
      if (challengeJoinLog == null) continue;
      rewardTargetMemberSet.add(challengeJoinLog.getMember());
    }

    ChallengeProblem challengeProblem = coreChallengeProblemService.findById(LocalDate.now().minusDays(1));
    for (Member member : rewardTargetMemberSet) {
      List<ChallengeWinner> challengeWinnerList = challengeWinnerRepository.findChallengeWinnerByMember(member);

      // 1등 3회 누적 시 보상 획득
      if (challengeWinnerList.size() == 2) {

        // 보상 저장
        challengeRewardRepository.save(
          ChallengeReward.builder()
            .member(member)
            .build()
        );

        // 1등한 문제 목록
        List<Integer> problemList = new ArrayList<>(challengeWinnerList.stream()
          .map(challengeWinner -> challengeWinner.getChallengeProblem().getProblem().getNumber())
          .toList());
        problemList.add(challengeProblem.getProblem().getNumber());

        // 보상 이력 저장
        challengeRewardLogRepository.save(
          ChallengeRewardLog.builder()
            .member(member)
            .problemList(problemList)
            .rewardCount(challengeRewardRepository.countChallengeRewardByMember(member))
            .build()
        );

        challengeWinnerRepository.deleteChallengeWinnerByMember(member);
        continue;
      }

      // 보상 획득 시점 아닐 시, 1등 기록만 누적
      challengeWinnerRepository.save(
        ChallengeWinner.builder()
          .challengeProblem(challengeProblem)
          .member(member)
          .build()
      );

    }

  }

  /**
   * 챌린지 보상 사용
   */
  public void useChallengeReward(UseChallengeRewardRequest request) {
    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    long rewardCount = challengeRewardRepository.countChallengeRewardByMember(member);
    if (rewardCount < request.requestList().size()) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "요청하신 개수가 보유하신 보상 수를 초과하였습니다.");
    }

    List<ChallengeRewardLog> challengeRewardLogList = new ArrayList<>();
    for (UseChallengeRewardDto dto : request.requestList()) {
      Attendance attendance = Optional.ofNullable(detailAttendanceRepository.getAttendanceForReward(dto))
        .orElseThrow(() -> new GeneralException(ErrorStatus.BAD_REQUEST, "잘못된 요청입니다."));
      attendance.updateAttendance(dto.attendanceType());

      challengeRewardLogList.add(
        ChallengeRewardLog.builder()
          .member(member)
          .attendance(attendance)
          .attendanceType(dto.attendanceType())
          .rewardCount(--rewardCount)
          .build()
      );
    }

    // 사용 이력 저장
    challengeRewardLogRepository.saveAll(challengeRewardLogList);

    // 보상 소모
    createChallengeRewardRepository.deleteUsedChallengeReward(member, request.requestList().size());

  }
}
