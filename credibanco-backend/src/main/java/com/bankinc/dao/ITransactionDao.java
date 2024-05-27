package com.bankinc.dao;

import org.springframework.data.repository.CrudRepository;

import com.bankinc.entity.Transaction;

public interface ITransactionDao extends CrudRepository<Transaction, Long> {

}
