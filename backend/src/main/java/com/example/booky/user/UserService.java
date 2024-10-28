package com.example.booky.user;


import com.example.booky.user.dto.CredentialsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void register(CredentialsDto credentialsDto) {
        userRepository.save(User
                .builder()
                .username(credentialsDto.getUsername())
                .password(hashedPassword(credentialsDto.getPassword()))
                .build());
    }

    public Optional<User> getUser(String username){
        return userRepository.findByUsername(username);
    }

    private String hashedPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }


}
