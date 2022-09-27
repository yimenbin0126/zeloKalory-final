package eunbin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/*")
public class e_MainController {

	@GetMapping("/main")
	public void getMain() throws Exception {
		System.out.println("MainController - getMain");
	}
	
	@PostMapping("/main")
	public void postMain() throws Exception {
		System.out.println("MainController - postMain");
	}
}
