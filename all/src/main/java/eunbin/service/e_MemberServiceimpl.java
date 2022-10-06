package eunbin.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eunbin.DAO.e_MemberDAO;
import eunbin.DTO.e_MemberDTO;

@Service
public class e_MemberServiceimpl implements e_MemberService {

	@Autowired
	private e_MemberDAO dao;
	
	// 회원가입 (멤버 추가 메서드)
	public void addMember(e_MemberDTO dto) throws Exception {
		System.out.println("e_MemberServiceimpl - addMember - 회원가입");
		dao.addMember(dto);
	}
	
	// 유효성 검사
	public String testMember(e_MemberDTO dto) throws Exception {
		System.out.println("e_MemberServiceimpl - testMember - 유효성 검사");
		// 아이디
		if(Pattern.matches("^[a-z0-9]{5,9}$", dto.getId()) == false
				|| Pattern.matches(".*[a-z].*", dto.getId()) == false
				|| Pattern.matches(".*[0-9].*", dto.getId()) == false
				|| idCheck(dto)==1) {
			return "아이디를 제대로 입력해주세요.";
		}
		// 비밀번호
		if(Pattern.matches("^[a-zA-Z0-9!@#*]{5,9}$", dto.getPw()) == false
				|| Pattern.matches(".*[a-z].*", dto.getPw()) == false
				|| Pattern.matches(".*[A-Z].*", dto.getPw()) == false
				|| Pattern.matches(".*[0-9].*", dto.getPw()) == false
				|| Pattern.matches(".*[@,!,#,*].*", dto.getPw()) == false) {
			return "비밀번호를 제대로 입력해주세요.";
		}
		// 닉네임
		if(Pattern.matches("^[가-힣]{2,5}$", dto.getNickname()) == false
				|| nickCheck(dto)==1) {
			return "닉네임을 제대로 입력해주세요.";
		}
		// 이메일
		if(Pattern.matches("^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+", dto.getEmail()) == false) {
			return "이메일을 제대로 입력해주세요.";
		}
		// 전화번호
		if(Pattern.matches("^01([0|1|6|7|8|9])$", dto.getTel().substring(0,3)) == false
				|| Pattern.matches("^[0-9]*$", dto.getTel().substring(4,8)) == false
				|| Pattern.matches("^[0-9]*$", dto.getTel().substring(9)) == false
				|| dto.getTel().charAt(3) != '-'
				|| dto.getTel().charAt(8) != '-'
				|| dto.getTel().length() != 13) {
			return "전화번호를 제대로 입력해주세요.";
		}
		// 키
		if(Pattern.matches("^[0-9]{3,3}$", String.valueOf(dto.getHeight())) == false
				|| dto.getHeight() <= 100
				|| dto.getHeight() >= 200) {
			return "키를 제대로 입력해주세요.";
		}
		
		// 최종까지 통과
		return "통과";
	}

	// 로그인 체크 (아이디, 비밀번호)
	public int loginMember(e_MemberDTO dto) throws Exception {
		System.out.println("e_MemberServiceimpl - loginMember - 로그인 체크");
		return dao.loginMember(dto);
	}

	// 회원정보 불러오기 (아이디. 로그인용)
	public e_MemberDTO id_loadMember(e_MemberDTO dto) throws Exception {
		System.out.println("e_MemberServiceimpl - id_loadMember - 회원정보 불러오기");
		return dao.id_loadMember(dto);
	}

	// 회원정보 불러오기 (member_no 이용)
	public e_MemberDTO member_no_loadMember(e_MemberDTO dto) throws Exception {
		System.out.println("e_MemberServiceimpl - member_no_loadMember - 회원정보 불러오기");
		return dao.member_no_loadMember(dto);
	}

	// 아이디 중복 체크
	public int idCheck(e_MemberDTO dto) throws Exception {
		System.out.println("e_MemberServiceimpl - idCheck - 아이디 중복 체크");
		return dao.idCheck(dto);
	}

	// 닉네임 중복 체크
	public int nickCheck(e_MemberDTO dto) throws Exception {
		System.out.println("e_MemberServiceimpl - nickCheck - 닉네임 중복 체크");
		return dao.nickCheck(dto);
	}
	
	// 회원정보 변경
	public int id_updateMember(e_MemberDTO dto) throws Exception {
		System.out.println("e_MemberServiceimpl - id_updateMember - 회원정보 변경");
		return dao.id_updateMember(dto);
	}
}