<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"
 %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 


<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
      <link rel="stylesheet" href="/all/resources/board_community/css/header.css">
<%--       <title><tiles:insertAttribute name="title" /></title>  --%>
  </head>
    <body>
    	<tiles:insertAttribute name="header"/>
	<div>
    	<tiles:insertAttribute name="body"/>  
    </div>
  </body>
</html>