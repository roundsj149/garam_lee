<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/home/main_page.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/home/main_page.js"></script>
<title>전체 메인페이지</title>
</head>
<body>
	<!-- wrap 시작 -->
	<div class="wrap" style="margin-bottom: 50px">
		<div class="row" style="margin: 0">
			<div class="col" style="padding: 0">
				<!-- 메인 화면 이미지 시작 -->
				<div id="carouselExampleCaptions" class="carousel slide"
					data-ride="carousel">
					<ol class="carousel-indicators">
						<li data-target="#carouselExampleCaptions" data-slide-to="0"
							class="active"></li>
						<li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
					</ol>
					<div class="carousel-inner">
						<div class="carousel-item active img-fluid" data-interval="10000"
							style="overflow: hidden; height: 450px;">
							<img
								src="${pageContext.request.contextPath}/resources/img/main_img1.jpg"
								class="d-block w-100" alt="Routemap 소개1">
							<div class="carousel-caption d-none d-md-block">
								<h5>RouteMap에 오신걸 환영합니다</h5>
								<p></p>
							</div>
						</div>
						<div class="carousel-item img-fluid"
							style="overflow: hidden; height: 450px;">
							<img
								src="${pageContext.request.contextPath}/resources/img/main_img1.jpg"
								class="d-block w-100" alt="Routemap 소개2">
							<div class="carousel-caption d-none d-md-block">
								<h5>RouteMap에서 나만의 여행 루트를 꾸며보세요</h5>
								<p></p>
							</div>
						</div>
					</div>
					<a class="carousel-control-prev" href="#carouselExampleCaptions"
						role="button" data-slide="prev"> <span
						class="carousel-control-prev-icon" aria-hidden="true"></span> <span
						class="sr-only">Previous</span>
					</a> <a class="carousel-control-next" href="#carouselExampleCaptions"
						role="button" data-slide="next"> <span
						class="carousel-control-next-icon" aria-hidden="true"></span> <span
						class="sr-only">Next</span>
					</a>
				</div>
				<!-- 메인 화면 이미지 종료 -->
			</div>
		</div>
		<!-- container 시작 -->
		<div class="container"
			style="display: flex; flex-direction: column; min-height: 700px">
			<form id="search_frm">
				<!-- 검색 바1 시작 -->
				<div class="row mt-5">
					<!-- 왼쪽 여백 -->
					<!-- 장소 or 루트 검색 선택 -->
					<div class="radio-container col form-check form-check-inline"
						style="display: flex; justify-content: center">
						<input id="locationRadio" class="form-check-input" type="radio"
							name="category_no" value="1" checked> <label
							class="form-check-label" for="locationRadio">여행지/맛집&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
						<input id="routeRadio" class="form-check-input" type="radio"
							name="category_no" value="2"> <label
							class="form-check-label" for="routeRadio">루트</label>
					</div>
				</div>
				<div class="row mt-4">
					<div class="col">
						<!-- 검색바 -->
						<div class="input-group mb-3"
							style="padding-left: 15px; padding-right: 15px;">
							<input type="text" name="search_word" id="search_word"
								class="form-control" placeholder="검색어를 입력하세요"
								onfocus="this.placeholder=''"
								onblur="this.placeholder='검색어를 입력하세요'" autocomplete="off"
								aria-label="Recipient's username"
								onkeypress="if(event.keyCode==13) {searchByTitle(); return false;}"
								aria-describedby="button-addon2">
							<div class="input-group-append">
								<button class="btn btn-outline-success" type="button"
									id="button-addon2" onclick="searchByTitle()">검색</button>
							</div>
						</div>
					</div>
				</div>
				<!-- 글 리스트 시작 -->
				<div class="container mt-4">
					<!-- row 시작 -->
					<div class="row">
						<!--장소 - 좋아요 높은 3건 출력 시작 -->
						<div class="col">
							<h3 style="margin-bottom: 20px">
								<i class="fas fa-paperclip mr-2" style="color: #58ae9e"></i>추천
								장소
							</h3>

							<c:forEach var="locationData" items="${locationboardDataList}">
								<c:if test="${locationData.locationboardVo != null}">
									<div class="card mb-3" style="max-width: 540px;">
										<div class="row no-gutters">
											<div class="col-md-4"
												style="width: 300px; height: 152px; overflow: hidden">
												<img style="width: 300px; height: auto;"
													src="../../routemap/storage/${locationData.locationboardUploadFileVo.locationboard_file_link_path}"
													class="card-img rounded-left" alt="이미지를 불러올 수 없습니다.">
											</div>
											<div class="col-md-8">
												<div class="card-body">
													<h5 class="card-title list-box-overflow">
														<a class="custom-link"
															href="${pageContext.request.contextPath}/locationboard/lc_read_content_page.do?locationboard_no=${locationData.locationboardVo.locationboard_no}">${locationData.locationboardVo.locationboard_title}</a>
													</h5>
													<p class="card-text list-box-overflow">${locationData.locationboardUploadFileVoList.locationboard_content}</p>
													<p class="card-text">
														<small class="text-muted"><fmt:formatDate
																value="${locationData.locationboardVo.locationboard_writedate}"
																pattern="yyyy.MM.dd hh:mm" /></small>
													</p>
												</div>
											</div>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<!-- 장소 - 좋아요 높은 3건 출력 끝 -->
						<!--루트 - 좋아요 높은 3건 출력 시작 -->
						<div class="col">
							<h3 style="margin-bottom: 20px">
								<i class="fas fa-paperclip mr-2" style="color: #58ae9e"></i>추천
								루트
							</h3>
							<c:forEach var="routeData" items="${routeboardDataList}">
								<c:if test="${routeData.routeboardVo != null}">
									<div class="card mb-3" style="max-width: 540px;">
										<div class="row no-gutters">
											<div class="col-md-4"></div>
											<div class="col-md-8">
												<div class="card-body">
													<h5 class="card-title list-box-overflow">
														<a class="custom-link"
															href="${pageContext.request.contextPath}/routeboard/rt_read_content_page.do?routeboard_no=${routeData.routeboardVo.routeboard_no}">${routeData.routeboardVo.routeboard_title}</a>
													</h5>
													<p class="card-text list-box-overflow">${routeData.routeboardVo.routeboard_content}</p>
													<p class="card-text">
														<small class="text-muted"><fmt:formatDate
																value="${routeData.routeboardVo.routeboard_writedate}"
																pattern="yyyy.MM.dd hh:mm" /></small>
													</p>
												</div>
											</div>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<!-- 루트 - 좋아요 높은 3건 출력 끝 -->
					</div>
					<!-- row 끝 -->
				</div>
				<!-- 글 리스트 끝-->
				<div class="col-3"></div>
				<i class="fas fa-arrow-circle-up fa-2x"
					onclick="$('html, body').stop().animate( { scrollTop : '0' } );"
					onmouseover="$(this).css('color','#58AF9E');"
					onmouseout="$(this).css('color','grey');"></i>
				<!-- 오른쪽 여백 -->
			</form>
		</div>
		<!-- container 끝 -->
	</div>
	<!-- wrap 끝 -->
</body>