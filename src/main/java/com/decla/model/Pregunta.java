package com.decla.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPregunta;
    private String nombre;
    private String tipo;
    @Column(nullable = true, columnDefinition = "integer default 1")
    private int estado;

    @ManyToMany(mappedBy = "preguntas")
    private List<Declaracion> declaracions = new ArrayList<>();    

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<Declaracion> getDeclaracions() {
        return declaracions;
    }

    public void setDeclaracions(List<Declaracion> declaracions) {
        this.declaracions = declaracions;
    }    

    @Override
    public String toString() {
        return "Pregunta [idPregunta=" + idPregunta + ", nombre=" + nombre + ", tipo=" + tipo + "]";
    }

}