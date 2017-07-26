package com.royalty.resource;

import com.royalty.dto.EpisodeDTO;
import com.royalty.dto.EpisodesGroupDTO;
import com.royalty.dto.PaymentDTO;
import com.royalty.dto.PaymentStudioDTO;
import com.royalty.dto.StudioDTO;
import com.royalty.dto.ViewingRequestDTO;
import com.royalty.service.RoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Component
@Path("/royaltymanager")
public class RoyaltyManagerResource {

    @Autowired
    RoyaltyService royaltyService;

    @GET
    @Path("/studios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudios() {
        List<StudioDTO> studios = royaltyService.getStudios();
        return Response.ok().entity(studios).build();
    }

    @POST
    @Path("/reset")
    public Response resetViewings() {
        royaltyService.resetViewings();
        return Response.accepted().build();
    }

    @POST
    @Path("/viewings")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postViewing(@Valid ViewingRequestDTO viewingRequest) {
        royaltyService.createViewing(viewingRequest.getCustomer(), viewingRequest.getEpisode());
        return Response.accepted().build();
    }

    @GET
    @Path("/payments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoyaltyPayments() {
        List<PaymentDTO> payments = royaltyService.getRoyaltyPayments();
        return Response.ok().entity(payments).build();
    }

    @GET
    @Path("/payments/{rightOwnerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoyaltyPayments(@PathParam("rightOwnerId") String rightOwnerId) {
        PaymentStudioDTO payments = royaltyService.getRoyaltyPayments(rightOwnerId);
        return Response.ok().entity(payments).build();
    }

    @GET
    @Path("/episodes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisodes() {
        List<EpisodesGroupDTO> episodesByStudio = royaltyService.getEpisodesByStudio();
        return Response.ok().entity(episodesByStudio).build();
    }

}
