package com.royalty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStudioDTO {
    private String rightsOwner;
    private BigDecimal royalty;
    private Integer viewings;

}
