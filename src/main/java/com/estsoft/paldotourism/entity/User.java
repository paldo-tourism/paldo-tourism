package com.estsoft.paldotourism.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "Users") // User는 예약어라 테이블명을 Users로 지정
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 아이디(PK)

    @Column(name = "email", nullable = false)
    private String email; // 이메일(사실상의 아이디)

    @Column(name = "nickname", nullable = false)
    private String nickName; // 닉네임

    @Column(name = "password", nullable = false)
    private String password; // 비밀번호

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; // 전화번호

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role; // 역할

    public User(String email, String nickName, String password, String phoneNumber, Role role) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
