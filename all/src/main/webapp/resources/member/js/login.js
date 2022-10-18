// 전체 적용
window.onload = function () {
   confirm_idpass();
};

// 아이디 비밀번호 유효성 검사 - 길이
function confirm_idpass() {
   document.querySelector("#e_login_btn").addEventListener('click', function (e) {
      e.preventDefault();
      document.querySelector('#e_auto_login_check').value = "N";

      if (document.querySelector("#e_input_id").value.length == 0) {
         alert("아이디를 입력해주세요.");
      } else {
         confirm_pass();
      }

      function confirm_pass() {
         if (document.querySelector("#e_input_pass").value.length == 0) {
            alert("비밀번호를 입력해주세요.");
         } else {
            login();
         }
      }

      function login() {

         if (document.querySelector('#e_auto_login').checked == true) {
            document.querySelector('#e_auto_login_check').value = "Y";
         }

         $.ajax({
            url: "/all/member/login",
            type: "post",
            data: {
               id: $('#e_input_id').val(),
               pw: $('#e_input_pass').val(),
               e_auto_login_check: $('#e_auto_login_check').val()
            },
            success: function (data) {
               if (data == "id") {
                  alert('아이디가 틀렸습니다.');
               } else if (data == "pw") {
                  alert('비밀번호가 틀렸습니다.');
               } else {
                  location.href = "/all/main/main";
               }
            }
         });
      }
   });

}





