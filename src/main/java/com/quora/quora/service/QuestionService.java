package com.quora.quora.service;

import com.quora.quora.DTO.QuestionDto;
import com.quora.quora.models.Question;
import com.quora.quora.models.Topic;
import com.quora.quora.models.User;
import com.quora.quora.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final TopicService topicService;

    public Question createQuestion(QuestionDto questionDto) {
        User user = userService.getUserById(questionDto.getUserId());

        Question question = Question.builder()
                .title(questionDto.getTitle())
                .body(questionDto.getBody())
                .user(user)
                .build();

        if (questionDto.getTopicTags() != null && !questionDto.getTopicTags().isEmpty()) {
            Set<Topic> topics = new HashSet<>();
            for (String topicName : questionDto.getTopicTags()) {
                Topic topic = topicService.findOrCreateTopic(topicName);
                topics.add(topic);
            }
            question.setTopics(topics);
        }


        return questionRepository.save(question);
    }

    public List<Question> getQuestions() {
        return questionRepository.findAllOrderByCreatedAtDesc();
    }

    public List<Question> searchQuestions(String text, String tag) {
        return questionRepository.searchQuestions(text, tag);
    }




}
