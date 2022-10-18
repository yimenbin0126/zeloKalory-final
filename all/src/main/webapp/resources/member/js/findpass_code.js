// 전체 적용
window.onload = function () {
   findpass();
};

// 비밀번호 찾기
function findpass() {
    // 인증코드
   if (document.querySelector("#input_sub_code input")){
	    document.querySelector("#input_sub_code input").addEventListener('click', function (e) {
		    e.preventDefault();
			$.ajax({
		        url: "/all/member/findpass-code",
		        type: "post",
		        data: {
		           code : $('#e_input_code').val()
		        },
		        success: function (data) {
		        	if(data=="O"){
		        		alert('인증이 완료되었습니다.');
		        		location.href='/all/member/findpass-new';
		        	} else {
		        		alert('인증코드가 틀립니다.\n다시 입력해주세요.');
		        	}
		        }
		     });
	   });
   }
}
