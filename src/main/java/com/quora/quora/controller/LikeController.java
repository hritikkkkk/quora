package com.quora.quora.controller;

import com.quora.quora.DTO.LikeDto;
import com.quora.quora.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{type}/{id}/likes")
    public ResponseEntity<Map<String, String>> likeContent(
            @PathVariable String type,
            @PathVariable UUID id,
            @Valid @RequestBody LikeDto likeCreateDto) {
        likeService.likeContent(type, id, likeCreateDto.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Content liked successfully"));
    }


}
