package com.ravinder.api.post.service;

import com.ravinder.api.post.entity.Post;
import com.ravinder.api.post.repository.PostRepository;
import com.ravinder.api.post.scheduler.TaskScheduler;
import com.ravinder.api.post.scheduler.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    public Post createPost(Post post){
        taskScheduler.scheduleTask(new Task(
                "create-post-" + System.currentTimeMillis(),
                "Save post to DB",
                1,
                () -> postRepository.save(post)
        ));
        return post;
    }

    @Cacheable(value = "posts", key = "#id")
    public Post getPost(Long id){
        logger.info("Fetching post with ID {} from DB", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        logger.info("Post with ID {} retrieved from DB", id);
        return post;
    }

    @Cacheable(value = "postList")
    public List<Post> getAllPosts(){
        logger.info("fetcing all posts from DB");
        List<Post> posts = postRepository.findAll();
        logger.info("retrieved {} posts from DB", posts.size());
        return posts;
    }


    @CacheEvict(value = "posts", key = "#id")
    public Post updatePost(Long id, Post updatedPost){
        Post existingPost = getPost(id); // Fetches from cache if available
        existingPost.setAuthor(updatedPost.getAuthor());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setTitle(updatedPost.getTitle());
        taskScheduler.scheduleTask(new Task(
                "update-post - " + id,
                "update post in DB",
                1,
                () -> postRepository.save(existingPost)
        ));
        return existingPost;
    }

    @CacheEvict(value = {"posts", "postList"}, allEntries = true)
    public void deletePost(Long id) {
        taskScheduler.scheduleTask(new Task(
                "delete-post - " + id,
                "delete post from DB",
                1,
                () -> postRepository.deleteById(id)
        ));
    }


}
