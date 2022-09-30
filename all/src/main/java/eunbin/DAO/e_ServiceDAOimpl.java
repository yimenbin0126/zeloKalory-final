package eunbin.DAO;

import java.net.Inet4Address;
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
	// 글 작성 - 최초
	public void board_write(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_write - 글 작성 - 최초");
		sql.insert("serviceMapper.board_write", s_dto);
	}
	
	// 글 삭제
	public void board_delete(int bno) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_delete - 글 삭제");
		sql.delete("serviceMapper.board_delete", bno);
	}
	
	// 글 수정
	public void board_fix(e_ServiceDTO s_dto) throws Exception {
		System.out.println("e_ServiceDAOimpl - board_fix - 글 수정");
		sql.update("serviceMapper.board_fix", s_dto);
	}
	
	// 부수적인 기능 (조회수, 좋아요)
	// 조회수 증가
	public void board_viewUp(e_ServiceDTO s_dto) throws Exception {
		sql.update("serviceMapper.board_viewUp", s_dto);
	}
	
	// 조회수 증가 가능 여부 체크
	// member_no
	 public boolean board_viewCheck(e_SvViewcheckDTO s_viewCheck) throws Exception {
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
			 // 하루 지났음
			 return true;
		 }
		 // 하루 안 지남
		 return false;
	 }
	 
	// ip로 접속했을 경우 (ip가 중복되지 않을 시 증가)
	// member_ip
 	public boolean board_Ipcheck(e_SvViewcheckDTO s_viewCheck) throws Exception {
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
		 new_s_viewCheck = sql.selectOne("serviceMapper.view_load_mNo", s_viewCheck);
		 
		 // 테이블 날짜 체크 - 하루 지났는지 확인
		 if ((int)sql.selectOne("serviceMapper.view_dateCheck", new_s_viewCheck) >= 1) {
			 // 하루 지났음
			 return true;
		 }
		 // 하루 안 지남
		 return false;
 	}
}