package com.example.vpdomaci5;


import com.example.vpdomaci5.repository.InMemoryPostRepository;
import com.example.vpdomaci5.repository.PostRepository;
import com.example.vpdomaci5.service.PostService;
import com.example.vpdomaci5.service.PostServiceImplementation;
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
                this.bind(InMemoryPostRepository.class).to(PostRepository.class).in(Singleton.class);
                this.bind(PostServiceImplementation.class).to(PostService.class);
            }
        };

        register(binder);
        packages("com.example.vpdomaci5.resources");
    }
}