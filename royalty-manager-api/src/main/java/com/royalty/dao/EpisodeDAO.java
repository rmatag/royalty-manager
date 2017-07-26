package com.royalty.dao;

import com.royalty.model.Episode;
import com.royalty.model.Payment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EpisodeDAO extends AbstractDAO {

    static final String FIND_EPISODES_BY_STUDIO =
            "SELECT studios.id studioId, studios.name studioName, episodes.id, episodes.name\n"
                    + "FROM\n"
                    + "studios,\n"
                    + "episodes\n"
                    + "WHERE\n"
                    + "studios.id = episodes.studioId\n"
                    + "ORDER BY studios.name";

    public List<Episode> getEpisodesByStudio() {
        Map<String, Object> params = new HashMap<>();
        List<Episode> result = new ArrayList<>();

        try {
            result = jdbcTemplate.query(
                    FIND_EPISODES_BY_STUDIO,
                    params,
                    this.getRowMapper());

        } catch (EmptyResultDataAccessException e) {}
        return result;
    }

    @Override
    RowMapper<Episode> getRowMapper() {
        return (rs, rowNum) -> Episode.builder()
                .id(rs.getString("id"))
                .name(rs.getString("name"))
                .studioName(rs.getString("studioName"))
                .studioId(rs.getString("studioId"))
                .build();
    }
}
