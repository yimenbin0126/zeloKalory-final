package tackjun.service;

import java.util.List;
import java.util.Map;

import tackjun.DTO.*;

public interface t_BoardService<t_ArticleDTO> {
	public List<t_ArticleDTO> listArticles() throws Exception;
	public int addNewArticle(Map articleMap) throws Exception;
	public t_ArticleDTO viewArticle(int articleNO) throws Exception;
	public void modArticle(Map articleMap) throws Exception;
	public void removeArticle(int articleNO) throws Exception;
}
