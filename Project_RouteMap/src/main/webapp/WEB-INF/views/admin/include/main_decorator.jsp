    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>
			<decorator:title default="admin_page" />
		</title>
		<!-- font관련 -->
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/common/font-style.css">
		<link rel="stylesheet"
			href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap">
		<!-- footer -->
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/common/footer.css">
		<!-- Font Awesome -->
		<script src="https://kit.fontawesome.com/fe02c43c55.js" crossorigin="anonymous"></script>
		<!-- Ionicons -->
		<link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
		<!-- overlayScrollbars -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/adminlte.min.css">
		<!-- Google Font: Source Sans Pro -->
		<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
		<!-- JQuery -->
		<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
		<!-- Bootstrap 4 -->
		<script src="${pageContext.request.contextPath}/resources/js/admin/bootstrap.bundle.min.js"></script>
		<!-- AdminLTE App -->
		<script src="${pageContext.request.contextPath}/resources/js/admin/adminlte.min.js"></script>
		<!-- AdminLTE for demo purposes -->
		<script src="${pageContext.request.contextPath}/resources/js/admin/demo.js"></script>
		<!-- 신고 -->
		<script src="${pageContext.request.contextPath}/resources/js/admin/report.js"></script>
		<decorator:head></decorator:head>
	</head>
	<body class="hold-transition sidebar-mini">
		<!-- Site wrapper -->
		<div class="wrapper">
		  <!-- Navbar -->
		  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
			<!-- Left navbar links -->
			<ul class="navbar-nav">
			  <li class="nav-item">
				<a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
			  </li>
			</ul>
			<!-- Right navbar links -->
			<ul class="navbar-nav ml-auto">
			  <!-- Messages Dropdown Menu -->
			  <li class="nav-item">
				<a class="nav-link" data-widget="control-sidebar" data-slide="true" href="#" role="button">
				  <i class="fas fa-th-large"></i>
				</a>
			  </li>
			</ul>
		  </nav>
		  <!-- /.navbar -->
		
		  <!-- Main Sidebar Container -->
		  <aside class="main-sidebar sidebar-dark-primary elevation-6">
			<!-- Brand Logo -->
			<a href="${pageContext.request.contextPath}/member/login_page.do" class="brand-link">
			  <img src="${pageContext.request.contextPath}/resources/img/admin/logo.png"
				   class="brand-image img-circle elevation-3"
				   style="opacity: .8">
			  <span class="brand-text font-weight-light">Route Map</span>
			</a>
		
			<!-- Sidebar -->
			<div class="sidebar">
			  <!-- Sidebar user (optional) -->
			  <div class="user-panel mt-3 pb-3 mb-3 d-flex">
				<div class="image">
				  <img src="${pageContext.request.contextPath}/resources/img/admin/user.jpg" class="img-circle elevation-2">
				</div>
				<div class="info dropdown">
				  <a href="#" class="d-block"><c:if test="${!empty sessionAdmin}">${sessionAdmin.admin_fullname}</c:if></a>
				  <c:if test="${!empty sessionAdmin}"><a href="${pageContext.request.contextPath }/member/admin_logout_process.do">로그아웃</a></c:if>
				</div>
			  </div>
			<div id="report" class="noticePopup" style="color:black; word-break:keep-all; display:grid; align-items:center; grid-template-columns:auto 1fr">
				
			</div>
			  <!-- Sidebar Menu -->
			  <nav class="mt-2">
				<ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
				  <!-- Add icons to the links using the .nav-icon class
					   with font-awesome or any other icon font library -->
				  <li class="nav-item has-treeview">
					<a href="${pageContext.request.contextPath}/admin/member_list_page.do" class="nav-link">
					  <i class="nav-icon fas fa-user"></i>
					  <p>
						회원 관리
					  </p>
					  <div id="memberBadge" class="badge badge-danger">
						
						</div>
					</a>
				  </li>
				  <li class="nav-item has-treeview">
					<a href="#" class="nav-link">
					  <i class="nav-icon fas fa-file-alt"></i>
					  <p>
						회원 게시판
						<i class="right fas fa-angle-left"></i>
					  </p>
					</a>
					<ul class="nav nav-treeview">
					  <li class="nav-item">
						<a href="${pageContext.request.contextPath}/admin/locationboard_list_page.do" class="nav-link">
						  <i class="far fa-circle nav-icon"></i>
						  <p>여행지/맛집</p>
						   <div id="locationBadge" class="badge badge-danger">
						
						</div>
						</a>
					  </li>
					  <li class="nav-item">
						<a href="${pageContext.request.contextPath}/admin/routeboard_list_page.do" class="nav-link">
						  <i class="far fa-circle nav-icon"></i>
						  <p>루트 공유</p>
						  <div id="routeBadge" class="badge badge-danger">
						
						</div>
						  
						</a>
					  </li>
					</ul>
				  </li>
				  <li class="nav-item has-treeview">
					<a href="#" class="nav-link">
					  <i class="nav-icon fas fa-edit"></i>
					  <p>
						관리자 게시판
						<i class="right fas fa-angle-left"></i>
					  </p>
					</a>
					<ul class="nav nav-treeview">
					  <li class="nav-item">
						<a href="${pageContext.request.contextPath}/admin/notice_page.do" class="nav-link">
						  <i class="far fa-circle nav-icon"></i>
						  <p>공지사항</p>
						</a>
					  </li>
					  <li class="nav-item">
						<a href="${pageContext.request.contextPath}/admin/faq_page.do" class="nav-link">
						  <i class="far fa-circle nav-icon"></i>
						  <p>자주묻는 질문</p>
						</a>
					  </li>
					  <li class="nav-item">
						<a href="${pageContext.request.contextPath}/admin/inquiry_page.do" class="nav-link">
						  <i class="far fa-circle nav-icon"></i>
						  <p>1:1 문의</p>
						</a>
					  </li>
					</ul>
				  </li>
				  <li class="nav-item has-treeview">
					<a href="${pageContext.request.contextPath}/admin/dashboard_page.do" class="nav-link">
					  <i class="nav-icon fas fa-chart-pie"></i>
					  <p>
						대쉬 보드
					  </p>
					</a>
				  </li>
				</ul>
			  </nav>
			  <!-- /.sidebar-menu -->
			</div>
			<!-- /.sidebar -->
		  </aside>
		<decorator:body></decorator:body>
		  
		  <!-- Site footer -->
		  <footer class="main-footer">
		    <strong>Copyright &copy; 2020-2020 <a href="http://adminlte.io">RouteMap</a>.</strong>
		    All rights reserved.
		    <div class="float-right d-none d-sm-inline-block">
		      <b>Version</b> 3.0.5
		    </div>
		  </footer>
		  </div>
	</body>
</html>
    
