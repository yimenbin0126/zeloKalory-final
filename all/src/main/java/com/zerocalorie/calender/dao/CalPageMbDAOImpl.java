package com.zerocalorie.calender.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zerocalorie.calender.dto.CalPageMbDTO;

@Repository
public class CalPageMbDAOImpl implements CalPageMbDAO {
	
	@Autowired
	SqlSession sqlSession;

	//id로 페이지 회원정보 조회
	@Override
	public CalPageMbDTO read(String id) {
		
		return sqlSession.selectOne("calPageMbMapper.selectList",id);
	}

}
