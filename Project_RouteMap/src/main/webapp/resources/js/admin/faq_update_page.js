function faqUpdateProcess() {
	var form = new FormData();
	
	form.append("faqNo",$('#faq_no').val());
	form.append("faqUpdateCategory",$('#faq_update_category').val());
	form.append("faqUpdateTitle",$('#faq_update_title').val());
	form.append("faqUpdateContent",$('#faq_update_content').val());
	
	console.log($('#faq_no').val());
	
 	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url : "/admin/faq_update_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
		success: function(data, status, xhr){
			if(data) {
				alert(data.returnMsg);
				location.href= "/admin/faq_content_page.do?faqNo=" + data.faqNo;
			}
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
}