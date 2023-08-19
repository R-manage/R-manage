package com.rmanage.rmanage.usermanage.config.security;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.usermanage.config.security.auth.jwt.JwtAuthenticationFilter;
import com.rmanage.rmanage.usermanage.config.security.auth.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CorsConfig corsConfig;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c->c.disable());
        // 권한 설정
        http.sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).formLogin(c->c.disable()).httpBasic(c->c.disable())
                .authorizeHttpRequests(c->c.requestMatchers("/every/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/worker/**").hasAnyRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().permitAll());
        http.apply(new MyCustomDsl());

        return http.build();
    }
    //이 코드는 웹 애플리케이션의 보안 설정을 정의
    //특정 URL 경로에 대한 역할 기반 권한 설정, 세션 정책, 로그인 방식, 사용자 정의 보안 설정을 포함

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {

            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter()) // @CrossOrigin(인증x), 시큐리티 필터에 등록 인증(x)
                    .addFilter(new JwtAuthenticationFilter(authenticationManager)); // AuthenticationManager가 무조건 필요.


            http.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));

        }
    }
    //MyCustomDsl 클래스는 Spring Security의 확장 클래스로서
    //CORS 설정 및 JWT를 이용한 사용자 인증과 권한 확인을 위한 필터를 추가하는 역할
}

