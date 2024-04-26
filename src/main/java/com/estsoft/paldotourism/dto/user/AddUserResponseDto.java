package com.estsoft.paldotourism.dto.user;

import com.estsoft.paldotourism.entity.User;
import lombok.Getter;

@Getter
public class AddUserResponseDto {

    private Long id;
    private String email;
    private String nickName;
    private String password;
    private String phoneNumber;

    public AddUserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
    }
}
