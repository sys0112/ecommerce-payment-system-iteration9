package com.simple.StES.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.simple.StES.Service.IamportService;
import com.simple.StES.repository.basketRepository;
import com.simple.StES.vo.basketVo;
import com.simple.StES.vo.memVo;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
    private IamportService iamportService;
	
	@Autowired 
	basketRepository ibr;

    
    @GetMapping("/form")
    public String checkout(Model model, HttpSession session) {
        memVo memVo = (memVo) session.getAttribute("memVo");
        List<basketVo> basketList = ibr.findByMemberId(memVo.getId());

        // 총합계 계산
        int totalSum = basketList.stream().mapToInt(b -> b.getCount() * b.getItem().getPrice()).sum();

        // 모델에 속성 추가
        model.addAttribute("totalSum", totalSum);
        return "paymentForm"; // checkout.html 템플릿
    }
    

//    @PostMapping("/verify")
//    public String verifyPayment(@RequestParam String impUid, Model model) {
//        Map<String, Object> paymentInfo = iamportService.getPaymentByImpUid(impUid);
//        model.addAttribute("paymentInfo", paymentInfo);
//        return "paymentResult";
//    }

}
