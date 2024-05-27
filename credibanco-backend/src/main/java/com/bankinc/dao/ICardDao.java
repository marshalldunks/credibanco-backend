package com.bankinc.dao;

import org.springframework.data.repository.CrudRepository;

import com.bankinc.entity.Card;

public interface ICardDao extends CrudRepository<Card, Long>{

}
