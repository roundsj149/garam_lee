//문의제목 정규식 입력방지
var replaceTitle = /[<>()'"{}#$%&*+_=/|`\-\[\]\\]/gi;

$(document).ready(function() {

    $("#cs_inquiry_title").on("focusout",function() {

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

        $("#cs_inquiry_content").on("focusout",function() {

            var x = $(this).val();

            if(x.match(replaceContent)) {
                x = x.replace(replaceTitle,"");
            }

            $(this).val(x)

        }).on("keyup", function() {

            $(this).val($(this).val().replace(replaceContent,""));

        });
        
        //첨부파일 파일명 표시하기
    	$("#upload_files").on("change",function(){
    		var fileName= $("#upload_files").val();
    		$(this).next(".custom-file-label").html(fileName);
    	})

    });

function inquirySubmit() {
    var frm = $("cs_inquiry_form");
    var titlebox = document.getElementById("cs_inquiry_title");
    var contentbox = $("cs_inquiry_content");

    var reg = "";
    if(reg == (titlebox).value) {
        alert("제목을 입력해주세요");

        $("#cs_inquiry_title").val = "";
        $("#cs_inquiry_title").focus();
        frm.submit();

        return;
    }
}


function faqWriteProcess() {

	var form = new FormData();
	
	form.append("faqCategoryNo",$('#cs_faq_select').val());
	form.append("faqTitle",$('#cs_faq_title').val());
	form.append("faqContent",$('#cs_faq_content').val());
	
 	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url : "/admin/faq_write_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
		success: function(data, status, xhr){
			if (data) {
				alert("글등록 완료");
				location.href = "/admin/faq_page.do";
			}
			
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
}

