package com.zerocalorie.mypage.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerocalorie.member.dto.e_MemberDTO;
import com.zerocalorie.mypage.dao.MypageMbInfoDAO;


@Service
public class MypageMbInfoServiceImpl implements MypageMbInfoService {
	

	@Autowired
	MypageMbInfoDAO mypageMbInfoDAO;
	
	@Override
	public e_MemberDTO id_loadMember(e_MemberDTO dto){
		System.out.println("MypageMbInfoServiceImpl-id_loadMember");
		return mypageMbInfoDAO.id_loadMember(dto);
	}
	
	@Override
	public e_MemberDTO member_no_loadMember(e_MemberDTO dto){
		System.out.println("MypageMbInfoServiceImpl-member_no_loadMember");
		return mypageMbInfoDAO.member_no_loadMember(dto);
	}
	
	@Override
	public void id_updateMember(HttpServletRequest request,e_MemberDTO dto){
		System.out.println("MypageMbInfoServiceImpl-id_updateMember");
		mypageMbInfoDAO.id_updateMember(dto);
	}
	

	@Override
	public e_MemberDTO pro_img_update(e_MemberDTO dto){
		System.out.println("MypageMbInfoServiceImpl-pro_img_update");
		return mypageMbInfoDAO.pro_img_update(dto);
	}
}
