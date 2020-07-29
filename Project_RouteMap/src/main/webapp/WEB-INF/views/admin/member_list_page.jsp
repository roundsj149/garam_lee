<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/faq_page.css">
	<script src="${pageContext.request.contextPath}/resources/js/admin/member_list_page.js"></script>
</head>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>회원관리</h1>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-12">
            <div class="card">
            	<ul class="nav nav-tabs">
				  	<li class="nav-item">
			 	   		<a class="nav-link active" href="${pageContext.request.contextPath}/admin/member_list_page.do">회원리스트</a>
				  	</li>
				  	<li class="nav-item">
				    	<a class="nav-link" href="${pageContext.request.contextPath}/admin/member_blacklist_page.do">블랙리스트</a>
				  	</li>	
				</ul>
              <div class="card-body">
                <table id="example2" class="table table-bordered table-hover">
                  <thead>
                  <tr>
                    <th>번호</th>
                    <th>레벨</th>
                    <th>아이디(이메일)</th>
                    <th>이름</th>
                    <th>닉네임</th>
                    <th>전화번호</th>
                    <th>가입일</th>
                    <th>상태</th>
                  </tr>
                  </thead>
                  <tbody id="member_List">
				  </tbody>
                </table>
              </div>
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
              <!-- /.card-body -->
              	<div class="card-footer">
					<div class="row" style="justify-content:center;">		

						<!-- 검색바 -->
						<div class="input-group" id="member_search">
							<!-- 셀렉트 박스 -->
							<select id="search_category" name="select_search_no" class="custom-select input-group-text">
								<option value="0">전체</option>
								<option value="1">번호</option>
								<option value="2">아이디</option>
								<option value="3">이름</option>
								<option value="4">닉네임</option>
							</select>
							<input id="member_search_input" class="form-control" name="faqSearchTitle" type="text" value="${faqSearchTitle}" onkeyup="enterKey()">
							<c:if test="${!empty faqCategoryNo}"><input type="hidden" name="faqCategoryNo" value="${faqCategoryNo}"></c:if>
							<button id="search_button" type="button" class="btn btn-sm btn-primary" onclick="test()"><i class="fas fa-search fa-lg"></i></button>
						</div>
					</div>
				</div>
				<!-- /.card-footer-->
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </div>
      <!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->