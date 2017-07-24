package com.royalty.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PaymentDAOTest extends DAOTest {
    private PaymentDAO paymentDAO = new PaymentDAO();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setJdbcTemplatemock(paymentDAO);
    }

    @Test
    public void getGroupedRoyaltyPayments() {
        paymentDAO.getGroupedRoyaltyPayments();
        verify(jdbcTemplateMock)
                .query(PaymentDAO.FIND_PAYMENTS_BY_STUDIO, new HashMap<>(),
                        paymentDAO.getRowMapper());

    }

    @Test
    public void getGroupedRoyaltyPaymentsByStudioId() {
        String studioId = "studio";

        paymentDAO.getGroupedRoyaltyPayments(studioId);
        verify(jdbcTemplateMock)
                .query(PaymentDAO.FIND_PAYMENTS_BY_STUDIO_NAME,
                        new HashMap<String, Object>() {{put("studioId", studioId);}},
                        paymentDAO.getRowMapper());

    }
}
