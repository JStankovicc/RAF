package com.example.vpdomaci5.service;

import com.example.vpdomaci5.model.Comment;
import com.example.vpdomaci5.model.Post;
import com.example.vpdomaci5.model.dto.CommentDTO;
import com.example.vpdomaci5.model.dto.PostDTO;
import com.example.vpdomaci5.repository.PostRepository;

import javax.inject.Inject;
import java.util.List;

public class PostServiceImplementation implements PostService{
    @Inject
    private PostRepository postRepository;

    public PostServiceImplementation() {
        System.out.println(this);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

    @Override
    public Post getPostById(long id) {
        return postRepository.getPostById(id);
    }

    @Override
    public Post addPost(PostDTO postDto) {
        Post post = new Post(postDto.getAuthor(), postDto.getTitle(), postDto.getContent());
        return postRepository.save(post);
    }

    @Override
    public Comment addComment(long id, CommentDTO commentDto) {
        Comment comment = new Comment(commentDto.getAuthor(), commentDto.getComment());
        postRepository.addComment(id, comment);
        return comment;
    }
}
