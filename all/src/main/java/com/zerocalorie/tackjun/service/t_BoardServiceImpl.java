package com.zerocalorie.tackjun.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zerocalorie.tackjun.DAO.t_BoardDAO;
import com.zerocalorie.tackjun.DTO.t_ArticleDTO;


@Service("t_BoardService")
@Transactional(propagation = Propagation.REQUIRED)
public class t_BoardServiceImpl  implements t_BoardService{
	@Autowired
	t_BoardDAO boardDAO;
	
	public List<t_ArticleDTO> listArticles() throws Exception{
		List<t_ArticleDTO> articlesList =  boardDAO.selectAllArticlesList();
        return articlesList;
	}

	
	//단일 이미지 추가하기
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		return boardDAO.insertNewArticle(articleMap);
	}
	
	 //단일 파일 보이기
	@Override
	public t_ArticleDTO viewArticle(int articleNO) throws Exception {
		t_ArticleDTO articleVO = new t_ArticleDTO();
		articleVO = (t_ArticleDTO) boardDAO.selectArticle(articleNO);
		return articleVO;
	}


	@Override
	public void modArticle(Map articleMap) throws Exception {
		// TODO Auto-generated method stub
		boardDAO.modArticle(articleMap);
	}

	@Override
	public void removeArticle(int articleNO) throws Exception {
		// TODO Auto-generated method stub
		boardDAO.removeArticle(articleNO);
	}
}