package org.example.domain.challenge_reward.repository;

import static org.example.domain.challenge_reward.QChallengeReward.challengeReward;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.Member;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CreateChallengeRewardRepository {

  private final JPAQueryFactory queryFactory;


  public void deleteUsedChallengeReward(Member member, int size) {
    List<Long> idList = queryFactory
      .select(challengeReward.id)
      .where(challengeReward.member.eq(member))
      .from(challengeReward)
      .orderBy(challengeReward.createdTime.asc())
      .limit(size)
      .fetch();

    queryFactory.delete(challengeReward)
      .where(
        challengeReward.member.eq(member),
        challengeReward.id.in(idList)
      )
      .execute();

  }
}
