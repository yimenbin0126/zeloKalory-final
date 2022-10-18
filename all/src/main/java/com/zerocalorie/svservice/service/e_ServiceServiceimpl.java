package com.zerocalorie.svservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerocalorie.svservice.dao.e_ServiceDAO;
import com.zerocalorie.svservice.dto.e_ServiceDTO;
import com.zerocalorie.svservice.dto.e_SvCommentDTO;
import com.zerocalorie.svservice.dto.e_SvFileDTO;
import com.zerocalorie.svservice.dto.e_SvLikecheckDTO;
import com.zerocalorie.svservice.dto.e_SvPagingViewDTO;
import com.zerocalorie.svservice.dto.e_SvSearchDTO;
import com.zerocalorie.svservice.dto.e_SvViewcheckDTO;

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
	
	// 타입별 게시물 갯수 불러오기 (sv_type)
	public int board_count_All(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_count_All - 타입별 게시물 갯수 불러오기 (sv_type)");
		return s_dao.board_count_All(s_dto);
	}
	
	// 타입별 게시물 갯수 불러오기 (sv_type) - 내 게시물만
	public int myboard_count_All(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceServiceimpl - myboard_count_All - 타입별 게시물 갯수 불러오기 (sv_type) - 내 게시물만");
		return s_dao.myboard_count_All(s_dto);
	}
	
	// 특정 게시물 묶음 불러오기 - 페이징
	public List<e_ServiceDTO> board_paging(e_SvPagingViewDTO s_paging) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_paging - 특정 게시물 묶음 불러오기 - 페이징");
		return s_dao.board_paging(s_paging);
	}
	
	// 게시물 원글+답글 묶음 불러오기 - 페이징
	public List<e_ServiceDTO> board_paging_origin_reply(e_SvPagingViewDTO s_paging) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_paging_origin_reply - 게시물 원글+답글 묶음 불러오기 - 페이징");
		return s_dao.board_paging_origin_reply(s_paging);
	}
	
	// 게시물 원글+답글 묶음 불러오기 - 페이징 - 내 게시물만
	public List<e_ServiceDTO> myboard_paging_origin_reply(e_SvPagingViewDTO s_paging) throws Exception {
		System.out.println("e_ServiceServiceimpl - myboard_paging_origin_reply - 게시물 원글+답글 묶음 불러오기 - 페이징 - 내 게시물만");
		return s_dao.myboard_paging_origin_reply(s_paging);
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
	// 게시물 번호 시퀀스 생성
	public int board_write_bno() throws Exception {
		System.out.println("e_ServiceServiceimpl - board_write_bno - 게시물 번호 시퀀스 생성");
		return s_dao.board_write_bno();
	}
	
	// 답글 작성
	public void board_reply(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_reply - 답글 작성");
		s_dao.board_reply(s_dto);
	}
	
	// 글 작성 - 최초
	public void board_write(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_write - 글 작성 - 최초");
		s_dao.board_write(s_dto);
	}
	
	// 글 작성 - 첨부파일
	public void board_write_file(e_SvFileDTO s_filedto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_write_file - 글 작성 - 첨부파일");
		s_dao.board_write_file(s_filedto);
	}
	
	// 첨부파일 전부 불러오기
	public List<e_SvFileDTO> board_load_file(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_load_file - 첨부파일 전부 불러오기");
		return s_dao.board_load_file(s_dto);
	}
	
	// 첨부파일 부분 불러오기
	public e_SvFileDTO board_load_file_one(e_SvFileDTO s_filedto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_load_file_one - 첨부파일 부분 불러오기");
		return s_dao.board_load_file_one(s_filedto);
	}
	
	// 첨부파일 부분 삭제 : 게시물과 연관된
	public void board_delete_file_one(e_SvFileDTO s_filedto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_delete_file_one - 첨부파일 부분 삭제 : 게시물과 연관된");
		s_dao.board_delete_file_one(s_filedto);
	}
	
	// 여러 글 삭제
	public void board_deleteAll(List<String> e_bno_list) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_deleteAll - 여러 글 삭제");
		s_dao.board_deleteAll(e_bno_list);
	}
	
	// 글 삭제
	public void board_delete(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_delete - 글 삭제");
		s_dao.board_delete(s_dto);
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
    		System.out.println("e_ServiceServiceimpl - board_admin_type - 관리자 여부 반환 끝");
    		return "Y";
    	} else {
    		System.out.println("관리자 여부 : 회원");
    		System.out.println("e_ServiceServiceimpl - board_admin_type - 관리자 여부 반환 끝");
    		return "N";
    	}
    }
    
    // 부수적인 기능 (조회수, 좋아요)
 	// 조회수 증가
 	public void board_viewUp(e_ServiceDTO s_dto) throws Exception {
 		System.out.println("e_ServiceServiceimpl - board_viewUp - 조회수 증가");
 		s_dao.board_viewUp(s_dto);
 	}
 	
 	// 조회수 증가 가능 여부 체크
 	public boolean board_viewCheck(e_SvViewcheckDTO s_viewCheck) throws Exception {
 		System.out.println("e_ServiceServiceimpl - board_viewCheck - 조회수 증가 가능 여부 체크");
 		return s_dao.board_viewCheck(s_viewCheck);
 	}
 	
 	// ip로 접속했을 경우 (ip가 중복되지 않을 시 증가)
 	public boolean board_Ipcheck(e_SvViewcheckDTO s_viewCheck) throws Exception {
 		System.out.println("e_ServiceServiceimpl - board_Ipcheck - ip로 접속했을 경우 (ip가 중복되지 않을 시 증가)");
 		return s_dao.board_Ipcheck(s_viewCheck);
 	}
 	
 	// 좋아요 증가
 	public void board_like_up(e_SvLikecheckDTO s_likeCheck) throws Exception {
 		System.out.println("e_ServiceServiceimpl - board_like_up - 좋아요 증가");
 		s_dao.board_like_up(s_likeCheck);
 	}
 	
 	// 좋아요 감소
 	public void board_like_down(e_SvLikecheckDTO s_likeCheck) throws Exception {
 		System.out.println("e_ServiceServiceimpl - board_like_down - 좋아요 감소");
 		s_dao.board_like_down(s_likeCheck);
 	}
 	
 	// 좋아요 불러오기
  	public e_SvLikecheckDTO board_like_load(e_SvLikecheckDTO s_likeCheck) throws Exception {
  		System.out.println("e_ServiceServiceimpl - board_like_load - 좋아요 불러오기");
  		return s_dao.board_like_load(s_likeCheck);
  	}
  	
  	// 싫어요 증가
 	public void board_dislike_up(e_SvLikecheckDTO s_likeCheck) throws Exception {
 		System.out.println("e_ServiceServiceimpl - board_dislike_up - 싫어요 증가");
  		s_dao.board_dislike_up(s_likeCheck);
 	}
 	
 	// 싫어요 감소
	public void board_dislike_down(e_SvLikecheckDTO s_likeCheck) throws Exception {
		System.out.println("e_ServiceServiceimpl - board_dislike_down - 싫어요 감소");
  		s_dao.board_dislike_down(s_likeCheck);
	}
  	
  	// 검색
 	// 검색한 게시물 갯수 가져오기
 	public int board_search_count_All(e_SvSearchDTO s_searchdto) throws Exception {
 		return s_dao.board_search_count_All(s_searchdto);
 	}
 	
 	// 검색한 게시물 전체 가져오기 : 페이징 적용
 	public List<e_ServiceDTO> board_search_All(e_SvSearchDTO s_searchdto) throws Exception {
 		return s_dao.board_search_All(s_searchdto);
 	}
 	
 	// 검색한 게시물 갯수 가져오기 - myboard
 	public int myboard_search_count_All(e_SvSearchDTO s_searchdto) throws Exception {
 		return s_dao.myboard_search_count_All(s_searchdto);
 	}
 	
 	// 검색한 게시물 전체 가져오기 : 페이징 적용 - myboard
 	public List<e_ServiceDTO> myboard_search_All(e_SvSearchDTO s_searchdto) throws Exception {
 		return s_dao.myboard_search_All(s_searchdto);
 	}
 	
 	// 댓글
 	// 댓글 갯수 불러오기
   	public int comment_count_load_All(e_ServiceDTO s_dto) throws Exception {
   		return s_dao.comment_count_load_All(s_dto);
   	}
 	
 	// 댓글 시퀀스 가져오기
  	public int comment_c_code() throws Exception {
  		return s_dao.comment_c_code();
  	}
 	
   	// 댓글 전부 가져오기
   	public List<e_SvCommentDTO> comment_load_All(e_SvCommentDTO s_commentDTO) throws Exception {
   		return s_dao.comment_load_All(s_commentDTO);
   	}
   	
   	// 댓글 작성
	public void comment_insert(e_SvCommentDTO s_commentDTO) throws Exception {
		s_dao.comment_insert(s_commentDTO);
	}
	
	// 댓글 수정
	public void comment_update(e_SvCommentDTO s_commentDTO) throws Exception {
		s_dao.comment_update(s_commentDTO);
	}
	
	// 댓글 삭제
	public void comment_delete(e_SvCommentDTO s_commentDTO) throws Exception {
		s_dao.comment_delete(s_commentDTO);
	}
}