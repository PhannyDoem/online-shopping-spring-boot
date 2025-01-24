package com.onlineShopping.repository;

import com.onlineShopping.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDetails, Long> {
    public UserDetails findByEmail(String email);

    public List<UserDetails> findByRole(String role);

    public UserDetails findByResetToken(String token);

    public Boolean existsByEmail(String email);
}
