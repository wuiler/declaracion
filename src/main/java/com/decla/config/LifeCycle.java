package com.decla.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;


@ApplicationScoped
public class LifeCycle {

    private static final Logger LOGGER = LoggerFactory.getLogger("APPLifeCycle");

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Iniciando la aplicacion ... Yeah!!!");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("Deteniendo la aplicacion ... Buuu :-(");
    }

}