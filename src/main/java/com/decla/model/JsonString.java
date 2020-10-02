package com.decla.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JsonString implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
