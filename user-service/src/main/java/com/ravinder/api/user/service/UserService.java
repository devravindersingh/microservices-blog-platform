package com.ravinder.api.user.service;

import com.ravinder.api.user.entity.User;
import com.ravinder.api.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @KafkaListener(topics = "post-created", groupId = "user-service")
    public void handlePostCreated(String username) {
        logger.info("Recieved post-created event for username: {}", username);
        User user = userRepository.findByUsername(username);
        if(user == null){
            user = new User(username);
        }
        user.setPostCount(user.getPostCount() + 1);
        userRepository.save(user);
        logger.info("Updated post count for user {}: {}", username, user.getPostCount());
    }


}
