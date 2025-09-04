package com.quora.quora.repository;
import com.quora.quora.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    boolean existsByUserIdAndTargetIdAndTargetType(UUID userId, UUID targetId, Like.TargetType targetType);
}
