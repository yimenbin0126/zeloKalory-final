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
    <link href="/all/resources/member/css/findpass_new.css" rel="stylesheet">
    <script src="/all/resources/member/js/findpass_new.js"></script>
    <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>

<body>
    <section>
		<div id="j_wrap">
			<div id="j_box">
				<!-- 로고 -->
				<div class="e_logo">아이디/비밀번호 찾기</div>
				
				<!-- 내용 -->
				<div class="e_hd_content">
					<!-- 설명 -->
					<div class="e_h_content">
						<p>
						앞으로 쓰실 고객님의<br>
						새로운 비밀번호를 입력해주세요.<br>
						</p>
						<p style="color:gray;">
						(*특수문자(!@#*),
							영문 대문자, 영문 소문자,<br>숫자 최소 한개 이상을 넣어 혼합해 5~9자리로 만들어주세요.
						</p>
					</div>
					<!-- 비밀번호 지정 -->
					<div class="e_h_input">
						<div class="e_input_code">
							<input type="password" id="e_input_code"
								placeholder="새로운 비밀번호를 입력해 주세요." onfocus="this.placeholder=''"
								onblur="this.placeholder='새로운 비밀번호를 입력해 주세요.'">
						</div>
						
						<div class="e_input_code_re">
							<input type="password" id="e_input_code_re"
								placeholder="새로운 비밀번호를 다시 입력해 주세요." onfocus="this.placeholder=''"
								onblur="this.placeholder='새로운 비밀번호를 다시 입력해 주세요.'">
						</div>
						<div class="input_sub" id="input_sub_new">
							<input type="submit" id="e_input_email" value="비밀번호 변경">
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

</body>

</html>