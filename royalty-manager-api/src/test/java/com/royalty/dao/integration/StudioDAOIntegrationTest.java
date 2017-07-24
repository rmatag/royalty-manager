package com.royalty.dao.integration;

import com.royalty.dao.StudioDAO;
import com.royalty.model.Studio;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

public class StudioDAOIntegrationTest extends DAOIntegrationTest {
    private StudioDAO studioDAO = new StudioDAO();

    @Test
    public void studiosIntegrationTest() {
        setTemplate(studioDAO);
        List<Studio> studios = studioDAO.getStudios();
        assertThat("Available studios in DB are correct", studios.size(), is(4));
    }
}
