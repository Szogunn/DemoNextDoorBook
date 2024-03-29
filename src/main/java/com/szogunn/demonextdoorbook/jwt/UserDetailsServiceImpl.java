package com.szogunn.demonextdoorbook.jwt;

import com.szogunn.demonextdoorbook.model.User;
import com.szogunn.demonextdoorbook.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + username));
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        return UserDetailsImpl.build(user);
    }
}
