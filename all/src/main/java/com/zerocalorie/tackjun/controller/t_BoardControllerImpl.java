package com.zerocalorie.tackjun.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zerocalorie.member.dto.e_MemberDTO;
import com.zerocalorie.member.service.e_MemberService;
import com.zerocalorie.tackjun.DTO.t_ArticleDTO;
import com.zerocalorie.tackjun.DTO.t_Article_plusDTO;
import com.zerocalorie.tackjun.service.t_BoardService;

@Controller
public class t_BoardControllerImpl implements t_BoardController{
	private static final String CURR_IMAGE_REPO_PATH = "C://zerokalory_file";

	private static final String ARTICLE_IMAGE_REPO = null;
	
	@Autowired
	e_MemberService m_service;
	
	@Autowired
	private t_BoardService t_boardService;

	@Autowired
	private t_ArticleDTO t_articleDTO;
	
	@Override
	@RequestMapping(value= "/community/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		List articlesList = t_boardService.listArticles();
		System.out.println(articlesList.size()==0);
		ModelAndView mav = new ModelAndView(viewName);
		String nick = "";
		// 플러스 객체
		List<t_Article_plusDTO> articlesList_Plus = new ArrayList<t_Article_plusDTO>();
		for(int i=0; i<articlesList.size(); i++) {
			// 한줄 갖고오기
			t_ArticleDTO articleDTO = new t_ArticleDTO();
			articleDTO = (t_ArticleDTO)articlesList.get(i);
			// 닉네임 가져오기
			e_MemberDTO m_dto = new e_MemberDTO();
			m_dto.setMember_no(articleDTO.getMember_no());
			m_dto = (e_MemberDTO)m_service.member_no_loadMember(m_dto);
			nick = m_dto.getNickname();
			// 변형
			t_Article_plusDTO articleDTO_plus = new t_Article_plusDTO();
			articleDTO_plus.setArticleNO(articleDTO.getArticleNO());
			articleDTO_plus.setContent(articleDTO.getContent());
			articleDTO_plus.setImageFileName(articleDTO.getImageFileName());
			articleDTO_plus.setMember_no(articleDTO.getMember_no());
			articleDTO_plus.setParentNO(articleDTO.getParentNO());
			articleDTO_plus.setTitle(articleDTO.getTitle());
			articleDTO_plus.setWriteDate(articleDTO.getWriteDate());
			articleDTO_plus.setNickname(nick);
			articlesList_Plus.add(articleDTO_plus);
		}
		mav.addObject("articlesList_Plus", articlesList_Plus);
		return mav;
		
	}
	
	 //한 개 이미지 글쓰기
	@GetMapping("/community/articleForm")
	public String addNewArticle_get(
			HttpServletResponse request) throws Exception {
		System.out.println("출력");
		return "/community/articleForm";
	}
	
	@Override
	@RequestMapping(value="/community/addArticle.do" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest, 
	HttpServletResponse response) throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String,Object> articleMap = new HashMap<String, Object>();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name,value);
		}
		
		String imageFileName= upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		e_MemberDTO memberVO = (e_MemberDTO) session.getAttribute("user");
		int member_no = memberVO.getMember_no();
		articleMap.put("parentNO", 0);
		articleMap.put("member_no", member_no);
		articleMap.put("imageFileName", imageFileName);
		
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			int articleNO = t_boardService.addNewArticle(articleMap);
			if(imageFileName!=null && imageFileName.length()!=0) {
				File srcFile = new 
				File(ARTICLE_IMAGE_REPO+ "\\" + "temp"+ "\\" + imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir,true);
			}
	
			message = "<script>";
			message += " alert('새글을 추가했습니다.');";
			message += " location.href='"+multipartRequest.getContextPath()+"/community/listArticles.do'; ";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			srcFile.delete();
			
			message = " <script>";
			message +=" alert('오류가 발생했습니다. 다시 시도해 주세요');');";
			message +=" location.href='"+multipartRequest.getContextPath()+"/community/articleForm.do'; ";
			message +=" </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	
	//한개의 이미지 보여주기
	@RequestMapping(value="/community/viewArticle.do" ,method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		t_ArticleDTO articleDTO = new t_ArticleDTO();
		articleDTO = (t_ArticleDTO) t_boardService.viewArticle(articleNO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		// 닉네임 가져오기
		e_MemberDTO m_dto = new e_MemberDTO();
		m_dto.setMember_no(articleDTO.getMember_no());
		m_dto = (e_MemberDTO)m_service.member_no_loadMember(m_dto);
		String nick = m_dto.getNickname();
		// 변형
		t_Article_plusDTO articleDTO_plus = new t_Article_plusDTO();
		articleDTO_plus.setArticleNO(articleDTO.getArticleNO());
		articleDTO_plus.setContent(articleDTO.getContent());
		articleDTO_plus.setImageFileName(articleDTO.getImageFileName());
		articleDTO_plus.setMember_no(articleDTO.getMember_no());
		articleDTO_plus.setParentNO(articleDTO.getParentNO());
		articleDTO_plus.setTitle(articleDTO.getTitle());
		articleDTO_plus.setWriteDate(articleDTO.getWriteDate());
		articleDTO_plus.setNickname(nick);
		mav.addObject("article", articleDTO_plus);
		return mav;
	}
	
	//한 개 이미지 수정 기능
	  @RequestMapping(value="/community/modArticle.do" ,method = RequestMethod.POST)
	  @ResponseBody
	  public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest,  
	    HttpServletResponse response) throws Exception{
	    multipartRequest.setCharacterEncoding("utf-8");
		Map<String,Object> articleMap = new HashMap<String, Object>();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name,value);
		}
		
		String imageFileName= upload(multipartRequest);
		articleMap.put("imageFileName", imageFileName);
		
		String articleNO=(String)articleMap.get("articleNO");
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	    try {
	       t_boardService.modArticle(articleMap);
	       if(imageFileName!=null && imageFileName.length()!=0) {
	         File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
	         File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
	         FileUtils.moveFileToDirectory(srcFile, destDir, true);
	         
	         String originalFileName = (String)articleMap.get("originalFileName");
	         File oldFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+originalFileName);
	         oldFile.delete();
	       }	
	       message = "<script>";
		   message += " alert('글을 수정했습니다.');";
		   message += " location.href='"+multipartRequest.getContextPath()+"/community/viewArticle.do?articleNO="+articleNO+"';";
		   message +=" </script>";
	       resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    }catch(Exception e) {
	      File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
	      srcFile.delete();
	      message = "<script>";
		  message += " alert('오류가 발생했습니다.다시 수정해주세요');";
		  message += " location.href='"+multipartRequest.getContextPath()+"/community/viewArticle.do?articleNO="+articleNO+"';";
		  message +=" </script>";
	      resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    }
	    return resEnt;
	  }
	  
	  private String upload(MultipartHttpServletRequest multipartRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	  @RequestMapping(value="/community/removeArticle.do" ,method = RequestMethod.POST)
	  @ResponseBody
	  public ResponseEntity  removeArticle(@RequestParam("articleNO") int articleNO,
	                              HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html; charset=UTF-8");
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			t_boardService.removeArticle(articleNO);
			File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
			FileUtils.deleteDirectory(destDir);
			
			message = "<script>";
			message += " alert('글을 삭제했습니다.');";
			message += " location.href='"+request.getContextPath()+"/community/listArticles.do';";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		    
		}catch(Exception e) {
			message = "<script>";
			message += " alert('작업중 오류가 발생했습니다.다시 시도해 주세요.');";
			message += " location.href='"+request.getContextPath()+"/community/listArticles.do';";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		    e.printStackTrace();
		}
		return resEnt;
	  }  
}
