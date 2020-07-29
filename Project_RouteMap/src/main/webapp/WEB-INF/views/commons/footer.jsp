
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Site footer -->
<footer class="site-footer">
	<div class="container">
		<div class="row">
			<div class="col-sm-12 col-md-6">
				<h6>About</h6>
				<p class="text-justify" style="word-break:keep-all">
					루트맵은 다양한 여행지와 맛집 정보를 공유하고 나만의 루트를 만들 수 있는 여행 플랫폼입니다.<br>
					몇 번의 클릭만으로 숨겨져 있던 보석 같은 인기 있는 여행지와 맛집을 만나볼 수 있고<br>
					상세한 검색으로 비용과 시간에 맞추어 완벽한 여행을 계획할 수 있습니다.<br>
					가족, 친구, 연인과 다녀온 여행지와 맛집 정보를 공유하고 경험치를 획득하여 레벨을 올려보세요.<br>
					나만의 여행지를 고를 수 있는 RouteMap에서 나의 관심과 루트로 여러분의 여행을 도와드립니다!</p>
			</div>
			<div class="col-xs-6 col-md-2 ml-5">
				<h6>Categories</h6>
				<ul class="footer-links">
					<li><a href="${pageContext.request.contextPath}/locationboard/lc_main_page.do">여행지/맛집</a></li>
					<li><a href="${pageContext.request.contextPath}/routeboard/rt_main_page.do">루트공유</a></li>
					<li><a href="${pageContext.request.contextPath}/myfar/fr_my_favorite_page.do">나의 관심/루트</a></li>
					<li><a href="${pageContext.request.contextPath}/freeboard/fb_main_page.do">자유게시판</a></li>
				</ul>
			</div>
			<div class="col-xs-6 col-md-3 mt-2">
				<!-- popup_chat include -->
				<jsp:include page="../popchat/popup_chat.jsp"></jsp:include>
			</div>
		</div>
		<hr>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-8 col-sm-6 col-xs-12">
				<p class="copyright-text">
					Copyright &copy; 2020 All Rights Reserved <br>Explorer
				</p>
			</div>
			<div class="col-md-4 col-sm-6 col-xs-12">
				<ul class="social-icons">
					<li><a class="facebook" href="#"><i class="fa fa-facebook"></i></a></li>
					<li><a class="twitter" href="#"><i class="fa fa-twitter"></i></a></li>
					<li><a class="linkedin" href="#"><i class="fa fa-linkedin"></i></a></li>
				</ul>
			</div>
		</div>
	</div>
</footer>