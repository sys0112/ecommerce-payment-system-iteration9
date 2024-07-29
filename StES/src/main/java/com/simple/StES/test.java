package com.simple.StES;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class test {
	@GetMapping("/main")
	public String main() {
		
		return "/pay/testp";
	}

}
