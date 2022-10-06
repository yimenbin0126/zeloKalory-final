package eunbin.DAO;

import java.util.List;

import eunbin.DTO.e_ServiceDTO;
import eunbin.DTO.e_SvFileDTO;
import eunbin.DTO.e_SvLikecheckDTO;
import eunbin.DTO.e_SvPagingView;
import eunbin.DTO.e_SvViewcheckDTO;

public interface e_ServiceDAO {

	// 게시물 불러오기
	// 특정 게시판 목록들 불러오기 - 전체
	public List<e_ServiceDTO> board_bno_All(String sv_type) throws Exception;
	
	// 타입별 게시물 갯수 불러오기 (sv_type)
	public int board_count_All(e_ServiceDTO s_dto) throws Exception;
	
	// 특정 게시물 묶음 불러오기 - 페이징
	public List<e_ServiceDTO> board_paging(e_SvPagingView s_paging) throws Exception;
	
	// 특정 게시물 불러오기 - 번호 (bno)
	public e_ServiceDTO board_one(int bno) throws Exception;
	
	// 게시물 번호 가져오기 - 개별
	public int board_bno(e_ServiceDTO s_dto) throws Exception;
	
	// 게시물 작성/삭제/수정
	// 게시물 번호 시퀀스 생성
	public int board_write_bno() throws Exception;
	
	// 글 작성 - 최초
	public void board_write(e_ServiceDTO s_dto) throws Exception;
	
	// 글 작성 - 첨부파일
	public void board_write_file(e_SvFileDTO s_filedto) throws Exception;
	
	// 첨부파일 전부 불러오기
	public List<e_SvFileDTO> board_load_file(e_ServiceDTO s_dto) throws Exception;
	
	// 첨부파일 부분 불러오기
	public e_SvFileDTO board_load_file_one(e_SvFileDTO s_filedto) throws Exception;
	
	// 첨부파일 부분 삭제 : 게시물과 연관된
	public void board_delete_file_one(e_SvFileDTO s_filedto) throws Exception;
	
	// 글 삭제
	public void board_delete(e_ServiceDTO s_dto) throws Exception;
	
	// 글 수정
	public void board_fix(e_ServiceDTO s_dto) throws Exception;
	
	// 부수적인 기능 (조회수, 좋아요)
	// 조회수 증가
	public void board_viewUp(e_ServiceDTO s_dto) throws Exception;
	
	// 조회수 증가 가능 여부 체크
	public boolean board_viewCheck(e_SvViewcheckDTO s_viewCheck) throws Exception;
	 
	// ip로 접속했을 경우 (ip가 중복되지 않을 시 증가)
	public boolean board_Ipcheck(e_SvViewcheckDTO s_viewCheck) throws Exception;
	
	// 좋아요 증가
	public void board_like_up(e_SvLikecheckDTO s_likeCheck) throws Exception;
	
	// 좋아요 감소
	public void board_like_down(e_SvLikecheckDTO s_likeCheck) throws Exception;
	
	// 좋아요 불러오기
	public e_SvLikecheckDTO board_like_load(e_SvLikecheckDTO s_likeCheck) throws Exception;
}
