package com.zerocalorie.calender.dao;

import java.util.List;

import com.zerocalorie.calender.dto.CalSearchMbDTO;

public interface CalSearchMbDAO {
	
	public List<CalSearchMbDTO> searchMembers(String new_id);

}
