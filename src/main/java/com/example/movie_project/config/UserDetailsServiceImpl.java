package com.example.movie_project.config;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.movie_project.entity.MemberEntity;
import com.example.movie_project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(username).get();
        if(memberEntity == null) {
            throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
        }
        return new CustomUserDetails(memberEntity);
    }
    
}
