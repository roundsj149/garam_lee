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
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>보낸 쪽지함</title>
<script>
function checkList(){
   var check = document.getElementById("checkbox");
   var arr = document.getElementsByName("note_no");
   if(check.checked == true){
      for(var i = 0; i < arr.length; i++){
         arr[i].checked = true;   
      }
   }else if (check.checked == false) {
      for(var i = 0; i < arr.length; i++){
         arr[i].checked = false;   
      }
   }
}
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
           <li class="col p-0"><h5 class="font-weight-bold">보낸 쪽지함 <small class="text-muted">${totalCount}</small></h5></li>
         </ul>

         <!-- 받은 쪽지 리스트 시작 -->
         <form action="nt_update_send_note_delete_process.do" method="get">
         <div class="row ml-1 pr-3">
            <table class="table table-sm table-hover">
           <thead>
             <tr>
               <th scope="col"><input id="checkbox" type="checkbox" onclick="checkList()"></th>
               <th scope="col">받은 사람</th>
               <th scope="col">제목</th>
               <th scope="col">보낸 날짜</th>
               <th scope="col">받은 날짜</th>
               <th scope="col">발송 취소</th>
             </tr>
           </thead>
           <tbody>
           <c:forEach items="${sendList}" var="sendList">
             <tr>
               <th scope="row"><input type="checkbox" name="note_no" value="${sendList.note_no}"></th>
               <td>${sendList.recv_member_id}</td>
               <!-- 수정해야함 -->
               <td><a class="custom-link" href="${pageContext.request.contextPath}/note/nt_send_read_page.do?note_no=${sendList.note_no}">${sendList.note_title}</a></td>
               <td><fmt:formatDate value="${sendList.note_send_date}" pattern="yyyy.MM.dd HH:mm:ss" /></td>
               <td><fmt:formatDate value="${sendList.note_read_date}" pattern="yyyy.MM.dd HH:mm:ss" /></td>
               <td>
                <c:choose>
                    <c:when test="${empty sendList.note_read_date}">
                        <a class="custom-link" href="${pageContext.request.contextPath}/note/nt_send_delete_process.do?note_no=${sendList.note_no}">발송취소</a>
                    </c:when>
                </c:choose>
               </td>
             </tr>
           </c:forEach>
           </tbody>
         </table>
       </div>
       <div class="row ml-1">
           <!-- 삭제버튼 시작 -->
           <div class="col">
           <input class="btn btn-outline-danger" type="submit" value="삭제">           
           </div>
           <!-- 삭제버튼 종료 -->
           <!-- 페이징 시작 -->
           <div class="col d-flex justify-content-center">
             <nav aria-label="Page navigation example">
                 <ul class="pagination">
                   <li class="page-item<c:if test="${beginPage-1 <= 0}"> disabled</c:if>"><a class="page-link" href="./nt_send_page.do?currPage=${beginPage-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                   <c:forEach begin="${beginPage}" end="${endPage}" var="i">
                      <li class="page-item<c:if test="${currPage==i}"> active</c:if>"><a class="page-link" href="./nt_send_page.do?currPage=${i}">${i}</a></li>
                   </c:forEach>
                   <li class="page-item<c:if test="${endPage+1 > (totalCount-1)/10+1 }"> disabled</c:if>"><a class="page-link" href="./nt_send_page.do?currPage=${endPage+1}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
                 </ul>
               </nav>
            </div>
            <!-- 페이징 종료 -->
         <div class="col"></div>
        </div>
        </form>
         <!-- 받은 쪽지 리스트 종료 -->
       </section>
       <!-- section 종료 -->
     </div>
   </div>
   <!-- container 종료-->
</div>
<!-- wrap 끝 -->
</body>