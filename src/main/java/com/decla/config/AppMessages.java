package com.decla.config;

import io.quarkus.qute.i18n.Message;
import io.quarkus.qute.i18n.MessageBundle;

@MessageBundle
public interface AppMessages {

    @Message("Una app para firmar declaraciones juradas...!")
    String app_description();
}