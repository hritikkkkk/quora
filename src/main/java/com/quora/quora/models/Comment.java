package com.quora.quora.models;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment extends BaseModel {

    @Column(name = "parent_id", columnDefinition = "BINARY(16)")
    private UUID parentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_type")
    private ParentType parentType;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"questions", "answers", "comments", "followers", "following"})
    private User user;

    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"parentId", "parentType"})
    private Set<Comment> childComments = new HashSet<>();

    public enum ParentType {
        ANSWER, COMMENT
    }
}