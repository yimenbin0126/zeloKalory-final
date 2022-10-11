// 전역 변수 선언
// 파일이 늘때마다 고유 번호가 저장됨 (삭제해도 지워지지 않음)
let fileNo = 0;
// 전체 파일 목록을 담을 배열 (삭제해도 지워지지 않음)
let filesArr = new Array();

window.onload = function(){
	document.querySelector("#e_file_detail_input").addEventListener('change', file);
	form();
}

// 첨부 파일
function file() {
	// 첨부파일 최대 개수
	var maxFileCount = 5;
	// 기존 추가된 첨부파일 갯수
    var allFileCount = document.querySelectorAll('.fileList').length;
    // 첨부 가능한 파일 추가 갯수
	var possibleFileCount = maxFileCount - allFileCount;
	// 현재 선택된 첨부파일 갯수
	var choiceFileCount = document.querySelector('#e_file_detail_input').files.length;
    
	// 첨부파일 최대 갯수 한도 초과
    if (possibleFileCount == 0) {
    	alert("첨부파일은 최대 " + maxFileCount + "개 까지만 첨부가 가능합니다.");
    }
    
    // 첨부파일 추가
    // 현재 선택된 파일 갯수, 첨부 가능한 파일 갯수 중 최솟값 (추가 가능한 갯수까지 한정으로 추가) 
	// 다중 선택시 파일 개별 선택
   for (const file of document.querySelector('#e_file_detail_input').files) {
        file.is_delete = false;
        filesArr.push(file);
        // 파일 내용 읽기
        var reader = new FileReader();
        // onload = 읽기 동작이 성공적으로 완료되었을 때
        reader.onload = function () {
        	// 개별 파일을 저장
	        reader.readAsDataURL(file);
        };
        
        // 목록 추가
        let html = '';
        html += '<div id="file' + fileNo + '" class="fileList">';
        html += '   <p class="filename">' + file.name + '</p>';
        html += '   <a class="filedelete" onclick="deleteFile(' + fileNo + ');">❌</a>';
        html += '</div>';
        // jquery 로 추가하면 자동으로 dom 으로 변환되어 추가됨
        $('.file_group').append(html);
        fileNo++;
		console.log("첨부파일 추가완료");
    }
	    
    // 추가한 뒤 초기화
    document.querySelector("input[type=file]").value = "";
}

// 첨부파일 삭제
function deleteFile(num) {
    document.querySelector("#file" + num).remove();
    // 이미 삭제된 파일 해당 인덱스에
    // .is_delete = true 라는 키와 값을 추가해 구분
    filesArr[num].is_delete = true;
}

// 글쓰기 데이터 전달
function form(){


	// 기본 선택값 초기화
	var select = $('#e_con_choice option:selected').val();
	document.querySelector('#e_choice_val').value = select;
	
	// 클릭하면 선택값 변화
	document.querySelector('#e_con_choice').addEventListener('click', ()=>{
		var select = $('#e_con_choice option:selected').val();
		document.querySelector('#e_choice_val').value = select;
	});

	// 글쓰기 버튼 클릭
	document.querySelector('#e_btn_write_btn').addEventListener('click', (e)=>{
		e.preventDefault();
		// 폼 데이터에 값 담기
	    let formData = new FormData();
		// 파일을 폼데이터에 담기
	    for (let i = 0; i < filesArr.length; i++) {
	        // 삭제되지 않은 파일만 폼데이터에 담기
	        if (!filesArr[i].is_delete) {
	            formData.append('file'+i, filesArr[i]);
	        }
	    }
		// 제목
		formData.append('title', document.querySelector('#e_ti_detail_input').value);
		// 내용
		formData.append('description', document.querySelector('#e_cont_detail_input').value);
		// 글쓰기 유형 선택
		formData.append('sv_type', document.querySelector('#e_choice_val').value);
		$.ajax({
		    url: '/all/service/question-public-reply',
		    type: 'POST',
		    processData: false,
		    contentType: false,
		    dataType:'text',
		    data: formData,
		    success: function(result){
		    		location.href="/all/service/question-public-reply";
		    }
		});
	});
	
}