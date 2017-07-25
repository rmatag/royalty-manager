package com.royalty.service;

import com.royalty.dao.EpisodeDAO;
import com.royalty.dao.PaymentDAO;
import com.royalty.dao.StudioDAO;
import com.royalty.dao.ViewingDAO;
import com.royalty.dto.EpisodeDTO;
import com.royalty.dto.PaymentDTO;
import com.royalty.dto.PaymentStudioDTO;
import com.royalty.dto.StudioDTO;
import com.royalty.exceptions.GUIDNotFoundException;
import com.royalty.model.Episode;
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
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
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

    @Mock
    private EpisodeDAO episodeDAOMock;

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
        royaltyService.episodeDAO = episodeDAOMock;
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
        List<PaymentDTO> expectedPayments = givenPaymentDTOs();
        List<Payment> payments = givenPayments();

        when(paymentDAOMock.getGroupedRoyaltyPayments()).thenReturn(payments);
        List<PaymentDTO> royaltyPayments = royaltyService.getRoyaltyPayments();

        assertThat("Every DTO is returned", royaltyPayments.size(), is(expectedPayments.size()));

        for (int i=0; i<expectedPayments.size(); i++) {
            assertEquals(expectedPayments.get(i).getRightsOwner(), royaltyPayments.get(i).getRightsOwner());
            assertEquals(expectedPayments.get(i).getRightsOwnerId(), royaltyPayments.get(i).getRightsOwnerId());
            assertEquals(expectedPayments.get(i).getRoyalty(), royaltyPayments.get(i).getRoyalty());
            assertEquals(expectedPayments.get(i).getViewings(), royaltyPayments.get(i).getViewings());
        }
    }

    @Test
    public void getRoyaltyPaymentsByOwnerId() {
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
    public void getRoyaltyPaymentsByOwnerId_NotFoundException(){

        when(paymentDAOMock.getGroupedRoyaltyPayments()).thenReturn(null);
        royaltyService.getRoyaltyPayments(anyString());
    }

    @Test
    public void getEpisodesByStudio() {
        List<Episode> episodes = givenEpisodes();
        when(episodeDAOMock.getEpisodesByStudio()).thenReturn(episodes);
        Map<String, List<EpisodeDTO>> episodesByStudio = royaltyService.getEpisodesByStudio();

        assertThat("Viewings are the expected ones", episodesByStudio.keySet().size(), is(1));

        String studio = episodesByStudio.keySet().iterator().next();
        List<EpisodeDTO> episodeDTOS = episodesByStudio.get(studio);
        assertThat("One group by studio", studio, is(episodes.get(0).getStudioName()));
        assertThat("Number of episodes is the expected one", episodeDTOS.size(), is(2));

    }

    private List<Episode> givenEpisodes() {
        return new ArrayList<Episode>() {{
            add(new Episode("studio1", "123", "aaa"));
            add(new Episode("studio1", "123123", "bbb"));

        }};
    }

    private PaymentStudioDTO givenPaymentStudioDTO(String rightOwnerName, BigDecimal royalty, int viewings) {
        return PaymentStudioDTO.builder()
                .rightsOwner(rightOwnerName)
                .royalty(royalty)
                .viewings(viewings)
                .build();
    }

    private List<Payment> givenPayments() {
        return new ArrayList<Payment>() {{
            add(new Payment("1", "studio1", new BigDecimal(13.1) ,2));
            add(new Payment("2", "studio2", new BigDecimal(12), 1));

        }};
    }
    private List<PaymentDTO> givenPaymentDTOs() {
        return new ArrayList<PaymentDTO>() {{
            add(new PaymentDTO("1", "studio1", new BigDecimal(13.1) ,2));
            add(new PaymentDTO("2", "studio2", new BigDecimal(12), 1));

        }};
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
