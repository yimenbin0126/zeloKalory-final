package com.zerocalorie.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zerocalorie.community.DTO.RecDTO;
import com.zerocalorie.community.service.RecService;

@Controller
@RequestMapping("/rec")
public class RecController {
	
	@Autowired
	private RecService service;
	
	@ResponseBody
	@RequestMapping(value="/recommend", method=RequestMethod.POST)
	public ResponseEntity<String> recommend(@RequestBody RecDTO dto) {		
		int a = service.recommend(dto);
		
		if(a > 0) {
			return new ResponseEntity<String>("추천 성공", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("추천 실패", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
	@RequestMapping(value="/rec", method=RequestMethod.GET)
	public RecDTO recCount(@RequestParam int articleNO, @RequestParam(required = false) Integer member_no) {
		
		RecDTO reqDto = new RecDTO();
		reqDto.setArticleno(articleNO);
		reqDto.setMember_no(member_no);
		
		RecDTO rtnDto = service.getRecCount(reqDto);
		
		return rtnDto;
	}
	
	@RequestMapping(value="/top5", method=RequestMethod.GET)
	public String recommendTop5(Model model) {
		ModelAndView mav = new ModelAndView();
		List<RecDTO> list = service.getRecTop5();

		model.addAttribute("list", list);
		
		return "result";
	}
}
