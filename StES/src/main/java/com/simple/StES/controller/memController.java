package com.simple.StES.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.simple.StES.vo.memVo;

import jakarta.servlet.http.HttpSession;

import com.simple.StES.Service.PasswordResetService;
import com.simple.StES.Service.SmsService;
import com.simple.StES.repository.memRepository;

@Controller
@RequestMapping("/mem")
public class memController {
	
	@Autowired
	memRepository mr;
	
	@Autowired
    private PasswordResetService passwordResetService;
	
	
	private Map<String, String> verificationCodes = new HashMap<>();

	
	@GetMapping("/admin")
	public String list(Model model) {
		Iterable<memVo> memList=mr.findAllByOrderByIdAsc();
		model.addAttribute("memList",memList);
		return "mem/list";
	}
	
	@GetMapping(value="/login")
	public String login() {
		return "/mem/login";
	}
	
	@PostMapping("/login")
//	톰캣서버에 필요한 객체가 있다면 매개변수로 작성하면 사용가능
	public String login(@RequestParam(value = "id") String id ,@RequestParam(value = "pw") String pw, HttpSession session) { // 오버로딩 
		
			memVo memVo=mr.findByIdAndPw(id, pw);
	
			if(memVo!=null) {
			session.setAttribute("memVo", memVo);
	
			return "redirect:/"; // response.sendRedirect("/")			
			}else {
				return "redirect:/mem/login";
			}
		
	}
	
	
	@GetMapping(value="/find-username")
    public String findUsernameByEmail() {
		return "/mem/idfound";
    }
	
	@PostMapping("/find-username")
    public ResponseEntity<?> findUsernameByEmail(@RequestParam(value = "phone") String phone) {
        memVo user = mr.findByPhone(phone);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("등록된 번호가 없습니다.");
        }

        return ResponseEntity.ok(user.getId());
    }
	
	@GetMapping("/request-password-reset")
	public String requestPasswordReset() {
	    return "/mem/passwordreset";
	}
	
	
	@PostMapping("/request-password-reset")
    public String requestPasswordReset(@RequestParam(value = "email") String email) {
            passwordResetService.createPasswordResetToken(email); 
            return "/mem/success"; // Return the view name for success
        
    }
    
    
	
	@GetMapping(value="/reset-password")
    public String resetPasswordForm(@RequestParam(value = "token") String token, Model model) {
        // 토큰을 모델에 추가하여 뷰에서 사용 가능하게 함
        model.addAttribute("token", token);
        return "/mem/passwordmatch";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam(value = "token") String token,
                                @RequestParam(value = "newPassword") String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        // 비밀번호가 성공적으로 변경되었음을 알리는 페이지로 리다이렉트
        return "redirect:/";
    }
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	
}


