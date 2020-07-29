console.log("include resources/js/locationboard/lc_read_content_page.js");

window.onload = function() {
   refreshReplyList();
   like_check();
};

// 댓글 내용 작성 전 로그인 확인
function checkLoginForForm(clicked_id) {
   
   // 세션 값
   const session_check = document.getElementById("session_check").value;

   if(session_check == "false") {
	   alert('로그인 후 이용해주세요.');
	   location.href = "/member/login_page.do?uri=locationboard/lc_read_content_page.do?locationboard_no="+clicked_id;
	}
}       

// 버튼 - 댓글 쓰기
function buttonWriteReply(clicked_id) {
   
   // 로그인 확인
   checkLoginForWriteReply(clicked_id);
   
}

// 로그인 안 했으면 기능(댓글) 못 하게
function checkLoginForWriteReply(clicked_id) {
   
   // 세션 값
   const session_check = document.getElementById("session_check").value;
   
   if(session_check == "false") {

	   alert('로그인 후 이용해주세요.');
	   location.href = "/member/login_page.do?uri=locationboard/lc_read_content_page.do?locationboard_no="+clicked_id;
	  
      return;
      
   } else {

	   // 댓글 내용을 확인
	   checkReplyContentForWriteReply();
   }
}

// 확인 - 댓글 내용(스페이스, 엔터 등의 공백)
function checkReplyContentForWriteReply() {
   
	 var replyContentBox = document.getElementById("locationboard_reply_content");
	    var replyContent = replyContentBox.value;
	    const blankContent = "";
	    const blankPattern = /^\s+|\s+$/g;
	    
	    replyContent = replyContent.replace(blankPattern, "");
	    // 특수문자 치환
	    replyContent = encodeURIComponent(replyContent);
	    if (replyContent != blankContent) {
	        // 댓글 쓰기
	        doWriteReply(replyContent);
	    } else {
	        // 댓글 내용이 빈칸일 경우
	        alert('댓글 내용을 입력하지 않았습니다.');
	        // 댓글 작성칸 비우기
	        replyContentBox.value = "";
	    }

      
}

//기능 - 댓글 쓰기
function doWriteReply(replyContent) {
    // var frm_writeReply = document.getElementById("frm_writeReply");
    // frm_writeReply.submit(); // 댓글 작성 버튼 눌렀을 때 전송됨
    var xmlHttpRequest = new XMLHttpRequest();
    const locationboardNo = document.getElementById("locationboard_no").value;
    var replyContentBox = document.getElementById("locationboard_reply_content");
    // 장소 댓글 목록 갱신
    xmlHttpRequest.onreadystatechange = function () {
        if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
            refreshReplyList();
            replyContentBox.value = "";            // 댓글 작성칸 비우기
        }
    };
    xmlHttpRequest.open("post", "lc_write_reply_process.do", true);
    xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttpRequest.send("locationboard_no=" + locationboardNo + "&locationboard_reply_content=" + replyContent);
}
   
// 댓글 갱신
function refreshReplyList(locatinoboardReplyNo) {
   var xmlHttpRequest = new XMLHttpRequest();
   var ReplyNoForUpdate = locatinoboardReplyNo;
               
   xmlHttpRequest.onreadystatechange = function() {
       if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
      
      // 자바 스크립트로 동적 UI 꾸미기
      var resultData = JSON.parse(xmlHttpRequest.responseText);   
      var replyBox = document.getElementById("reply_box");
            
      // 댓글 박스 안에 내용 지우기
      var length = replyBox.childNodes.length;
      for(var i = 0; i < length; i++){
         replyBox.removeChild(replyBox.childNodes[0]);
      }
            
      for(data of resultData){
                     
                 var locatinoboardReplyNo = data.locationboardReplyVo.locatinoboard_reply_no;
         
                 /* 댓글 박스 - 시작 */
                 // boxRow1
                 var boxRow1 = document.createElement("div");   // 작성 정보
                 boxRow1.setAttribute("class","row");

                 // boxRow1 - col1
                 var boxRow1_col1 = document.createElement("div");
                 boxRow1_col1.setAttribute("class","col-10");
                 boxRow1_col1.setAttribute("style","font-weight: bold");
                 
                 var locatinoboardReplyNo = document.createElement("input");      // 댓글
                                                                  // 번호
                 locatinoboardReplyNo.setAttribute("type","hidden");
                 locatinoboardReplyNo.setAttribute("name","locatinoboard_reply_no");
                 locatinoboardReplyNo.setAttribute("value",data.locationboardReplyVo.locatinoboard_reply_no);
                  
                 var boxNickName = document.createElement("div");      // 닉네임
                 boxNickName.setAttribute("class","col-10 mr-5");
                 boxNickName.setAttribute("style","font-weight: bold");
                 boxNickName.innerText = data.memberVo.member_nickname;
                 boxRow1_col1.appendChild(boxNickName);
                 boxRow1.appendChild(boxRow1_col1);
                 // boxRow1 - col2
                 var boxRow1_col2 = document.createElement("div");   // 작성일
                 boxRow1_col2.setAttribute("class","col text-center"); 
                 boxRow1_col2.setAttribute("style","font-size:12px");
                  
                 var boxWriteDate = document.createElement("div");
                 boxWriteDate.setAttribute("class","col");
                 boxWriteDate.setAttribute("style","font-size:12px");
                 var milliseconds = data.locationboardReplyVo.locationboard_reply_writedate;
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
                 // var sec = date.getSeconds(); // s
                    // sec = sec >= 10 ? sec : '0' + sec; // ss 두자리로 저장
                 // yy.MM.dd hh:mm:ss
                 boxWriteDate.innerText = year+"."+month+"."+day+" "+hour+":"+min;
                  
                 boxRow1_col2.appendChild(boxWriteDate);
                 boxRow1.appendChild(boxRow1_col2);
                  
                 // boxRow3
                 var boxRow3 = document.createElement("div");
                 boxRow3.setAttribute("class","row");
                    
                 var spaceButtonCol = document.createElement("div");
                 spaceButtonCol.setAttribute("class","col-10");
                    var buttonCol = document.createElement("div");
                 buttonCol.setAttribute("class","col text-center");
                  
          // 세션 확인
          var sessionNo = data.sessionMemberNo;
      
          if(sessionNo == data.memberVo.member_no){
         
             // 댓글 수정으로 전환
             if(ReplyNoForUpdate == data.locationboardReplyVo.locatinoboard_reply_no){ 
                 // update
                 var buttonUpdateReply = document.createElement("a"); // 수정
                                                         // 완료
                                                         // 버튼
                 buttonUpdateReply.setAttribute("href","javascript:void(0);");
                 buttonUpdateReply.setAttribute("class","custom-link");
                 buttonUpdateReply.setAttribute("style","padding:5px; font-size:12px;");
                 buttonUpdateReply.setAttribute("onclick","buttonUpdateReply("+data.locationboardReplyVo.locatinoboard_reply_no+")");
                 buttonUpdateReply.innerText="완료";
                                    
                 // cancel update
                 var buttonDeleteReply = document.createElement("a");  // 댓글
                                                         // 수정
                                                         // 취소
                 buttonDeleteReply.setAttribute("href","javascript:void(0);");
                 buttonDeleteReply.setAttribute("class","custom-link");
                 buttonDeleteReply.setAttribute("style","padding:5px; font-size:12px;");
                 buttonDeleteReply.setAttribute("onclick","buttonEditReply()");
                 buttonDeleteReply.innerText="취소";
                    
             } else {
                     // 댓글 출력
                 var buttonUpdateReply = document.createElement("a"); // 댓글
                                                         // 수정
                                                         // 페이지로
                                                         // 이동
                 buttonUpdateReply.setAttribute("href","javascript:void(0);");
                 buttonUpdateReply.setAttribute("class","custom-link");
                 buttonUpdateReply.setAttribute("style","padding:5px; font-size:12px;");
                 buttonUpdateReply.setAttribute("onclick","buttonEditReply("+data.locationboardReplyVo.locatinoboard_reply_no+")");
                 buttonUpdateReply.innerText="수정";
                      
                 var buttonDeleteReply = document.createElement("a");  // 댓글
                                                         // 삭제
                 buttonDeleteReply.setAttribute("href","javascript:void(0);");
                 buttonDeleteReply.setAttribute("class","custom-link");
                 buttonDeleteReply.setAttribute("style","padding:5px; font-size:12px;");
                 buttonDeleteReply.setAttribute("onclick","buttonDeleteReply("+data.locationboardReplyVo.locatinoboard_reply_no+")");
                 buttonDeleteReply.innerText="삭제";
              } 
                  
                buttonCol.appendChild(buttonUpdateReply);
                 buttonCol.appendChild(buttonDeleteReply);
                  
           }
                  
                 boxRow3.appendChild(spaceButtonCol);
                 boxRow3.appendChild(buttonCol);
                  
                 // boxRow2
                 var boxRow2 = document.createElement("div");   
                 boxRow2.setAttribute("class","row");
                 // boxRow2 - col1
                 var boxRow2_col1 = document.createElement("div");   // 댓글 내용
                 boxRow2_col1.setAttribute("class","col mb-4"); 
                 boxRow2_col1.setAttribute("style","border-bottom:solid 1px #cccccc");
                  
             // 댓글 수정으로 전환
             if(ReplyNoForUpdate == data.locationboardReplyVo.locatinoboard_reply_no){ 
                 var boxReplyContent = document.createElement("textarea");
                 boxReplyContent.setAttribute("id","locationboard_reply_content");
                 boxReplyContent.setAttribute("name","locationboard_reply_content");
                 boxReplyContent.setAttribute("class","form-control mt-2 mb-3");
                 boxReplyContent.setAttribute("onclick","checkLoginForForm()");
                 boxReplyContent.setAttribute("style","resize:none");
                 boxReplyContent.setAttribute("placeholder","댓글 내용 입력");
                 boxReplyContent.setAttribute("onfocus","this.placeholder=''");
                 boxReplyContent.innerText = data.locationboardReplyVo.locationboard_reply_content;
                  
             } else {
                 // 댓글 내용 출력
                 var boxReplyContent = document.createElement("div");
                 boxReplyContent.setAttribute("class","col mt-2 mb-5");
                 boxReplyContent.innerText = data.locationboardReplyVo.locationboard_reply_content;
             }
                  
                 boxRow2_col1.appendChild(boxReplyContent);
                 boxRow2.appendChild(boxRow2_col1);
                  
                 replyBox.appendChild(boxRow1);
                 replyBox.appendChild(boxRow3);
                 replyBox.appendChild(boxRow2);
                  
                    /* 댓글 박스 - 끝 */
                              
        }
    }
   };
   
   const locationboardNo = document.getElementById("locationboard_no").value;
   
    xmlHttpRequest.open("get", "lc_get_reply_list.do?locationboard_no="+locationboardNo, true);
      
    xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
      
    xmlHttpRequest.send();
}
   
// 버튼 - 댓글 수정 편집창 열기
function buttonEditReply(locatinoboard_reply_no) {
      
    var xmlHttpRequest = new XMLHttpRequest();
    const locationboardNo = document.getElementById("locationboard_no").value;
    var locatinoboardReplyNo = locatinoboard_reply_no;
    var replyContentBox = document.getElementById("locationboard_reply_content");
    var replyContent = replyContentBox.value;
            
    refreshReplyList(locatinoboardReplyNo);
    replyContentBox.value = "";            // 댓글 작성칸 비우기

    xmlHttpRequest.open("get", "lc_get_reply_list.do?locationboard_no="+locationboardNo, true);
    xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttpRequest.send();
      
}

// 버튼 - 댓글 수정 완료
function buttonUpdateReply(locatinoboard_reply_no) {
   
   // 로그인 확인
   checkLoginForUpdateReply(locatinoboard_reply_no);
   
}

// 로그인 안 했으면 기능(댓글 수정) 못 하게
function checkLoginForUpdateReply(locatinoboard_reply_no) {
   
   // 세션 값
   const session_check = document.getElementById("session_check").value;
   
   if(session_check == "false") {
      
      alert('로그인 후 이용해주세요.');
      location.href = "../member/login_page.do";
      
      return;
      
   } else {
            
      // 댓글 내용을 확인
      checkReplyContentForUpdateReply(locatinoboard_reply_no);
      
   }
       
}

// 확인 - 댓글 내용(스페이스, 엔터 등의 공백)
function checkReplyContentForUpdateReply(locatinoboard_reply_no) {
   
   var replyContentBox = document.getElementById("locationboard_reply_content");
   var replyContent = replyContentBox.value;
   
   const blankContent = "";
   const blankPattern = /^\s+|\s+$/g;
   replyContent = replyContent.replace(blankPattern, "");
   // 특수문자 치환
   replyContent = encodeURIComponent(replyContent);
      
   if(replyContent != blankContent) {
      
      // 댓글 수정
      doUpdateReply(locatinoboard_reply_no);

   } else {
      
      // 댓글 내용이 빈칸일 경우
      alert('댓글 내용을 입력하지 않았습니다.');
      // 댓글 작성칸 비우기
      replyContentBox.value = "";  
      
   }
      
}

function doUpdateReply(locatinoboard_reply_no) {
    // var frm_writeReply = document.getElementById("frm_writeReply");
    // frm_writeReply.submit(); // 댓글 작성 버튼 눌렀을 때 전송됨
    var xmlHttpRequest = new XMLHttpRequest();
    var locatinoboardReplyNo = locatinoboard_reply_no;
    var replyContentBox = document.getElementById("locationboard_reply_content");
    var replyContent = replyContentBox.value;

    // 장소 댓글 목록 갱신
    xmlHttpRequest.onreadystatechange = function() {
        if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
            refreshReplyList();
            replyContentBox.value = "";            // 댓글 작성칸 비우기
        }
    };

    xmlHttpRequest.open("post", "lc_update_reply_process.do", true);
    xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttpRequest.send("locatinoboard_reply_no=" + locatinoboardReplyNo + "&locationboard_reply_content=" + replyContent);
}

function deleteReply(locatinoboard_reply_no) {
      
    var xmlHttpRequest = new XMLHttpRequest();
    var locatinoboardReplyNo = locatinoboard_reply_no;
    var replyContentBox = document.getElementById("locationboard_reply_content");

    // 장소 댓글 목록 갱신
    xmlHttpRequest.onreadystatechange = function() {
    if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {

    	refreshReplyList();
        replyContentBox.value = "";      // 댓글 작성칸 비우기
    } 
};
      
    xmlHttpRequest.open("post", "lc_delete_reply_process.do", true);
    xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttpRequest.send("locatinoboard_reply_no=" + locatinoboardReplyNo);
}
   
// 글 삭제 확인
function buttonDeletePost(locationboard_no) {
       
    if (confirm("글을 삭제하시겠습니까?")){    // 확인

        location.href = "/locationboard/lc_delete_process.do?locationboard_no="+locationboard_no;

    } else {   // 취소

        return false;

    }
}
    
// 댓글 삭제 확인
function buttonDeleteReply(deleteByreplyNo) {
           
    if (confirm("댓글을 삭제하시겠습니까?")){    // 확인

        deleteReply(deleteByreplyNo);
           
    } else {   // 취소
        return false;
    }
}
    
// 좋아요 출력
function like_check() {
	
	var session_check = document.getElementById("session_check").value;
				
	if(session_check == "false") {
	    		
	} else {
	
		var xmlhttp = new XMLHttpRequest();
					
		xmlhttp.onreadystatechange = function() {
		
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var resultData = JSON.parse(xmlhttp.responseText);

				for(var data of resultData){
								
					var aa = data.locationboard_no;
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

// 좋아요, 좋아요 취소
function like_func(clicked_id) {
	
	var session_check = document.getElementById("session_check").value;
	if(session_check == "false") {
		
		alert('로그인 후 이용해주세요.');
		location.href = "../member/login_page.do?uri=locationboard/lc_read_content_page.do?locationboard_no="+clicked_id;
		
	} else {
		var locationboard_no = clicked_id;
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
		xmlhttp.send("locationboard_no=" + locationboard_no);
	}
		
}

// 관심 확인
function favorite_check(clicked_id) {
	
	var session_check = document.getElementById("session_check").value;
	if(session_check == "false") {

		alert('로그인 후 이용해주세요.');
		location.href = "../member/login_page.do?uri=locationboard/lc_read_content_page.do?locationboard_no="+clicked_id;
		
	} else {
		
		var form = new FormData();
		var clickId = clicked_id;
		var splitId = clickId.split('_');
		
		form.append("locationboard_no",splitId[1]);
		
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
					location.href = "../member/login_page.do?uri=locationboard/lc_read_content_page.do?locationboard_no="+clicked_id;
				}
	
				else if(data.returnCode == '0001') {
				$('#favorite_' + data.locationboard_no).removeClass("far fa-flag myfavorite fa-lg").addClass("fas fa-flag myfavorite fa-lg");
				}
				
				else if(data.returnCode == '0002') {
					$('#favorite_' + data.locationboard_no).removeClass("fas fa-flag myfavorite fa-lg").addClass("far fa-flag myfavorite fa-lg");
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

// 사용자 신고
function reportUser(member_no, locationboard_no) {
	
	location.href="/report/report_user_page.do?member_no="+member_no+"&uri=locationboard/lc_read_content_page.do?locationboard_no="+locationboard_no;
	
}

// 여행지/맛집 게시물 신고

function reportLocation(locationboard_no) {

	if(confirm("신고하시겠습니까?")) {
		
		location.href="/report/report_location_page.do?locationboard_no="+locationboard_no+"&uri=locationboard/lc_read_content_page.do?locationboard_no="+locationboard_no;
		
		return true;
	} else {
		
		return false;
	}
}





























