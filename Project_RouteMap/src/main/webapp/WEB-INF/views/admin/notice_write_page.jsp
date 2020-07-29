<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/faq_write_page.css">
	<script src="${pageContext.request.contextPath}/resources/js/admin/notice_write_page.js"></script>
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
					<div class="row" id="cs_inquiry_inputbox">
						<table class="table">
							<colgroup>
								<col width="10%">
								<col width="40%">
								<col width="10%">
								<col width="40%">
		                    </colgroup>
		                    <tbody>
							    <tr>
								    <th class="text-center cs_inquiry_th">제목</th>
								    <td colspan="3" class="text-left cs_inquiry_td"><input type="text" class="form-control form-control-sm" id="cs_notice_title" maxlength="66"></td>
							    </tr>
							    <tr>
							    	<th class="text-center cs_inquiry_th">작성자</th>
							    	<td class="text-left cs_inquiry_td">${sessionAdmin.admin_nickname}</td>
							    	<th class="text-center cs_inquiry_th">작성일</th>
							    	<td class="text-left cs_inquiry_td">${date}</td>
								</tr>
							    <tr>
							    	<th class="text-center cs_inquiry_th">내용</th>
							    	<td colspan="3" class="text-left cs_inquiry_td"><textarea class="form-control" rows="8" id="cs_notice_content" maxlength="333"></textarea></td>
		                        </tr>
							</tbody>
		                  </table>
		            </div>
				</div>
				<div class="row" id="cs_inquiry_submitbox">
	                <a class="btn btn-light btn" id="cs_inquiry_submitbox_button" href="${pageContext.request.contextPath}//admin/notice_page.do">취소</a>
	                <input type="button" class="btn btn-secondary btn-block" id="cs_inquiry_submitbox_sumbit" value="등록" onclick="noticeWriteProcess()">
            	</div>
				<!-- /.card-body -->
			  </div>
			  <!-- /.card -->
		
			</section>
			<!-- /.content -->
		  </div>
		  <!-- /.content-wrapper -->
	