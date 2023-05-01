package com.domaci.VPDomaci5;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {
    public HelloApplication(){

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(InMemoryClanakRepository.class).to(ClanakRepository.class).in(Singleton.class);

                this.bindAsContract(ClanakService.class);
            }
        };
        register(binder);
        packages("com.domaci.VPDomaci5.resources");
    }
}