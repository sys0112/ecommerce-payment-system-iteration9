package com.simple.StES.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.StES.DTO.AddressDTO;
import com.simple.StES.Service.IamportService;
import com.simple.StES.Service.MemberService;
import com.simple.StES.Service.basketService;
import com.simple.StES.repository.basketRepository;
import com.simple.StES.repository.memRepository;
import com.simple.StES.repository.payRepository;
import com.simple.StES.vo.basketVo;
import com.simple.StES.vo.memVo;
import com.simple.StES.vo.payVo;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.simple.StES.DTO.PayRequest;

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
	
	@Autowired
    private basketService basketService;

    
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
    @ResponseBody
    public String savePayments(@RequestBody PayRequest payRequest, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        memVo memVo = (memVo) session.getAttribute("memVo");
        
        // 현재 시간을 가져옴
        LocalDateTime now = LocalDateTime.now();
        
        // 밀리초를 제거하고 초까지만 남김
        LocalDateTime truncatedNow = now.truncatedTo(ChronoUnit.SECONDS);

        for (PayRequest.Item item : payRequest.getItems()) {
        // 결제 정보 저장
        payVo pay = new payVo();
        pay.setPayname(item.getName()); // 예시로 첫 번째 아이템의 이름을 사용
        pay.setCount(item.getCount()); // 예시로 첫 번째 아이템의 개수를 사용
        pay.setPrice(item.getPrice()); // 예시로 첫 번째 아이템의 가격을 사용
        pay.setMemberId(memVo.getId()); // 사용자 아이디 저장
        pay.setAddress(payRequest.getAddress().getAddress());
        pay.setPostcode(payRequest.getAddress().getPostcode());
        pay.setDetailAddress(payRequest.getAddress().getDetailAddress());
        pay.setBuyerName(payRequest.getBuyerName());
        pay.setBuyerTel(payRequest.getBuyerTel());
        pay.setPaymentMethod(payRequest.getPaymentMethod());
        pay.setPayTime(truncatedNow);

        pr.save(pay);
        }
        
     // 장바구니 리스트 삭제
        basketService.clearBasketForUser(memVo.getId());
        
        return "결제 성공";
    }
    
    @GetMapping("/payment_success")
    public String paymentSuccess() {
        return "payment_success"; // 성공 페이지로 이동
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
    
    
    @GetMapping("/myshop")
    public String myshop(Model model, HttpSession session) {
    	memVo memVo=(memVo)session.getAttribute("memVo");
        List<payVo> payList = pr.findByMemberId(memVo.getId());
        
     // pay_time을 년, 월, 일, 시, 분, 초 단위로 그룹화, null 값에 대해 기본값 설정
        Map<LocalDateTime, List<payVo>> groupedByPayTime = payList.stream()
            .collect(Collectors.groupingBy(pay -> {
                LocalDateTime payTime = pay.getPayTime();
                if (payTime == null) {
                    return LocalDateTime.MIN; // 기본값 설정 (null인 경우)
                } else {
                    return LocalDateTime.of(
                        payTime.getYear(),
                        payTime.getMonth(),
                        payTime.getDayOfMonth(),
                        payTime.getHour(),
                        payTime.getMinute(),
                        payTime.getSecond()
                    );
                }
            }));

        // 모델에 데이터를 추가
        model.addAttribute("groupedPayList", groupedByPayTime.entrySet().stream()
            .map(entry -> new PayGroup(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList()));
        
        return "pay/myacc";
    }
    
    public static class PayGroup {
        private LocalDateTime payTime;
        private List<payVo> pays;

        public PayGroup(LocalDateTime payTime, List<payVo> pays) {
            this.payTime = payTime;
            this.pays = pays;
        }

        public LocalDateTime getPayTime() {
            return payTime;
        }

        public List<payVo> getPays() {
            return pays;
        }
    }
    
    

    
    @GetMapping("/detailsGroup")
    public String detailsGroup(@RequestParam("payTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime payTime, Model model) {
        // Fetch all payments that share the same payTime
        List<payVo> payList = pr.findByPayTime(payTime);
        
        
        model.addAttribute("payList", payList);
        model.addAttribute("payTime", payTime);
        return "pay/detailsGroup";
    }
    

}
