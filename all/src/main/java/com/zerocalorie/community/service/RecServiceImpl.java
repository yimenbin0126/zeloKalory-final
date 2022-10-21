package com.zerocalorie.community.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerocalorie.community.DAO.RecDAO;
import com.zerocalorie.community.DTO.RecDTO;

@Service
public class RecServiceImpl implements RecService {
	
	private static final Logger logger = LoggerFactory.getLogger(RecServiceImpl.class);
	
	@Autowired
	private RecDAO dao;
	
	@Override
	public RecDTO getRecCount(RecDTO dto) {
		return dao.getRecCount(dto);
	}

	@Override
	public List<RecDTO> getRecTop5() {
		return dao.getRecTop5();
	}

	@Override
	public int recommend(RecDTO recDTO) {
		int rec = 0;
		
		// 추천했는지 확인
		RecDTO dto = new RecDTO();
		dto = dao.getRecCount(recDTO);
		int count = dto.getReccount();
		if (count > 0 ) {
			return 0;
		} else {
			rec = dao.recommend(recDTO);			
			return rec;
		}
	}

}
