<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.zerocalorie.member.dto.e_MemberDTO,com.zerocalorie.svservice.dto.e_ServiceDTO,com.zerocalorie.svservice.dto.e_SvFileDTO,
	com.zerocalorie.svservice.service.e_ServiceService,com.zerocalorie.svservice.service.e_ServiceServiceimpl,
	com.zerocalorie.svservice.dto.e_SvCommentDTO, com.zerocalorie.member.dao.e_MemberDAOimpl,
	java.util.List, java.util.ArrayList" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객센터</title>
<link href="/all/resources/service/css/header.css" rel="stylesheet">
<link href="/all/resources/service/css/question-public-detail.css" rel="stylesheet">
<script src="/all/resources/service/js/question-public-detail.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>

<body>

	<!-- 헤더 시작 -->
    <div id="j_hi">
        <!-- <img src="./img/logo.png" id="j_logo"> -->

		<%
       		e_MemberDTO m_dto = new e_MemberDTO();
        		
        	// 로그인 유무
           	if((e_MemberDTO)session.getAttribute("user") !=null){
           		m_dto = (e_MemberDTO)session.getAttribute("user");
        %>
		<ul id="j_list">
            <li class="j_menu1 j_menu" onclick="location.href='/all/cal/<%=m_dto.getId()%>'">캘린더</li>
            <li class="j_menu2 j_menu" onclick="location.href='/all/community/listArticles.do'">커뮤니티</li>
            <li class="j_menu5 j_menu" onclick="location.href='/all/service/allService'">고객센터</li>
        </ul>
        <div id = e_nav>
        	<div id="e_welcome">
        		<%=m_dto.getNickname()%>님 환영합니다.
        	</div>
        	<form name="e_nav_btn">
	        	<input type ="hidden" name="e_logout" value="Y">                   
        	</form>
        	<!-- null 오류 방지용 시작 -->
        	<input type ='hidden' class = "j_btn1 j_btn" onclick="location.href='/all/member/login'" value="로그인">
            <input type ='hidden' class = "j_btn2 j_btn" onclick="location.href='/all/member/join'" value="회원가입">
            <!-- null 오류 방지용 끝 -->
            <!-- 나타나는 부분 시작 -->
            <input type ='button' class = "e_btn e_btn" onclick="location.href='/all/member/logout'" value="로그아웃">
            <input type ='button' class = "e_btn2 e_btn" onclick="location.href='/all/mypage/mypage'" value="마이페이지">
            <!-- 나타나는 부분 끝 -->
        </div>
        <%
        	} else {
        %>
        <ul id="j_list">
            <li class="j_menu1 j_menu" onclick="location.href='/all/cal/<%=m_dto.getId()%>'">캘린더</li>
            <li class="j_menu2 j_menu" onclick="location.href='/all/community/listArticles.do'">커뮤니티</li>
            <li class="j_menu5 j_menu" onclick="location.href='/all/service/allService'">고객센터</li>
        </ul>
        <div id = j_nav>
        	<!-- null 오류 방지용 시작 -->
            <input type ='hidden' class = "e_btn e_btn" onclick="location.href='/all/member/logout'" value="로그아웃">
            <input type ='hidden' class = "e_btn2 e_btn" onclick="location.href='/all/mypage/mypage'" value="마이페이지">               
            <!-- null 오류 방지용 끝 -->
            <!-- 나타나는 부분 시작 -->
            <input type ='button' class = "j_btn1 j_btn" onclick="location.href='/all/member/login'" value="로그인">
            <input type ='button' class = "j_btn2 j_btn" onclick="location.href='/all/member/join'" value="회원가입">
            <!-- 나타나는 부분 끝 -->
        </div>
        <%
        	}
        %>
    </div>
    <!-- 헤더 끝 -->

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
							자주하는 질문
						</div>
						<!-- 공개 건의함 -->
						<div class="e_nav_onebyone" onclick="location.href='/all/service/question-public'">
							<div class="e_que_div">공개 건의함</div>
							<div><img src="/all/resources/service/img/category_click.png"></div>
						</div>
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
							if (s_dto.getSv_type()!=null && s_dto.getSv_type().equals("question_public")) {
						%>
						<!-- 회원 정보 관리 -->
						<div class="e_hd_top">고객센터 &gt; 공개 건의함</div>
						<div class="e_header">
							<div class="e_hd_top_que">공개 건의함</div>
						</div>
						<%
							}
						%>
						<!-- 내용 -->
						<div class="e_hd_top_con">
							<!-- 답글인 경우 -->
							<%
								if(s_dto.getAdmin_type().equals("reply")){
							%>
							<div class="origin_board">
								<a href="/all/service/question-public-detail?bno=<%=s_dto.getGroup_origin()%>">[원본 게시물로 이동]</a>
							</div>
							<%
								}
							%>
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
								<!-- 좋아요 / 싫어요 -->
								<!-- 좋아요 -->
								<div class="e_content_like_dislike">
									<div class="e_content_like">
										<input type="hidden" name="e_bno" id="e_bno_value" value="<%=s_dto.getBno()%>">
										<%
											// 초기화 : 좋아요 눌러져 있는지 확인
											if (request.getAttribute("like_click")!=null
											&& request.getAttribute("like_click").equals("Y")
											&& (e_MemberDTO)session.getAttribute("user") !=null) {
										%>
											<!-- 좋아요 눌러져 있음 -->
											<input type="hidden" id="heart_click" value="Y">
											<p id="e_like_heart_n" class="e_like_heart" style="display:none;">
												<img src="/all/resources/service/img/like_blank.png" style="width:35px; height:35px;">
											</p>
											<p id="e_like_heart_y" class="e_like_heart">
												<img src="/all/resources/service/img/like_full.png" style="width:35px; height:35px;">
											</p>
										<%
											} else if (request.getAttribute("like_click")!=null
											&& request.getAttribute("like_click").equals("N")
											&& (e_MemberDTO)session.getAttribute("user") !=null) {
										%>
											<!-- 좋아요 눌러져 있지 않음 -->
											<input type="hidden" id="heart_click" value="N">
											<p id="e_like_heart_n" class="e_like_heart">
												<img src="/all/resources/service/img/like_blank.png" style="width:35px; height:35px;">
											</p>
											<p id="e_like_heart_y" class="e_like_heart" style="display:none;">
												<img src="/all/resources/service/img/like_full.png" style="width:35px; height:35px;">
											</p>
										<%
											} else {
										%>
											<!-- 로그인 안 되어있음 (누를 수 없게 함) -->
											<p class="e_like_heart">
												<img src="/all/resources/service/img/like_blank.png" style="width:35px; height:35px;">
											</p>
											<!-- null 값 방지 -->
											<input type="hidden" id="heart_click" value="none">
											<p id="e_like_heart_n" class="e_like_heart" style="display:none;">
												<img src="/all/resources/service/img/like_blank.png" style="width:35px; height:35px;">
											</p>
											<p id="e_like_heart_y" class="e_like_heart" style="display:none;">
												<img src="/all/resources/service/img/like_full.png" style="width:35px; height:35px;">
											</p>
										<%
											}
										%>
											<p class="e_like_num"><%=s_dto.getLike_check()%></p>
									</div>
									<!-- 싫어요 -->
									<div class="e_content_dislike">
										<%
											// 초기화 : 싫어요 눌러져 있는지 확인
											if (request.getAttribute("dislike_click")!=null
											&& request.getAttribute("dislike_click").equals("Y")
											&& (e_MemberDTO)session.getAttribute("user") !=null) {
										%>
											<!-- 싫어요 눌러져 있음 -->
											<input type="hidden" id="heart_click" value="Y">
											<p id="e_dislike_heart_n" class="e_dislike_heart" style="display:none;">
												<img src="/all/resources/service/img/dislike_blank.png" style="width:35px; height:35px;">
											</p>
											<p id="e_dislike_heart_y" class="e_dislike_heart">
												<img src="/all/resources/service/img/dislike_full.png" style="width:35px; height:35px;">
											</p>
										<%
											} else if (request.getAttribute("dislike_click")!=null
											&& request.getAttribute("dislike_click").equals("N")
											&& (e_MemberDTO)session.getAttribute("user") !=null) {
										%>
											<!-- 싫어요 눌러져 있지 않음 -->
											<input type="hidden" id="heart_click" value="N">
											<p id="e_dislike_heart_n" class="e_dislike_heart">
												<img src="/all/resources/service/img/dislike_blank.png" style="width:35px; height:35px;">
											</p>
											<p id="e_dislike_heart_y" class="e_dislike_heart" style="display:none;">
												<img src="/all/resources/service/img/dislike_full.png" style="width:35px; height:35px;">
											</p>
										<%
											} else {
										%>
											<!-- 로그인 안 되어있음 (누를 수 없게 함) -->
											<p class="e_like_heart">
												<img src="/all/resources/service/img/dislike_blank.png" style="width:35px; height:35px;">
											</p>
											<!-- null 값 방지 -->
											<input type="hidden" id="heart_click" value="none">
											<p id="e_dislike_heart_n" class="e_dislike_heart" style="display:none;">
												<img src="/all/resources/service/img/dislike_blank.png" style="width:35px; height:35px;">
											</p>
											<p id="e_dislike_heart_y" class="e_dislike_heart" style="display:none;">
												<img src="/all/resources/service/img/dislike_full.png" style="width:35px; height:35px;">
											</p>
										<%
											}
										%>
											<p class="e_dislike_num"><%=s_dto.getDislike_check()%></p>
									</div>
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
						
						<!-- 댓글 -->
						<div class="e_comment">
							<!-- 데이터 전달용 -->
							<input type="hidden" id="hidden_bno" value="<%=s_dto.getBno()%>">
							<%
								if (((ArrayList<e_SvCommentDTO>)request.getAttribute("comment_list")).size() == 0) {
							%>
							<!-- 댓글 개수 -->
							<div class="com_num">
								<span class="basic_span">댓글</span><span class="c_n_color">0</span><span class="basic_span">개</span>
							</div>
							<%
								} else if (((ArrayList<e_SvCommentDTO>)request.getAttribute("comment_list")).size() != 0) {
									List<e_SvCommentDTO> comment_list = new ArrayList<e_SvCommentDTO>();
									comment_list = (ArrayList<e_SvCommentDTO>)request.getAttribute("comment_list");
							%>
							<!-- 댓글 개수 -->
							<div class="com_num">
								<span class="basic_span">댓글</span><span class="c_n_color"><%=comment_list.size()%></span><span class="basic_span">개</span>
							</div>
							<%
									for (int i=0; i<comment_list.size(); i++){
										e_SvCommentDTO s_commentDTO = new e_SvCommentDTO();
										s_commentDTO = comment_list.get(i);
							%>
							<!-- 댓글 리스트 -->
							<div class="com_list">
							<%
										// 댓글일 경우
										if(s_commentDTO.getType_code().equals("comment")
												|| s_commentDTO.getType_code().equals("comment_update")){
							%>
								<!-- 댓글 -->
								<!-- 댓글 : 기본 -->
								<div class="c_l_comment">
									<!-- 프로필 이미지 -->
									<div class="com_profile">
										<img src="/all/service/load-proimg?fileName=<%=s_commentDTO.getPro_img()%>">
									</div>
									<!-- 프로필 옆 -->
									<!-- 닉네임, 작성시간 -->
									<div class="com_pro_next">
										<div class="com_nick_time">
											<!-- 닉네임 -->
											<div class="com_nickname">
												<span class="nickname"><%=s_commentDTO.getNickname()%></span>
												<%
													if(s_commentDTO.getMember_no() == s_dto.getMember_no()){
												%>
												<span class="nickname_writer">작성자</span>
												<%
													}
												%>
											</div>
											<!-- 작성 시간 -->
											<div class="com_createtime">
												<%=s_commentDTO.getCreate_time()%>
												<%
													if (s_commentDTO.getType_code().equals("comment_update")){
												%>
												<span style="padding-left:3px; font-size:11px; color:gray;">수정됨</span>
												<%
													}
												%>
											</div>
										</div>
										<!-- 댓글 내용 -->
										<div class="com_content">
											<pre><%=s_commentDTO.getComment_code()%></pre>
										</div>
										<%
											// 로그인 유무
												if((e_MemberDTO)session.getAttribute("user") !=null){
           											m_dto = (e_MemberDTO)session.getAttribute("user");
													// 로그인한 정보와 비교
													if(m_dto.getMember_no()==s_commentDTO.getMember_no()){
										%>
										<!-- 버튼 -->
										<div class="com_btn">
											<input type="hidden" value="<%=s_commentDTO.getC_code()%>">
											<input type="hidden" value="<%=s_commentDTO.getNickname()%>">
											<input type="hidden" value="<%=s_commentDTO.getComment_code()%>">
											<!-- 답글 -->
											<div class="com_btn_reply">
												<a>답글</a>
											</div>
											<!-- 수정 -->
											<div class="com_btn_fix">
												<a>수정</a>
											</div>
											<!-- 삭제 -->
											<div class="com_btn_del">
												<a>삭제</a>
											</div>
										</div>
										<%
													} else {
										%>
										<!-- 버튼 -->
										<div class="com_btn">
											<input type="hidden" value="<%=s_commentDTO.getC_code()%>">
											<input type="hidden" value="<%=s_commentDTO.getNickname()%>">
											<input type="hidden" value="<%=s_commentDTO.getComment_code()%>">
											<!-- 답글 -->
											<div class="com_btn_reply">
												<a>답글</a>
											</div>
										</div>
										<%
													}
												}
										%>
									</div>
								</div>
								<%
										// 답글일 경우
										} else if (s_commentDTO.getType_code().equals("reply")
												|| s_commentDTO.getType_code().equals("reply_update")){
								%>
								<!-- 답글일 경우 -->
								<div class="c_l_reply">
									<!-- 꺽쇠 여백 -->
									<div class="reply_blank">
										↳
									</div>
									<!-- 프로필 이미지 -->
									<div class="com_profile">
										<img src="/all/service/load-proimg?fileName=<%=s_commentDTO.getPro_img()%>">
									</div>
									<!-- 프로필 옆 -->
									<!-- 닉네임, 작성시간 -->
									<div class="reply_pro_next">
										<div class="reply_nick_time">
											<!-- 닉네임 -->
											<div class="com_nickname">
												<span class="nickname"><%=s_commentDTO.getNickname()%></span>
												<%
													if(s_commentDTO.getMember_no() == s_dto.getMember_no()){
												%>
												<span class="nickname_writer">작성자</span>
												<%
													}
												%>
											</div>
											<!-- 작성 시간 -->
											<div class="com_createtime">
												<%=s_commentDTO.getCreate_time()%>
												<%
													if (s_commentDTO.getType_code().equals("reply_update")){
												%>
												<span style="padding-left:3px; font-size:11px; color:gray;">수정됨</span>
												<%
													}
												%>
											</div>
										</div>
										<!-- 답글 내용 -->
										<div class="reply_content">
											<div style="padding-bottom: 5px; font-weight:bold; color:gray;">
												@<%=s_commentDTO.getTo_nickname()%>
											</div>
											<div><pre><%=s_commentDTO.getComment_code()%></pre></div>
										</div>
										<%
											// 로그인 유무
												if((e_MemberDTO)session.getAttribute("user") !=null){
           											m_dto = (e_MemberDTO)session.getAttribute("user");
													// 로그인한 정보와 비교
													if(m_dto.getMember_no()==s_commentDTO.getMember_no()){
										%>
										<!-- 버튼 -->
										<div class="reply_btn">
											<input type="hidden" value="<%=s_commentDTO.getOrigin_code()%>">
											<input type="hidden" value="<%=s_commentDTO.getNickname()%>">
											<input type="hidden" value="<%=s_commentDTO.getComment_code()%>">
											<input type="hidden" value="<%=s_commentDTO.getC_code()%>">
											<!-- 답글 -->
											<div class="reply_btn_reply">
												<a>답글</a>
											</div>
											<!-- 수정 -->
											<div class="reply_btn_fix">
												<a>수정</a>
											</div>
											<!-- 삭제 -->
											<div class="reply_btn_del">
												<a>삭제</a>
											</div>
										</div>
										<%
													} else {
										%>
										<!-- 버튼 -->
										<div class="reply_btn">
											<input type="hidden" value="<%=s_commentDTO.getOrigin_code()%>">
											<input type="hidden" value="<%=s_commentDTO.getNickname()%>">
											<input type="hidden" value="<%=s_commentDTO.getComment_code()%>">
											<!-- 답글 -->
											<div class="reply_btn_reply">
												<a>답글</a>
											</div>
										</div>
										<%
													}
												}
										%>
									</div>
								</div>
								<%
										}
								%>
							</div>
							<%
									}
								}
								// 로그인 되어있지 않을때
								if((e_MemberDTO)session.getAttribute("user") !=null){
							%>
							<!-- 댓글 입력창 -->
							<div class="com_input">
								<!-- 댓글 입력창 -->
								<div class="c_i_textarea">
									<textarea wrap="hard" placeholder="댓글을 작성해 주세요."
										name="description" id="e_cont_detail_input"></textarea>
								</div>
								<!-- 댓글 등록 버튼 -->
								<div class="c_i_btn">
									<button class="c_i_btn_Y">등록</button>
								</div>
							</div>
							<%
								} else {
							%>
							<!-- 댓글 입력창 -->
							<div class="com_input">
								<!-- 댓글 입력창 -->
								<div class="c_i_textarea">
									<textarea wrap="hard" placeholder="로그인 후 댓글을 작성해 주세요."
										name="description" id="e_cont_detail_input" disabled></textarea>
								</div>
								<!-- 댓글 등록 버튼 -->
								<div class="c_i_btn">
									<button class="c_i_btn_N">등록</button>
								</div>
							</div>
							<%
								}
							%>
						</div>
						<!-- 댓글 끝 -->

						<div class="e_button">
						<%
							// 게시판 관련 메서드 불러오기
							e_ServiceService s_service = new e_ServiceServiceimpl();
							if ((e_MemberDTO)session.getAttribute("user") !=null){
								if(s_dto.getAdmin_type().equals("origin")){
						%>
							<!-- 답글 쓰기, 게시물 수정, 삭제 버튼 -->
							<div class="e_hidden" id="e_hidden_reply">
								<form name="e_btn_reply_form">
									<!-- 게시판 데이터 보내기 -->
									<input type="hidden" name="e_btn" value="reply">
									<input type="hidden" name="e_bno" value="<%=s_dto.getBno()%>">
									<input type="submit" id="e_btn_reply" class="e_btn_css" value="답글 쓰기">
								</form>
							</div>
						<%
								}
						%>
						<%
							// 글 작성자와 동일한지
							// 게시물 수정, 삭제 버튼 보이기 여부
							if(s_dto.getNickname().equals(m_dto.getNickname())){
						%>
							<div class="e_hidden" id="e_hidden_fix">
								<form name="e_btn_fix_form">
									<!-- 게시판 데이터 보내기 -->
									<input type="hidden" name="e_btn" value="fix">
									<input type="hidden" name="e_bno" value="<%=s_dto.getBno()%>">
									<input type="submit" id="e_btn_fix" class="e_btn_css" value="글 수정">
								</form>
							</div>
							<div class="e_hidden" id="e_hidden_del">
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

</body>
</html>