window.onload = function(){
	form();
	search();
}

// 글상세 데이터 전달
function form(){
	// 초기화
	document.querySelector('#e_bno_val').value = "none";
	
	// 게시물을 각각 선택시 글상세로 넘어감
	const e_blist_title = document.querySelectorAll(".blist_title");
	for (const e_title of e_blist_title) {
	  e_title.addEventListener('click', function(e) {
	    var val = e.target.parentElement.firstElementChild.value;
		document.querySelector('#e_bno_val').value = val;
		sub();
	  })
	}

	// 글 상세보기 이동
	function sub() {
		var e_bno_val_form = document.e_bno_val_form;
		e_bno_val_form.method = "get";
		e_bno_val_form.action = "/all/service/question-detail";
		e_bno_val_form.submit();
	}
	
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
		
		let url = "/all/service/question-member-search";
		
		url += "?search_time="+e_search_time;
		url += "&search_type="+e_search_type;
		url += "&search_content="+e_search_content;
		
		location.href= url;
	});
}