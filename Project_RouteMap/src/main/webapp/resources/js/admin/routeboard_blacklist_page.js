var totalCount = 0;
var newCount = 0;
var currPage = 1;

//로드 시 목록
$(function() {
	routeBlacklist();
});

//2초마다 한번씩 목록 확인
setInterval(function() {
	
	if(totalCount == 0) {
		totalCount = newCount;
	}
	routeBlacklistCount();
	
	if(totalCount < newCount) {
		noticeRoutePopupInit();
		routeBlacklist(currPage);
		totalCount = newCount;
	}
}, 2000);


//새 신고 있을 때 알림 띄우기
var noticeRoutePopupTimer = setTimeout(function(){}, 1);

function noticeRoutePopupInit() {
	
 var element = $("#report");
 
 element.css({
     'position':'fixed',
     'bottom':'-200px',
     'right':'5px',
     'padding':'10px',
     'font-size':'12px',
     'width':'250px',
     'height':'80px',
     'border-width':'1px 1px 0 1px',
     'border-color':'#aaaaaa',
     'border-style':'solid',
     'background-color': '#ffffff',
 });
 
 element.html('<div class="col"><img src="/resources/img/map.png" width="50px" height="60px" style="float:left"></div><div class="col">새로운  <strong>루트공유 게시물 신고</strong>가 접수되었습니다</div>');
 var link = '<a href="/admin/routeboard_blacklist_page.do"></a>';
 element.wrap(link);
 // Clear Animation
 element.stop();
 clearTimeout(noticeRoutePopupTimer);
 // Start Animation
 element.animate({'bottom':'0px'}, 'slow', function() {
	 noticeRoutePopupTimer = setTimeout(function() {
		 element.animate({'bottom':'-200px'}, 'slow');
	 }, 10000);
 });
};

// 1-1. 루트공유 게시물 신고 리스트
function routeBlacklist(curr_page) {
	
	var count = 0;
	
	if(curr_page == undefined) {
		currPage = 1;
	} else {
		currPage = curr_page;
	}
	
	$.ajax({
		type: "POST",
		url : "/admin/route_blacklist_process.do",
		data: {"currPage":currPage},
		success: function(result, status, xhr){
//			
//			if(result == "") {
//					var noResult = '<div>신고된 게시물이 존재하지 않습니다.<div>';
//					$('#example2').empty();
//					$('#example2').html(noResult);
//
//					navigation(1, 1);
//					return;	
//			}
			var str = '';
			
			for(data of result) {
				str+= '<tr>';
				str+= '<td>'+data.report_no+'</td>';
				str+= '<td>'+data.member_id+'</td>';
				str+= '<td>'+data.routeboard_no+'</td>';
				str+= '<td>'+data.routeboard_title+'</td>';
				var milliseconds = data.report_time;
				var date = new Date(milliseconds);
	    		var year = date.getFullYear();                // yyyy
	    		year = year.toString();
	    		year = year.substring(2, 4);            // yy 두자리로 저장
	    		var month = (1 + date.getMonth());          // M
	    		month = month >= 10 ? month : '0' + month;  // MM 두자리로 저장
	    		var day = date.getDate();                   // d
	    		day = day >= 10 ? day : '0' + day;          // dd 두자리로 저장
	    		var hour = date.getHours();            // h
	    		hour = hour >= 10 ? hour : '0' + hour;      // hh 두자리로 저장
	    		var min = date.getMinutes();           // m
	    		min = min >= 10 ? min : '0' + min;           // mm 두자리로 저장
	    		// var sec = date.getSeconds(); // s
	    		// sec = sec >= 10 ? sec : '0' + sec; // ss 두자리로 저장
	    		var reportDate = year+"."+month+"."+day+" "+hour+":"+min;
	    		var reportStatus = '';
	    		var reportClass = '';
	    		if(data.report_status == 'Y') {
	    			reportStatus = '확인';
	    			reportClass = 'badge badge-primary';
	    		} else {
	    			reportStatus = '미확인';
	    			reportClass = 'badge badge-secondary';
	    		
	    		}
				str+= '<td>'+reportDate+'</td>';
				str+= '<td>'+data.report_category_name+'</td>';
				str+= '<td style="text-align:center"><span id="checkReport_'+data.report_no+'" class="'+reportClass+'" style="cursor:pointer" onclick="changeStatus(this.id)">'+reportStatus+'</span></td>';
				str+= '</tr>';
				count = data.count;
				pageNavigation(count, currPage);
			}
			$("#route_blacklist").html(str);
			
		}
			// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
			// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
	});
	
}

// 1-2. 루트공유 게시물 신고 리스트 페이징
function pageNavigation(count, currPageInfo) {
	
	$('#route_blacklist_pagination').empty();
	var currPage = currPageInfo;
	var beginPage = parseInt((currPage - 1) / 5) * 5 + 1;	// 6, 7일 때 6
	var endPage = parseInt((currPage - 1) / 5) * 5 + 5;	// 6, 7일 때 10
	
	var totalCount = count;
	
	if(endPage >= parseInt(totalCount/10)+1) {
		if(totalCount%10 == 0) {
			endPage = parseInt(totalCount/10);
		} else {
			endPage = parseInt(totalCount/10)+1;
		}

	}
	
	$('#route_blacklist_pagination').append('<li id="beginPage" class="page-item"><a class="page-link" onclick="routeBlacklist('+(beginPage-1)+')"aria-label="Previous"><i class="fas fa-angle-left"></i></a></li>');
	if(beginPage == 1) {
		$('#beginPage').addClass("disabled");
	}
	for(var i=beginPage; i<= endPage; i++) {
		$('#route_blacklist_pagination').append('<li id="currPage_'+i+'" class="page-item"><a class="page-link" href="javascript:void(0);" onclick="routeBlacklist('+i+')">'+i+'</a></li>');
		if(i == currPage) {
			$('#currPage_'+i+'').addClass("active");
		}
	}
	$('#route_blacklist_pagination').append('<li id="endPage" class="page-item"><a class="page-link" onclick="routeBlacklist('+(endPage+1)+')"aria-label="Next"><i class="fas fa-angle-right"></i></a></li>');
	var lastPage = parseInt((totalCount-1)/10)+1;
	if(endPage >= lastPage) {
		$('#endPage').addClass("disabled");
	}
	
}

// 신고 확인여부 상태변환
function changeStatus(id) {
	var status;
	var report_no = id.split('_');
	
	// 확인->미확인
	if($("#"+id+"").text() == '확인') {
		$("#"+id+"").attr('class','badge badge-secondary');
		$("#"+id+"").text('미확인');
		status = 'N';
		
		//alert('확인->미확인'+report_no[1]);
		
	} else {
		
		// 미확인->확인
		$("#"+id+"").attr('class','badge badge-primary');
		$("#"+id+"").text('확인');
		status = 'Y';

	}
	
	$.ajax({
		type: "POST",
		url : "/admin/route_blacklist_check.do",
		data: {"report_no":report_no[1], "report_status": status},
		success: function(result, status, xhr){
			
			for(data of result) {
				
			}
		}
	});
}


// 루트공유 게시물 신고 게시물 수
function routeBlacklistCount() {
	
	var count = 0;
	
	$.ajax({
		type: "POST",
		url : "/admin/route_blacklist_count_process.do",
		success: function(result, status, xhr){
			
			count = result;
			
				newCount = count;
				
		}
	});
}














