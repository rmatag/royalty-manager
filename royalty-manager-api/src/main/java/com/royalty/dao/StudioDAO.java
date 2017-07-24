package com.royalty.dao;

import com.royalty.model.Studio;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudioDAO extends AbstractDAO {

    static final String FIND_ALL_STUDIOS = "SELECT * FROM studios";

    public List<Studio> getStudios() {
        Map<String, Object> params = new HashMap<>();
        List<Studio> result = null;
        try {
            result = jdbcTemplate.query(
                    FIND_ALL_STUDIOS,
                    params,
                    this.getRowMapper());
        } catch (EmptyResultDataAccessException e) {}


        return result;
    }

    @Override
    RowMapper<Studio> getRowMapper() {
        return (rs, rowNum) -> Studio.builder()
                .id(rs.getString("id"))
                .name(rs.getString("name"))
                .payment(rs.getBigDecimal("payment"))
                .build();
    }
}
