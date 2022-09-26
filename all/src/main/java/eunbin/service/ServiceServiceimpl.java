package eunbin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import eunbin.DAO.ServiceDAO;
import eunbin.DTO.ServiceDTO;

public class ServiceServiceimpl implements ServiceService {

	@Autowired
	private ServiceDTO s_dto;
	private ServiceDAO s_dao;
	
	// 게시물 불러오기
	// 특정 게시판 목록들 불러오기 - 전체
	public List<ServiceDTO> board_bno_All(String sv_type) throws Exception {
		return s_dao.board_bno_All(sv_type);
	}
	
	// 특정 게시물 불러오기 - 번호 (bno)
	public ServiceDTO board_one(int bno) throws Exception {
		return s_dao.board_one(bno);
	}
	
	// 게시물 번호 가져오기 - 개별
	public int board_bno(ServiceDTO s_dto) throws Exception {
		return s_dao.board_bno(s_dto);
	}
	
	// 게시물 작성/삭제/수정
	// 글 작성 - 최초
	public void board_write(ServiceDTO s_dto) throws Exception {
		s_dao.board_write(s_dto);
	}
	
	// 글 삭제
	public void board_delete(int bno) throws Exception {
		s_dao.board_delete(bno);
	}
	
	// 글 수정
	public void board_fix(ServiceDTO s_dto) throws Exception {
		s_dao.board_fix(s_dto);
	}
	
	// 관리자 여부 반환 (admin == 관리자 아이디)
    public String board_admin_type(String id) throws Exception {
    	System.out.println("ServiceDAO - board_admin_type - 관리자 여부 반환 시작");
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
}