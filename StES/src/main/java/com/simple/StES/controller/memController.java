package com.simple.StES.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	
	
	@GetMapping("/signup")
	public ModelAndView signup(ModelAndView model) { // 맨위 public String login이랑 비교하면됨.
		model.setViewName("/mem/signup");
		return model;
	}
	
//	@PostMapping("/signup")
//	public String singup(memVo memVo, HttpSession session) {
//		boolean insert=false;
//		try {
//			Optional<memVo> memOption=mr.findById(memVo.getId()); // 기본으로 제공되는 함수
//			if(memOption.isEmpty()) { // 있는지 검사해서 없을때만 저장
//				memVo insertMem=mr.save(memVo);
//				System.out.println(insertMem);
//				if(insertMem!=null) {
//					insert=true;
//				}else {
//					session.setAttribute("msg", "존재하는 아이디입니다.");
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			session.setAttribute("msg", "Email 또는 Phone이 이미 존재합니다.");
//		}
//		if(insert) {
//			return "redirect:/";			
//		}else {
//			return "redirect:/mem/signup";
//		}
//	}
	
	
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
//	
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
	
	
//	@GetMapping("/ajax/findId/{id}")
//	public @ResponseBody Optional<memVo> findId(@PathVariable("id") String id) {
//		return mr.findById(id);
//	}
//	@GetMapping("/ajax/findEmail/{email}")
//	public @ResponseBody Optional<memVo> findEmail(@PathVariable("email") String email){
//		return mr.findByEmail(email);
//	}
//	@GetMapping("/ajax/findPhone/{phone}")
//	public @ResponseBody Optional<memVo> findByPhone(@PathVariable("phone") String phone){
//		return mr.findByPhone(phone);
//	}
	
}


