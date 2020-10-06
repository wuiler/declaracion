package com.decla.config;

import io.quarkus.qute.i18n.Localized;
import io.quarkus.qute.i18n.Message;

@Localized("de")
public interface SpanishAppMessages extends AppMessages {
    
    @Override
    @Message("Deu")
    String app_description();

}