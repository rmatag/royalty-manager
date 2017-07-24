package com.royalty.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String rightsOwnerId;
    private String rightsOwner;
    private BigDecimal royalty;
    private Integer viewings;

}
