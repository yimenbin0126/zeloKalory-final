package com.zerocalorie.calender.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.zerocalorie.calender.dto.CalSearchMbDTO;

@Repository
public class CalSearchMbDAOImpl implements CalSearchMbDAO {

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<CalSearchMbDTO> searchMembers(String id) {

		System.out.println(">>>>>>>>>>>>>>>> id : " + id);
		List<CalSearchMbDTO> list = sqlSession.selectList("calSearchMbMapper.searchMembers",id);
		
		System.out.println(">>>>>>>>>>>>>>>> list: " + list.size());
		
		return list;
	}
}
