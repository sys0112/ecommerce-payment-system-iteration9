package com.simple.StES.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.simple.StES.vo.memVo;
import com.simple.StES.vo.payVo;

public interface payRepository extends CrudRepository<payVo, Integer>{
	
	public List<payVo> findByMemberId(String memberId);
	public payVo findByPayNum(Long payNum);
	public List<payVo> findByPayTime(LocalDateTime payTime);

}
