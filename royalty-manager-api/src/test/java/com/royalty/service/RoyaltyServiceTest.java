package com.royalty.service;

import com.royalty.dao.PaymentDAO;
import com.royalty.dao.StudioDAO;
import com.royalty.dao.ViewingDAO;
import com.royalty.dto.PaymentStudioDTO;
import com.royalty.dto.StudioDTO;
import com.royalty.exceptions.GUIDNotFoundException;
import com.royalty.model.Payment;
import com.royalty.model.Studio;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoyaltyServiceTest {

    private RoyaltyService royaltyService = new RoyaltyService();

    @Autowired
    private DozerBeanMapper mapper;

    @Mock
    private StudioDAO studioDAOMock;

    @Mock
    private ViewingDAO viewingDAOMock;

    @Mock
    private PaymentDAO paymentDAOMock;

    @Captor
    private ArgumentCaptor<String> userIdCaptor = ArgumentCaptor.forClass(String.class);

    @Captor
    private ArgumentCaptor<String> episodeIdCaptor = ArgumentCaptor.forClass(String.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        royaltyService.mapper = mapper;
        royaltyService.studioDAO = studioDAOMock;
        royaltyService.viewingDAO = viewingDAOMock;
        royaltyService.paymentDAO = paymentDAOMock;
    }

    @Test
    public void getStudios() {
        List<Studio> studios = givenStudios();
        List<StudioDTO> expectedDTOs = givenStudiosDTO();

        when(studioDAOMock.getStudios()).thenReturn(studios);
        List<StudioDTO> studiosDTO = royaltyService.getStudios();

        assertThat("Every DTO is returned", studiosDTO.size(), is(expectedDTOs.size()));

        for (int i=0; i<expectedDTOs.size(); i++) {
            assertEquals(expectedDTOs.get(i).getId(), studiosDTO.get(i).getId());
            assertEquals(expectedDTOs.get(i).getName(), studiosDTO.get(i).getName());
            assertEquals(expectedDTOs.get(i).getPayment(), studiosDTO.get(i).getPayment());
        }
    }

    @Test
    public void createViewing() {

        String userId = "userId";
        String episodeId = "episodeId";
        royaltyService.createViewing(userId, episodeId);
        verify(viewingDAOMock).postViewing(userIdCaptor.capture(), episodeIdCaptor.capture());

        assertThat("UserId is posted", userIdCaptor.getValue(), is(userId));
        assertThat("Episode is posted", episodeIdCaptor.getValue(), is(episodeId));
    }

    @Test
    public void resetViewings() {
        royaltyService.resetViewings();
        verify(viewingDAOMock).reset();
    }

    @Test
    public void getRoyaltyPayments() {
        String rightOwnerId = "rightOwner1";
        String rightOwnerName = "rightOwnerName";
        BigDecimal royalty = new BigDecimal(12.22);
        int viewings = 2;

        Payment payment = givenAPayment(rightOwnerName, royalty, viewings);
        PaymentStudioDTO expectedPaymentStudioDAO = givenPaymentStudioDTO(rightOwnerName, royalty, viewings);

        when(paymentDAOMock.getGroupedRoyaltyPayments(rightOwnerId)).thenReturn(payment);
        PaymentStudioDTO royaltyPayments = royaltyService.getRoyaltyPayments(rightOwnerId);

        assertThat("Right owner is the expected one", royaltyPayments.getRightsOwner(), is(expectedPaymentStudioDAO.getRightsOwner()));
        assertThat("Royalty is the expected one", royaltyPayments.getRoyalty(), is(expectedPaymentStudioDAO.getRoyalty()));
        assertThat("Viewings are the expected ones", royaltyPayments.getViewings(), is(expectedPaymentStudioDAO.getViewings()));

    }


    @Test(expected = GUIDNotFoundException.class)
    public void getRoyaltyPayments_NotFoundException(){

        when(paymentDAOMock.getGroupedRoyaltyPayments()).thenThrow(new GUIDNotFoundException(""));
        royaltyService.getRoyaltyPayments("");
    }

    private PaymentStudioDTO givenPaymentStudioDTO(String rightOwnerName, BigDecimal royalty, int viewings) {
        return PaymentStudioDTO.builder()
                .rightsOwner(rightOwnerName)
                .royalty(royalty)
                .viewings(viewings)
                .build();
    }

    private Payment givenAPayment(String rightOwnerName, BigDecimal royalty, int viewings) {
        return Payment.builder()
                .rightsOwner(rightOwnerName)
                .royalty(royalty)
                .viewings(viewings)
                .build();
    }

    private List<StudioDTO> givenStudiosDTO() {

        return new ArrayList<StudioDTO>() {{
            add(new StudioDTO("1", "studio1", new BigDecimal(13.1)));
            add(new StudioDTO("2", "studio2", new BigDecimal(12)));

        }};
    }

    private List<Studio> givenStudios() {

        return new ArrayList<Studio>() {{
            add(new Studio("1", "studio1", new BigDecimal(13.1)));
            add(new Studio("2", "studio2", new BigDecimal(12)));

        }};
    }
}
