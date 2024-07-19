package com.simple.StES.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.simple.StES.vo.itemVo;

public interface itemRepository extends CrudRepository<itemVo, Integer>{
	
	public Iterable<itemVo> findAll();
	
	public itemVo findByItemNum(Integer itemNum);
	
}
