<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
   // 로그인 체크
    function loginCheck() {
       
      var sessionCheck = $('#login_check').val();
      
      if(sessionCheck == "false") {
         alert('로그인 후 이용하실 수 있습니다.');
         location.href = "${pageContext.request.contextPath }/member/login_page.do?uri=myfar/fr_my_favorite_page.do";
      } else {
         
         location.href = "${pageContext.request.contextPath }/myfar/fr_my_favorite_page.do";
      }
    }
</script>
<!--header 시작-->
<header class="navbar navbar-expand-lg navbar-light bg-light">
   <!-- 로고 부분 -->
   <a class="navbar-brand" href="${pageContext.request.contextPath}/main_page.do"><img class="logo-img-size" alt="" src="${pageContext.request.contextPath}/resources/img/RouteMap_logo2.png"></a>
   <button class="navbar-toggler" type="button" data-toggle="collapse"
      data-target="#navbarNav" aria-controls="navbarNav"
      aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
   </button>
   <div class="ml-1 collapse navbar-collapse d-flex justify-content-end" id="navbarNav">
      <input id="login_check" type="hidden" value="${!empty sessionUser}">
      <c:choose>
         <c:when test="${!empty sessionUser}">
         	<span style="font-size:14px; padding-bottom:5px; color:grey"><strong style="font-size:14px;color:#58ae9e">
         	
         	${sessionUser.member_nickname }</strong> 님, 환영합니다</span><span style="padding-bottom:5px;"> &nbsp; ㅣ</span>
            <ul class="header-font navbar-nav">
               <li class="nav-font-size nav-item"><a class="nav-link" href="${pageContext.request.contextPath }/member/logout_process.do">로그아웃</a></li>
               <li class="nav-font-size nav-item"><a id="mypage-nav" class="nav-link" href="${pageContext.request.contextPath }/member/user_info_page.do">마이 페이지</a></li>
               <li class="nav-font-size nav-item"><a id="cs-nav" class="nav-link" href="${pageContext.request.contextPath }/clientservice/notice_page.do">고객센터</a></li>
               <li class="nav-font-size nav-item"><a id="note-nav" class="nav-link" href="${pageContext.request.contextPath }/note/nt_main_page.do">쪽지</a></li>
            </ul>
         </c:when>
         <c:otherwise>
            <ul class="header-font navbar-nav">
               <li class="nav-font-size nav-item"><a class="nav-link" href="${pageContext.request.contextPath }/member/login_page.do">로그인</a></li>
               <li class="nav-font-size nav-item"><a class="nav-link" href="${pageContext.request.contextPath }/member/join_member_page.do">회원가입</a></li>
               <li class="nav-font-size nav-item"><a id="cs-nav" class="nav-link" href="${pageContext.request.contextPath }/clientservice/notice_page.do">고객센터</a></li>
               <li class="nav-font-size nav-item"><a class="nav-link" href="${pageContext.request.contextPath }/admin/dashboard_page.do">관리자</a></li>
            </ul>
         </c:otherwise>
      </c:choose>
   </div>
</header>
<!--header 끝-->
<!-- 메뉴 바 시작 -->
<nav class="navbar navbar-expand-lg navbar-light border-top border-bottom shadow-sm">
   <div class="col-4"></div>
   <ul class="nav-font navbar-nav">
      <li id="location" class="nav-font-size navbar-brand" ><a id="location-nav" class="nav-link" href="${pageContext.request.contextPath}/locationboard/lc_main_page.do">여행지/맛집</a></li>
      <li id="route" class="nav-font-size navbar-brand ml-5"><a id="route-nav" class="nav-link" onclick="document.getElementsByClassName('active')" 
         href="${pageContext.request.contextPath }/routeboard/rt_main_page.do">루트공유</a></li>
      <li id="myfar" class="nav-font-size navbar-brand ml-5"><a id="my-nav" class="nav-link" href="javascript:void(0);" onClick='loginCheck(); return false'>나의 관심/루트</a></li>
      <li class="nav-font-size navbar-brand ml-5"><a id="freeboard-nav" class="nav-link" href="${pageContext.request.contextPath }/freeboard/fb_main_page.do">자유게시판</a></li>
   </ul>
   <div class="col-3"></div>
</nav>