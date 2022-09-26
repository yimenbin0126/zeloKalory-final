<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="eunbin.DTO.MemberDTO"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인페이지</title>
    <link rel="stylesheet" href="main/main.css">
    <script src="main/main.js"></script>
</head>
<body>
	<!-- 헤더 시작 -->
    <div id="j_hi">
        <!-- <img src="./img/logo.png" id="j_logo"> -->
        
        <ul id="j_list">
            <li class="j_menu1 j_menu">캘린더</li>
            <li class="j_menu2 j_menu">커뮤니티</li>
            <li class="j_menu3 j_menu">공지사항</li>
            <li class="j_menu4 j_menu">운동</li>
            <li class="j_menu5 j_menu" onclick="location.href='/all/service/allService'">고객센터</li>
        </ul>
        <%
             		MemberDTO m_dto = new MemberDTO();
             		
             		// 로그인 유무
                	if((MemberDTO)session.getAttribute("user") !=null){
                		m_dto = (MemberDTO)session.getAttribute("user");
        %>
        <div id = e_nav>
        	<div id="e_welcome">
        		<%=m_dto.getNickname()%>님 환영합니다.
        	</div>
        	<form name="e_nav_btn">
	        	<input type ="hidden" name="e_logout" value="Y">                   
        	</form>
        	<!-- null 오류 방지용 시작 -->
        	<input type ='hidden' class = "j_btn1 j_btn" onclick="location.href='/all/login'" value="로그인">
            <input type ='hidden' class = "j_btn2 j_btn" onclick="location.href='/all/join'" value="회원가입">
            <!-- null 오류 방지용 끝 -->
            <!-- 나타나는 부분 시작 -->
            <input type ='button' class = "e_btn e_btn" onclick="location.href='/all/logout'" value="로그아웃">
            <input type ='button' class = "e_btn2 e_btn" onclick="location.href='../mypage/mypage.html'" value="마이페이지">
            <!-- 나타나는 부분 끝 -->
        </div>
        <%
        	} else {
        %>
        <div id = j_nav>
        	<!-- null 오류 방지용 시작 -->
            <input type ='hidden' class = "e_btn e_btn" onclick="location.href='/all/logout'" value="로그아웃">
            <input type ='hidden' class = "e_btn2 e_btn" onclick="location.href='../mypage/mypage.html'" value="마이페이지">               
            <!-- null 오류 방지용 끝 -->
            <!-- 나타나는 부분 시작 -->
            <input type ='button' class = "j_btn1 j_btn" onclick="location.href='/all/login'" value="로그인">
            <input type ='button' class = "j_btn2 j_btn" onclick="location.href='/all/join'" value="회원가입">
            <!-- 나타나는 부분 끝 -->
        </div>
        <%
        	}
        %>
    </div>
    <!-- 헤더 끝 -->

	<div id="j_bg" style="margin-top: 170px;">

		<div class="container">
			<p class="title title1">LET'S
			<p>
		</div>
		<div class="container">
			<p class="title title2">WORKOUT
			<p>
		</div>
		<div class="container">
			<p class="title title3">TOGETHER!
			<p>
		</div>
	</div>


</body>
</html>