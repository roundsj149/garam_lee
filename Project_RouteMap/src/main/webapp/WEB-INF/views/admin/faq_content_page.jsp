<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/faq_write_page.css">
	<script src="${pageContext.request.contextPath}/resources/js/admin/faq_content_page.js"></script>
</head>
<!-- Content Wrapper. Contains page content -->
<input type="hidden" id="faq_no" value="${faqNo}">
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
									<col width="10%">
		                            <col width="50%">
		                            <col width="10%">
		                            <col width="30%">
			                    </colgroup>
			                    <tbody id="faq_content" style="border: solid 1px #dee2e6;">
								</tbody>
		                      </table>
		                      <div class="col" style="text-align:right;">
		                        <a href="${pageContext.request.contextPath}/admin/faq_page.do">
		                        	<button class="btn btn-secondary" id="cs_inquiry_submitbox_button" style="width:10%;">목록</button>
		                        </a>
		                        <a href="${pageContext.request.contextPath}/admin/faq_update_page.do?faqNo=${faqNo}">
		                        	<button class="btn btn-secondary" id="cs_inquiry_submitbox_button" style="width:10%;">수정</button>
		                        </a>
		                        	<button class="btn btn-secondary" id="cs_inquiry_submitbox_button" style="width:10%;" onclick="faq_delete_process()">삭제</button>
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
	