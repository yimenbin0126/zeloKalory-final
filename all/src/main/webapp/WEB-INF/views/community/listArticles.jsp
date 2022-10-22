<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    import="com.zerocalorie.member.dto.e_MemberDTO,com.zerocalorie.community.DAO.t_MemberDAO,com.zerocalorie.community.DTO.t_Article_plusDTO, java.util.*"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<%
request.setCharacterEncoding("UTF-8");
%>  
  <link rel="stylesheet" href="/all/resources/board_community/css/listArticle.css">
 <style>
   .cls1 {text-decoration:none;}
   .h{text-align:center;}
  </style>
  <meta charset="UTF-8">
  <title>게시판 글 목록</title>
	<section>
		<div id="j_wrap">
			<div id="j_box">
				
				<div id="e_box">
					<h1 class="h">커뮤니티 게시판</h1>
					<table align="center"  width="80%"  >
					  <tr align="center"  id="table_header">
					     <td >글번호</td>
					     <td >작성자</td>              
					     <td >제목</td>
					     <td >작성일</td>
					     <td width="150">추천수</td>
					  </tr>
					<c:choose>
					  <c:when test="${empty articlesList_Plus }" >
					    <tr  height="10">
					      <td colspan="4">
					         <p align="center">
					            <b><span style="font-size:9pt; color: red;">등록된 글이 없습니다.</span></b>
					        </p>
					      </td>  
					    </tr>
					  </c:when>
							<c:when test="${!empty  articlesList_Plus}">
								
									<tr align="center">
									<%
									List<t_Article_plusDTO> articlesList_Plus = new ArrayList<t_Article_plusDTO>();
									articlesList_Plus = (List<t_Article_plusDTO>) request.getAttribute("articlesList_Plus");
									for (int i = 0; i < articlesList_Plus.size(); i++) {
										t_Article_plusDTO a_vo = new t_Article_plusDTO();
										a_vo = articlesList_Plus.get(i);
									%>
									<td width="5%"><%=i+1%></td>
										<td width="10%">
										<%=a_vo.getNickname()%>
										</td>
										<td align='left' width="32%"><span
											style="padding-right: 30px"></span> <c:choose>
												<c:when test='${article.articleNO > 1 }'>
													<c:forEach begin="1" end="<%=a_vo.getArticleNO()%>" step="1">
														<span style="padding-left: 10px"></span>
													</c:forEach>
													<span style="font-size: 12px;">┗[답변]</span>
													<a class='cls1'
														href="${contextPath}/community/viewArticle.do?articleNO=<%=a_vo.getArticleNO()%>"><%=a_vo.getTitle()%></a>
												</c:when>
												<c:otherwise>
													<a class='cls1'
														href="${contextPath}/community/viewArticle.do?articleNO=<%=a_vo.getArticleNO()%>"><%=a_vo.getTitle()%></a>
												</c:otherwise>
											</c:choose></td>
										<td width="10%"><fmt:formatDate
												value="<%=a_vo.getWriteDate()%>" /></td>
										
										<td align='center' width="3%"><%=a_vo.getReccount()%></td>
									
									
									</tr>
									
								<%
								}
								%>
								
								
							</c:when>
						</c:choose>
					</table>
					<%
					if((e_MemberDTO)session.getAttribute("user") !=null){
					%>
					<div class="btn_class">
						<a  class="cls1"  href="${contextPath}/community/articleForm.do">글쓰기</a>
					</div>	
					<%
						}
					%>
				</div>
			</div>
		</div>
	</section>