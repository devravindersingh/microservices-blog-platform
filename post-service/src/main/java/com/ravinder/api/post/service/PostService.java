package com.ravinder.api.post.service;

import com.ravinder.api.post.entity.Post;
import com.ravinder.api.post.repository.PostRepository;
import com.ravinder.api.post.scheduler.TaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    public Post createPost(Post post){

    }

}
