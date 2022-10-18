// 전체 적용
window.onload = function () {
   confirm_idpass();
};

// 아이디 비밀번호 유효성 검사 - 길이
function confirm_idpass() {
   // 새 비밀번호
   if (document.querySelector("#input_sub_new input")){
	    document.querySelector("#input_sub_new input").addEventListener('click', function (e) {
		    e.preventDefault();
			$.ajax({
		        url: "/all/member/findpass-new",
		        type: "post",
		        data: {
		           pass : $('#e_input_code').val(),
		           pass_re : $('#e_input_code_re').val()
		        },
		        success: function (data) {
		        	if(data=="O"){
		        		alert('비밀번호가 변경되었습니다.');
		        		location.href='/all/member/findpass';
		        	} else if (data=="X1") {
		        		alert('기존 비밀번호와 똑같은 비밀번호입니다.\n다시 입력해주세요.');
		        	} else if (data=="X2") {
		        		alert('비밀번호가 올바르지 않습니다.\n다시 입력해주세요.');
		        	} else if (data=="X3") {
		        		alert('비밀번호 재입력이 올바르지 않습니다.\n다시 입력해주세요.');
		        	}
		        }
		     });
	   });
   }

}





