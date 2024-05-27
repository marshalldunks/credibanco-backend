package com.bankinc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "El campo id no puede estar vacio")
	@JsonProperty("cardId")
	private Long idCard;
	
	@NotNull(message = "El campo precio no puede estar vacio")
	@JsonProperty("price")
	private Double precio;

}
