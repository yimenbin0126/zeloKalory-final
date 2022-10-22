package com.zerocalorie.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/*")
public class e_MainController {

	@GetMapping("/main")
	public String getMain() throws Exception {
		System.out.println("MainController - getMain");
		return "main/main";
	}
	
	@PostMapping("/main")
	public String postMain() throws Exception {
		System.out.println("MainController - postMain");
		return "main/main";
	}
}
