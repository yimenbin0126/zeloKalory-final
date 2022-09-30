package eunbin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eunbin.DAO.e_ServiceDAO;
import eunbin.DTO.e_ServiceDTO;
import eunbin.DTO.e_SvViewcheckDTO;

@Service
public class e_ServiceServiceimpl implements e_ServiceService {

	@Autowired
	private e_ServiceDAO s_dao;
	
	// 게시물 불러오기
	// 특정 게시판 목록들 불러오기 - 전체
	public List<e_ServiceDTO> board_bno_All(String sv_type) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_bno_All - 특정 게시판 목록들 불러오기 - 전체");
		return s_dao.board_bno_All(sv_type);
	}
	
	// 특정 게시물 불러오기 - 번호 (bno)
	public e_ServiceDTO board_one(int bno) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_one - 특정 게시물 불러오기 - 번호 (bno)");
		return s_dao.board_one(bno);
	}
	
	// 게시물 번호 가져오기 - 개별
	public int board_bno(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_bno - 게시물 번호 가져오기 - 개별");
		return s_dao.board_bno(s_dto);
	}
	
	// 게시물 작성/삭제/수정
	// 글 작성 - 최초
	public void board_write(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_write - 글 작성 - 최초");
		s_dao.board_write(s_dto);
	}
	
	// 글 삭제
	public void board_delete(int bno) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_delete - 글 삭제");
		s_dao.board_delete(bno);
	}
	
	// 글 수정
	public void board_fix(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_fix - 글 수정");
		s_dao.board_fix(s_dto);
	}
	
	// 관리자 여부 반환 (admin == 관리자 아이디)
    public String board_admin_type(String id) throws Exception {
    	System.out.println("e_ServiceServiceimpl - board_admin_type - 관리자 여부 반환 시작");
    	if(id.equals("admin")) {
    		System.out.println("관리자 여부 : 관리자");
    		System.out.println("ServiceDAO - board_admin_type - 관리자 여부 반환 끝");
    		return "Y";
    	} else {
    		System.out.println("관리자 여부 : 회원");
    		System.out.println("ServiceDAO - board_admin_type - 관리자 여부 반환 끝");
    		return "N";
    	}
    }
    
    // 부수적인 기능 (조회수, 좋아요)
 	// 조회수 증가
 	public void board_viewUp(e_ServiceDTO s_dto) throws Exception {
 		s_dao.board_viewUp(s_dto);
 	}
 	
 	// 조회수 증가 가능 여부 체크
 	public boolean board_viewCheck(e_SvViewcheckDTO s_viewCheck) throws Exception {
 		return s_dao.board_viewCheck(s_viewCheck);
 	}
 	
 	// ip로 접속했을 경우 (ip가 중복되지 않을 시 증가)
 	public boolean board_Ipcheck(e_SvViewcheckDTO s_viewCheck) throws Exception {
 		return s_dao.board_Ipcheck(s_viewCheck);
 	}
}