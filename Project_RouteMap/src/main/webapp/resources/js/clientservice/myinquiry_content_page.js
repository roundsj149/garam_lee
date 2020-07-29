//ajax를 이용한 prameter를 controller 로 넘기기

//잠시보류
function inquiryDeleteProcess() {

	var form = new FormData();
	form.append('deleteInquiryNo',$('#delete_inquiry_no').val());
	console.log($('#delete_inquiry_no').val());
   $.ajax({
		type	: "post", //요청 메소드 타입
		url		: "/clientservice/inquiryDelete_process.do", //요청 경로
		data  : form, //요청 시 포함되어질 데이터
		async : true,
		processData : false, //데이터를 컨텐트 타입에 맞게 변환 여부
		cache : false, //캐시 여부
		contentType : false, //요청 컨텐트 타입 "application/x-www-form-urlencoded; charset=UTF-8"
		dataType	: "json", //응답 데이터 형식 명시하지 않을 경우 자동으로 추측
		success: function(data, status, xhr){
			if (data) {
				if (data.returnCode == '0000') {
					alert(data.returnMsg);
					location.href = "/clientservice/myInquiry_page.do";
				} else {
					alert(data.returnMsg);
				}
			}
			
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
}