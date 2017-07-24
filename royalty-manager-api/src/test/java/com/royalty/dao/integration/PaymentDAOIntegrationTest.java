package com.royalty.dao.integration;

import com.royalty.dao.PaymentDAO;
import com.royalty.dao.ViewingDAO;
import com.royalty.model.Payment;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class PaymentDAOIntegrationTest extends DAOIntegrationTest {
    private ViewingDAO viewingDAO = new ViewingDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    @Test
    public void paymentsIntegrationTest() {
        setTemplate(paymentDAO);
        setTemplate(viewingDAO);

        String foxGUID = "49924ec6ec6c4efca4aa8b0779c89406";
        String foxStudioEpisode1 = "89eb6371df374163859c5d69ae0fc561";
        String foxStudioEpisode2 = "13f7c592d73342c98f936620e65197e2";

        viewingDAO.postViewing("user1", foxStudioEpisode1);
        viewingDAO.postViewing("user1", foxStudioEpisode2);

        String hboStudioEpisode1 = "111cd2dfd8c94682988e61ca087a09a4";
        viewingDAO.postViewing("user2", hboStudioEpisode1);

        List<Payment> groupedRoyaltyPayments = paymentDAO.getGroupedRoyaltyPayments();

        assertThat("Number of Payments", groupedRoyaltyPayments.size(), is(2));
        Payment foxRoyaltyPayments = groupedRoyaltyPayments.stream()
                .filter(p -> p.getRightsOwner().equals("Fox")).findFirst()
                .orElse(null);
        assertThat("Fox grouped Royalty payments", foxRoyaltyPayments, notNullValue());
        assertThat("", foxRoyaltyPayments.getRightsOwnerId(), is(foxGUID));
        assertThat("", foxRoyaltyPayments.getRightsOwner(), is("Fox"));

        BigDecimal expectedPayments = new BigDecimal(34.68).setScale(2, BigDecimal.ROUND_CEILING);
        assertThat("", foxRoyaltyPayments.getRoyalty(), is(expectedPayments));
        assertThat("", foxRoyaltyPayments.getViewings(), is(new Integer(2)));


        Payment groupedRoyaltyPaymentsByOwner = paymentDAO.getGroupedRoyaltyPayments(foxGUID);
        assertThat("", groupedRoyaltyPaymentsByOwner.getRightsOwner(), is("Fox"));
        assertThat("", groupedRoyaltyPaymentsByOwner.getRoyalty(), is(expectedPayments));
        assertThat("", groupedRoyaltyPaymentsByOwner.getViewings(), is(new Integer(2)));

    }

}
