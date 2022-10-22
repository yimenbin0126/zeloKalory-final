<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.*"
	import=" com.zerocalorie.calender.dto.*"
	import="com.zerocalorie.member.dto.*"
%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% // 소연
	e_MemberDTO sessionUser = (e_MemberDTO)session.getAttribute("user"); %>	
    <title>마이페이지</title>
    <link rel="stylesheet" href="/all/resources/mypage/css/mypage.css">
    
    <style>
    #mod_weight{
    	display: none;
    }
    #mod_msg{
    	color: red;
    	font-size: 12px;
    }
    #yoo_cur_btn{
    border: 0px;
    color: white;
    background-color: black;
    width: 50px;
    height: 30px;
    background-color: rgb(39, 56, 104);
}
</style>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.js">// jquery로딩완료</script>
	
<script>
    window.onload=function(){
    	yoo_bind();
    }

	function yoo_bind() {
		let option = {
			url: "/all/mypage/chartJSON",
			type: "get",
			dataType: 'json',
			data: {"member_no": <%=sessionUser.getMember_no()%>},
			success: function (data) {
				try {
					console.log(data);
					yoo_drawChart(data);
					drawSelect(data);
					document.querySelector("#yoo_select_date").addEventListener("change", function (item) {
						console.log("value ::::", document.querySelector("select option:checked").value);
						let index = document.querySelector("select option:checked").value;
						yoo_drawChart(data, data.length - index);
					});
				} catch (err) {
					console.log("ERR", err);
				}
			},
			error: function (err) { 
				console.log("ERR view click", err);
			},
			complete: function (data) {
				console.log("완료", data);
			}
		}
		$.ajax(option);
		return 1;
	}

	function yoo_drawChart(data, date_count = data.length) {
		console.log("date_count", date_count);
		let current_weight = [];
		let target_weight = [];
		let weight_date = [];
		
		// 오늘 날짜 생성
		let dateInfo = dateInfo_fn();


		let j = 0;
		for (let i =data.length - date_count; i < data.length; i++) {
			console.log("j : ", j, ", i : ", i,", data.length : ",data.length, ", date_count : ",date_count );

				console.log("data[i].member_no");
				console.log(data[i].member_no);
				current_weight[j] = data[i].current_weight;
				target_weight[j] = data[i].target_weight;
				weight_date[j] = data[i].weight_date;

			// (입력)직전 목표 표시 부분
			document.querySelector("#yoo_TARGET_WEIGHT_input").setAttribute("value", target_weight[j]);
			
			// (수정)직전 목표 표시 부분
			document.querySelector("#yoo_CURRENT_WEIGHT_input_mod").setAttribute("value",current_weight[j]);
			document.querySelector("#yoo_TARGET_WEIGHT_input_mod").setAttribute("value",target_weight[j]);

			
			// ajax에서 가져온 값중에 오늘 날짜의 값이 있으면 입력창이 수정창으로 바뀜
			if(weight_date[j]==dateInfo){
				input_to_mod();
			}
			j++;
		}

		let chartStatus = Chart.getChart("yoo_canvas");
		if (chartStatus != undefined) {
			chartStatus.destroy();
		}

		let ctx = document.getElementById('yoo_canvas').getContext('2d');
		let mixedChart = new Chart(ctx, {
			data: {
				datasets: [
					{
						type: 'bar',
						label: 'today 몸무게',
						data: current_weight,
						backgroundColor: [
							//색상
							'rgba(54, 79, 145, 0.2)',
							'rgba(55, 109, 198, 0.2)',
							'rgba(7, 147, 233, 0.2)',
							'rgba(55, 99, 200, 0.2)',
							'rgba(54, 79, 145, 0.2)',
							'rgba(7, 147, 233, 0.2)',
							'rgba(80, 120, 216, 0.2)'],
						borderColor: [
							//경계선 색상
							'rgba(50, 66, 118, 1)', 'rgba(50, 66, 118, 1)',
							'rgba(50, 66, 118, 1)',
							'rgba(50, 66, 118, 1)',
							'rgba(50, 66, 118, 1)',
							'rgba(50, 66, 118, 1)'],
						borderWidth: 1
					}, {
						type: 'line',
						label: '목표 몸무게',
						data: target_weight,
						backgroundColor: ['rgba(63, 72, 204, 0.9)'],
						borderColor: ['rgba(63, 72, 204, 0.9)'],
						borderWidth: 2,
					}],
				labels: weight_date
			},
		});
	}
	function drawSelect(data) {
		let weight_date = [];
		let html = "";
		$(data).each(function (index, item) { // 항목 추가
			html += '<option class="yoo_select_date" value="' + index + '">' + item.weight_date + '</option>';
		});
		$("#yoo_select_date").append($(html));

	}
	// 몸무게 입력 클릭했을때
	function click_add_weight(){
		
		// 오늘의 몸무게
		let current_weight = document.querySelector("#yoo_CURRENT_WEIGHT_input").value;
		
		// 목표 몸무게
		let target_weight = document.querySelector("#yoo_TARGET_WEIGHT_input").value;
		
		// 정규식 숫자만 
		let numbers = /^[0-9]*$/;	
		
		if( ((! numbers.test(current_weight)) ||  (! numbers.test(target_weight))) ){
			alert('숫자만 입력해 주세요');
			
		// 오늘의 몸무게, 또는 목표 몸무게가 빈칸이면		
		}else if((current_weight=="") ||(target_weight=="")){
			alert('숫자를 입력해 주세요');
			
		}else{

	 		document.add_weight.method = "post";
			document.add_weight.action = "weightAdd";
			document.add_weight.submit(); 	 
		}	
	}
	
	
	// 몸무게 수정 클릭했을때
	function click_mod_weight(){
		
		
		// 오늘의 몸무게
		let current_weight = document.querySelector("#yoo_CURRENT_WEIGHT_input_mod").value;
		
		// 목표 몸무게
		let target_weight = document.querySelector("#yoo_TARGET_WEIGHT_input_mod").value;
		
		// 정규식 숫자만 
		let numbers = /^[0-9]*$/;	
		
		// 오늘의 몸무게, 또는 목표 몸무게가 숫자가 아닐때 
		console.log( numbers.test(current_weight));
		console.log( numbers.test(target_weight));
		if( ((! numbers.test(current_weight)) ||  (! numbers.test(target_weight))) ){
			alert('숫자만 입력해 주세요');
			
		// 오늘의 몸무게, 또는 목표 몸무게가 빈칸이면		
		}else if((current_weight=="") ||(target_weight=="")){
			alert('숫자를 입력해 주세요');
			
		}else{

	 		document.mod_weight.method = "post";
			document.mod_weight.action = "weightMod";
			document.mod_weight.submit(); 	 
		}	
	}
		

	function dateInfo_fn() {
<%
		GregorianCalendar now = new GregorianCalendar();
		String dateInfo = String.format("%d-%02d-%02d", now.get(1), now.get(2)+1, now.get(5));
		
%>
		// 오늘 날짜 생성
		dateInfo = '<%=dateInfo%>';
		return dateInfo;
	}	
	
	function input_to_mod(){
		$("#mod_weight").show();
		$("#add_weight").hide();
		
	}
</script>
    <div id="j_wrap">
        <div id="j_box">
        
        	<!--  지수씨 부분 시작 -->    
			<div id="j_sec1">
			<!--//////////// 지수씨 파트 주석을 다 칠수 없어서 나머지는 일단 다른곳에 저장 해놓음 //////////////-->
		<%	// 지수씨
			// MemberDAO_e dao = new MemberDAO_e();%>
		<%
		/*
                // 데이터 불러오기 위한 선언
                MemberDTO_e m_dto = new MemberDTO_e();
                MemberDAO_e m_dao = new MemberDAO_e();
                m_dto = (MemberDTO_e)session.getAttribute("user");
        */
        %>
			

			</div>
			<!--  지수씨 부분 끝 -->
        
        
            <!-- 소연씨 파트 -->
            <div id="yoo_div">
            	
           
	            <h3>내 몸무게 보기</h3> 
	            
				<div id="yoo_weight_input">
					<form id="add_weight"  name ="add_weight">
						오늘의 몸무게 : <input id="yoo_CURRENT_WEIGHT_input" name="current_weight" type="text" maxlength="5">
						kg, 
						목표 몸무게: <input id="yoo_TARGET_WEIGHT_input" name="target_weight" type="text" maxlength="5"> kg 
						<input id="yoo_cur_btn" type="button" value="입력" onclick="click_add_weight()">
						<input type="hidden" name="command" value="weightAdd" />
					</form>
					
					<form id="mod_weight" name ="mod_weight">
						오늘의 몸무게 : <input id="yoo_CURRENT_WEIGHT_input_mod" name="current_weight" type="text" maxlength="5">
						kg, 
						목표 몸무게: <input id="yoo_TARGET_WEIGHT_input_mod" name="target_weight" type="text" maxlength="5"> kg 
						<input id="yoo_cur_btn" type="button" value="수정" onclick="click_mod_weight()">
						<br><span id=mod_msg>오늘은 이미 몸무게를 입력 하셨습니다. 수정만 가능합니다.</span>
						<input type="hidden" name="command" value="weightMod" />
					</form>
				</div>
				
				<br>
	            <select id="yoo_select_date"></select>
				<canvas id="yoo_canvas"></canvas>
			</div>
        </div>
	</div>