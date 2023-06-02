package com.example.vpdomaci5.service;

import com.example.vpdomaci5.model.Comment;
import com.example.vpdomaci5.model.Post;
import com.example.vpdomaci5.model.dto.CommentDTO;
import com.example.vpdomaci5.model.dto.PostDTO;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(long id);

    Post addPost(PostDTO post);

    Comment addComment(long id, CommentDTO comment);
}
