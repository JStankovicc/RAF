package com.example.vpdomaci5.repository;

import com.example.vpdomaci5.model.Comment;
import com.example.vpdomaci5.model.Post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);

    List<Post> getAllPosts();

    Post getPostById(long id);

    void addComment(long postId, Comment comment);

    List<Comment> getPostComments(long postId);

}
