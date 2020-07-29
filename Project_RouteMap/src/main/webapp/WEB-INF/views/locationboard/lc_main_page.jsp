    <%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
   href="${pageContext.request.contextPath}/resources/css/locationboard/lc_main_page.css">
<title>여행지/맛집 메인페이지</title>
</head>
<body>
   <!-- wrap 시작-->
   <div class="wrap" style="background-color:#f7f7f7; min-height:700px">
      <!-- global nav 적용 -->
      <div class="container-lg pt-5 shadow-sm" style="background-color:#ffffff; height:auto;">
         <!-- container 시작-->
         <form id="frm" action="./lc_main_page.do" method="get">
            <input type="hidden" id="session_check"   value="${!empty sessionUser }">
            <div class="row border-bottom pb-3">
                     <div class="col ml-2"><h2><i class="fas fa-car-side" style="color:#58AF9E"></i> 여행지/맛집</h2></div>
                 </div>
               <div class="row">
                  <aside class="float_sidebar col-2 list-group list-group-flush mt-3" id="side_menu">
                     <ul class="checkbox-container row">
                        <li class="aside_wrap mt-2"><input id="checkAllProvince" name="province_category_no" type="checkbox" value="0"><label class="form-check-label clickCheckbox checkbox" for="checkAllProvince">&nbsp;전체</label></li>
                        <li class="aside_wrap mt-2"><input id="province1" name="province_category_no" class="province_class" type="checkbox" value="1"><label class="form-check-label clickCheckbox" for="province1">&nbsp;서울특별시</label></li>
                        <li class="aside_wrap mt-2"><input id="province2" name="province_category_no" class="province_class" type="checkbox" value="2"><label class="form-check-label clickCheckbox" for="province2">&nbsp;부산광역시</label></li>
                        <li class="aside_wrap mt-2"><input id="province3" name="province_category_no" class="province_class" type="checkbox" value="3"><label class="form-check-label clickCheckbox" for="province3">&nbsp;인천광역시</label></li>
                        <li class="aside_wrap mt-2"><input id="province4" name="province_category_no" class="province_class" type="checkbox" value="4"><label class="form-check-label clickCheckbox" for="province4">&nbsp;대구광역시</label></li>
                        <li class="aside_wrap mt-2"><input id="province5" name="province_category_no" class="province_class" type="checkbox" value="5"><label class="form-check-label clickCheckbox" for="province5">&nbsp;대전광역시</label></li>
                        <li class="aside_wrap mt-2"><input id="province6" name="province_category_no" class="province_class" type="checkbox" value="6"><label class="form-check-label clickCheckbox" for="province6">&nbsp;광주광역시</label></li>
                        <li class="aside_wrap mt-2"><input id="province7" name="province_category_no" class="province_class" type="checkbox" value="7"><label class="form-check-label clickCheckbox" for="province7">&nbsp;울산광역시</label></li>
                        <li class="aside_wrap mt-2"><input id="province8" name="province_category_no" class="province_class" type="checkbox" value="8"><label class="form-check-label clickCheckbox" for="province8">&nbsp;경기도</label></li>
                        <li class="aside_wrap mt-2"><input id="province9" name="province_category_no" class="province_class" type="checkbox" value="9"><label class="form-check-label clickCheckbox" for="province9">&nbsp;강원도</label></li>
                        <li class="aside_wrap mt-2"><input id="province10" name="province_category_no" class="province_class" type="checkbox" value="10"><label class="form-check-label clickCheckbox" for="province10">&nbsp;충청도</label></li>
                        <li class="aside_wrap mt-2"><input id="province11" name="province_category_no" class="province_class" type="checkbox" value="11"><label class="form-check-label clickCheckbox" for="province11">&nbsp;전라도</label></li>
                        <li class="aside_wrap mt-2"><input id="province12" name="province_category_no" class="province_class" type="checkbox" value="12"><label class="form-check-label clickCheckbox" for="province12">&nbsp;경상도</label></li>
                        <li class="aside_wrap mt-2"><input id="province13" name="province_category_no" class="province_class" type="checkbox" value="13"><label class="form-check-label clickCheckbox" for="province13">&nbsp;제주특별자치도</label></li>
                        <li class="aside_wrap mt-2"><input id="province14" name="province_category_no" class="province_class" type="checkbox" value="14"><label class="form-check-label clickCheckbox" for="province14">&nbsp;세종특별자치시</label></li>
                     </ul>
                  </aside>
               <section class="col border-left">
                  <ul class="row mt-5">
                  <!-- 검색창 시작 -->
                     <li class="col" style="padding-right:56px">
                        <div class="input-group mb-3">
                           <div class="input-group-prepend">
                              <select id="category" name="select_search_no"
                                 class="custom-select input-group-text"> <!--  원래 아이디: id="basic-addon1" -->
                                 <option value="0"
                                    <c:if test="${param.select_search_no==0 || param.category_no==0}">selected</c:if>>전체</option>
                                 <option value="1"
                                    <c:if test="${param.select_search_no==1 || param.category_no==1}">selected</c:if>>여행지</option>
                                 <option value="2"
                                    <c:if test="${param.select_search_no==2 || param.category_no==2}">selected</c:if>>맛집</option>
                              </select>
                           </div>
                           <input id="search_word" type="text" name="search_word"
                              <c:if test="${!empty param.search_word}">value="${param.search_word }"</c:if>
                              class="form-control" placeholder="검색어 입력"
                              onfocus="this.placeholder=''"
                              onblur="this.placeholder='검색어 입력'" autocomplete="off"
                              aria-label="Username" aria-describedby="basic-addon1"
                              onkeyup="searchList()"   
                              onkeypress="if(event.keyCode==13) {searchList(); return false;}">
                           <div class="input-group-append">
                              <input type="button"
                                 class="btn btn-outline-success" id="basic-addon2"
                                 value="검색" onclick="searchList()">
                           </div>
                        </div>
                        <div class="row input-group" style="margin:0px; display:grid; grid-template-columns: 1fr auto">
                           <div class="dropdown input-group-prepend" style="padding-right:0px;padding-left: 0px;">
                              <button class="btn btn-outline-success btn-sm btn-block dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown" 
                                    aria-haspopup="true" aria-expanded="true" style="align-items:center">
                                    상세검색
                              </button>
                              <div class="dropdown-menu dropdown-menu-form" style="width:100%;">
                                 <ul class="checkbox-container" id="detailSearch">
                                    <li>
                                       <span class="h3_font_size search">비용</span>
                                       <input id="checkAllCost" name="select_cost" type="checkbox" value="0" class="cost_class"><label class="form-check-label clickCheckbox" for="checkAllCost">&nbsp;전체</label>
                                         <input name="select_cost" id="cost1" class="cost_class" type="checkbox" value="1"><label class="form-check-label clickCheckbox" for="cost1">&nbsp;1만원 이하</label>
                                       <input name="select_cost" id="cost2" class="cost_class" type="checkbox" value="2"><label class="form-check-label clickCheckbox" for="cost2">&nbsp;1만원~3만원</label>
                                       <input name="select_cost" id="cost3" class="cost_class" type="checkbox" value="3"><label class="form-check-label clickCheckbox" for="cost3">&nbsp;3만원~5만원</label>
                                       <input name="select_cost" id="cost4" class="cost_class" type="checkbox" value="4"><label class="form-check-label clickCheckbox" for="cost4">&nbsp;5만원 이상</label>
                                    </li>
                                    <li>
                                       <span class="h3_font_size search">유형</span>
                                       <input id="checkAllType" name="select_type" type="checkbox" value="0"><label class="form-check-label clickCheckbox" for="checkAllType">&nbsp;전체</label>
                                         <input name="select_type" id="type1" class="type_class" type="checkbox" value="1"><label class="form-check-label clickCheckbox" for="type1">&nbsp;혼자</label>
                                       <input name="select_type" id="type2" class="type_class" type="checkbox" value="2"><label class="form-check-label clickCheckbox" for="type2">&nbsp;커플</label>
                                       <input name="select_type" id="type3" class="type_class" type="checkbox" value="3"><label class="form-check-label clickCheckbox" for="type3">&nbsp;친구와</label>
                                       <input name="select_type" id="type4" class="type_class" type="checkbox" value="4"><label class="form-check-label clickCheckbox" for="type4">&nbsp;가족과</label>
                                    </li>
                                    <li>
                                       <span class="h3_font_size search">시간</span>
                                       <input id="checkAllTime" name="select_time" type="checkbox" value="0"><label class="form-check-label clickCheckbox" for="checkAllTime">&nbsp;전체</label>
                                         <input name="select_time" id="time1" class="time_class" type="checkbox" value="1"><label class="form-check-label clickCheckbox" for="time1">&nbsp;1시간 이내</label>
                                         <input name="select_time" id="time2" class="time_class" type="checkbox" value="2"><label class="form-check-label clickCheckbox" for="time2">&nbsp;1시간~3시간</label>
                                       <input name="select_time" id="time3" class="time_class" type="checkbox" value="3"><label class="form-check-label clickCheckbox" for="time3">&nbsp;3시간~5시간</label>
                                       <input name="select_time" id="time4" class="time_class" type="checkbox" value="4"><label class="form-check-label clickCheckbox" for="time4">&nbsp;5시간 이상</label>
                                    </li>
                                    <li>
                                       <span class="h3_font_size search_style">스타일</span>
                                       <input id="checkAllMood" name="select_mood" type="checkbox" value="0"><label class="form-check-label clickCheckbox" for="checkAllMood">&nbsp;전체</label>
                                         <input name="select_mood" id="mood1" class="mood_class" type="checkbox" value="1"><label class="form-check-label clickCheckbox" for="mood1">&nbsp;조용한 곳</label>
                                         <input name="select_mood" id="mood2" class="mood_class" type="checkbox" value="2"><label class="form-check-label clickCheckbox" for="mood2">&nbsp;시끄러운 곳</label>
                                         <input name="select_mood" id="mood3" class="mood_class" type="checkbox" value="3"><label class="form-check-label clickCheckbox" for="mood3">&nbsp;활동적인 곳</label>
                                         <input name="select_mood" id="mood4" class="mood_class" type="checkbox" value="4"><label class="form-check-label clickCheckbox" for="mood4">&nbsp;분위기 좋은 곳</label>
                                    </li>
                                 </ul>
                                 <div id="detailSearch_button" style="text-align:center; padding:1rem 0rem 1rem 0rem;">
                                       <button type="button" class="btn btn-success btn-sm" id="confirmButton">확인</button>
                                    </div>
                              </div>
                           </div>
                           <div class="input-group-append dropdown" style="padding:0px">
                              <div class="input-group-prepend">
                                 <select id="list_align" class="custom-select">
                                    <option value="1" selected>최신순</option>
                                    <option value="2">조회순</option>
                                    <option value="3">좋아요순</option>
                                 </select>
                              </div>
                           </div>
                        </div>
                        </li>
                     <!-- 검색창 종료 -->
                  </ul>
                  <!-- 글 리스트 시작 -->
                  <ul class="row">
                     <li class="col-12">
                        <h6 id="countList"></h6></li>
                  </ul>
                  <div class="row ml-1">
                     <div class="col">
                        <!-- 전체 글 리스트 시작 -->
                        <div id="boardList" class="container list-container" style="padding:0px; margin-left:20.5px; min-height:600px">
                        </div>
                     </div>
                     <!-- 전체 글 리스트 종료 -->
                  </div>
                  <!-- 버튼 - 글쓰기 -->
                  <div class="row mt-1 mr-3" style="text-align: right">
                     <div class="col">
                        <!--  페이지 내비게이션 시작 -->
                        <nav aria-label="Page navigation example" style="display:grid; grid-template-columns:6fr auto;">
                           <div class="col">
                              <ul id="page_navi"
                                 class="pagination mt-3" style="justify-content:center;padding-left:120px">
                              </ul>
                           </div>
                           <div class="col" style="align-self:center;">
                           <a class="btn btn-success" href="javascript:void(0)"
                           onclick="checkLogin(${!empty sessionUser}); return false" style="align-self:center;">글쓰기</a>
                           </div>
                        </nav>
                        <!--  페이지 내비게이션 끝 -->
                        
                     </div>
                  </div>
                  <!-- 글 리스트 종료-->
                  <div class="row mt-5">
                     <i class="fas fa-arrow-circle-up fa-2x"
                  onclick="$('html, body').stop().animate( { scrollTop : '0' } );" onmouseover="$(this).css('color','#009966');"
                  onmouseout="$(this).css('color','grey');"></i>
                  </div>
               </section>
            </div>
         </form>
      </div>
      <!-- container 종료-->
      <!-- footer 적용 해야함 -->
   </div>
   <!-- wrap 끝 -->
   <script
      src="${pageContext.request.contextPath}/resources/js/locationboard/lc_main_page.js"></script>   
</body>