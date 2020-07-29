<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/faq_write_page.css">
	<script src="${pageContext.request.contextPath}/resources/js/admin/notice_content_page.js"></script>
</head>
<!-- Content Wrapper. Contains page content -->
<input type="hidden" id="notice_no" value="${noticeNo}">
		  <div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
			  <div class="container-fluid">
				<div class="row mb-2">
				  <div class="col-sm-6">
					<h1>관리자 게시물 > 공지사항</h1>
				  </div>
				</div>
			  </div><!-- /.container-fluid -->
			</section>
		
			<!-- Main content -->
			<section class="content">
		
			  <!-- Default box -->
			  <div class="card" style="width:100%; padding:1rem 10rem;">
				<div class="card-body">
					<div class="row" id="cs_inquiry_inputbox">
						<table class="table">
							<colgroup>
								<col width="10%">
								<col width="40%">
								<col width="10%">
								<col width="40%">
		                    </colgroup>
		                    <tbody id="notice_content">
							</tbody>
		                  </table>
		            </div>
				</div>
                <div class="col" style="text-align:right;" id="notice_button">

                </div>
				<!-- /.card-body -->
			  </div>
			  <!-- /.card -->
		
			</section>
			<!-- /.content -->
		  </div>
		  <!-- /.content-wrapper -->
	