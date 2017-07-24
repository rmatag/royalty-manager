package com.royalty.dao;

import org.mockito.Mock;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DAOTest {

    @Mock
    protected NamedParameterJdbcTemplate jdbcTemplateMock;

    protected void setJdbcTemplatemock(AbstractDAO dao) {
        dao.jdbcTemplate = jdbcTemplateMock;
    }
}
