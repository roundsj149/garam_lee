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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/clientservice/myinquiry_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<title>루트맵 고객센터</title>
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
						<li class="list-group-item"><a href="${pageContext.request.contextPath}/clientservice/notice_page.do" class="cs_menu cs-custom-link">공지사항</a></li>
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
						<li class="list-group-item active"><a href="${pageContext.request.contextPath}/clientservice/myInquiry_page.do" class="cs_menu cs-custom-link">나의 문의내역</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="col-9 mt-4">
			<div class="row">
                <div id="cs_myinquiry_titlebox">
                    <h4>1:1 문의관리</h4>
                </div>
				<!-- <div id="cs_notice_search">
					<p id="cs_notice_search_text">자주하는 질문 검색</p>
					<div class="input-group" id="cs_notice_search_inputbox">
						<input class="form-control" id="notice_search_input" type="text">
						<button class="btn btn-sm btn-primary"><i class="fas fa-search fa-lg"></i></button>
					</div>
				</div> -->
			</div>
			<div class="row" id="cs_inquiry_inputbox">
				<table class="table">
					<colgroup>
						<col width="10%">
                        <col width="40%">
                        <col width="20%">
                        <col width="10%">
                        <col width="20%">
                        
                    </colgroup>
                    <thead>
                        <tr>
                            <th class="text-center">번호</th>
                            <th class="text-center">문의제목</th>
                            <th class="text-center">답변여부</th>
                            <th class="text-center">관리</th>
                            <th class="text-center">문의일자</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${dataList }" var="inquiryData">
                        	<tr>
	                        	<td class="text-center">${inquiryData.inquiryVo.inquiry_row}</td>
	                        	<td class="text-left"><a class="custom-link" href="${pageContext.request.contextPath}/clientservice/myInquiry_content_page.do?inquiry_no=${inquiryData.inquiryVo.inquiry_no}">${inquiryData.inquiryVo.inquiry_title }</a></td>
	                        	<td class="text-center">답변대기</td>
								<td class="text-center" style="padding:0.5rem"><a href="${pageContext.request.contextPath}/clientservice/myInquiry_update_page.do?inquiry_no=${inquiryData.inquiryVo.inquiry_no}"><button class="btn btn-success btn-sm">수정</button></a></td>
	                        	<td class="text-center"><fmt:formatDate pattern="yyyy-MM-dd" value="${inquiryData.inquiryVo.inquiry_writedate}"/></td>
	                        </tr>
                        </c:forEach>
					    <%-- <tr>
                            <td class="text-center">1</td>
                            <td class="text-left"><a href="#myinquiry_content" data-toggle="collapse">버그있어요</a></td>
                            <td class="text-center">답변대기</td>
                            <td class="text-center" style="padding:0.5rem"><a href="${pageContext.request.contextPath}/clientservice/myInquiry_update_page.do"><button class="btn btn-secondary btn-sm">수정</button></a></td>
                            <td class="text-center">2020.05.27</td>
                        </tr>
                        <tr id="myinquiry_content" class="collapse">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="text-center">2</td>
                            <td class="text-left">안되요</td>
                            <td class="text-center">답변완료</td>
                            <td class="text-center"></td>
                            <td class="text-center">2020.05.27</td>
					    </tr> --%>
					</tbody>
                  </table>
            </div>
            <div id="cs_notice_paginationbox">
					<ul class="pagination" id="cs_notice_pagination">
						<li class="page-item<c:if test="${inquiryPageData.currentPage <= 1}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/myInquiry_page.do?currPage=${inquiryPageData.startPageGroup-1}"><i class="fas fa-angle-double-left"></i></a></li>
						<li class="page-item<c:if test="${inquiryPageData.currentPage <= 1}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/myInquiry_page.do?currPage=${inquiryPageData.currentPage-1}"><i class="fas fa-angle-left"></i></a></li>
					  <c:forEach begin="${inquiryPageData.startPageGroup}" end="${inquiryPageData.endPageGroup}" var="i">
					  	<li class="page-item<c:if test="${inquiryPageData.currentPage==i}"> active</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/myInquiry_page.do?currPage=${i}">${i}</a></li>
					  </c:forEach>
						<li class="page-item<c:if test="${inquiryPageData.currentPage >= inquiryPageData.totalPageCount}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/myInquiry_page.do?currPage=${inquiryPageData.currentPage+1}"><i class="fas fa-angle-right"></i></a></li>
						<li class="page-item<c:if test="${inquiryPageData.currentPage >= inquiryPageData.totalPageCount}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/myInquiry_page.do?currPage=${inquiryPageData.totalRecordsCount}"><i class="fas fa-angle-double-right"></i></a></li>
					</ul>
				</div>
		</div>
	</div>
</div>
</body>