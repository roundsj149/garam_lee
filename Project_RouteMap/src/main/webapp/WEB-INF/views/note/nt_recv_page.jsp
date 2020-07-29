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
<title>받는 쪽지함</title>
<script>
window.onload = function () {
   refreshNoteList();
   recvTotalCount();
}

// 쪽지 체크
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

//쪽지 선택 삭제 배열 처리
function deleteRecvNoteNo(){
   //배열 받아옴
   var noteNo = document.getElementsByName("note_no");
   var noteNoArr = [];
   
   // 체크된 값 배열에 담기
   for(var i=0; i<noteNo.length; i++) {
      if(noteNo[i].checked) {
         noteNoArr.push(noteNo[i].value);
      }
   }
   alert(noteNoArr);
   deleteRecvNote(noteNoArr);
}

//기능 - 받은 쪽지함 삭제 
function deleteRecvNote(note_no){
   var xmlhttp = new XMLHttpRequest();
   //var noteRecvList = document.getElementById("note_recv_list");
   
   //서버로 요청 전송을 위한 코드
   xmlhttp.open("get","./nt_update_recv_note_delete_process.do?note_no="+note_no, true);
   xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
   xmlhttp.send("");
   
   //클라이언트 화면에 갱신시켜주는 용도
   //서버로부터의 응답을 처리하기 위한 함수 선언
   xmlhttp.onreadystatechange = function(){
      //서버로부터의 응답 결과가 성공일 경우 처리
      if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
         
         refreshNoteList();
         recvTotalCount();
      }
   }
}

// 받은 쪽지함 Ajax 불러오기, 삭제 처리
function refreshNoteList(){
   var xmlHttpRequest = new XMLHttpRequest();
   xmlHttpRequest.onreadystatechange = function(){
      if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200){
         //alert(xmlHttpRequest.responseText);
         // 자바스크립트로 동적 UI 꾸미기
         var resultData = JSON.parse(xmlHttpRequest.responseText);
         
         //document.getElementById("t_count").innerText = resultData.length;
                      
         var tbody = document.getElementById("note_recv_tbody");
         
         // 쪽지 내용 지우기
         var length = tbody.childNodes.length;
         for(var i = 0; i < length; i++){
            tbody.removeChild(tbody.childNodes[0]);
         }
         
         /*
         <c:forEach items="${recvList}" var="recvList">
         <tr>
           <th scope="row"><input type="checkbox" name="note_no" value="${recvList.note_no}"></th>
           <td>${recvList.send_member_id}</td>
           <!-- 수정해야함 -->
           <td><a href="${pageContext.request.contextPath}/note/nt_recv_read_page.do?note_no=${recvList.note_no}">${recvList.note_title}</a></td>
           <td><fmt:formatDate value="${recvList.note_send_date}" pattern="yyyy.MM.dd HH:mm:ss" /></td>
         </tr>
       </c:forEach>
         */
         
         for(data of resultData){
            var tr = document.createElement("tr");
            
            var th = document.createElement("th");
            th.setAttribute("scope" , "row");
            
            var input = document.createElement("input");
            input.setAttribute("type" , "checkbox");
            input.setAttribute("name" , "note_no");
            input.setAttribute("value" , String(data.note_no));
            th.appendChild(input);
            tr.appendChild(th);
               
            var td = document.createElement("td");
            td.innerText = data.send_member_id;
            tr.appendChild(td);

            var td = document.createElement("td");
            var a = document.createElement("a");
            a.setAttribute("href" , "${pageContext.request.contextPath}/note/nt_recv_read_page.do?note_no=" + data.note_no);
            a.setAttribute("class", "custom-link");
            a.innerText = data.note_title;
            td.appendChild(a);
            tr.appendChild(td);
            
            var td = document.createElement("td");
            
  
            var date = new Date(data.note_send_date);
            /*
            var strDate = "";
            strDate += date.getFullYear();
            strDate += "-"
            strDate += date.getMonth()+1;
            strDate += "-"
            strDate += date.getDate();
            
             //td.innerText = strDate;
            */
            var year = date.getFullYear();
            var month = date.getMonth()+1;
            month = month >= 10 ? month : '0' + month;
            var day = date.getDate();
            day = day >= 10 ? day : '0' + day;
            var hour = date.getHours();
            hour = hour >= 10 ? hour : '0' + hour;
            var min = date.getMinutes();
            min = min >= 10 ? min : '0'+ min;
            var sec = date.getSeconds(); 
            sec = sec >= 10 ? sec : '0' + sec; 
                       
            td.innerText = year+"."+month+"."+day+" "+hour+":"+min+":"+sec;
            
            tr.appendChild(td);
            
            tbody.appendChild(tr);
            
            /////////////////////////
         }
         
      }
   }
   xmlHttpRequest.open("get", "./nt_recv_page_rest.do", true);
   xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
   xmlHttpRequest.send();
   
}
// 쪽지 전체 카운트
function recvTotalCount(){
   var xmlhttp = new XMLHttpRequest();
   
   //서버로부터의 응답을 처리하기 위한 함수 선언
   xmlhttp.onreadystatechange = function(){
      //서버로부터의 응답 결과가 성공일 경우 처리
      if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
         var data = JSON.parse(xmlhttp.responseText);
                  
        document.getElementById("t_count").innerText = data.count;  
     }
    }
    //서버로 요청 전송을 위한 코드
    xmlhttp.open("get","./nt_recv_totalcount_rest.do", true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("");
      
}

/*
//쪽지 페이징 처리
function refreshNoteList(currPage){
      var xmlhttp = new XMLHttpRequest();
         
      //클라이언트 화면에 갱신시켜주는 용도
      //서버로부터의 응답을 처리하기 위한 함수 선언
      xmlhttp.onreadystatechange = function(){
         //서버로부터의 응답 결과가 성공일 경우 처리
         if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
            
            refreshNoteList();

         }

      }
      xmlHttpRequest.open("get", "./nt_recv_page_rest.do?currPage="+currPage, true);
      xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
      xmlHttpRequest.send();
}
*/

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
           <li class="col p-0"><h5 class="font-weight-bold">받은 쪽지함 <small id="t_count" class="text-muted">${totalCount}</small></h5></li>
         </ul>
         <!-- 받은 쪽지 리스트 시작 -->
         <!-- <form action="nt_update_recv_note_delete_process.do" method="get"> -->
         <div class="row ml-1 pr-3">
            <table class="table table-sm table-hover">
           <thead>
             <tr>
               <th scope="col"><input id="checkbox" type="checkbox" onclick="checkList()"></th>
               <th scope="col">보낸 사람</th>
               <th scope="col">제목</th>
               <th scope="col">날짜</th>
             </tr>
           </thead>
           <!-- ajax 받은 쪽지함 삭제처리  -->
           <tbody id="note_recv_tbody">
<%--           
              <c:forEach items="${recvList}" var="recvList">
                <tr>
                  <th scope="row"><input type="checkbox" name="note_no" value="${recvList.note_no}"></th>
                  <td>${recvList.send_member_id}</td>
                  <!-- 수정해야함 -->
                  <td><a href="${pageContext.request.contextPath}/note/nt_recv_read_page.do?note_no=${recvList.note_no}">${recvList.note_title}</a></td>
                  <td><fmt:formatDate value="${recvList.note_send_date}" pattern="yyyy.MM.dd HH:mm:ss" /></td>
                </tr>
              </c:forEach>
 --%>              
           </tbody>
         </table>
         </div>
      <div class="row ml-1">
        <!-- 삭제버튼 시작 -->
        <div class="col">
        <input onclick="deleteRecvNoteNo()" class="btn btn-outline-danger" type="submit" value="삭제">           
        </div>
        <!-- 삭제버튼 종료 -->
        
        <!--  Ajax 페이징 처리 시도중
        <div class="col d-flex justify-content-center">
          <div aria-label="Page navigation example">
             <ul class="pagination">
               <li class="page-item<c:if test="${beginPage-1 <= 0}"> disabled</c:if>"><a class="page-link" href="./nt_recv_page.do?currPage=${beginPage-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
               <c:forEach begin="${beginPage}" end="${endPage}" var="i">
                  <li class="page-item<c:if test="${currPage==i}"> active</c:if>"><a onclick="refreshNoteList(${i})" class="page-link" href="./nt_recv_page.do?currPage="></a></li>
               </c:forEach>
               <li class="page-item<c:if test="${endPage+1 > (totalCount-1)/10+1 }"> disabled</c:if>"><a class="page-link" href="./nt_recv_page.do?currPage=${endPage+1}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
             </ul>
           </div>
        </div>
        -->
        
        <!-- 페이징 시작 -->
        <div class="col d-flex justify-content-center">
          <div aria-label="Page navigation example">
              <ul class="pagination">
                <li class="page-item<c:if test="${beginPage-1 <= 0}"> disabled</c:if>"><a class="page-link" href="./nt_recv_page.do?currPage=${beginPage-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                <c:forEach begin="${beginPage}" end="${endPage}" var="i">
                   <li class="page-item<c:if test="${currPage==i}"> active</c:if>"><a class="page-link" href="./nt_recv_page.do?currPage=${i}">${i}</a></li>
                </c:forEach>
                <li class="page-item<c:if test="${endPage+1 > (totalCount-1)/10+1 }"> disabled</c:if>"><a class="page-link" href="./nt_recv_page.do?currPage=${endPage+1}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
              </ul>
            </div>
         </div>
        <!-- 페이징 종료 -->
                  
         <div class="col"></div>
        </div>
        <!-- </form> -->
        <!-- 받은 쪽지 리스트 종료 -->
       </section>
       <!-- section 종료 -->
     </div>
   </div>
   <!-- container 종료-->
</div>
<!-- wrap 끝 -->
</body>
</html>