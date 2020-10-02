package com.decla.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "app.config") 
public interface AppConfig {

    @ConfigProperty(name = "url") 
    String url();

}