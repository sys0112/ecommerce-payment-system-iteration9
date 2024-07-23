package com.simple.StES.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.StES.Service.IamportService;
import com.simple.StES.repository.basketRepository;
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
    public String savePayments(@RequestBody List<PayRequest> payRequests) {
        for (PayRequest payRequest : payRequests) {
            payVo pay = new payVo();
            pay.setPayname(payRequest.getName());
            pay.setCount(payRequest.getCount());
            pay.setPrice(payRequest.getPrice());
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
    public String myshop(Model model) {
    	model.addAttribute("payList",pr.findAll());
        return "pay/myacc";
    }
    
    
   
//    @PostMapping("/verify")
//    public String verifyPayment(@RequestParam String impUid, Model model) {
//        Map<String, Object> paymentInfo = iamportService.getPaymentByImpUid(impUid);
//        model.addAttribute("paymentInfo", paymentInfo);
//        return "paymentResult";
//    }

}
