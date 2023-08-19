package com.rmanage.rmanage.usermanage.config.security.auth;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PrincipleDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 테스트");
        User user = userRepository.findUserByEmail(email);
        return new PrincipleDetails(user);
    }
}
