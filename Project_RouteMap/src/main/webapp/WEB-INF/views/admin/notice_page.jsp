<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/faq_page.css">
	<script src="${pageContext.request.contextPath}/resources/js/admin/notice_page.js"></script>
</head>
<!-- Content Wrapper. Contains page content -->
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
				  <div class="col">
				  	<ul id="faq_category" class="search_category">
				  	</ul>
				  </div>
				  <div class="col" style="margin-top:2rem;">
					<div class="row" id="cs_notice_list">
						<table class="table">
							<colgroup>
								<col width="10%">
								<col width="60%">
								<col width="15%">
								<col width="15%">
							</colgroup>
							<thead>
							  <tr>
							  	<th class="text-center" scope="col">번호</th>
								<th class="text-center" scope="col">제목</th>
								<th class="text-center" scope="col">작성자</th>
								<th class="text-center" scope="col">작성일</th>
							  </tr>
							</thead>
							<tbody id="notice_list">

							</tbody>
						  </table>
						  <div id="cs_notice_paginationbox">
				  			<ul class="pagination" id="cs_notice_pagination">
			
							</ul>
							<a href="${pageContext.request.contextPath}/admin/notice_write_page.do" class="btn btn-info">글 등록</a>
						  </div>
					</div>
				  </div>
				</div>
				<!-- /.card-body -->
				
				<div class="card-footer">
					<div class="row" style="justify-content:center;">		

						<!-- 검색바 -->
						<div class="input-group" id="cs_notice_search_inputbox">
							<!-- 셀렉트 박스 -->
							<select id="notice_search_select" name="select_search_no" class="custom-select input-group-text" id="basic-addon1">
								<option value="0">전체</option>
								<option value="1">제목</option>
								<option value="2">작성자</option>
							</select>
							<input id="notice_search_input" class="form-control" name="faqSearchTitle" type="text" onkeyup="enterKey()">
							<button id="notice_search_submit" type="button" class="btn btn-sm btn-primary" onclick="test()"><i class="fas fa-search fa-lg"></i></button>
						</div>
					</div>
				</div>
				<!-- /.card-footer-->
			  </div>
			  <!-- /.card -->
		
			</section>
			<!-- /.content -->
		  </div>
		  <!-- /.content-wrapper -->