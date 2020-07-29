    <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<head>
<title>updateFreeboard</title>
<style>
	#freeboard-nav {
		border-bottom: 4px solid #58AF9E;
	    padding-bottom: 8px;
	}
</style>
</head>
<body>
	<div class="row mt-3"></div>
	<form
		action="${pageContext.request.contextPath }/freeboard/fb_update_content_process.do"
		method="post" enctype="multipart/form-data">
		<div class="row">
			<div class="col-3"></div>
			<div class="col">
				글쓴이: ${news.memberVo.member_nickname }<br> 제목: <input
					type="text" name="freeboard_title"
					value="${news.freeboardVo.freeboard_title}"><br> 내용: <br>
				<textarea rows="10" cols="70" name="freeboard_content">${news.freeboardVo.freeboard_content }</textarea>
				<br> <input type="hidden" name="freeboard_no"
					value="${news.freeboardVo.freeboard_no }">
			</div>
		</div>
		<div class="row">
		<div class="col-5"></div>
		<div class="col">
			<input class="btn btn-secondary btn-sm" type="submit" value="올리기">
			<a class="btn btn-danger btn-sm"
				href="${pageContext.request.contextPath }/freeboard/fb_main_page.do">
				취소</a>
		</div>
	</div>
	</form>
</body>