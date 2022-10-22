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
    <link href="/all/resources/member/css/findid.css" rel="stylesheet">
    <script src="/all/resources/member/js/findid.js"></script>
    <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>

<body>
    <section>
		<div id="j_wrap">
			<div id="j_box">
				<!-- 로고 -->
				<div class="e_logo">아이디/비밀번호 찾기</div>

				<!-- 카데고리 선택 -->
				<div class="e_hd_choice">
					<div id="e_choice_id">아이디 찾기</div>
					<div id="e_choice_pass" onclick="location.href='/all/member/findpass'">비밀번호 찾기</div>
				</div>
				
				<!-- 내용 -->
				<div class="e_hd_content">
					<!-- 설명 -->
					<div class="e_h_content">
						아이디를 찾으려면<br>
						아래 칸에 이메일을 입력하세요.
					</div>
					<!-- 이메일 주소 -->
					<div class="e_h_input">
						<div class="input_text">
							<input type="email" id="e_input_email"
								placeholder="이메일을 입력해 주세요." onfocus="this.placeholder=''"
								onblur="this.placeholder='이메일을 입력해 주세요.'">
						</div>
						<div class="input_sub">
							<input type="submit" id="e_input_sub" value="전송하기">
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

</body>

</html>