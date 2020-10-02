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

import com.decla.util.Fecha;

@Entity
public class DeclaracionEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDeclaracionEntidad;

	@ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="idEntidad")
    private Entidad entidad;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="idDeclaracion")
    private Declaracion declaracion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "timestamp default now()")
    private Date fecha;

    private int estado;

    private int tipoIngreso;

    public int getIdDeclaracionEntidad() {
        return idDeclaracionEntidad;
    }

    public void setIdDeclaracionEntidad(int idDeclaracionEntidad) {
        this.idDeclaracionEntidad = idDeclaracionEntidad;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Declaracion getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(Declaracion declaracion) {
        this.declaracion = declaracion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public int getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(int tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    @Override
    public String toString() {
        return "DeclaracionEntidad [declaracion=" + declaracion + ", entidad=" + entidad + ", estado=" + estado
                + ", fecha=" + fecha + ", idDeclaracionEntidad=" + idDeclaracionEntidad + ", tipoIngreso=" + tipoIngreso
                + "]";
    }

}