window.onload = function(){
	form();
}

// 뷰 데이터 전달
function form(){


	// 관리자 접속 시, 버튼 보이게 하기
	if (document.querySelector('#e_hidden_YN').value == "Y"){
		document.querySelector('#e_hidden_fix').style.display = "block";
		document.querySelector('#e_hidden_del').style.display = "block";
	} else {
		document.querySelector('#e_hidden_fix').style.display = "none";
		document.querySelector('#e_hidden_del').style.display = "none";
	}
	
	// 좋아요 버튼 누를시
	// 좋아요 X -> 좋아요 누르기
	document.querySelector('#e_like_heart_n').addEventListener('click', (event)=>{
		$.ajax({
	        url: "/all/service/question-detail",
	        type:"POST",
	        data: {
	            e_heart_check : "Y",
	            e_bno : document.querySelector('#e_bno_value').value
	        },
	        success : function(data){
	        	// 객체 값 가져오기
	        	// 하트 칠하기
	        	document.querySelector('#e_like_heart_n').style.display = "none";
	        	document.querySelector('#e_like_heart_y').style.display = "block";
	        	// 숫자 증가
	        	var heart_num = document.querySelector('.e_like_num').innerText;
	        	document.querySelector('.e_like_num').innerText = parseInt(heart_num) + 1;
	        },
	        });
	});
	
	// 좋아요 O -> 좋아요 누르기
	document.querySelector('#e_like_heart_y').addEventListener('click', (event)=>{
		$.ajax({
	        url: "/all/service/question-detail",
	        type:"POST",
	        data: {
	            e_heart_check : "N",
	            e_bno : document.querySelector('#e_bno_value').value
	        },
	        success : function(data){
	        	// 하트 칠하기
	        	document.querySelector('#e_like_heart_n').style.display = "block";
	        	document.querySelector('#e_like_heart_y').style.display = "none";
	        	// 숫자 감소
	        	var heart_num = document.querySelector('.e_like_num').innerText;
	        	document.querySelector('.e_like_num').innerText = parseInt(heart_num) - 1;
	        },
	        });
	});
	
	// 버튼 클릭
	if(document.querySelector('.e_hidden').style.display == "block") {

		// 글 수정 버튼
		document.querySelector('#e_btn_fix').addEventListener('click', (event)=>{
			var e_btn_fix_form = document.e_btn_fix_form;
			var con_fix = confirm('정말 글을 수정하시겠습니까?');
			if (con_fix == true) {
				e_btn_fix_form.method="post";
				e_btn_fix_form.action="/all/service/question-detail-button";
				e_btn_fix_form.submit();
			} else {
				event.preventDefault();
			}
		});
		
		// 글 삭제 버튼
		document.querySelector('#e_btn_delete').addEventListener('click', (event)=>{
			var e_btn_delete_form = document.e_btn_delete_form;
			var con_del = confirm('정말 글을 삭제하시겠습니까?');
			if (con_del == true) {
				e_btn_delete_form.method="post";
				e_btn_delete_form.action="/all/service/question-detail-button";
				e_btn_delete_form.submit();
			} else {
				event.preventDefault();
			}
		});
	}
	
}