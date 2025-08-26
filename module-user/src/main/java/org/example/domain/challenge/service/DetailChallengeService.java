package org.example.domain.challenge.service;

import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_join_log.ChallengeJoinLog;
import org.example.domain.challenge_join_log.enums.LanguageType;
import org.example.domain.challenge_join_log.repository.DetailChallengeJoinLogRepository;
import org.example.domain.challenge_join_log.service.CreateChallengeJoinLogService;
import org.example.domain.challenge_problem.ChallengeProblem;
import org.example.domain.challenge_problem.repository.CoreChallengeProblemService;
import org.example.domain.member.Member;
import org.example.domain.member.repository.MemberRepository;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailChallengeService {

  private final CreateChallengeJoinLogService createChallengeJoinLogService;
  private final CoreChallengeProblemService coreChallengeProblemService;
  private final DetailChallengeJoinLogRepository detailChallengeJoinLogRepository;
  private final MemberRepository memberRepository;

  /**
   * 금일 챌린지 참여 여부 확인
   */
  public Boolean checkChallengeJoin() {
    // 로그인 안 했으면 false
    Optional<Member> optionalMember = memberRepository.findByEmail(SecurityUtils.getCurrentMemberEmail());
    if (optionalMember.isEmpty()) return false;

    // 챌린지 참여 이력에 있다면 true
    Member member = optionalMember.get();
    boolean joinYn = detailChallengeJoinLogRepository.isJoinedMember(member, LocalDate.now());
    if (joinYn) return true;

    // 챌린지 참여 이력에 크롤링 후 판단
    ChallengeProblem challengeProblem = coreChallengeProblemService.findById(LocalDate.now());
    ChallengeJoinLog cppLog = createChallengeJoinLogService.crawlLog(member, challengeProblem, LanguageType.CPP);
    ChallengeJoinLog pythonLog = createChallengeJoinLogService.crawlLog(member, challengeProblem, LanguageType.PYTHON);
    ChallengeJoinLog javaLog = createChallengeJoinLogService.crawlLog(member, challengeProblem, LanguageType.JAVA);
    return cppLog != null || pythonLog != null || javaLog != null;
  }
}
