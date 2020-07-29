window.onload = function() {
	
	allList(1);
}

// (1-1) 내가 쓴 전체 게시물 -자동 로드
function allList(pageInfo) {
	
	var currPage = 1;	// 페이지 초기값
	var count = 0;	// 게시물 수
	
	if (pageInfo == undefined) {
		pageInfo = 1;
	}
	
	currPage = pageInfo;
	
		$.ajax({
			url: "fr_my_location_process.do",
			type:"get",
			data:{"currPage":currPage},
			dataType:"json",
			success:function(result){
				
				if(result == "") {
					var noResult = '<div style="margin-top:50px; margin-left:50px">작성한 게시물이 없습니다.</div>';
					$('#boardList').html(noResult);
					allPageNavigation(1, 1);
					return;	
				}
				
				var str='';
				
				for(data of result) {
					
					str += '<div class="row">';
					str += '<div class="col card-deck list-box-size mt-3">';
					str += '<div class="card tong">';
					str += '<img src="" class="card-img-top" alt="">';
					str += '<div class="card-body">';
					str += '<h5 class="card-title list-box-overflow">';
					str += '<a class="custom-link" href="/locationboard/lc_read_content_page.do?locationboard_no='+data.locationboard_no+'">'+data.locationboard_title+'</a></h5>'
					str += '<p class="card-text list-box-overflow">'+data.locationboard_content+'</p></div>'
					str += '<div class="card-body padding"><p class="button">';
					str += '<a class="custom-link" href="/locationboard/lc_update_content_page.do?locationboard_no='+data.locationboard_no+'">수정</a>';
					str += '<a href="javascript:void(0)" id="'+data.locationboard_no+'" onclick="delete_check(this.id); return false">&emsp;삭제</a></p></div>';	
					str += '<div class="card-footer">';					
					var milliseconds = data.locationboard_writedate;
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
			        min = min >= 10 ? min : '0' + min;           // mm 두자리로
			        var writeDate = year+"."+month+"."+day+" "+hour+":"+min;
					str += '<small class="text-muted">'+writeDate+'</small></div></div></div></div>'; 
					count++;
					
					allPageNavigation(count, currPage);
				}
				$('#boardList').html(str);
		    }
		});

}

//(1-2) 전체게시물 페이징
function allPageNavigation(count, currPageInfo) {
	
	$('#page_navi').empty();
	var currPage = currPageInfo;
	var beginPage = parseInt((currPage - 1) / 5) * 5 + 1;	// 6, 7일 때 6
	var endPage = parseInt((currPage - 1) / 5) * 5 + 5;	// 6, 7일 때 10
	
	var totalCount = count;
	
	if(endPage >= parseInt(totalCount/12)+1) {
		if(totalCount%12 == 0) {
			endPage = parseInt(totalCount/12);
		} else {
			endPage = parseInt(totalCount/12)+1;
		}

	}
	
	$('#page_navi').append('<li id="beginPage" class="page-item"><a class="page-link" onclick="allList('+(beginPage-1)+')"aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
	if(beginPage == 1) {
		$('#beginPage').addClass("disabled");
	}
	for(var i=beginPage; i<= endPage; i++) {
		$('#page_navi').append('<li id="currPage_'+i+'" class="page-item"><a class="page-link" href="javascript:void(0);" onclick="allList('+i+')">'+i+'</a></li>');
		if(i == currPage) {
			$('#currPage_'+i+'').addClass("active");
		}
	}
	$('#page_navi').append('<li id="endPage" class="page-item"><a class="page-link" onclick="allList('+(endPage+1)+')"aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
	var lastPage = parseInt((totalCount-1)/12)+1;
	if(endPage >= lastPage) {
		$('#endPage').addClass("disabled");
	}
	
}


// (2-1) 내가 쓴 전체 게시물 - 전체 버튼 클릭 시
$(function() {
	$('#getMyAllLocation').click(function() {
		var select = document.querySelectorAll(".btn-success");
		select[0].setAttribute("class","button-size btn btn-outline-success");
		$("#getMyAllLocation").removeClass("btn-outline-success").addClass("btn-success");
		allList(1);
		
	});
});

// (3-1) 내가 쓴 여행지 게시물 - 여행지 또는 맛집 버튼 클릭 시
	function categoryList(category_no, pageInfo) {
		
		var location_category_no = category_no;
		var currPage = 1;	// 페이지 초기값
		var count = 0;	// 게시물 수
		if(category_no==1) {
			
			var select = document.querySelectorAll(".btn-success");
			select[0].setAttribute("class","button-size btn btn-outline-success");
			$("#getMySightLocation").removeClass("btn-outline-success").addClass("btn-success");
			
		} else {
			
			var select = document.querySelectorAll(".btn-success");
			select[0].setAttribute("class","button-size btn btn-outline-success");
			$("#getMyFoodLocation").removeClass("btn-outline-success").addClass("btn-success");
			
		}
		if (pageInfo == undefined) {
			pageInfo = 1;
		}
		
		currPage = pageInfo;
		
		$.ajax({
			url: "fr_my_sight_food_process.do?location_category_no="+location_category_no,
			type:"get",
			data:{"currPage":currPage},
			dataType:"json",
			success:function(result){
				if(result == "") {
					var noResult = '<div style="margin-top:50px; margin-left:50px">작성한 게시물이 없습니다.</div>';
					$('#boardList').html(noResult);
					categoryPageNavigation(1, 1);
					return;	
				}
				var str='';
				
				for(data of result) {
					str += '<div class="row">';
					str += '<div class="col card-deck list-box-size mt-3">';
					str += '<div class="card tong">';
					str += '<img src="" class="card-img-top" alt="">';
					str += '<div class="card-body">';
					str += '<h5 class="card-title list-box-overflow">';
					str += '<a class="custom-link" href="/locationboard/lc_read_content_page.do?locationboard_no='+data.locationboard_no+'">'+data.locationboard_title+'</a></h5>'
					str += '<p class="card-text list-box-overflow">'+data.locationboard_content+'</p></div>'
					str += '<div class="card-body padding"><p class="button">';
					str += '<a href="/locationboard/lc_update_content_page.do?locationboard_no='+data.locationboard_no+'">수정</a>';
					str += '<a href="javascript:void(0)" id="'+data.locationboard_no+'" onclick="delete_check(this.id); return false">&emsp;삭제</a></p></div>';	
					str += '<div class="card-footer">';					
					var milliseconds = data.locationboard_writedate;
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
			        min = min >= 10 ? min : '0' + min;           // mm 두자리로
			        var writeDate = year+"."+month+"."+day+" "+hour+":"+min;
					str += '<small class="text-muted">'+writeDate+'</small></div></div></div></div>'; 
					count++;
					
					categoryPageNavigation(count, currPage);
				}
				$('#boardList').html(str);
		    }
		});
		
}


//(3-2) 내가 쓴 여행지 게시물 - 여행지 또는 맛집 버튼 클릭 시 페이징
function categoryPageNavigation(count, currPageInfo) {
	
	$('#page_navi').empty();
	var currPage = currPageInfo;
	var beginPage = parseInt((currPage - 1) / 5) * 5 + 1;	// 6, 7일 때 6
	var endPage = parseInt((currPage - 1) / 5) * 5 + 5;	// 6, 7일 때 10
	
	var totalCount = count;
	
	if(endPage >= parseInt(totalCount/12)+1) {
		if(totalCount%12 == 0) {
			endPage = parseInt(totalCount/12);
		} else {
			endPage = parseInt(totalCount/12)+1;
		}

	}
	
	$('#page_navi').append('<li id="beginPage" class="page-item"><a class="page-link" onclick="categoryList('+(beginPage-1)+')"aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
	if(beginPage == 1) {
		$('#beginPage').addClass("disabled");
	}
	for(var i=beginPage; i<= endPage; i++) {
		$('#page_navi').append('<li id="currPage_'+i+'" class="page-item"><a class="page-link" href="javascript:void(0);" onclick="categoryList('+i+')">'+i+'</a></li>');
		if(i == currPage) {
			$('#currPage_'+i+'').addClass("active");
		}
	}
	$('#page_navi').append('<li id="endPage" class="page-item"><a class="page-link" onclick="categoryList('+(endPage+1)+')"aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
	var lastPage = parseInt((totalCount-1)/12)+1;
	if(endPage >= lastPage) {
		$('#endPage').addClass("disabled");
	}
	
}


//글 삭제 확인
function delete_check(locationboard_no) {

	if (confirm("정말 삭제하시겠습니까?")) { //확인

		location.href = "fr_my_locationboard_delete_process.do?locationboard_no="
				+ locationboard_no;

	} else { //취소

		return false;

	}
}
