package eunbin.service;

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