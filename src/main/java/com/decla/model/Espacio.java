package com.decla.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEspacio;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    @ManyToOne
    @JoinColumn(name = "idTipo")
    private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "idEntidad")
    private Entidad entidad;

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        return "Espacio [descripcion=" + descripcion + ", entidad=" + entidad + ", idEspacio=" + idEspacio + ", nombre="
                + nombre + ", tipo=" + tipo + ", ubicacion=" + ubicacion + "]";
    }
  
}