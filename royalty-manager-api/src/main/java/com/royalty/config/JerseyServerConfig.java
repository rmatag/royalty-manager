package com.royalty.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


@Configuration
public class JerseyServerConfig extends ResourceConfig {

    @Autowired
    public JerseyServerConfig(ObjectMapper objectMapper) {
        packages("com.royalty");
        register(new ObjectMapperContextResolver(objectMapper));
    }

    @Provider
    public static class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

        private final ObjectMapper mapper;

        public ObjectMapperContextResolver(ObjectMapper mapper) {
            this.mapper = mapper;
            this.mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        }

        @Override
        public ObjectMapper getContext(Class<?> type) {
            return mapper;
        }
    }


    @Component
    public static class SimpleCORSFilter implements ContainerResponseFilter {


        @Override
        public void filter(ContainerRequestContext request,
                ContainerResponseContext response) throws IOException {


            response.getHeaders().add("Access-Control-Allow-Origin", "*");
            response.getHeaders().add("Access-Control-Allow-Headers",
                    "origin, content-type, accept, authorization");
            response.getHeaders().add("Access-Control-Allow-Credentials", "true");
            response.getHeaders().add("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        }
    }

}
