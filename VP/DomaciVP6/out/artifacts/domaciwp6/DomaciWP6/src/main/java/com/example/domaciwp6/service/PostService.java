package com.example.domaciwp6.service;

import com.example.domaciwp6.model.Comment;
import com.example.domaciwp6.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(int id);

    Post addPost(Post post);

    Comment addComment(int id, Comment comment);
}
