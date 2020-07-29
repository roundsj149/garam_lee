<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/faq_page.css">
	<script src="${pageContext.request.contextPath}/resources/js/admin/routeboard_list_page.js"></script>
</head>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>회원 게시글  > 루트공유</h1>
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
			 	   		<a class="nav-link active" href="${pageContext.request.contextPath}/admin/routeboard_list_page.do">루트공유 리스트</a>
				  	</li>
				  	<li class="nav-item">
				    	<a class="nav-link" href="${pageContext.request.contextPath}/admin/routeboard_blacklist_page.do">신고 리스트</a>
				  	</li>	
				</ul>
              <div class="card-body">
               <table id="example2" class="table table-bordered table-hover">
                  <thead>
                  <tr>
                    <th>글 번호</th>
                    <th>제목</th>
                    <th>좋아요 수</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>공유</th>
                    <th>상태</th>
                  </tr>
                  </thead>
                  <tbody id="RouteList">
				  </tbody>
                </table>
              </div>
              <div id="cs_notice_paginationbox">
	  			<ul class="pagination" id="cs_notice_pagination">
				</ul>
			  </div>
              <!-- /.card-body -->
              	<div class="card-footer">
					<div class="row" style="justify-content:center;">		

						<!-- 검색바 -->
						<div class="input-group" id="cs_notice_search_inputbox">
							<!-- 셀렉트 박스 -->
							<select id="search_category" name="select_search_no" class="custom-select input-group-text">
								<option value="0">전체</option>
								<option value="1">글 번호</option>
								<option value="2">제목</option>
								<option value="3">작성자</option>
							</select>
							<input id="route_search_input" class="form-control" name="faqSearchTitle" type="text" value="${faqSearchTitle}">
							<c:if test="${!empty faqCategoryNo}"><input type="hidden" name="faqCategoryNo" value="${faqCategoryNo}"></c:if>
							<button type="button" class="btn btn-sm btn-primary" onclick="search()"><i class="fas fa-search fa-lg"></i></button>
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