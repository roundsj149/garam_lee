    <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%-- 뒤로가기 방지하는거  차선책 --%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<head>
<meta charset="UTF-8">
<title>루트맵 고객센터</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/clientservice/inquiry_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<script src="${pageContext.request.contextPath}/resources/js/clientservice/inquiry_page.js"></script>
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
						<li class="list-group-item active"><a href="${pageContext.request.contextPath}/clientservice/inquiry_page.do" class="cs_menu cs-custom-link">1:1 문의하기</a></li>
						<li class="list-group-item"><a href="${pageContext.request.contextPath}/clientservice/myInquiry_page.do" class="cs_menu cs-custom-link">나의 문의내역</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="col-9 mt-4">
			<div class="row">
                <div id="cs_inquiry_titlebox">
                    <h4>1:1 문의하기</h4>
                </div>
			</div>
			<form id="frm_cs_inquiry" type="multiPart/">
				<div class="row" id="cs_inquiry_inputbox">
					<table class="table">
						<colgroup>
							<col width="10%">
							<col width="90%">
	                    </colgroup>
	                    <tbody>
						    <tr>
							    <th class="text-center cs_inquiry_th">유형</th>
							    <td class="text-left">
	                                <select class="form-control form-control-sm cs_inquiry_category_no" id="cs_inquiry_select">
	                                    <option value="1">여행지/맛집</option>
										<option value="2">루트공유</option>
										<option value="3">나의루트</option>
										<option value="4">자유게시판</option>
										<option value="5">계정관련</option>
										<option value="6">제안</option>
	                                </select>
	                            </td>
						    </tr>
						    <tr>
							    <th class="text-center cs_inquiry_th">제목</th>
							    <td class="text-left"><input type="text" class="form-control form-control-sm" id="cs_inquiry_title" maxlength="66"></td>
						    </tr>
						    <tr>
						    	<th class="text-center cs_inquiry_th">내용</th>
						    	<td class="text-left"><textarea class="form-control" rows="8" id="cs_inquiry_content" maxlength="333"></textarea></td>
	                        </tr>
	                        <tr id="cs_inquiry_tr">
							    <th class="text-center cs_inquiry_th">첨부</th>
							    <td class="text-left">
							    	<div class="custom-file mb-3">
										<input type="file" class="custom-file-input" id="upload_files" name="inquiry_upload_files" accept="image/*" required>
										<label class="custom-file-label" for="upload_files"></label>
										<div class="invalid-feedback">Example invalid custom file feedback</div>
									</div>
	                            </td>
						    </tr>
						</tbody>
	                  </table>
	            </div>
	        </form>
            <div class="row" id="cs_inquiry_submitbox">
                <button class="btn btn-outline-success btn-xs" id="cs_inquiry_submitbox_button">취소</button>
                <input type="button" class="btn btn-success btn-block" id="cs_inquiry_submitbox_sumbit" value="등록" onclick="inquiryProcess()">
            </div>
		</div>
	</div>
</div>
</body>