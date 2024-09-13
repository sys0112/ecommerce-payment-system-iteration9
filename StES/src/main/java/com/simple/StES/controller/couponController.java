package com.simple.StES.controller;


import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/coupons")
public class couponController {

    // 쿠폰 정보를 저장할 Map (임시 데이터베이스 역할)
    private Map<String, Integer> coupons = new HashMap<>();

    // 쿠폰 생성 (쿠폰 코드와 할인율)
    @PostMapping("/create")
    public String createCoupon(@RequestParam String code, @RequestParam int discount) {
        if (coupons.containsKey(code)) {
            return "Coupon already exists!";
        }
        coupons.put(code, discount);
        return "Coupon created: " + code + " with discount: " + discount + "%";
    }

    // 쿠폰 조회 (쿠폰 코드로 할인율 확인)
    @GetMapping("/get/{code}")
    public String getCoupon(@PathVariable String code) {
        if (coupons.containsKey(code)) {
            return "Coupon " + code + " offers " + coupons.get(code) + "% discount.";
        }
        return "Coupon not found!";
    }

    // 쿠폰 삭제
    @DeleteMapping("/delete/{code}")
    public String deleteCoupon(@PathVariable String code) {
        if (coupons.containsKey(code)) {
            coupons.remove(code);
            return "Coupon " + code + " deleted.";
        }
        return "Coupon not found!";
    }
}

