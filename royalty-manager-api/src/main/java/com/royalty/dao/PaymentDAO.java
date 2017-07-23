package com.royalty.dao;

import com.royalty.model.Payment;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentDAO extends AbstractDAO {
    private static final String FIND_PAYMENTS_BY_STUDIO =
            "SELECT studios.id, studios.name, SUM(studios.payment) royalty, COUNT(*) viewings \n"
                    + "FROM studios, episodes, viewings \n"
                    + "WHERE \n"
                    + "studios.id = episodes.studioId AND \n"
                    + "episodes.id = viewings.episodeId \n"
                    + "GROUP BY studios.id, studios.name";

    private static final String FIND_PAYMENTS_BY_STUDIO_NAME =
            "SELECT studios.id, studios.name, SUM(studios.payment) royalty, COUNT(*) viewings \n"
                    + "FROM studios, episodes, viewings \n"
                    + "WHERE \n"
                    + "studios.id = episodes.studioId AND \n"
                    + "episodes.id = viewings.episodeId AND \n"
                    + "studios.id = :studioId \n"
                    + "GROUP BY studios.id, studios.name";

    public List<Payment> getGroupedRoyaltyPayments() {
        Map<String, Object> params = new HashMap<>();
        List<Payment> result = new ArrayList<>();

        try {
            result = jdbcTemplate.query(
                    FIND_PAYMENTS_BY_STUDIO,
                    params,
                    this.getRowMapper());

        } catch (EmptyResultDataAccessException e) {}
        return result;
    }

    public Payment getGroupedRoyaltyPayments(String studioId) {
        Map<String, Object> params = new HashMap<>();
        params.put("studioId", studioId);

        List<Payment> result = null;
        try {
            result = jdbcTemplate.query(
                    FIND_PAYMENTS_BY_STUDIO_NAME,
                    params,
                    this.getRowMapper());

        } catch (EmptyResultDataAccessException e) {}

        return !CollectionUtils.isEmpty(result) ? result.get(0) : null;
    }

    @Override
    RowMapper<Payment> getRowMapper() {
        return (rs, rowNum) -> Payment.builder()
                .rightsOwnerId(rs.getString("id"))
                .rightsOwner(rs.getString("name"))
                .royalty(rs.getBigDecimal("royalty"))
                .viewings(rs.getInt("viewings"))
                .build();
    }

}
