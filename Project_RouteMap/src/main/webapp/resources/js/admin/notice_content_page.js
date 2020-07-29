 function noticeContentProcess() {
	var form = new FormData();
	
	form.append("noticeNo", $('#notice_no').val());

 	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url : "/admin/notice_content_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
		success: function(data, status, xhr){
			if (data) {
				
				notice_list(data);
			}
			
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
}
function notice_list(data) {
	
	var milsec = data.noticeContent.noticeVo.notice_writedate;
	var date = new Date(milsec);
	var year = date.getFullYear();
	var month = 1 + date.getMonth();
	if(month < 10) {
		month = '0' + month
	}
	var day = date.getDate();
	if(day < 10) {
		day = '0' + day
	}
	var writedate = year + '.' + month + '.' + day;
	
	
	html = '';
	html += 
			'<tr>' +
				'<th class="text-center cs_inquiry_th">제목</th>' +
				'<td colspan="3" class="text-left cs_inquiry_td">' + data.noticeContent.noticeVo.notice_title + '</td>' +
			'</tr>' +
			'<tr>' +
				'<th class="text-center cs_inquiry_th">작성자</th>' +
				'<td class="text-left cs_inquiry_td">' + data.noticeContent.adminName + '</td>' +
				'<th class="text-center cs_inquiry_th">작성일</th>' +
				'<td class="text-left cs_inquiry_td"><span class="badge badge-pill badge-secondary">' + writedate + '</span></td>' +
			'</tr>' +
			'<tr>' +
				'<th class="text-center cs_inquiry_th">내용</th>' +
				'<td colspan="3" class="text-left cs_inquiry_td">' + data.noticeContent.noticeVo.notice_content + '</td>' +
			'</tr>' +
			'<input id="notice_admin" type="hidden" value="' + data.noticeContent.noticeVo.admin_no + '">' + 
			'<input id="notice_no" type="hidden" value="' + data.noticeContent.noticeVo.notice_no +'">';
			
	$('#notice_content').html(html);
	
	html2 = '';
	html2 += 
				
    '<a href="../admin/notice_page.do">' +
		'<button class="btn btn-secondary" id="cs_inquiry_submitbox_button" style="width:10%;">목록</button>' +
	'</a>' +
  	'<button class="btn btn-secondary" id="cs_inquiry_submitbox_button" style="width:10%;" onclick="notice_update_page()">수정</button>' +
  	'<button class="btn btn-secondary" id="cs_inquiry_submitbox_button" style="width:10%;" onclick="notice_delete_process()">삭제</button>';
	
	$('#notice_button').html(html2);
}

function notice_update_page() {
	
	var form = new FormData();
	
	form.append("noticeNo", $('#notice_no').val());
	
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url : "/admin/notice_content_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
		success: function(data, status, xhr){
			if (data) {
				
				var milsec = data.noticeContent.noticeVo.notice_writedate;
				var date = new Date(milsec);
				var year = date.getFullYear();
				var month = 1 + date.getMonth();
				if(month < 10) {
					month = '0' + month
				}
				var day = date.getDate();
				if(day < 10) {
					day = '0' + day
				}
				var writedate = year + '.' + month + '.' + day;
				
				console.log(writedate);
				html = '';
				html += 
						'<tr>' +
							'<th class="text-center cs_inquiry_th">제목</th>' +
							'<td colspan="3" class="text-left cs_inquiry_td"><input id="notice_title" class="form-control form-control-sm" type="text" value="' + data.noticeContent.noticeVo.notice_title + '"></td>' +
						'</tr>' +
						'<tr>' +
							'<th class="text-center cs_inquiry_th">작성자</th>' +
							'<td class="text-left cs_inquiry_td">' + data.noticeContent.adminName + '</td>' +
							'<th class="text-center cs_inquiry_th">작성일</th>' +
							'<td class="text-left cs_inquiry_td">' + writedate + '</td>' +
						'</tr>' +
						'<tr>' +
							'<th class="text-center cs_inquiry_th">내용</th>' +
							'<td colspan="3" class="text-left cs_inquiry_td"><textarea id="notice_textarea" class="form-control" rows="8" id="faq_update_content" maxlength="333">' + data.noticeContent.noticeVo.notice_content + '</textarea></td>' +
						'</tr>' + 
						'<input id="notice_admin" type="hidden" value="' + data.noticeContent.noticeVo.admin_no + '">' +
						'<input id="notice_no" type="hidden" value="' + data.noticeContent.noticeVo.notice_no +'">';
				
						
				$('#notice_content').html(html);
				
				html2 = '';
				html2 += 
							
            	'<button class="btn btn-secondary" id="cs_inquiry_submitbox_button" style="width:10%;" onclick="noticeContentProcess()">취소</button>' +
            	'<button class="btn btn-secondary" id="cs_inquiry_submitbox_button" style="width:10%;" onclick="notice_update_process()">확인</button>';
            	
				$('#notice_button').html(html2);
				
			}
			
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
}

function notice_update_process() {
	
var form = new FormData();
	
	form.append("noticeNo", $('#notice_no').val());
	form.append("noticeTitle",$('#notice_title').val());
	form.append("noticeContent",$('#notice_textarea').val());
	form.append("adminNo",$('#notice_admin').val());
	
	

 	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url : "/admin/notice_update_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
		success: function(data, status, xhr){
			if (data.returnMsg == '1111') {
				alert("수정불가");
			} else if(data.returnMsg == '0000') {
				alert("수정완료");
				location.href="/admin/notice_content_page.do?noticeNo=" + data.noticeNo;
			}
			
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
	
}

function notice_delete_process() {
	
	var form = new FormData();
	
	form.append("noticeNo",$('#notice_no').val());
	form.append("adminNo",$('#notice_admin').val());
	
 	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url : "/admin/notice_delete_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
		success: function(data, status, xhr){
			if (data) {
				alert("삭제되었습니다.");
				location.href="/admin/notice_page.do";
			}
			
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
}

window.onload = noticeContentProcess;

