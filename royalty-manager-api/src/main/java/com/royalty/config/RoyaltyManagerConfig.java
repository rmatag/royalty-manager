package com.royalty.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ JerseyServerConfig.class, H2DataBaseConfig.class, DozerConfig.class, WebSecurityConfiguration.class })
public class RoyaltyManagerConfig {

}
