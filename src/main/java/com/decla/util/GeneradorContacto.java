package com.decla.util;

import com.decla.model.Persona;

/**
 *
 * @author nyj
 */
public class GeneradorContacto {

    public GeneradorContacto() {
    }

    public static String encodeVCard(Persona persona) {
        StringBuilder content = new StringBuilder();

        String name = persona.getNombreyApellido();
        String documento = String.valueOf(persona.getNumeroDocumento());
        String phone = persona.getTelefonoMovil();
        String email = persona.getMail();
        String addr = persona.getDomicilio();

        content.append("BEGIN:VCARD\n"); // begin of vcard

        content.append("N:").append(name).append("\n");
        if (!phone.isEmpty()) {
            content.append("TEL:").append(phone).append("\n");
        }
        if (!email.isEmpty()) {
            content.append("EMAIL:" + email + "\n");
        }
        if (!addr.isEmpty()) {
            content.append("ADR:").append(addr).append("\n");
        }
        if (!documento.isEmpty()) {
            content.append("NOTE:" + documento + "\n");
        }

        content.append("END:VCARD"); // end of VCARD
        return content.toString();
    }
}
