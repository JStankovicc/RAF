package com.example.domaciwp6.repository.post;

import com.example.domaciwp6.model.Comment;
import com.example.domaciwp6.model.Post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);

    List<Post> getAllPosts();

    Post getPostById(int id);

    void addComment(int postId, Comment comment);

    List<Comment> getPostComments(int postId);

}
