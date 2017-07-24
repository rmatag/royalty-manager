package com.royalty.resource;

import com.royalty.dto.PaymentDTO;
import com.royalty.dto.PaymentStudioDTO;
import com.royalty.dto.StudioDTO;
import com.royalty.dto.ViewingRequestDTO;
import com.royalty.exceptions.GUIDNotFoundException;
import com.royalty.service.RoyaltyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoyaltyManagerResourceTest {
    private RoyaltyManagerResource royaltyManagerResource = new RoyaltyManagerResource();

    @Mock
    private RoyaltyService royaltyServiceMock;

    @Captor
    private ArgumentCaptor<String> viewingReqCustomerCaptor = ArgumentCaptor.forClass(String.class);

    @Captor
    private ArgumentCaptor<String> viewingReqEpisodeCaptor = ArgumentCaptor.forClass(String.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        royaltyManagerResource.royaltyService = royaltyServiceMock;
    }

    @Test
    public void getStudios() {
        List<StudioDTO> expectedStudios = givenExistingStudios();
        when(royaltyServiceMock.getStudios()).thenReturn(expectedStudios);
        Response response = this.royaltyManagerResource.getStudios();

        assertThat("Response code is ok", response.getStatus(), is(200));
        List<StudioDTO> entity = (List<StudioDTO>) response.getEntity();
        assertThat("Response entity is the expected", entity.size(),
                is(expectedStudios.size()));

        for (int i=0; i<expectedStudios.size(); i++) {
            assertEquals(expectedStudios.get(i), entity.get(i));
        }
    }

    @Test
    public void resetViewings() {
        royaltyManagerResource.resetViewings();
        verify(royaltyServiceMock).resetViewings();
    }

    @Test
    public void postViewings() {

        ViewingRequestDTO viewingRequestDTO  = givenAViewingRequest();

        doNothing().when(royaltyServiceMock)
                .createViewing(viewingReqCustomerCaptor.capture(), viewingReqEpisodeCaptor.capture());

        Response response = royaltyManagerResource.postViewing(viewingRequestDTO);

        assertThat("Posted customer is the expected one", viewingReqCustomerCaptor.getValue(), is(viewingRequestDTO.getCustomer()));
        assertThat("Posted episode is the expected one", viewingReqEpisodeCaptor.getValue(), is(viewingRequestDTO.getEpisode()));
        assertThat("Response code is accepted", response.getStatus(), is(202));

    }


    @Test(expected = GUIDNotFoundException.class)
    public void postViewings_NotFoundException() {
        ViewingRequestDTO viewingRequestDTO  = givenAViewingRequest();

        doThrow(new GUIDNotFoundException("")).when(royaltyServiceMock).createViewing(viewingRequestDTO.getCustomer(), viewingRequestDTO.getEpisode());
        royaltyManagerResource.postViewing(viewingRequestDTO);

    }

    @Test
    public void getRoyaltyPayments() {
        List<PaymentDTO> expectedPayments = givenExistingPayments();
        when(royaltyServiceMock.getRoyaltyPayments()).thenReturn(expectedPayments);

        Response response = royaltyManagerResource.getRoyaltyPayments();

        assertThat("Response code is ok", response.getStatus(), is(200));
        List<PaymentDTO> entity = (List<PaymentDTO>) response.getEntity();
        assertThat("Response entity is the expected", entity.size(),
                is(expectedPayments.size()));


        for (int i=0; i<expectedPayments.size(); i++) {
            assertEquals(expectedPayments.get(i), entity.get(i));
        }
    }

    @Test
    public void getRoyatyPaymentByRightOwnerId() {
        String rightOwnerId = "rightOwner1";
        PaymentStudioDTO expectedPayments = givenPaymentsByRightOwner(rightOwnerId);
        when(royaltyServiceMock.getRoyaltyPayments(rightOwnerId)).thenReturn(expectedPayments);

        Response response = royaltyManagerResource.getRoyaltyPayments(rightOwnerId);

        assertThat("Response code is ok", response.getStatus(), is(200));
        assertThat("Response entity is the expected", (response.getEntity()),
                is(expectedPayments));

    }

    private PaymentStudioDTO givenPaymentsByRightOwner(String rightOwnerId) {

        return new PaymentStudioDTO(rightOwnerId, new BigDecimal(12.2), 3);
    }

    private List<PaymentDTO> givenExistingPayments() {

        return new ArrayList<PaymentDTO>() {{
            add(new PaymentDTO("1", "studio1", new BigDecimal(13.1), 1));
            add(new PaymentDTO("2", "studio2", new BigDecimal(12), 2));

        }};
    }

    private ViewingRequestDTO givenAViewingRequest() {
        return new ViewingRequestDTO("1111", "customer1");
    }

    private List<StudioDTO> givenExistingStudios() {

        return new ArrayList<StudioDTO>() {{
            add(new StudioDTO("1", "studio1", new BigDecimal(13.1)));
            add(new StudioDTO("2", "studio2", new BigDecimal(12)));

        }};
    }
}
