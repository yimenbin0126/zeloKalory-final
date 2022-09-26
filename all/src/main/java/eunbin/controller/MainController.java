package eunbin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/*")
public class MainController {

	@GetMapping("/home")
	public void getHome() throws Exception {
		System.out.println("MainController - getHome");
	}
	
	@PostMapping("/home")
	public void postHome() throws Exception {
		System.out.println("MainController - postHome");
	}
}
