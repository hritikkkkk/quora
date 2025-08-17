package com.quora.quora.service;

import com.quora.quora.exception.DuplicateResourceException;
import com.quora.quora.models.Topic;
import com.quora.quora.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicService {

    private final TopicRepository topicRepository;

    public Topic createTopic(Topic topic) {
        if (topicRepository.existsByName(topic.getName())) {
            throw new DuplicateResourceException("Topic already exists with name: " + topic.getName());
        }
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic findOrCreateTopic(String name) {
        return topicRepository.findByName(name)
                .orElseGet(() -> {
                    Topic newTopic = Topic.builder().name(name).build();
                    return topicRepository.save(newTopic);
                });
    }
}