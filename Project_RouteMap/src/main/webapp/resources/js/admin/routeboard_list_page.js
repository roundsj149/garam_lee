window.onload = routeProcess();

//검색바 클릭 시 검색카테고리,검색어 ajax로 넘기면서 호출!
function search() {
	var searchVal = $('#search_category').val();
	console.log(searchVal);
	var searchWord = $('#route_search_input').val();
	console.log(searchWord);
	
	routeProcess(1, searchVal, searchWord);
	
}

//루트공유 프로세스 ajax 호출
function routeProcess(page, searchVal, searchWord) {
	
	$.ajax({
		
		type: "get",
		url: "/admin/routeboard_list_process.do",
		data: {'currPage' : page, 'searchVal' : searchVal, 'searchWord' : searchWord},
		dataType: "json",
    	success:function(result){
    		if(result) {
    			routeList(result.routeList);
    			setRouteListPagi(result)
    		}
    	}
	});
	
}

//루트공유 리스트 조회
function routeList(data) {
	
	if(data.routeboardVo == null) {
		var	html =	'<tr>' +
						'<td class="text-center" colspan="8">데이터가 없습니다.</td>' +
					'</tr>';
			
			$('#RouteList').html(html);
		}
	
	var html = '';
	
	$.each(data,function(index,value) {
		
		var milsec = value.routeboardVo.routeboard_writedate;
		var date = new Date(milsec);
		var year = date.getFullYear();
		var month = 1 + date.getMonth();
		if(month < 10) {
			month = '0' + month
		}
		var day = date.getDate();
		if(day <10) {
			day = '0' + day
		}
		var writeDate = year + '.' + month + '.' + day;
		
		html += 
				'<tr>' +
					'<td>' + value.routeboardVo.routeboard_no + '</td>' +
					'<td><a target="_blank" href="/routeboard/rt_read_content_page.do?routeboard_no=' + value.routeboardVo.routeboard_no + '">' + value.routeboardVo.routeboard_title + '</a></td>' +
					'<td>' + value.routeLike + '</td>' +
					'<td>' + value.routeMember + '</td>' +
					'<td>' + writeDate + '</td>' +
					'<td>' + value.routeboardVo.routeboard_share +
					'<td>' +
						'<select class="input-group">' +
							'<option value="1">정상</option>' +
							'<option value="2">블라인드</option>' +
							'<option value="3">삭제</option>' +
						'</select>' +
					'</td>' +
				'</tr>';
			
		$('#RouteList').html(html);
	})
}

//페이징처리하기
function setRouteListPagi(data) {
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
			'<a class="page-link" href="javascript:void(0)" onclick="routeProcess(' + (data.pageVo.startPageGroup-1) + ')"><i class="fas fa-angle-double-left"></i></a>' + 
		'</li>' +
		// 이전
		'<li class="page-item ' + preCls + '">' + 
			'<a class="page-link" href="javascript:void(0)" onclick="routeProcess(' + (data.pageVo.currentPage - 1) + ')"><i class="fas fa-angle-left"></i></a>' + 
		'</li>';
	
	for (var i = data.pageVo.startPageGroup; i <= data.pageVo.endPageGroup; i++) {
		
		var pageCls = '';
		if (data.pageVo.currentPage == i) {
			pageCls = 'active';
		}
		
		html += 
			'<li class="page-item ' + pageCls + '">' + 
				'<a class="page-link" href="javascript:void(0)" onclick="routeProcess(' + i + ')">' + i + '</a>' + 
			'</li>';
	}

	html +=
		'<li class="page-item ' + nextCls + '"><a class="page-link" href="javascript:void(0)" onclick="routeProcess(' + data.pageVo.currentPage + 1 + ')"><i class="fas fa-angle-right"></i></a></li>' + 
		'<li class="page-item ' + nextCls + '"><a class="page-link" href="javascript:void(0)" onclick="routeProcess(' + data.pageVo.totalRecordsCount + ')"><i class="fas fa-angle-double-right"></i></a></li>';
	
	$('#cs_notice_pagination').html(html);
}