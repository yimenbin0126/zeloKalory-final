<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.zerocalorie.mypage.dao.*" %>
<%@page import="com.zerocalorie.member.dto.*" %>
<%@page import="java.util.*" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>프로필 수정</title>
    <link rel="stylesheet" href="/all/resources/mypage/css/editMemberForm.css">
		<%
                // 데이터 불러오기 위한 선언
                e_MemberDTO m_dto = new e_MemberDTO();
                m_dto = (e_MemberDTO)session.getAttribute("user");
        %>

	<div id="j_wrap">
		<div id="j_box">
			<div id="j_sec1">
				<h1><%=m_dto.getNickname() %>님의 프로필 수정</h1>
				<form action="/all/mypage/upload.do" method="post" enctype="multipart/form-data" onSubmit="return profile_img(this)">					
						<div class ="profile">
							<img class="k_profile" src="/all/mypage/download_k.do?fileName=<%= m_dto.getPro_img() %>"><br>
							<input class="k_change_profile" type="file" accept="image/*" name="profileImg"><br>
 							<input class="k_saveimg" type="submit" value="사진 저장">
							<input type="hidden" name="memberId" id="memberId" value="<%=m_dto.getId()%>" /> 							
						</div><!-- 얘 묶어 -->
				</form>
			
				<form name="frmMember" action="/all/mypage/editMemberForm" method="post">
				
					<div class="k_profile_detail">
						<div class="k_t_box">
							<input type="hidden" value="<%= m_dto.getId() %>" name="id">
							<div id="name">
								이름<input type="text" value="<%=m_dto.getName() %>" name="name">
							</div>
							<div id="nickname">
							닉네임<input type="text" value="<%=m_dto.getNickname() %>" name="nickname">
							</div>
						</div>
						<div class="k_t_box">
							<div id="height"> 
							&nbsp;키<input type="text" value="<%=m_dto.getHeight() %>" name="height">
							</div>
							<div id="gender">
							&nbsp;성별&nbsp;&nbsp;<input type="text" value="<%=m_dto.getGender() %>" name="gender">
							</div>
						</div>
						<div class="k_t_box2">
							<div id="birth">
								생년월일 <input type="text" value="<%=m_dto.getBirth() %>" name="birth"><br>
							</div>
							<div id="email">
								이메일&nbsp;&nbsp; <input type="text" value="<%=m_dto.getEmail() %>" name="email"><br>
							</div>
							<div id="tel"> 
							전화번호 <input type="text" value="<%=m_dto.getTel() %>" name="tel">
							</div>
						</div>
					</div>
				<div class="save_btn">
					<input id="k_save_btn" type="submit" value="저장하기"> 
				</div>
				</form>
			</div>
		</div>
	</div>