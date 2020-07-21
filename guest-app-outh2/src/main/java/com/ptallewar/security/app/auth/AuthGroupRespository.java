package com.ptallewar.security.app.auth;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthGroupRespository extends JpaRepository<AuthGroup, Long> {

	List<AuthGroup> findByUsername(String username);
}
