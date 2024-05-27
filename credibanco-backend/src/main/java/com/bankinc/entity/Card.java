package com.bankinc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name="cards")
public class Card implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_card", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "nombre_titular", unique = true, length = 200, nullable = false)
	private String nombreTitular;
	
	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@Column(name = "saldo", nullable = false)
	private double saldo;
	
	private Boolean activa;

}
