package com.bankinc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollCardDTO implements Serializable {

	
private static final long serialVersionUID = 1L;
	
	@NotNull(message = "El campo cardId no puede estar vacio")
	@JsonProperty("cardId") 
	private Long idCard;

}
