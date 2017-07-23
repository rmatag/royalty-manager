package com.royalty.exceptions;

import javax.ws.rs.WebApplicationException;

public class GUIDNotFoundException extends WebApplicationException {
    
    public GUIDNotFoundException(String message) {
        super(message);
    }
}
