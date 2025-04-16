package com.ravinder.api.post.controller;


import com.ravinder.api.post.entity.Post;
import com.ravinder.api.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id){
        return postService.getPost(id);

    }

    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }


    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post){
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id){
        postService.deletePost(id);
    }

    @PostMapping("/cache/clear")
    public String clearCache(){
        postService.clearCache();
        return "Cache cleared successfully";
    }


}
