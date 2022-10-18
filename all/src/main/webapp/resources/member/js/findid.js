// 전체 적용
window.onload = function () {
   findid();
};

// 아이디 찾기 - 이메일 주소
function findid() {
    document.querySelector("#e_input_sub").addEventListener('click', function (e) {
    e.preventDefault();
	$.ajax({
        url: "/all/member/findid",
        type: "post",
        data: {
           email : $('#e_input_email').val()
        },
        success: function (data) {
           if (data == "X") {
              alert('이메일이 존재하지 않습니다.');
           } else {
              alert('이메일로 고객님의 아이디를 전해드렸습니다.\n본인의 이메일을 확인해주세요.');
           } 
        }
     });
   });
}
