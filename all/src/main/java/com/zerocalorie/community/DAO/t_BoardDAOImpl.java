package com.zerocalorie.community.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("t_BoardDAO")
public class t_BoardDAOImpl<t_ArticleDTO> implements t_BoardDAO {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List selectAllArticlesList() throws Exception {
		List<t_ArticleDTO> articlesList = (List<t_ArticleDTO>)sqlSession.selectList("mapper.board.selectAllArticlesList");
		return articlesList;
	}
	
	@Override
	public int insertNewArticle(Map articleMap) throws Exception {
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert("mapper.board.insertNewArticle",articleMap);
		return articleNO;
	}
    
	@Override
	public t_ArticleDTO selectArticle(int articleNO) throws Exception {
		return sqlSession.selectOne("mapper.board.selectArticle", articleNO);
	}

	@Override
	public void updateArticle(Map articleMap) throws Exception {
		sqlSession.update("mapper.board.updateArticle", articleMap);
	}

	@Override
	public void deleteArticle(int articleNO) throws Exception {
		sqlSession.delete("mapper.board.deleteArticle", articleNO);
	}
	
	private int selectNewArticleNO() throws Exception {
		return sqlSession.selectOne("mapper.board.selectNewArticleNO");
	}

	@Override
	public void modArticle(Map articleMap) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update("mapper.board.updateArticle", articleMap);
	}

	@Override
	public void removeArticle(int articleNO) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete("mapper.board.deleteArticle", articleNO);
	}

}
