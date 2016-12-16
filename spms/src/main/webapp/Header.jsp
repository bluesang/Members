<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<div>
	SPMS(Simple Project Management System)
	<br/>
	<c:if test="${member.email != null }">
		<span>
			${member.name}
			<a href="<c:url value='/auth/logout'/>">로그아웃</a>
		</span>
	</c:if>
	<c:if test="${member.email == null }">
		<a href="<c:url value='/auth/login'/>">로그인</a>
	</c:if>
</div>