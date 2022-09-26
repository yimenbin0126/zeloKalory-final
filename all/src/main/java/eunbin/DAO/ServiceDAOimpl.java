package eunbin.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import eunbin.DTO.MemberDTO;
import eunbin.DTO.ServiceDTO;

public class ServiceDAOimpl implements ServiceDAO {

	@Autowired
	private SqlSession sql;
	

	// 특정 게시판 목록들 불러오기 - 전체
	public List<ServiceDTO> board_bno_All(String sv_type) throws Exception {
		// 타입별 게시물 번호들 불러오기 (sv_type)
		// 게시물 번호들 얻기 - 새 객체에 타입 저장
		ServiceDTO s_dto = new ServiceDTO();
		s_dto.setSv_type(sv_type);
		ArrayList bno_list = sql.selectOne("serviceMapper.board_bno_All", s_dto);
		// 번호들로 게시물 얻기
		List<ServiceDTO> s_dtoList = new ArrayList();
		for(int i=0; i<bno_list.size(); i++) {
			// 리스트에 저장할 객체 생성
			ServiceDTO s_dto_new = new ServiceDTO();
			// 객체에 게시물 번호 삽입
			s_dto_new.setBno((int)bno_list.get(i));
			// 게시물 번호가 저장된 객체로 원본 게시물 불러오기
			s_dto_new = sql.selectOne("serviceMapper.board_one", s_dto_new);
			// 해당 게시물을 리스트에 저장
			s_dtoList.add(s_dto_new);
		}
		return s_dtoList;
	}
	
	// 특정 게시물 불러오기 - 번호 (bno)
	public ServiceDTO board_one(int bno) throws Exception {
		return sql.selectOne("serviceMapper.board_one", bno);
	}
	
	// 게시물 번호 가져오기 - 개별
	public int board_bno(ServiceDTO s_dto) throws Exception {
		return sql.selectOne("serviceMapper.board_bno", s_dto);
	}
	
	// 게시물 작성/삭제/수정
	// 글 작성 - 최초
	public void board_write(ServiceDTO s_dto) throws Exception {
		sql.insert("serviceMapper.board_write", s_dto);
	}
	
	// 글 삭제
	public void board_delete(int bno) throws Exception {
		sql.delete("serviceMapper.board_delete", bno);
	}
	
	// 글 수정
	public void board_fix(ServiceDTO s_dto) throws Exception {
		sql.update("serviceMapper.board_fix", s_dto);
	}
}