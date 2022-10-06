package eunbin.controller;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import eunbin.DTO.e_MemberDTO;
import eunbin.service.e_MemberService;

@Controller
@RequestMapping("/member/*")
public class e_MemberController {

	@Autowired
	e_MemberService m_service;
	
	// 로그인
	@GetMapping("/login")
	public String getLogin(HttpServletRequest request)
			throws Exception {
		System.out.println("MemberController - getLogin");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			// 로그인 했을 때 - 비정상 접근
			return "/main/main";
		} 
		return "/member/login";
	}
	
	@PostMapping("/login")
	public String postLogin(@ModelAttribute e_MemberDTO m_dto,
			@RequestParam("e_auto_login_check") String e_auto_login_check,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("MemberController - postLogin");
		
		// 세션 생성
		HttpSession session = request.getSession();

		// 아이디, 비밀번호 체크
		if (m_service.loginMember(m_dto) == 1) {
			m_dto = m_service.id_loadMember(m_dto);
			// 세션에 회원정보 등록
			session.setAttribute("user", m_dto);
			System.out.println("로그인 완료");

			// 자동로그인 체크 여부 - 체크 했을 시
			if (request.getParameter("e_auto_login_check").equals("Y")
					&& request.getParameter("e_auto_login_check") != null) {
				Cookie cookie = new Cookie("userId", session.getId());
				cookie.setPath("/");
				// 유효기간 7일
				cookie.setMaxAge(60 * 60 * 24 * 7);
				// 쿠키 적용
				response.addCookie(cookie);
			}
			return "/main/main";
		} else {
			System.out.println("로그인 실패");
			// 응답 - 한글 처리
			response.setContentType("text/html;charset=UTF-8");
			// 로그인 실패 시 경고창
			PrintWriter out = response.getWriter();
			out.println(
					"<script language ='javascript'>alert('아이디 혹은 비밀번호가 맞지 않습니다. \\n다시 로그인 해주세요.'); location.href='/all/member/login';</script>");
			out.flush();
		}
		return "/member/login";
	}

	// 로그아웃
	@GetMapping("/logout")
	public void getLogout(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("MemberController - logout");
		
		// 로그인 세션 불러와 로그아웃
		HttpSession session = request.getSession();
		session.invalidate();
		// 쿠키 내역 있는지 확인 - 자동 로그인 해제
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userId")) {
					// 유효기간 0으로 만들어 초기화
					Cookie del_c = new Cookie("userId", "");
					del_c.setMaxAge(0);
					response.addCookie(del_c);
				}
			}
		}
		// 응답 - 한글 처리
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script language ='javascript'>alert('로그아웃 하였습니다.'); location.href='/all/main/main'; </script>");
		out.flush();
	}
	
	// 회원가입 - 항목별 체크
	@GetMapping("/join")
	public String getJoin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("MemberController - getJoin");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			// 로그인 했을 때 - 비정상 접근
			return "/main/main";
		}
		return "/member/join";
	}
	
	@ResponseBody
	@PostMapping("/join")
	public String postJoin(
			@RequestParam(value = "e_input", required = false) String e_input,
			@RequestParam(value = "e_Check_click", required = false) String e_Check_click)
			throws Exception {
		System.out.println("MemberController - postJoin");

		e_MemberDTO m_dto = new e_MemberDTO();
		// 중복체크
		if (e_Check_click.equals("e_join_idcheck")) {
			m_dto.setId(e_input);
			// 아이디 중복 체크 - 값 다시 전달
			return String.valueOf(m_service.idCheck(m_dto));
		} else if (e_Check_click.equals("e_join_nickcheck")) {
			m_dto.setNickname(e_input);
			// 닉네임 중복 체크 - 값 다시 전달
			return String.valueOf(m_service.nickCheck(m_dto));
		}
		return null;
	}
	
	// 회원가입
	@GetMapping("/joinComplet")
	public void getJoinComplet() throws Exception {
		System.out.println("MemberController - getJoinComplet");
	}

	// 회원가입 - 유효성 검사
	@ResponseBody
	@PostMapping(path="/joinComplet", produces="application/text;charset=utf-8")
	public String postJoinComplet(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("MemberController - postJoinComplet");
		e_MemberDTO m_dto = new e_MemberDTO();
		// 프로필 이미지 파일 불러오기
		// 파일 경로
		String savePath = "C:\\zerokalory_file";
		// 파일 크기 15MB
		int sizeLimit = 1024 * 1024 * 15;
		// 파라미터를 전달해줌 (같은 이름의 파일명 방지)
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
				new DefaultFileRenamePolicy());
		// getFilesystemName() : 파일 이름 받아오기
		String fileName = multi.getFilesystemName("pro_img");
		// 파일의 전체 경로
		String pro_img = savePath + "/" + fileName;
		// 파일 등록 안 했을시
		if (fileName==null || fileName.equals("")) {
			pro_img = savePath + "/" + "dietmall_basic_profile.png";
		}
		// 프로필 이미지 값 저장
		m_dto.setPro_img(pro_img);
		// 그 외 값 불러오기
		// 필수값 불러오기
		m_dto.setId(multi.getParameter("id"));
		m_dto.setPw(multi.getParameter("pw"));
		m_dto.setNickname(multi.getParameter("nickname"));
		m_dto.setEmail(multi.getParameter("email"));
		m_dto.setTel(multi.getParameter("tel"));
		m_dto.setHeight(Integer.valueOf(String.valueOf(multi.getParameter("height"))));
		// 필수 아닌 값 불러오기
		// 이름
		if (multi.getParameter("name") == null || multi.getParameter("name").equals("")
				|| Pattern.matches("^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,3}$", multi.getParameter("name")) == false) {
			m_dto.setName("홍길동");
		} else {
			m_dto.setName(multi.getParameter("name"));
		}
		// 생년월일
		if (Date.valueOf((String)multi.getParameter("birth")) == null || Date.valueOf((String)multi.getParameter("birth")).equals("")
				|| Pattern.matches("^[0-9]*$", String.valueOf(Date.valueOf((String)multi.getParameter("birth"))).substring(0,4)) == false
				|| Pattern.matches("^[0-9]*$", String.valueOf(Date.valueOf((String)multi.getParameter("birth"))).substring(5,7)) == false
				|| Pattern.matches("^[0-9]*$", String.valueOf(Date.valueOf((String)multi.getParameter("birth"))).substring(8)) == false
				|| String.valueOf(Date.valueOf((String)multi.getParameter("birth"))).length() != 10
				|| String.valueOf(Date.valueOf((String)multi.getParameter("birth"))).charAt(4) != '-'
				|| String.valueOf(Date.valueOf((String)multi.getParameter("birth"))).charAt(7) != '-'
				) {
			m_dto.setBirth(java.sql.Date.valueOf("2000-01-01"));
		} else {
			m_dto.setBirth(Date.valueOf((String)multi.getParameter("birth")));
		}
		// 성별
		if (multi.getParameter("gender") == null || multi.getParameter("gender").equals("e_none")) {
			m_dto.setGender("F");
		} else {
			m_dto.setGender(multi.getParameter("gender"));
		}
		// 유효성 검사
		String msg = m_service.testMember(m_dto); 
		if (msg.equals("통과")) {
			// 유효성 검사 일치할 때
			// 회원정보 추가
			m_service.addMember(m_dto);
			System.out.println("회원가입 성공");
		} else {
			// 유효성 검사가 일치하지 않을 때
			System.out.println("회원가입 실패");
		}
		// 결과 값 전달
		return msg;
	}
	
}
