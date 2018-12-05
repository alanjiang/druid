package com.agilean.lessons.druid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agilean.lessons.druid.hibernate.jpa.entity.Druid;
import com.agilean.lessons.druid.hibernate.jpa.repository.DruidRepository;

@Service("druidService")
public class DruidService {

	@Autowired
	private DruidRepository druidRepository;
	@Transactional(value="transactionManager",readOnly=true)
	public Druid findDruid(Long id) {
		
		return druidRepository.findById(id).get();
	}
	@Transactional(value="transactionManager",rollbackFor={RuntimeException.class, Exception.class})
    public Druid save(Druid druid) {
		
		return druidRepository.save(druid);
	}
    @Transactional(value="transactionManager",readOnly=true)
   public Iterable<Druid> browse() {
		
		return druidRepository.findAll();
	}
}
