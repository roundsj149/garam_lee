<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/faq_page.css">
	<script src="${pageContext.request.contextPath}/resources/js/admin/faq_page.js"></script>
</head>
<!-- Content Wrapper. Contains page content -->
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
				  <div class="col">
				  	<ul id="faq_category" class="search_category">
<!-- 				  		<li class="btn-info" onclick="faqList(0,1)">전체</li>
				  		<li class="btn-info" onclick="faqList(1,1)">여행지/맛집</li>
				  		<li class="btn-info" onclick="faqList(2,1)">루트공유</li>
				  		<li class="btn-info" onclick="faqList(3,1)">나의관심/루트</li>
				  		<li class="btn-info" onclick="faqList(4,1)">자유게시판</li>
				  		<li class="btn-info" onclick="faqList(5,1)">계정관련</li> -->
				  	</ul>
				  </div>
				  <div class="col" style="margin-top:2rem;">
					<div class="row" id="cs_notice_list">
						<table class="table">
							<colgroup>
								<col width="10%">
								<col width="10%">
								<col width="60%">
								<col width="10%">
								<col width="10%">
							</colgroup>
							<thead>
							  <tr>
							  	<th class="text-center" scope="col">번호</th>
								<th class="text-center" scope="col">카테고리</th>
								<th class="text-center" scope="col">제목</th>
								<th class="text-center" scope="col">작성자</th>
								<th class="text-center" scope="col">작성일</th>
							  </tr>
							</thead>
							<tbody id="faq_list">

							</tbody>
						  </table>
						  <div id="cs_notice_paginationbox">
				  			<ul class="pagination" id="cs_notice_pagination">
			
							</ul>
							<a href="${pageContext.request.contextPath}/admin/faq_write_page.do" class="btn btn-info">글 등록</a>
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
							<select id="category" name="select_search_no" class="custom-select input-group-text" id="basic-addon1">
								<option value="0">전체</option>
								<option value="1">제목</option>
								<option value="2">작성자</option>
							</select>
							<input id="faq_search_input" class="form-control" name="faqSearchTitle" type="text">
							<c:if test="${!empty faqCategoryNo}"><input type="hidden" name="faqCategoryNo" value="${faqCategoryNo}"></c:if>
							<button id="faq_search_submit" type="button" class="btn btn-sm btn-primary"><i class="fas fa-search fa-lg"></i></button>
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
	