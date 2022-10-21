package com.zerocalorie.community.DAO;

import java.util.List;

import com.zerocalorie.community.DTO.RecDTO;

public interface RecDAO {
	
	public RecDTO getRecCount(RecDTO dto);
	
	public List<RecDTO> getRecTop5();
	
	public int recommend(RecDTO recDTo);

}
