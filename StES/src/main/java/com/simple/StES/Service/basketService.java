package com.simple.StES.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simple.StES.repository.basketRepository;

@Service
public class basketService {

	@Autowired
    private basketRepository basketRepository;
	
	public void clearBasketForUser(String userId) {
        
    }
}
