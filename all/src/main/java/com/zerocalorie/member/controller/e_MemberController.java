package com.zerocalorie.member.controller;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.zerocalorie.member.dto.e_MemberDTO;
import com.zerocalorie.member.service.e_MemberService;

@Controller
@RequestMapping("/member/*")
public class e_MemberController {

	@Autowired
	e_MemberService m_service;
	
	@Autowired
	BCryptPasswordEncoder pwdEncoder;
	
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
	
	@ResponseBody
	@PostMapping("/login")
	public String postLogin(
			@RequestParam("id") String id,
			@RequestParam("pw") String pw,
			@RequestParam("e_auto_login_check") String e_auto_login_check,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("MemberController - postLogin");
		// 객체 대입
		e_MemberDTO m_dto = new e_MemberDTO();
		m_dto.setId(id);
		m_dto.setPw(pw);
		// 세션 생성
		HttpSession session = request.getSession();
		// 아이디, 비밀번호 체크
		if (m_service.loginMember(m_dto) == 1) {
			e_MemberDTO _m_dto = new e_MemberDTO();
			// 아이디 체크 완료시 회원정보 불러오기
			_m_dto = m_service.id_loadMember(m_dto);
			boolean pwdMatch = false;
			// 테스트용 계정, 회원가입용 계정 분리
			if(m_dto.getId().equals("qwer") || m_dto.getId().equals("asdf")
					|| m_dto.getId().equals("zxcv23") || m_dto.getId().equals("qwwww")
					|| m_dto.getId().equals("admin") || m_dto.getId().equals("zxasqw")
					|| m_dto.getId().equals("hanii") || m_dto.getId().equals("hong")) {
				if (m_dto.getPw().equals(_m_dto.getPw())) {
					pwdMatch = true;
				}
			} else {
				// DB에 저장된 암호화 == 내가 작성한 암호
				pwdMatch = pwdEncoder.matches(m_dto.getPw(), _m_dto.getPw());
			}

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
			if (pwdMatch == true) {
				// 세션에 회원정보 등록
				session.setAttribute("user", _m_dto);
				System.out.println("로그인 성공");
				return "success";
			} else {
				System.out.println("로그인 실패");
				return "pw";
			}
		} else {
			System.out.println("로그인 실패");
			return "id";
		}
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
			System.out.println(m_dto.toString());
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
		// 파일의 전체 경로 말고 이름만 저장
		String pro_img = fileName;
		// 파일 등록 안 했을시
		if (fileName==null || fileName.equals("")) {
			pro_img = "dietmall_basic_profile.png";
		}
		// 프로필 이미지 값 저장
		m_dto.setPro_img(pro_img);
		// 그 외 값 불러오기
		// 필수값 불러오기
		m_dto.setId(multi.getParameter("id"));
		// 패스워드 암호화
		String pwd = pwdEncoder.encode(multi.getParameter("pw"));
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
			m_dto.setPw(pwd);
			m_service.addMember(m_dto);
			System.out.println("회원가입 성공");
		} else {
			// 유효성 검사가 일치하지 않을 때
			System.out.println("회원가입 실패");
		}
		// 결과 값 전달
		return msg;
	}
	
	// 회원정보 찾기 - 아이디
	@GetMapping("/findid")
	public String getFindid(HttpServletRequest request)
			throws Exception {
		System.out.println("MemberController - getFindid");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			// 로그인 했을 때 - 비정상 접근
			return "/main/main";
		} 
		return "/member/findid";
	}
	
	@ResponseBody
	@PostMapping("/findid")
	public String postFindid(
			@RequestParam String email,
			HttpServletRequest request)
			throws Exception {
		System.out.println("MemberController - postFindid");
		e_MemberDTO m_dto = new e_MemberDTO();
		m_dto.setEmail(email);
		// 이메일 존재 확인
		if (m_service.findid_email(m_dto)!=null) {
			e_MemberDTO _m_dto = new e_MemberDTO();
			_m_dto = m_service.findid_email(m_dto);
			// 이메일 있을 시
			m_service.sendEmail_findid(_m_dto);
			return "O";
		}
		// 이메일 없을 시
		return "X";
	}
	
	// 회원정보 찾기 - 비밀번호
	// 첫화면 - 아이디, 이메일 검사
	@GetMapping("/findpass")
	public ModelAndView getFindpass(
			ModelAndView mv,
			HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			// 로그인 했을 때 - 비정상 접근
			mv.setViewName("/main/main");
		} else {
			mv.setViewName("/member/findpass");
		}
		return mv;
	}
	
	@ResponseBody
	@PostMapping("/findpass")
	public String postFindpass(
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String id,
			HttpServletRequest request)
			throws Exception {
		System.out.println("MemberController - postFindpass");
		// 이메일, 아이디 존재 확인
		e_MemberDTO m_dto = new e_MemberDTO();
		HttpSession session = request.getSession();
		// 인증 코드
		m_dto.setEmail(email);
		m_dto.setId(id);
		if (m_service.findid_email_id(m_dto)!=null) {
			e_MemberDTO _m_dto = new e_MemberDTO();
			_m_dto = m_service.findid_email_id(m_dto);
			// 이메일 있을 시
			String code = m_service.sendEmail_findpass(_m_dto);
			// 기존 세션 있으면 삭제
			if (session.getAttribute("code_findid") != null) {
				session.removeAttribute("code_findid");
				session.removeAttribute("member_findid");
			}
			// 코드랑 회원정보 세션에 저장
			session.setAttribute("code_findid", code);
			session.setAttribute("member_findid", _m_dto);
			return "O";
		} else {
			// 이메일 없을 시
			return "X";
		}
	}

	// 인증 코드 검사
	@GetMapping("/findpass-code")
	public ModelAndView getFindpass_code(
			ModelAndView mv,
			HttpServletRequest request)
			throws Exception {
		System.out.println("MemberController - getFindpass_code");
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null
				|| session.getAttribute("code_findid") == null) {
			// 로그인 했을 때 - 비정상 접근
			mv.setViewName("/main/main");
		} else {
			mv.setViewName("/member/findpass-code");
		}
		return mv;
	}
	
	@ResponseBody
	@PostMapping("/findpass-code")
	public String postFindpass_code(
			@RequestParam(required = false) String code,
			HttpServletRequest request)
			throws Exception {
		System.out.println("MemberController - postFindpass_code");
		// 세션에서 인증코드 가져오기
		e_MemberDTO m_dto = new e_MemberDTO();
		HttpSession session = request.getSession();
		// 인증코드 같은지 확인
		if (session.getAttribute("code_findid").equals(code)) {
			session.removeAttribute("code_findid");
			return "O";
		} else {
			// 인증코드가 다를 때
			return "X";
		}
	}
	
	// 비밀번호 변경
	@GetMapping("/findpass-new")
	public ModelAndView getFindpass_new(
			ModelAndView mv,
			HttpServletRequest request)
			throws Exception {
		System.out.println("MemberController - getFindpass_new");
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null
				&& session.getAttribute("member_findid") == null
				&& session.getAttribute("code_findid") != null) {
			// 로그인 했을 때 - 비정상 접근
			mv.setViewName("/main/main");
		} else {
			mv.setViewName("/member/findpass-new");
		}
		return mv;
	}
	
	@ResponseBody
	@PostMapping("/findpass-new")
	public String postFindpass_new(
			@RequestParam(required = false) String pass,
			@RequestParam(required = false) String pass_re,
			HttpServletRequest request)
			throws Exception {
		System.out.println("MemberController - postFindpass_new");
		HttpSession session = request.getSession();
		// 비밀번호 변경
		// 세션의 회원정보 불러오기
		e_MemberDTO m_dto = new e_MemberDTO();
		m_dto = (e_MemberDTO)session.getAttribute("member_findid");
		System.out.println(m_dto.toString());
		System.out.println(pass);
		System.out.println(pass_re);
		// 비밀번호 검증
		System.out.println(pwdEncoder.matches(pass, m_dto.getPw()));
		if (pwdEncoder.matches(pass, m_dto.getPw())) {
			// 비밀번호 기존거랑 같음
			return "X1";
		}
		if (Pattern.matches("^[a-zA-Z0-9!@#*]{5,9}$", pass) == false
				|| Pattern.matches(".*[a-z].*", pass) == false
				|| Pattern.matches(".*[A-Z].*", pass) == false
				|| Pattern.matches(".*[0-9].*", pass) == false
				|| Pattern.matches(".*[@,!,#,*].*", pass) == false
				|| pass.length()==0) {
			// 비밀번호 유효성 검사가 제대로 안됨
			return "X2";
		}
		if (!pass.equals(pass_re) || pass_re.length()==0) {
			// 비밀번호 재입력이 제대로 입력되지 않음
			return "X3";
		}
		// 제대로 입력됨
		// 비밀번호 수정된거 업데이트
		String pwd = pwdEncoder.encode(pass);
		m_dto.setPw(pwd);
		m_service.id_updateMember(m_dto);
		// 세션 삭제
		session.removeAttribute("member_findid");
		return "O";
	}
	
}
