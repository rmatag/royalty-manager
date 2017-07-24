package com.royalty.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    abstract <T> RowMapper<T> getRowMapper();

    public void setJdbdTemplate(NamedParameterJdbcTemplate template) {
        this.jdbcTemplate = template;
    }

}
