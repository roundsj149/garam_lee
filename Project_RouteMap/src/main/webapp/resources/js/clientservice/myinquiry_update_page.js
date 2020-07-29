//문의제목 정규식 입력방지
var replaceTitle = /[<>()'"{}#$%&*+_=/|`\-\[\]\\]/gi;

$(document).ready(function() {
	
	//첨부파일 삭제이벤트 (뷰어에서만 사라지고 데이터는 존재함)
	$( '#uploaded_delete_button' ).click( function() {
		if (confirm("삭제하시겠습니까?")) {
			$("input[type=hidden][name=num]").val('Y');
	    $('#uploaded_file_box').remove();
	    $('#uploaded_delete_button').remove();
		}
	  } );
	
	 debugger;

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

	


//ajax를 이용한 prameter를 controller 로 넘기기

//function inquiryProcess() {
//
//    var sendData = {
//    	"title" : $('#cs_inquiry_title').val()
//    	};
//
//    $.ajax({
//		type	: "post", //요청 메소드 타입
//		url		: "./inquiry_process.do", //요청 경로
//		data  : sendData, //요청 시 포함되어질 데이터
//		async : true,
//		processData : true, //데이터를 컨텐트 타입에 맞게 변환 여부
//		cache : true, //캐시 여부
//		contentType : "application/json;charset=UTF-8", //요청 컨텐트 타입 "application/x-www-form-urlencoded; charset=UTF-8"
//		dataType	: "json", //응답 데이터 형식 명시하지 않을 경우 자동으로 추측
//		beforeSend  : function(){
//		// XHR Header를 포함해서 HTTP Request를 하기전에 호출됩니다.
//		},
//		done	: function(data, status, xhr){
//		// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
//		// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
//		},
//		fail	: function(xhr, status, error){
//		// 응답을 받지 못하였다거나 정상적인 응답이지만 데이터 형식을 확인할 수 없기 때문에 error 콜백이 호출될 수 있습니다.
//		// 예를 들어, dataType을 지정해서 응답 받을 데이터 형식을 지정하였지만, 서버에서는 다른 데이터형식으로 응답하면  error 콜백이 호출되게 됩니다.
//		},
//		always : function(xhr, status){
//		// success와 error 콜백이 호출된 후에 반드시 호출됩니다.
//		// try - catch - finally의 finally 구문과 동일합니다.
//		}
//	});
//    console.log(sendData);
//}

/*function inquiryProcess() {

	var formData = new FormData();
	formData.append("inquiry_upload_files",$("input[name=inquiry_upload_files]")[0].files[0]);
	
	var sendData = JSON.stringify({inquiryTitle:$('#cs_inquiry_title').val(),inquiryContent:$('#cs_inquiry_content').val()}); 
        console.log(sendData);
        
 	$.ajax({
		type: "POST",
		url : "./inquiry_process.do",
		processData: false,
		data: sendData, formData,
		dataType: "json",
		contentType:"application/json;charset=UTF-8",
		async: true
	
		
	});
}*/


function inquriyUpdateProcess() {

	var form = new FormData();
	
	form.append("upload_files",$("#upload_files")[0].files[0]);
	form.append('cs_inquiry_category_no', $('#cs_inquiry_select').val());
	form.append('cs_inquiry_title', $('#cs_inquiry_title').val());
	form.append('cs_inquiry_content', $('#cs_inquiry_content').val());
	form.append('cs_inquiry_no', $('#cs_inquiry_no').val());
	form.append('upload_delete_key',$('#delete_key').val());
	
	console.log($('#cs_inquiry_select').val());
	
 	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url : "/clientservice/myInquiry_update_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
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

