package com.royalty.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Payment {
    private String rightsOwnerId;
    private String rightsOwner;
    private BigDecimal royalty;
    private Integer viewings;

}
