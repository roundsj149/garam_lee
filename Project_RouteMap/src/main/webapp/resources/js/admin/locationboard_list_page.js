window.onload = locationProcess();

//검색바 클릭 시 검색카테고리,검색어 ajax로 넘기면서 호출!
function search() {
	var searchVal = $('#search_category').val();
	console.log(searchVal);
	var searchWord = $('#location_search_input').val();
	console.log(searchWord);
	
	locationProcess(1, searchVal, searchWord);
	
}

//여행지,맛집 프로세스 ajax 호출
function locationProcess(page, searchVal, searchWord) {
	
	$.ajax({
		
		type: "get",
		url: "/admin/locationboard_list_process.do",
		data: {'currPage' : page, 'searchVal' : searchVal, 'searchWord' : searchWord},
		dataType: "json",
    	success:function(result){
    		if(result) {
    			locationList(result.locationList);
    			setLocationListPagi(result);
    		}
    	}
	});
	
}

//여행지,맛집 리스트 조회
function locationList(data) {
	
	if(data.locationboardVo == null) {
		var	html =	'<tr>' +
						'<td class="text-center" colspan="8">데이터가 없습니다.</td>' +
					'</tr>';
			
			$('#locationList').html(html);
		}
	
	var html = '';
	
	$.each(data,function(index,value) {
		
		var milsec = value.locationboardVo.locationboard_writedate;
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
					'<td>' + value.locationboardVo.locationboard_no + '</td>' +
					'<td>' + value.locationCategoryVo.location_category_name + '</td>' +
					'<td>' + value.provinceCategoryVo.province_category_name + '</td>' +
					'<td><a target="_blank" href="/locationboard/lc_read_content_page.do?locationboard_no=' + value.locationboardVo.locationboard_no + '">' + value.locationboardVo.locationboard_title + '</a></td>' +
					'<td>' + value.locationLike + '</td>' +
					'<td>' + value.locationMember + '</td>' +
					'<td>' + writeDate + '</td>' +
					'<td>' +
						'<select class="input-group">' +
							'<option value="1">정상</option>' +
							'<option value="2">블라인드</option>' +
							'<option value="3">삭제</option>' +
						'</select>' +
					'</td>' +
				'</tr>';
			
		$('#locationList').html(html);
	})
}

//페이징처리하기
function setLocationListPagi(data) {
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
			'<a class="page-link" href="javascript:void(0)" onclick="locationProcess(' + (data.pageVo.startPageGroup-1) + ')"><i class="fas fa-angle-double-left"></i></a>' + 
		'</li>' +
		// 이전
		'<li class="page-item ' + preCls + '">' + 
			'<a class="page-link" href="javascript:void(0)" onclick="locationProcess(' + (data.pageVo.currentPage - 1) + ')"><i class="fas fa-angle-left"></i></a>' + 
		'</li>';
	
	for (var i = data.pageVo.startPageGroup; i <= data.pageVo.endPageGroup; i++) {
		
		var pageCls = '';
		if (data.pageVo.currentPage == i) {
			pageCls = 'active';
		}
		
		html += 
			'<li class="page-item ' + pageCls + '">' + 
				'<a class="page-link" href="javascript:void(0)" onclick="locationProcess(' + i + ')">' + i + '</a>' + 
			'</li>';
	}

	html +=
		'<li class="page-item ' + nextCls + '"><a class="page-link" href="javascript:void(0)" onclick="locationProcess(' + data.pageVo.currentPage + 1 + ')"><i class="fas fa-angle-right"></i></a></li>' + 
		'<li class="page-item ' + nextCls + '"><a class="page-link" href="javascript:void(0)" onclick="locationProcess(' + data.pageVo.totalRecordsCount + ')"><i class="fas fa-angle-double-right"></i></a></li>';
	
	$('#cs_notice_pagination').html(html);
}