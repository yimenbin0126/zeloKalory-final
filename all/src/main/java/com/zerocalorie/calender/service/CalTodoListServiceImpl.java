package com.zerocalorie.calender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerocalorie.calender.dao.TodoListDAO;
import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.TodoListDTO;

@Service
public class CalTodoListServiceImpl implements CalTodoListService {

	@Autowired
	TodoListDAO todoListDAO;

	
	@Autowired
	CalenderService calenderService;
	
	//calTodoRead 달력에 월간 정보 조회 (JSON 아니였던 방식)
	public List<TodoListDTO> calTodoRead(HttpServletRequest request, Map pageDateInfo, CalPageMbDTO calPageMbDTO) {
		List<TodoListDTO> calTodolist = new ArrayList<TodoListDTO>();

		String dateMonth = "" + pageDateInfo.get("pageYear") + "-"
				+ (Integer.parseInt(pageDateInfo.get("pageMonth").toString()) + 1);
		TodoListDTO todoListDTO = new TodoListDTO();
		todoListDTO.setTdl_date(dateMonth); // 날짜세팅
		todoListDTO.setMember_no(calPageMbDTO.getMember_no()); // 페이지회원의id


		calTodolist = todoListDAO.readMonth(todoListDTO);
		for (int i = 0; i < calTodolist.size(); i++) {
			TodoListDTO vo = calTodolist.get(i);

		/*	System.out.println(" vo.tdl_no : "+ vo.getTdl_no() + 
								", vo.tdl_date : "+ vo.getTdl_date() +
								", vo.tdl_contents : "+ vo.getTdl_contents() +
								", vo.tdl_category : "+ vo.getTdl_category() +
								", vo.member_no : "+ vo.getMember_no() ); */
		}
		// System.out.println("TodoListlist 읽기 성공");
		return calTodolist;
	}
		
	//calTodoRead 달력에 월간 정보 조회
	public List<TodoListDTO> calTodoReadJSON(String pageYear, String pageMonth,String pageId) {
		
		CalPageMbDTO calPageMbDTO = calenderService.idToMbNo(pageId);
		
		List<TodoListDTO> calTodolist = new ArrayList<TodoListDTO>();

		String dateMonth = "" + pageYear + "-"
				+ (Integer.parseInt(pageMonth) + 1);
		TodoListDTO todoListDTO = new TodoListDTO();
		todoListDTO.setTdl_date(dateMonth); // 날짜세팅
		todoListDTO.setMember_no(calPageMbDTO.getMember_no()); // 페이지회원의id


		calTodolist = todoListDAO.readMonth(todoListDTO);
		for (int i = 0; i < calTodolist.size(); i++) {
			TodoListDTO vo = calTodolist.get(i);

		/*	System.out.println(" vo.tdl_no : "+ vo.getTdl_no() + 
								", vo.tdl_date : "+ vo.getTdl_date() +
								", vo.tdl_contents : "+ vo.getTdl_contents() +
								", vo.tdl_category : "+ vo.getTdl_category() +
								", vo.member_no : "+ vo.getMember_no() ); */
		}
		// System.out.println("TodoListlist 읽기 성공");
		return calTodolist;
	}
	
	// >>>>> todolist 조회
	@Override
	public List<TodoListDTO> TodoListRead(HttpServletRequest request, CalPageMbDTO calPageMbDTO, Map pageDateInfo) {

		List<TodoListDTO> TodoListlist = List.of();
		String tdl_date = request.getParameter("tdl_date");
		if (tdl_date == null) {
			tdl_date = "" + pageDateInfo.get("pageYear") + "-"
						+ (Integer.parseInt(pageDateInfo.get("pageMonth").toString()) + 1) + "-" + pageDateInfo.get("pageDate");
		}
			
		// 클릭한 달력 날짜가 있으면
		TodoListDTO todoListDTO = new TodoListDTO();
		todoListDTO.setTdl_date(tdl_date); // 날짜
		todoListDTO.setMember_no(calPageMbDTO.getMember_no()); // 페이지회원의id
		
		TodoListlist = todoListDAO.read(todoListDTO);

		for (int i = 0; i < TodoListlist.size(); i++) {
			TodoListDTO vo = TodoListlist.get(i);
		
				/* System.out.println(" vo.tdl_no : " + vo.getTdl_no() + ", vo.tdl_date : " + vo.getTdl_date()
						+ ", vo.tdl_contents : " + vo.getTdl_contents() + ", vo.tdl_category : " + vo.getTdl_category()
						+ ", vo.member_no : " + vo.getMember_no());*/
		}
			//System.out.println("TodoListlist 읽기 성공");
		return TodoListlist;
	}
	
	// todoList 추가
	@Override
	public void todoListAdd(HttpServletRequest request, String pageId) {
		try {
			TodoListDTO vo = new TodoListDTO();
			
			int pageYear = Integer.parseInt(request.getParameter("pageYear"));
			int pageMonth =  Integer.parseInt(request.getParameter("pageMonth"))+1;
			int pageDate = Integer.parseInt(request.getParameter("pageDate"));
	
			String tdl_date = pageYear+"-"+pageMonth+"-"+pageDate;
			vo.setTdl_contents( request.getParameter("tdl_contents") );
			vo.setTdl_category(request.getParameter("tdl_category") );
			vo.setTdl_date(tdl_date);
			vo.setMember_no(calenderService.idToMbNo(pageId).getMember_no());
			
			System.out.println("tdl_date : " + vo.getTdl_date());
			System.out.println("tdl_contents : " + vo.getTdl_contents());
			System.out.println("tdl_category : " + vo.getTdl_category());
			todoListDAO.add(vo);
		}catch(Exception e) {e.printStackTrace();}
	}
		
		// todoList 삭제
		@Override
		public void todoListDel(HttpServletRequest request) {
			TodoListDTO vo = new TodoListDTO();
			vo.setTdl_no( Integer.parseInt(request.getParameter("tdl_no") ));
			todoListDAO.del(vo);
		}

		// todoList 수정
		@Override
		public void todoListMod(HttpServletRequest request) {
			TodoListDTO vo = new TodoListDTO();
			vo.setTdl_no( Integer.parseInt(request.getParameter("tdl_no") ));
			vo.setTdl_category( (request.getParameter("tdl_category") ));
			vo.setTdl_contents( (request.getParameter("tdl_contents") ));
			todoListDAO.mod(vo);
		}
}
