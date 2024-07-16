package com.simple.StES;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
	@GetMapping("/main")
	public String main() {
		
		return "hello world";
	}

}
