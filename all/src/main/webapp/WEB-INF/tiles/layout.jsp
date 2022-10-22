<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"
 %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 


<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
     <link href="/all/resources/main/css/header.css" rel="stylesheet"> 
      <title><tiles:insertAttribute name="title" /></title> 
  </head>
    <body>
    	<tiles:insertAttribute name="header"/>
	<div>
    	<tiles:insertAttribute name="body"/>  
    </div>
  </body>
</html>