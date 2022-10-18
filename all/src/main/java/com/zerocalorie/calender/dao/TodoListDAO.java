package com.zerocalorie.calender.dao;

import java.util.List;

import com.zerocalorie.calender.dto.TodoListDTO;

public interface TodoListDAO {
	public List<TodoListDTO> readMonth (TodoListDTO todoListDTO );
	
	public List<TodoListDTO> read(TodoListDTO todoListDTO);

	public void add(TodoListDTO todoListDTO);
	
	public void del(TodoListDTO todoListDTO);
	
	public void mod(TodoListDTO todoListDTO);
}
