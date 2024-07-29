package com.simple.StES.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.StES.DTO.AddressDTO;
import com.simple.StES.Service.IamportService;
import com.simple.StES.Service.MemberService;
import com.simple.StES.repository.basketRepository;
import com.simple.StES.repository.memRepository;
import com.simple.StES.repository.payRepository;
import com.simple.StES.vo.basketVo;
import com.simple.StES.vo.memVo;
import com.simple.StES.vo.payVo;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
    private IamportService iamportService;
	
	@Autowired 
	basketRepository ibr;
	
	@Autowired 
	payRepository pr;
	
	@Autowired
    private MemberService memberService;

    
    @GetMapping("/form")
    public String checkout(Model model, HttpSession session) {
        memVo memVo = (memVo) session.getAttribute("memVo");
        List<basketVo> basketList = ibr.findByMemberId(memVo.getId());

        // 총합계 계산
        int totalSum = basketList.stream().mapToInt(b -> b.getCount() * b.getItem().getPrice()).sum();
        
        
        String goodsname = basketList.stream()
                .map(b -> b.getItem().getName() + " x " + b.getCount())
                .collect(Collectors.joining(", "));

// goodsname 출력
        System.out.println("Goods Name: " + goodsname);
        
        List<BasketDto> basketDtoList = basketList.stream()
                .map(basket -> new BasketDto(
                    basket.getItem().getName(),
                    basket.getCount(),
                    basket.getItem().getPrice()
                ))
                .collect(Collectors.toList());
        
        // 모델에 속성 추가
        model.addAttribute("basketList", basketDtoList);
        model.addAttribute("totalSum", totalSum);
        model.addAttribute("memVo", memVo);
        
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String basketListJson = objectMapper.writeValueAsString(basketDtoList);
            model.addAttribute("basketListJson", basketListJson);
            System.out.println("Basket List JSON: " + basketListJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return "/pay/paymentForm"; // checkout.html 템플릿
    }
    
    
    public class BasketDto {
        private String name;
        private int count;
        private int price;

        public BasketDto(String name, int count, int price) {
            this.name = name;
            this.count = count;
            this.price = price;
        }



		public String getName() {
			return name;
		}



		public void setName(String name) {
			this.name = name;
		}



		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

        
    }
    
    
    
    @PostMapping("/save")
    public String savePayments(@RequestBody List<PayRequest> payRequests, HttpSession session) {
    	memVo memVo=(memVo)session.getAttribute("memVo");
        for (PayRequest payRequest : payRequests) {
            payVo pay = new payVo();
            pay.setPayname(payRequest.getName());
            pay.setCount(payRequest.getCount());
            pay.setPrice(payRequest.getPrice());
            pay.setMemberId(memVo.getId());
            pr.save(pay);
        }

        return "redirect:/";
    }
   
    
 // RequestBody 클래스 정의
    static class PayRequest {
        private String name;
        private int count;
        private int price;

        
        public PayRequest() {
        }

        
        public PayRequest(String name, int count, int price) {
            this.name = name;
            this.count = count;
            this.price = price;
        }



		public String getName() {
			return name;
		}



		public void setName(String name) {
			this.name = name;
		}



		public int getCount() {
			return count;
		}



		public void setCount(int count) {
			this.count = count;
		}



		public int getPrice() {
			return price;
		}



		public void setPrice(int price) {
			this.price = price;
		}
        
        
    }
    
    
    @GetMapping("/myshop")
    public String myshop(Model model, HttpSession session) {
    	memVo memVo=(memVo)session.getAttribute("memVo");
        List<payVo> payList = pr.findByMemberId(memVo.getId());
        
        model.addAttribute("payList", payList);
        return "pay/myacc";
    }
    
    
    @PostMapping("/saveAddress")
    public String saveAddress(@RequestBody AddressDTO address, HttpSession session) {
        // 사용자 ID는 세션이나 토큰에서 가져올 수 있습니다.
        memVo memVo = (memVo) session.getAttribute("memVo");
        if (memVo == null) {
            return "redirect:/";
        }
        String userId = memVo.getId(); // 세션에서 사용자 ID를 가져옴

        try {
            memberService.updateAddress(userId, address, session);
            return "redirect:/payments/saveAddress";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }
    
    
    
    

}
