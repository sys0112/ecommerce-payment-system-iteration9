package com.simple.boottest1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testcontroller {
	
	@GetMapping("/main")
	public String main() {
		
		return "hello world";
	}
	

}
