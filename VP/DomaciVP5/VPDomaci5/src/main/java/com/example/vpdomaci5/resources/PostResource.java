package com.example.vpdomaci5.resources;

import com.example.vpdomaci5.model.dto.CommentDTO;
import com.example.vpdomaci5.model.dto.PostDTO;
import com.example.vpdomaci5.service.PostService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/posts")
public class PostResource {
    @Inject
    private PostService postService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts() {
        return Response.ok(postService.getAllPosts()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(@PathParam("id") long id) {
        return Response.ok(postService.getPostById(id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewPost(PostDTO post) {
        return Response.ok(postService.addPost(post)).build();
    }

    @POST
    @Path("/{id}/comment")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewComment(@PathParam("id") long id, CommentDTO comment) {
        return Response.ok(postService.addComment(id, comment)).build();
    }
}
