package com.decla.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.decla.util.Crypto;

@Entity
public class Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEntidad;
    private String nombre;
    private String nombreCorto;
    private String url;
    private String telefono;
    private String direccion;
    private String mail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, columnDefinition = "timestamp default now()")
    private Date createdAt;    

    @ManyToOne
    @JoinColumn(name = "idTipo")
    private Tipo tipo;
    
    private String creadoPor;    
    private Boolean esPublica;

    public long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getHashId() {
        return Crypto.b64Encode(String.valueOf(this.idEntidad));
    }

    public Boolean getEsPublica() {
        return esPublica;
    }

    public void setEsPublica(Boolean esPublica) {
        this.esPublica = esPublica;
    }

    @Override
    public String toString() {
        return "Entidad [creadoPor=" + creadoPor + ", createdAt=" + createdAt + ", direccion=" + direccion
                + ", idEntidad=" + idEntidad + ", mail=" + mail + ", nombre=" + nombre + ", nombreCorto=" + nombreCorto
                + ", telefono=" + telefono + ", tipo=" + tipo + ", url=" + url + "]";
    }

}