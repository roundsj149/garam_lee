    <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<head>
<title>writeFreeboard</title>
<style>
	#freeboard-nav {
		border-bottom: 4px solid #58AF9E;
	    padding-bottom: 8px;
	}
</style>
</head>
<body>
				<form
					action="${pageContext.request.contextPath}/freeboard/fb_write_content_process.do"
					method="post" enctype="multipart/form-data">

					<div class="row mt-3"></div>
					<div class="row">
						<div class="col-3"></div>
						<div class="col">
							글쓴이: ${sessionUser.member_nickname }<br> 제목: <input
								type="text" name="freeboard_title"><br> 내용: <br>

							<textarea rows="10" cols="70" name="freeboard_content"></textarea>
							<br> <label for="file">첨부하기</label> <input type="file"
								name="uploadfiles" multiple accept="image/*"><br>
						</div>
						<div class="col-3"></div>
					</div>
					<div class="row">
						<div class="col-5"></div>
						<div class="col">
						<input type="submit" class="btn btn-success btn-sm" value="올리기">

						<a class="btn btn-danger btn-sm"
							href="${pageContext.request.contextPath }/freeboard/fb_main_page.do">취소</a>
							</div>
					</div>
				</form>
</body>