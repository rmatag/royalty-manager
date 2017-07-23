package com.royalty.exceptions.mapper;

import com.royalty.exceptions.GUIDNotFoundException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class GUIDNotFoundMapper implements
        ExceptionMapper<GUIDNotFoundException> {

    @Override
    public Response toResponse(GUIDNotFoundException exception)
    {
        return Response.status(404).entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN).build();
    }
}