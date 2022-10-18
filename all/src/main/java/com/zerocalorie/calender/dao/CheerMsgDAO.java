package com.zerocalorie.calender.dao;

import java.util.List;

import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.CheerMsgDTO;

public interface CheerMsgDAO {
	
	public List<CheerMsgDTO> read(CalPageMbDTO calPageMbDTO);
	
	public void add(CheerMsgDTO cheerMsgDTO);
	
	public void empty(CheerMsgDTO cheerMsgDTO);
	
	public void del(CheerMsgDTO cheerMsgDTO);

}
