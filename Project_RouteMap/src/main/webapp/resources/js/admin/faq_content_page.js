function faqContentProcess() {
	var form = new FormData();
	
	form.append("faqNo",$('#faq_no').val());
	
	console.log($('#faq_no').val());
 	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url : "/admin/faq_content_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
		success: function(data, status, xhr){
			if (data) {
				
				faq_list(data);
			}
			
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
}
//자주묻는 질문 조회
function faq_list(data) {
	
	var milsec = data.faqContent.faqVo.faq_writedate;
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
				'<th class="text-center cs_inquiry_th">유형</th>' +
				'<td class="text-left cs_inquiry_td">' + data.faqContent.clientServiceFaqCategoryVo.faq_category_name + '</td>' +
				'<th class="text-center cs_inquiry_th">작성일</th>' +
				'<td class="text-center cs_inquiry_td"><span class="badge badge-pill badge-secondary">' + writedate + '</span></td>' +
			'</tr>' +
			'<tr>' +
				'<th class="text-center cs_inquiry_th">제목</th>' +
				'<td class="text-left cs_inquiry_td">' + data.faqContent.faqVo.faq_title + '</td>' +
				'<th class="text-center cs_inquiry_th">작성자</th>' +
				'<td class="text-center cs_inquiry_td">' + data.faqContent.adminName + '</td>' +
			'</tr>' +
			'<tr>' +
				'<td colspan="4" class="text-left cs_inquiry_td">' + data.faqContent.faqVo.faq_content + '</td>' +
			'</tr>';
			
	$('#faq_content').html(html);
}

//자주묻는 질문 삭제
function faq_delete_process() {
	
	var form = new FormData();
	
	form.append("faqNo",$('#faq_no').val());
	
	console.log($('#faq_no').val());
 	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url : "/admin/faq_delete_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
		success: function(data, status, xhr){
			if (data) {
				alert("삭제되었습니다.");
				location.href="/admin/faq_page.do";
			}
			
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
}

window.onload = faqContentProcess;
