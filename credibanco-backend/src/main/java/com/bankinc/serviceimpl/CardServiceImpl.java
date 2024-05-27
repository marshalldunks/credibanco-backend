package com.bankinc.serviceimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankinc.dao.ICardDao;
import com.bankinc.dao.ITransactionDao;
import com.bankinc.dto.AnulationDTO;
import com.bankinc.dto.BalanceCardDTO;
import com.bankinc.dto.EnrollCardDTO;
import com.bankinc.dto.PurchaseDTO;
import com.bankinc.dto.TransactionDTO;
import com.bankinc.entity.Card;
import com.bankinc.entity.Transaction;
import com.bankinc.exception.CredibancoException;
import com.bankinc.service.ICardService;

@Service
public class CardServiceImpl implements ICardService{
	
	@Autowired
	private ICardDao cardDao;
	
	@Autowired
	private ITransactionDao transactionDao;

	@Override
	public Long generateNumber(Long productId) {
		String result = productId.toString()+generateRandomNumber();
		
		Card newCard = new Card();
		newCard.setId(new Long(result));
		newCard.setNombreTitular("nuevo titular");
		newCard.setSaldo(0L);
		newCard.setFechaCreacion(new Date());
		newCard.setActiva(Boolean.FALSE);
		cardDao.save(newCard);
		
		return new Long(result);
	}
	
	
	
    private Long generateRandomNumber() { 
        Random random = new Random(); 
        long randomNumber = Math.abs(random.nextLong()); 
        String randomString = Long.toString(randomNumber); 
        String tenDigitNumber = randomString.substring(0, 10); 
        return new Long(tenDigitNumber);
    }
    

	@Override
	public EnrollCardDTO enrollCard(EnrollCardDTO enrollCardDTO) throws CredibancoException {
		Optional<Card> cardOpt = cardDao.findById(enrollCardDTO.getIdCard());
		if(!cardOpt.isPresent()) {
			throw new CredibancoException("La tarjeta no existe en el sistema");
		}
		
		Card newCard = cardOpt.get();
		newCard.setActiva(Boolean.TRUE);
		cardDao.save(newCard);
		return enrollCardDTO;
		
	}



	@Override
	public Long blockCard(Long cardId) throws CredibancoException {
		Optional<Card> cardOpt = cardDao.findById(cardId);
		if(!cardOpt.isPresent()) {
			throw new CredibancoException("La tarjeta no existe en el sistema");
		}
		
		Card newCard = cardOpt.get();
		newCard.setActiva(Boolean.FALSE);
		cardDao.save(newCard);
		return cardId;
	}



	@Override
	public BalanceCardDTO addBalance(BalanceCardDTO balanceCardDTO) throws CredibancoException {
		
		if(balanceCardDTO.getBalance()< 0) {
			throw new CredibancoException("El balance no puede ser negativo");
		}
		
		Optional<Card> cardOpt = cardDao.findById(balanceCardDTO.getIdCard());
			
		if(!cardOpt.isPresent()) {
			throw new CredibancoException("La tarjeta no existe en el sistema");
		}
		
		Double newSaldo = balanceCardDTO.getBalance() + cardOpt.get().getSaldo();
		
		Card newCard = cardOpt.get();
		newCard.setSaldo(newSaldo);
		cardDao.save(newCard);
		return balanceCardDTO;
	}



	@Override
	public BalanceCardDTO getBalance(Long cardId) throws CredibancoException {
		Optional<Card> cardOpt = cardDao.findById(cardId);
		if(!cardOpt.isPresent()) {
			throw new CredibancoException("La tarjeta no existe en el sistema");
		}
		
		BalanceCardDTO result = new BalanceCardDTO();
		result.setIdCard(cardId);
		result.setBalance(cardOpt.get().getSaldo());
		
		return result;
	}



	@Override
	public PurchaseDTO purchase(PurchaseDTO purchaseDTO) throws CredibancoException {
		Optional<Card> cardOpt = cardDao.findById(purchaseDTO.getIdCard());
		if(!cardOpt.isPresent()) {
			throw new CredibancoException("La tarjeta no existe en el sistema");
		}
		
		if(cardOpt.get().getSaldo() < purchaseDTO.getPrecio()) {
			throw new CredibancoException("El saldo no es suficiente para realizar la compra");
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(cardOpt.get().getFechaCreacion());
		cal.add(Calendar.YEAR, 3);
		
		Date modifiedDate = cal.getTime();
		Date today = new Date();
		
		if(today.after(modifiedDate)) {
			throw new CredibancoException("La fecha actual es mayor que la fecha de vencimiento");
		}
		
		Transaction transaction = new Transaction();
		transaction.setCard(cardOpt.get());
		transaction.setPrice(purchaseDTO.getPrecio());
		transaction.setValid(Boolean.TRUE);
		transaction.setFechaCreacion(new Date());
		transactionDao.save(transaction);
		
		cardOpt.get().setSaldo(cardOpt.get().getSaldo() - purchaseDTO.getPrecio());
		cardDao.save(cardOpt.get());
		
		return purchaseDTO;
	}



	@Override
	public TransactionDTO findTransactionById(Long cardId) throws CredibancoException {
		TransactionDTO transactionDTO = null;
		
		Optional<Transaction> transactiondOpt = transactionDao.findById(cardId);
		if(!transactiondOpt.isPresent()) {
			throw new CredibancoException("La transaccion no existe en el sistema");
		}
		
		transactionDTO = new TransactionDTO();
		transactionDTO.setId(transactiondOpt.get().getId());
		transactionDTO.setIdCard(transactiondOpt.get().getCard().getId());
		transactionDTO.setPrice(transactiondOpt.get().getPrice());
		transactionDTO.setFechaCreacion(transactiondOpt.get().getFechaCreacion());
		transactionDTO.setValid(transactiondOpt.get().getValid());
		
		return transactionDTO;
	}



	@Override
	public AnulationDTO anulation(AnulationDTO anulationDTO) throws CredibancoException {
		
		Optional<Transaction> transactiondOpt = transactionDao.findById(anulationDTO.getTransactionId());
		if(!transactiondOpt.isPresent()) {
			throw new CredibancoException("La transaccion no existe en el sistema");
		}
		
		Optional<Card> cardOpt = cardDao.findById(anulationDTO.getIdCard());
		if(!cardOpt.isPresent()) {
			throw new CredibancoException("La tarjeta no existe en el sistema");
		}
		
		cardOpt.get().setSaldo(cardOpt.get().getSaldo() + transactiondOpt.get().getPrice());
		cardDao.save(cardOpt.get());
		
		transactiondOpt.get().setValid(Boolean.FALSE);
		transactionDao.save(transactiondOpt.get());
		
		return anulationDTO;
	}

}
