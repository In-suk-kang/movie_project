package com.example.movie_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SimpleUrlAuthenticationSuccessHandler successHandler;
    private final SimpleUrlAuthenticationFailureHandler failureHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().disable()
        .authorizeRequests()
        .anyRequest().permitAll()
        .and()
        .formLogin()
        .usernameParameter("memberEmail")
        .passwordParameter("memberPassword")
        .loginPage("/member/login")
        .loginProcessingUrl("/member/login")
        .successHandler(successHandler)
        .failureHandler(failureHandler)
        .and()
        .logout()
        .logoutUrl("/member/logout")
        .logoutSuccessUrl("/");
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}