$(document).ready(function() {
	//문의제목 정규식 입력방지
	var replaceTitle = /[<>()'"{}#$%&*+_=/|`\-\[\]\\]/gi;

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
    
    noticeSearchProcess();
});

function noticeSearchProcess(page) {
	var noticeSearchTitle = $('#noticeSearchTitle').val(); // 검색어
	var form = new FormData();
	
	// form.append("noticeSearchTitle",noticeSearchTitle ? noticeSearchTitle : ""); // 검색어
	// form.append("currPage", page ? page : 1); // 페이지
	
	var form = {
		"noticeSearchTitle": noticeSearchTitle ? noticeSearchTitle : "",
		"currPage": page ? page : 1
	};
	
 	$.ajax({
		type: "post",
		url : "/clientservice/notice_page_list.do",
		data: JSON.stringify(form),
		dataType: "json",
		contentType:false,
		enctype: 'multipart/form-data',
		async: true,
		processData: false,
		success: function(res, status, xhr){
			if (res) {
				if (res.returnCode == '0000') {
					setNoticeList(res.data);
					setNoticeListPagination(res.page);
				} else {
					alert(res.returnMsg);
				}
			}
		}
	});
} // end of function noticeSearchProcess

function setNoticeList(data) {
	var html = '';
	
	if (data) {
		$.each(data, function(i, e) {
			html += 
				'<tr>' +
					'<td class="text-center">' + e.notice_no + '</td>' + 
					'<td class="text-left">' + 
						'<a href="/clientservice/noticeContent_page.do?notice_no=' + e.notice_no + '">' + e.notice_title + '</a>' + 
					'</td>' + 
					'<td class="text-center">' + e.notice_writedate + '</td>' +
				'</tr>';
		});
	} 
	// 데이터가 없을 경우
	else {
		html = "<tr><td colspan=\"3\">조회된 데이턱 없습니다</td><tr>"
	}

	$('#notice_list').html(html);
}

function setNoticeListPagination(data) {
	var html = '';
	var preCls = '';
	var nextCls = '';
	if (data.currentPage <= 1) {
		preCls = 'disabled';
	}
	
	if (data.currentPage >= data.totalPageCount) {
		nextCls = 'disabled';
	}
	
	html += 
		// 제일 이전 
		'<li class="page-item ' + preCls + '">' + 
			'<a class="page-link" href="javascript:void(0)" onclick="noticeSearchProcess(' + (data.startPageGroup-1) + ')"><i class="fas fa-angle-double-left"></i></a>' + 
		'</li>' +
		// 이전
		'<li class="page-item ' + preCls + '">' + 
			'<a class="page-link" href="javascript:void(0)" onclick="noticeSearchProcess(' + (data.currentPage - 1) + ')"><i class="fas fa-angle-left"></i></a>' + 
		'</li>';
	
	for (var i = data.startPageGroup; i <= data.endPageGroup; i++) {
		var pageCls = '';
		if (data.currentPage == i) {
			pageCls = 'active';
		}
		
		html += 
			'<li class="page-item ' + pageCls + '">' + 
				'<a class="page-link" href="javascript:void(0)" onclick="noticeSearchProcess(' + i + ')">' + i + '</a>' + 
			'</li>';
	}

	html +=
		'<li class="page-item ' + nextCls + '"><a class="page-link" href="javascript:void(0)" onclick="noticeSearchProcess(' + (data.currentPage + 1) + ')"><i class="fas fa-angle-right"></i></a></li>' + 
		'<li class="page-item ' + nextCls + '"><a class="page-link" href="javascript:void(0)" onclick="noticeSearchProcess(' + data.totalRecordsCount + ')"><i class="fas fa-angle-double-right"></i></a></li>';
	
	$('#cs_notice_pagination').html(html);
}
//# sourceURL=notice_page_sample.js