package com.bankinc.dto;

import java.io.Serializable;
import java.util.Date;

import com.bankinc.entity.Card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long idCard;
	
	private Double price;
	
	private Date fechaCreacion;
	
	private Boolean valid;

}
