package com.decla.model;

import java.util.Date;

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

import com.decla.util.Fecha;

@Entity
public class DeclaracionPersona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDeclaracionPersona;

	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="mail")
    private Persona persona;

	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="idEntidad")
    private Entidad entidad;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="idDeclaracion")
    private Declaracion declaracion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "timestamp default now()")
    private Date fecha;

    private int estado;

    public int getIdDeclaracionPersona() {
        return idDeclaracionPersona;
    }

    public void setIdDeclaracionPersona(int idDeclaracionPersona) {
        this.idDeclaracionPersona = idDeclaracionPersona;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Declaracion getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(Declaracion declaracion) {
        this.declaracion = declaracion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFechaFormateada() {
        return Fecha.formatDateTimeFull(this.getFecha());
    }

    @Override
    public String toString() {
        return "DeclaracionPersona [declaracion=" + declaracion + ", entidad=" + entidad + ", fecha=" + fecha
                + ", idDeclaracionPersona=" + idDeclaracionPersona + ", persona=" + persona + "]";
    }

}