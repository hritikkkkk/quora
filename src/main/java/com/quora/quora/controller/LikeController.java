package com.quora.quora.controller;

import com.quora.quora.DTO.LikeDto;
import com.quora.quora.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @DeleteMapping("/{type}/{id}/likes")
    public ResponseEntity<Map<String, String>> unlikeContent(
            @PathVariable String type,
            @PathVariable UUID id,
            @RequestParam UUID userId) {
        likeService.unlikeContent(type, id, userId);
        return ResponseEntity.ok(Map.of("message", "Content unliked successfully"));
    }

    @GetMapping("/{type}/{id}/likes/count")
    public ResponseEntity<Map<String, Long>> getLikeCount(
            @PathVariable String type,
            @PathVariable UUID id) {
        long count = likeService.getLikeCount(type, id);
        return ResponseEntity.ok(Map.of("count", count));
    }


}
