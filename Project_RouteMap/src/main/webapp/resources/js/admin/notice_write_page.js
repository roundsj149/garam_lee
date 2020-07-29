//문의제목 정규식 입력방지
var replaceTitle = /[<>()'"{}#$%&*+_=/|`\-\[\]\\]/gi;

$(document).ready(function() {

    $("#cs_notice_title").on("focusout",function() {

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

//문의내용 특수문자 정규식 입력방지
var replaceContent = /[<>()'"{}#$%&*+_=/|`\-\[\]\\]/gi;

    $(document).ready(function() {

        $("#cs_notice_content").on("focusout",function() {

            var x = $(this).val();

            if(x.match(replaceContent)) {
                x = x.replace(replaceTitle,"");
            }

            $(this).val(x)

        }).on("keyup", function() {

            $(this).val($(this).val().replace(replaceContent,""));

        });

    });

function inquirySubmit() {
    var frm = $("cs_inquiry_form");
    var titlebox = document.getElementById("cs_notice_title");
    var contentbox = $("cs_notice_content");

    var reg = "";
    if(reg == (titlebox).value) {
        alert("제목을 입력해주세요");

        $("#cs_notice_title").val = "";
        $("#cs_notice_title").focus();
        frm.submit();

        return;
    }
}


function noticeWriteProcess() {

	var form = new FormData();
	
	form.append("noticeTitle",$('#cs_notice_title').val());
	form.append("noticeContent",$('#cs_notice_content').val());
	
 	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url : "/admin/notice_write_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
		success: function(data, status, xhr){
			if (data.returnMsg == '0000') {
				alert("글등록 완료");
				location.href = "/admin/notice_page.do";
			}
			
			else if (data.returnMsg == '1111') {
				alert("로그인이 필요합니다.");
				location.href = "/member/login_page.do";
			}
			
			
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
}

