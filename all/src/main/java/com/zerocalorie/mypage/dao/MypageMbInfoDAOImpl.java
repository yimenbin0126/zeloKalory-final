package com.zerocalorie.mypage.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zerocalorie.member.dto.e_MemberDTO;

@Repository
public class MypageMbInfoDAOImpl implements MypageMbInfoDAO {

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public e_MemberDTO id_loadMember(e_MemberDTO dto) {
		System.out.println("e_MemberDAOimpl - id_loadMember - 회원정보 불러오기");
		return sqlSession.selectOne("mypageMbInfoMapper.id_loadMember", dto);
	}
	
	@Override
	public e_MemberDTO member_no_loadMember(e_MemberDTO dto) {
		System.out.println("e_MemberDAOimpl - member_no_loadMember - 회원정보 불러오기");
		return sqlSession.selectOne("mypageMbInfoMapper.member_no_loadMember", dto);
	}

	@Override
	public void id_updateMember(e_MemberDTO dto){
		System.out.println("e_MemberDAOimpl - id_updateMember - 회원정보 변경");
		sqlSession.update("mypageMbInfoMapper.id_updateMember", dto);	
	}
	
	@Override
	public e_MemberDTO pro_img_update(e_MemberDTO dto){
		System.out.println("e_MemberDAOimpl - pro_img_update - 프로필사진 변경");
		sqlSession.update("memberMapper.pro_img_update", dto);
		return dto;	
	}

}
