<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/top.jsp" %>
<div class="container">
  <div class="jumbotron">
  	
    <h1>처리중 에러가 발생했습니다.</h1>      
    <p>${msg}</p>
  </div>  
  <p><a href="${root}">메인으로 가기</a></p>
</div>
</body>
</html>