package com.bankinc.service;

import com.bankinc.dto.AnulationDTO;
import com.bankinc.dto.BalanceCardDTO;
import com.bankinc.dto.EnrollCardDTO;
import com.bankinc.dto.PurchaseDTO;
import com.bankinc.dto.TransactionDTO;
import com.bankinc.exception.CredibancoException;


public interface ICardService {
	
	public Long generateNumber(Long productId);
	
	public EnrollCardDTO enrollCard(EnrollCardDTO enrollCardDTO) throws CredibancoException;
	
	public Long blockCard(Long cardId) throws CredibancoException;
	
	public BalanceCardDTO addBalance(BalanceCardDTO balanceCardDTO) throws CredibancoException;
	
	public BalanceCardDTO getBalance(Long cardId) throws CredibancoException;
	
	public PurchaseDTO purchase(PurchaseDTO purchaseDTO) throws CredibancoException;
	
	public TransactionDTO findTransactionById(Long cardId) throws CredibancoException;
	
	public AnulationDTO anulation(AnulationDTO anulationDTO) throws CredibancoException;

}
