package com.royalty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewingRequestDTO {

    @NotEmpty
    private String episode;

    @NotEmpty
    private String customer;
}
