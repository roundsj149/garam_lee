<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/note/nt_main_page.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>쪽지</title>
<script>
$(function() {
   if($("#session_check").val() == "") {
      location.href="/error/no_authority_page.do";
      return;
   }
});

// 쪽지 유효성 검사
function note_write_submit(){
   var nw = document.getElementById("note-write");
   var recvCheck = document.getElementById("inputText");
   var titleCheck = document.getElementById("title-check");
   var contentCheck = document.getElementById("content-check"); 

   
   //var mailJ = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    
   if(recvCheck.value == ""){
      alert("받는 사람 아이디를 입력해주세요");

      return;
   }/*
   else if((recvCheck.value).match(mailJ)){
      alert("이메일 형식으로 입력해주세요");
      return;
   }*/
   
   if(titleCheck.value == ""){
      alert("제목을 입력해주세요");
      return;
   }
   if(contentCheck.value == ""){
      alert("내용을 입력해주세요");
      return;
   }

   //if(recvCheck.value == mailJ){
  //    alert("test");
  // }
   alert("쪽지를 성공적으로 보냈습니다.");
   
   nw.submit();
}

/*//0 / 1000자 처리
function txt(obj, maxByte){
   var strValue = obj.value;
   var strLen = strValue.length;
   var total Byte = 0;
   var len = 0;
   var oneChar = "";
   var str2 = "";
   
   for(var i = 0; i < strLen; i++){
         oneChar = strValue.charAt(i);
         if(escape(oneChar).length > 4){
            totalByte += 2;
         }else{
            totalByte++;
         }
         if(totalByte <= maxByte){
            len = i+1;
         }
   }
   if(totalByte > maxByte){
      alert(maxByte + "자를 초과 입력 할 수 없습니다.");
      str2 = strValue.substr(0, len);
      obj.value = str2;
      txt(obj, 1000);
   }
}

function getByteLength(str){
   var len = 0;
   var max = str.length;
   
   for(var i = 0; i<max; i++){
      var ch = escape(str.charAt(i));
      
      if(ch.length == 1)len++;
      else if(ch.indexOf("%u") != -1) len += 2;
      else if(ch.indexOf("%") != -1) len += ch.length/3;
   }
   
   return len;
}

function setCutStr(str, limit_len) {
 var max = str.length;
 var bytes = 0;
 for (var i = 0; i < max; i++) {
  var ch = escape(str.charAt(i));
  
  if (ch.length == 1) bytes++;
  else if (ch.indexOf("%u") != -1) bytes += 2;
  else if (ch.indexOf("%") != -1) bytes += ch.length / 3;
  
  if (bytes > limit_len) {
   return str.substring(0, i);
  }
 }
 
 return str;
}

jQuery('#textarea').on('keyup', function() {
      var strlen = getByteLength(this.value);
      if (strlen > 500) {
       alert('500byte 까지 입력가능합니다.');
       var result = setCutStr(this.value, 500);
       jQuery('#textarea'').val(result);
       
       strlen = getByteLength(result);
       jQuery('#textLength').html(strlen);
      } else {
       var strlen = getByteLength(this.value);
       jQuery('#textLength').html(strlen);
      }
     });
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
         <!-- 글 리스트 시작 -->
         <div class="row mt-4 pl-3">
            <div class="col"><h5 class="font-weight-bold">쪽지 쓰기</h5></div>
         </div>
         <!-- 전체 글 리스트 시작 -->
         <form id="note-write" class="pr-3" action="./nt_send_process.do" method="get">
         <div class="row ml-1">
           <div class="col">
               <div class="form-group row">
                 <label for="staticEmail" class="col-sm-2 col-form-label">보내는 사람</label>
                 <div class="col-sm-10">
                    ${sessionUser.member_id}
                   <input name="send_member_id" type="hidden" value="${sessionUser.member_id}">
                 </div>
               </div>
               <div class="form-group row">
                 <div class="col">
                 <label for="inputText" class="col-form-label">받는 사람</label>
                 <input id="inputText" name="recv_member_id" type="email" class="form-control" value="${param.member_id}">
                 </div>
               </div>
               <div class="form-group row">
                 <div class="col">
                 <label for="inputTitle" class="col-form-label">제목</label>
                 <input id="title-check" name="note_title" type="text" class="form-control" id="inputTitle">
                 </div>
               </div>
               <div class="form-group row">
                 <div class="col">
                 <label for="formControlTextarea" class="col-form-label">내용 작성</label> <!--  onkeyup="txt(this)" -->
                 <textarea id="content-check" name="note_content" class="form-control" id="formControlTextarea" rows="5"></textarea>
                 </div>
               </div>
           </div>
         </div>
         <!-- 전체 글 리스트 종료 -->
         <!-- 글 리스트 종료-->
         <div class="row ml-1">
           <div class="col">
               <!-- <input name="note_type" type="hidden" value="1"> -->
               <input onclick="note_write_submit()" class="btn btn-success" type="button" value="보내기">
            </div>
            <div class="col-2 text-right">
               <span id="countLetter" class="bytes">0</span>&nbsp;/&nbsp;<span>1000자</span>
            </div>
         </div>
         </form>
       </section>
     </div>
   </div>
   <!-- container 종료-->
</div>
<!-- wrap 끝 -->
</body>    