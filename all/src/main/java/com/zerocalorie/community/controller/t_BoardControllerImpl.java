package com.zerocalorie.community.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.zerocalorie.community.DTO.t_ArticleDTO;
import com.zerocalorie.community.DTO.t_Article_plusDTO;
import com.zerocalorie.member.dto.e_MemberDTO;
import com.zerocalorie.member.service.e_MemberService;
import com.zerocalorie.svservice.dto.e_SvFileDTO;
import com.zerocalorie.community.service.t_BoardService;

@Controller
public class t_BoardControllerImpl implements t_BoardController{

	private static final String ARTICLE_IMAGE_REPO = "C://zerokalory_file";
	
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
			articleDTO_plus.setReccount(articleDTO.getReccount());
			
			articlesList_Plus.add(articleDTO_plus);
		}
		mav.addObject("articlesList_Plus", articlesList_Plus);
		return mav;
		
	}
	
	//한 개 이미지 글쓰기
	@GetMapping("/community/articleForm")
	public String getQuestion_write(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ServiceController - getQuestion_write");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			// 응답 - 한글 처리
			response.setContentType("text/html;charset=UTF-8");
			// 로그인 안했을 때 - 잘못된 접근
			// 뒤로가기
			PrintWriter out = response.getWriter();
			out.println("<script language ='javascript'>window.history.back();</script>");
			out.flush();
		} else {
			// 로그인 되있을 때 - 정상 접근
			// 게시물 쓰기 뷰
			return "/community/articleForm";
		}
		return null;
	}
	
	@Override
	@RequestMapping(value="/community/addArticle.do" ,method = RequestMethod.POST)
	@ResponseBody
	public void addNewArticle(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// 첨부 파일 불러오기 - (1)
		request.setCharacterEncoding("utf-8");
		// 파일 경로
		String savePath = "C:\\zerokalory_file";
		// 파일 크기 15MB
		int sizeLimit = 1024 * 1024 * 15;
		// 파라미터를 전달해줌 (같은 이름의 파일명 방지)
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
				new DefaultFileRenamePolicy());
				
		// 추가
		Enumeration enumeration = multi.getFileNames();
		Map<String,Object> articleMap = new HashMap<String, Object>();
		String fileName = "";
		// 데이터가 있을때, 불러오기
		while(enumeration.hasMoreElements()){
			// 파일 객체 불러오기
			fileName = multi.getFilesystemName((String) enumeration.nextElement());
		}
		
		e_MemberDTO memberVO = (e_MemberDTO) session.getAttribute("user");
		int member_no = memberVO.getMember_no();
		articleMap.put("title", multi.getParameter("title"));
		articleMap.put("content", multi.getParameter("content"));
		articleMap.put("member_no", member_no);
		articleMap.put("parentNO", 0);
		articleMap.put("imageFileName", fileName);
		int articleNO = t_boardService.addNewArticle(articleMap);
		System.out.println("이미지"+fileName);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script language ='javascript'>alert('새글을 추가했습니다.'); location.href='/all/community/listArticles.do'; </script>");
		out.flush();
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
		articleDTO_plus.setReccount(articleDTO.getReccount());
		mav.addObject("article", articleDTO_plus);
		return mav;
	}
	
	@GetMapping("/community/load-proimg")
	public void getLoad_proimg(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam String fileName
			)
			throws Exception {
		System.out.println("ServiceController - getFileDownload - 첨부파일 다운로드");
		
		// 객체에서 정보 가져오기 (이름, 경로)
		String filePath = "C:\\zerokalory_file";
		// 파일 객체 선언 (파일 경로, 파일 이름)
        File file = new File(filePath, fileName);
        int fileLength = (int)file.length();
        
        if (fileLength > 0) {
        	// 파일 학장자 체크
        	if (file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")) {
        		response.setContentType("image/jpeg");
        		} else if (file.getName().endsWith(".png")) {
        		response.setContentType("image/png");
        		} 
        	response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Length", "" + fileLength);
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");
            
            // FileInputStream : 파일을 바이트 스트림으로 읽어줌
			try (FileInputStream fis = new FileInputStream(file);
					OutputStream out = response.getOutputStream();) {
				int readCount = 0;
				byte[] buffer = new byte[1024];
				// fis.read(buffer) : 파일 바이트 타입으로 읽기
				// -1 : 파일 다 읽었을 때
				// write : 읽어들인 파일의 바이트를 출력
				while ((readCount = fis.read(buffer)) != -1) {
					out.write(buffer, 0, readCount);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}
	
	//한 개 이미지 수정 기능
	  @RequestMapping(value="/community/modArticle.do" ,method = RequestMethod.POST)
	  @ResponseBody
	  public void modArticle(HttpServletRequest request, 
				HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		// 첨부 파일 불러오기 - (1)
		request.setCharacterEncoding("utf-8");
		// 파일 경로
		String savePath = "C:\\zerokalory_file";
		// 파일 크기 15MB
		int sizeLimit = 1024 * 1024 * 15;
		// 파라미터를 전달해줌 (같은 이름의 파일명 방지)
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
				new DefaultFileRenamePolicy());
				
		// 추가
		Enumeration enumeration = multi.getFileNames();
		Map<String,Object> articleMap = new HashMap<String, Object>();
		String fileName = "";
		// 데이터가 있을때, 불러오기
		while(enumeration.hasMoreElements()){
			// 파일 객체 불러오기
			fileName = multi.getFilesystemName((String) enumeration.nextElement());
		}
		
		e_MemberDTO memberVO = (e_MemberDTO) session.getAttribute("user");
		int member_no = memberVO.getMember_no();
		articleMap.put("title", multi.getParameter("title"));
		articleMap.put("content", multi.getParameter("content"));
		articleMap.put("imageFileName", fileName);
		int articleNO = Integer.valueOf(multi.getParameter("articleNO"));
		articleMap.put("articleNO", articleNO);
		System.out.println("이미지"+fileName);
		t_boardService.modArticle(articleMap);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script language ='javascript'>alert('글을 수정했습니다.'); location.href='/all/community/viewArticle.do?articleNO="+articleNO+"'; </script>");
		out.flush();
		  
	  }
	  
	  //사진업로드
	  private String upload(MultipartHttpServletRequest multipartRequest) throws Exception{
			String imageFileName= null;
			Iterator<String> fileNames = multipartRequest.getFileNames();
			
			while(fileNames.hasNext()){
				String fileName = fileNames.next();
				MultipartFile mFile = multipartRequest.getFile(fileName);
				imageFileName=mFile.getOriginalFilename();
				File file = new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+"\\" + fileName);
				if(mFile.getSize()!=0){
					if(!file.exists()){
						file.getParentFile().mkdirs();
						mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+ "\\"+imageFileName));
					}
				}
				
			}
			return imageFileName;
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
