package com.zerocalorie.calender.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.TodoListDTO;

public interface CalTodoListService {
	
	// >> todolist
	
	// 달력 json용 todolist 조회
	public List<TodoListDTO> calTodoReadJSON(String pageYear, String pageMonth,String pageId);
	
	// 달력 todolist 조회
	public List<TodoListDTO> calTodoRead(HttpServletRequest request, Map pageDateInfo, CalPageMbDTO calPageMbDTO);
	
	// todolist 조회
	public List<TodoListDTO> TodoListRead(HttpServletRequest request, CalPageMbDTO calPageMbDTO, Map pageDateInfo);
	
	// todoList 추가 
	public void todoListAdd(HttpServletRequest request, String pageId);
	
	// todoList 삭제 
	public void todoListDel(HttpServletRequest request);
	
	// todoList 수정
	public void todoListMod(HttpServletRequest request);

}
