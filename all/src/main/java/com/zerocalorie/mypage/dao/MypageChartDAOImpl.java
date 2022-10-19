package com.zerocalorie.mypage.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.zerocalorie.mypage.dto.MypageChartDTO;

@Repository
public class MypageChartDAOImpl implements MypageChartDAO {
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<MypageChartDTO> read(int member_no) {
		return sqlSession.selectList("mypageChartMapper.read",member_no);
	}

	@Override
	public void add(MypageChartDTO mypageChartDTO) {
		sqlSession.insert("mypageChartMapper.add",mypageChartDTO);
	}

	@Override
	public void mod(MypageChartDTO mypageChartDTO) {
		sqlSession.update("mypageChartMapper.mod",mypageChartDTO);

	}

}
