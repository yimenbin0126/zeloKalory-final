package eunbin.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eunbin.DTO.e_ServiceDTO;

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
}