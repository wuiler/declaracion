package com.decla.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String mail;	
	
	private String nombre;
	
	private String apellido;
	
	private String cuil;
	
	private long numeroDocumento;
	
	private int tipoDocumento;
		
	private String domicilio;

	private String numero;
	
	private String piso;
	
	private String departamento;

	private String sexo;

	private String telefonoFijo;

	private String telefonoMovil;
	
	private String fechaNacimiento;
	
	private String estadoCivil;

	private String barrio;
	
	private String pais;

	private String nacionalidad;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public long getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public int getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
		
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getNombreyApellido() {
		return (apellido==null) ? nombre : nombre + ' ' + apellido;
		//StringBuilder no se puede usar al estar hererado de otra clase.
		//new StringBuilder(nombre).append(" ").append(apellido).toString();
	}
	
	public String getApellidoyNombre() {
		return (apellido==null) ? nombre : apellido + ' ' + nombre;  
		//StringBuilder no se puede usar al estar hererado de otra clase.		
		//new StringBuilder(apellido).append(" ").append(nombre).toString();
	}

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Persona [nombre=");
		builder.append(nombre);
		builder.append(", apellido=");
		builder.append(apellido);
		builder.append(", cuil=");
		builder.append(cuil);
		builder.append(", domicilio=");
		builder.append(domicilio);
		builder.append(", numeroDocumento=");
		builder.append(numeroDocumento);
		builder.append(", tipoDocumento=");
		builder.append(tipoDocumento);
		builder.append(", mail=");
		builder.append(mail);
		builder.append("]");
		return builder.toString();
	}

}
