package com.decla.rest;

import java.util.Collection;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import com.decla.config.AppConfig;
import com.decla.model.Declaracion;
import com.decla.model.DeclaracionEntidad;
import com.decla.model.Entidad;
import com.decla.model.Tipo;
import com.decla.repository.DeclaracionEntidadRepository;
import com.decla.repository.DeclaracionRepository;
import com.decla.repository.EntidadRepository;
import com.decla.repository.TipoRepository;
import com.decla.util.Crypto;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import io.quarkus.mailer.Mailer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@ApplicationScoped
@Path("/api/entidad")
public class EntidadResource {

    private static final Logger LOG = Logger.getLogger(EntidadResource.class);

    @Inject
    Mailer mailer;

    @Inject
    EntidadRepository controladorEntidad;

    @Inject
    DeclaracionRepository controladorDeclaracion;

    @Inject
    DeclaracionEntidadRepository controladorDeclaracionEntidad;

    @Inject
    TipoRepository controladorTipo;

    @Inject
    AppConfig config;

    @POST
    @Path("/registrar")
    @Produces({ MediaType.APPLICATION_JSON })
    public Entidad create(@FormParam("txtNombre") String nombre, @FormParam("txtMobile") String telefono, @FormParam("txtMail") String mail, @FormParam("cmbTipo") int idTipo) {
        
        Tipo tipo = controladorTipo.findById(idTipo);

        Entidad e = new Entidad();
        e.setNombre(nombre); 
        e.setTelefono(telefono);
        e.setTipo(tipo);
        e.setMail(mail);
        e.setEsPublica(true);


        controladorEntidad.save(e);

        return e;

    }

    @POST
    @Path("/registraryasignar")
    @Produces({ MediaType.APPLICATION_JSON })
    public Entidad create(@FormParam("txtCreadoPor") String creadoPor, @FormParam("txtNombre") String nombre, @FormParam("txtMobile") String telefono, @FormParam("txtMail") String mail, @FormParam("cmbTipo") int idTipo, @FormParam("cmbPublica") String esPublica) {
        
        Tipo tipo = controladorTipo.findById(idTipo);

        LOG.infof("user registraryasignar: %s",creadoPor);
        LOG.infof("user esPublica: %s",esPublica);

        Boolean publica = false;

        if ("1".equals(esPublica)) {
            publica = true;
        } else {
            publica = false;
        }

        Entidad e = new Entidad();
        e.setNombre(nombre); 
        e.setTelefono(telefono);
        e.setTipo(tipo);
        e.setMail(mail);        
        e.setEsPublica(publica);
        e.setCreatedAt(new Date());
        e.setCreadoPor(creadoPor);        

        controladorEntidad.save(e);

        Declaracion declaracion = controladorDeclaracion.findDeclaracionBase();

        LOG.infof("Declaracion : %s", declaracion.toString());

        DeclaracionEntidad declaracionEntidad = new DeclaracionEntidad();
        declaracionEntidad.setEntidad(e);
        declaracionEntidad.setDeclaracion(declaracion);
        declaracionEntidad.setEstado(1);
        declaracionEntidad.setFecha(new Date());
        

        controladorDeclaracionEntidad.save(declaracionEntidad);
        
        return e;

    }


    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Collection<Entidad> list() {
        
        return controladorEntidad.list();

    }

    @GET
    @Produces("image/jpeg")
    @Path("/qr/{id}")
    public Response qr(@PathParam("id") String id) {

        QRCodeWriter writer = new QRCodeWriter();
        String sbQR = "";

        ResponseBuilder response = null;
        
        try {
            
            //Hashtable<EncodeHintType, Integer> hintMap = null;
            Hashtable<EncodeHintType, Object> hintMap = new Hashtable<EncodeHintType, Object>();
            hintMap.put(EncodeHintType.MARGIN, 2);
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);


            String url = config.url();
            String hash = Crypto.b64Encode(id);
            
            sbQR = url + "declaracion?entidad="+hash;

            BitMatrix matrix = writer.encode(sbQR, BarcodeFormat.QR_CODE, 400, 400, hintMap);
            
            java.awt.image.BufferedImage bf = MatrixToImageWriter.toBufferedImage(matrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bf, "jpg", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();

            ResponseBuilder responseImage = Response.ok(imageInByte);
            responseImage.header("Content-Disposition", "inline");


            response = responseImage;

            //return null;

        } catch (WriterException we) {
            System.out.print("Error en qr{}"+we);
        } catch (IOException e) {
			System.out.println(e.getMessage());
		}

        return response.build();

    }


}
