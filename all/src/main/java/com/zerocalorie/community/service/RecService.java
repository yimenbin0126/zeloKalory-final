package com.zerocalorie.community.service;

import java.util.List;

import com.zerocalorie.community.DTO.RecDTO;

public interface RecService {

	public RecDTO getRecCount(RecDTO dto);
	
	public List<RecDTO> getRecTop5();
	
	public int recommend(RecDTO recDTO);
	
}
