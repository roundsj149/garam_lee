    <%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- jstl - 작성일 처리-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<head>
<!-- 장소 메인 css -->
<link rel="stylesheet"
   href="${pageContext.request.contextPath}/resources/css/routeboard/rt_read_content_page.css">
<!-- 카카오 지도 -->
<script type="text/javascript"
   src="//dapi.kakao.com/v2/maps/sdk.js?appkey=04bd7fa6b50e0645001fa4622f6cb63b"></script>
<title>루트공유 글 읽기</title>
</head>
<body>
<div class="wrap pb-5 bg-light" style="min-height:700px">
   <!-- 컨테이너 시작 -->
   <div class="container py-5">
   <div class="row text-left">
         <div class="col" style="padding-bottom:20px; font-size:20px; font-weight:bold">루트공유 글읽기</div>
      </div>
      <div class="row">
         <div class="col">
            <!-- 가운데 내용 들어가는 부분 -->
            <!-- 페이지 네비게이션 -->
            <div class="row py-3"
               style="text-align: left; padding: 0px 0px 16px 0px; border-bottom: solid 2px #cccccc;">
               <div class="col">메인 페이지 > 루트 공유</div>
            </div>
               <!-- 루트쪽 -->
               <input type="hidden" id="routeboard_no"
                  value="${routeboardData.routeboardVo.routeboard_no}">
               <div class="row mt-3 ">
                  <div class="col-1" style="color:grey">제목</div>
                  <div class="col-7" style="padding:0px;">
                     <!-- 루트 제목 -->
                     <h5 style="width:95%;">${routeboardData.routeboardVo.routeboard_title}</h5>
                  </div>
                  <!-- 루트 작성일 -->
                  <div class="col-3" style="padding:0px; display:block">
                     <div style="display:flex;">
                        <div style="margin-right:1rem; color:grey">작성일</div>
                        <div
                           style="font-size: 13px; padding: 0px; margin-top:0.2rem; color: gray">
                           <fmt:formatDate
                              value="${routeboardData.routeboardVo.routeboard_writedate }"
                              pattern="yyyy.MM.dd HH:mm" />
                        </div>
                     </div>
                     <!-- 루트 - 조회수 -->
                     <div class="mt-3" style="display:flex;">
                        <div style="margin-right:1rem; color:grey">조회수</div>
                        <div style="font-size : 13px; margin-top:0.2rem; color:gray;">${routeboardData.routeboardVo.routeboard_readcount }</div>
                     </div>
                  </div>
               </div>
               <div class="row mt-2">
                  <!-- 루트 - 작성자 -->
                  <div class="col-1" style="color:grey">작성자</div>
                  <div class="col-2"
                     style="display: flex; align-items: center; color: grey; padding: 0px">
                     <!-- 레벨 -->
                     <div class="badge badge-pill badge-danger" style="margin-right : 0.4rem;">
                        Lv.
                        <c:choose>
                           <c:when
                              test="${routeboardData.levelCategoryVo.level_category_no < 10}">0${routeboardData.levelCategoryVo.level_category_no}</c:when>
                           <c:otherwise>${routeboardData.levelCategoryVo.level_category_no}</c:otherwise>
                        </c:choose>
                     </div>
                     <!-- 장소 - 작성자 닉네임 -->
                     <div class="dropdown">
                        <div id="dropdownMenuButton" class="col" style="padding: 0px"
                           <c:if test="${!empty sessionUser && sessionUser.member_no != routeboardData.memberVo.member_no }">data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"</c:if>>
                           ${routeboardData.memberVo.member_nickname }</div>
                        <c:if
                           test="${!empty sessionUser && sessionUser.member_no != routeboardData.memberVo.member_no }">
                           <div id="content" class="dropdown-menu"
                              aria-labelledby="dropdownMenuButton">
                              <a href="javascript:void(0);" id="writeNote" onclick='sendUser("${routeboardData.memberVo.member_id}"); return false' class="dropdown-item">쪽지 보내기</a> 
                              <a
                                 href='javascript:void(0);' id="reportUser"
                                 onclick='reportUser(${routeboardData.memberVo.member_no }, ${routeboardData.routeboardVo.routeboard_no }); return false'
                                 class="dropdown-item">신고하기</a>
                           </div>
                        </c:if>
                     </div>
                  </div>
                  <!-- 우측 정렬 임시 -->
                  <div class="col-5"></div>
                  <!--루트 - 좋아요 표시 -->
                  <span style="color:gray; margin-right:1rem; margin-top:0.2rem;">좋아요</span>
                  <span style="margin-right:2rem; margin-top:0.2rem;">

                     <input type="hidden" id="session_check"
                        value="${!empty sessionUser }"> <i
                        id="${routeboardData.routeboardVo.routeboard_no}"
                        class="far fa-heart fa-lg"
                        style="color: #f06595; cursor: pointer; font-size:1rem;"
                        onclick="like_func(this.id)"></i><span
                        id="like_${routeboardData.routeboardVo.routeboard_no}"
                        style="color : gray; font-size: 12px">${route_like_count }</span>
                  </span>
                  <!-- 루트 - 관심등록 -->
                  <span style="color:gray; margin-right:1rem; margin-top:0.2rem;">관심</span>
                  <span style="margin-top:0.2rem;">
                     <i
                        <c:choose>
                           <c:when test="${!empty routeboardData.myFarRouteVo}">class="fas fa-flag myfavorite fa-lg"</c:when>
                           <c:when test="${empty routeboardData.myFarRouteVo }">class="far fa-flag myfavorite fa-lg"</c:when>
                        </c:choose>
                        id="favorite_${routeboardData.routeboardVo.routeboard_no}"
                        onclick="favorite_check(this.id)"
                        style="color: green; cursor: pointer; font-size:1rem; margin-right:2rem;"> </i>
                  </span>
                  <!-- 게시물 신고 -->
                  <c:if
                     test="${!empty sessionUser && sessionUser.member_no != routeboardData.memberVo.member_no}">
                     <span style="color:gray; margin-right:1rem; margin-top:0.2rem;">신고
                        <i id="report_route"
                           class="fas fa-bullhorn fa-lg"
                           style="color: red; cursor: pointer; font-size:1rem; margin-right:2rem;"
                           onclick="reportRoute(${routeboardData.routeboardVo.routeboard_no})"></i>
                     </span>
                  </c:if>
               </div>
               <div class="row pb-3"
                  style="border-bottom: solid 2px #cccccc;">
               </div>
               <div class="row mt-3 mb-5 ">
                  <div class="col" style="display:flex; align-items:center">
                     <i class="fas fa-map-signs fa-2x" style="color:#58AF9E"></i>&nbsp;&nbsp;<strong>나의
                        루트</strong>
                  </div>
               </div>
               <!-- 장소글 반복 시작 -->
               <c:forEach var="data" items="${locationboardDataList}"
                  varStatus="status">
                  <div class="row">
                     <div class="col panel-group" id="accordion" role="tablist"
                        aria-multiselectable="true" style="width:100%">
                        <div class="panel panel-default">
                           <div class="panel-heading" role="tab" id="headingOne">
                              <div class="col" style="display:flex">
                              <input type="hidden" id="locationboard_no" value="${data.locationboardVo.locationboard_no }">
                           
                                 <c:choose>
                                    <c:when test="${status.first }">

                                       <div class="col-1 my-3 text-center">
                                          <i class="icon fas fa-plane-departure fa-2x"></i>
                                       </div>
                                       <div class="col">
                                       <p class="triangle-border left mb-0" style="display:flex; justify-content:space-between; align-items:center">
                                             <a data-toggle="collapse" data-parent="#accordion"
                                                class="custom-link" href="#collapse${status.index}" aria-expanded="true"
                                                aria-controls="collapseOne" style="font-size: 13px">
                                                <strong>${data.provinceCategoryVo.province_category_name }&nbsp;${data.locationCategoryVo.location_category_name }<i
                                                   class="fas fa-angle-right"></i></strong>
                                                ${data.locationboardVo.locationboard_title }
                                                
                                             </a>
                                             <span>
                                                <i id="location_${data.locationboardVo.locationboard_no}"
                                                class="far fa-heart fa-lg" style="color: #f06595;cursor: pointer"
                                                onclick="location_like_func(this.id)"></i>
                                                <span
                                                   id="location_like_${data.locationboardVo.locationboard_no}"
                                                   style="font-size: 12px">${data.locationboardCount }</span>
                                                <i
                                                   <c:choose>
                                                      <c:when test="${!empty data.myFarVo}">class="fas fa-flag myfavorite fa-lg"</c:when>
                                                      <c:when test="${empty data.myFarVo}">class="far fa-flag myfavorite fa-lg"</c:when>
                                                   </c:choose>
                                                   id="lcfavorite_${data.locationboardVo.locationboard_no}"
                                                   onclick="favorite_locationcheck(this.id)"
                                                   style="color: green; cursor: pointer;"> </i>
                                             </span>
                                          </p>
                                       </div>
                                    </c:when>
                                    <c:when test="${status.last }">
                                       <div class="col-1 my-3 text-center">
                                          <i class="icon fas fa-plane-arrival fa-2x"></i>
                                       </div>
                                       <div class="col">
                                          <p class="triangle-border left mb-0" style="display:flex; justify-content:space-between; align-items:center">
                                             <a data-toggle="collapse" data-parent="#accordion"
                                                class="custom-link" href="#collapse${status.index}" aria-expanded="true"
                                                aria-controls="collapseOne" style="font-size: 13px">
                                                <strong>${data.provinceCategoryVo.province_category_name }&nbsp;${data.locationCategoryVo.location_category_name }<i
                                                   class="fas fa-angle-right"></i></strong>
                                                ${data.locationboardVo.locationboard_title }
                                             </a>
                                             <span style="float:right">
                                                <i id="location_${data.locationboardVo.locationboard_no}"
                                                   class="far fa-heart fa-lg" style="color: #f06595;cursor: pointer"
                                                   onclick="location_like_func(this.id)"></i><span
                                                   id="location_like_${data.locationboardVo.locationboard_no}"
                                                   style="font-size: 12px">${data.locationboardCount }</span>
                                                   
                                                <i
                                                   <c:choose>
                                                      <c:when test="${!empty data.myFarVo}">class="fas fa-flag myfavorite fa-lg"</c:when>
                                                      <c:when test="${empty data.myFarVo}">class="far fa-flag myfavorite fa-lg"</c:when>
                                                   </c:choose>
                                                   id="lcfavorite_${data.locationboardVo.locationboard_no}"
                                                   onclick="favorite_locationcheck(this.id)"
                                                   style="color: green; cursor: pointer;"> </i>
                                             </span>
                                          </p>
                                          
                                       </div>
                                    </c:when>
                                    <c:otherwise>
                                       <div class="col-1 my-3 text-center">
                                          <i class="icon fas fa-road fa-2x"></i>
                                       </div>
                                       <div class="col">
                                          <p class="triangle-border left mb-0" style="display:flex; justify-content:space-between; align-items:center">
                                             <a data-toggle="collapse" data-parent="#accordion"
                                                class="custom-link" href="#collapse${status.index}" aria-expanded="true"
                                                aria-controls="collapseOne" style="font-size: 13px">
                                                <strong>${data.provinceCategoryVo.province_category_name }&nbsp;${data.locationCategoryVo.location_category_name }<i
                                                   class="fas fa-angle-right"></i></strong>
                                                ${data.locationboardVo.locationboard_title }
                                             </a>
                                             <span style="float:right">
                                                <i id="location_${data.locationboardVo.locationboard_no}"
                                                   class="far fa-heart fa-lg" style="color: #f06595;cursor: pointer"
                                                   onclick="location_like_func(this.id)"></i><span
                                                   id="location_like_${data.locationboardVo.locationboard_no}"
                                                   style="font-size: 12px">${data.locationboardCount }</span>
                                                   <i
                                                   <c:choose>
                                                      <c:when test="${!empty data.myFarVo}">class="fas fa-flag myfavorite fa-lg"</c:when>
                                                      <c:when test="${empty data.myFarVo}">class="far fa-flag myfavorite fa-lg"</c:when>
                                                   </c:choose>
                                                   id="lcfavorite_${data.locationboardVo.locationboard_no}"
                                                   onclick="favorite_locationcheck(this.id)"
                                                   style="color: green; cursor: pointer;"> </i>
                                             </span>
                                          </p>
                                       </div>
                                    </c:otherwise>
                                 </c:choose>
                              </div>
                           </div>
                           <div id="collapse${status.index}"
                              class="${status.index eq 0 ? 'panel-collapse collapse in':'panel-collapse collapse'}"
                              role="tabpanel" aria-labelledby="headingOne">
                              <div class="panel-body">
                                 <!-- 카드 바디 -->
                                 <div class="row card-body" id="location_box">
                                    <div class="col">
                                       <div class="row" id="location_title">
                                          <!-- 장소 카테고리 : 여행지, 맛집 구분-->
                                          <a class="custom-link" href="${pageContext.request.contextPath }/locationboard/lc_read_content_page.do?locationboard_no=${data.locationboardVo.locationboard_no }">${data.locationboardVo.locationboard_title }<i style="margin-left:2rem;" class="fas fa-search"></i></a>
                                       </div>
                                       <div class="row mt-5" id="small_category">
                                          <!-- 시/도 구분  -->
                                          <div><i style="margin-left:0rem;" class="fas fa-map-marked-alt"></i></div>
                                          <div class="small_category_content">${data.provinceCategoryVo.province_category_name }</div>
                                          <!-- 비용 -->
                                          <div><i class="fas fa-won-sign"></i></div>
                                          <div class="small_category_content"><fmt:formatNumber value="${data.linkVo.link_cost }" pattern="#,###원"/></div>
                                          <!-- 소요시간 -->
                                          <!-- 분을 시간과 분으로 바꾸기 -->
                                          <div><i class="far fa-clock"></i></div>
                                          <div class="small_category_content">
                                             <fmt:parseNumber var="hour"
                                                value="${data.linkVo.link_time/60 }" integerOnly="true" />
                                             <c:if test="${hour != 0}">${hour }시간</c:if>
                                             <c:if test="${data.linkVo.link_time%60 != 0}">
                                                ${data.linkVo.link_time%60}분</c:if>
                                          </div>
                                          <!-- 유형 -->
                                          <div><i class="fas fa-users"></i></div>
                                          <div class="small_category_content">${data.typeCategoryVo.type_category_name }</div>
                                          <!-- 분위기 -->
                                          <div><i class="fas fa-music"></i></div>
                                          <div style="border-right:0px;" class="small_category_content">${data.moodCategoryVo.mood_category_name }</div>
                                       </div>
                                       <!-- ------------------------ -->
				                        <div class="row" id="small_category">
				                           <!-- 장소명 -->
				                           <div><i style="margin-left:0rem;" class="fas fa-store"></i></div>
				                           <div class="small_category_content">${data.locationboardVo.locationboard_storename }</div>
				                           <!-- 주소  -->
				                          <div><i style="margin-left:0rem;" class="fas fa-map-pin"></i></div>
				                           <div class="small_category_content">${data.locationboardVo.locationboard_storeaddress }</div>
				                        </div>
				                        <!-- ---------------------- -->
                                       <!-- 내용 -->
                                       <div class="row mt-3" id="location_content">
                                          <div class="col">
                                             <div class="row card-body bg-white">
                                                <div class="col">
                                                   ${data.locationboardVo.locationboard_content }</div>
                                             </div>
                                          </div>
                                       </div>
                                       <!-- 사진 슬라이드 시작 -->
                                       <div class="row mt-3" style="text-align: center;">
                                          <div class="col px-0">
                                             <div id="carouselExampleInterval" class="carousel slide"
                                                data-ride="carousel">
                                                <div class="carousel-inner"
                                                   style="text-align: center; width: 100%; height: 100%;">
                                                   <c:forEach
                                                      items="${data.locationboardUploadFileVoList }"
                                                      var="locationboardUploadFileVo" varStatus="status2">
                                                      <c:choose>
                                                         <c:when test="${status2.index eq 0}">
                                                            <div class="carousel-item active"
                                                               data-interval="3000">
                                                               <img
                                                                  src="../../routemap/storage/${locationboardUploadFileVo.locationboard_file_link_path}"
                                                                  class="d-block" alt="이미지를 불러올 수 없습니다."
                                                                  style="width: 100%; height: 480px;">
                                                            </div>
                                                         </c:when>
                                                         <c:otherwise>
                                                            <div class="carousel-item" data-interval="3000">
                                                               <img
                                                                  src="../../routemap/storage/${locationboardUploadFileVo.locationboard_file_link_path}"
                                                                  class="d-block" alt="이미지를 불러올 수 없습니다."
                                                                  style="width: 100%; height: 480px;">
                                                            </div>
                                                         </c:otherwise>
                                                      </c:choose>
                                                   </c:forEach>
                                                </div>
                                                <a class="carousel-control-prev"
                                                   href="#carouselExampleInterval" role="button"
                                                   data-slide="prev"> <span
                                                   class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                   <span class="sr-only">Previous</span>
                                                </a> <a class="carousel-control-next"
                                                   href="#carouselExampleInterval" role="button"
                                                   data-slide="next"> <span
                                                   class="carousel-control-next-icon" aria-hidden="true"></span>
                                                   <span class="sr-only">Next</span>
                                                </a>
                                             </div>
                                          </div>
                                       </div>
                                       <!-- 사진 슬라이드 끝 -->
                                    </div>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
                  <c:if test="${status.last == false}">
                     <div class="row my-4" style="display:center; justify-content:center;">
<!--                         <div class="col my-3 ml-5 pl-3"> -->
                           <i class="fas fa-arrow-down fa-2x" style="color: #dc3545"></i>
                     </div>
                  </c:if>
               </c:forEach>
               <!-- 장소글 반복 끝-->
               <div class="row my-5 pt-3 " style="border-top: 1px solid #cccccc">
                  <div class="col" style="display:flex; align-items:center">
                     <i class="fas fa-map-signs fa-2x" style="color:#58AF9E"></i>&nbsp;&nbsp;<strong>루트
                        정보</strong>
                  </div>
               </div>
               <!-- 총소요시간, 총비용 -->
               <div class="row text-left ">
               <div class="col" style="display:flex">
                  <div class="col" style="font-size: 13px">
                     <i class="fas fa-won-sign fa-lg" style="color: #dc3545"></i> 총
                     경비: <fmt:formatNumber value="${routeboardData.routeboardVo.routeboard_cost}" pattern="#,###원"/>
                  </div>
                  <div class="col" style="font-size: 13px">
                     <i class="far fa-clock fa-lg" style="color: #dc3545"></i> 총
                     소요시간:
                     <fmt:parseNumber var="hour"
                        value="${routeboardData.routeboardVo.routeboard_time/60 }"
                        integerOnly="true" />
                     <c:if test="${hour != 0}">${hour } 시간</c:if>
                     <c:if
                        test="${routeboardData.routeboardVo.routeboard_time%60 != 0}">   
                                   ${routeboardData.routeboardVo.routeboard_time%60}분</c:if>
                  </div>
                  </div>
               </div>
               <!-- 루트쪽 - 내용 -->
               <div class="row my-5 pt-3 " style="border-top: 1px solid #cccccc">
                  <div class="col" style="display:flex; align-items:center">
                     <i class="fas fa-map-signs fa-2x" style="color:#58AF9E"></i>&nbsp;&nbsp;<strong>루트
                        후기</strong>
                  </div>
               </div>
               <div class="row " >
                  <div class="col mx-4 px-4 py-4" style="border-radius: 20px; background-color:#f6f6f6; min-height:300px">${routeboardData.routeboardVo.routeboard_content }</div>
               </div>
               <!-- 시간 카테고리 ${routeboardData.routeboardVo.time_category_no}
                   비용 카테고리${routeboardData.routeboardVo.cost_category_no}
                -->
               <div class="row mt-1" style="text-align: right">
                  <!-- 버튼 -->
                  <div class="col mt-5 pr-0">
                     <c:if test="${!empty sessionUser }">
                        <!-- 글쓰기 -->
                        <a
                           href="${pageContext.request.contextPath }/routeboard/rt_write_content_page.do">
                           <input class="btn btn-success" type="button"
                           value="글쓰기">
                        </a>
                     </c:if>
                     <c:if
                        test="${sessionUser.member_no == routeboardData.memberVo.member_no}">
                        <!-- 수정-->
                        <a
                           href="${pageContext.request.contextPath }/routeboard/rt_update_content_page.do?routeboard_no=${routeboardData.routeboardVo.routeboard_no}">
                           <input class="btn btn-success" type="button"
                           value="수정">
                        </a>
                        <!-- 삭제-->
                        <input class="btn btn-outline-success" type="button" value="삭제"
                           onclick="delete_check(${routeboardData.routeboardVo.routeboard_no})">
                     </c:if>
                     <!-- 목록 -->
                     <a
                        href="${pageContext.request.contextPath }/routeboard/rt_main_page.do">
                        <input class="btn btn-outline-success" type="button" value="목록">
                     </a>
                  </div>
               </div>
               <!-- 댓글 박스 리스트 시작 -->
               <div class="row mt-2 " style="border-top: solid 2px #cccccc">
                  <div class="col px-0">
                     
                     <div class="row mt-4 ">
                        <!-- 댓글 반복 시작 -->
                        <div id="reply_box" class="col">
                           <!-- 자바스크립트에 있음! -->
                        </div>
                        <!-- 댓글 반복 끝-->
                     </div>
                  </div>
               </div>
               <!-- 댓글 박스 리스트 끝 -->
               <!-- 댓글 작성 박스 시작-->
               <div class="row ">
                        <!-- 작성자 정보 -->
                        <div class="col" style="padding: 0px">
                           <!-- 댓글 쓰기 버튼 -->
                           <div class="col">
                              <strong><i class="far fa-comment-dots mr-1"></i>댓글 달기</strong>
                           </div>
                           <div class="col">
                              <textarea id="routeboard_reply_content"
                                 style="resize: none"
                                 name="routeboard_reply_content" class="col form-control mt-4"
                                 placeholder="댓글 내용 입력" onfocus="this.placeholder=''"
                                 onblur="this.placeholder='댓글 내용 입력'"
                                 onclick="checkLogin(${!empty sessionUser}, ${routeboardData.routeboardVo.routeboard_no})"></textarea>
                           </div>
                           <div class="col mt-2" style="text-align: right">
                              <a class="custom-link" href="javascript:void(0);"
                                 onclick="writeReply(${!empty sessionUser}); return false"><strong>댓글
                                    작성</strong></a>
                           </div>
                        </div>
                     </div>
               </div>
            <i class="fas fa-arrow-circle-up fa-2x"
               onclick="$('html, body').stop().animate( { scrollTop : '0' } );"
               onmouseover="$(this).css('color','#009966');"
               onmouseout="$(this).css('color','grey');"></i>
         </div>
      </div>

   <script
      src="${pageContext.request.contextPath}/resources/js/routeboard/rt_read_content_page.js"></script>
</div>
</body>