package com.simple.StES.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simple.StES.repository.basketRepository;
import com.simple.StES.vo.basketVo;

import jakarta.transaction.Transactional;

@Service
public class basketService {

	@Autowired
    private basketRepository basketRepository;
	
	@Transactional
    public void clearBasketForUser(String userId) {
        List<basketVo> basketItems = basketRepository.findByMemberId(userId);
        for (basketVo item : basketItems) {
            basketRepository.delete(item);
        }
    }
}
