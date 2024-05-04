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

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    // 회원가입 서비스
    @Transactional
    public AddUserResponseDto saveUser(AddUserRequestDto requestDto) {
        User user = new User(requestDto.getEmail(), requestDto.getNickName(), encoder.encode(requestDto.getPassword()), requestDto.getPhoneNumber(), Role.ROLE_USER);
        userRepository.save(user);

        AddUserResponseDto responseDto = new AddUserResponseDto(user);
        return responseDto;
    }

    // 이메일 중복 확인
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    // 닉네임 중복 확인
    public boolean isNicknameAvailable(String nickname) {
        return !userRepository.existsByNickName(nickname);
    }

    @Transactional
    public boolean changePassword(User user, String currentPassword, String newPassword) {
        if (user != null && encoder.matches(currentPassword, user.getPassword())) {
            // 현재 비밀번호가 일치하는 경우 새 비밀번호로 변경
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // 비밀번호 찾기 서비스
    @Transactional
    public String resetPassword(String email, String phoneNumber) {
        String newPassword = generateRandomPassword(8);
        User user = userRepository.findByEmailAndPhoneNumber(email, phoneNumber);
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return newPassword;
    }

    // 임시 비밀번호 생성 메소드
    public String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }

    // 비밀번호 확인
    public boolean checkPassword(User user, String password) {
        return encoder.matches(password, user.getPassword());
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(User user) {
        // 회원 탈퇴 시 연관된 정보들도 삭제해야함
        // 게시글, 댓글, 찜 등에서 해당 user의 정보 삭제후
        userRepository.delete(user);
    }
}
