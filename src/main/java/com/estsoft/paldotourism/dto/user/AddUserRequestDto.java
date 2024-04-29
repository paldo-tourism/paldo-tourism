package com.estsoft.paldotourism.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddUserRequestDto {

    private String email;
    private String nickName;
    private String password;
    private String phoneNumber;

    public AddUserRequestDto(String email, String nickName, String password, String phoneNumber) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
