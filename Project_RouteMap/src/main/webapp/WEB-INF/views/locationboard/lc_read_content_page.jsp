<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- jstl - 작성일 처리-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
   href="${pageContext.request.contextPath}/resources/css/locationboard/lc_read_content_page.css">
<!-- 카카오 지도 -->
<script type="text/javascript"
   src="//dapi.kakao.com/v2/maps/sdk.js?appkey=25f30bf0195e51648727ff628cea7c4c"></script>
<title>여행지/맛집 글 읽기</title>
</head>
<body>
<div class="wrap pb-5 bg-light" style="min-height:700px">
   <!-- 컨테이너 시작-->
   <div class="container py-5">
      <div class="row text-left">
         <div class="col" style="padding-bottom:20px; font-size:20px; font-weight:bold">여행지/맛집 글읽기</div>
      </div>
      <div class="row">
         <!-- 중간 -->
         <div class="col">
            <!-- 페이지 네비게이션 -->
            <div class="row py-3"
               style="text-align: left; border-bottom: solid 2px #cccccc;">
               <div class="col">메인 페이지 >
                  ${data.locationCategoryVo.location_category_name } >
                  ${data.provinceCategoryVo.province_category_name }</div>
            </div>
            <!-- 장소 -->
            <div class="row">
               <div class="col card bg-light mt-3">
                  <!-- 카드 헤더 -->
                  <div class="row card-header">
                     <div class="col">
                        <div class="row mt-2">
                             <!-- 장소 게시글 번호 -->
                        <input type="hidden" id="locationboard_no" name="locationboard_no" value="${data.locationboardVo.locationboard_no }">
                           <div class="col-1" style="color:grey">제목</div>
                     <div class="col-7" style="padding:0px;">
                        <!-- 장소 제목 -->
                        <h5 style="width:95%;">${data.locationboardVo.locationboard_title }</h5>
                     </div>
                     <div class="col-3" style="padding:0px; display:block">
                        <div style="display:flex;">
                                 <!-- 장소 - 작성일 -->
                           <div style="margin-right:1rem; color:grey;">작성일</div>
                           <div
                              style="font-size: 13px; padding: 0px; margin-top:0.2rem; color: gray">
                              <fmt:formatDate
                              value="${data.locationboardVo.locationboard_writedate}"
                              pattern="yyyy.MM.dd HH:mm" />
                           </div>
                        </div>
                               <div class="mt-3" style="display:flex;">
                               <!-- 장소 - 조회수 -->
                                   <div style="margin-right:1rem; color:grey">조회수</div>
                           <div style="font-size : 13px; margin-top:0.2rem; color:gray;">${data.locationboardVo.locationboard_readcount }</div>
                               </div>
                            </div>
                         </div>
                        <div class="row mt-2">
                           <!-- 장소 - 작성자 -->
                           <div class="col-1" style="color:grey">작성자</div>
                           <div class="col-2" style="display:flex; align-items:center; color: grey; padding: 0px;">
                              <!-- 레벨 -->
                              <div class="badge badge-pill badge-danger" style="margin-right : 0.4rem;">
                                 Lv.
                                 <c:choose>
                                    <c:when
                                       test="${data.levelCategoryVo.level_category_no < 10}">0${data.levelCategoryVo.level_category_no}</c:when>
                                    <c:otherwise>${data.levelCategoryVo.level_category_no}</c:otherwise>
                                 </c:choose>
                              </div>
                              <!-- 장소 - 작성자 닉네임 -->
                        <div class="dropdown">
                           <div id="dropdownMenuButton" class="col" style="padding:0px;" <c:if test="${!empty sessionUser && sessionUser.member_no != data.memberVo.member_no }">data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"</c:if>>
                                 ${data.memberVo.member_nickname }
                           </div>
                           <c:if test="${!empty sessionUser && sessionUser.member_no != data.memberVo.member_no }">
                              <div id="content" class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                  <a href="javascript:void(0);" id="writeNote" onclick='sendUser("${data.memberVo.member_id}"); return false' class="dropdown-item">쪽지 보내기</a>
                                 <a href='javascript:void(0);' id="reportUser" onclick='reportUser(${data.memberVo.member_no }, ${data.locationboardVo.locationboard_no }); return false' class="dropdown-item">신고하기</a>
                              </div>
                           </c:if>
                        </div>
                           </div>
                           <div class="col-5"></div>
                           <span style="color:gray; margin-right:1rem; margin-top:0.2rem;">좋아요</span>
                           <span style="margin-right:2rem; margin-top:0.2rem;">
                              <!--장소 - 좋아요 표시/세션체크 -->
                              <input type="hidden" id="session_check"
                                 value="${!empty sessionUser }"> <i
                                 id="${data.locationboardVo.locationboard_no}"
                                 class="far fa-heart fa-lg" style="color: #f06595;cursor: pointer; font-size:1rem;"
                                 onclick="like_func(this.id)"></i><span
                                 id="like_${data.locationboardVo.locationboard_no}"
                                 style="color:gray; font-size:12px;">${like_count }</span>
                           </span>
                           <span style="color:gray; margin-right:1rem; margin-top:0.2rem;">관심</span>
                           <span style="margin-top:0.2rem;">
                              <!-- 장소 - 관심등록 -->
                              <i id="favorite_${data.locationboardVo.locationboard_no}"
                                 style="color: green; cursor: pointer; font-size:1rem; margin-right:2rem;"
                                 onclick="favorite_check(this.id)"
                                 <c:choose>
                                    <c:when test="${!empty farVo}">class="fas fa-flag myfavorite fa-lg"</c:when>
                                    <c:when test="${empty farVo}">class="far fa-flag myfavorite fa-lg"</c:when>
                                 </c:choose>> 
                              </i>
                           </span>
                           <!-- 게시물 신고 -->
                           <c:if test="${!empty sessionUser && sessionUser.member_no != data.memberVo.member_no}">
                              <span style="color:gray; margin-right:1rem; margin-top:0.2rem;">신고</span>
                              <span style="margin-top:0.2rem;">
                                 <i id="report_location" class="fas fa-bullhorn fa-lg" 
                                       style="color: red; cursor: pointer; font-size:1rem; margin-right:2rem;" onclick="reportLocation(${data.locationboardVo.locationboard_no})"></i>
                              </span>
                           </c:if>
                        </div>
                        <div class="row">
                           
                           <div class="col-5"></div>
                           
                        </div>
                     </div>
                  </div>
                  <!-- 카드 바디 -->
                  <div class="row card-body">
                     <div class="col">
                         <!--<div class="row">
                           장소 카테고리 : 여행지, 맛집 구분
                           <div class="col-1">분류</div>
                           <div class="col">${data.locationCategoryVo.location_category_name }</div>
                        </div>-->
                         <div class="row mt-5" id="small_category">
                           <!-- 시/도 구분  -->
                           <div><i style="margin-left:0rem;" class="fas fa-map-marked-alt"></i></div>
                           <div class="small_category_content">${data.provinceCategoryVo.province_category_name }</div>
                           <!-- 비용 -->
                          <div><i class="fas fa-won-sign"></i></div>
                           <div class="small_category_content"><fmt:formatNumber value="${data.locationboardVo.locationboard_cost }" pattern="#,###원"/></div>
                           <!-- 소요시간 -->
                           <!-- 분을 시간과 분으로 바꾸기 -->
                           <div><i class="far fa-clock"></i></div>
                           <div class="small_category_content">
                              <fmt:parseNumber var="hour"
                                 value="${data.locationboardVo.locationboard_time/60 }"
                                 integerOnly="true" />
                              <c:if test="${hour != 0}">${hour } 시간</c:if>
                              <c:if
                                 test="${data.locationboardVo.locationboard_time%60 != 0}">
                                        ${data.locationboardVo.locationboard_time%60}분</c:if>
                           </div>
                           <!-- 유형 -->
                          <div><i class="fas fa-users"></i></div>
                           <div class="small_category_content">${data.typeCategoryVo.type_category_name }</div>
                           <!-- 분위기 -->
                           <div><i class="fas fa-music"></i></div>
                           <div class="small_category_content">${data.moodCategoryVo.mood_category_name }</div>
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
                        <div class="row mt-3 mx-0" style="border-radius:20px;">
                           <div class="col">
                              <div class="row card-body" style="background-color:#f6f6f6; border-radius:20px">
                                 <div class="col" style="border-radius:20px; min-height:200px">
                                    ${data.locationboardVo.locationboard_content }</div>
                              </div>
                           </div>
                        </div>
                        
                        <!-- 사진 슬라이드 시작 -->
                        <div class="row mt-3" style="text-align: center;">
                           <div class="col">
                              <div id="carouselExampleInterval" class="carousel slide"
                                 data-ride="carousel">
                                 <div class="carousel-inner"
                                    style="text-align: center; width: 100%; height: 100%;">
                                    <c:forEach items="${data.locationboardUploadFileVoList }"
                                       var="locationboardUploadFileVo" varStatus="status">
                                       <c:choose>
                                          <c:when test="${status.index eq 0}">
                                             <div class="carousel-item active" data-interval="3000">
                                                <img
                                                   src="../../routemap/storage/${locationboardUploadFileVo.locationboard_file_link_path}"
                                                   class="d-block" alt="이미지를 불러올 수 없습니다."
                                                   style="width: 100%; height: 720px;">
                                             </div>
                                          </c:when>
                                          <c:otherwise>
                                             <div class="carousel-item" data-interval="3000">
                                                <img
                                                   src="../../routemap/storage/${locationboardUploadFileVo.locationboard_file_link_path}"
                                                   class="d-block" alt="이미지를 불러올 수 없습니다."
                                                   style="width: 100%; height: 720px;">
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
                        <!-- 지도 -->
                        <div class="row mt-3">
                           <div class="col">
                              <div id="map" style="width: 100%; height: 480px;"></div>
                              <input id="str" type="hidden"
                                 value="${data.locationboardVo.locationboard_coordinate}">
                              <script>
                                        var string = $("#str").val();
                                        var strArray = string.split('-');

                                        var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
                                            mapOption = {
                                                center: new kakao.maps.LatLng(strArray[0], strArray[1]), // 지도의 중심좌표
                                                level: 3 // 지도의 확대 레벨
                                            };

                                        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

                                        //마커가 표시될 위치입니다 
                                        var markerPosition = new kakao.maps.LatLng(strArray[0], strArray[1]);

                                        //마커를 생성합니다
                                        var marker = new kakao.maps.Marker({
                                            position: markerPosition
                                        });
                                        //마커가 지도 위에 표시되도록 설정합니다
                                        marker.setMap(map);
                                //아래 코드는 지도 위의 마커를 제거하는 코드입니다
                                //marker.setMap(null);
                                    </script>
                           </div>
                        </div>
                     </div>
                  </div>


               </div>

            </div>

            <!-- 버튼 -->
            <div class="row mt-1" style="text-align: right">
               <div class="col my-3 pr-0">
                  <c:if test="${!empty sessionUser }">
                     <!-- 글쓰기 -->
                     <a
                        href="${pageContext.request.contextPath }/locationboard/lc_write_content_page.do">
                        <input class="btn btn-success" type="button"
                        value="글쓰기">
                     </a>
                  </c:if>
                  <c:if
                     test="${sessionUser.member_no == data.memberVo.member_no || !empty sessionAdmin}">
                     <!-- 수정-->
                     <a
                        href="${pageContext.request.contextPath }/locationboard/lc_update_content_page.do?locationboard_no=${data.locationboardVo.locationboard_no}">
                        <input class="btn btn-success" type="button" value="수정">
                     </a>
                     <!-- 삭제-->
                     <input class="btn btn-outline-success" type="button" value="삭제"
                        onclick="buttonDeletePost(${data.locationboardVo.locationboard_no})">
                  </c:if>
                  <!-- 목록 -->
                  <a
                     href="${pageContext.request.contextPath }/locationboard/lc_main_page.do">
                     <input class="btn btn-outline-success" type="button" value="목록">
                  </a>
               </div>
            </div>
            <!-- 댓글 박스 리스트 시작 -->
            <div class="row mt-2" style="border-top: solid 2px #cccccc">
               <div class="col">
                  
                  <div class="row mt-4">
                     <!-- ajax - 댓글 생성 시작 -->
                     <div id="reply_box" class="col">
                        <!-- ajax - 댓글 박스 -->
                     </div>
                  </div>
               </div>
            </div>
            <!-- 댓글 박스 리스트 끝 -->
            <!-- 댓글 작성 박스 시작-->
            <div class="row">
               <div class="col">
                     <!-- 작성자 정보 -->
                     <div class="col" style="padding: 0px">
                        <!-- 댓글 쓰기 버튼 -->
                        <div class="col">
                           <strong><i class="far fa-comment-dots mr-1"></i>댓글 달기</strong>
                        </div>
                        <!-- 댓글 내용 입력 -->
                        <div class="col">
                           <textarea id="locationboard_reply_content"
                              style="resize: none"
                              name="locationboard_reply_content"
                              class="col form-control mt-4"
                              onclick="checkLoginForForm(${data.locationboardVo.locationboard_no})"
                              placeholder="댓글 내용 입력" onfocus="this.placeholder=''"
                              onblur="this.placeholder='댓글 내용 입력'"></textarea>
                        </div>
                        <div class="col mt-2" style="text-align: right">
                           <a class="custom-link" href="javascript:void(0);"
                              onclick="buttonWriteReply(${data.locationboardVo.locationboard_no}); return false"><strong>댓글
                                 작성</strong></a>
                        </div>
                     </div>
               </div>
            </div>
            <!-- 댓글 작성 박스 끝-->
            <i class="fas fa-arrow-circle-up fa-2x"
                  onclick="$('html, body').stop().animate( { scrollTop : '0' } );" onmouseover="$(this).css('color','#58AF9E');"
                  onmouseout="$(this).css('color','grey');"></i>
         </div>
      </div>
   </div>
   <script
      src="${pageContext.request.contextPath}/resources/js/locationboard/lc_read_content_page.js"></script>
</div>
</body>