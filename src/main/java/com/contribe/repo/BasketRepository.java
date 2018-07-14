package com.contribe.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contribe.domainobject.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long>  {
	
	Basket findByUserId(Long userId);

}
