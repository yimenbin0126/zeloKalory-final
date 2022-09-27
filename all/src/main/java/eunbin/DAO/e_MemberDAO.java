package eunbin.DAO;

import eunbin.DTO.e_MemberDTO;

public interface e_MemberDAO {

	// 회원가입 (멤버 추가 메서드)
	public void addMember(e_MemberDTO dto) throws Exception;
    
    // 로그인 체크 (아이디, 비밀번호)
	public int loginMember(e_MemberDTO dto) throws Exception;
    
    // 회원정보 불러오기 (아이디. 로그인용)
	public e_MemberDTO id_loadMember(e_MemberDTO dto) throws Exception;
	
	// 회원정보 불러오기 (member_no 이용)
	public e_MemberDTO member_no_loadMember(e_MemberDTO dto) throws Exception;
	
    // 아이디 중복 체크
	public int idCheck(e_MemberDTO dto) throws Exception;
	
    // 닉네임 중복 체크
	public int nickCheck(e_MemberDTO dto) throws Exception;
	
	// 회원정보 변경
	public int id_updateMember(e_MemberDTO dto) throws Exception;
}
