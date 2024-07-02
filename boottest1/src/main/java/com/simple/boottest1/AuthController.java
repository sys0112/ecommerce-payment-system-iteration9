package com.simple.boottest1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
    private SmsService smsService;

    // 메모리 기반의 간단한 스토어 (실제 구현에서는 Redis 같은 캐시 사용 권장)
    private Map<String, String> verificationCodes = new HashMap<>();

    @GetMapping("/sendCode")
    public String sendVerificationCode(@RequestParam("phoneNumber") String phoneNumber) {
        String code = generateVerificationCode();
        String message = "Your verification code is " + code;
        String response = smsService.sendSms(phoneNumber, message);

        // 메모리에 인증 코드 저장
        verificationCodes.put(phoneNumber, code);

        return response;
    }

    @GetMapping("/verifyCode")
    public boolean verifyCode(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("code") String code) {
        String storedCode = verificationCodes.get(phoneNumber);
        return storedCode != null && storedCode.equals(code);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 숫자 코드
        return String.valueOf(code);
    }

}
