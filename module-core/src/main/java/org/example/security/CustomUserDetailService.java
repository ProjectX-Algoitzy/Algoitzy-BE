package org.example.security;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final CoreMemberService coreMemberService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = coreMemberService.findByEmail(email);
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
