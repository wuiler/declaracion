package com.decla.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRespuesta;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="mail")
    private Persona persona;

	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="idEntidad")
    private Entidad entidad;
   
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="idPregunta")
    private Pregunta pregunta;

    private String valor;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, columnDefinition = "timestamp default now()")
    private Date fecha;

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Respuesta [entidad=" + entidad + ", fecha=" + fecha + ", idRespuesta=" + idRespuesta + ", persona="
                + persona + ", pregunta=" + pregunta + ", valor=" + valor + "]";
    }

}
