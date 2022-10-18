package com.zerocalorie.calender.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zerocalorie.calender.dto.TodoListDTO;

@Repository
public class TodoListDAOImpl implements TodoListDAO {
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<TodoListDTO> readMonth(TodoListDTO todoListDTO) {
		return sqlSession.selectList("todoListMapper.readMonth",todoListDTO);
	}

	@Override
	public List<TodoListDTO> read(TodoListDTO todoListDTO) {
		return sqlSession.selectList("todoListMapper.read",todoListDTO);
	}

	@Override
	public void add(TodoListDTO todoListDTO) {
		sqlSession.insert("todoListMapper.add",todoListDTO);
	}

	@Override
	public void del(TodoListDTO todoListDTO) {
		sqlSession.delete("todoListMapper.del",todoListDTO);
	}

	@Override
	public void mod(TodoListDTO todoListDTO) {
		sqlSession.update("todoListMapper.mod",todoListDTO);
	}

}
