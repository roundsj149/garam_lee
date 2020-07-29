    <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<head>
<title>루트맵 고객센터</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clientservice/notice_page.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/clientservice/notice_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
</head>
<body>
<!--고객센터 공지사항-->
<div class="container mt-5 pb-5">
    <div class="row" style="">
        <h2 id="cs_title"><i class="fas fa-id-card-alt mr-3" style="color:#58AF9E"></i>고객센터</h2>
	</div>
	<div class="row">
		<div class="col-3">
			<div class="row mt-4" id="cs_content">
				<div class="col">
					<ul class="list-group">
						<li class="list-group-item active"><a href="${pageContext.request.contextPath}/clientservice/notice_page.do" class="cs_menu cs-custom-link">공지사항</a></li>
							<li class="list-group-item">
								<a href="#faq_menulist" data-toggle="collapse" class="cs_menu cs-custom-link">자주하는 질문</a>
								<ul class="list-group list-group-flush collapse show mt-3" id="faq_menulist">
									<li class="list-group-item list-group-item-action"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do">전체</a></li>
									<li class="list-group-item list-group-item-action"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?faqCategoryNo=1">맛집/여행지</a></li>
									<li class="list-group-item list-group-item-action"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?faqCategoryNo=2">루트 공유</a></li>
									<li class="list-group-item list-group-item-action"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?faqCategoryNo=3">나의 관심/루트</a></li>
									<li class="list-group-item list-group-item-action"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?faqCategoryNo=4">자유게시판</a></li>
									<li class="list-group-item list-group-item-action"><a class="cs-custom-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?faqCategoryNo=5">기타</a></li>
								</ul>
							</li>
						<li class="list-group-item"><a href="${pageContext.request.contextPath}/clientservice/inquiry_page.do" class="cs_menu cs-custom-link">1:1 문의하기</a></li>
						<li class="list-group-item"><a href="${pageContext.request.contextPath}/clientservice/myInquiry_page.do" class="cs_menu cs-custom-link">나의 문의내역</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="col-9 mt-4">
			<div class="row">
				<div id="cs_notice_search">
					<p id="cs_notice_search_text">공지사항 검색</p>
					<form action="${pageContext.request.contextPath}/clientservice/notice_page.do" method="get">
					<div class="input-group" id="cs_notice_search_inputbox">
						<input id="notice_search_input" class="form-control" name="noticeSearchTitle" type="text" placeholder="검색어를 입력해주세요." value="${noticeSearchTitle}">
						<button type="submit" class="btn btn-sm btn-success"><i class="fas fa-search fa-lg"></i></button>
					</div>
					</form>
				</div>
			</div>
			<div class="row" id="cs_notice_list">
								<table class="table">
					<colgroup>
						<col width="10%">
						<col width="60%">
						<col width="30%">
					</colgroup>
					<thead>
					  <tr>
						<th class="text-center" scope="col">번호</th>
						<th class="text-left" scope="col">제목</th>
						<th class="text-center" scope="col">등록일</th>
					  </tr>
					</thead>
					<tbody>
						<c:forEach items="${noticeData }" var="notice">
						<tr>
							<td class="text-center">${notice.notice_no }</td>
							<td class="text-left"><a class="custom-link" href="${pageContext.request.contextPath }/clientservice/noticeContent_page.do?notice_no=${notice.notice_no}"> ${notice.notice_title }</a></td>
							<td class="text-center"><fmt:formatDate pattern="yyyy-MM-dd" value="${notice.notice_writedate}"/></td>
					  	</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="cs_notice_paginationbox">
					<ul class="pagination" id="cs_notice_pagination">
						<li class="page-item<c:if test="${noticePageData.currentPage <= 1}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/notice_page.do?currPage=${noticePageData.startPageGroup-1}&noticeSearchTitle=${noticeSearchTitle}"><i class="fas fa-angle-double-left"></i></a></li>
						<li class="page-item<c:if test="${noticePageData.currentPage <= 1}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/notice_page.do?currPage=${noticePageData.currentPage-1}&noticeSearchTitle=${noticeSearchTitle}"><i class="fas fa-angle-left"></i></a></li>
					  <c:forEach begin="${noticePageData.startPageGroup}" end="${noticePageData.endPageGroup}" var="i">
					  	<li class="page-item<c:if test="${noticePageData.currentPage==i}"> active</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/notice_page.do?currPage=${i}&noticeSearchTitle=${noticeSearchTitle}">${i}</a></li>
					  </c:forEach>
						<li class="page-item<c:if test="${noticePageData.currentPage >= noticePageData.totalPageCount}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/notice_page.do?currPage=${noticePageData.currentPage+1}&noticeSearchTitle=${noticeSearchTitle}"><i class="fas fa-angle-right"></i></a></li>
						<li class="page-item<c:if test="${noticePageData.currentPage >= noticePageData.totalPageCount}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/notice_page.do?currPage=${noticePageData.totalRecordsCount}&noticeSearchTitle=${noticeSearchTitle}"><i class="fas fa-angle-double-right"></i></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
</body>