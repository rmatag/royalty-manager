package com.royalty.exceptions.mapper;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class ConstraintValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(400).entity("Please review the mandatory fields")
                .type(MediaType.TEXT_PLAIN).build();
    }
}
