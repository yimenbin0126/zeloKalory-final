<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.zerocalorie.member.dto.e_MemberDTO,com.zerocalorie.svservice.dto.e_ServiceDTO,com.zerocalorie.svservice.dto.e_SvFileDTO,com.zerocalorie.svservice.service.e_ServiceService,com.zerocalorie.svservice.service.e_ServiceServiceimpl,
	java.util.List, java.util.ArrayList" %>
<title>고객센터</title>
<link href="/all/resources/service/css/question-public-fix.css" rel="stylesheet">
<script src="/all/resources/service/js/question-public-fix.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
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

					<%
						// 데이터 불러오기 위한 선언
						e_ServiceDTO s_dto = new e_ServiceDTO();
						s_dto = (e_ServiceDTO)request.getAttribute("s_dto");
						// 글쓰기 줄바꿈 html 안보이게 저장
						s_dto.setDescription(s_dto.getDescription().replace("<br>","\r\n"));
					%>
					<!-- 오른쪽 내용 -->
					<div class="e_right">
						<!-- 상단 -->
						<div class="e_header">
							<div class="e_hd_top">고객센터 &gt; 공개 건의함 &gt; 글수정</div>
							<div class="e_hd_top_title">글수정</div>
						</div>

						<!-- 카데고리별 -->
						<form name="e_fix_form">
							<div class="e_content">
								<!-- 글쓰기 제목 -->
								<div class="e_con_title">
									<div class="e_ti_title">제목</div>
									<div class="e_ti_detail">
										<input type="text" name="title"
											id="e_ti_detail_input" value="<%=s_dto.getTitle()%>">
									</div>
								</div>

								<!-- 글쓰기 내용 -->
								<div class="e_con_content">
									<div class="e_cont_title">내용</div>
									<div class="e_cont_detail">
										<textarea name="description"
											id="e_cont_detail_input"><%=s_dto.getDescription()%></textarea>
									</div>
								</div>
								
								<!-- 첨부파일 -->
								<div class="e_con_file">
									<div class="e_file_title">첨부파일</div>
									<div class="e_file_detail">
										<!-- 파일 업로드 -->
										<label class="e_file_btn" for="e_file_detail_input">
											파일 업로드
										</label>
										<input type="file" name="file"
											id="e_file_detail_input" style="display:none" multiple>
									</div>
								</div>
								<!-- 파일 업로드 -->
								<div class="e_con_file_upload">
									<div class="e_file_title"></div>
									<div class="file_group">
									<!-- 업로드 된 파일 -->
									<%
										// 게시판 첨부파일 객체
										List<e_SvFileDTO> filelist = new ArrayList<e_SvFileDTO>();
										if (((ArrayList<e_SvFileDTO>)request.getAttribute("filelist")).size() != 0) {
											filelist = (ArrayList<e_SvFileDTO>)request.getAttribute("filelist");
											for (int i=0; i<filelist.size(); i++){
												e_SvFileDTO s_filedto = new e_SvFileDTO();
												s_filedto = filelist.get(i);
									%>
										<div id="prev_file<%=s_filedto.getFile_order()%>" class="fileList">
								           <p class="filename"><%=s_filedto.getFilename()%></p>
								           <a class="filedelete" onclick="prev_deleteFile(<%=s_filedto.getFile_order()%>);">❌</a>
								        </div>
									<%
											}
										}
									%>
									</div>
								</div>
							</div>

							<!-- 글쓰기 버튼 -->
							<div class="e_button">
								<div class="e_btn_fix">
									<!-- 게시판 데이터 보내기 -->
									<input type="hidden" name="bno" id="e_bno"
										value="<%=s_dto.getBno()%>">
										<input type="submit" value="수정 완료" id="e_btn_fix_btn">
								</div>
							</div>
						</form>
					</div>
					
				</div>

			</div>
		</div>
	</section>