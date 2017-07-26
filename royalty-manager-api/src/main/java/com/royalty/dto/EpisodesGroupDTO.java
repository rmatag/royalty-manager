package com.royalty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpisodesGroupDTO {

    String studioId;
    String studioName;
    List<EpisodeDTO> episodes;
}
