package com.rmanage.rmanage.usermanage.config.security.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.usermanage.config.security.auth.PrincipleDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    //인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
        System.out.println(jwtHeader);

        // header가 있는 지 확인
        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }

        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX,"");

        // 서명 검증 (username이 있음)

        String username = null;

        try {
            username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
        }   catch (JWTVerificationException e){
            response.sendError(3011,e.toString());
        }

        // 서명이 정상적으로 됨 (username이 null이 아님)
        if(username != null) {
            User userEntity = userRepository.findUserByEmail(username);
            PrincipleDetails principleDetails = new PrincipleDetails(userEntity);

            // JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principleDetails, null, principleDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }

    // JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
}
