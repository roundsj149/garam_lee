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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/myfar/fr_my_route_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/myfar/fr_banner.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내가 쓴 루트 글</title>
</head>
<body>
   <!-- wrap 시작-->
   <div class="wrap" style="background-color:#f7f7f7; min-height:700px">
      <div class="container-lg pt-5 shadow-sm" style="background-color:#ffffff; height:auto;">
     <!-- container 시작-->
     <div class="row  border-bottom pb-3">
         <div class="col ml-2"><h2><i class="far fa-calendar-check" style="color:#58AE9E ;"></i> 나의 관심/루트</h2></div>
     </div>
     <div class="row">
        <aside class="col-2 list-group list-group-flush mt-3">
         <input id="session_check" type="hidden" value="${sessionUser }">
           <ul class="side">
            <li class="aside_wrap"><a href="${pageContext.request.contextPath }/myfar/fr_my_favorite_page.do">나의 관심</a></li>
            <li class="aside_wrap"><a href="${pageContext.request.contextPath }/myfar/fr_my_location_page.do">나의 여행지/맛집</a></li>
            <li class="aside_wrap"><a href="${pageContext.request.contextPath }/myfar/fr_my_route_page.do">나의 루트</a></li>
           </ul>
           <div><hr></div>
           <div class="banner" style="margin-left:10px";>
              <a href="https://www.somebymi.com/"><img class="banner-img" alt="광고 배너" src="${pageContext.request.contextPath}/resources/img/ad.jpg"></a>
           </div>
        </aside>
            <section class="col border-left">
               <!-- 글 리스트 시작 -->
               <div class="row mt-4 ml-3">
               		<h5>나의 루트</h5>
               </div>
               <div class="row mt-3">
                  <div class="col">
                     <!-- 전체 글 리스트 시작 -->
                     <div id="boardList" class="container list-container" style="min-height:600px">
                     </div>
                     <!-- 전체 글 리스트 끝 -->
                     <!-- 모달 시작 -->
                     <div class="row">
                           <div class="col card-deck mt-3">
                              <!-- 모달 버튼 -->
                              <div class="row" style="text-align: center">
                                 <div class="col">
                                 </div>
                              </div>
                              <!-- 모달 창 시작 -->
                              <form
                                 action="${pageContext.request.contextPath }/routeboard/rt_write_content_page.do">
                                 <div class="modal fade" id="exampleModal" tabindex="-1"
                                    role="dialog" aria-labelledby="exampleModalLabel"
                                    aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                       <div class="modal-content">
                                          <div class="modal-header">
                                             <h5 class="modal-title" id="exampleModalLabel">새 루트
                                                만들기</h5>
                                             <button class="close" data-dismiss="modal"
                                                aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                             </button>
                                          </div>
                                          <div class="modal-body">
                                             <input type="text" name="routeboard_title"
                                                class="form-control" placeholder="루트 이름을 입력해주세요"
                                                onfocus="this.placeholder=''"
                                                onblur="this.placeholder='루트 이름을 입력해주세요'">
                                          </div>
                                          <div class="modal-footer">
                                             <input type="submit" class="btn btn-primary" value="확인">
                                             <button type="button" class="btn btn-secondary"
                                                data-dismiss="modal">취소</button>
                                          </div>
                                       </div>
                                    </div>
                                 </div>
                              </form>
                              <!-- 모달창 끝 -->
                           </div>
                        </div>
                        <!-- 모달 끝 -->
                  </div>
                  <!-- 전체 글 리스트 종료 -->
               </div>
               <!-- 글 리스트 종료-->
            <!--  페이지 내비게이션 시작 -->
            <div class="row pb-3">
               <div class="col">
                  <nav aria-label="Page navigation example">
                     <ul id="page_navi" class="pagination justify-content-center mt-3 pl-5 ml-5">
                     </ul>
                  </nav>
               </div>
               <div class="col-2 mt-3">
                  <button type="button" class="btn btn-success d-flex align-items-center" data-toggle="modal" data-target="#exampleModal">등록</button>
               </div>
            </div>
            <!--  페이지 내비게이션 끝-->
            </section>
         </div>
      </div>
      <!-- container 종료-->
   </div>
   <!-- wrap 끝 -->
   <script
      src="${pageContext.request.contextPath}/resources/js/myfar/fr_my_route_page.js"></script>
</body>