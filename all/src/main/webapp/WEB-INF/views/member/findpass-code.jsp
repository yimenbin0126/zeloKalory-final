<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.zerocalorie.member.dto.e_MemberDTO" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link href="/all/resources/member/css/header.css" rel="stylesheet">
    <link href="/all/resources/member/css/findpass_code.css" rel="stylesheet">
    <script src="/all/resources/member/js/findpass_code.js"></script>
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
            <li class="j_menu3 j_menu">공지사항</li>
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
            <input type ='button' class = "e_btn2 e_btn" onclick="location.href='/all/mypage'" value="마이페이지">
            <!-- 나타나는 부분 끝 -->
        </div>
        <%
        	} else {
        %>
        <ul id="j_list">
            <li class="j_menu1 j_menu" onclick="location.href='/all/cal/<%=m_dto.getId()%>'">캘린더</li>
            <li class="j_menu2 j_menu" onclick="location.href='/all/community/listArticles.do'">커뮤니티</li>
            <li class="j_menu3 j_menu">공지사항</li>
            <li class="j_menu5 j_menu" onclick="location.href='/all/service/allService'">고객센터</li>
        </ul>
        <div id = j_nav>
        	<!-- null 오류 방지용 시작 -->
            <input type ='hidden' class = "e_btn e_btn" onclick="location.href='/all/member/logout'" value="로그아웃">
            <input type ='hidden' class = "e_btn2 e_btn" onclick="location.href='/all/mypage'" value="마이페이지">               
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
				<!-- 로고 -->
				<div class="e_logo">아이디/비밀번호 찾기</div>
				<%
					if (request.getAttribute("code") != null
					&& (e_MemberDTO)request.getAttribute("m_dto") != null){
						e_MemberDTO _m_dto = new e_MemberDTO();
						_m_dto = (e_MemberDTO)request.getAttribute("m_dto");
				%>
					<input type="hidden" id="code" value="<%=request.getAttribute("code")%>">
					<input type="hidden" id="member_no" value="<%=_m_dto.getMember_no()%>">
				<%
					}
				%>
				<!-- 내용 -->
				<div class="e_hd_content">
					<!-- 인증 -->
					<div class="e_h_content">
						이메일로 전송한<br>
						인증번호를 정확하게 입력해주세요.
					</div>
					<!-- 인증코드 입력 -->
					<div class="e_h_input" id="e_h_input_new">
						<input type="hidden" id="e_h_input_code_val" value="">
						<div class="input_code">
							<input type="text" id="e_input_code"
								placeholder="인증번호를 입력해 주세요." onfocus="this.placeholder=''"
								onblur="this.placeholder='인증번호를 입력해 주세요.'">
						</div>
						<div class="input_sub" id="input_sub_code">
							<input type="submit" id="e_input_sub" value="인증하기">
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

</body>

</html>