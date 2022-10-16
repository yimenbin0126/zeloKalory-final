window.onload = function () {
    form();
    btn();
    comment();
}

// 뷰 데이터 전달
function form() {
    // 좋아요 버튼 누를시
    // 좋아요 X -> 좋아요 누르기
    if (document.querySelector('#e_like_heart_n img')) {
        document.querySelector('#e_like_heart_n img').addEventListener('click', (event) => {
            $.ajax({
                url: "/all/service/question-public-detail",
                type: "POST",
                data: {
                    e_heart_check: "like_Y",
                    e_bno: document.querySelector('#e_bno_value').value
                },
                success: function (data) {
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
    }

    // 좋아요 O -> 좋아요 누르기
    if (document.querySelector('#e_like_heart_y img')) {
        document.querySelector('#e_like_heart_y img').addEventListener('click', (event) => {
            $.ajax({
                url: "/all/service/question-public-detail",
                type: "POST",
                data: {
                    e_heart_check: "like_N",
                    e_bno: document.querySelector('#e_bno_value').value
                },
                success: function (data) {
                    // 하트 칠하기
                    document.querySelector('#e_like_heart_n').style.display = "block";
                    document.querySelector('#e_like_heart_y').style.display = "none";
                    // 숫자 감소
                    var heart_num = document.querySelector('.e_like_num').innerText;
                    document.querySelector('.e_like_num').innerText = parseInt(heart_num) - 1;
                },
            });
        });
    }

    // 싫어요 버튼 누를시
    // 싫어요 X -> 싫어요 누르기
    if (document.querySelector('#e_dislike_heart_n img')) {
        document.querySelector('#e_dislike_heart_n img').addEventListener('click', (event) => {
            $.ajax({
                url: "/all/service/question-public-detail",
                type: "POST",
                data: {
                    e_heart_check: "dislike_Y",
                    e_bno: document.querySelector('#e_bno_value').value
                },
                success: function (data) {
                    // 객체 값 가져오기
                    // 하트 칠하기
                    document.querySelector('#e_dislike_heart_n').style.display = "none";
                    document.querySelector('#e_dislike_heart_y').style.display = "block";
                    // 숫자 증가
                    var heart_num = document.querySelector('.e_dislike_num').innerText;
                    document.querySelector('.e_dislike_num').innerText = parseInt(heart_num) + 1;
                },
            });
        });
    }

    // 싫어요 O -> 싫어요 누르기
    if (document.querySelector('#e_dislike_heart_y img')) {
        document.querySelector('#e_dislike_heart_y img').addEventListener('click', (event) => {
            $.ajax({
                url: "/all/service/question-public-detail",
                type: "POST",
                data: {
                    e_heart_check: "dislike_N",
                    e_bno: document.querySelector('#e_bno_value').value
                },
                success: function (data) {
                    // 하트 칠하기
                    document.querySelector('#e_dislike_heart_n').style.display = "block";
                    document.querySelector('#e_dislike_heart_y').style.display = "none";
                    // 숫자 감소
                    var heart_num = document.querySelector('.e_dislike_num').innerText;
                    document.querySelector('.e_dislike_num').innerText = parseInt(heart_num) - 1;
                },
            });
        });
    }
}

function btn() {
    // 버튼 클릭
    // 글 답글 쓰기 버튼
    if (document.querySelector('#e_btn_reply')) {
        document.querySelector('#e_btn_reply').addEventListener('click', (event) => {
            var e_btn_reply_form = document.e_btn_reply_form;
            var con_reply = confirm('정말 답글을 작성하시겠습니까?');
            if (con_reply == true) {
                e_btn_reply_form.method = "get";
                e_btn_reply_form.action = "/all/service/question-public-detail-button";
                e_btn_reply_form.submit();
            } else {
                event.preventDefault();
            }
        });
    }

    // 글 수정 버튼
    if (document.querySelector('#e_btn_fix')) {
        document.querySelector('#e_btn_fix').addEventListener('click', (event) => {
            var e_btn_fix_form = document.e_btn_fix_form;
            var con_fix = confirm('정말 글을 수정하시겠습니까?');
            if (con_fix == true) {
                e_btn_fix_form.method = "get";
                e_btn_fix_form.action = "/all/service/question-public-detail-button";
                e_btn_fix_form.submit();
            } else {
                event.preventDefault();
            }
        });
    }

    // 글 삭제 버튼
    if (document.querySelector('#e_btn_delete')) {
        document.querySelector('#e_btn_delete').addEventListener('click', (event) => {
            var e_btn_delete_form = document.e_btn_delete_form;
            var con_del = confirm('정말 글을 삭제하시겠습니까?');
            if (con_del == true) {
                e_btn_delete_form.method = "get";
                e_btn_delete_form.action = "/all/service/question-public-detail-button";
                e_btn_delete_form.submit();
            } else {
                event.preventDefault();
            }
        });
    }
}

// 댓글, 답글 작성
function comment() {
    // 글 번호
    var hidden_bno = document.querySelector('#hidden_bno').value;

	console.log(document.querySelector('.com_input .c_i_textarea textarea').value);
	// 댓글 입력창
    if (document.querySelector('.com_input .c_i_btn .c_i_btn_Y')) {
        document.querySelector('.com_input .c_i_btn .c_i_btn_Y').addEventListener('click', function (event) {
	        var comment_code = document.querySelector('.com_input .c_i_textarea textarea').value;
        	if (comment_code.length == 0) {
            comment_code = " ";
            }
            $.ajax({
                url: "/all/service/question-public-comment/insert",
                type: 'get',
                data: {
                    "bno": hidden_bno,
                    "comment_code": comment_code
                },
                success: function (result) {
                    if (result == "X") {
                        alert('댓글 내용을 제대로 입력해주세요.');
                    } else {
                        location.href = "/all/service/question-public-detail?bno=" + hidden_bno;
                    }
                }
            });
        });
    }

    // 댓글의 답글
    if (document.querySelector('.com_btn .com_btn_reply')) {
        // 댓글의 답글 시작
        // 각각 답글 객체
        const comment_reply_list = document.querySelectorAll(".com_btn .com_btn_reply a");
        for (const com_reply of comment_reply_list) {
            com_reply.addEventListener('click', function (e) {
                // 답글이 하나만 떠있어야함. 이미 존재하면 취소.
                if (!document.querySelector('.c_l_comment_new') && !document.querySelector('.c_l_reply_new')
                && !document.querySelector('.c_l_reply_fix') && !document.querySelector('.c_l_comment_fix')) {
                    // 아래에 답글 작성 폼 생김
                    var html = '<div class="c_l_comment_new">';
                    html += '<div class="reply_new_blank">↳</div>';
                    html += '<div class="r_new_content">';
                    html += '<textarea wrap="hard" placeholder="내용을 입력해 주세요." id="e_cont_detail_input"></textarea>';
                    html += '<div class="c_i_new_btn"><button>등록</button></div>';
                    html += '</div></div></div>';
                    $(e.target).parent().parent().parent().parent().after(html);
                    
                    // 답글 취소폼으로 만들기
                   	$(e.target).after('<a class="com_reply_del_click"><b>답글취소</b></a>');
                   	$(e.target).css('display', 'none')
                    // 답글 취소시
                    $('.com_reply_del_click').click(function(){
                    	$('.com_reply_del_click').remove();
                    	$('.c_l_comment_new').remove();
                   		$(e.target).css('display', 'block');
                    });
                    // 작성 완료시
                    if(document.querySelector('.c_i_new_btn button')){
	                    document.querySelector('.c_i_new_btn button').addEventListener('click', function (event) {
	                    	// 댓글 원본 코드, 닉네임, 내용
		                    var origin_code_re = e.target.parentElement.parentElement.firstElementChild.value;
		                    var to_nickname_re = e.target.parentElement.parentElement.children[1].value;
		                    var comment_code_re = document.querySelector('.r_new_content textarea').value;
	                        var con_del = confirm('정말 답글을 작성하시겠습니까?');
	                        if (con_del == true) {
	                            $.ajax({
	                                url: "/all/service/question-public-reply/insert",
	                                type: 'get',
	                                data: {
	                                    "bno": hidden_bno,
	                                    "origin_code": origin_code_re,
	                                    "comment_code": comment_code_re,
	                                    "to_nickname": to_nickname_re
	                                },
	                                success: function (result) {
	                                    if (result == "X") {
	                                        alert('댓글 내용을 제대로 입력해주세요.');
	                                    } else {
	                                        location.href = "/all/service/question-public-detail?bno=" + hidden_bno;
	                                    }
	                                }
	                            });
	                        } else {
	                            event.preventDefault();
	                        }
	                    });
                	}
                } else {
                    // 답글이 이미 있다
                    alert("댓글을 작성해주세요.");
                }
                // 답글 취소시
            })
        }
    }
    // 댓글의 답글 끝

    // 댓글 수정 시작
    if (document.querySelector('.com_btn .com_btn_fix')) {
        // 각각 댓글 객체
        const comment_fix_list = document.querySelectorAll(".com_btn .com_btn_fix a");
        for (const com_fix of comment_fix_list) {
        
            com_fix.addEventListener('click', function (e) {
                // 댓글이 수정폼으로 바뀌어야함. 수정폼은 한개.
                if (!document.querySelector('.c_l_comment_new') && !document.querySelector('.c_l_reply_new')
                && !document.querySelector('.c_l_reply_fix') && !document.querySelector('.c_l_comment_fix')) {
                    // 수정폼 생성 + 원본 가리기
                    // 원본 코멘트
                    var origin_comment = e.target.parentElement.parentElement.children[2].value;
                    // 원본 가리기
                    e.target.parentElement.parentElement.parentElement.parentElement.style.display = "none";
                    // 수정폼 생성
                    var html = '<div class="c_l_comment_fix">';
                    html += '<div class="c_l_comment_fix_content">';
                    html += '<div class="c_fix_textarea">';
                    html += '<textarea wrap="hard" placeholder="내용을 입력해 주세요." name="description" id="fix_con_input">' + origin_comment + '</textarea>';
                    html += '</div>';
                    html += '<div class="fix_con_btn"><button>수정하기</button></div>';
                    html += '</div>';
                    html += '</div>';
                    $(e.target).parent().parent().parent().parent().after(html);
                    
                    // 수정 취소폼으로 만들기
                   	$('.c_l_comment_fix').append('<a class="com_fix_del_click" style="text-decoration:none"><b style="font-size:13px; color: gray;">수정취소</b></a>');
                    // 수정 취소시
                    $('.com_fix_del_click').click(function(){
                    	$('.com_fix_del_click').remove();
                    	$('.c_l_comment_fix').remove();
                   		e.target.parentElement.parentElement.parentElement.parentElement.style.display = "flex";
                    });
                    
                    // 수정폼 완료시 + 수정폼 삭제 + 원본 보여주기
                    // 수정 완료시
                    if (document.querySelector('.c_l_comment_fix_content .fix_con_btn button')){
	                    document.querySelector('.c_l_comment_fix_content .fix_con_btn button').addEventListener('click', function (event) {
	                    	// 댓글 원본 코드, 내용
	                    	var c_code_fix = e.target.parentElement.parentElement.firstElementChild.value;
	                    	var comment_code_fix = document.querySelector('.c_fix_textarea textarea').value;
	                        var con_fix = confirm('정말 답글을 수정하시겠습니까?');
	                        if (con_fix == true) {
	                            $.ajax({
	                                url: "/all/service/question-public-comment/fix",
	                                type: 'get',
	                                data: {
	                                    "bno": hidden_bno,
	                                    "c_code": c_code_fix,
	                                    "comment_code": comment_code_fix,
	                                },
	                                success: function (result) {
	                                    if (result == "X") {
	                                        alert('댓글 내용을 제대로 입력해주세요.');
	                                    } else {
	                                        location.href = "/all/service/question-public-detail?bno=" + hidden_bno;
	                                    }
	                                }
	                            });
	                        } else {
	                            event.preventDefault();
	                        }
	                    });
                    }
                } else {
                    // 답글이 이미 있다
                    alert("댓글을 작성해주세요.");
                }
                // 답글 취소시
            })
        }
    }
    // 댓글 수정 끝

    // 댓글 삭제 시작
    if (document.querySelector('.com_btn .com_btn_del')) {
        // 각각 댓글 객체
        const comment_del_list = document.querySelectorAll(".com_btn .com_btn_del a");
        for (const com_del of comment_del_list) {
            com_del.addEventListener('click', function (e) {
                var c_code_del = e.target.parentElement.parentElement.firstElementChild.value;
                // 댓글을 삭제함
                var con_del = confirm('정말 댓글을 삭제하시겠습니까?');
                if (con_del == true) {
                    $.ajax({
                        url: "/all/service/question-public-comment/delete",
                        type: 'get',
                        data: {
                            "bno": hidden_bno,
                            "c_code": c_code_del
                        },
                        success: function (result) {
                            if (result == "X") {
                                alert('댓글 내용을 제대로 입력해주세요.');
                            } else {
                                location.href = "/all/service/question-public-detail?bno=" + hidden_bno;
                            }
                        }
                    });
                } else {
                    e.preventDefault();
                }
            });
        }
    }
    // 댓글 삭제 끝

    // 댓글 끝

    // 답글 시작
    if (document.querySelector('.c_l_reply')) {
        // 각각 답글 객체
        // 답글의 답글 시작
        const reply_reply_list = document.querySelectorAll(".reply_btn .reply_btn_reply a");
        for (const reply_reply of reply_reply_list) {
            reply_reply.addEventListener('click', function (e) {
                // 답글이 하나만 떠있어야함. 이미 존재하면 취소.
                if (!document.querySelector('.c_l_comment_new') && !document.querySelector('.c_l_reply_new')
                && !document.querySelector('.c_l_reply_fix') && !document.querySelector('.c_l_comment_fix')) {
                    // 아래에 답글 작성 폼 생김
                    var html = '<div class="c_l_reply_new">';
                    html += '<div class="reply_new_blank">↳</div>';
                    html += '<div class="r_new_content">';
                    html += '<textarea wrap="hard" placeholder="내용을 입력해 주세요." id="e_cont_detail_input"></textarea>';
                    html += '<div class="c_i_new_btn"><button>등록</button></div>';
                    html += '</div></div></div>';
                    $(e.target).parent().parent().parent().parent().after(html);
                    
                    // 답글 취소폼으로 만들기
                   	$(e.target).after('<a class="reply_reply_del_click"><b>답글취소</b></a>');
                   	$(e.target).css('display', 'none')
                    // 답글 취소시
                    $('.reply_reply_del_click').click(function(){
                    	$('.reply_reply_del_click').remove();
                    	$('.c_l_reply_new').remove();
                   		$(e.target).css('display', 'block');
                    });
                    
                    // 작성 완료시
                    document.querySelector('.c_i_new_btn button').addEventListener('click', function (event) {
                    	// 답글 원본 코드, 닉네임, 내용
                   		var origin_code_reply = e.target.parentElement.parentElement.firstElementChild.value;
                    	var to_nickname_reply = e.target.parentElement.parentElement.children[1].value;
                    	var comment_code_reply = document.querySelector('.r_new_content textarea').value;
                        var c_reply_del = confirm('정말 답글을 작성하시겠습니까?');
                        if (c_reply_del == true) {
                            $.ajax({
                                url: "/all/service/question-public-reply/insert",
                                type: 'get',
                                data: {
                                    "bno": hidden_bno,
                                    "origin_code": origin_code_reply,
                                    "comment_code": comment_code_reply,
                                    "to_nickname": to_nickname_reply
                                },
                                success: function (result) {
                                    if (result == "X") {
                                        alert('답글 내용을 제대로 입력해주세요.');
                                    } else {
                                        location.href = "/all/service/question-public-detail?bno=" + hidden_bno;
                                    }
                                }
                            });
                        } else {
                            event.preventDefault();
                        }
                    });
                } else if (document.querySelectorAll('.c_l_reply_new').length==1) {
                    // 답글이 이미 있다
                    alert("댓글을 작성해주세요.");
                }
                // 답글 취소시
            })
        }
    }
    // 답글의 답글 끝

    // 답글 수정 시작
    if (document.querySelector('.reply_btn .reply_btn_fix')) {
        // 각각 답글 객체
        const reply_fix_list = document.querySelectorAll(".reply_btn .reply_btn_fix a");
        for (const reply_fix of reply_fix_list) {
            reply_fix.addEventListener('click', function (e) {
                // 댓글이 수정폼으로 바뀌어야함. 수정폼은 한개.
                if (!document.querySelector('.c_l_comment_new') && !document.querySelector('.c_l_reply_new')
                && !document.querySelector('.c_l_reply_fix') && !document.querySelector('.c_l_comment_fix')) {
                    // 수정폼 생성 + 원본 가리기
                    // 원본 코멘트
                    var reply_origin_comment = e.target.parentElement.parentElement.children[2].value;
                    // 원본 가리기
                    e.target.parentElement.parentElement.parentElement.parentElement.style.display = "none";
                    // 수정폼 생성
                    var html = '<div class="c_l_reply_fix">';
                    html += '<div class="r_fix_content">';
                    html += '<div class="reply_blank">↳</div>';
                    html += '<div class="r_fix_textarea">';
                    html += '<textarea wrap="hard" placeholder="내용을 입력해 주세요." name="description" id="fix_reply_input">' + reply_origin_comment + '</textarea>';
                    html += '</div>';
                    html += '<div class="fix_reply_btn"><button>수정하기</button></div>';
                    html += '</div>';
                    html += '</div>';
                    $(e.target).parent().parent().parent().parent().after(html);
                    
                    // 수정 취소폼으로 만들기
                   	$('.c_l_reply_fix').append('<a class="reply_fix_del_click" style="text-decoration:none"><b style="font-size:13px; color: gray; margin-left:20px;">수정취소</b></a>');
                    // 수정 취소시
                    $('.reply_fix_del_click').click(function(){
                    	$('.reply_fix_del_click').remove();
                    	$('.c_l_reply_fix').remove();
                   		e.target.parentElement.parentElement.parentElement.parentElement.style.display = "flex";
                    });
                    
                    // 수정폼 완료시 + 수정폼 삭제 + 원본 보여주기
                    // 수정 완료시
                    if (document.querySelector('.r_fix_content .fix_reply_btn button')){
	                    document.querySelector('.r_fix_content .fix_reply_btn button').addEventListener('click', function (event) {
	                    // 댓글 원본 코드, 내용
                    	var c_code_fix_reply = e.target.parentElement.parentElement.children[3].value;
                    	var comment_code_fix_reply = document.querySelector('.r_fix_textarea textarea').value;
                    	console.log(comment_code_fix_reply);
	                        var c_reply_fix = confirm('정말 답글을 수정하시겠습니까?');
	                        if (c_reply_fix == true) {
	                            $.ajax({
	                                url: "/all/service/question-public-reply/fix",
	                                type: 'get',
	                                data: {
	                                    "bno": hidden_bno,
	                                    "c_code": c_code_fix_reply,
	                                    "comment_code": comment_code_fix_reply
	                                },
	                                success: function (result) {
	                                    if (result == "X") {
	                                        alert('답글 내용을 제대로 입력해주세요.');
	                                    } else {
	                                        location.href = "/all/service/question-public-detail?bno=" + hidden_bno;
	                                    }
	                                }
	                            });
	                        } else {
	                            event.preventDefault();
	                        }
	                    });
	                }
                } else {
                    // 답글이 이미 있다
                    alert("댓글을 작성해주세요.");
                }
            })
        }
    }
    // 답글 수정 끝

    // 답글 삭제 시작
    if (document.querySelector('.reply_btn .reply_btn_del')) {
        // 각각 답글 객체
        const reply_del_list = document.querySelectorAll(".reply_btn .reply_btn_del a");
        for (const reply_del of reply_del_list) {
            reply_del.addEventListener('click', function (e) {
                var c_code_del_reply = e.target.parentElement.parentElement.children[3].value;
                // 답글을 삭제함
                var c_reply_del = confirm('정말 답글을 삭제하시겠습니까?');
                if (c_reply_del == true) {
                    $.ajax({
                        url: "/all/service/question-public-reply/delete",
                        type: 'get',
                        data: {
                            "bno": hidden_bno,
                            "c_code": c_code_del_reply
                        },
                        success: function (result) {
                            if (result == "X") {
                                alert('답글 내용을 제대로 입력해주세요.');
                            } else {
                                location.href = "/all/service/question-public-detail?bno=" + hidden_bno;
                            }
                        }
                    });
                } else {
                    e.preventDefault();
                }
            });
        }
        // 답글 삭제 끝
    }
    // 답글 끝
}