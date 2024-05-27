package com.bankinc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalanceCardDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "El campo id no puede estar vacio")
	@JsonProperty("cardId")
	private Long idCard;
	
	@NotNull(message = "El campo balance no puede estar vacio")
	private Double balance;

}
