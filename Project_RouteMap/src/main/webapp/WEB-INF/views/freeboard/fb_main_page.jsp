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
<title>mainFreeboard</title>
<style>
	#freeboard-nav {
		border-bottom: 4px solid #58AF9E;
	    padding-bottom: 8px;
	}
</style>
</head>
<body>
	<div class="container mt-6" style="min-height:700px">
		<div class="row">
			<div class="col-1"></div>
			<div class="col">
				<form class="./fb_main_page.do" method="get">

					<div class="row my-3">
						<div class="col-8"></div>
						<div class="col">
							<input name="word" type="text" class="form-control">
						</div>
						<div class="col">
							<input type="submit" class="btn btn-info btn-sm" value="찾기">
						</div>
					</div>
				</form>
				<div class="row">

					<table class="table table-hover">
						<thead>
							<tr>
								<td>번호</td>
								<td>제목</td>
								<td>글쓴이</td>
								<td>조회수</td>
								<td>쓴날짜</td>
								
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${dataList }" var="dl">
								<tr>
									<td>${dl.freeboardVo.freeboard_no}</td>
									<td><a
										href="./fb_read_content_page.do?freeboard_no=${dl.freeboardVo.freeboard_no }">${dl.freeboardVo.freeboard_title }</a></td>
									<td>${dl.memberVo.member_nickname }</td>
									<td>${dl.freeboardVo.freeboard_readcount }</td>
									<td><fmt:formatDate
											value="${dl.freeboardVo.freeboard_writedate }"
											pattern="yy.MM.dd"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="row mt-4">
					<div class="col-4"></div>
					<div class="col">
						<nav aria-label="page navigation example">
							<ul class="pagination">

								<li
									class="page-item<c:if test="${Spage-1 <= 0}"> disabled</c:if>"><a
									class="page-link"
									href="./fb_main_page.do?currPage=${Spage-1 }&word=${param.word}">&lt;</a></li>
								<c:forEach begin="${Spage }" end="${Epage }" var="i">
									<li class="page-item<c:if test="${currPage==i}"> active</c:if>"><a
										class="page-link"
										href="./fb_main_page.do?currPage=${i}&search_word=${param.word}">${i}</a></li>
								</c:forEach>
								<li
									class="page-item<c:if test="${Epage+1 > (sumCnt-1)/10+1 }"> disabled</c:if>"><a
									class="page-link"
									href="./fb_main_page.do?currPage=${Epage+1 }&word=${param.word}">></a></li>

							</ul>
						</nav>
					</div>
					<div class="col-2">
						<a class="btn btn-success btn-sm"
							href="./fb_write_content_page.do">쓰기</a>
					</div>

					<div class="col-2"></div>

				</div>
			</div>
		</div>
	</div>
</body>