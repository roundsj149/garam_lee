<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/faq_page.css">
	<script src="${pageContext.request.contextPath}/resources/js/admin/locationboard_blacklist_page.js"></script>
</head>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>여행지/맛집 신고 게시글 관리</h1>
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
			 	   		<a class="nav-link" href="${pageContext.request.contextPath}/admin/locationboard_list_page.do">여행지/맛집 리스트</a>
				  	</li>
				  	<li class="nav-item">
				    	<a class="nav-link active" href="${pageContext.request.contextPath}/admin/locationboard_blacklist_page.do">신고 리스트</a>
				  	</li>	
				</ul>
             <div class="card-body">
                <table id="example2" class="table table-bordered table-hover">
                  <thead>
                  <tr>
                    <th>번호</th>
                    <th>신고자 아이디</th>
                    <th>게시물 번호</th>
                    <th>게시물 제목</th>
                    <th>신고일</th>
                    <th>신고사유</th>
                    <th>확인여부</th>
                  </tr>
                  </thead>
                  <tbody id="location_blacklist">
                  	<!-- 신고 리스트 -->
                  </tbody>
                </table>
              </div>
              <div id="paginationbox">
	  			<ul class="pagination" id="location_blacklist_pagination" style="display:flex; justify-content:center">
	  			<!-- 
					<li class="page-item<c:if test="${faqPageData.currentPage <= 1}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?currPage=${faqPageData.startPageGroup-1}&faqSearchTitle=${faqSearchTitle}"><i class="fas fa-angle-double-left"></i></a></li>
					<li class="page-item<c:if test="${faqPageData.currentPage <= 1}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?currPage=${faqPageData.currentPage-1}&faqSearchTitle=${faqSearchTitle}"><i class="fas fa-angle-left"></i></a></li>
					  <c:forEach begin="${faqPageData.startPageGroup}" end="${faqPageData.endPageGroup}" var="i">
				  	<li class="page-item<c:if test="${faqPageData.currentPage==i}"> active</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?currPage=${i}&faqSearchTitle=${faqSearchTitle}<c:if test="${!empty faqCategoryNo}">&faqCategoryNo=${faqCategoryNo}</c:if>">${i}</a></li>
					  </c:forEach>
					<li class="page-item<c:if test="${faqPageData.currentPage >= faqPageData.totalPageCount}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?currPage=${faqPageData.currentPage+1}&faqSearchTitle=${faqSearchTitle}"><i class="fas fa-angle-right"></i></a></li>
					<li class="page-item<c:if test="${faqPageData.currentPage >= faqPageData.totalPageCount}"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath}/clientservice/faq_page.do?currPage=${faqPageData.totalRecordsCount}&faqSearchTitle=${faqSearchTitle}"><i class="fas fa-angle-double-right"></i></a></li>
				 -->
				</ul>
			  </div>
              <!-- /.card-body -->
              	<div class="card-footer">
					<div class="row" style="justify-content:center;">		

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