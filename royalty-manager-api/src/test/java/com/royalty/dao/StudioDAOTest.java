package com.royalty.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class StudioDAOTest extends DAOTest {
    private StudioDAO studioDAO = new StudioDAO();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setJdbcTemplatemock(studioDAO);
    }
    @Test
    public void getStudios() {
        studioDAO.getStudios();
        verify(jdbcTemplateMock)
                .query(StudioDAO.FIND_ALL_STUDIOS, new HashMap<>(), studioDAO.getRowMapper());

    }
}
