<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link rel="stylesheet"
   href="${pageContext.request.contextPath}/resources/css/note/nt_main_page.css">
<link rel="stylesheet"
   href="${pageContext.request.contextPath}/resources/css/note/nt_read_page.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>받은 쪽지 읽는 페이지</title>
<script>
$(function() {
   if($("#session_check").val() == "") {
      location.href="/error/no_authority_page.do";
      return;
   }
});
</script>
</head>
<body>
<!-- wrap 시작-->
<div class="wrap" style="background-color:#f7f7f7;">
   <!-- container 시작-->
     <div class="container-lg pt-5 shadow-sm" style="background-color:#ffffff; height:auto;">
     <div class="row border-bottom pb-3">
       <div class="col ml-2"><h2><i class="fas fa-comments" style="color:#58AF9E"></i> 쪽지</h2></div>
     </div>
     <div class="row">
       <aside class="mt-3 col-2 list-group list-group-flush">
       <input id="session_check" type="hidden" value="${sessionUser }">
         <ul class="row">
           <li class="aside_wrap mt-2"><a class="custom-link" href="./nt_main_page.do">쪽지 쓰기</a></li>
           <li class="aside_wrap mt-2"><a class="custom-link" href="./nt_recv_page.do">받은 쪽지함</a></li>
           <li class="aside_wrap mt-2"><a class="custom-link" href="./nt_send_page.do">보낸 쪽지함</a></li>
         </ul>
       </aside>
       <section class="col border-left">
         <ul class="row mt-5">
           <li class="col"><h3>받은 쪽지함</h3></li>
         </ul>
         <!-- 받는 쪽지 읽기 시작 -->
          <div class="row">
              <div class="col-md-8">
               <div class="post-content shadow-sm">
               <div class="post-container">
                  <div class="post-detail">
                    <div class="user-info">
                      <h5>보낸 사람&nbsp;<a class="custom-link" href="timeline.html" class="profile-link">${recvReadNote.noteVo.send_member_id}</a></h5>
                      <p class="text-muted"><fmt:formatDate value="${recvReadNote.noteVo.note_send_date}" pattern="yyyy.MM.dd HH:mm:ss" /></p>
                    </div>
                    <div class="line-divider"></div>
                    <div class="post-text">
                      <h6>${recvReadNote.noteVo.note_title}</h6>
                      <p>${recvReadNote.noteVo.note_content} <i class="em em-anguished"></i> <i class="em em-anguished"></i> <i class="em em-anguished"></i></p>
                    </div>
                    <div class="line-divider"></div>
                  </div>
               </div>
               </div>
              </div>
          </div>
         <!-- 받는 쪽지 읽기 종료 -->
       </section>
       <!-- section 종료 -->
     </div>
   </div>
   <!-- container 종료-->
</div>
<!-- wrap 끝 -->
</body>
</html>