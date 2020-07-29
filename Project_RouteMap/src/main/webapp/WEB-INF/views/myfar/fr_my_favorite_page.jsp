<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<head>
<!-- 메인 css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/myfar/fr_my_location_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/myfar/fr_banner.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내가 쓴 여행지/맛집 글</title>
</head>
<body>
   <!-- wrap 시작-->
   <div class="wrap" style="background-color:#f7f7f7; min-height:700px">
      <div class="container-lg pt-5 shadow-sm" style="background-color:#ffffff; height:auto;">
         <!-- container 시작-->
         <div class="row border-bottom pb-3">
             <div class="col ml-2"><h2><i class="far fa-calendar-check" style="color:#58AE9E ;"></i> 나의 관심/루트</h2></div>
         </div>
         <div class="row">
            <aside class="col-2 list-group list-group-flush mt-3">
             <input id="session_check" type="hidden" value="${sessionUser }">
               <ul class="side">
                <li class="aside_wrap"><a id="" href="${pageContext.request.contextPath }/myfar/fr_my_favorite_page.do">나의 관심</a></li>
                <li class="aside_wrap"><a href="${pageContext.request.contextPath }/myfar/fr_my_location_page.do">나의 여행지/맛집</a></li>
                <li class="aside_wrap"><a href="${pageContext.request.contextPath }/myfar/fr_my_route_page.do">나의 루트</a></li>
               </ul>
               <div><hr></div>
               <div class="banner" style="margin-left:10px";>
                  <a href="https://www.somebymi.com/"><img class="banner-img" alt="광고 배너" src="${pageContext.request.contextPath}/resources/img/ad.jpg"></a>
               </div>
            </aside>
            <section class="col border-left">
               <!-- 전체/여행지/맛집 선택 -->
               <div class="row mt-4 ml-3">
               		<h5>나의 관심</h5>
               </div>
               <div class="row mt-4 ml-3">
               		
                  <div class="col pl-0">
                  <div class="btn-group" role="group" aria-label="Basic example">
                     <button id="getMyAllLocation"
                        class="button-size btn btn-success">전체</button>
                     <button id="getMySightLocation" class="button-size btn btn-outline-success" onclick="categoryList(1)">여행지</button>
                     <button id="getMyFoodLocation" class="button-size btn btn-outline-success" onclick="categoryList(2)">맛집</button>
                     <button id="getMyRoute" class="button-size btn btn-outline-success" onclick="myfavoriteRouteList()">루트</button>
                  </div>
                   </div>
               </div>
               <!-- 글 리스트 시작 -->
               <div class="row">
                  <div class="col">
                     <div id="boardList" class="container list-container" style="min-height:600px">
                        <!-- 
                        <c:forEach var="locationList" items="${myLocationList }">
                           <div class="row">
                              <div class="col card-deck list-box-size mt-3">
                                 <div class="card">
                                    <img src="" class="card-img-top" alt="">
                                    <div class="card-body">
                                       <h5 class="card-title list-box-overflow">
                                          <a
                                             href="${pageContext.request.contextPath }/locationboard/lc_read_content_page.do?locationboard_no=${locationList.locationboard_no}">${locationList.locationboard_title}</a>
                                       </h5>

                                       <p class="card-text list-box-overflow">${locationList.locationboard_content}</p>
                                    </div>
                                    <div class="card-body padding">
                                       <p class="button">
                                          <a
                                             href="${pageContext.request.contextPath }/locationboard/lc_update_content_page.do?locationboard_no=${locationList.locationboard_no}">수정</a>
                                          <a href="javascript:void(0)"
                                             id="${locationList.locationboard_no}"
                                             onclick="delete_check(this.id); return false">&emsp;삭제</a>
                                       </p>
                                    </div>
                                    <div class="card-footer">
                                       <small class="text-muted">${locationList.locationboard_writedate}</small>
                                    </div>
                                 </div>
                              </div>
                           </div>
                        </c:forEach>
                         -->
                     </div>
                  </div>
                  <!-- 전체 글 리스트 종료 -->
               </div>
               <!-- 글 리스트 종료-->

               <!--  페이지 내비게이션 시작 -->
               <nav aria-label="Page navigation example" style="margin-bottom:30px; margin-right:70px">
                  <ul id="page_navi" class="pagination justify-content-center mt-3">
                     <!-- 
                     <li
                        class="page-item<c:if test="${currPage <= 1}"> disabled</c:if>"><a
                        class="page-link"
                        href="${pageContext.request.contextPath }/locationboard/lc_main_page.do?search_word=${param.search_word }&province_category_no=${param.province_category_no }&currPage=${currPage-1}">이전페이지</a></li>
                     <c:forEach begin="${beginPage}" end="${endPage}" var="i">
                        <li
                           class="page-item<c:if test="${currPage==i}"> active</c:if>"><a
                           class="page-link"
                           href="${pageContext.request.contextPath }/locationboard/lc_main_page.do?search_word=${param.search_word }&province_category_no=${param.province_category_no }&currPage=${i}">${i}</a></li>
                     </c:forEach>
                     <li class="page-item"><a class="page-link"
                        href="${pageContext.request.contextPath }/locationboard/lc_main_page.do?search_word=${param.search_word }&province_category_no=${param.province_category_no }&currPage=${currPage+1}">다음페이지</a></li>
                     -->
                  </ul>
               </nav>
               <!--  페이지 내비게이션 끝-->
            </section>
         </div>
      </div>
      <!-- container 종료-->
   </div>
   <!-- wrap 끝 -->
   <script src="${pageContext.request.contextPath}/resources/js/myfar/fr_my_favorite_page.js"></script>
</body>