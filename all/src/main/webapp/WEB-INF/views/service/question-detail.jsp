<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.zerocalorie.member.dto.e_MemberDTO,com.zerocalorie.svservice.dto.e_ServiceDTO,com.zerocalorie.svservice.dto.e_SvFileDTO,com.zerocalorie.svservice.service.e_ServiceService,com.zerocalorie.svservice.service.e_ServiceServiceimpl,
	java.util.List, java.util.ArrayList" %>
<title>고객센터</title>
<link href="/all/resources/service/css/question-detail.css" rel="stylesheet">
<script src="/all/resources/service/js/question-detail.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
		<%
       		e_MemberDTO m_dto = new e_MemberDTO();
        		
        	// 로그인 유무
           	if((e_MemberDTO)session.getAttribute("user") !=null){
           		m_dto = (e_MemberDTO)session.getAttribute("user");
           	}
        %>
	<section>
			<div id="j_wrap">
				<div id="j_box">

				<div class="e_div">
					<!-- 왼쪽 카데고리 -->
					<nav class="e_nav">
						<!-- 문의 전체보기 -->
						<div class="e_nav_all" onclick="location.href='/all/service/allService'">
							문의 전체보기
						</div>
						<!-- 자주하는 질문 -->
						<div class="e_nav_question" onclick="location.href='/all/service/question-member'">
							<div class="e_que_div">자주하는 질문</div>
							<div><img src="/all/resources/service/img/category_click.png"></div>
						</div>
						<!-- 공개 건의함 -->
						<div class="e_nav_onebyone" onclick="location.href='/all/service/question-public'">공개 건의함</div>
					</nav>

					<!-- 오른쪽 내용 -->
					<div class="e_right">
						<!-- 상단 -->
						<%
							// 게시판 객체
							e_ServiceDTO s_dto = new e_ServiceDTO();
							s_dto = (e_ServiceDTO)request.getAttribute("s_dto");
							// 게시판 첨부파일 객체
							List<e_SvFileDTO> filelist = new ArrayList<e_SvFileDTO>();
							// 글쓰기 줄바꿈 보이게 저장
							s_dto.setDescription(s_dto.getDescription().replace("\r\n", "<br>"));
							if (s_dto.getSv_type()!=null && s_dto.getSv_type().equals("question_member")) {
						%>
						<!-- 회원 정보 관리 -->
						<div class="e_hd_top">고객센터 &gt; 자주하는 질문 &gt; 회원 정보 관리</div>
						<div class="e_header">
							<div class="e_hd_top_que">회원 정보 관리</div>
						</div>
						<%
							} else if (s_dto.getSv_type()!=null && s_dto.getSv_type().equals("question_guide")) {
						%>
						<!-- 사이트 이용 가이드 -->
						<div class="e_hd_top">고객센터 &gt; 자주하는 질문 &gt; 사이트 이용 가이드</div>
						<div class="e_header">
							<div class="e_hd_top_que">사이트 이용 가이드</div>
						</div>
						<%
							}
						%>
						<!-- 내용 -->
						<div class="e_hd_top_con">
							<div class="e_con_title"><%=s_dto.getTitle()%></div>
							<div class="e_con_explain">
								<div class="e_explain_member">
									<span><%=s_dto.getNickname()%></span> <span> | 조회 : <%=s_dto.getView_no()%></span>
								</div>
								<div class="e_explain_date"><%=s_dto.getCreate_time()%></div>
							</div>
							<div class="e_con_content">
								<p>
									<%=s_dto.getDescription()%>
								</p>
								<div class="e_blank"></div>
								<div class="e_content_like">
								<input type="hidden" name="e_bno" id="e_bno_value" value="<%=s_dto.getBno()%>">
								<%
									// 초기화 : 좋아요 눌러져 있는지 확인
									if (request.getAttribute("heart_click")!=null
									&& request.getAttribute("heart_click").equals("Y")
									&& (e_MemberDTO)session.getAttribute("user") !=null) {
								%>
									<!-- 좋아요 눌러져 있음 -->
									<input type="hidden" id="heart_click" value="Y">
									<p id="e_like_heart_n" class="e_like_heart" style="display:none;">♡</p>
									<p id="e_like_heart_y" class="e_like_heart">♥</p>
								<%
									} else if (request.getAttribute("heart_click")!=null
									&& request.getAttribute("heart_click").equals("N")
									&& (e_MemberDTO)session.getAttribute("user") !=null) {
								%>
									<!-- 좋아요 눌러져 있지 않음 -->
									<input type="hidden" id="heart_click" value="N">
									<p id="e_like_heart_n" class="e_like_heart">♡</p>
									<p id="e_like_heart_y" class="e_like_heart" style="display:none;">♥</p>
								<%
									} else {
								%>
									<!-- 로그인 안 되어있음 (누를 수 없게 함) -->
									<p class="e_like_heart">♡</p>
									<!-- null 값 방지 -->
									<input type="hidden" id="heart_click" value="none">
									<p id="e_like_heart_n" class="e_like_heart" style="display:none;">♡</p>
									<p id="e_like_heart_y" class="e_like_heart" style="display:none;">♥</p>
								<%
									}
								%>
									<p class="e_like_num"><%=s_dto.getLike_check()%></p>
								</div>
							</div>
							<!-- 첨부 파일 -->
							<div class="e_content_file">
									<div class="c_file_title">첨부 파일</div>
									<%
										if (((ArrayList<e_SvFileDTO>)request.getAttribute("filelist")).size() != 0) {
											filelist = (ArrayList<e_SvFileDTO>)request.getAttribute("filelist");
											for (int i=0; i<filelist.size(); i++){
												e_SvFileDTO s_filedto = new e_SvFileDTO();
												s_filedto = filelist.get(i);
									%>
									<div class="c_file_content"><a href="/all/service/file/download?file_order=<%=s_filedto.getFile_order()%>"><%=s_filedto.getFilename()%></a></div>
									<%
											}
											} else {
									%>
									<div class="c_file_content">* 첨부된 파일 없음</div>
									<%
										}
									%>
							</div>
						</div>

						<div class="e_button">
						<%
							// 게시판 관련 메서드 불러오기
							e_ServiceService s_service = new e_ServiceServiceimpl();
							// 관리자 여부
							if ((e_MemberDTO)session.getAttribute("user") !=null){
								m_dto = (e_MemberDTO)session.getAttribute("user");
								if(m_dto.getId().equals("admin")){
							// 게시물 수정, 삭제 버튼 보이기 여부
						%>
						
							<!-- 게시물 수정, 삭제 버튼 -->
							<div id="e_hidden_fix">
								<form name="e_btn_fix_form">
									<!-- 게시판 데이터 보내기 -->
									<input type="hidden" name="e_btn" value="fix">
									<input type="hidden" name="e_bno" value="<%=s_dto.getBno()%>">
									<input type="submit" id="e_btn_fix" class="e_btn_css" value="글 수정">
								</form>
							</div>
							<div id="e_hidden_del">
								<form name="e_btn_delete_form">
									<input type="hidden" name="e_btn" value="delete">
									<input type="hidden" name="e_bno" value="<%=s_dto.getBno()%>">
									<input type="submit" id="e_btn_delete" class="e_btn_css" value="글 삭제">
								</form>
							</div>
						<%
								}
							}
						%>
							
							<!-- 뒤로 가기 -->
							<div class="e_btn_css" onclick="window.history.back();">뒤로 가기</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</section>