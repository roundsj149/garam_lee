    <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/clientservice/faq_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<script src="${pageContext.request.contextPath}/resources/js/clientservice/faq_page.js"></script>
<script src="https://kit.fontawesome.com/fe02c43c55.js" crossorigin="anonymous"></script>

<title>루트맵 공지사항</title>
</head>
<body>
<!--고객센터 공지사항-->
<div class="container mt-5 pb-5">
    <div class="row">
        <h2 id="cs_title"><i class="fas fa-id-card-alt mr-3" style="color:#58AF9E"></i>고객센터</h2>
	</div>
	<div class="row">
		<div class="col-3">
			<div class="row mt-4" id="cs_content">
				<div class="col">
					<ul class="list-group">
						<li class="list-group-item "><a href="${pageContext.request.contextPath}/clientservice/notice_page.do" class="cs_menu cs-custom-link">공지사항</a></li>
							<li class="list-group-item">
								<a href="#faq_menulist" data-toggle="collapse" class="cs_menu cs-custom-link">자주하는 질문</a>
								<ul class="list-group list-group-flush collapse show mt-3" id="faq_menulist">
									<li class="list-group-item list-group-item-action <c:if test="${faqCategoryNo == null}">active</c:if>"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do">전체</a></li>
									<li class="list-group-item list-group-item-action <c:if test="${faqCategoryNo == 1}">active</c:if>"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?faqCategoryNo=1">맛집/여행지</a></li>
									<li class="list-group-item list-group-item-action <c:if test="${faqCategoryNo == 2}">active</c:if>"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?faqCategoryNo=2">루트 공유</a></li>
									<li class="list-group-item list-group-item-action <c:if test="${faqCategoryNo == 3}">active</c:if>"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?faqCategoryNo=3">나의 관심/루트</a></li>
									<li class="list-group-item list-group-item-action <c:if test="${faqCategoryNo == 4}">active</c:if>"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?faqCategoryNo=4">자유게시판</a></li>
									<li class="list-group-item list-group-item-action <c:if test="${faqCategoryNo == 5}">active</c:if>"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?faqCategoryNo=5">기타</a></li>
								</ul>
							</li>
						<li class="list-group-item"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/inquiry_page.do" class="cs_menu">1:1 문의하기</a></li>
						<li class="list-group-item"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/myInquiry_page.do" class="cs_menu" onclick="myInquiry()">나의 문의내역</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="col-9 mt-4">
			<div class="row">
				<div id="cs_notice_search">
					<p id="cs_notice_search_text">자주하는 질문 검색</p>
					<form action="${pageContext.request.contextPath}/clientservice/faq_page.do" method="get">
						<div class="input-group" id="cs_notice_search_inputbox">
							<input id="faq_search_input" class="form-control" name="faqSearchTitle" type="text" placeholder="검색어를 입력해주세요." value="${faqSearchTitle}">
							<c:if test="${!empty faqCategoryNo}"><input type="hidden" name="faqCategoryNo" value="${faqCategoryNo}"></c:if>
							<button type="submit" class="btn btn-sm btn-success"><i class="fas fa-search fa-lg"></i></button>
						</div>
					</form>
				</div>
			</div>
			<div class="row" id="cs_notice_list">
				<table class="table">
					<colgroup>
						<col width="80%">
						<col width="10%">
					</colgroup>
					<thead>
					  <tr>
						<th class="text-center" scope="col">질문</th>
						<th></th>
					  </tr>
					</thead>
					<tbody>
						<c:forEach items="${faqList }" var="faqList">
							<tr>
								<td class="text-left" style="padding-left : 5rem;">
									<a class="custom-link" href="#faq${faqList.faq_no}" data-toggle="collapse">${faqList.faq_title}</a>
								</td>
								<td><i class="fas fa-angle-down fa-lg faq_toggle_icon faq${faqList.faq_no}" style="padding-right : 5rem;"></i></td>
					  		</tr>
					  		<tr class="collapse faq_toggle" id="faq${faqList.faq_no}">
								<td class="text-left" style="padding-left : 5rem;">${faqList.faq_content}</td>
						  		<td></td>
					 		</tr>
						</c:forEach>
					</tbody>
				  </table>
				  <div id="cs_notice_paginationbox">
					<ul class="pagination" id="cs_notice_pagination">
						<li class="page-item<c:if test="${faqPageData.currentPage <= 1}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?currPage=${faqPageData.startPageGroup-1}&faqSearchTitle=${faqSearchTitle}"><i class="fas fa-angle-double-left"></i></a></li>
						<li class="page-item<c:if test="${faqPageData.currentPage <= 1}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?currPage=${faqPageData.currentPage-1}&faqSearchTitle=${faqSearchTitle}"><i class="fas fa-angle-left"></i></a></li>
					  <c:forEach begin="${faqPageData.startPageGroup}" end="${faqPageData.endPageGroup}" var="i">
					  	<li class="page-item<c:if test="${faqPageData.currentPage==i}"> active</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?currPage=${i}&faqSearchTitle=${faqSearchTitle}<c:if test="${!empty faqCategoryNo}">&faqCategoryNo=${faqCategoryNo}</c:if>">${i}</a></li>
					  </c:forEach>
						<li class="page-item<c:if test="${faqPageData.currentPage >= faqPageData.totalPageCount}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?currPage=${faqPageData.currentPage+1}&faqSearchTitle=${faqSearchTitle}"><i class="fas fa-angle-right"></i></a></li>
						<li class="page-item<c:if test="${faqPageData.currentPage >= faqPageData.totalPageCount}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?currPage=${faqPageData.totalRecordsCount}&faqSearchTitle=${faqSearchTitle}"><i class="fas fa-angle-double-right"></i></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
