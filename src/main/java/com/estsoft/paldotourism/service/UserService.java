package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.user.AddUserRequestDto;
import com.estsoft.paldotourism.dto.user.AddUserResponseDto;
import com.estsoft.paldotourism.entity.Role;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    // 회원가입 서비스
    @Transactional
    public AddUserResponseDto saveUser(AddUserRequestDto requestDto) {
        User user = new User(requestDto.getEmail(), requestDto.getNickName(), encoder.encode(requestDto.getPassword()), requestDto.getPhoneNumber(), Role.ROLE_USER);
        userRepository.save(user);

        AddUserResponseDto responseDto = new AddUserResponseDto(user);
        return responseDto;
    }
}
