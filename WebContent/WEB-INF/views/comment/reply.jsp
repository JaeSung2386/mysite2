<%@page import="com.douzone.mysite.vo.CommentVo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	CommentVo vo = (CommentVo)request.getAttribute("vo");
	pageContext.setAttribute("vo", vo);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/comment">
					<input type = "hidden" name = "a" value="reply">
					<!-- user의 no와 현재 게시물의 no도 넘겨준다. -->
					<input type="hidden" name="no" value="${vo.no }">
					<input type="hidden" name="g_no" value="${vo.g_no }">
					<input type="hidden" name="o_no" value="${vo.o_no }">
					<input type="hidden" name="depth" value="${vo.depth }">
					<input type="hidden" name="board_no" value="${vo.board_no }"/>
					<input type="hidden" name="user_no" value="${authuser.no }"/>
					<table class="tbl-ex">
						<tr>
							<th colspan="2">댓글의 답글작성</th>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="contents" name="contents"></textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board?a=view&no=${vo.board_no}">취소</a>
						<input type="submit" value="등록">
					</div>
				</form>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>