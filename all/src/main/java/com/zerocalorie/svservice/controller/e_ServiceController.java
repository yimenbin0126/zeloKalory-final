package com.zerocalorie.svservice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.zerocalorie.member.dto.e_MemberDTO;
import com.zerocalorie.member.service.e_MemberService;
import com.zerocalorie.svservice.dto.e_ServiceDTO;
import com.zerocalorie.svservice.dto.e_SvCommentDTO;
import com.zerocalorie.svservice.dto.e_SvFileDTO;
import com.zerocalorie.svservice.dto.e_SvLikecheckDTO;
import com.zerocalorie.svservice.dto.e_SvPagingViewDTO;
import com.zerocalorie.svservice.dto.e_SvSearchDTO;
import com.zerocalorie.svservice.dto.e_SvViewcheckDTO;
import com.zerocalorie.svservice.service.e_ServiceService;

// 고객센터
@Controller
@RequestMapping("/service/*")
public class e_ServiceController {

	@Autowired
	e_ServiceService s_service;
	
	@Autowired
	e_MemberService m_service;
	
	// 고객센터 - 문의 전체보기
	@GetMapping("/allService")
	public void getAllService()
			throws Exception {
		System.out.println("ServiceController - getAllService");
	}
	
	@PostMapping("/allService")
	public void postAllService(HttpServletRequest request)
			throws Exception {
		System.out.println("ServiceController - postAllService");
	}
	
	// 고객센터 - 자주하는 질문
	// 자주하는 질문 - 회원 정보 관리
	@GetMapping("/question-member")
	public ModelAndView getQuestion_member(
			ModelAndView mv,
			@RequestParam(value="page_NowBno", defaultValue="1") int page_NowBno)
			throws Exception {
		System.out.println("ServiceController - getQuestion_member");
		
		// 데이터 불러오기 위한 선언
		List<e_ServiceDTO> s_dto_list = new ArrayList<e_ServiceDTO>();

		// 첫 화면 - 회원정보 관리
		// 게시물 갯수 불러오기
		e_ServiceDTO s_dto = new e_ServiceDTO();
		s_dto.setSv_type("question_member");
		int board_AllCount = s_service.board_count_All(s_dto);
		// 페이징
		e_SvPagingViewDTO s_page = new e_SvPagingViewDTO(board_AllCount, page_NowBno);
		s_page.setSv_type("question_member");
		// 페이지 내 게시물 목록 불러오기
		s_dto_list = s_service.board_paging(s_page);
		if (s_dto_list != null && s_dto_list.size() != 0) {
			System.out.println("ServiceController - getQuestion_member - 존재하는 게시물 있음");
		} else {
			System.out.println("ServiceController - getQuestion_member - 존재하는 게시물 없음");
		}
		mv.addObject("s_page", s_page);
		mv.addObject("s_dto_list", s_dto_list);
		mv.setViewName("/service/question-member");
		return mv;
	}
	
	@PostMapping("/question-member")
	public void postQuestion_member(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - postQuestion_member");
	}
	
	// 자주하는 질문 - 사이트 이용 가이드
	@GetMapping("/question-guide")
	public ModelAndView getQuestion_guide(
			ModelAndView mv,
			@RequestParam(value="page_NowBno", defaultValue="1") int page_NowBno)
			throws Exception {
		System.out.println("ServiceController - getQuestion_guide");
		
		// 데이터 불러오기 위한 선언
		List<e_ServiceDTO> s_dto_list = new ArrayList<e_ServiceDTO>();

		// 첫 화면 - 회원정보 관리
		// 게시물 갯수 불러오기
		e_ServiceDTO s_dto = new e_ServiceDTO();
		s_dto.setSv_type("question_guide");
		int board_AllCount = s_service.board_count_All(s_dto);
		// 페이징
		e_SvPagingViewDTO s_page = new e_SvPagingViewDTO(board_AllCount, page_NowBno);
		s_page.setSv_type("question_guide");
		// 페이지 내 게시물 목록 불러오기
		s_dto_list = s_service.board_paging(s_page);
		if (s_dto_list != null && s_dto_list.size() != 0) {
			System.out.println("ServiceController - getQuestion_member - 존재하는 게시물 있음");
		} else {
			System.out.println("ServiceController - getQuestion_member - 존재하는 게시물 없음");
		}
		mv.addObject("s_page", s_page);
		mv.addObject("s_dto_list", s_dto_list);
		mv.setViewName("/service/question-guide");
		return mv;
	}
	
	@PostMapping("/question-guide")
	public void postQuestion_guide(HttpServletRequest request)
			throws Exception {
		System.out.println("ServiceController - postQuestion_guide");
	}
	
	// 자주하는 질문 - 상세보기 - 첨부파일 다운로드
	@GetMapping("/file/download")
	public void getFileDownload(
			HttpServletRequest request, HttpServletResponse response
			)
			throws Exception {
		System.out.println("ServiceController - getFileDownload - 첨부파일 다운로드");
		
		// 파일 객체 선언
		int file_order = Integer.valueOf((String)request.getParameter("file_order"));
		e_SvFileDTO s_filedto = new e_SvFileDTO();
		s_filedto.setFile_order(file_order);
		s_filedto = s_service.board_load_file_one(s_filedto);
		// 객체에서 정보 가져오기 (이름, 경로)
		String fileName = s_filedto.getFilename();
		String filePath = "C:\\zerokalory_file";
		// 파일 객체 선언 (파일 경로, 파일 이름)
        File file = new File(filePath, fileName);
        int fileLength = (int)file.length();
        
        if (fileLength > 0) {
        	response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Length", "" + fileLength);
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");
            
            // FileInputStream : 파일을 바이트 스트림으로 읽어줌
			try (FileInputStream fis = new FileInputStream(file);
					OutputStream out = response.getOutputStream();) {
				int readCount = 0;
				byte[] buffer = new byte[1024];
				// fis.read(buffer) : 파일 바이트 타입으로 읽기
				// -1 : 파일 다 읽었을 때
				// write : 읽어들인 파일의 바이트를 출력
				while ((readCount = fis.read(buffer)) != -1) {
					out.write(buffer, 0, readCount);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}
	
	// 자주하는 질문 - 상세보기
	@GetMapping("/question-detail")
	public ModelAndView getQuestion_detail(
			HttpServletRequest request,
			@RequestParam int bno,
			ModelAndView mv)
			throws Exception {
		System.out.println("ServiceController - getQuestion_detail");
		
		// 데이터 불러오기 위한 선언
		e_ServiceDTO s_dto = new e_ServiceDTO();
		List<e_SvFileDTO> filelist = new ArrayList<e_SvFileDTO>(); 
		// 게시물 대표번호로 게시물 정보 가져오기
		s_dto = s_service.board_one(bno);
		// 파일도 같이 가져오기
		filelist = s_service.board_load_file(s_dto);
		// 로그인한 경우 (작성자와 같은 아이디 아닐시 증가)
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			// 멤버 테이블 불러오기
			e_MemberDTO m_dto = new e_MemberDTO();
			m_dto = (e_MemberDTO)session.getAttribute("user");
			// 게시물 조회수 증가
			// 조회수 테이블 불러오기
			e_SvViewcheckDTO s_viewCheck = new e_SvViewcheckDTO();
			s_viewCheck.setBno(bno);
			s_viewCheck.setMember_no(m_dto.getMember_no());
			// 게시판을 쓴 회원 번호 != 로그인한 회원번호 일시 조회수 증가
			if (s_dto.getMember_no() != m_dto.getMember_no()) {
				// 이미 조회수가 증가된 경우가 아닐 때 허용
				if(s_service.board_viewCheck(s_viewCheck) == true) {
					// 조회수 증가
					s_dto.setView_no(s_dto.getView_no()+1);
					// 증가한 값 업데이트
					s_service.board_viewUp(s_dto);
				}
				
				// 좋아요 테이블 불러오기
				e_SvLikecheckDTO s_likeCheck = new e_SvLikecheckDTO();
				s_likeCheck.setBno(bno);
				s_likeCheck.setMember_no(m_dto.getMember_no());
				
				// 좋아요 테이블 여부
				if (s_service.board_like_load(s_likeCheck) != null) {
					// 좋아요 여부 찾기
					s_likeCheck = s_service.board_like_load(s_likeCheck);
					if(s_likeCheck.getLike_check()==1) {
						// 좋아요를 눌렀다
						mv.addObject("heart_click", "Y");
					} else {
						// 좋아요를 누르지 않았다
						mv.addObject("heart_click", "N");
					}
				} else {
					// 좋아요를 누르지 않았다
					mv.addObject("heart_click", "N");
				}
			}
			
		} else if(session.getAttribute("user")==null) {
			// 조회수 테이블 불러오기
			e_SvViewcheckDTO s_viewCheck = new e_SvViewcheckDTO();
			s_viewCheck.setBno(bno);
			// ip로 접속했을 경우 (ip가 중복되지 않을 시 증가)
			if (s_service.board_Ipcheck(s_viewCheck) == true) {
				// 조회수 증가
				s_dto.setView_no(s_dto.getView_no()+1);
				// 증가한 값 업데이트
				s_service.board_viewUp(s_dto);
			}
		}
		
		// 게시판 불러오기 (값 변화 이후 불러오기)
		mv.addObject("filelist", filelist);
		mv.addObject("s_dto", s_dto);
		mv.setViewName("/service/question-detail");

		return mv;
	}
	
	// 자주하는 질문 - 상세보기 - 좋아요 체크
	@ResponseBody
	@PostMapping("/question-detail")
	public e_ServiceDTO postQuestion_detail(
			HttpServletRequest request,
			@RequestParam(value = "e_bno", required = false) int e_bno,
			@RequestParam(value = "e_heart_check", required = false) String e_heart_check)
			throws Exception {
		System.out.println("ServiceController - postQuestion_detail - 좋아요 체크");
		
		// 데이터 불러오기 위한 선언
		e_ServiceDTO s_dto = new e_ServiceDTO();
		// 게시물 번호로 게시물 객체 가져오기
		s_dto = s_service.board_one(e_bno);
		// 멤버 객체 테이블 불러오기
		HttpSession session = request.getSession();
		e_MemberDTO m_dto = new e_MemberDTO();
		m_dto = (e_MemberDTO)session.getAttribute("user");
		// 좋아요 객체 테이블 불러오기
		e_SvLikecheckDTO s_likeDTO = new e_SvLikecheckDTO();
		s_likeDTO.setBno(e_bno);
		s_likeDTO.setMember_no(m_dto.getMember_no());
		// 좋아요 증가 / 감소
		if (e_heart_check.equals("Y")) {
			s_service.board_like_up(s_likeDTO);
		} else if (e_heart_check.equals("N")) {
			s_service.board_like_down(s_likeDTO);
		}
		return s_dto;
	}
	
	// 자주하는 질문 - 상세보기 - 수정/삭제/뒤로가기 이동
	@PostMapping("/question-detail-button")
	public ModelAndView postQuestion_detail_button(
			@RequestParam String e_btn,
			@RequestParam int e_bno,
			ModelAndView mv)
			throws Exception {
		System.out.println("ServiceController - postQuestion_detail_button");
		
		// 데이터 불러오기 위한 선언
		e_ServiceDTO s_dto = new e_ServiceDTO();
		// 게시물 번호로 게시물 객체 가져오기
		s_dto = s_service.board_one(e_bno);
		// 게시판 첨부파일 객체
		List<e_SvFileDTO> filelist = new ArrayList<e_SvFileDTO>();
		// 파일도 같이 가져오기
		filelist = s_service.board_load_file(s_dto);
		
		if (e_btn.equals("fix")) {
			// 게시물 수정 버튼 누를시
			// 수정 페이지로 이동
			mv.addObject("filelist", filelist);
			mv.addObject("s_dto", s_dto);
			mv.setViewName("/service/question-fix");
			return mv;
		} else if (e_btn.equals("delete")) {
			s_service.board_delete(s_dto);
		}
		// 게시판 메인 화면으로 이동
		mv.setViewName("redirect:/service/question-member");
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
			// 응답 - 한글 처리
			response.setContentType("text/html;charset=UTF-8");
			// 로그인 안했을 때 - 잘못된 접근
			// 뒤로가기
			PrintWriter out = response.getWriter();
			out.println("<script language ='javascript'>window.history.back();</script>");
			out.flush();
		}
	}
	
	@ResponseBody
	@PostMapping("/question-fix")
	public void postQuestion_fix(
			HttpServletRequest request)
			throws Exception {
		System.out.println("ServiceController - postQuestion_fix");
		request.setCharacterEncoding("utf-8");
		// 객체 생성
		e_MemberDTO m_dto = new e_MemberDTO();
		e_ServiceDTO s_dto = new e_ServiceDTO();
		
		// 첨부 파일 불러오기 - (1)
		// 파일 경로
		String savePath = "C:\\zerokalory_file";
		// 파일 크기 15MB
		int sizeLimit = 1024 * 1024 * 15;
		// 파라미터를 전달해줌 (같은 이름의 파일명 방지)
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
				new DefaultFileRenamePolicy());
		
		// 글 수정
		// 글쓰기 제목, 내용 - 줄바꿈 저장, 글 번호
		s_dto.setTitle(multi.getParameter("title"));
		s_dto.setDescription(multi.getParameter("description").replace("\r\n","<br>"));
		int bno = Integer.valueOf((String)multi.getParameter("bno"));
		s_dto.setBno(bno);
		
		// 게시물 수정
		s_service.board_fix(s_dto);
		// 첨부파일 수정
		// 첨부파일 기존꺼 삭제
		if (!multi.getParameterValues("file_del_num")[0].equals("")) {
			String[] values = multi.getParameterValues("file_del_num");
			int val = 0;
			//차례대로 삭제
			for(int i=0; i<values.length; i++) {
				val = Integer.valueOf(values[i]);
				e_SvFileDTO s_filedto = new e_SvFileDTO();
				s_filedto.setBno(bno);
				s_filedto.setFile_order(val);
				s_service.board_delete_file_one(s_filedto);
			}
		} 
		
		// 첨부파일 새 파일 추가
		// 첨부 파일 불러오기 - (2)
		// getFileNames() : 파일 이름들 받아오기
		Enumeration enumeration = multi.getFileNames();
		String fileName = "";
		String f_path = "";
		int i = 0;
		// 데이터가 있을때, 불러오기
		while(enumeration.hasMoreElements()){
			i++;
			// 파일 객체 불러오기
			e_SvFileDTO s_filedto = new e_SvFileDTO();
			s_filedto.setBno(bno);
			fileName = multi.getFilesystemName((String) enumeration.nextElement());
			s_filedto.setFilename(fileName);
			// 파일의 전체 경로
			f_path = savePath + "/" + fileName;
			s_filedto.setF_path(f_path);
			s_filedto.setFile_order(i);
			// 첨부파일 생성
			s_service.board_write_file(s_filedto);
		}
		//return "/service/question-member";
	}
	
	// 자주하는 질문 - 게시물 작성
	@GetMapping("/question-write")
	public String getQuestion_write(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - getQuestion_write");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			// 응답 - 한글 처리
			response.setContentType("text/html;charset=UTF-8");
			// 로그인 안했을 때 - 잘못된 접근
			// 뒤로가기
			PrintWriter out = response.getWriter();
			out.println("<script language ='javascript'>window.history.back();</script>");
			out.flush();
		} else {
			// 로그인 되있을 때 - 정상 접근
			// 게시물 쓰기 뷰
			return "/service/question-write";
		}
		return null;
	}
	
	@ResponseBody
	@PostMapping("/question-write")
	public String postQuestion_write(
			HttpServletRequest request)
			throws Exception {
		System.out.println("ServiceController - postQuestion_write");
		request.setCharacterEncoding("utf-8");
		
		// 객체 생성
		e_MemberDTO m_dto = new e_MemberDTO();
		e_ServiceDTO s_dto = new e_ServiceDTO();
		
		// 첨부 파일 불러오기 - (1)
		// 파일 경로
		String savePath = "C:\\zerokalory_file";
		// 파일 크기 15MB
		int sizeLimit = 1024 * 1024 * 15;
		// 파라미터를 전달해줌 (같은 이름의 파일명 방지)
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
				new DefaultFileRenamePolicy());
		
		// 글 작성
		// 로그인한 회원 정보 불러오기
		HttpSession session = request.getSession();
		m_dto = (e_MemberDTO)session.getAttribute("user");
		// 닉네임 저장
		s_dto.setNickname(m_dto.getNickname());
		// 작성 시간, 회원번호
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
		s_dto.setSv_type(multi.getParameter("sv_type"));
		
		// 제목, 내용 검사
		if (multi.getParameter("title").trim().length()==0) {
			return "title_null";
		} else if (multi.getParameter("title").length()>50) {
			return "title_full";
		} else if (multi.getParameter("description").trim().length()==0) {
			return "description_null";
		} else if (multi.getParameter("description").length()>1000) {
			return "description_full";
		} else {
			// 글쓰기 제목, 내용 - 줄바꿈 저장
			s_dto.setTitle(multi.getParameter("title"));
			s_dto.setDescription(multi.getParameter("description").replace("\r\n","<br>"));
			
			// 글 시퀀스 불러오기
			int bno = s_service.board_write_bno();
			s_dto.setBno(bno);
			// 글 작성
			s_service.board_write(s_dto);
			// 첨부 파일 불러오기 - (2)
			// getFileNames() : 파일 이름들 받아오기
			Enumeration enumeration = multi.getFileNames();
			String fileName = "";
			String f_path = "";
			int i = 0;
			// 데이터가 있을때, 불러오기
			while(enumeration.hasMoreElements()){
				i++;
				// 파일 객체 불러오기
				e_SvFileDTO s_filedto = new e_SvFileDTO();
				s_filedto.setBno(bno);
				s_filedto.setFile_date(date_re);
				fileName = multi.getFilesystemName((String) enumeration.nextElement());
				s_filedto.setFilename(fileName);
				// 파일의 전체 경로
				f_path = savePath + "/" + fileName;
				s_filedto.setF_path(f_path);
				s_filedto.setFile_order(i);
				// 첨부파일 생성
				s_service.board_write_file(s_filedto);
			}
			// 게시판 메인 홈페이지로 돌아가기
			//return "redirect:/service/question-member";
			return "success";
		}
	}
	
	// 검색 - question-member
	@GetMapping("/question-member-search")
	public ModelAndView getQuestion_member_search(
			ModelAndView mv,
			@RequestParam(value="standard", defaultValue="new") String standard,
			@RequestParam(value="page_NowBno", defaultValue="1") int page_NowBno,
			@RequestParam(value="search_time") String search_time,
			@RequestParam(value="search_type") String search_type,
			@RequestParam(value="search_content") String search_content
			)throws Exception {
		System.out.println("ServiceController - getQuestion_member_search");
		
		// 디코딩
		//search_content = java.net.URLDecoder.decode(search_content);
		
		// 검색 객체
		e_SvSearchDTO s_searchdto = new e_SvSearchDTO();
		s_searchdto.setSearch_time(search_time);
		s_searchdto.setSearch_type(search_type);
		s_searchdto.setSearch_content(search_content);
		System.out.println(search_content);
		s_searchdto.setSv_type("question_member");
		s_searchdto.setStandard(standard);
		// 페이지 전체 갯수
		int board_AllCount = s_service.board_search_count_All(s_searchdto);
		// 페이징
		e_SvPagingViewDTO s_pageviewdto = new e_SvPagingViewDTO(board_AllCount, page_NowBno);
		s_searchdto.setBoard_NowStartBno(s_pageviewdto.getBoard_NowStartBno());
		s_searchdto.setBoard_NowEndBno(s_pageviewdto.getBoard_NowEndBno());
		
		// 데이터 전달
		// 데이터 불러오기 위한 선언
		List<e_ServiceDTO> s_dto_list = new ArrayList<e_ServiceDTO>();
		// 페이지 내 게시물 목록 불러오기
		s_dto_list = s_service.board_search_All(s_searchdto);
		
		if (s_dto_list != null && s_dto_list.size() != 0) {
			System.out.println("ServiceController - getQuestion_member_search - 존재하는 게시물 있음");
		} else {
			System.out.println("ServiceController - getQuestion_member_search - 존재하는 게시물 없음");
		}
		mv.addObject("s_searchdto", s_searchdto);
		mv.addObject("s_page", s_pageviewdto);
		mv.addObject("s_dto_list", s_dto_list);
		mv.setViewName("/service/question-member-search");
		return mv;
	}
	
	// 검색 - question-guide
	@GetMapping("/question-guide-search")
	public ModelAndView getQuestion_guide_search(
			ModelAndView mv,
			@RequestParam(value="standard", defaultValue="new") String standard,
			@RequestParam(value="page_NowBno", defaultValue="1") int page_NowBno,
			@RequestParam(value="search_time") String search_time,
			@RequestParam(value="search_type") String search_type,
			@RequestParam(value="search_content") String search_content
			)throws Exception {
		System.out.println("ServiceController - getQuestion_guide_search");
		
		// 검색 객체
		e_SvSearchDTO s_searchdto = new e_SvSearchDTO();
		s_searchdto.setSearch_time(search_time);
		s_searchdto.setSearch_type(search_type);
		s_searchdto.setSearch_content(search_content);
		s_searchdto.setSv_type("question_guide");
		s_searchdto.setStandard(standard);
		// 페이지 전체 갯수
		int board_AllCount = s_service.board_search_count_All(s_searchdto);
		// 페이징
		e_SvPagingViewDTO s_pageviewdto = new e_SvPagingViewDTO(board_AllCount, page_NowBno);
		s_searchdto.setBoard_NowStartBno(s_pageviewdto.getBoard_NowStartBno());
		s_searchdto.setBoard_NowEndBno(s_pageviewdto.getBoard_NowEndBno());
		
		// 데이터 전달
		// 데이터 불러오기 위한 선언
		List<e_ServiceDTO> s_dto_list = new ArrayList<e_ServiceDTO>();
		// 페이지 내 게시물 목록 불러오기
		s_dto_list = s_service.board_search_All(s_searchdto);
		
		if (s_dto_list != null && s_dto_list.size() != 0) {
			System.out.println("ServiceController - getQuestion_guide_search - 존재하는 게시물 있음");
		} else {
			System.out.println("ServiceController - getQuestion_guide_search - 존재하는 게시물 없음");
		}
		mv.addObject("s_searchdto", s_searchdto);
		mv.addObject("s_page", s_pageviewdto);
		mv.addObject("s_dto_list", s_dto_list);
		mv.setViewName("/service/question-guide-search");
		return mv;
	}
	
	
	
	// 고객센터 - 공개 건의함
	// 메인화면
	@GetMapping("/question-public")
	public ModelAndView getQuestion_public(
			ModelAndView mv,
			@RequestParam(value="standard", defaultValue="new") String standard,
			@RequestParam(value="page_NowBno", defaultValue="1") int page_NowBno)
			throws Exception {
		System.out.println("ServiceController - getQuestion_public");
		
		// 데이터 불러오기 위한 선언
		List<e_ServiceDTO> s_dto_list = new ArrayList<e_ServiceDTO>();

		// 첫 화면 - 회원정보 관리
		// 게시물 갯수 불러오기
		e_ServiceDTO s_dto = new e_ServiceDTO();
		s_dto.setSv_type("question_public");
		int board_AllCount = s_service.board_count_All(s_dto);
		// 페이징
		e_SvPagingViewDTO s_page = new e_SvPagingViewDTO(board_AllCount, page_NowBno);
		s_page.setSv_type("question_public");
		s_page.setStandard(standard);
		// 페이지 내 게시물 목록 불러오기
		s_dto_list = s_service.board_paging_origin_reply(s_page);
		// 게시물 원글, 답글 가져오기
		if (s_dto_list != null && s_dto_list.size() != 0) {
			System.out.println("ServiceController - getQuestion_public - 존재하는 게시물 있음");
		} else {
			System.out.println("ServiceController - getQuestion_public - 존재하는 게시물 없음");
		}
		mv.addObject("s_page", s_page);
		mv.addObject("s_dto_list", s_dto_list);
		mv.setViewName("/service/question-public");
		return mv;
	}
	
	// 내가 쓴 글 보기
	@GetMapping("/question-public-myboard")
	public ModelAndView getQuestion_public_myboard(
			HttpServletRequest request,
			ModelAndView mv,
			@RequestParam(value="page_NowBno", defaultValue="1") int page_NowBno)
			throws Exception {
		System.out.println("ServiceController - getQuestion_public_myboard");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if(session.getAttribute("user")==null) {
			// 로그인 안했을 때 - 비정상 접근
			mv.setViewName("/main/main");
		} else {
			// 데이터 불러오기 위한 선언
			List<e_ServiceDTO> s_dto_list = new ArrayList<e_ServiceDTO>();
	
			// 첫 화면 - 회원정보 관리
			// 게시물 갯수 불러오기
			e_ServiceDTO s_dto = new e_ServiceDTO();
			s_dto.setSv_type("question_public");
			// 내가 쓴 게시물만 불러오기
			e_MemberDTO m_dto = new e_MemberDTO();
			m_dto = (e_MemberDTO)session.getAttribute("user");
			s_dto.setMember_no(m_dto.getMember_no());
			int board_AllCount = s_service.myboard_count_All(s_dto);
			// 페이징
			e_SvPagingViewDTO s_page = new e_SvPagingViewDTO(board_AllCount, page_NowBno);
			s_page.setSv_type("question_public");
			// 페이지 내 게시물 목록 불러오기
			s_page.setMember_no(m_dto.getMember_no());
			s_dto_list = s_service.myboard_paging_origin_reply(s_page);
			// 게시물 원글, 답글 가져오기
			if (s_dto_list != null && s_dto_list.size() != 0) {
				System.out.println("ServiceController - getQuestion_public_myboard - 존재하는 게시물 있음");
			} else {
				System.out.println("ServiceController - getQuestion_public_myboard - 존재하는 게시물 없음");
			}
			mv.addObject("s_page", s_page);
			mv.addObject("s_dto_list", s_dto_list);
			mv.setViewName("/service/question-public-myboard");
		}
		return mv;
	}
	
	// 여러 글 삭제 - 선택
	@ResponseBody
	@PostMapping("/question-public-myboard-delete")
	public String postQuestion_public_myboard_delete(
			@RequestBody List<String> e_bno_list)
			throws Exception {
		System.out.println("ServiceController - postQuestion_public_myboard_delete");
		// 값이 제대로 안넘어 왔을 경우
		if(e_bno_list==null || e_bno_list.size()==0) {
			return "X";
		} else {
			// 글 삭제
			s_service.board_deleteAll(e_bno_list);
			return "O";
		}
	}
	
	// 공개 건의함 - 상세보기
	@GetMapping("/question-public-detail")
	public ModelAndView getQuestion_public_detail(
			HttpServletRequest request,
			@RequestParam int bno,
			ModelAndView mv)
			throws Exception {
		System.out.println("ServiceController - getQuestion_public_detail");
		
		// 데이터 불러오기 위한 선언
		e_ServiceDTO s_dto = new e_ServiceDTO();
		List<e_SvFileDTO> filelist = new ArrayList<e_SvFileDTO>(); 
		// 게시물 대표번호로 게시물 정보 가져오기
		s_dto = s_service.board_one(bno);
		// 파일도 같이 가져오기
		filelist = s_service.board_load_file(s_dto);
		// 댓글도 가져오기
		List<e_SvCommentDTO> comment_list = new ArrayList<e_SvCommentDTO>();
		e_SvCommentDTO s_commentDTO = new e_SvCommentDTO();
		comment_list = s_service.comment_load_All(s_commentDTO);
		// 로그인한 경우 (작성자와 같은 아이디 아닐시 증가)
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			// 멤버 테이블 불러오기
			e_MemberDTO m_dto = new e_MemberDTO();
			m_dto = (e_MemberDTO)session.getAttribute("user");
			// 게시물 조회수 증가
			// 조회수 테이블 불러오기
			e_SvViewcheckDTO s_viewCheck = new e_SvViewcheckDTO();
			s_viewCheck.setBno(bno);
			s_viewCheck.setMember_no(m_dto.getMember_no());
			// 게시판을 쓴 회원 번호 != 로그인한 회원번호 일시 조회수 증가
			if (s_dto.getMember_no() != m_dto.getMember_no()) {
				// 이미 조회수가 증가된 경우가 아닐 때 허용
				if(s_service.board_viewCheck(s_viewCheck) == true) {
					// 조회수 증가
					s_dto.setView_no(s_dto.getView_no()+1);
					// 증가한 값 업데이트
					s_service.board_viewUp(s_dto);
				}
				
				// 좋아요 테이블 불러오기
				e_SvLikecheckDTO s_likeCheck = new e_SvLikecheckDTO();
				s_likeCheck.setBno(bno);
				s_likeCheck.setMember_no(m_dto.getMember_no());
				
				// 좋아요 테이블 여부
				if (s_service.board_like_load(s_likeCheck) != null) {
					// 좋아요 여부 찾기
					s_likeCheck = s_service.board_like_load(s_likeCheck);
					if(s_likeCheck.getLike_check()==1) {
						// 좋아요를 눌렀다
						mv.addObject("like_click", "Y");
					} else {
						// 좋아요를 누르지 않았다
						mv.addObject("like_click", "N");
					}
				} else {
					// 좋아요를 누르지 않았다
					mv.addObject("like_click", "N");
				}
				
				// 싫어요 테이블 여부
				if (s_service.board_like_load(s_likeCheck) != null) {
					// 싫어요 여부 찾기
					s_likeCheck = s_service.board_like_load(s_likeCheck);
					if(s_likeCheck.getDislike_check()==1) {
						// 싫어요를 눌렀다
						mv.addObject("dislike_click", "Y");
					} else {
						// 싫어요를 누르지 않았다
						mv.addObject("dislike_click", "N");
					}
				} else {
					// 싫어요를 누르지 않았다
					mv.addObject("dislike_click", "N");
				}
			}
			
		} else if(session.getAttribute("user")==null) {
			// 조회수 테이블 불러오기
			e_SvViewcheckDTO s_viewCheck = new e_SvViewcheckDTO();
			s_viewCheck.setBno(bno);
			// ip로 접속했을 경우 (ip가 중복되지 않을 시 증가)
			if (s_service.board_Ipcheck(s_viewCheck) == true) {
				// 조회수 증가
				s_dto.setView_no(s_dto.getView_no()+1);
				// 증가한 값 업데이트
				s_service.board_viewUp(s_dto);
			}
		}
		
		// 게시판 불러오기 (값 변화 이후 불러오기)
		mv.addObject("comment_list", comment_list);
		mv.addObject("filelist", filelist);
		mv.addObject("s_dto", s_dto);
		mv.setViewName("/service/question-public-detail");

		return mv;
	}
	
	// 공개 건의함 - 상세보기 - 좋아요, 싫어요 체크
	@ResponseBody
	@PostMapping("/question-public-detail")
	public e_ServiceDTO postQuestion_public_detail(
			HttpServletRequest request,
			@RequestParam(value = "e_bno", required = false) int e_bno,
			@RequestParam(value = "e_heart_check", required = false) String e_heart_check)
			throws Exception {
		System.out.println("ServiceController - postQuestion_public_detail - 좋아요 체크");
		
		// 데이터 불러오기 위한 선언
		e_ServiceDTO s_dto = new e_ServiceDTO();
		// 게시물 번호로 게시물 객체 가져오기
		s_dto = s_service.board_one(e_bno);
		// 멤버 객체 테이블 불러오기
		HttpSession session = request.getSession();
		e_MemberDTO m_dto = new e_MemberDTO();
		m_dto = (e_MemberDTO)session.getAttribute("user");
		// 좋아요 객체 테이블 불러오기
		e_SvLikecheckDTO s_likeDTO = new e_SvLikecheckDTO();
		s_likeDTO.setBno(e_bno);
		s_likeDTO.setMember_no(m_dto.getMember_no());
		// 좋아요 증가 / 감소
		if (e_heart_check.equals("like_Y")) {
			s_service.board_like_up(s_likeDTO);
		} else if (e_heart_check.equals("like_N")) {
			s_service.board_like_down(s_likeDTO);
			// 싫어요 증가 / 감소
		} else if (e_heart_check.equals("dislike_Y")) {
			s_service.board_dislike_up(s_likeDTO);
		} else if (e_heart_check.equals("dislike_N")) {
			s_service.board_dislike_down(s_likeDTO);
		}
		return s_dto;
	}
	
	// 공개 건의함 - 상세보기 - 수정/삭제/뒤로가기 이동
	@GetMapping("/question-public-detail-button")
	public ModelAndView getQuestion_public_detail_button(
			@RequestParam String e_btn,
			@RequestParam int e_bno,
			ModelAndView mv)
			throws Exception {
		System.out.println("ServiceController - postQuestion_public_detail_button");
		
		// 데이터 불러오기 위한 선언
		e_ServiceDTO s_dto = new e_ServiceDTO();
		// 게시물 번호로 게시물 객체 가져오기
		s_dto = s_service.board_one(e_bno);
		// 게시판 첨부파일 객체
		List<e_SvFileDTO> filelist = new ArrayList<e_SvFileDTO>();
		// 파일도 같이 가져오기
		filelist = s_service.board_load_file(s_dto);
		
		if (e_btn.equals("fix")) {
			// 게시물 수정 버튼 누를시
			// 수정 페이지로 이동
			mv.addObject("filelist", filelist);
			mv.addObject("s_dto", s_dto);
			mv.setViewName("/service/question-public-fix");
			return mv;
		} else if (e_btn.equals("delete")) {
			s_service.board_delete(s_dto);
		} else if (e_btn.equals("reply")) {
			mv.addObject("e_bno", e_bno);
			mv.setViewName("/service/question-public-reply");
			return mv;
		}
		// 게시판 메인 화면으로 이동
		mv.setViewName("redirect:/service/question-public");
		return mv;
	}
	
	// 공개 건의함 - 게시물 수정
	@GetMapping("/question-public-fix")
	public void getQuestion_public_fix(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - getQuestion_public_fix");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			// 응답 - 한글 처리
			response.setContentType("text/html;charset=UTF-8");
			// 로그인 안했을 때 - 잘못된 접근
			// 뒤로가기
			PrintWriter out = response.getWriter();
			out.println("<script language ='javascript'>window.history.back();</script>");
			out.flush();
		}
	}
	
	@ResponseBody
	@PostMapping("/question-public-fix")
	public void postQuestion_public_fix(
			HttpServletRequest request)
			throws Exception {
		System.out.println("ServiceController - postQuestion_public_fix");
		request.setCharacterEncoding("utf-8");
		// 객체 생성
		e_MemberDTO m_dto = new e_MemberDTO();
		e_ServiceDTO s_dto = new e_ServiceDTO();
		
		// 첨부 파일 불러오기 - (1)
		// 파일 경로
		String savePath = "C:\\zerokalory_file";
		// 파일 크기 15MB
		int sizeLimit = 1024 * 1024 * 15;
		// 파라미터를 전달해줌 (같은 이름의 파일명 방지)
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
				new DefaultFileRenamePolicy());
		
		// 글 수정
		// 글쓰기 제목, 내용 - 줄바꿈 저장, 글 번호
		s_dto.setTitle(multi.getParameter("title"));
		s_dto.setDescription(multi.getParameter("description").replace("\r\n","<br>"));
		int bno = Integer.valueOf((String)multi.getParameter("bno"));
		s_dto.setBno(bno);
		
		// 게시물 수정
		s_service.board_fix(s_dto);
		// 첨부파일 수정
		// 첨부파일 기존꺼 삭제
		if (!multi.getParameterValues("file_del_num")[0].equals("")) {
			String[] values = multi.getParameterValues("file_del_num");
			int val = 0;
			//차례대로 삭제
			for(int i=0; i<values.length; i++) {
				val = Integer.valueOf(values[i]);
				e_SvFileDTO s_filedto = new e_SvFileDTO();
				s_filedto.setBno(bno);
				s_filedto.setFile_order(val);
				s_service.board_delete_file_one(s_filedto);
			}
		} 
		
		// 첨부파일 새 파일 추가
		// 첨부 파일 불러오기 - (2)
		// getFileNames() : 파일 이름들 받아오기
		Enumeration enumeration = multi.getFileNames();
		String fileName = "";
		String f_path = "";
		int i = 0;
		// 데이터가 있을때, 불러오기
		while(enumeration.hasMoreElements()){
			i++;
			// 파일 객체 불러오기
			e_SvFileDTO s_filedto = new e_SvFileDTO();
			s_filedto.setBno(bno);
			fileName = multi.getFilesystemName((String) enumeration.nextElement());
			s_filedto.setFilename(fileName);
			// 파일의 전체 경로
			f_path = savePath + "/" + fileName;
			s_filedto.setF_path(f_path);
			s_filedto.setFile_order(i);
			// 첨부파일 생성
			s_service.board_write_file(s_filedto);
		}
		//return "/service/question-member";
	}
	
	// 공개 건의함 - 게시물 작성
	@GetMapping("/question-public-write")
	public String getQuestion_public_write(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - getQuestion_write");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			// 응답 - 한글 처리
			response.setContentType("text/html;charset=UTF-8");
			// 로그인 안했을 때 - 잘못된 접근
			// 뒤로가기
			PrintWriter out = response.getWriter();
			out.println("<script language ='javascript'>window.history.back();</script>");
			out.flush();
		} else {
			// 로그인 되있을 때 - 정상 접근
			// 게시물 쓰기 뷰
			return "/service/question-public-write";
		}
		return null;
	}
	
	@ResponseBody
	@PostMapping("/question-public-write")
	public String postQuestion_public_write(
			HttpServletRequest request)
			throws Exception {
		System.out.println("ServiceController - postQuestion_public_write");
		request.setCharacterEncoding("utf-8");
		
		// 객체 생성
		e_MemberDTO m_dto = new e_MemberDTO();
		e_ServiceDTO s_dto = new e_ServiceDTO();
		
		// 첨부 파일 불러오기 - (1)
		// 파일 경로
		String savePath = "C:\\zerokalory_file";
		// 파일 크기 15MB
		int sizeLimit = 1024 * 1024 * 15;
		// 파라미터를 전달해줌 (같은 이름의 파일명 방지)
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
				new DefaultFileRenamePolicy());
		
		// 글 작성
		// 로그인한 회원 정보 불러오기
		HttpSession session = request.getSession();
		m_dto = (e_MemberDTO)session.getAttribute("user");
		// 닉네임 저장
		s_dto.setNickname(m_dto.getNickname());
		// 작성 시간, 회원번호
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
		s_dto.setSv_type("question_public");
		
		// 제목, 내용 검사
		if (multi.getParameter("title").trim().length()==0) {
			return "title_null";
		} else if (multi.getParameter("title").length()>50) {
			return "title_full";
		} else if (multi.getParameter("description").trim().length()==0) {
			return "description_null";
		} else if (multi.getParameter("description").length()>1000) {
			return "description_full";
		} else {
			// 글쓰기 제목, 내용 - 줄바꿈 저장
			s_dto.setTitle(multi.getParameter("title"));
			s_dto.setDescription(multi.getParameter("description").replace("\r\n","<br>"));
			
			// 글 시퀀스 불러오기
			int bno = s_service.board_write_bno();
			s_dto.setBno(bno);
			// 글 작성
			s_service.board_write(s_dto);
			// 첨부 파일 불러오기 - (2)
			// getFileNames() : 파일 이름들 받아오기
			Enumeration enumeration = multi.getFileNames();
			String fileName = "";
			String f_path = "";
			int i = 0;
			// 데이터가 있을때, 불러오기
			while(enumeration.hasMoreElements()){
				i++;
				// 파일 객체 불러오기
				e_SvFileDTO s_filedto = new e_SvFileDTO();
				s_filedto.setBno(bno);
				s_filedto.setFile_date(date_re);
				fileName = multi.getFilesystemName((String) enumeration.nextElement());
				s_filedto.setFilename(fileName);
				// 파일의 전체 경로
				f_path = savePath + "/" + fileName;
				s_filedto.setF_path(f_path);
				s_filedto.setFile_order(i);
				// 첨부파일 생성
				s_service.board_write_file(s_filedto);
			}
			return "success";
		}
	}
	
	// 공개 건의함 - 답글 작성
	@GetMapping("/question-public-reply")
	public String getQuestion_public_reply(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - getQuestion_public_reply");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			// 응답 - 한글 처리
			response.setContentType("text/html;charset=UTF-8");
			// 로그인 안했을 때 - 잘못된 접근
			// 뒤로가기
			PrintWriter out = response.getWriter();
			out.println("<script language ='javascript'>window.history.back();</script>");
			out.flush();
		} else {
			// 로그인 되있을 때 - 정상 접근
			// 게시물 쓰기 뷰
			return "/service/question-public-reply";
		}
		return null;
	}
	
	@ResponseBody
	@PostMapping("/question-public-reply")
	public void postQuestion_public_reply(
			HttpServletRequest request)
			throws Exception {
		System.out.println("ServiceController - postQuestion_public_reply");
		request.setCharacterEncoding("utf-8");
		
		// 객체 생성
		e_MemberDTO m_dto = new e_MemberDTO();
		e_ServiceDTO s_dto = new e_ServiceDTO();
		
		// 첨부 파일 불러오기 - (1)
		// 파일 경로
		String savePath = "C:\\zerokalory_file";
		// 파일 크기 15MB
		int sizeLimit = 1024 * 1024 * 15;
		// 파라미터를 전달해줌 (같은 이름의 파일명 방지)
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
				new DefaultFileRenamePolicy());
		
		// 글 작성
		// 로그인한 회원 정보 불러오기
		HttpSession session = request.getSession();
		m_dto = (e_MemberDTO)session.getAttribute("user");
		// 닉네임 저장
		s_dto.setNickname(m_dto.getNickname());
		// 작성 시간, 회원번호
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
		s_dto.setSv_type("question_public");
		// 글쓰기 제목, 내용 - 줄바꿈 저장
		s_dto.setTitle(multi.getParameter("title"));
		s_dto.setDescription(multi.getParameter("description").replace("\r\n","<br>"));
		
		// 답글 원글 번호 불러오기
		System.out.println(multi.getParameter("e_bno"));
		int group_origin = Integer.valueOf(multi.getParameter("e_bno"));
		s_dto.setGroup_origin(group_origin);
		// 글 시퀀스 불러오기
		int bno = s_service.board_write_bno();
		s_dto.setBno(bno);
		// 답글 작성
		s_service.board_reply(s_dto);
		// 첨부 파일 불러오기 - (2)
		// getFileNames() : 파일 이름들 받아오기
		Enumeration enumeration = multi.getFileNames();
		String fileName = "";
		String f_path = "";
		int i = 0;
		// 데이터가 있을때, 불러오기
		while(enumeration.hasMoreElements()){
			i++;
			// 파일 객체 불러오기
			e_SvFileDTO s_filedto = new e_SvFileDTO();
			s_filedto.setBno(bno);
			s_filedto.setFile_date(date_re);
			fileName = multi.getFilesystemName((String) enumeration.nextElement());
			s_filedto.setFilename(fileName);
			// 파일의 전체 경로
			f_path = savePath + "/" + fileName;
			s_filedto.setF_path(f_path);
			s_filedto.setFile_order(i);
			// 첨부파일 생성
			s_service.board_write_file(s_filedto);
		}
	}
	
	// 검색 - question-public
	@GetMapping("/question-public-search")
	public ModelAndView getQuestion_public_search(
			ModelAndView mv,
			@RequestParam(value="standard", defaultValue="new") String standard,
			@RequestParam(value="page_NowBno", defaultValue="1") int page_NowBno,
			@RequestParam(value="search_time") String search_time,
			@RequestParam(value="search_type") String search_type,
			@RequestParam(value="search_content") String search_content
			)throws Exception {
		System.out.println("ServiceController - getQuestion_public_search");
		
		// 검색 객체
		e_SvSearchDTO s_searchdto = new e_SvSearchDTO();
		s_searchdto.setSearch_time(search_time);
		s_searchdto.setSearch_type(search_type);
		s_searchdto.setSearch_content(search_content);
		s_searchdto.setSv_type("question_public");
		// 페이지 전체 갯수
		int board_AllCount = s_service.board_search_count_All(s_searchdto);
		// 페이징
		e_SvPagingViewDTO s_pageviewdto = new e_SvPagingViewDTO(board_AllCount, page_NowBno);
		s_searchdto.setBoard_NowStartBno(s_pageviewdto.getBoard_NowStartBno());
		s_searchdto.setBoard_NowEndBno(s_pageviewdto.getBoard_NowEndBno());
		s_searchdto.setStandard(standard);
		// 데이터 전달
		// 데이터 불러오기 위한 선언
		List<e_ServiceDTO> s_dto_list = new ArrayList<e_ServiceDTO>();
		// 페이지 내 게시물 목록 불러오기
		s_dto_list = s_service.board_search_All(s_searchdto);
		
		if (s_dto_list != null && s_dto_list.size() != 0) {
			System.out.println("ServiceController - getQuestion_public_search - 존재하는 게시물 있음");
		} else {
			System.out.println("ServiceController - getQuestion_public_search - 존재하는 게시물 없음");
		}
		mv.addObject("s_searchdto", s_searchdto);
		mv.addObject("s_page", s_pageviewdto);
		mv.addObject("s_dto_list", s_dto_list);
		mv.setViewName("/service/question-public-search");
		return mv;
	}
	
	// 검색 - question-public-myboard
	@GetMapping("/question-public-myboard-search")
	public ModelAndView getQuestion_public_myboard_search(
			HttpServletRequest request,
			ModelAndView mv,
			@RequestParam(value="standard", defaultValue="new") String standard,
			@RequestParam(value="page_NowBno", defaultValue="1") int page_NowBno,
			@RequestParam(value="search_time") String search_time,
			@RequestParam(value="search_type") String search_type,
			@RequestParam(value="search_content") String search_content
			)throws Exception {
		System.out.println("ServiceController - getQuestion_public_myboard_search");
		
		// 세션 생성
		HttpSession session = request.getSession();
		// 내가 쓴 게시물만 불러오기
		e_MemberDTO m_dto = new e_MemberDTO();
		m_dto = (e_MemberDTO)session.getAttribute("user");
		// 검색 객체
		e_SvSearchDTO s_searchdto = new e_SvSearchDTO();
		s_searchdto.setSearch_time(search_time);
		s_searchdto.setSearch_type(search_type);
		s_searchdto.setSearch_content(search_content);
		s_searchdto.setSv_type("question_public");
		s_searchdto.setMember_no(m_dto.getMember_no());
		s_searchdto.setStandard(standard);
		// 페이지 전체 갯수
		int board_AllCount = s_service.myboard_search_count_All(s_searchdto);
		// 페이징
		e_SvPagingViewDTO s_pageviewdto = new e_SvPagingViewDTO(board_AllCount, page_NowBno);
		s_searchdto.setBoard_NowStartBno(s_pageviewdto.getBoard_NowStartBno());
		s_searchdto.setBoard_NowEndBno(s_pageviewdto.getBoard_NowEndBno());
		
		// 데이터 전달
		// 데이터 불러오기 위한 선언
		List<e_ServiceDTO> s_dto_list = new ArrayList<e_ServiceDTO>();
		// 페이지 내 게시물 목록 불러오기
		s_dto_list = s_service.myboard_search_All(s_searchdto);
		
		if (s_dto_list != null && s_dto_list.size() != 0) {
			System.out.println("ServiceController - getQuestion_public_myboard_search - 존재하는 게시물 있음");
		} else {
			System.out.println("ServiceController - getQuestion_public_myboard_search - 존재하는 게시물 없음");
		}
		mv.addObject("s_searchdto", s_searchdto);
		mv.addObject("s_page", s_pageviewdto);
		mv.addObject("s_dto_list", s_dto_list);
		mv.setViewName("/service/question-public-myboard-search");
		return mv;
	}
	
	// 댓글, 답글
	// 댓글 작성하기
	@ResponseBody
	@GetMapping("/question-public-comment/insert")
	public String getQuestion_public_comment(
			HttpServletRequest request,
			@RequestParam int bno,
			@RequestParam String comment_code
			)throws Exception {
		System.out.println("ServiceController - getQuestion_public_comment");
		e_SvCommentDTO s_commentDTO = new e_SvCommentDTO();
		if(comment_code.length()==0 || comment_code.equals(" ")) {
			return "X";
		}
		// origin_code - 시퀀스 가져오기
		int origin_code = s_service.comment_c_code();
		s_commentDTO.setOrigin_code(origin_code);
		s_commentDTO.setC_code(origin_code);
		// 타입
		s_commentDTO.setType_code("comment");
		// 닉네임
		HttpSession session = request.getSession();
		e_MemberDTO m_dto = new e_MemberDTO();
		m_dto = (e_MemberDTO)session.getAttribute("user");
		s_commentDTO.setNickname(m_dto.getNickname());
		// 댓글 내용
		s_commentDTO.setComment_code(comment_code);
		// bno
		s_commentDTO.setBno(bno);
		// member_no
		s_commentDTO.setMember_no(m_dto.getMember_no());
		// 댓글 작성
		s_service.comment_insert(s_commentDTO);
		return "O";
	}
	
	// 답글 작성하기
	@ResponseBody
	@GetMapping("/question-public-reply/insert")
	public String getQuestion_public_reply(
			HttpServletRequest request,
			@RequestParam int bno,
			@RequestParam int origin_code,
			@RequestParam String comment_code,
			@RequestParam String to_nickname
			)throws Exception {
		System.out.println("ServiceController - getQuestion_public_reply");
		if(comment_code.length()==0 || comment_code.equals(" ")) {
			return "X";
		}
		e_SvCommentDTO s_commentDTO = new e_SvCommentDTO();
		// origin_code
		s_commentDTO.setOrigin_code(origin_code);
		// c_code
		int c_code = s_service.comment_c_code();
		s_commentDTO.setC_code(c_code);
		// 타입
		s_commentDTO.setType_code("reply");
		// 닉네임
		HttpSession session = request.getSession();
		e_MemberDTO m_dto = new e_MemberDTO();
		m_dto = (e_MemberDTO)session.getAttribute("user");
		s_commentDTO.setNickname(m_dto.getNickname());
		// to_nickname
		s_commentDTO.setTo_nickname(to_nickname);
		// 댓글 내용
		s_commentDTO.setComment_code(comment_code);
		// bno
		s_commentDTO.setBno(bno);
		// member_no
		s_commentDTO.setMember_no(m_dto.getMember_no());
		// 답글 작성
		s_service.comment_insert(s_commentDTO);
		return "O";
	}
	
	// 댓글 삭제
	@ResponseBody
	@GetMapping("/question-public-comment/delete")
	public String getQuestion_public_comment_delete(
			@RequestParam int bno,
			@RequestParam int c_code
			)throws Exception {
		System.out.println("ServiceController - getQuestion_public_comment_delete");
		e_SvCommentDTO s_commentDTO = new e_SvCommentDTO();
		s_commentDTO.setType_code("comment");
		s_commentDTO.setC_code(c_code);
		// 댓글 삭제
		s_service.comment_delete(s_commentDTO);
		return "O";
	}
	
	// 답글 삭제
	@ResponseBody
	@GetMapping("/question-public-reply/delete")
	public String getQuestion_public_reply_delete(
			@RequestParam int bno,
			@RequestParam int c_code
			)throws Exception {
		System.out.println("ServiceController - getQuestion_public_reply_delete");
		e_SvCommentDTO s_commentDTO = new e_SvCommentDTO();
		s_commentDTO.setType_code("reply");
		s_commentDTO.setC_code(c_code);
		// 답글 삭제
		s_service.comment_delete(s_commentDTO);
		return "O";
	}
	
	// 댓글 수정
	@ResponseBody
	@GetMapping("/question-public-comment/fix")
	public String getQuestion_public_comment_fix(
			@RequestParam int bno,
			@RequestParam int c_code,
			@RequestParam String comment_code
			)throws Exception {
		System.out.println("ServiceController - getQuestion_public_comment_fix");
		if(comment_code.length()==0 || comment_code.equals(" ")) {
			return "X";
		}
		e_SvCommentDTO s_commentDTO = new e_SvCommentDTO();
		s_commentDTO.setType_code("comment");
		s_commentDTO.setBno(bno);
		s_commentDTO.setC_code(c_code);
		s_commentDTO.setComment_code(comment_code);
		s_service.comment_update(s_commentDTO);
		return "O";
	}
	
	// 답글 수정
	@ResponseBody
	@GetMapping("/question-public-reply/fix")
	public String getQuestion_public_reply_fix(
			@RequestParam int bno,
			@RequestParam int c_code,
			@RequestParam String comment_code
			)throws Exception {
		System.out.println("ServiceController - getQuestion_public_reply_fix");
		if(comment_code.length()==0 || comment_code.equals(" ")) {
			return "X";
		}
		e_SvCommentDTO s_commentDTO = new e_SvCommentDTO();
		s_commentDTO.setType_code("reply");
		s_commentDTO.setBno(bno);
		s_commentDTO.setC_code(c_code);
		s_commentDTO.setComment_code(comment_code);
		s_service.comment_update(s_commentDTO);
		return "O";
	}
}
