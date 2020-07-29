<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/faq_write_page.css">
	<script src="${pageContext.request.contextPath}/resources/js/admin/faq_update_page.js"></script>
</head>
<!-- Content Wrapper. Contains page content -->
<input type="hidden" id="faq_no" value="${faqContent.faqVo.faq_no}">
		  <div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
			  <div class="container-fluid">
				<div class="row mb-2">
				  <div class="col-sm-6">
					<h1>글 작성 > 자주묻는 질문</h1>
				  </div>
				</div>
			  </div><!-- /.container-fluid -->
			</section>
		
			<!-- Main content -->
			<section class="content">
		
			  <!-- Default box -->
			  <div class="card" style="width:100%; padding:1rem 10rem;">
				<div class="card-body">
					<div class="row">
						<div class="col">
		                    <table class="table">
								<colgroup>
									<col width="13%">
		                            <col width="77%">
		                            <col width="10%">
			                    </colgroup>
			                    <tbody style="border: solid 1px #dee2e6;">
			                  	 	<tr>
									    <th class="text-center" style="border-right: solid 1px #dee2e6;" >유형</th>
									    <td colspan="2" class="text-left">
											<select class="form-control form-control-sm cs_inquiry_category_no" id="faq_update_category">
			                                    <option value="1">여행지/맛집</option>
												<option value="2">루트공유</option>
												<option value="3">나의관심/루트</option>
												<option value="4">자유게시판</option>
												<option value="5">계정관련</option>
		                                	</select>
		                                </td>
								    </tr>
								    <tr>
									    <th class="text-center" style="border-right: solid 1px #dee2e6;" >제목</th>
									    <td colspan="2" class="text-left">
											<input class="form-control form-control-sm" id="faq_update_title" type="text" value="${faqContent.faqVo.faq_title}">
		                                </td>
								    </tr>
								    <tr>
									    <th class="text-center" style="border-right: solid 1px #dee2e6;">작성자</th>
									    <td colspan="2" class="text-left">
									    	<p id="faq_admin" style="margin:0rem;">${faqContent.faqVo.admin_no}</p>
									    </td>
								    </tr>
								    <tr>
								    	<td colspan="3">
		                                    <textarea class="form-control" rows="8" id="faq_update_content" maxlength="333">${faqContent.faqVo.faq_content}</textarea>
		                                </td>
			                        </tr>
								</tbody>
		                      </table>
		                      <div class="col" style="text-align:right;">
		                        <a href="${pageContext.request.contextPath}/admin/faq_content_page.do?faqNo=${faqContent.faqVo.faq_no}">
		                        	<button class="btn btn-secondary" id="cs_inquiry_submitbox_button" style="width:10%;">취소</button>
		                        </a>
		                        	<button class="btn btn-secondary" id="cs_inquiry_submitbox_button" style="width:10%;" onclick="faqUpdateProcess()">확인</button>
		                      </div>
						</div>
					</div>
				</div>
				<!-- /.card-body -->
			  </div>
			  <!-- /.card -->
		
			</section>
			<!-- /.content -->
		  </div>
		  <!-- /.content-wrapper -->
	