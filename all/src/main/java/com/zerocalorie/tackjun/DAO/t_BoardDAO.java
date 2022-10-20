package com.zerocalorie.tackjun.DAO;

import java.util.List;
import java.util.Map;

import com.zerocalorie.tackjun.DTO.*;


public interface t_BoardDAO<t_ArticleDTO> {
	public List selectAllArticlesList() throws Exception;
	public int insertNewArticle(Map articleMap) throws Exception;
	//public void insertNewImage(Map articleMap) throws DataAccessException;
	public void modArticle(Map articleMap) throws Exception;
	public void removeArticle(int articleNO) throws Exception;
	public t_ArticleDTO selectArticle(int articleNO) throws Exception;
	public void updateArticle(Map articleMap) throws Exception;
	public void deleteArticle(int articleNO) throws Exception;
	
}