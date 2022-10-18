package com.zerocalorie.calender.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.CalSearchMbDTO;
import com.zerocalorie.calender.dto.TodoListDTO;
import com.zerocalorie.calender.service.CalenderService;
import com.zerocalorie.member.dto.e_MemberDTO;



@RequestMapping("/cal/*")
@Controller
public class CalenderController {

	@Autowired
	CalenderService calenderService;

	@RequestMapping("/*")
	public ModelAndView calenderController(HttpServletRequest request, HttpServletResponse response
	// , @ModelAttribute CalPageMbDTO calPageMbDTO
			, @RequestParam(value = "command", required = false) String command) {

		CalPageMbDTO calPageMbDTO;
		e_MemberDTO sessionUserDTO;
		boolean mypage; // 내 페이지 인지 확인하는 변수(세션id=페이지id)
		int calPageMbNo; // 페이지 멤버no
		String calPageMbNickName; // 페이지 멤버닉넴
		String pageId; // 페이지 id

		Map pageDateInfo = new HashMap(); // 페이지 날짜정보 map

		// 세션에서 접속자의 member_no()를 가져옴
		sessionUserDTO = (e_MemberDTO) request.getSession().getAttribute("user");

		System.out.println("sessionUserDTO 되냐: "+sessionUserDTO);

		// 세션 정보가 없으면 (login 안했으면) login창으로 보냄
		if (sessionUserDTO == null) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("calender/sendLoginPage");
			//mav.setViewName("redirect:calender/sendLoginPage"); // string 일때
			return mav;
		}

		pageId = calenderService.findId(request);
		
		System.out.println("pageId 되냐: "+pageId);

		// 페이지 id를 이용해서 회원정보 테이블에서 회원정보no, 닉넴 가져옴
		calPageMbDTO = calenderService.idToMbNo(pageId);

		// 접속자가 본인 페이지 보는거면 true
		mypage = calenderService.checkMyPage(pageId, sessionUserDTO);

		// JSP(뷰)에서 가져온 pageYear, pageMonth 있는지확인 후 있으면 해당날짜 돌려주고, 없으면 오늘날짜 세팅
		pageDateInfo = calenderService.setPageDate(request);

		/////////////////////////////////////////////////////////////
		
		// command 값을 받아옴 (읽기, 추가 삭제 등이 들어오면 수행 (read, add, del))
		calenderService.reciveCommand(request, calPageMbDTO, sessionUserDTO );
		
		
		//////////////// 창 새로고침 되면서 기본적으로 조회되는 것들	
		// >>>>응원msg db에서 조회 
		//TODO : 응원메세지 조회 대신 페이징
//		//List<CheerMsgVO> cheerMsglist = cheerMsgRead (calPageMbVO);
		
		// 페이징
		Map chrPagingMap = calenderService.paging(request,  response, calPageMbDTO);
		
		// >>>>달력 db에서 조회 
		List<TodoListDTO> calTodolist = calenderService.calTodoRead(request,  pageDateInfo, calPageMbDTO);

		// >>>>todolist 조회 
		List<TodoListDTO> todoListlist = calenderService.TodoListRead(request, calPageMbDTO, pageDateInfo);
		
		// >>>> 회원 조회  
		List<CalSearchMbDTO> serchMemberlist = calenderService.searchUser(request);
				
		
		
		////////////////////////////////////////////////////////////////
		ModelAndView mav = new ModelAndView();

		mav.addObject("mypage", mypage);
		mav.addObject("pageDateInfo", pageDateInfo);
		mav.addObject("calPageMbDTO", calPageMbDTO);
		mav.addObject("chrPagingMap", chrPagingMap);
		
		mav.addObject("todoListlist", todoListlist);
		mav.addObject("calTodolist", calTodolist);
		mav.addObject("serchMemberlist", serchMemberlist);
		
		mav.setViewName("calender/cal");
		return mav;
	}
	@RequestMapping("/pro_img.do")
	void ProfileImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 경로 확보
		String file_repo = "C:\\zerokalory_file";
		// 파일명 확보
		String fileName = (String) request.getParameter("fileName");
		//파일에 대한 풀 경로(file full path) (윈도우라서 \\ 해줌)
		String downFile = file_repo +"\\"+ fileName;
		// 이러면 리눅스나 윈도우 상관없이 쓸수 있음
		// String downFile = file_repo +System.getProperty("file.separator")+ fileName;
		// String downFile = file_repo + File.separator+ fileName;
//		System.out.println("폴더 구분자 1: "+System.getProperty("file.separator"));
//		System.out.println("폴더 구분자 2: "+ File.separator);
		
		// 파일 그 자체
		File f = new File(downFile);
		
		// 파일을 읽어오기 위한 세팅
		FileInputStream in = new FileInputStream( f );
		// 브라우져가 cache를 사용하는방법
		// no-cache : cache를 사용하지 않는 방법
		response.setHeader("Cache-Control", "no-cache");
		// 지금 브라우져가 받은 내용이 파일이라는 것을 명시
		// 그 파일을 다운로드할대 초기값을 명시
		response.addHeader("Content-disposition", "attachment; fileName="+"file.txt");

		OutputStream out = response.getOutputStream();
		
		
		byte[] buf = new byte[1024 * 8]; //8kb
		while(true) {
			// 파일 읽기(buffer 크기만큼 읽어서 buffer에 저장)
			int count = in.read(buf);
			// 읽을 내용이 없으면 -1을 반환....
			if(count == -1) {
				break;
			}
			out.write(buf, 0, count);
		}
		
		in.close();
		out.close();

	}

}
