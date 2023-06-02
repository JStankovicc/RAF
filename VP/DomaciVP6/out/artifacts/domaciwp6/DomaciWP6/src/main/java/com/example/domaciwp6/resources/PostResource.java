package com.example.domaciwp6.resources;

import com.example.domaciwp6.model.Comment;
import com.example.domaciwp6.model.Post;
import com.example.domaciwp6.service.PostService;

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
    public Response getPost(@PathParam("id") int id) {
        return Response.ok(postService.getPostById(id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewPost(Post post) {
        return Response.ok(postService.addPost(post)).build();
    }

    @POST
    @Path("/{id}/comment")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewComment(@PathParam("id") int id, Comment comment) {
        return Response.ok(postService.addComment(id, comment)).build();
    }
}
