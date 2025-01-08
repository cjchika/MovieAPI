package com.cjchika.movieApi.auth.repositories;

import com.cjchika.movieApi.auth.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
