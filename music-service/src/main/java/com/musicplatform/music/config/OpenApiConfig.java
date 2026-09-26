package com.musicplatform.music.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	
	  @Bean
	    public OpenAPI musicPlatformOpenApi() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("Music Platform API")
	                        .version("1.0")
	                        .description("REST API for a Spotify-inspired music platform. "
	                                + "Manages artists, albums, and songs with pagination, "
	                                + "validation, and unified error responses."));
	    }

}
