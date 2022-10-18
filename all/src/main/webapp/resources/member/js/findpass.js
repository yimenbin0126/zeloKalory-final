// 전체 적용
window.onload = function () {
   findpass();
};

// 비밀번호 찾기 - 이메일 주소
function findpass() {
	// 처음
	if (document.querySelector("#input_sub_input input")){
	    document.querySelector("#input_sub_input input").addEventListener('click', function (e) {
		    e.preventDefault();
			$.ajax({
		        url: "/all/member/findpass",
		        type: "post",
		        data: {
		           email : $('#e_input_email').val(),
		           id : $('#e_input_id').val()
		        },
		        success: function (data) {
		        	if(data=="O"){
		        		alert('고객님의 이메일로 인증코드를 보내드렸습니다.\n인증을 완료해주세요.');
		        		location.href='/all/member/findpass-code';
		        	} else {
		        		alert('아이디 혹은 이메일이 틀립니다.\n다시 입력해주세요.');
		        	}
		        }
		     });
	   });
   }
   
}
