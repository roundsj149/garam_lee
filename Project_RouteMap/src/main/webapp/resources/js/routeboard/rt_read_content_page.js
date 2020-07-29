window.onload = function() {
	refreshReplyList();
	like_check();
	location_like_check();
	}



// 지도

// 댓글창 눌렀을 때 로그인 여부 확인
function checkLogin(session, clicked_id) {
	var sessionCheck = session;
	if(sessionCheck == false) {
		alert('로그인 후 이용해주세요');
		location.href = "/member/login_page.do?uri=routeboard/rt_read_content_page.do?routeboard_no="+clicked_id;
		return false;
	} else {
		return true;
	}
}

// 댓글 불러오기
function refreshReplyList(replyNo) {
    		
	var routeboardNo = document.getElementById("routeboard_no").value;
	var xmlhttp = new XMLHttpRequest();
    
    	xmlhttp.onreadystatechange = function() {
    		
    		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    			
    			var resultData = JSON.parse(xmlhttp.responseText);
				var replyBox = document.getElementById("reply_box");
				
		        // 댓글 박스 안에 내용 지우기
	            var length = replyBox.childNodes.length;
	            for(var i = 0; i < length; i++){
	               replyBox.removeChild(replyBox.childNodes[0]);
	            }
				
				for(var data of resultData){
					
					var nicknameRow = document.createElement("div");
					nicknameRow.setAttribute("class","row");
										
					var nicknameValue = document.createElement("div");
					nicknameValue.setAttribute("class","col-10");
					nicknameValue.setAttribute("style","font-weight: bold; padding-left:45px");
					nicknameValue.innerText = data.memberVo.member_nickname;
					
					var writedateCol = document.createElement("div");
 					writedateCol.setAttribute("class","col text-center");
					writedateCol.setAttribute("style","font-size:12px");
					
					var milliseconds = data.routeboardReplyVo.routeboard_reply_writedate;
	                var date = new Date(milliseconds);
	                var year = date.getFullYear();                // yyyy
	                year = year.toString();
	                year = year.substring(2, 4);            // yy 두자리로 저장
	                var month = (1 + date.getMonth());          // M
	                month = month >= 10 ? month : '0' + month;  // MM 두자리로 저장
	                var day = date.getDate();                   // d
	                day = day >= 10 ? day : '0' + day;          // dd 두자리로 저장
	                var hour = date.getHours();            // h
	                hour = hour >= 10 ? hour : '0' + hour;      // hh 두자리로 저장
	                var min = date.getMinutes();           // m
	                min = min >= 10 ? min : '0' + min;           // mm 두자리로
																	// 저장
	                var sec = date.getSeconds();           // s
	                sec = sec >= 10 ? sec : '0' + sec;           // ss 두자리로
																	// 저장
	                // yy.MM.dd hh:mm:ss
	                writedateCol.innerText = year+"."+month+"."+day+" "+hour+":"+min;
					
	                nicknameRow.appendChild(nicknameValue);
					nicknameRow.appendChild(writedateCol);
					
					replyBox.appendChild(nicknameRow);
					
					var sessionNo = data.sessionMemberNo;
					
	                if(sessionNo == data.memberVo.member_no) {
	                	
						var buttonRow = document.createElement("div");
						buttonRow.setAttribute("class","row");
						
						var spaceButtonCol = document.createElement("div");
						spaceButtonCol.setAttribute("class","col-10");
						var buttonCol = document.createElement("div");
						buttonCol.setAttribute("class","col text-center");
						
						if(replyNo == data.routeboardReplyVo.routeboard_reply_no) {
							
							var modifyButton = document.createElement("a");	// 댓글
																			// 수정
																			// 완료
							modifyButton.setAttribute("href","javascript:void(0);");
							modifyButton.setAttribute("class","custom-link");
							modifyButton.setAttribute("style","padding:5px; font-size:12px;");
							modifyButton.setAttribute("onclick","updateReplyProcess("+data.routeboardReplyVo.routeboard_reply_no+"); return false");
	 						modifyButton.innerText="완료";		
							
							
							var deleteButton = document.createElement("a");  // 댓글
																				// 수정
																				// 취소
							deleteButton.setAttribute("href","javascript:void(0);");
							deleteButton.setAttribute("class","custom-link");
							deleteButton.setAttribute("style","padding:5px; font-size:12px;");
							deleteButton.setAttribute("onclick","refreshReplyList()");
							deleteButton.innerText="취소";
							
						} else {
							
							var modifyButton = document.createElement("a");	// 댓글
																			// 수정
							modifyButton.setAttribute("href","javascript:void(0);");
							modifyButton.setAttribute("class","custom-link");
							modifyButton.setAttribute("style","padding:5px; font-size:12px;");
							modifyButton.setAttribute("onclick","updateReply("+data.routeboardReplyVo.routeboard_reply_no+"); return false"); // 댓글
																																				// 수정
							modifyButton.innerText="수정";
							
							var deleteButton = document.createElement("a");  // 댓글
																				// 삭제
							deleteButton.setAttribute("href","javascript:void(0);");
							deleteButton.setAttribute("class","custom-link");
							deleteButton.setAttribute("style","padding:5px; font-size:12px;");
							deleteButton.setAttribute("onclick","delete_reply_check("+data.routeboardReplyVo.routeboard_reply_no+")");
							deleteButton.innerText="삭제";
						}
						
						buttonCol.appendChild(modifyButton);
						buttonCol.appendChild(deleteButton);
						buttonRow.appendChild(spaceButtonCol);
						buttonRow.appendChild(buttonCol);
						replyBox.appendChild(buttonRow);
					}
	                
					var contentRow = document.createElement("div");
					contentRow.setAttribute("class","row");
					var contentCol = document.createElement("div");
					contentCol.setAttribute("class","col mb-4");
					contentCol.setAttribute("style","border-bottom:solid 1px #cccccc; margin-right: 15px;margin-left: 15px;");
					
					var hiddenValue = document.createElement("input");
					hiddenValue.setAttribute("type","hidden");
					hiddenValue.setAttribute("id","reply_no_check");
					hiddenValue.setAttribute("value",""+data.routeboardReplyVo.routeboard_reply_no+"");
					
					if(replyNo == data.routeboardReplyVo.routeboard_reply_no) {
						var contentValue = document.createElement("textarea");
						contentValue.setAttribute("class","form-control mt-2 mb-3");
						contentValue.setAttribute("style","resize:none");
						contentValue.setAttribute("id","update_routeboard_reply_content");
						contentValue.innerText = data.routeboardReplyVo.routeboard_reply_content;
					} else {
						var contentValue = document.createElement("div");
						contentValue.setAttribute("class","col mt-2 mb-5");
						contentValue.innerText = data.routeboardReplyVo.routeboard_reply_content;
					}
					
					contentCol.appendChild(hiddenValue);
					contentCol.appendChild(contentValue);
					contentRow.appendChild(contentCol);
					
					replyBox.appendChild(contentRow);
				}
    		}
    	};
    		
		xmlhttp.open("post","rt_get_reply_list.do" , true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("routeboard_no=" + routeboardNo);
}

// 댓글 쓰기
function writeReply(session) {
		
		var loginCheck = session;
		
		
		if(checkLogin(loginCheck)) {
			var replyContentCheck = document.getElementById("routeboard_reply_content");
			var replyContent = document.getElementById("routeboard_reply_content").value;
			
			// 공백 입력도 막기
	        const blankPattern = /^\s+|\s+$/g;

	        // 공백을 ""로 치환
	        replyContent = replyContent.replace(blankPattern, "");

	        // 특수문자 치환
	        replyContent = encodeURIComponent(replyContent);
			
	        // 공백 입력 시 경고창
			if(replyContent == "") {
				alert('내용을 입력해주세요');
				return;
			}
		
			var routeboardNo = document.getElementById("routeboard_no").value;
			var ReplyContentBox = document.getElementById("routeboard_reply_content");

			var xmlHttpRequest = new XMLHttpRequest();
		    
			xmlHttpRequest.onreadystatechange = function() {
		    		
		    		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
						
		    			refreshReplyList();
		    			ReplyContentBox.value="";
		    		}
		    	};
		    		
		    	xmlHttpRequest.open("post","rt_write_reply_process.do",true);
		  		xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xmlHttpRequest.send("routeboard_no=" + routeboardNo + "&routeboard_reply_content=" + replyContent);
		}
}

// 댓글 삭제
function deleteReply(replyNo) {
	
	var xmlHttpRequest = new XMLHttpRequest();
    
	xmlHttpRequest.onreadystatechange = function() {
    		
    	if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
				
    		refreshReplyList();
    	}
    };
	xmlHttpRequest.open("post","rt_delete_reply_process.do",true);
	xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlHttpRequest.send("routeboard_reply_no=" + replyNo);
}


function updateReply(replyNo) {
	
	refreshReplyList(replyNo);
	
}

// 댓글 수정
function updateReplyProcess(replyNo) {
	
	var routeboardReplyNo = replyNo;
	var replyContent = document.getElementById("update_routeboard_reply_content").value;
	var ReplyContentBox = document.getElementById("update_routeboard_reply_content");

	// 공백 입력도 막기
    const blankPattern = /^\s+|\s+$/g;

    // 공백을 ""로 치환
    replyContent = replyContent.replace(blankPattern, "");

    // 특수문자 치환
    replyContent = encodeURIComponent(replyContent);
	
    // 공백 입력 시 경고창
	if(replyContent == "") {
		alert('내용을 입력해주세요');
		return;
	}
	
	var xmlHttpRequest = new XMLHttpRequest();
    
	xmlHttpRequest.onreadystatechange = function() {
    		
    		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
				
    			refreshReplyList();
    			ReplyContentBox.value="";
    		}
    	};
    		
    	xmlHttpRequest.open("post","rt_update_reply_process.do",true);
  		xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xmlHttpRequest.send("routeboard_reply_no=" + routeboardReplyNo + "&routeboard_reply_content=" + replyContent);
    }
    
// 글 삭제 확인
function delete_check(routeboard_no) {
   if (confirm("정말 삭제하시겠습니까?")){    // 확인
      
      location.href = "rt_delete_content_process.do?routeboard_no="+routeboard_no;
         
   }else{   // 취소

      return false;
   }
}

// 댓글 삭제 확인
function delete_reply_check(deleteByreplyNo) {
	   
	   if (confirm("정말 삭제하시겠습니까?")){    // 확인

		   deleteReply(deleteByreplyNo);
	   
		 }else{   // 취소

		     return false;
		 }
}

// 루트 - 좋아요 출력
function like_check() {
	
	var session_check = document.getElementById("session_check").value;
				
	if(session_check == "false") {
	    		
	} else {
	
		var xmlhttp = new XMLHttpRequest();
					
		xmlhttp.onreadystatechange = function() {
		
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var resultData = JSON.parse(xmlhttp.responseText);

				for(var data of resultData){
								
					var aa = data.routeboard_no;
					var like_icon = document.getElementById(aa);
					if(like_icon != null) {
						like_icon.setAttribute("class","fas fa-heart fa-lg");
					
					}
				}
			}
		}
					
	xmlhttp.open("post", "like_all_process.do", true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send();
				
	};
}

// 루트 - 좋아요, 좋아요 취소
function like_func(clicked_id) {
	
	var session_check = document.getElementById("session_check").value;
	if(session_check == "false") {
		alert('로그인 후 이용해주세요.');
		location.href = "../member/login_page.do?uri=routeboard/rt_read_content_page.do?routeboard_no="+clicked_id;
		
	} else {
		var routeboard_no = clicked_id;
		var like_icon = document.getElementById(clicked_id);
		var like_count_update = document.getElementById("like_"+clicked_id);
		var xmlhttp = new XMLHttpRequest();
			
		xmlhttp.onreadystatechange = function() {
				
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var resultData = JSON.parse(xmlhttp.responseText);
				
				if(resultData.value == 'true') {	// 좋아요 이미 함 -> 빈 하트로 바뀌어야 함
					like_icon.setAttribute("class","far fa-heart fa-lg");
					like_count_update.innerText = resultData.count;
					
				} else {	// 좋아요한 적 없음 -> 빨간 하트로 바뀌어야 함
					like_icon.setAttribute("class","fas fa-heart fa-lg");
					like_count_update.innerText = resultData.count;
				}
				like_check();
			}
		};
			
		xmlhttp.open("post", "like_process.do", true);
		xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xmlhttp.send("routeboard_no=" + routeboard_no);
	}
}
    
// 장소 - 좋아요 출력
function location_like_check() {
	
	var session_check = document.getElementById("session_check").value;
				
	if(session_check == "false") {
	    		
	} else {
	
		var xmlhttp = new XMLHttpRequest();
					
		xmlhttp.onreadystatechange = function() {
		
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var resultData = JSON.parse(xmlhttp.responseText);
				
				for(var data of resultData){
								
					var like_status = data.locationboard_no;
					var like_icon = document.getElementById('location_'+like_status);
					if(like_icon != null) {
						like_icon.setAttribute("class","fas fa-heart fa-lg");
					
					}
				}
			}
		}
					
	xmlhttp.open("post", "../locationboard/like_all_process.do", true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send();
				
	};
}

// 장소 - 좋아요, 좋아요 취소
function location_like_func(clicked_id) {

	var session_check = document.getElementById("session_check").value;
	if(session_check == "false") {
		alert('로그인 후 이용해주세요.');
		location.href = "../member/login_page.do?uri=routeboard/rt_read_content_page.do?routeboard_no="+clicked_id;
	}
	
	var location_id = clicked_id;
	var locationboard_no = location_id.split('_');
	var like_icon = document.getElementById(clicked_id);
	var like_count_update = document.getElementById('location_like_'+locationboard_no[1]);
	var xmlhttp = new XMLHttpRequest();
		
	xmlhttp.onreadystatechange = function() {
		
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var resultData = JSON.parse(xmlhttp.responseText);
			
			if(resultData.value == 'true') {	// 좋아요 이미 함 -> 빈 하트로 바뀌어야 함
				like_icon.setAttribute("class","far fa-heart fa-lg");
				like_count_update.innerText = resultData.count;
				
			} else {	// 좋아요한 적 없음 -> 빨간 하트로 바뀌어야 함
				like_icon.setAttribute("class","fas fa-heart fa-lg");
				like_count_update.innerText = resultData.count;
			}
			like_check();
		}
	};
		
	xmlhttp.open("post", "../locationboard/like_process.do", true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("locationboard_no=" + locationboard_no[1]);
}

// 루트 관심
function favorite_check(clicked_id) {
	
	var session_check = document.getElementById("session_check").value;
	var form = new FormData();
	var clickId = clicked_id;
	var splitId = clickId.split('_');
	
	console.log(clickId);
	
	if(session_check == "false") {
		alert("로그인이 필요합니다.");
		location.href= "/member/login_page.do?uri=routeboard/rt_read_content_page.do?routeboard_no="+splitId[1];
	
	} else {
	
		
		
		form.append("routeboard_no",splitId[1])
		
	    $.ajax({
			type	: "post", // 요청 메소드 타입
			url		: "/routeboard/favorite_process.do", // 요청 경로
			data  : form, // 요청 시 포함되어질 데이터
			async : true,
			processData : false, // 데이터를 컨텐트 타입에 맞게 변환 여부
			cache : false, // 캐시 여부
			contentType : false, // 요청 컨텐트 타입
									// "application/x-www-form-urlencoded;
									// charset=UTF-8"
			dataType	: "json", // 응답 데이터 형식 명시하지 않을 경우 자동으로 추측
			beforeSend  : function(){
			// XHR Header를 포함해서 HTTP Request를 하기전에 호출됩니다.
			},
			success	: function(data, status, xhr){
				if (data.returnCode == '0003') {
					alert(data.returnMsg);
					location.href = "/member/login_page.do?uri=routeboard/rt_read_content_page.do?routeboard_no="+clicked_id;
				}
	
				else if(data.returnCode == '0001') {
					$('#favorite_' + data.routeboard_no).removeClass("far fa-flag myfavorite fa-lg").addClass("fas fa-flag myfavorite fa-lg");
				}
				
				else if(data.returnCode == '0002') {
					$('#favorite_' + data.routeboard_no).removeClass("fas fa-flag myfavorite fa-lg").addClass("far fa-flag myfavorite fa-lg");
				}
				
				
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
			},
			error	: function(xhr, status, error){
			// 응답을 받지 못하였다거나 정상적인 응답이지만 데이터 형식을 확인할 수 없기 때문에 error 콜백이 호출될 수
			// 있습니다.
			// 예를 들어, dataType을 지정해서 응답 받을 데이터 형식을 지정하였지만, 서버에서는 다른 데이터형식으로 응답하면
			// error 콜백이 호출되게 됩니다.
			},
			always : function(xhr, status){
			// success와 error 콜백이 호출된 후에 반드시 호출됩니다.
			// try - catch - finally의 finally 구문과 동일합니다.
			}
    	});
	}
}

// 장소 관심 등록
function favorite_locationcheck(clicked_id) {
	
	var session_check = document.getElementById("session_check").value;
	
	var form = new FormData();
	var clickId = clicked_id;
	var splitId = clickId.split('_');
	
	console.log(clickId);
	
	if(session_check == "false") {
		alert("로그인이 필요합니다.");
		location.href= "/member/login_page.do?uri=routeboard/rt_read_content_page.do?routeboard_no="+splitId[1];
	
	} else {
		
		form.append("locationboard_no",splitId[1])
		
	    $.ajax({
			type	: "post", // 요청 메소드 타입
			url		: "/locationboard/favorite_process.do", // 요청 경로
			data  : form, // 요청 시 포함되어질 데이터
			async : true,
			processData : false, // 데이터를 컨텐트 타입에 맞게 변환 여부
			cache : false, // 캐시 여부
			contentType : false, // 요청 컨텐트 타입
									// "application/x-www-form-urlencoded;
									// charset=UTF-8"
			dataType	: "json", // 응답 데이터 형식 명시하지 않을 경우 자동으로 추측
			beforeSend  : function(){
			// XHR Header를 포함해서 HTTP Request를 하기전에 호출됩니다.
			},
			success	: function(data, status, xhr){
				if (data.returnCode == '0003') {
					alert(data.returnMsg);
					location.href = "/member/login_page.do?uri=routeboard/rt_read_content_page.do?routeboard_no="+clicked_id;
				}
	
				else if(data.returnCode == '0001') {
					$('#lcfavorite_' + data.locationboard_no).removeClass("far fa-flag myfavorite fa-lg").addClass("fas fa-flag myfavorite fa-lg");
				}
				
				else if(data.returnCode == '0002') {
					$('#lcfavorite_' + data.locationboard_no).removeClass("fas fa-flag myfavorite fa-lg").addClass("far fa-flag myfavorite fa-lg");
				}
				
				
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
			},
			error	: function(xhr, status, error){
			// 응답을 받지 못하였다거나 정상적인 응답이지만 데이터 형식을 확인할 수 없기 때문에 error 콜백이 호출될 수
			// 있습니다.
			// 예를 들어, dataType을 지정해서 응답 받을 데이터 형식을 지정하였지만, 서버에서는 다른 데이터형식으로 응답하면
			// error 콜백이 호출되게 됩니다.
			},
			always : function(xhr, status){
			// success와 error 콜백이 호출된 후에 반드시 호출됩니다.
			// try - catch - finally의 finally 구문과 동일합니다.
			}
    	});
	}
}

//쪽지 보내기
function sendUser(member_id){
	
   location.href="/note/nt_main_page.do?member_id="+member_id;
}

//사용자 신고
function reportUser(member_no, routeboard_no) {
	
	location.href="/report/report_user_page.do?member_no="+member_no+"&uri=routeboard/rt_read_content_page.do?routeboard_no="+routeboard_no;
	
}

function reportRoute(routeboard_no) {

	if(confirm("신고하시겠습니까?")) {
		
		location.href="/report/report_route_page.do?routeboard_no="+routeboard_no+"&uri=routeboard/rt_read_content_page.do?routeboard_no="+routeboard_no;
		
		return true;
	} else {
		
		return false;
	}
}