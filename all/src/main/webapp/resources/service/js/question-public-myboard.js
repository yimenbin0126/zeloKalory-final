window.onload = function(){
	select();
	form();
	del();
	search();
}

// 전체 선택
function select() {
	document.querySelector('.checkbox_class input').addEventListener('click', ()=>{
		// 전체 선택으로 바꾸기
		var c_list = document.querySelectorAll('.check_list');
		for(var i=0; i<c_list.length; i++){
			c_list[i].checked = document.querySelector('.checkbox_class input').checked;
		};
	});
}

// 글상세 데이터 전달
function form(){
	// 초기화
	document.querySelector('#e_bno_val').value = "none";
	
	// 게시물을 각각 선택시 글상세로 넘어감
	const e_blist_title = document.querySelectorAll(".blist_title");
	for (const e_title of e_blist_title) {
	  e_title.addEventListener('click', function(e) {
	    var val = e.target.parentElement.parentElement.firstElementChild.value;
		document.querySelector('#e_bno_val').value = val;
		sub();
	  })
	}

	// 글 상세보기 이동
	function sub() {
		var e_bno_val_form = document.e_bno_val_form;
		e_bno_val_form.method = "get";
		e_bno_val_form.action = "/all/service/question-public-detail";
		e_bno_val_form.submit();
	}
	
}

// 선택 글 삭제
function del(){
	// 글 삭제 버튼
	document.querySelector('.e_hd_top_del').addEventListener('click', (event)=>{
		var e_btn_delete_form = document.e_btn_delete_form;
		let data = {};
		// 선택한 파일 있으면 담기
		let array_del = new Array();
		let length_del = $('input[name=check_list]:checked').length;
	    if (length_del >= 1) {
	    	$("input:checkbox[name=check_list]:checked").each(function() {
            	array_del.push($(this).val());
			});
			
			var con_del = confirm('정말 글을 삭제하시겠습니까?');
			if (con_del == true) {
					// 글쓰기 유형 선택
				$.ajax({
				    url: '/all/service/question-public-myboard-delete',
				    type: 'POST',
				    dataType:'text',
				    contentType: 'application/json',
				    data: JSON.stringify(array_del),
				    success: function(result){
				    	console.log(result);
				    	if (result=="X"){
				    		event.preventDefault();
	        				alert('삭제할 게시물을 선택해 주세요.');
				    	} else {
					    	location.href="/all/service/question-public-myboard";
				    	}
				    }
				});
			} else {
				event.preventDefault();
			}
        } else {
        	// 없으면 담으라고 명령하기
        	event.preventDefault();
        	alert('삭제할 게시물을 선택해 주세요.');
        }
	});
}

// 검색 결과 전달
function search(){
	
	// 엔터 시 클릭
	document.querySelector('#s_content_input').addEventListener('keydown',function(event){
        if(event.keyCode ==13){
        	event.preventDefault();
            document.querySelector('#s_content_btn').click();
        }
    });
	
	// 버튼 누를 시 실행
	document.querySelector('#s_content_btn').addEventListener('click', ()=>{
		// 검색 기간
		let e_search_time = document.querySelector('#e_search_time_sel').value;
		
		// 검색 타입
		let e_search_type = document.querySelector('#e_search_type_sel').value;
		
		// 검색 내용
		let e_search_content = document.querySelector('#s_content_input').value;
		
		let url = "/all/service/question-public-myboard-search";
		
		url += "?search_time="+e_search_time;
		url += "&search_type="+e_search_type;
		url += "&search_content="+e_search_content;
		
		location.href= url;
	});
}