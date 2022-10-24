package com.zerocalorie.calender.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerocalorie.calender.dao.CheerMsgDAO;
import com.zerocalorie.calender.dao.ChrPagingDAO;
import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.CheerMsgDTO;
import com.zerocalorie.member.dto.e_MemberDTO;

@Service
public class CalCheerMsgServiceImpl implements CalCheerMsgService {
	
	@Autowired
	ChrPagingDAO chrPagingDAO;

	@Autowired
	CheerMsgDAO cheerMsgDAO;
	
	@Autowired
	CalenderService calenderService;

	// 응원 msg db에 추가
	@Override
	public void cheerMsgAdd(HttpServletRequest request, String pageId) {
		try {
			
			System.out.println("cheerMsgAdd 요청");
			
			// 주소에서 pageid를 자겨옴
			//String pageId = findId(request);
			
			// 페이지 id를 이용해서 회원정보 테이블에서 회원정보no, 닉넴 가져옴
			CalPageMbDTO calPageMbDTO = calenderService.idToMbNo(pageId);
			
			// 세션에서 접속자의 member_no()를 가져옴
			e_MemberDTO sessionUserDTO = (e_MemberDTO) request.getSession().getAttribute("user");

			CheerMsgDTO vo = new CheerMsgDTO();
			vo.setChr_msg(request.getParameter("CHR_MSG"));
			vo.setFr_member_no(sessionUserDTO.getMember_no());
		    vo.setTo_member_no(calPageMbDTO.getMember_no());
		    vo.setChr_parents_no(Integer.parseInt(request.getParameter("CHR_PARENTS_NO")));
		    System.out.println(request.getParameter("CHR_MSG"));
		    cheerMsgDAO.add(vo);
		    System.out.println("cheerMsgAdd 성공");
		}catch(Exception e) {e.printStackTrace();}
	}
	
	//TODO
	// 응원 msg db에서 삭제
	@Override
	public void cheerMsgDel(HttpServletRequest request, List<Integer> pCHR_NOList) {
		try {
			System.out.println("cheerMsgdel 요청");
			CheerMsgDTO vo = new CheerMsgDTO();
			vo.setChr_no(Integer.parseInt(request.getParameter("CHR_NO")));
	        cheerMsgDAO.del(vo);
	        System.out.println("cheerMsgdel 성공");
		
        
        
	        /////// 부모댓글도 지울 기능 넣을지 말지 고민되서 따로 넣음
	        if(pCHR_NOList !=null) {
	        	
	    		for(int CHR_NO : pCHR_NOList) {
	    			System.out.println("삭제할 부모 CHR_NO : "+CHR_NO);
	    			vo.setChr_no(CHR_NO);
	    			
	    			cheerMsgDAO.del(vo);
	    		}
	        }
	        ///////////////////////////////////////////////////////////
		}catch(Exception e) {e.printStackTrace();}
	}
	
	// 응원 msg db에서 삭제 >> cheerMsgDelnMod 했음
	@Override
	public void cheerMsgDelnMod(HttpServletRequest request, String pageId){
		
		// 페이지 id를 이용해서 회원정보 테이블에서 회원정보no, 닉넴 가져옴
		CalPageMbDTO calPageMbDTO = calenderService.idToMbNo(pageId);
		
		// 세션에서 접속자의 member_no()를 가져옴
		e_MemberDTO sessionUserDTO = (e_MemberDTO) request.getSession().getAttribute("user");
		
		// 페이징 db 한바퀴 돌아서 전부가져옴
		List<CheerMsgDTO> cheerMsglist = chrPagingDAO.selectPagingList( calPageMbDTO , 0, chrPagingDAO.selectListCount(calPageMbDTO));

		CheerMsgDTO cheerMsgDTO = new CheerMsgDTO();

		ArrayList<Integer> pCHR_NOList = new ArrayList<Integer>() ;
		
		int nowDepth = 1;
		int nextDepth = 1;	// 다음 댓글의 자식 유무 판단
		for(int i =0; i<cheerMsglist.size(); i++) {
			cheerMsgDTO = (CheerMsgDTO)cheerMsglist.get(i);
			
			// 받아온 chr_no의 i를 찾음
			if(cheerMsgDTO.getChr_no()==Integer.parseInt(request.getParameter("CHR_NO"))){
				
				// 현재 depth를 저장
				nowDepth = cheerMsgDTO.getDepth();
				
				// 다음 댓글의 번호가 1이면 자식(대댓글)이 없고, 2 이면 있는것
				if (i< cheerMsglist.size()-2) {
					
					CheerMsgDTO nextVO = new CheerMsgDTO();
            		nextVO = (CheerMsgDTO)cheerMsglist.get(i+1);
            		nextDepth = nextVO.getDepth();
            		
            	// 다음 댓글이 없으면 자식이 없으므로 지워져도 되니까 다음댓글을 1로 줌
				}else {
					nextDepth = 1;
				}
				
				// 지금 댓글의 원글 갯수와 정보를 보냄(parentsDapth)
				// 레벨 은 1부터 시작, 현재 내 레벨 빼기 때문에 -1
				// for 내 dapth-1 만큼 돌림 	
				Loop1:
				for( int pDapth = 1; pDapth<cheerMsgDTO.getDepth(); pDapth++){
					
					// if 이전 댓글이 null 인지? 맞으면 (같이 지울 삭제된 댓글 있는지?)
					if(cheerMsglist.get(i-pDapth).getChr_msg() == null){
						
						// if depth가 내 depth -1인가  맞으면 (근데 그게 내 부모댓글 인지?)
						if(cheerMsglist.get(i-pDapth).getDepth() == cheerMsgDTO.getDepth()-pDapth){
							// chr_no 저장
							pCHR_NOList.add(cheerMsglist.get(i-pDapth).getChr_no());
							
						}else{// else  : 돌필요 없음
							// for문 끝냄
							break Loop1;
						}	
					}
					else{ // else : 돌필요 없음
						// for문 끝냄
						break;
					}
				 }
			}
		} 
		
		
		// 대댓글이 없는 글이라면 삭제 (다음 댓글의 Depth가 현재 Depth 보다 작거나 같으면)
		// 하고 위에 부모댓글도 내용이 없으면 삭제
		if(nextDepth<=nowDepth) {
			System.out.println("nextDepth : "+nextDepth);
			cheerMsgDel(request, pCHR_NOList); 
			
		// 대댓글이 있다면 내용만 비움(삭제된 메세지입니다로 표시하게됨)
		}else {
			System.out.println("nextDepth : "+nextDepth);
			cheerMsgEmpty(request);
		}
	}
	
	// 응원 msg db에서 메세지만 비움 >> cheerMsgDelnMod 했음
	@Override
	public void cheerMsgEmpty(HttpServletRequest request) {
		try {
			System.out.println("cheerMsgEmpty 요청");
			CheerMsgDTO vo = new CheerMsgDTO();
			vo.setChr_no(Integer.parseInt(request.getParameter("CHR_NO")));
	        cheerMsgDAO.empty(vo);
	        System.out.println("cheerMsgEmpty 성공");
		}catch(Exception e) {e.printStackTrace();}
	}

	
	// >>> 페이징 관련
	@Override
	public Map paging(HttpServletRequest request, HttpServletResponse response, CalPageMbDTO calPageMbDTO) {
		
		int[] countPerPageArr = {2,3,4,5}; // 한 페이지당 보여줄 글 개수
		
		// 기본값
		int pageNum = 1; // 현재페이지
		int countPerPage = 3; // 페이지당 보여줄 개수 배열 주소
		
		String str_pageNum = request.getParameter("pageNum");
		String str_countPerPage = request.getParameter("countPerPage");
		
	
//		if (str_pageNum != null) {
//			pageNum = Integer.parseInt(str_pageNum);
//		}
//		if (str_countPerPage != null) {
//			countPerPage = Integer.parseInt(str_countPerPage);
//		}
		
		try {
			pageNum = Integer.parseInt(str_pageNum);
		} catch (NumberFormatException nfe) {}
		
		try {
			// 변환한 str_countPerPage 의 값이 배열 길이 안에 있으면 저장해줌
			if(Integer.parseInt(str_countPerPage) < countPerPageArr.length) {
				countPerPage = Integer.parseInt(str_countPerPage);
			}
		} catch (NumberFormatException nfe) {}

		
		Map chrPagingMap = getPagingList(calPageMbDTO, pageNum, countPerPage, countPerPageArr);
		chrPagingMap.put("pageNum", pageNum);
		chrPagingMap.put("countPerPage", countPerPage);
		chrPagingMap.put("countPerPageArr", countPerPageArr);
		chrPagingMap.put("uri", request.getRequestURI());

		return chrPagingMap;
	}
	
	@Override
	public Map getPagingList(CalPageMbDTO calPageMbDTO, int pageNum, int countPage, int[] countPerPageArr){
		
		countPage = countPerPageArr[countPage];
		
		System.out.println("countPage : "+countPage);
		
		int start=0, end=0;
		
		// 1,5
		// start = 1, end = 5
		// 2,5
		// start = 6, end = 10
		// 3,5
		// start = 11, end = 15
		
		start = ((pageNum-1)*countPage) +1;
		//end = pageNum*countPage;
		end = start + countPage-1;

		// 해당 페이지의 목록만 조회
		List<CheerMsgDTO> list = chrPagingDAO.selectPagingList(calPageMbDTO, start, end);
		
		// 전체 글 개수 가져옴
		int count =  chrPagingDAO.selectListCount(calPageMbDTO);
		
		Map chrPagingMap = new HashMap();
		chrPagingMap.put("list", list);
		chrPagingMap.put("count", count);
		
		System.out.println("count : " + count);
		
		return chrPagingMap;
	}
}
