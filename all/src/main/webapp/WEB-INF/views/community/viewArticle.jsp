<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    import="com.zerocalorie.member.dto.e_MemberDTO,com.zerocalorie.community.DAO.t_MemberDAO,com.zerocalorie.community.DTO.t_Article_plusDTO" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
   <title>글보기</title>
   <link rel="stylesheet" href="/all/resources/board_community/css/viewArticle.css">
   <style>
     #tr_btn_modify{
       display:none;
     }
   </style>
   <script src="http://code.jquery.com/jquery-latest.min.js"></script> 
   <script type="text/javascript" >
     function backToList(obj){
	    obj.action="${contextPath}/community/listArticles.do";
	    obj.submit();
     }
 
	 function fn_enable(obj){
		 document.getElementById("i_title").disabled=false;
		 document.getElementById("i_content").disabled=false;
		 if (document.getElementById("i_imageFileName")){
			 document.getElementById("i_imageFileName").style.display="block";
		 }
		 document.getElementById("tr_btn_modify").style.display="block";
		 document.getElementById("tr_btn").style.display="none";
	 }
	 
	 function fn_modify_article(obj){
		 obj.action="${contextPath}/community/modArticle.do";
		 obj.submit();
	 }
	 
	 function fn_remove_article(url,articleNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var articleNOInput = document.createElement("input");
	     articleNOInput.setAttribute("type","hidden");
	     articleNOInput.setAttribute("name","articleNO");
	     articleNOInput.setAttribute("value", articleNO);
		 
	     form.appendChild(articleNOInput);
	     document.body.appendChild(form);
	     form.submit();
	 
	 }
	 
	 function fn_reply_form(url, parentNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var parentNOInput = document.createElement("input");
	     parentNOInput.setAttribute("type","hidden");
	     parentNOInput.setAttribute("name","parentNO");
	     parentNOInput.setAttribute("value", parentNO);
		 
	     form.appendChild(parentNOInput);
	     document.body.appendChild(form);
		 form.submit();
	 }
	 
	 function readURL(input) {
	     if (input.files && input.files[0]) {
	         var reader = new FileReader();
	         reader.onload = function (e) {
	             $('#preview').attr('src', e.target.result);
	         }
	         reader.readAsDataURL(input.files[0]);
	     }
	 }  
 </script>
	<%
       	e_MemberDTO m_dto = new e_MemberDTO();
        		
       	// 로그인 유무
       	if((e_MemberDTO)session.getAttribute("user") !=null){
   		m_dto = (e_MemberDTO)session.getAttribute("user"); 	}
     %>
   
	<section>
		<div id="j_wrap">
			<div id="j_box">
			
			<div id="e_box">

			  <form name="frmArticle" method="post" action="${contextPath}" enctype="multipart/form-data">
			  <h1 class="first_h1">글 작성</h1>
			  <table border=0 align="center">
			   <%
			   		t_Article_plusDTO a_vo = new t_Article_plusDTO();
			   		a_vo = (t_Article_plusDTO)request.getAttribute("article");
				%>
			  
			  <tr>
			    <td width="150" align="center" bgcolor="#273DB2" class="e_td">
			      제목 
			   </td>
			   <td>
			    <input type=text value="${article.title }"  name="title"  id="i_title" disabled class="e_input" />
			   </td>   
			  </tr>
			  <tr>
			    <td width="150" align="center" bgcolor="#273DB2" class="e_td">
			      내용
			   </td>
			   <td>
			    <textarea rows="20" cols="60" name="content" id="i_content" disabled />${article.content }</textarea>
			   </td>  
			  </tr>
			 
			<c:if test="${not empty article.imageFileName && article.imageFileName!='null' }">  
			<tr>
			    <td width="150" align="center" bgcolor="#273DB2" rowspan="2" class="img_td">
			      이미지
			   </td>
			   <td>
			     <input type="hidden" name="originalFileName" value="${article.imageFileName }" />
			    <img width="100" height="100" src="/all/community/load-proimg?fileName=${article.imageFileName}" id="preview"  /><br>
			       
			   </td>
			  </tr>  
			  <tr>
			    <td>
			       <input type="file" name="imageFileName" id="i_imageFileName" onchange="readURL(this);" style="display:none;"  />
			    </td>
			  </tr>
			 </c:if>
			  <tr>
				   <td width="150" align="center" bgcolor="#273DB2" class="e_td">
				      등록일자
				   </td>
				   <td>
				    <input type=text value="<fmt:formatDate value="${article.writeDate}" />" disabled class="e_input" />
				   </td>   
			  </tr>
			  <tr id="tr_btn_modify">
				   <td colspan="3" align="center" id="btn">
				        <input type=button id="btn_update" value="수정반영하기" onClick="fn_modify_article(frmArticle)"  >
			            <input type=button id="btn_no" value="취소" onClick="backToList(frmArticle)">
				   		<input type="hidden" name="articleNO" value="${article.articleNO}">
				   </td>   
			  </tr>
			  	<tr class="blank"></tr>
			    
			  <tr id="tr_btn">
			   <td colspan="2" align="center">
			    <%
					if((e_MemberDTO)session.getAttribute("user") !=null ){
						e_MemberDTO new_m_dto = (e_MemberDTO)session.getAttribute("user");
						if(new_m_dto.getMember_no()==a_vo.getMember_no()){
				%>
				    <input type=button value="수정하기" onClick="fn_enable(this.form)" class="cor_btn">
				    <input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/community/removeArticle.do', ${article.articleNO})">
				    <input type=button value="답글쓰기" style="display:none;" onClick="fn_reply_form('${contextPath}/community/replyForm.do', ${article.articleNO})">
			  <%
					}
					}
			  %>
				    <input type=button value="리스트로 돌아가기" onClick="backToList(this.form)" class="back_btn">
				    <input type=button value="추천 ( ${article.reccount} )"  class="get_btn" id="recommend">
			   </td>
			  </tr>
			 </table>
			 </form>
			 
			 </div>
 
			</div>
		</div>
	</section>
 <script type="text/javascript">
   		document.getElementById("recommend").addEventListener("click", function(){
   			// 추천하기 버튼 클릭
   			var result = '';
   			fetch("${pageContext.request.contextPath}/rec/recommend", {
			  method: "POST",
			  headers: {
			    "Content-Type": "application/json",
			  },
			  body: JSON.stringify({
				articleno: ${article.articleNO},
			    member_no: <%=m_dto.getMember_no()%>
			  })
			}).then(response => {
				if(response.ok) {
					alert("추천하였습니다!");
					location.reload();
				} else if(response.status == 406) {
					alert("이미 추천하였습니다.");
				}
	    	});
   		});
   </script>