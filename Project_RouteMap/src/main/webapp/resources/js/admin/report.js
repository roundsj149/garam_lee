var totalMemberCount = 0;
var newMemberCount = 0;
var memberNCount = 0;
var totalLocationCount = 0;
var newLocationCount = 0;
var locationNCount = 0;
var totalRouteCount = 0;
var newRouteCount = 0;
var routeNCount = 0;

// 2초마다 한번씩 목록 확인
setInterval(function() {
	
	memberBlacklistCount();
	
	if(totalMemberCount == 0) {
		totalMemberCount = newMemberCount;
	}
	
	if(totalMemberCount < newMemberCount) {
		var msg = '<div class="col">새로운 <strong>사용자 신고</strong>가 접수되었습니다</div>';
		var link = '<a href="/admin/member_blacklist_page.do">';
		noticePopupInit(msg, link);
		totalMemberCount = newMemberCount;
	}
	
	locationBlacklistCount();
	
	if(totalLocationCount == 0) {
		totalLocationCount = newLocationCount;
	}
	
	
	if(totalLocationCount < newLocationCount) {
		var msg = '<div class="col">새로운  <strong>여행지/맛집 게시물 신고 </strong>가 접수되었습니다</div>';
		var link = '<a href="/admin/locationboard_blacklist_page.do">';
		noticePopupInit(msg, link);
		totalLocationCount = newLocationCount;
	}
	
	routeBlacklistCount();
	
	if(totalRouteCount == 0) {
		totalRouteCount = newRouteCount;
	}
	
	if(totalRouteCount < newRouteCount) {
		var msg = '<div class="col">새로운  <strong>루트공유 게시물 신고</strong>가 접수되었습니다</div>';
		var link = '<a href="/admin/routeboard_blacklist_page.do"></a>';
		noticePopupInit(msg, link);
		totalRouteCount = newRouteCount;
	}
	
}, 2000);

//1초마다 한번씩 미확인 수 확인
setInterval(function() {
	
	memberBlacklistNCount();
	$("#memberBadge").text(memberNCount);
	
	locationBlacklistNCount();
	$("#locationBadge").text(locationNCount);
	
	routeBlacklistNCount();
	$("#routeBadge").text(routeNCount);
	
}, 1000);

// 새 신고 있을 때 알림 띄우기
var noticePopupTimer = setTimeout(function(){}, 1);

function noticePopupInit(msg, link) {
	
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
     'background-color': '#ffffff'
 });
 var img = "<div class='col'><img src='/resources/img/map.png' width='50px' height='60px' style='float:left'></div>";
 element.html(img+msg);
 element.wrap(link);
 // Clear Animation
 element.stop();
 clearTimeout(noticePopupTimer);
 // Start Animation
 element.animate({'bottom':'0px'}, 'slow', function() {
	 noticePopupTimer = setTimeout(function() {
		 element.animate({'bottom':'-200px'}, 'slow');
	 }, 10000);
 });
};

// 사용자 신고 게시물 수
function memberBlacklistCount() {
	
	var count = 0;
	
	$.ajax({
		type: "POST",
		url : "/admin/member_blacklist_count_process.do",
		success: function(result, status, xhr){
			
			count = result;
			
			newMemberCount = count;
				
		}
	});
}

//사용자 신고 미확인 수
function memberBlacklistNCount() {
	
	var count = 0;
	
	$.ajax({
		type: "POST",
		url : "/admin/member_blacklist_n_count_process.do",
		success: function(result, status, xhr){
			
			count = result;
			
			memberNCount = count;
				
		}
	});
}


//여행지/맛집 신고 게시물 수
function locationBlacklistCount() {
	
	var count = 0;
	
	$.ajax({
		type: "POST",
		url : "/admin/location_blacklist_count_process.do",
		success: function(result, status, xhr){
			
			count = result;
			
			newLocationCount = count;
				
		}
	});
}

// 여행지/맛집 신고 미확인 수
function locationBlacklistNCount() {
	
	var count = 0;
	
	$.ajax({
		type: "POST",
		url : "/admin/location_blacklist_n_count_process.do",
		success: function(result, status, xhr){
			
			count = result;
			
			locationNCount = count;
				
		}
	});
}

//루트공유 게시물 신고 게시물 수
function routeBlacklistCount() {
	
	var count = 0;
	
	$.ajax({
		type: "POST",
		url : "/admin/route_blacklist_count_process.do",
		success: function(result, status, xhr){
			
			count = result;
			
			newRouteCount = count;
				
		}
	});
}


// 루트공유 게시물 신고 미확인 수
function routeBlacklistNCount() {
	
	var count = 0;
	
	$.ajax({
		type: "POST",
		url : "/admin/route_blacklist_n_count_process.do",
		success: function(result, status, xhr){
			
			count = result;
			
			routeNCount = count;
				
		}
	});
}








