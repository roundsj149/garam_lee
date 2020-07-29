//문의제목 정규식 입력방지
var replaceTitle = /[<>()'"{}#$%&*+_=/|`\-\[\]\\]/gi;

$(document).ready(function() {

    $("#notice_search_input").on("focusout",function() {

        var x = $(this).val();
        if (x.length > 0) {
            if (x.match(replaceTitle)) {
                x = x.replace(replaceTitle,"");
            }

            $(this).val(x)
        }
    
    }).on("keyup", function() {
        $(this).val($(this).val().replace(replaceTitle,""));
    
    });

});
//ajax를 이용한 prameter를 controller 로 넘기기

//잠시보류
/*function noticeSearchProcess() {

	var form = new FormData();
	form.append('searchTitle',$('#notice_search_input').val());

   $.ajax({
		type	: "post", //요청 메소드 타입
		url		: "/clientservice/notice_search_process.do", //요청 경로
		data  : form, //요청 시 포함되어질 데이터
		async : true,
		processData : false, //데이터를 컨텐트 타입에 맞게 변환 여부
		cache : false, //캐시 여부
		contentType : false, //요청 컨텐트 타입 "application/x-www-form-urlencoded; charset=UTF-8"
		dataType	: "json", //응답 데이터 형식 명시하지 않을 경우 자동으로 추측
		beforeSend  : function(){
		// XHR Header를 포함해서 HTTP Request를 하기전에 호출됩니다.
		},
		success: function (xhr, status, error) {
		},
               
		// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
		// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.

		fail	: function(xhr, status, error){
		// 응답을 받지 못하였다거나 정상적인 응답이지만 데이터 형식을 확인할 수 없기 때문에 error 콜백이 호출될 수 있습니다.
		// 예를 들어, dataType을 지정해서 응답 받을 데이터 형식을 지정하였지만, 서버에서는 다른 데이터형식으로 응답하면  error 콜백이 호출되게 됩니다.
		},
		always : function(xhr, status){
			if(data.redirect) {
				console.log(data.redirect);
				window.location.href=data.redirect;
			}
		// success와 error 콜백이 호출된 후에 반드시 호출됩니다.
		// try - catch - finally의 finally 구문과 동일합니다.
		}
	});
}*/