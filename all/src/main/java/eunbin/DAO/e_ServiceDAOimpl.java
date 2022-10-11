package eunbin.DAO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import eunbin.DTO.e_ServiceDTO;
import eunbin.DTO.e_SvFileDTO;
import eunbin.DTO.e_SvLikecheckDTO;
import eunbin.DTO.e_SvPagingViewDTO;
import eunbin.DTO.e_SvSearchDTO;
import eunbin.DTO.e_SvViewcheckDTO;

@Repository
public class e_ServiceDAOimpl implements e_ServiceDAO {

	@Autowired
	private SqlSession sql;
	
	// 특정 게시판 목록들 불러오기 - 전체
	public List<e_ServiceDTO> board_bno_All(String sv_type) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_bno_All - 특정 게시판 목록들 불러오기 - 전체");
		// 타입별 게시물 번호들 불러오기 (sv_type)
		// 게시물 번호들 얻기 - 새 객체에 타입 저장
		e_ServiceDTO s_dto = new e_ServiceDTO();
		s_dto.setSv_type(sv_type);
		List bno_list = new ArrayList(); 
		bno_list = sql.selectList("serviceMapper.board_bno_All", s_dto);
		// 게시물 번호 내림차순 정렬
		bno_list.sort(Comparator.reverseOrder());
		// 번호들로 게시물 얻기
		List<e_ServiceDTO> s_dtoList = new ArrayList();
		for(int i=0; i<bno_list.size(); i++) {
			// 리스트에 저장할 객체 생성
			e_ServiceDTO s_dto_new = new e_ServiceDTO();
			// 객체에 게시물 번호 삽입
			s_dto_new.setBno(Integer.valueOf(String.valueOf(bno_list.get(i))));
			// 게시물 번호가 저장된 객체로 원본 게시물 불러오기
			s_dto_new = sql.selectOne("serviceMapper.board_one", s_dto_new);
			// 해당 게시물을 리스트에 저장
			s_dtoList.add(s_dto_new);
		}
		return s_dtoList;
	}
	
	// 타입별 게시물 갯수 불러오기 (sv_type)
	public int board_count_All(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_count_All - 타입별 게시물 갯수 불러오기 (sv_type)");
		return sql.selectOne("serviceMapper.board_count_All", s_dto);
	}
	
	// 특정 게시물 묶음 불러오기 - 페이징
	public List<e_ServiceDTO> board_paging(e_SvPagingViewDTO s_paging) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_paging - 특정 게시물 묶음 불러오기 - 페이징");
		return sql.selectList("serviceMapper.board_paging", s_paging);
	}
	
	// 특정 게시물 불러오기 - 번호 (bno)
	public e_ServiceDTO board_one(int bno) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_one - 특정 게시물 불러오기 - 번호");
		return sql.selectOne("serviceMapper.board_one", bno);
	}
	
	// 게시물 번호 가져오기 - 개별
	public int board_bno(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_bno - 게시물 번호 가져오기 - 개별");
		return sql.selectOne("serviceMapper.board_bno", s_dto);
	}
	
	// 게시물 작성/삭제/수정
	// 게시물 번호 시퀀스 생성
	public int board_write_bno() throws Exception {
		System.out.println("e_ServiceDAOimpl - board_write_bno - 글 작성 - 최초");
		return sql.selectOne("serviceMapper.board_write_bno");
	}
	
	// 답글 작성
	public void board_reply(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_reply - 답글 작성");
		sql.insert("serviceMapper.board_reply", s_dto);
	}
	
	// 글 작성 - 최초
	public void board_write(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_write - 글 작성 - 최초");
		sql.insert("serviceMapper.board_write", s_dto);
	}
	
	// 글 작성 - 첨부파일
	public void board_write_file(e_SvFileDTO s_filedto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_write_file - 글 작성 - 첨부파일");
		sql.insert("serviceMapper.board_write_file", s_filedto);
	}
	
	// 첨부파일 전부 불러오기
	public List<e_SvFileDTO> board_load_file(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_load_file - 첨부파일 전부 불러오기");
		return sql.selectList("serviceMapper.board_load_file", s_dto);
	}
	
	// 첨부파일 부분 불러오기
	public e_SvFileDTO board_load_file_one(e_SvFileDTO s_filedto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_load_file_one - 첨부파일 부분 불러오기");
		return sql.selectOne("serviceMapper.board_load_file_one", s_filedto);
	}
	
	// 첨부파일 부분 삭제 : 게시물과 연관된
	public void board_delete_file_one(e_SvFileDTO s_filedto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_delete_file_one - 첨부파일 부분 삭제");
		sql.delete("serviceMapper.board_delete_file_one", s_filedto);
	}
	
	// 글 삭제
	public void board_delete(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_delete - 글 삭제");
		// 게시물 삭제 - 좋아요 테이블
		sql.delete("serviceMapper.like_delete", s_dto);
		// 게시물 삭제 - 조회수 테이블
		sql.delete("serviceMapper.view_delete", s_dto);
		// 게시물 삭제 - 첨부파일 테이블
		sql.delete("serviceMapper.board_delete_file", s_dto);
		// 게시물 삭제 - 원본
		sql.delete("serviceMapper.board_delete", s_dto);
	}
	
	// 글 수정
	public void board_fix(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_fix - 글 수정");
		sql.update("serviceMapper.board_fix", s_dto);
	}
	
	// 부수적인 기능 (조회수, 좋아요)
	// 조회수 증가
	public void board_viewUp(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_viewUp - 조회수 증가");
		sql.update("serviceMapper.board_viewUp", s_dto);
	}
	
	// 조회수 증가 가능 여부 체크
	// member_no
	 public boolean board_viewCheck(e_SvViewcheckDTO s_viewCheck) throws Exception {
		 System.out.println("e_ServiceDAOimpl - board_viewCheck - 조회수 증가 가능 여부 체크 - member_no");
		 // 테이블 존재 확인
		 if ((int)sql.selectOne("serviceMapper.view_alive_mNo", s_viewCheck) == 0) {
			 // 테이블이 존재하지 않으면 테이블 생성
			 sql.insert("serviceMapper.view_insert_mNo", s_viewCheck);
			 return true;
		 }
		 // 테이블 불러오기
		 e_SvViewcheckDTO new_s_viewCheck = new e_SvViewcheckDTO();
		 new_s_viewCheck = sql.selectOne("serviceMapper.view_load_mNo", s_viewCheck);
		 
		 // 테이블 날짜 체크 - 하루 지났는지 확인
		 if ((int)sql.selectOne("serviceMapper.view_dateCheck", new_s_viewCheck) >= 1) {
			 // 테이블의 날짜 변경하기
			 sql.update("serviceMapper.view_save_mNo", new_s_viewCheck);
			 // 하루 지났음
			 return true;
		 }
		 // 하루 안 지남
		 return false;
	 }
	 
	// ip로 접속했을 경우 (ip가 중복되지 않을 시 증가)
	// member_ip
 	public boolean board_Ipcheck(e_SvViewcheckDTO s_viewCheck) throws Exception {
 		System.out.println("e_ServiceDAOimpl - board_Ipcheck - 조회수 증가 가능 여부 체크 - member_ip");
 		// ipv6 방식으로 ip명 가져오기
 		// RequestContextHolder : 전역으로 request 에 대한 정보를 가져옴
 		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
 		String ip = request.getHeader("X-Forwarded-For");
 		
 		// 정확한 클라이언트 ip를 가져오기 위해 중간에 스위치, 프록시 서버 등의 개입을 거름
 		if(ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
 			ip = request.getHeader("Proxy-Client-IP");
 		}
 		if(ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
 			ip = request.getHeader("WL-Proxy-Client-IP");
 		}
 		if(ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
 			ip = request.getHeader("HTTP_Client_IP");
 		}
 		if(ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
 			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
 		}
 		if(ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
 			ip = request.getRemoteAddr();
 		}
 		
 		// ip명 삽입
 		s_viewCheck.setMember_ip(ip);
 		// 테이블 존재 확인
		 if ((int)sql.selectOne("serviceMapper.view_alive_mIp", s_viewCheck)==0) {
			 // 테이블이 존재하지 않으면 테이블 생성
			 sql.insert("serviceMapper.view_insert_mIp", s_viewCheck);
			 // 조회수 증가
			 return true;
		 }
		 // 테이블 존재 시 불러오기
		 e_SvViewcheckDTO new_s_viewCheck = new e_SvViewcheckDTO();
		 new_s_viewCheck = sql.selectOne("serviceMapper.view_load_mIp", s_viewCheck);
		 
		 // 테이블 날짜 체크 - 하루 지났는지 확인
		 if ((int)sql.selectOne("serviceMapper.view_dateCheck", new_s_viewCheck) >= 1) {
			 // 테이블의 날짜 변경하기
			 sql.update("serviceMapper.view_save_mIp", new_s_viewCheck);
			 // 하루 지났음 - 값 넘겨줌
			 return true;
		 }
		 // 하루 안 지남
		 return false;
 	}
 	
 	// 좋아요 불러오기
 	public e_SvLikecheckDTO board_like_load(e_SvLikecheckDTO s_likeCheck) throws Exception {
 		System.out.println("e_ServiceDAOimpl - board_like_load - 좋아요 불러오기");
 		return sql.selectOne("serviceMapper.like_load", s_likeCheck);
 	}
 	
 	// 좋아요 증가
 	public void board_like_up(e_SvLikecheckDTO s_likeCheck) throws Exception {
 		System.out.println("e_ServiceDAOimpl - board_like_up - 좋아요 증가");
 		// 좋아요 테이블 불러오기
  		if(sql.selectOne("serviceMapper.like_load", s_likeCheck) == null) {
  			// 테이블이 없을 시
  			// 좋아요 테이블 생성
  			sql.insert("serviceMapper.like_insert_one", s_likeCheck);
  			s_likeCheck = sql.selectOne("serviceMapper.like_load", s_likeCheck);
  		}
 		// 게시판 테이블 불러오기
 		e_ServiceDTO s_dto = new e_ServiceDTO();
 		s_dto.setBno(s_likeCheck.getBno());
 		s_dto = sql.selectOne("serviceMapper.board_one", s_dto);
 		// 좋아요 증가
 		sql.update("serviceMapper.like_up", s_likeCheck);
 		s_dto.setLike_check(s_dto.getLike_check()+1);
 		// 좋아요 증가한 값 업데이트
 		sql.update("serviceMapper.like_update", s_dto);
 	}
 	
 	// 좋아요 감소
  	public void board_like_down(e_SvLikecheckDTO s_likeCheck) throws Exception {
  		System.out.println("e_ServiceDAOimpl - board_like_down - 좋아요 감소");
  		// 좋아요 테이블 불러오기
  		sql.selectOne("serviceMapper.like_load", s_likeCheck);
  		// 게시판 테이블 불러오기
 		e_ServiceDTO s_dto = new e_ServiceDTO();
 		s_dto.setBno(s_likeCheck.getBno());
 		s_dto = sql.selectOne("serviceMapper.board_one", s_dto);
 		// 좋아요 감소
  		sql.update("serviceMapper.like_down", s_likeCheck);
  		s_dto.setLike_check(s_dto.getLike_check()-1);
  	 	// 좋아요 감소한 값 업데이트
  		sql.update("serviceMapper.like_update", s_dto);
  	}
  	
  	// 싫어요 증가
  	public void board_dislike_up(e_SvLikecheckDTO s_likeCheck) throws Exception {
  		System.out.println("e_ServiceDAOimpl - board_dislike_up - 싫어요 증가");
  		// 좋아요 테이블 불러오기
   		if(sql.selectOne("serviceMapper.like_load", s_likeCheck) == null) {
   			// 테이블이 없을 시
   			// 싫어요 테이블 생성
   			sql.insert("serviceMapper.dislike_insert_one", s_likeCheck);
   			s_likeCheck = sql.selectOne("serviceMapper.like_load", s_likeCheck);
   		}
   		// 게시판 테이블 불러오기
 		e_ServiceDTO s_dto = new e_ServiceDTO();
 		s_dto.setBno(s_likeCheck.getBno());
 		s_dto = sql.selectOne("serviceMapper.board_one", s_dto);
 		// 싫어요 증가
 		sql.update("serviceMapper.dislike_up", s_likeCheck);
 		s_dto.setDislike_check(s_dto.getDislike_check()+1);
 		// 싫어요 증가한 값 업데이트
 		sql.update("serviceMapper.dislike_update", s_dto);
  	}
  	
  	// 싫어요 감소
   	public void board_dislike_down(e_SvLikecheckDTO s_likeCheck) throws Exception {
   		System.out.println("e_ServiceDAOimpl - board_dislike_down - 싫어요 감소");
   		// 싫어요 테이블 불러오기
   		sql.selectOne("serviceMapper.like_load", s_likeCheck);
   		// 게시판 테이블 불러오기
 		e_ServiceDTO s_dto = new e_ServiceDTO();
 		s_dto.setBno(s_likeCheck.getBno());
 		s_dto = sql.selectOne("serviceMapper.board_one", s_dto);
 		// 싫어요 감소
 		sql.update("serviceMapper.dislike_down", s_likeCheck);
  		s_dto.setDislike_check(s_dto.getDislike_check()-1);
  	 	// 좋아요 감소한 값 업데이트
  		sql.update("serviceMapper.dislike_update", s_dto);
   	}
  	
  	// 검색
 	// 검색한 게시물 갯수 가져오기
 	public int board_search_count_All(e_SvSearchDTO s_searchdto) throws Exception {
 		System.out.println("e_ServiceDAOimpl - board_search_count_All - 검색한 게시물 갯수 가져오기");
 		return sql.selectOne("serviceMapper.board_search_count_All", s_searchdto);
 	}
 	
 	// 검색한 게시물 전체 가져오기 : 페이징 적용
 	public List<e_ServiceDTO> board_search_All(e_SvSearchDTO s_searchdto) throws Exception {
 		System.out.println("e_ServiceDAOimpl - board_search_All - 검색한 게시물 전체 가져오기 : 페이징 적용");
 		return sql.selectList("serviceMapper.board_search_All", s_searchdto);
 	}
  	
}