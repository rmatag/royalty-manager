package com.royalty.dao;

import com.royalty.exceptions.GUIDNotFoundException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ViewingDAO extends AbstractDAO {
    static final String COUNT_ALL_VIEWINGS = "SELECT COUNT(*) FROM viewings";
    static final String FIND_EPISODE_BY_ID = "SELECT COUNT(*) FROM episodes WHERE id = :episodeId";
    static final String INSERT_VIEWING = "INSERT INTO viewings (episodeId, userId) VALUES (:episodeId, :userId)";
    static final String DELETE_VIEWINGS = "DELETE FROM viewings";

    public void postViewing(String userId, String episodeId) throws GUIDNotFoundException {
        if (!existEpisodeInSystem(episodeId)) {
            throw new GUIDNotFoundException(String.format("The Episode GUID %s does not exist", episodeId));
        }
        insertNewViewing(userId, episodeId);

        System.out.println("number of viewings: " + getCurrentNumberOfViewings());
    }

    public void reset() {
        jdbcTemplate.update(DELETE_VIEWINGS, new HashMap<>());
        System.out.println("number of viewings: " + getCurrentNumberOfViewings());


    }

    public Integer getCurrentNumberOfViewings() {
        return jdbcTemplate.queryForObject(COUNT_ALL_VIEWINGS, new HashMap<>(), Integer.class);
    }

    private Boolean existEpisodeInSystem(String episodeId) {
        Map namedParameters = new HashMap();
        namedParameters.put("episodeId", episodeId);
        return jdbcTemplate.queryForObject(FIND_EPISODE_BY_ID, namedParameters, Integer.class) > 0;
    }

    private void insertNewViewing(String userId, String episodeId) {
        Map namedParameters = new HashMap();
        namedParameters.put("episodeId", episodeId);
        namedParameters.put("userId", userId);

        jdbcTemplate.update(INSERT_VIEWING, namedParameters);
    }

    @Override
    RowMapper<Void> getRowMapper() {
        return null;
    }

}
