package com.royalty.config;

import com.royalty.dto.EpisodeDTO;
import com.royalty.dto.PaymentDTO;
import com.royalty.dto.PaymentStudioDTO;
import com.royalty.dto.StudioDTO;
import com.royalty.model.Episode;
import com.royalty.model.Payment;
import com.royalty.model.Studio;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {

    @Bean
    public BeanMappingBuilder beanMappingBuilder() {
        BeanMappingBuilder builder = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Studio.class, StudioDTO.class);
                mapping(Payment.class, PaymentDTO.class);
                mapping(Payment.class, PaymentStudioDTO.class);
                mapping(Episode.class, EpisodeDTO.class);
            }
        };
        return builder;
    }

    @Bean
    public DozerBeanMapper dozerBeanMapper(BeanMappingBuilder beanMappingBuilder) {
        DozerBeanMapper mapper =  new DozerBeanMapper();
        mapper.addMapping(beanMappingBuilder);
        return mapper;
    }
}
