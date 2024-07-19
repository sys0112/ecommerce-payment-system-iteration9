package com.simple.StES.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.simple.StES.vo.basketVo;

public interface basketRepository extends JpaRepository<basketVo, Integer> {

	public List<basketVo> findByMemberId(String memberId);
	public basketVo findByItemNumAndMemberId(int itemNum,String memberId);

}
