package eunbin.service;

import org.springframework.beans.factory.annotation.Autowired;

import eunbin.DAO.MemberDAO;
import eunbin.DTO.MemberDTO;

public class MemberServiceimpl implements MemberService {

	@Autowired
	private MemberDAO dao;
	
	// 회원가입 (멤버 추가 메서드)
	public void addMember(MemberDTO dto) throws Exception {
		dao.addMember(dto);
	}

	// 로그인 체크 (아이디, 비밀번호)
	public int loginMember(MemberDTO dto) throws Exception {
		return dao.loginMember(dto);
	}

	// 회원정보 불러오기 (아이디. 로그인용)
	public MemberDTO load_login(MemberDTO dto) throws Exception {
		return dao.load_login(dto);
	}

	// 회원정보 불러오기 (member_no 이용)
	public MemberDTO load_member(MemberDTO dto) throws Exception {
		return dao.load_member(dto);
	}

	// 아이디 중복 체크
	public int idCheck(MemberDTO dto) throws Exception {
		return dao.idCheck(dto);
	}

	// 닉네임 중복 체크
	public int nickCheck(MemberDTO dto) throws Exception {
		return dao.nickCheck(dto);
	}
}