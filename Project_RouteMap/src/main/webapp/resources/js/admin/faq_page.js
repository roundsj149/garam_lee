window.onload = faqList(0);

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
function faqList(faqCategoryNo, page, search) {
	
	if(faqCategoryNo == undefined) {
		var faqCategoryNo = "0";
	}
	
	if(search !== "") {
		search = $('#faq_search_input').val();
		console.log(search);
	}
	
	faqCategoryNo = faqCategoryNo;
	
	$.ajax({
		url: "/admin/faq_list.do?faqCategoryNo=" + faqCategoryNo,
    	type:"get",
    	data: {'currPage' : page, 'faqSearch' : search},
    	dataType:"json",
    	success:function(result){
    		
    		
    		
    		var html = '';
    		html += '<li id="faq_category0" class="btn-info" onclick="faqList(0,1)">전체</li>' +
    				'<li id="faq_category1" class="btn-info" onclick="faqList(1,1)">여행지/맛집</li>' +
    				'<li id="faq_category2" class="btn-info" onclick="faqList(2,1)">루트공유</li>' +
    				'<li id="faq_category3" class="btn-info" onclick="faqList(3,1)">나의관심/루트</li>' +
    				'<li id="faq_category4" class="btn-info" onclick="faqList(4,1)">자유게시판</li>' +
    				'<li id="faq_category5" class="btn-info" onclick="faqList(5,1)">계정관련</li>';
    		
    		$('#faq_category').html(html);
    		
    		if(result.faqCtgNo == 1) {
    			$('#faq_category1').removeClass().addClass('btn-primary');
    		} else if(result.faqCtgNo == 2) {
    			$('#faq_category2').removeClass().addClass('btn-primary');
    		} else if(result.faqCtgNo == 3) {
    			$('#faq_category3').removeClass().addClass('btn-primary');
    		} else if(result.faqCtgNo == 4) {
    			$('#faq_category4').removeClass().addClass('btn-primary');
    		} else if(result.faqCtgNo == 5) {
    			$('#faq_category5').removeClass().addClass('btn-primary');
    		} else {
    			$('#faq_category0').removeClass().addClass('btn-primary');
    		}
    		
    		//리스트가 없을때
    		if(result.faqList == "") {
    			
    			var html = '';
    			html += 
    				'<tr>' +
    					'<td></td>' +
    					'<td></td>' +
    					'<td class = "text-center">데이터가 없습니다.</td>' +
    					'<td></td>' +
    					'<td></td>' +
    				'</tr>';
    			
    			$('#faq_list').html(html);
    			
    			setFaqPagi(result)
    			
    			return;
    		}
    		
    		//리스트가 있을때
    		
    		$('#faq_search_input').attr('onkeyup','enterKey()');
    		$('#faq_search_submit').attr('onclick','faqList(' + result.faqCtgNo + ',1)');
    		
    		setFaqList(result.faqList);
    		setFaqPagi(result);
    	}
	});
			
}

//게시물 출력하기
function setFaqList(data) {
	
	var html = '';
	
	if(data) {
		$.each(data, function(index,value) {
			console.log("마지막호출 성공");
			
			var milsec = value.faqVo.faq_writedate;
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
					'<td class="text-center">' + value.faqVo.faq_no + '</td>' +
					'<td class="text-center">' + value.faqCtrVo.faq_category_name + '</td>' +
					'<td class="text-left">' + '<a href="/admin/faq_content_page.do?faqNo=' + value.faqVo.faq_no + '">' + value.faqVo.faq_title + '</a></td>' +
					'<td class="text-center">' + value.adminName + '</td>' +
					'<td class="text-center">' + writedate + '</td>' +
				'</tr>';
		});
	
	} else {
		console.log("마지막호출 실패");
		html += "<p>조회된 데이터가없습니다</p>";
	}
	
	console.log("마지막호출 실패");
	$('#faq_list').html(html);
}

//페이징처리하기
function setFaqPagi(data) {
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

