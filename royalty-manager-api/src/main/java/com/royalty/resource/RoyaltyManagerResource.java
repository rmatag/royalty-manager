package com.royalty.resource;

import com.royalty.dto.Payment;
import com.royalty.service.RoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Component
@Path("/royaltymanager")
public class RoyaltyManagerResource {

    @Autowired
    private RoyaltyService royaltyService;

    @GET
    @Path("/payments")
    @Produces(MediaType.APPLICATION_JSON)
    public Payment getPayments() {
        return royaltyService.getPayments();
    }

}
