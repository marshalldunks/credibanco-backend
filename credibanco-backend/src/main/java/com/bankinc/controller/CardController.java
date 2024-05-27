package com.bankinc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bankinc.dto.AnulationDTO;
import com.bankinc.dto.BalanceCardDTO;
import com.bankinc.dto.EnrollCardDTO;
import com.bankinc.dto.PurchaseDTO;
import com.bankinc.dto.TransactionDTO;
import com.bankinc.exception.CredibancoException;
import com.bankinc.service.ICardService;




@CrossOrigin(origins = { "*" })
@RestController
//@RequestMapping("/card")
public class CardController {
	
	@Autowired
	ICardService cardService;
	
	@RequestMapping(value="/card/{productId}/number", method=RequestMethod.GET)
	public ResponseEntity<?> generateNumber(@PathVariable Long productId) {
		
		Long newProductId = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			newProductId = cardService.generateNumber(productId);
		} catch (Exception e) {
			response.put("mensaje", "Error al crear la tarjeta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<Long>(newProductId, HttpStatus.OK);
	}
	
    @RequestMapping(value="/card/enroll", method=RequestMethod.POST)
	public ResponseEntity<?> enrollCard(@Valid @RequestBody EnrollCardDTO enrollCardDTO, BindingResult result) {
		
		EnrollCardDTO EnrollCardDTONew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			EnrollCardDTONew = cardService.enrollCard(enrollCardDTO);
		} catch(DataAccessException | CredibancoException e) {
			response.put("mensaje", e.getMessage());
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		response.put("mensaje", "La tarjeta ha sido activada con éxito!");
		response.put("tarjeta", EnrollCardDTONew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
    @RequestMapping(value="/card/{cardId}", method=RequestMethod.DELETE)
	public ResponseEntity<?> blockCard(@PathVariable Long cardId) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			cardService.blockCard(cardId);
		} catch (DataAccessException | CredibancoException e) {
			response.put("mensaje", "Error al bloquear La tarjeta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Tarjeta bloqueada con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
    
    @RequestMapping(value="/card/balance", method=RequestMethod.POST)
	public ResponseEntity<?> addBalance(@Valid @RequestBody BalanceCardDTO balanceCardDTO, BindingResult result) {
		
    	BalanceCardDTO BalanceCardDTONew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			BalanceCardDTONew = cardService.addBalance(balanceCardDTO);
		} catch(DataAccessException | CredibancoException e) {
			response.put("mensaje", e.getMessage());
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		response.put("mensaje", "Balance ha sido agregado con éxito!");
		response.put("cliente", BalanceCardDTONew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
    @RequestMapping(value="/card/balance/{cardId}", method=RequestMethod.GET)
	public ResponseEntity<?> getBalance(@PathVariable Long cardId) {
		
    	BalanceCardDTO card = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			card = cardService.getBalance(cardId);
		} catch (Exception e) {
			response.put("mensaje", "Error al consultar el balance de la tarjeta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<BalanceCardDTO>(card, HttpStatus.OK);
	}
    
    
    @RequestMapping(value="/transaction/purchase", method=RequestMethod.POST)
	public ResponseEntity<?> purchase(@Valid @RequestBody PurchaseDTO purchaseDTO, BindingResult result) {
		
    	PurchaseDTO purchaseDTONew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			purchaseDTONew = cardService.purchase(purchaseDTO);
		} catch(DataAccessException | CredibancoException e) {
			response.put("mensaje", e.getMessage());
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		response.put("mensaje", "Compra realizada con éxito!");
		response.put("cliente", purchaseDTONew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
    @RequestMapping(value="/transaction/{transactionId}", method=RequestMethod.GET)
	public ResponseEntity<?> getTransaction(@PathVariable Long transactionId) {
		
    	TransactionDTO transactionDTO = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			transactionDTO = cardService.findTransactionById(transactionId);
		} catch (DataAccessException | CredibancoException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(transactionDTO == null) {
			response.put("mensaje", "La transaccion ID: ".concat(transactionId.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TransactionDTO>(transactionDTO, HttpStatus.OK);
	}
    
    @RequestMapping(value="/transaction/anulation", method=RequestMethod.POST)
	public ResponseEntity<?> anulation(@Valid @RequestBody AnulationDTO anulationDTO, BindingResult result) {
		
    	AnulationDTO anulationDTONew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			anulationDTONew = cardService.anulation(anulationDTO);
		} catch(DataAccessException | CredibancoException e) {
			response.put("mensaje", e.getMessage());
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		response.put("mensaje", "Compra anulada con éxito!");
		response.put("cliente", anulationDTONew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
