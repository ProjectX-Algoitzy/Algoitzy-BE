package org.example.domain.member.repository;

import java.util.Optional;
import org.example.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  Optional<Member> findByNameAndPhoneNumber(String name, String phoneNumber);

  Optional<Member> findByHandle(String handle);

  @Query("select m from Member m where m.role = 'ROLE_OWNER'")
  Member getOwner();

  @Modifying
  @Query("update Member m set m.blockYN = false")
  void initBlockYN();
}
