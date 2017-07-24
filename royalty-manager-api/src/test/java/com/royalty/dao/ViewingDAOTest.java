package com.royalty.dao;

import com.royalty.exceptions.GUIDNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewingDAOTest extends DAOTest {
    private ViewingDAO viewingDAO = new ViewingDAO();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setJdbcTemplatemock(viewingDAO);
    }

    @Test
    public void postViewing() {
        String userId = "123123";
        String episodeId = "123";

        when(jdbcTemplateMock.queryForObject(ViewingDAO.FIND_EPISODE_BY_ID,
                    new HashMap<String, Object>() {{put("episodeId", episodeId);}},
                    Integer.class)).thenReturn(1);

        viewingDAO.postViewing(userId, episodeId);

        verify(jdbcTemplateMock)
                .update(ViewingDAO.INSERT_VIEWING,
                        new HashMap<String, Object>(){{
                            put("episodeId", episodeId);
                            put("userId", userId);
                        }}
                );

    }

    @Test(expected = GUIDNotFoundException.class)
    public void postViewing_NotFoundException() {
        String userId = "123123";
        String episodeId = "123";

        when(jdbcTemplateMock.queryForObject(ViewingDAO.FIND_EPISODE_BY_ID,
                new HashMap<String, Object>() {{put("episodeId", episodeId);}},
                Integer.class)).thenReturn(0);

        viewingDAO.postViewing(userId, episodeId);
    }

    @Test
    public void reset() {
        viewingDAO.reset();
        verify(jdbcTemplateMock)
                .update(ViewingDAO.DELETE_VIEWINGS,
                 new HashMap<>());
    }
}
