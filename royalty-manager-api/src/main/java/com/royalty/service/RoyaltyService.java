package com.royalty.service;

import com.royalty.dao.PaymentDAO;
import com.royalty.dao.StudioDAO;
import com.royalty.dao.ViewingDAO;
import com.royalty.dto.PaymentDTO;
import com.royalty.dto.PaymentStudioDTO;
import com.royalty.dto.StudioDTO;
import com.royalty.exceptions.GUIDNotFoundException;
import com.royalty.model.Payment;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

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

    public List<StudioDTO> getStudios() {
        final List<StudioDTO> studioDTOS = new ArrayList<>();

        studioDAO.getStudios().stream().forEach(s ->
            studioDTOS.add(mapper.map(s, StudioDTO.class)
        ));
        return studioDTOS;
    }

    public void createViewing(String userId, String episodeId) {
        this.viewingDAO.postViewing(userId, episodeId);
    }

    public void resetViewings() {
        this.viewingDAO.reset();
    }

    public List<PaymentDTO> getRoyaltyPayments() {
        final List<PaymentDTO> paymentDTOS = new ArrayList<>();
        paymentDAO.getGroupedRoyaltyPayments().stream().forEach(p ->
            paymentDTOS.add(mapper.map(p, PaymentDTO.class)
        ));

        return paymentDTOS;
    }

    public PaymentStudioDTO getRoyaltyPayments(String rightOwnerId) {

        Payment payment = paymentDAO.getGroupedRoyaltyPayments(rightOwnerId);
        if (payment == null) {
            throw new GUIDNotFoundException(String.format("There is no viewings for the Right Owner: %s", rightOwnerId));
        }
        return mapper.map(payment, PaymentStudioDTO.class);

    }
}
