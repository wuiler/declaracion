package com.decla.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.decla.model.Persona;
import com.decla.repository.PersonaRepository;
import com.decla.util.GeneradorContacto;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.jboss.logging.Logger;

import io.quarkus.mailer.Mailer;

@ApplicationScoped
@Path("/api/persona")
public class PersonaResource {

    private static final Logger LOG = Logger.getLogger(PersonaResource.class);

    @Inject
    Mailer mailer;

    @Inject
    PersonaRepository controlador;

    @POST
    @Produces({ MediaType.APPLICATION_JSON }) 
    @Path("/registrar")       
    public Persona create(@FormParam("txtMail") String mail, @FormParam("txtDocumento") Long documento, @FormParam("txtNombre") String nombre, @FormParam("txtApellido") String apellido, @FormParam("txtDomicilio") String domicilio, @FormParam("txtMobile") String mobile) {       

        Persona persona = null;
        persona = controlador.findById(mail);
        

        if (persona!=null) {
            LOG.info(persona.toString());
            
        } else {

            persona = new Persona();
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setDomicilio(domicilio);
            persona.setTelefonoMovil(mobile);
            persona.setNumeroDocumento(documento);
            persona.setMail(mail);
    
            LOG.info(persona.toString());
    
            controlador.save(persona);
    
        }

        return persona;

    }


    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/{mail}")       
    public Persona find(@PathParam("mail") String mail) {

        LOG.infof("El mail es : %s" , mail);

        Persona persona = null;
        persona = controlador.findById(mail);
        
        if (persona==null) {
    //ver la exception
            LOG.infof("No existe la persona con id: %s ", mail);        
        }

        return persona;

    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/list")
    public Collection<Persona> list() {
        return controlador.list();

    }

    @GET
    @Produces("image/jpeg")
    @Path("/qr")
    public Response qr(@PathParam("txtMail") String mail) {
        Persona persona = null;
        persona = controlador.findById(mail);
///ver si exsite la persona

        QRCodeWriter writer = new QRCodeWriter();
        String sbQR = "";

        ResponseBuilder response = null;
        
        try {
            
            Hashtable<EncodeHintType, Object> hintMap = new Hashtable<EncodeHintType, Object>();
            hintMap.put(EncodeHintType.MARGIN, 2);
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);


            sbQR = GeneradorContacto.encodeVCard(persona);

            BitMatrix matrix = writer.encode(sbQR, BarcodeFormat.QR_CODE, 200, 200, hintMap);
            
            java.awt.image.BufferedImage bf = MatrixToImageWriter.toBufferedImage(matrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bf, "jpg", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();

            ResponseBuilder responseImage = Response.ok(imageInByte);
            responseImage.header("Content-Disposition", "inline");

            response = responseImage;

        } catch (WriterException we) {
            System.out.print("Error en qr{}"+we);
        } catch (IOException e) {
			System.out.println(e.getMessage());
		}

        return response.build();

    }

}
