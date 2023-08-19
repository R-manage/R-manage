package com.rmanage.rmanage.usermanage.config.security.auth.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmanage.rmanage.usermanage.config.security.auth.PrincipleDetails;
import com.rmanage.rmanage.usermanage.dto.LoginRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import com.auth0.jwt.JWT;



@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginRequestDto loginRequestDto = null;

        // 1. username, password 받아서
        try {  //username과 password가 담겨있음.
//           BufferedReader br = request.getReader();
//           String input;
//             while((input = br.readLine()) != null){
//               System.out.println(input);
//              }
            System.out.println(request.getInputStream().toString());
            System.out.println("==============================");
            ObjectMapper om = new ObjectMapper(); // json 데이터를 파싱해줌.
            loginRequestDto = om.readValue(request.getInputStream(),LoginRequestDto.class);



        } catch (Exception e){
            e.printStackTrace();
        }
        // Principal Details Service에 loadByUserUsername() 함수가 실행됨.
        // DB에 있는 username>email과 password가 일치한다.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);


        PrincipleDetails principleDetails = (PrincipleDetails) authentication.getPrincipal();
        System.out.println(principleDetails.getUsername()); // 로그인을 성공했다는 뜻

        System.out.println("성공!");
        // authentication 객체가 session영역에 저장됨.
        // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문.
        // 굳이 JWT 토큰을 사용하면서 세션을 만들이유x = > 권한 처리때문에 session에 넣는 것임.
        return authentication;
        // 2. 정상인지 로그인 시도를 해보는 거에요. authenticationManager로 로그인 시도를 하면 PrincipalDetailService가 자동으로 시행됨.
        // PrincipalDetailService가 호출 loadUserByUsername() 함수 실행됨.
        // 3. Principal Details를 세션에 담고 (세션에 안 담으면 권한 관리가 안됨.(권한 관리가 필요가 없으면 상관x))
        // 4. JWT토큰을 만들어서 응답해주면됨.

    }

    // attemptAuthentication실행 후 인증이 정상적으로 되었으면 success 함수가 실행이 됨.
    // JWT 토큰 만들어서 request요청한 사용자에게 JWT 토큰으 response 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication이 실행됨. 인증이 완료되었다는 뜻임.");
        PrincipleDetails principleDetails = (PrincipleDetails) authResult.getPrincipal();

        // RSA 방식 x, HS256방식
        String jwtToken = JWT.create()
                .withSubject("cos토큰") // 토큰 이름 (의미 x)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) // 만료 시간
                .withClaim("id",principleDetails.getUser().getUserId()) // 비공개 클레임 / 필요 없음
                .withClaim("username",principleDetails.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 내 서버만 아는 고유의 값

        response.addHeader(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX + jwtToken);

    }
}
