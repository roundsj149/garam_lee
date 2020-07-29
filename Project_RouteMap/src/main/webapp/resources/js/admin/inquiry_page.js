window.onload = inquiryList(0);

//정규식 입력방지
var replaceTitle = /[<>()'"{}#$%&*+_=/|`\-\[\]\\]/gi;

//자주묻는질문 검색바 정규식 입력방지
$(document).ready(function() {
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
});

//검색바 엔터키 눌러서 검색하기
function enterKey() {
    if (window.event.keyCode == 13) {

         // 엔터키가 눌렸을 때 실행할 내용
    	$('#faq_search_submit').click();
    }
}

//검색, 카테고리클릭 할때 리스트출력
function inquiryList(categoryNo, page, searchWord) {
	
	if(categoryNo == undefined) {
		var categoryNo = "0";
	}
	
	if(searchWord !== "") {
		searchWord = $('#faq_search_input').val();
		console.log(searchWord);
	}
	
	var categoryVal = 0;
	
	$.ajax({
		url: "/admin/inquiry_process.do",
    	type:"get",
    	data: {'currPage' : page, 'categoryNo' : categoryNo, 'searchWord' : searchWord, 'categoryVal' : categoryVal},
    	dataType:"json",
    	success:function(result){
    		
    		setInquiryList(result.list);
    		
    	}
	});
			
}

//게시물 출력하기
function setInquiryList(data) {
	
	var html = '';
	
	if(data) {
		$.each(data, function(index,value) {
			console.log("호출 성공");
			
			var milsec = value.inquiryVo.inquiry_writedate;
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
			
			html += 
				'<tr>' +
					'<td class="text-center">' + value.inquiryVo.inquiry_no + '</td>' +
					'<td class="text-center">' + value.categoryName + '</td>' +
					'<td class="text-left">' + '<a href="/admin/inquiry_content_page.do?inquiryNo=' + value.inquiryVo.inquiry_no + '">' + value.inquiryVo.inquiry_title + '</a></td>' +
					'<td class="text-center">' + value.memberName + '</td>' +
					'<td class="text-center">' + writedate + '</td>' +
					'<td class="text-center">' + value.inquiryVo.inquiry_answer_key + '</td>' +
				'</tr>';
		});
	
	} else {
		console.log("마지막호출 실패");
		html += "<p>조회된 데이터가없습니다</p>";
	}
	
	console.log("마지막호출 실패");
	$('#inquiry_list').html(html);
}

//페이징처리하기
function setPagi(data) {
	var html = '';
	var preCls = '';
	var nextCls = '';
	if (data.pageVo.currentPage <= 1) {
		preCls = 'disabled';
	}
	
	if (data.pageVo.currentPage >= data.pageVo.totalPageCount) {
		nextCls = 'disabled';
	}
	
	html += 
		// 제일 이전 
		'<li class="page-item ' + preCls + '">' + 
			'<a class="page-link" href="javascript:void(0)" onclick="faqList(' + data.faqCtgNo + ',' + (data.pageVo.startPageGroup-1) + ')"><i class="fas fa-angle-double-left"></i></a>' + 
		'</li>' +
		// 이전
		'<li class="page-item ' + preCls + '">' + 
			'<a class="page-link" href="javascript:void(0)" onclick="faqList(' + data.faqCtgNo + ',' + (data.pageVo.currentPage - 1) + ')"><i class="fas fa-angle-left"></i></a>' + 
		'</li>';
	
	for (var i = data.pageVo.startPageGroup; i <= data.pageVo.endPageGroup; i++) {
		
		var pageCls = '';
		if (data.pageVo.currentPage == i) {
			pageCls = 'active';
		}
		
		html += 
			'<li class="page-item ' + pageCls + '">' + 
				'<a class="page-link" href="javascript:void(0)" onclick="faqList(' + data.faqCtgNo + ',' + i + ')">' + i + '</a>' + 
			'</li>';
	}

	html +=
		'<li class="page-item ' + nextCls + '"><a class="page-link" href="javascript:void(0)" onclick="faqList(' + data.faqCtgNo + ',' + data.pageVo.currentPage + 1 + ')"><i class="fas fa-angle-right"></i></a></li>' + 
		'<li class="page-item ' + nextCls + '"><a class="page-link" href="javascript:void(0)" onclick="faqList(' + data.faqCtgNo + ',' + data.pageVo.totalRecordsCount + ')"><i class="fas fa-angle-double-right"></i></a></li>';
	
	$('#cs_notice_pagination').html(html);
}

