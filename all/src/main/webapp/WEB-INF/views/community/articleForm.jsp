<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"
    isELIgnored="false"
    import="com.zerocalorie.member.dto.e_MemberDTO,com.zerocalorie.community.DAO.t_MemberDAO,com.zerocalorie.community.DTO.t_ArticleDTO" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"  /> 
<title>글쓰기창</title>
<link rel="stylesheet" href="/all/resources/board_community/css/articleForm.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
   function readURL(input) {
      if (input.files && input.files[0]) {
	      var reader = new FileReader();
	      console.log(input.files[0].name);
	      reader.onload = function (e) {
	        $('#preview').attr('src', e.target.result);
          }
         reader.readAsDataURL(input.files[0]);
      }
  }  
  function backToList(obj){
    obj.action="${contextPath}/community/listArticles.do";
    obj.submit();
  }
</script>
 <title>새글 쓰기 창</title>
	<section>
		<div id="j_wrap">
			<div id="j_box">

				<div id="e_box">
					<h1 style="text-align: center">글쓰기</h1>
					<form name="articleForm" method="post"
						action="${contextPath}/community/addArticle.do"
						enctype="multipart/form-data">
						<div class="e_box_write">

							<div class="header_title">
								<div class="title">글제목</div>
								<div>
									<input type="text" name="title" id="title_detail" />
								</div>
							</div>

							<div class="header_content">
								<div class="title">글내용</div>
								<div>
									<textarea name="content" maxlength="4000"
										id="content_detail"></textarea>
								</div>
							</div>
							
							<div>
								<div>
									<div class="image_content">
										<div align="right">
											<div>이미지파일 첨부</div>
											<div>
												<input type="file" name="imageFileName"
												onchange="readURL(this);" />
											</div>
										</div>
										<div class="show">
											<img id="preview" src="#" width=200 height=200
												style="display: none;" />
										</div>
									</div>
								</div>
							</div>

							<div>
								<div class="btn">
									<input type="submit" value="글쓰기" />
									<input type=button
										value="목록보기" onClick="backToList(this.form)" />
								</div>
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>
	</section>