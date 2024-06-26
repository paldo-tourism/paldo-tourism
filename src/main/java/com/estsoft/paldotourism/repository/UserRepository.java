package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickname);

    User findByEmailAndPhoneNumber(String email, String phoneNumber);

}
