package com.example.domaciwp6;

import com.example.domaciwp6.repository.post.MySqlPostRepository;
import com.example.domaciwp6.repository.post.PostRepository;
import com.example.domaciwp6.repository.user.InMemoryUserRepository;
import com.example.domaciwp6.repository.user.UserRepository;
import com.example.domaciwp6.service.PostService;
import com.example.domaciwp6.service.PostServiceImplementation;
import com.example.domaciwp6.service.UserService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;


import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {
    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlPostRepository.class).to(PostRepository.class).in(Singleton.class);
                this.bind(PostServiceImplementation.class).to(PostService.class);
                this.bind(InMemoryUserRepository.class).to(UserRepository.class).in(Singleton.class);

                this.bindAsContract(UserService.class);

            }
        };

        register(binder);
        packages("com.example.domaciwp6");
    }
}