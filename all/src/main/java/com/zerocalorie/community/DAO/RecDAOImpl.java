package com.zerocalorie.community.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zerocalorie.community.DTO.RecDTO;

@Repository
public class RecDAOImpl implements RecDAO {
	
	@Autowired
	private SqlSession sql;
	
	@Override
	public RecDTO getRecCount(RecDTO dto) {

		RecDTO resultDto = sql.selectOne("mapper.rec.getRecCount", dto);
		
		return resultDto;
	}

	@Override
	public List<RecDTO> getRecTop5() {
		
		List<RecDTO> list = sql.selectList("mapper.rec.getRecTop5");
		
		return list;
	}

	@Override
	public int recommend(RecDTO recDTO) {
		int i = 0;
		i = sql.insert("mapper.rec.setRecommend", recDTO);
		return i;
	}

}
