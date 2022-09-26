package eunbin.DAO;

import java.util.List;

import eunbin.DTO.MemberDTO;
import eunbin.DTO.ServiceDTO;

public interface ServiceDAO {

	// 게시물 불러오기
	// 특정 게시판 목록들 불러오기 - 전체
	public List<ServiceDTO> board_bno_All(String sv_type) throws Exception;
	
	// 특정 게시물 불러오기 - 번호 (bno)
	public ServiceDTO board_one(int bno) throws Exception;
	
	// 게시물 번호 가져오기 - 개별
	public int board_bno(ServiceDTO s_dto) throws Exception;
	
	// 게시물 작성/삭제/수정
	// 글 작성 - 최초
	public void board_write(ServiceDTO s_dto) throws Exception;
	
	// 글 삭제
	public void board_delete(int bno) throws Exception;
	
	// 글 수정
	public void board_fix(ServiceDTO s_dto) throws Exception;
	
}
