package com.zerocalorie.mypage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.zerocalorie.member.dto.e_MemberDTO;
import com.zerocalorie.mypage.dao.MypageChartDAO;
import com.zerocalorie.mypage.dao.MypageMbInfoDAO;
import com.zerocalorie.mypage.dto.MypageChartDTO;
import com.zerocalorie.mypage.service.MypageChartService;
import com.zerocalorie.mypage.service.MypageMbInfoService;


@RequestMapping("/mypage")
@Controller
public class MypageController {
	
	@Autowired
	MypageMbInfoDAO mypageMbInfoDAO;
	
	@Autowired
	MypageChartDAO mypageChartDAO;
	
	@Autowired
	MypageMbInfoService MypageMbInfoService;
	
	@Autowired
	MypageChartService mypageChartService;
	
	// mypage 
	@RequestMapping("/mypage")
	public ModelAndView mypageController(HttpServletRequest request, HttpServletResponse response
			, @ModelAttribute MypageChartDTO mypageChartDTO,e_MemberDTO e_memberDTO) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("mypage/mypage");
		return mav;
	}
	
	// 프로필 수정하기
	@RequestMapping("/edit")
	public ModelAndView editController(HttpServletRequest request, HttpServletResponse response
			, @ModelAttribute e_MemberDTO e_memberDTO) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("e_memberDTO",e_memberDTO);
		mav.setViewName("mypage/editMemberForm");
		mypageMbInfoDAO.member_no_loadMember(e_memberDTO);
		return mav;
	}
	
	// 회원정보 수정하기
	@RequestMapping("/editMemberForm")
	public String editMemberForm(HttpServletRequest request,
			@ModelAttribute("e_memberDTO") e_MemberDTO e_memberDTO) {
		
		mypageMbInfoDAO.id_updateMember(e_memberDTO);
		
		e_MemberDTO _m_dto = new e_MemberDTO();
		
		_m_dto = MypageMbInfoService.id_loadMember(e_memberDTO);
		
		HttpSession session= request.getSession();
		
		session.setAttribute("user", _m_dto);

		System.out.println("mypage > editMemberForm ");

		return "mypage/mypage";
	}
	
	
	//프로필 이미지 보이게
	@RequestMapping("/download_k.do")
	public void ProfileImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 경로 확보
		String file_repo = "C:\\zerokalory_file";
		// 파일명 확보
		String fileName = (String) request.getParameter("fileName");
		//파일에 대한 풀 경로(file full path) (윈도우라서 \\ 해줌)
		String downFile = file_repo +"\\"+ fileName;
		// 이러면 리눅스나 윈도우 상관없이 쓸수 있음
		
		// 파일 객체 선언 (파일 경로, 파일 이름)
        File file = new File(file_repo, fileName);
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
	
	
	// 파일 업로드하기
	//@ResponseBody
	@PostMapping("/upload.do")
	public String postQuestion_write(
			HttpServletRequest request,
			@RequestParam(value = "profileImg", required = false) String profileImg,
			@RequestParam(value = "memberId", required = false) String memberId )
			throws Exception {
		request.setCharacterEncoding("utf-8");

		// 파일 경로
		String savePath = "C:\\zerokalory_file";
		// 파일 크기 15MB
		int sizeLimit = 1024 * 1024 * 15;
		// 파라미터를 전달해줌 (같은 이름의 파일명 방지)
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
		new DefaultFileRenamePolicy());
	
		Enumeration enumeration = multi.getFileNames();
		
		while(enumeration.hasMoreElements()){
		e_MemberDTO dto = new e_MemberDTO();
		e_MemberDTO _m_dto = new e_MemberDTO();

		String fileName = multi.getFilesystemName((String) enumeration.nextElement());
	
		dto.setPro_img(fileName);
		dto.setId(multi.getParameter("memberId"));
		
		MypageMbInfoService.pro_img_update(dto);
		
		_m_dto = MypageMbInfoService.id_loadMember(dto);
		HttpSession session= request.getSession();
		session.setAttribute("user", _m_dto);
		
		System.out.println("user:"+_m_dto);
		System.out.println("get"+fileName);
		System.out.println("get"+dto.getId());

		
		}
		return "mypage/mypage";
	}
	
	// mypage 몸무게 추가
	@RequestMapping("/weightAdd")
	public String weightAdd(HttpServletRequest request
			, @ModelAttribute MypageChartDTO mypageChartDTO) {
		// jsp에서 넘어온 CURRENT_WEIGHT, TARGET_WEIGHT 등이 int 변환에 문제 try 해..
		
		System.out.println("mypage > /weightAdd ");
		mypageChartService.weightAdd(request, mypageChartDTO);
		
		return "mypage/mypage";
	}			
	
	
	
	// mypage 몸무게 수정
	@RequestMapping("/weightMod")
	public String weightMod(HttpServletRequest request
			, @ModelAttribute MypageChartDTO mypageChartDTO) {
		
		System.out.println("mypage > /weightMod ");
		mypageChartService.weightMod(request, mypageChartDTO);

		return "mypage/mypage";
	}
	
	// mypage chart JSON  부분
	@RequestMapping("/chartJSON")
	@ResponseBody
	public List MypageChartJSON(@RequestParam(value = "member_no") int member_no) {
		
		System.out.println("MypageController > MypageChartJSON");

		List<MypageChartDTO> MypageChartlist = mypageChartService.weightread(member_no);
		
		List list = new ArrayList();
		for(int i = 0; i<MypageChartlist.size(); i++) {
			MypageChartDTO vo = new MypageChartDTO();
			vo = MypageChartlist.get(i);
			list.add(vo);
		}
		return list;
	}
}
