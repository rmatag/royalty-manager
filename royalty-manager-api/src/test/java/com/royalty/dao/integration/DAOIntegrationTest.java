package com.royalty.dao.integration;

import com.royalty.dao.AbstractDAO;
import org.junit.After;
import org.junit.Before;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class DAOIntegrationTest {

    private NamedParameterJdbcTemplate template;

    private EmbeddedDatabase db;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("TestingDb")
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/insert-data.sql")
                .build();

        template = new NamedParameterJdbcTemplate(db);
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

    public void setTemplate(AbstractDAO dao) {
        dao.setJdbdTemplate(template);
    }
}
