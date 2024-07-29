package com.simple.StES.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.StES.DTO.AddressDTO;
import com.simple.StES.repository.memRepository;
import com.simple.StES.vo.memVo;

import jakarta.servlet.http.HttpSession;

@Service
public class MemberService {
	
	@Autowired
    private memRepository memberRepository;
	
	public void updateAddress(String userId, AddressDTO address, HttpSession session) {
        memVo memVo = (memVo) session.getAttribute("memVo");
        if (memVo != null) {
            memVo.setAddress(address.getAddress());
            memVo.setAddressDetail(address.getDetailAddress());

            memberRepository.save(memVo);
        } else {
            throw new RuntimeException("Session does not contain user data.");
        }
    }
	

	
}
