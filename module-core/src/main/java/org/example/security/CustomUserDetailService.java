package org.example.security;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.Member;
import org.example.domain.member.repository.CoreMemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final CoreMemberRepository coreMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = coreMemberRepository.findByEmail(email);
        return createUserDetails(member);
    }
    private CustomUserDetail createUserDetails(Member member) {
        return CustomUserDetail.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .role(member.getRole())
                .build();
    }
}
