package com.royalty.service;

import com.royalty.dao.EpisodeDAO;
import com.royalty.dao.PaymentDAO;
import com.royalty.dao.StudioDAO;
import com.royalty.dao.ViewingDAO;
import com.royalty.dto.EpisodeDTO;
import com.royalty.dto.EpisodesGroupDTO;
import com.royalty.dto.PaymentDTO;
import com.royalty.dto.PaymentStudioDTO;
import com.royalty.dto.StudioDTO;
import com.royalty.exceptions.GUIDNotFoundException;
import com.royalty.model.Episode;
import com.royalty.model.Payment;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoyaltyService {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    StudioDAO studioDAO;

    @Autowired
    ViewingDAO viewingDAO;

    @Autowired
    PaymentDAO paymentDAO;

    @Autowired
    EpisodeDAO episodeDAO;

    public List<StudioDTO> getStudios() {

        return studioDAO.getStudios()
                .stream()
                .map(s -> mapper.map(s, StudioDTO.class))
                .collect(Collectors.toList());
    }

    public void createViewing(String userId, String episodeId) {
        this.viewingDAO.postViewing(userId, episodeId);
    }

    public void resetViewings() {
        this.viewingDAO.reset();
    }

    public List<PaymentDTO> getRoyaltyPayments() {

        return paymentDAO.getGroupedRoyaltyPayments()
                .stream()
                .map(p -> mapper.map(p, PaymentDTO.class))
                .collect(Collectors.toList());

    }

    public PaymentStudioDTO getRoyaltyPayments(String rightOwnerId) {

        Payment payment = paymentDAO.getGroupedRoyaltyPayments(rightOwnerId);
        if (payment == null) {
            throw new GUIDNotFoundException(String.format("There is no viewings for the Right Owner: %s", rightOwnerId));
        }
        return mapper.map(payment, PaymentStudioDTO.class);

    }

    public List<EpisodesGroupDTO> getEpisodesByStudio() {
        List<EpisodeDTO> episodeDTOS = episodeDAO.getEpisodesByStudio()
                .stream()
                .map(e -> mapper.map(e, EpisodeDTO.class))
                .collect(Collectors.toList());

        Map<String, List<EpisodeDTO>> episodesByStudio = episodeDTOS.stream()
                .collect(Collectors.groupingBy(e -> e.getStudioName()));

        return episodesByStudio.keySet().stream()
                .map((String s) -> new EpisodesGroupDTO(episodesByStudio.get(s).get(0).getStudioId(), s,
                        episodesByStudio.get(s)))
                .collect(Collectors.toList());
    }
}
