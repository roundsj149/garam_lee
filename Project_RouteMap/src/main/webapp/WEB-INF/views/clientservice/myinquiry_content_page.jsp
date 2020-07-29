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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/clientservice/notice_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clientservice/myinquiry_content_page.js"></script>
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
				<!-- 공지사항 상세 위쪽 (타이틀, 배너)-->
				<div class="row">
					<div class="col"><h4>나의 문의내역</h4></div>
					<div class="col"><p style="font-size: 0.8rem; text-align:right;">HOME > 나의 문의내역</p></div>
				</div>
				<!-- 공지사항 상세테이블 -->
				<div class="row">
					<div class="col">
	                    <table class="table">
							<colgroup>
								<col width="15%">
	                            <col width="75%">
	                            <col width="10%">
		                    </colgroup>
		                    <tbody style="border: solid 1px #dee2e6;">
							    <tr>
								    <th class="text-center" style="border-right: solid 1px #dee2e6;" >유형</th>
								    <td class="text-left">
										<p style="margin:0rem;">${inquiryData.inquiryCategoryName}</p>
	                                </td>
	                                <td class="text-right"><span class="badge badge-pill badge-secondary"></span></td>
							    </tr>
							    <tr>
								    <th class="text-center" style="border-right: solid 1px #dee2e6;">제목</th>
								    <td colspan="2" class="text-left">
								    	<p style="margin:0rem;">${inquiryData.inquiryVo.inquiry_title}</p>
								    </td>
							    </tr>
							    <tr>
							    	<td colspan="3">
	                                    <p>${inquiryData.inquiryVo.inquiry_content}</p>
	                                    <c:if test="${!empty inquiryData.inquiryUploadFileVo.inquiry_file_link_path }">
	                                    	<img src="../../routemap/storage2/${inquiryData.inquiryUploadFileVo.inquiry_file_link_path }" class="rounded float-left">
	                                    </c:if>
	                                </td>
		                        </tr>
		                        <tr>
		                        	<th class="text-center" style="border-right : solid 1px #dee2e6;">첨부파일</th>
							    	<td colspan="2">
							    		<c:if test="${!empty inquiryData.inquiryUploadFileVo.inquiry_file_link_path }">
							    			${inquiryData.inquiryUploadFileVo.inquiry_file_link_path}
										</c:if>
	                           		</td>
		                        </tr>
							</tbody>
	                      </table>
	                      <div class="col" style="text-align:right;">
	                      	<a href="${pageContext.request.contextPath}/clientservice/myInquiry_update_page.do?inquiry_no=${inquiryData.inquiryVo.inquiry_no}"><button class="btn btn-success btn-xs" id="cs_inquiry_submitbox_button">수정</button></a>
	               		  	<c:if test="${inquiryData.inquiryVo.member_no == sessionUser.member_no}"><button class="btn btn-success btn-xs" onclick="inquiryDeleteProcess()">삭제</button></c:if>
	               		  	<input id="delete_inquiry_no" type="hidden" value="${inquiryData.inquiryVo.inquiry_no}">
	               		  	<a href="${pageContext.request.contextPath}/clientservice/myInquiry_page.do"><button class="btn btn-success btn-xs">목록</button></a>
	                      </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>