package com.simple.StES.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.simple.StES.vo.memVo;

public interface memRepository extends CrudRepository<memVo, Integer>{
	
	public List<memVo> findAllByOrderByIdAsc();
	public memVo findByIdAndPw(String id, String pw);
	public memVo findByPhone(String phone);
	public memVo findByEmail(String email);

}
