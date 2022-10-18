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
	
	@RequestMapping("")
	public ModelAndView mypageController(HttpServletRequest request, HttpServletResponse response
			, @ModelAttribute MypageChartDTO mypageChartDTO
			, @RequestParam(value = "command", required = false) String command) {
		/* 
		// 지수씨 부분: 이전소스 안건드리고 그대로 가져온것
		MemberDAO dao = new MemberDAO();
		String command = request.getParameter("command");
		*/
		
		
		if(request.getParameter("command")!=null && "editMember".equals(command)) {
			/*
			// 지수씨 부분
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String nickname = request.getParameter("nickname");
			String gender = request.getParameter("gender");
			int height = Integer.parseInt(request.getParameter("height"));
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			
			
					
			MemberVO vo = new MemberVO();
			vo.setId(id);
			vo.setName(name);
			vo.setNickname(nickname);
			vo.setGender(gender);
			vo.setHeigth(height);
			vo.setTel(tel);
			vo.setEmail(email);
			
			dao.editMember(vo);
			System.out.println("editmember 실행");
			*/
			
			// 소연 부분
		}else if ((command != null && command.equals("weightAdd"))) {
			
			// jsp에서 넘어온 CURRENT_WEIGHT, TARGET_WEIGHT 등이 int 변환에 문제가 생길수 있어서..
			try {

				e_MemberDTO sessionUserDTO = new e_MemberDTO();	// 접속자 정보
				sessionUserDTO = (e_MemberDTO)request.getSession().getAttribute("user");
				mypageChartDTO.setMember_no(sessionUserDTO.getMember_no());
				
				mypageChartService.weightAdd(mypageChartDTO);
				
			} catch (Exception e) {e.printStackTrace();}
			
		}else if ((command != null && command.equals("weightMod"))) {
			 
			// jsp에서 넘어온 CURRENT_WEIGHT, TARGET_WEIGHT 등이 int 변환에 문제가 생길수 있어서..
			try {
				e_MemberDTO sessionUserDTO = new e_MemberDTO();	// 접속자 정보
				sessionUserDTO = (e_MemberDTO)request.getSession().getAttribute("user");
				mypageChartDTO.setMember_no(sessionUserDTO.getMember_no());

				mypageChartService.weightMod(mypageChartDTO);
			} catch (Exception e) {e.printStackTrace();}
		}
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("mypage/mypage");
		return mav;
	}
	
	@RequestMapping("/chartJSON")
	@ResponseBody
	public List MypageChartJSON(HttpServletRequest request) {
		
		System.out.println("제이슨 들어왓다");
		e_MemberDTO sessionUserDTO  = (e_MemberDTO)request.getSession().getAttribute("user"); // 접속자 정보

		System.out.println("user: "+sessionUserDTO.getMember_no());
		List<MypageChartDTO> MypageChartlist = mypageChartService.weightread(sessionUserDTO.getMember_no());
		System.out.println("MypageChartlist.size() : "+ MypageChartlist.size());
		
		List list = new ArrayList();
		for(int i = 0; i<MypageChartlist.size(); i++) {
			MypageChartDTO vo = new MypageChartDTO();
			vo = MypageChartlist.get(i);
			list.add(vo);
		}
		
		return list;
	}
}
