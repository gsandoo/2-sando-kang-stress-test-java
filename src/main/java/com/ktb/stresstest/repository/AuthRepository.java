package com.ktb.stresstest.repository;

import com.ktb.stresstest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
}
