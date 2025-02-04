package com.ktb.stresstest.repository;

import com.ktb.stresstest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(@Param("email") String email);
    boolean existsByNickname(@Param("nickname") String nickname);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.refreshToken IS NOT NULL")
    Optional<User> findByIdAndRefreshTokenNotNull(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.refreshToken = :refreshToken WHERE u.id = :id")
    void updateRefreshToken(Long id, String refreshToken);


}
