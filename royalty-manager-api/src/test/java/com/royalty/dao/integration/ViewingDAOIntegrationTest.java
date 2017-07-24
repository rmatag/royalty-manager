package com.royalty.dao.integration;

import com.royalty.dao.ViewingDAO;
import com.royalty.model.Studio;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ViewingDAOIntegrationTest extends DAOIntegrationTest {
    private ViewingDAO viewingDAO = new ViewingDAO();

    @Test
    public void viewingsIntegrationTest() {
        setTemplate(viewingDAO);

        Integer initialViewings = viewingDAO.getCurrentNumberOfViewings();
        assertThat("There is no viewings", initialViewings, is(0));

        // Posting Viewings
        viewingDAO.postViewing("user1", "89eb6371df374163859c5d69ae0fc561");
        viewingDAO.postViewing("user1", "13f7c592d73342c98f936620e65197e2");

        Integer currentViewings = viewingDAO.getCurrentNumberOfViewings();
        assertThat("Number of viewings are the expected ones", currentViewings, is(2));

        // Resetting viewings
        viewingDAO.reset();

        currentViewings = viewingDAO.getCurrentNumberOfViewings();
        assertThat("Number of viewings are the expected ones", currentViewings, is(0));

    }

}
