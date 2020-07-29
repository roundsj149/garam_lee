//정규식 입력방지
var replaceTitle = /[<>()'"{}#$%&*+_=/|`\-\[\]\\]/gi;

$(document).ready(function() {
	//자주묻는질문 collapse
	$('.faq_toggle').on('show.bs.collapse', function() {
		var faqNo = $(this).attr("id");
		$('.' + faqNo).addClass('fa-angle-up').removeClass('fa-angle-down');
	});

	$('.faq_toggle').on('hide.bs.collapse', function() {
		var faqNo = $(this).attr("id");
		$('.' + faqNo).addClass('fa-angle-down').removeClass('fa-angle-up');
	});
	
	//자주묻는질문 검색바 정규식 입력방지
	  $("#faq_search_input").on("focusout",function() {

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


	$(document).ready(function() {

	  

	});
	
// 즐겨찾기 클릭시 빨갛게 현상
/*	$('#test').change(function(e) {
		var letMem = jq('#').val(0); // 비회원인지 아닌지 
		if (letMem == 0) {
			jq(this).prop('checked', false);
			return false;
		}
		doClick();
	});*/
});