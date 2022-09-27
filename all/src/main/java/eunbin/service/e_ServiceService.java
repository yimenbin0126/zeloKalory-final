package eunbin.service;

import java.util.List;

import eunbin.DTO.e_ServiceDTO;

public interface e_ServiceService {

	// 게시물 불러오기
	// 특정 게시판 목록들 불러오기 - 전체
	public List<e_ServiceDTO> board_bno_All(String sv_type) throws Exception;
	
	// 특정 게시물 불러오기 - 번호 (bno)
	public e_ServiceDTO board_one(int bno) throws Exception;
	
	// 게시물 번호 가져오기 - 개별
	public int board_bno(e_ServiceDTO s_dto) throws Exception;
	
	// 게시물 작성/삭제/수정
	// 글 작성 - 최초
	public void board_write(e_ServiceDTO s_dto) throws Exception;
	
	// 글 삭제
	public void board_delete(int bno) throws Exception;
	
	// 글 수정
	public void board_fix(e_ServiceDTO s_dto) throws Exception;
	
	// 관리자 여부 반환 (admin == 관리자 아이디)
	public String board_admin_type(String id) throws Exception;
}
