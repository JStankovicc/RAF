package com.example.domaciwp6.service;

import com.example.domaciwp6.model.Comment;
import com.example.domaciwp6.model.Post;
import com.example.domaciwp6.repository.post.PostRepository;

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
    public Post getPostById(int id) {
        return postRepository.getPostById(id);
    }

    @Override
    public Post addPost(Post postDto) {
        Post post = new Post(postDto.getAuthor(), postDto.getTitle(), postDto.getContent());
        return postRepository.save(post);
    }

    @Override
    public Comment addComment(int id, Comment commentDto) {
        Comment comment = new Comment(id,commentDto.getAuthor(), commentDto.getComment());
        postRepository.addComment(id, comment);
        return comment;
    }
}
