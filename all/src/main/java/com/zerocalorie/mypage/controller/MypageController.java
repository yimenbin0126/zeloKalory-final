package com.zerocalorie.mypage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zerocalorie.member.dto.e_MemberDTO;
import com.zerocalorie.mypage.dao.MypageChartDAO;
import com.zerocalorie.mypage.dto.MypageChartDTO;
import com.zerocalorie.mypage.service.MypageChartService;


@RequestMapping("/mypage")
@Controller
public class MypageController {
	
	@Autowired
	MypageChartDAO mypageChartDAO;
	
	@Autowired
	MypageChartService mypageChartService;
	
	// mypage 
	@RequestMapping("/mypage")
	public ModelAndView mypageController(HttpServletRequest request, HttpServletResponse response
			, @ModelAttribute MypageChartDTO mypageChartDTO) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("mypage/mypage");
		return mav;
	}
	
	// mypage 몸무게 추가
	@RequestMapping("/weightAdd")
	public String weightAdd(HttpServletRequest request
			, @ModelAttribute MypageChartDTO mypageChartDTO) {
		// jsp에서 넘어온 CURRENT_WEIGHT, TARGET_WEIGHT 등이 int 변환에 문제 try 해..
		
		System.out.println("mypage > /weightAdd ");
		mypageChartService.weightAdd(request, mypageChartDTO);

		return "mypage/mypage";
	}			
	
	// mypage 몸무게 수정
	@RequestMapping("/weightMod")
	public String weightMod(HttpServletRequest request
			, @ModelAttribute MypageChartDTO mypageChartDTO) {
		
		System.out.println("mypage > /weightMod ");
		mypageChartService.weightMod(request, mypageChartDTO);

		return "mypage/mypage";
	}
	
	// mypage chart JSON  부분
	@RequestMapping("/chartJSON")
	@ResponseBody
	public List MypageChartJSON(@RequestParam(value = "member_no") int member_no) {
		
		System.out.println("MypageController > MypageChartJSON");

		List<MypageChartDTO> MypageChartlist = mypageChartService.weightread(member_no);
		
		List list = new ArrayList();
		for(int i = 0; i<MypageChartlist.size(); i++) {
			MypageChartDTO vo = new MypageChartDTO();
			vo = MypageChartlist.get(i);
			list.add(vo);
		}
		return list;
	}
}
