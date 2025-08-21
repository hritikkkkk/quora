package com.quora.quora.config;

import com.quora.quora.models.*;
import com.quora.quora.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            loadSampleData();
        }
    }

    private void loadSampleData() {


        // Create users
        User user1 = User.builder()
                .username("john_doe")
                .email("john@example.com")
                .bio("Software Developer passionate about technology")
                .build();

        User user2 = User.builder()
                .username("jane_smith")
                .email("jane@example.com")
                .bio("Data Scientist and Machine Learning enthusiast")
                .build();

        User user3 = User.builder()
                .username("mike_wilson")
                .email("mike@example.com")
                .bio("Product Manager with 5+ years experience")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // Create topics
        Topic javaTopic = Topic.builder().name("Java").build();
        Topic springTopic = Topic.builder().name("Spring Boot").build();
        Topic pythonTopic = Topic.builder().name("Python").build();
        Topic mlTopic = Topic.builder().name("Machine Learning").build();

        topicRepository.save(javaTopic);
        topicRepository.save(springTopic);
        topicRepository.save(pythonTopic);
        topicRepository.save(mlTopic);

        // Create questions
        Question question1 = Question.builder()
                .title("What are the best practices for Spring Boot development?")
                .body("I'm starting with Spring Boot and would like to know the industry best practices.")
                .user(user1)
                .topics(Set.of(javaTopic, springTopic))
                .build();

        Question question2 = Question.builder()
                .title("How to get started with Machine Learning?")
                .body("I'm a beginner in ML. What resources and path would you recommend?")
                .user(user3)
                .topics(Set.of(mlTopic, pythonTopic))
                .build();

        questionRepository.save(question1);
        questionRepository.save(question2);

        // Create answers
        Answer answer1 = Answer.builder()
                .text("Here are some key best practices for Spring Boot: 1. Use proper layered architecture, 2. Implement proper exception handling, 3. Use configuration properties, 4. Write comprehensive tests.")
                .user(user2)
                .question(question1)
                .build();

        Answer answer2 = Answer.builder()
                .text("Start with Python basics, then learn numpy and pandas. After that, dive into scikit-learn for traditional ML algorithms. Don't forget to practice with real datasets!")
                .user(user1)
                .question(question2)
                .build();

        answerRepository.save(answer1);
        answerRepository.save(answer2);


    }
}