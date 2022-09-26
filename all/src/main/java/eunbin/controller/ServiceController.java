package eunbin.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eunbin.DTO.MemberDTO;
import eunbin.DTO.ServiceDTO;
import eunbin.service.MemberService;
import eunbin.service.ServiceService;

// 고객센터
@Controller
@RequestMapping("/service/*")
public class ServiceController {

	@Autowired
	ServiceService s_service;
	
	@Autowired
	MemberService m_service;
	
	// 고객센터 - 문의 전체보기
	@GetMapping("/allService")
	public void getAllService(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - getAllService");
	}
	
	@PostMapping("/allService")
	public void postAllService(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - postAllService");
	}
	
	// 고객센터 - 1:1 문의
	@GetMapping("/oneByone")
	public void getOneByone(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - getOneByone");
	}
	
	@PostMapping("/oneByone")
	public void postOneByone(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - postOneByone");
	}

	// 고객센터 - 자주하는 질문
	// 자주하는 질문 - 회원 정보 관리
	@GetMapping("/question-member")
	public ModelAndView getQuestion_member(ModelAndView mv)
			throws Exception {
		System.out.println("ServiceController - getQuestion_member");
		
		// 데이터 불러오기 위한 선언
		List<ServiceDTO> s_dto_list = new ArrayList<ServiceDTO>();

		// 첫 화면 - 회원정보 관리
		// 관리자 회원번호로 게시물들 불러오기
		s_dto_list = s_service.board_bno_All("question_member");
		if (s_dto_list != null && s_dto_list.size() != 0) {
			mv.addObject("s_dto_list", s_dto_list);
		} else {
			System.out.println("ServiceController - getQuestion_member - 존재하는 게시물 없음");
		}
		mv.setViewName("/all/service/question-member");
		return mv;
	}
	
	@PostMapping("/question-member")
	public void postQuestion_member(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - postQuestion_member");
	}
	
	// 자주하는 질문 - 사이트 이용 가이드
	@GetMapping("/question-guide")
	public ModelAndView getQuestion_guide(ModelAndView mv)
			throws Exception {
		System.out.println("ServiceController - getQuestion_guide");
		
		// 데이터 불러오기 위한 선언
		List<ServiceDTO> s_dto_list = new ArrayList<ServiceDTO>();

		// 첫 화면 - 회원정보 관리
		// 관리자 회원번호로 게시물들 불러오기
		s_dto_list = s_service.board_bno_All("question_guide");
		if (s_dto_list != null && s_dto_list.size() != 0) {
			mv.addObject("s_dto_list", s_dto_list);
		} else {
			System.out.println("ServiceController - getQuestion_guide - 존재하는 게시물 없음");
		}
		mv.setViewName("/all/service/question-guide");
		return mv;
	}
	
	@PostMapping("/question-guide")
	public void postQuestion_guide(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - postQuestion_guide");
	}
	
	// 자주하는 질문 - 상세보기
	@GetMapping("/question-detail")
	public ModelAndView getQuestion_detail(
			@RequestBody String bno,
			ModelAndView mv)
			throws Exception {
		System.out.println("ServiceController - getQuestion_detail");
		
		// 데이터 불러오기 위한 선언
		ServiceDTO s_dto = new ServiceDTO();
		// 게시물 대표번호로 게시물 정보 가져오기
		s_dto = s_service.board_one(Integer.valueOf(bno));
		mv.addObject("s_dto", s_dto);
		mv.setViewName("/all/service/question-detail");

		return mv;
	}
	
	@PostMapping("/question-detail")
	public ModelAndView postQuestion_detail(
			@RequestParam String e_btn,
			@RequestParam int e_bno,
			ModelAndView mv)
			throws Exception {
		System.out.println("ServiceController - postQuestion_detail");
		
		// 데이터 불러오기 위한 선언
		ServiceDTO s_dto = new ServiceDTO();

		// 게시물 번호로 게시물 객체 가져오기
		s_dto = s_service.board_one(e_bno);
		mv.addObject("s_dto", s_dto);
		
		if (e_btn.equals("fix")) {
			// 게시물 수정 버튼 누를시
			// 수정 페이지로 이동
			mv.setViewName("/all/service/question-fix");
			return mv;
		} else if (e_btn.equals("delete")) {
			s_service.board_delete(e_bno);
		}
		// 게시판 메인 화면으로 이동
		mv.setViewName("/all/service/question-member");
		return mv;
	}
	
	// 자주하는 질문 - 게시물 수정
	@GetMapping("/question-fix")
	public void getQuestion_fix(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - getQuestion_fix");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			// 로그인 안했을 때 - 잘못된 접근
			// 뒤로가기
			PrintWriter out = response.getWriter();
			out.println("<script language ='javascript'>window.history.back();</script>");
			out.flush();
		} 
	}
	
	@PostMapping("/question-fix")
	public String postQuestion_fix(@ModelAttribute ServiceDTO s_dto,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - postQuestion_fix");

		// 게시물 수정
		s_service.board_fix(s_dto);

		return "/all/service/question-member";
	}
	
	// 자주하는 질문 - 게시물 작성
	@GetMapping("/question-write")
	public String getQuestion_write(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - getQuestion_write");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			// 로그인 안했을 때 - 잘못된 접근
			// 뒤로가기
			PrintWriter out = response.getWriter();
			out.println("<script language ='javascript'>window.history.back();</script>");
			out.flush();
		} else {
			// 로그인 되있을 때 - 정상 접근
			// 게시물 쓰기 뷰
			return "/all/service/question-write";
		}
		return null;
	}
	
	@PostMapping("/question-write")
	public String postQuestion_write(
			@RequestParam String e_choice_val,
			@ModelAttribute ServiceDTO s_dto,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - postQuestion_write");
		
		// 멤버 객체 생성
		MemberDTO m_dto = new MemberDTO();

		// 로그인한 회원 정보 불러오기
		HttpSession session = request.getSession();
		m_dto = (MemberDTO)session.getAttribute("user");
		// 관리자 여부, 작성 시간, 회원번호
		// 관리자 여부 저장
		String admin_type = s_service.board_admin_type(m_dto.getId());
		s_dto.setAdmin_type(admin_type);
		// 작성 시간 저장
		java.util.Date date = new java.util.Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = simpleDateFormat.format(date);
		java.sql.Date date_re = java.sql.Date.valueOf(formattedDate);
		s_dto.setCreate_time(date_re);
		// 회원번호 저장
		int member_no = m_dto.getMember_no();
		s_dto.setMember_no(member_no);
		// 글쓰기 유형 저장
		s_dto.setSv_type(e_choice_val);
		
		// 글 작성
		s_service.board_write(s_dto);

		// 게시판 메인 홈페이지로 돌아가기
		return "/all/service/question-member";
	}
	
}
