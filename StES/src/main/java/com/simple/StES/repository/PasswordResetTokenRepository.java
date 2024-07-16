package com.simple.StES.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.StES.vo.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
        
	PasswordResetToken findByToken(String token);
	
}
