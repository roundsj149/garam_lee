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
<style>
	#freeboard-nav {
		border-bottom: 4px solid #58AF9E;
	    padding-bottom: 8px;
	}
</style>
<script type="text/javascript">
	
	
	function renewCommentList(){
		
		var freeboardNo = ${addCont.freeboard.freeboard_no};
		
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4 && xhr.status == 200){
				
				var resultData = JSON.parse(xhr.responseText);
				
				var commentbox = document.getElementById("commentbox");
				
				var length = commentbox.childNodes.length;
				for(var i=0; i<length; i++){
					
					commentbox.removeChild(commentbox.childNodes[0]);
				    
					}
				
				
				
				for(var data of resultData){
					
					var row1 = document.createElement("div");
					row1.setAttribute("class","row");
					
					var row1_col1 = document.createElement("div");
					row1_col1.setAttribute("class", "col-8");
					
					var callname = document.createElement("div");
					callname.setAttribute("class", "alert alert-secondary");
					
					callname.innerText = data.memberVo.member_nickname;
					
					row1_col1.appendChild(callname);
					row1.appendChild(row1_col1);
					
					
					var row1_col2 = document.createElement("div");
					row1_col2.setAttribute("class", "col");
					
					var date = document.createElement("div");
					date.setAttribute("class", "alert alert-secondary");
				
					var milliseconds = data.commentVo.reply_writedate;
					var date = new Date(milliseconds);
					
					date.innerText = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
					
					row1_col2.appendChild(date);
					row1.appendChild(row1_col2);
					
					var row2 = document.createElement("div");
					row2_col1.setAttribute("class","row");
					
					var row2_col1 = document.createElement("div");
					replyContentBox.setAttribute("class", "alert alert-primary");
					replyContentBox.innerText = data.commentVo.reply_content;
					
					row2_col1.appendChild(row1);
					row2.appendChild(row2_col1);
					
					commnetbox.appendChild(row1);
					commnetbox.appendChild(row2);
				
			}
			};
			
			xhr.open("get", "./get_reply_list.do?freeboard_no=" + freeboardNo, true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.send();
			
		}
		
		function writeRepl(){
			var freeboardNo = ${addCont.freeboardVo.freeboard_no};
			var replyContent = document.getElementById("reply_content").value;
			var xhr = new XMLHttpRequest();
			
			xhr.onreadystatechange = function(){
				if(xhr.readyState==4 && xhr.status == 200){
					
					renewCommentList();
				}
			};
			
			xhr.open("post", "./write_comment_process.do", true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.send("freeboard_no=" + freeboardNo + "&reply_content=" + replyContent);
			
		}
</script>
</head>
<body onload="renewCommentList()">
	<div class="row mt-3">
		<div class="col-3"></div>
		<div class="col">
			제목 : ${addCont.freeboardVo.freeboard_title }<br> 
			글쓴이 : ${addCont.memberVo.member_nickname }<br> 
			조회수 :${addCont.freeboardVo.freeboard_readcount }<br> 
			내용 : <br>
			${addCont.freeboardVo.freeboard_content }<br> <br>
</div>
<div class="col-3"></div>
<div class="row"></div>
		
	</div>

	<div class="row">
		<!-- 댓글 리스트 -->
		<div class="col">
			<div class="row">
				<!-- 1번 댓글... -->
				<div class="col-8">
					<!-- 닉 -->
					<div class="alert alert-secondary"></div>
				</div>
				<div class="col">
					<!-- 날짜 -->
					<div class="alert alert-secondary"></div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<!-- 내용 -->
					<div class="alert alert-primary"></div>
				</div>
			</div>
		</div>
	</div>

	<div class="row mt-3">
		<div class="col"></div>
		<div class="col-4">

			<textarea id="comment" class="form-control"></textarea>
		</div>
		<div class="col">
			<input onclick="writeRpl()" type="button"
				class="btn btn-success btn-sm" value="답하기">
		</div>

	</div>





	<c:forEach items="${addCont.fbFileVoList }" var="freeboardfileVo">
		<img src="/upload/${freeboardUploadFileVo.file_link_path }">
		<br>
	</c:forEach>

	<div class="row mt-3"></div>
	<div class="row">
		<div class="col-8"></div>
		<c:if
			test="${!empty sessionUser && sessionUser.member_no == addCont.memberVo.member_no}">

			<a
				href="${pageContext.request.contextPath}/freeboard/fb_delete_content_process.do?freeboard_no=${addCont.freeboardVo.freeboard_no}">
				<input class="btn btn-light btn-sm" type="button" value="지우기">
			</a>


			<a
				href="${pageContext.request.contextPath}/freeboard/fb_update_content_page.do?freeboard_no=${addCont.freeboardVo.freeboard_no}">
				<input class="btn btn-secondary btn-sm" type="button" value="고치기">
			</a>
		</c:if>

	</div>
	<div class="row">
		<div class="col-5"></div>
		<a
			href="${pageContext.request.contextPath }/freeboard/fb_main_page.do">
			<input class="btn btn-primary btn-sm" type="button" value="돌아가기">
		</a>



	</div>
</body>