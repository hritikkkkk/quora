package com.quora.quora.models;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Topic extends BaseModel {
    @NotBlank(message = "Topic name is required")
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "topics")
    @JsonIgnoreProperties({"topics", "questions"})
    private Set<Question> questions = new HashSet<>();
}