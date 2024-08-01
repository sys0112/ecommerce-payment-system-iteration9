package com.simple.StES.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.simple.StES.vo.memVo;

public interface memRepository extends CrudRepository<memVo, Integer>{
	
	public List<memVo> findAllByOrderByIdAsc();
	public memVo findByEmailAndPw(String email, String pw);
	
	public Optional<memVo> findById(String id);
	public Optional<memVo> findByEmail(String email);	
	public Optional<memVo> findByPhone(String phone);

}
