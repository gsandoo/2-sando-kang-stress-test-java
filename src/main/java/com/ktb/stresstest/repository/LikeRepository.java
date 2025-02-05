package com.ktb.stresstest.repository;

import com.ktb.stresstest.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("DELETE FROM Like l WHERE l.user.id = :userId AND l.post.id = :postId")
    void deleteByUserAndPost(Long userId, Long popupId);

    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.post.id = :postId")
    Like findByUserAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}
