package org.example.domain.member.repository;

import java.util.Optional;
import org.example.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  Optional<Member> findByNameAndPhoneNumber(String name, String phoneNumber);
}
