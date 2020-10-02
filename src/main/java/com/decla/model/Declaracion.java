package com.decla.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Declaracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeclaracion;

    private String nombre;

    @Column(columnDefinition = "timestamp default now()")
    private Date fechaInicio;

    private Date fechaFin;

    private String creadaPor;

    private int estado;

    private Boolean esBase;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "DeclaracionPreguntas",
        joinColumns = @JoinColumn(name = "idDeclaracion"),
        inverseJoinColumns = @JoinColumn(name = "idPregunta")
    )
    private List<Pregunta> preguntas = new ArrayList<>();

    public Long getIdDeclaracion() {
        return idDeclaracion;
    }

    public void setIdDeclaracion(Long idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCreadaPor() {
        return creadaPor;
    }

    public void setCreadaPor(String creadaPor) {
        this.creadaPor = creadaPor;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Boolean getEsBase() {
        return esBase;
    }

    public void setEsBase(Boolean esBase) {
        this.esBase = esBase;
    }

    @Override
    public String toString() {
        return "Declaracion [creadaPor=" + creadaPor + ", esBase=" + esBase + ", estado=" + estado + ", fechaFin="
                + fechaFin + ", fechaInicio=" + fechaInicio + ", idDeclaracion=" + idDeclaracion + ", nombre=" + nombre
                + ", preguntas=" + preguntas + "]";
    }

}