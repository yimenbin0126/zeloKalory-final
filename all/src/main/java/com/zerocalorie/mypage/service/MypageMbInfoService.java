package com.zerocalorie.mypage.service;

import javax.servlet.http.HttpServletRequest;

import com.zerocalorie.member.dto.e_MemberDTO;

public interface MypageMbInfoService {
	
	
	// id를 이용해 회원정보 불러오기
	public e_MemberDTO id_loadMember(e_MemberDTO dto);
	
	// 회원정보 불러오기 (member_no 이용)
	public e_MemberDTO member_no_loadMember(e_MemberDTO dto);
	
	// 회원정보 변경
	public void id_updateMember(HttpServletRequest request,e_MemberDTO dto);
	
	// 프로필 사진 업데이트
	public e_MemberDTO pro_img_update(e_MemberDTO dto);

}
