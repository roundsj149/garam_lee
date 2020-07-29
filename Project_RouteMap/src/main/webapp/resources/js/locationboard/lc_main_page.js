
// 상세검색 드롭박스
$(document).on('click', '.dropdown-menu', function (e) {
    e.stopPropagation();
});

$("#confirmButton").on('click', function() {
	$(".dropdown-menu").attr("class","dropdown-menu dropdown-menu-form");
});


window.onload = function() {
	
	// 전체 메인페이지에서 검색해서 넘어왔을 경우를 위해서 검색어를 받아줘야함
	var search_word;
	var _tempUrl = window.location.search.substring(1); // url에서 처음부터 '?'까지 삭제
	var _tempArray = _tempUrl.split('&'); // '&'을 기준으로 분리하기
	var _keyValuePair = _tempArray[0].split('='); // '=' 을 기준으로 분리하기
		
		if(_keyValuePair[0] == 'search_word'){ // _keyValuePair[0] : 파라미터 명
			// _keyValuePair[1] : 파라미터 값
			search_word = decodeURI(decodeURIComponent(_keyValuePair[1]));
			// 처음 로드될 때는 페이지 정보가 없으므로 1로 기본설정
			var pageInfo = 1;
	}
	allList(search_word, pageInfo);
	like_check();
}

// 글쓰기 버튼 눌렀을 때 로그인 여부 확인
function checkLogin(session) {
	var sessionCheck = session;
	if(sessionCheck == false) {
		alert('로그인 후 이용해주세요');
		location.href = "../member/login_page.do?uri=locationboard/lc_main_page.do";
		return false;
	} else {
		location.href = 'lc_write_content_page.do';
		return true;
	}
}

// (1-1) 메인페이지 로딩 시 전체 게시물 나오기

function allList(search_word, pageInfo) {
	
	var currPage = 1;	// 페이지 초기값
	var count = 0;	// 게시물 수
	
	if (pageInfo == undefined) {
		pageInfo = 1;
	}
	
	currPage = pageInfo;
	
	$.ajax({
    	url: "/locationboard/lc_main_process.do",
    	type:"post",
    	data: {"currPage":currPage, "searchWord":search_word},
    	dataType:"json",
    	
    	success:function(result){
    		// 가져오는 값이 없을 때
			if(result == "") {
				if(search_word == undefined) {
					var noResult = '<h5>게시물이 존재하지 않습니다.<h5>';
					$('#boardList').html(noResult);

					searchPageNavigation(1, 1);
					return;	
				} else {
					var noResult = '<h5>검색 결과가 없습니다.<h5>';
					$('#boardList').html(noResult);
					
					searchPageNavigation(1, 1);
					return;	
				}
			}
    	var str='';
    	//alert(result.length);
    	for(data of result) {
    		//alert(result[result.length-1].locationboardVo.locationboard_no);
    		var far='';
        	if(data.myFarVo != null) {
        		far = 'fas fa-flag myfavorite fa-lg';
        	} else {
        		far = 'far fa-flag myfavorite fa-lg';
        	}
    		
    		// 글목록 시작
    		str += '<div class="row">';
    		str += '<div class="col card-deck list-box-size mt-3">';
    		str += '<div class="card tong overflow-hidden">';
    		if(data.locationboardUploadFileVo != null) {
    		str += '<a href="/locationboard/lc_read_content_page.do?locationboard_no='+data.locationboardVo.locationboard_no+'"><img src="../../routemap/storage/'+data.locationboardUploadFileVo.locationboard_file_link_path+'" class="card-img-top" alt="이미지를 불러올 수 없습니다." style="height:200px"></a>';
    		}
    		str += '<div class="card-body pb-0">';
    		str += '<h5 class="card-title list-box-overflow">';
    		str += '<a class="custom-link" href="/locationboard/lc_read_content_page.do?locationboard_no='+data.locationboardVo.locationboard_no+'">'+data.locationboardVo.locationboard_title+'</a></h5>'
    		str += '<i id="'+data.locationboardVo.locationboard_no+'" class="far fa-heart fa-lg" style="color: #f06595;cursor: pointer" onclick="like_func(this.id)"></i>&nbsp;&nbsp;';
    		str += '<i class="'+far+'" id="favorite_'+data.locationboardVo.locationboard_no+'" style="color: green;cursor: pointer" onclick ="favorite_check(this.id)"></i>';
    		str += '</div>'
    		str += '<div class="card-footer" style="display:flex; align-items: center; justify-content: space-between;">';
    		var milliseconds = data.locationboardVo.locationboard_writedate;
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
    		var writeDate = year+"."+month+"."+day+" "+hour+":"+min;
    		var writeDay = ""+year+month+day+hour+min;
    		
    		// 오늘날짜
    		var today = new Date();
    		var thisYear = today.getFullYear(); // 년도
    		thisYear = thisYear.toString();
    		thisYear = thisYear.substring(2, 4);            // yy 두자리로 저장
    		var thisMonth = today.getMonth() + 1;  // 월
    		thisMonth = thisMonth >= 10 ? thisMonth : '0' + thisMonth;
    		var thisDay = today.getDate();  // 날짜
    		thisDay = thisDay >= 10 ? thisDay : '0' + thisDay;
    		var thisHour = today.getHours();            // h
    		thisHour = thisHour >= 10 ? thisHour : '0' + thisHour;      // hh
																		// 두자리로
																		// 저장
    		var thisMin = today.getMinutes();           // m
    		thisMin = thisMin >= 10 ? thisMin : '0' + thisMin;           // mm
																			// 두자리로
																			// 저장
    		var now = ""+thisYear+thisMonth+thisDay+thisHour+thisMin;
    		
    		str += '<small class="text-muted">'+writeDate+'</small>'; 
    		if(now - writeDay <30) {
    			str += '<span class="badge badge-pill badge-danger" style="float:right">NEW</span>';
    		}
    		str += '</div></div></div></div>';
    		count = data.count;
    		if(search_word == undefined) {
    			$('#countList').text('총 '+count+'건의 게시물이 있습니다.');
    		} else {
    			$('#countList').text('총 '+count+'건의 게시물이 검색되었습니다.');
    		}
    		
    		allPageNavigation(count, currPage, search_word);
    		}
    	
    	$('#boardList').html(str);
    	like_check();
    	}
	});
}

// (1-2) 전체게시물 페이징
function allPageNavigation(count, currPageInfo, search_word) {
	
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
	
	$('#page_navi').append('<li id="beginPage" class="page-item"><a class="page-link" onclick="allList('+search_word+','+(beginPage-1)+')"aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
	if(beginPage == 1) {
		$('#beginPage').addClass("disabled");
	}
	for(var i=beginPage; i<= endPage; i++) {
		$('#page_navi').append('<li id="currPage_'+i+'" class="page-item"><a class="page-link" href="javascript:void(0);" onclick="allList('+search_word+','+i+')">'+i+'</a></li>');
		if(i == currPage) {
			$('#currPage_'+i+'').addClass("active");
		}
	}
	$('#page_navi').append('<li id="endPage" class="page-item"><a class="page-link" onclick="allList('+search_word+','+(endPage+1)+')"aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
	var lastPage = parseInt((totalCount-1)/12)+1;
	if(endPage >= lastPage) {
		$('#endPage').addClass("disabled");
	}
	
}


// 체크박스 전체 클릭 시 전체선택/해제
$(function(){
	$("#checkAllProvince").click(function(){ // 만약 전체 선택 체크박스가 체크된상태일경우
 		if($("#checkAllProvince").prop("checked")) { // 해당화면에 전체 checkbox들을
														// 체크해준다
 			$("input[name=province_category_no]").prop("checked",true); 
		} else { // 해당화면에 모든 checkbox들의 체크를해제시킨다.
 			$("input[type=checkbox]").prop("checked",false); 
		} 
	});
	
	// 전체 클릭 시 체크박스 전체 선택 및 해제
	$("#checkAllCost").click(function(){ // 만약 전체 선택 체크박스가 체크된상태일경우
 		if($("#checkAllCost").prop("checked")) { // 해당화면에 전체 checkbox들을 체크해준다
 			$("input[name=select_cost]").prop("checked",true); // 전체선택 체크박스가
																// 해제된 경우
		} else { // 해당화면에 모든 checkbox들의 체크를해제시킨다.
 			$("input[name=select_cost]").prop("checked",false); 
		} 
	});
	
	$("#checkAllType").click(function(){ // 만약 전체 선택 체크박스가 체크된상태일경우
 		if($("#checkAllType").prop("checked")) { // 해당화면에 전체 checkbox들을 체크해준다
 			$("input[name=select_type]").prop("checked",true); // 전체선택 체크박스가
																// 해제된 경우
		} else { // 해당화면에 모든 checkbox들의 체크를해제시킨다.
 			$("input[name=select_type]").prop("checked",false); 
		} 
	});
	
	$("#checkAllTime").click(function(){ // 만약 전체 선택 체크박스가 체크된상태일경우
 		if($("#checkAllTime").prop("checked")) 
 		{ // 해당화면에 전체 checkbox들을 체크해준다
 			$("input[name=select_time]").prop("checked",true); 
 			// 전체선택 체크박스가 해제된 경우
		} else { // 해당화면에 모든 checkbox들의 체크를해제시킨다.
 			$("input[name=select_time]").prop("checked",false); 
		}
	});
	
	$("#checkAllMood").click(function(){ // 만약 전체 선택 체크박스가 체크된상태일경우
 		if($("#checkAllMood").prop("checked")) { // 해당화면에 전체 checkbox들을 체크해준다
 			$("input[name=select_mood]").prop("checked",true); // 전체선택 체크박스가
																// 해제된 경우
		} else { // 해당화면에 모든 checkbox들의 체크를해제시킨다.
 			$("input[name=select_mood]").prop("checked",false); 
		}
	});
	
	// 지역 - 하나라도 체크 해제하면 전체체크 해제하기
	$("input[class=province_class]").click(function() {
		var provinceNum = $("input[class=province_class]");	// 체크박스 개수
		var provinceArr = [];
		
		for(var i=0; i<provinceNum.length; i++) {
			if(provinceNum[i].checked == false) {
				$("#checkAllProvince").prop("checked", false);
			}
		}
	});
	
	// 비용 - 하나라도 체크 해제하면 전체체크 해제하기
	$("input[class=cost_class]").click(function() {
		var costNum = $("input[class=cost_class]");	// 체크박스 개수
		var costArr = [];
		
		for(var i=0; i<costNum.length; i++) {
			if(costNum[i].checked == false) {
				$("#checkAllCost").prop("checked", false);
			}
		}
	});
	
	// 유형 - 하나라도 체크 해제하면 전체체크 해제하기
	$("input[class=type_class]").click(function() {
		var typeNum = $("input[class=type_class]");	// 체크박스 개수
		var typeArr = [];
		
		for(var i=0; i<typeNum.length; i++) {
			if(typeNum[i].checked == false) {
				$("#checkAllType").prop("checked", false);
			}
		}
	});
	
		// 소요시간 - 하나라도 체크 해제하면 전체체크 해제하기
		$("input[class=time_class]").click(function() {
			var timeNum = $("input[class=time_class]");	// 체크박스 개수
			var timeArr = [];
			
			for(var i=0; i<timeNum.length; i++) {
				if(timeNum[i].checked == false) {
					$("#checkAllTime").prop("checked", false);
				}
			}
		});
	
	// 스타일 - 하나라도 체크 해제하면 전체체크 해제하기
	$("input[class=mood_class]").click(function() {
		var moodNum = $("input[class=mood_class]");	// 체크박스 개수
		var moodArr = [];
		
		for(var i=0; i<moodNum.length; i++) {
			if(moodNum[i].checked == false) {
				$("#checkAllMood").prop("checked", false);
			}
		}
	});
});


// (2-1) 지역 - 체크박스 체크 시 이벤트
$(document).ready(function(){
    $("input[name=province_category_no]").change(function(){
    	
    	// 체크박스 초기화
    	$("input:checkbox[id='checkAllCost']").prop("checked", false);
    	$("input:checkbox[id='checkAllType']").prop("checked", false);
    	$("input:checkbox[id='checkAllTime']").prop("checked", false);
    	$("input:checkbox[id='checkAllMood']").prop("checked", false);
    	$("input:checkbox[name='select_cost']").prop("checked", false);
    	$("input:checkbox[name='select_type']").prop("checked", false);
    	$("input:checkbox[name='select_time']").prop("checked", false);
    	$("input:checkbox[name='select_mood']").prop("checked", false);
    	// 검색어 초기화
    	$("#search_word").val('');
    	provinceList();

    });
});

function provinceList(pageInfo) {
	
	var currPage = 1;	// 페이지 초기값
	var count = 0;	// 게시물 수

	if (pageInfo == undefined) {
		pageInfo = 1;
	}
	
	currPage = pageInfo;
	
	var provinceCheckboxNum = document.getElementsByName("province_category_no");
	var provinceCheckboxArr = [];
	
	// 체크된 값 배열에 담기
	for(var i=0; i<provinceCheckboxNum.length; i++) {
		
		if(provinceCheckboxNum[i].checked) {
			provinceCheckboxArr.push(provinceCheckboxNum[i].value);
		}
	}
	
		$.ajax({
			url: "/locationboard/lc_main_process.do?currPage="+currPage,
			type:"get",
			data: {"provinceCheckboxArr": provinceCheckboxArr, "currPage":currPage},
			dataType:"json",
			success:function(result){
				// 검색결과 없을 때
				if(result == "") {
					var noResult = '<div>검색 결과가 없습니다.<div>';
					$('#boardList').html(noResult);
					$('#countList').text('');
					searchPageNavigation(1, 1);
					return;
				}
				var str='';
				
				for(data of result) {
					
					var far='';
					if(data.myFarVo != null) {
		        		far = 'fas fa-flag myfavorite fa-lg';
		        	} else {
		        		far = 'far fa-flag myfavorite fa-lg';
		        	}
					
		        	str += '<div class="row">';
		    		str += '<div class="col card-deck list-box-size mt-3">';
		    		str += '<div class="card tong overflow-hidden">';
		    		if(data.locationboardUploadFileVo != null) {
		    		str += '<a href="/locationboard/lc_read_content_page.do?locationboard_no='+data.locationboardVo.locationboard_no+'"><img src="../../routemap/storage/'+data.locationboardUploadFileVo.locationboard_file_link_path+'" class="card-img-top" alt="이미지를 불러올 수 없습니다." style="height:200px"></a>';
		    		}
		    		str += '<div class="card-body pb-0">';
		    		str += '<h5 class="card-title list-box-overflow">';
		    		str += '<a class="custom-link" href="/locationboard/lc_read_content_page.do?locationboard_no='+data.locationboardVo.locationboard_no+'">'+data.locationboardVo.locationboard_title+'</a></h5>'
		    		str += '<i id="'+data.locationboardVo.locationboard_no+'" class="far fa-heart fa-lg" style="color: #f06595;cursor: pointer" onclick="like_func(this.id)"></i>&nbsp;&nbsp;';
		    		str += '<i class="'+far+'" id="favorite_'+data.locationboardVo.locationboard_no+'" style="color: green;cursor: pointer" onclick ="favorite_check(this.id)"></i>';
		    		str += '</div>'
		    		str += '<div class="card-footer">';
		    		var milliseconds = data.locationboardVo.locationboard_writedate - 1000*60*60*9;
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
																	// 저장
			        var writeDate = year+"."+month+"."+day+" "+hour+":"+min;
			        var writeDay = ""+year+month+day+hour+min;
		    		
		    		// 오늘날짜
		    		var today = new Date();
		    		var thisYear = today.getFullYear(); // 년도
		    		thisYear = thisYear.toString();
		    		thisYear = thisYear.substring(2, 4);            // yy 두자리로
																	// 저장
		    		var thisMonth = today.getMonth() + 1;  // 월
		    		thisMonth = thisMonth >= 10 ? thisMonth : '0' + thisMonth;
		    		var thisDay = today.getDate();  // 날짜
		    		thisDay = thisDay >= 10 ? thisDay : '0' + thisDay;
		    		var thisHour = today.getHours();            // h
		    		thisHour = thisHour >= 10 ? thisHour : '0' + thisHour;      // hh
																				// 두자리로
																				// 저장
		    		var thisMin = today.getMinutes();           // m
		    		thisMin = thisMin >= 10 ? thisMin : '0' + thisMin;           // mm
																					// 두자리로
																					// 저장
		    		
		    		var now = ""+thisYear+thisMonth+thisDay+thisHour+thisMin;
		    		
		    		if(now - writeDay <1) {
		    			str += '<span class="badge badge-pill badge-danger">NEW</span>'
		    		}
					str += '<small class="text-muted">'+writeDate+'</small></div></div></div></div>'; 
					count = data.count;
					$('#countList').text('총 '+count+'건의 게시물이 검색되었습니다.');
					provincePageNavigation(count, currPage);
				}
				$('#boardList').html(str);
				like_check();
		    }
		});
	}

	// (2-2) 지역 - 체크박스 체크 페이징
	function provincePageNavigation(count, currPageInfo) {
	
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
		
		$('#page_navi').append('<li id="beginPage" class="page-item"><a class="page-link" onclick="provinceList('+search_word+','+(beginPage-1)+')"aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
		if(beginPage == 1) {
			$('#beginPage').addClass("disabled");
		}
		for(var i=beginPage; i<= endPage; i++) {
			$('#page_navi').append('<li id="currPage_'+i+'" class="page-item"><a class="page-link" href="javascript:void(0);" onclick="provinceList('+i+')">'+i+'</a></li>');
			if(i == currPage) {
				$('#currPage_'+i+'').addClass("active");
			}
		}
		$('#page_navi').append('<li id="endPage" class="page-item"><a class="page-link" onclick="allList('+search_word+','+(endPage+1)+')"aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
		var lastPage = parseInt((totalCount-1)/12)+1;
		if(endPage >= lastPage) {
			$('#endPage').addClass("disabled");
		}
	}

	// (3-1) 검색버튼 눌렀을 떄 - 여행지or맛집 + 지역검색 + 상세검색 + 검색어
	
	function searchList(pageInfo) {
		
		var searchWord = $('#search_word').val();	// 검색어
		var currPage = 1;	// 페이지 초기값
    	var count = 0;	// 게시물 수
    	
    	if (pageInfo == undefined) {
    		pageInfo = 1;
    	}
    	
    	currPage = pageInfo;
		
    	// 지역 체크된 값 가져오기
    	var provinceCheckboxNum = $('input[name=province_category_no]');
																			
    	var provinceCheckboxArr = [];
    	
    	for(var i=0; i<provinceCheckboxNum.length; i++) {
    		
    		if(provinceCheckboxNum[i].checked) {
    			provinceCheckboxArr.push(provinceCheckboxNum[i].value);
    			// alert(provinceCheckboxNum[i].value);
    		}
    	}
    	
    	// 여행지 or 맛집값
    	var categoryNum = $('#category').val();
		
    	// 비용 - 체크박스 체크된 값 가져오기
		var costCheckboxNum = $('input[name=select_cost]');	// 체크박스 체크된 값 가져오기
    	var costCheckboxArr = [];
    	
    	// 비용 -체크된 값 배열에 담기
    	for(var i=0; i<costCheckboxNum.length; i++) {
    		
    		if(costCheckboxNum[i].checked) {
    			costCheckboxArr.push(costCheckboxNum[i].value);
    		}
    	}
    	
    	// 유형 - 체크박스 체크된 값 가져오기
		var typeCheckboxNum = $('input[name=select_type]');	// 체크박스 체크된 값 가져오기
    	var typeCheckboxArr = [];
    	
    	// 유형 -체크된 값 배열에 담기
    	for(var i=0; i<typeCheckboxNum.length; i++) {
    		
    		if(typeCheckboxNum[i].checked) {
    			typeCheckboxArr.push(typeCheckboxNum[i].value);
    		}
    	}
    	
    	// 소요시간 - 체크박스 체크된 값 가져오기
		var timeCheckboxNum = $('input[name=select_time]');	// 체크박스 체크된 값 가져오기
    	var timeCheckboxArr = [];
    	
    	// 소요시간 -체크된 값 배열에 담기
    	for(var i=0; i<timeCheckboxNum.length; i++) {
    		
    		if(timeCheckboxNum[i].checked) {
    			timeCheckboxArr.push(timeCheckboxNum[i].value);
    		}
    	}
    	
    	// 스타일 - 체크박스 체크된 값 가져오기
		var moodCheckboxNum = $('input[name=select_mood]');	// 체크박스 체크된 값 가져오기
    	var moodCheckboxArr = [];
    	
    	// 스타일 -체크된 값 배열에 담기
    	for(var i=0; i<moodCheckboxNum.length; i++) {
    		
    		if(moodCheckboxNum[i].checked) {
    			moodCheckboxArr.push(moodCheckboxNum[i].value);
    		}
    	}
    	
    	// 정렬 ㅜㅜ
    	var alignNum = $('#list_align').val(); 
    	
		$.ajax({
			url: "/locationboard/lc_main_process.do?currPage="+currPage,
			type:"get",
			data: {"provinceCheckboxArr": provinceCheckboxArr, "categoryNum":categoryNum, "costCheckboxArr": costCheckboxArr, "typeCheckboxArr": typeCheckboxArr, "timeCheckboxArr": timeCheckboxArr, "moodCheckboxArr": moodCheckboxArr, "alignNum":alignNum, "searchWord":searchWord, "currPage":currPage},
			dataType:"json",
			success:function(result){
				
				// 검색결과 없을 때
				if(result == "") {
					var noResult = '<div>검색 결과가 없습니다.<div>';
					$('#boardList').html(noResult);
					$('#countList').text('');
					searchPageNavigation(1, 1);
					return;
				}
				var str='';
				
				for(data of result) {
					
					var far='';
					if(data.myFarVo != null) {
		        		far = 'fas fa-flag myfavorite fa-lg';
		        	} else {
		        		far = 'far fa-flag myfavorite fa-lg';
		        	}
					
		        	str += '<div class="row">';
		    		str += '<div class="col card-deck list-box-size mt-3">';
		    		str += '<div class="card tong overflow-hidden">';
		    		if(data.locationboardUploadFileVo != null) {
		    		str += '<a href="/locationboard/lc_read_content_page.do?locationboard_no='+data.locationboardVo.locationboard_no+'"><img src="../../routemap/storage/'+data.locationboardUploadFileVo.locationboard_file_link_path+'" class="card-img-top" alt="이미지를 불러올 수 없습니다." style="height:200px"></a>';
		    		}
		    		str += '<div class="card-body pb-0">';
		    		str += '<h5 class="card-title list-box-overflow">';
		    		str += '<a class="custom-link" href="/locationboard/lc_read_content_page.do?locationboard_no='+data.locationboardVo.locationboard_no+'">'+data.locationboardVo.locationboard_title+'</a></h5>'
		    		str += '<i id="'+data.locationboardVo.locationboard_no+'" class="far fa-heart fa-lg" style="color: #f06595;cursor: pointer" onclick="like_func(this.id)"></i>&nbsp;&nbsp;';
		    		str += '<i class="'+far+'" id="favorite_'+data.locationboardVo.locationboard_no+'" style="color: green;cursor: pointer" onclick ="favorite_check(this.id)"></i>';
		    		str += '</div>'
		    		str += '<div class="card-footer">';
		    		var milliseconds = data.locationboardVo.locationboard_writedate - 1000*60*60*9;
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
																	// 저장
			        var writeDate = year+"."+month+"."+day+" "+hour+":"+min;
			        var writeDay = ""+year+month+day+hour+min;
		    		
		    		// 오늘날짜
		    		var today = new Date();
		    		var thisYear = today.getFullYear(); // 년도
		    		thisYear = thisYear.toString();
		    		thisYear = thisYear.substring(2, 4);            // yy 두자리로
																	// 저장
		    		var thisMonth = today.getMonth() + 1;  // 월
		    		thisMonth = thisMonth >= 10 ? thisMonth : '0' + thisMonth;
		    		var thisDay = today.getDate();  // 날짜
		    		thisDay = thisDay >= 10 ? thisDay : '0' + thisDay;
		    		var thisHour = today.getHours();            // h
		    		thisHour = thisHour >= 10 ? thisHour : '0' + thisHour;      // hh
																				// 두자리로
																				// 저장
		    		var thisMin = today.getMinutes();           // m
		    		thisMin = thisMin >= 10 ? thisMin : '0' + thisMin;           // mm
																					// 두자리로
																					// 저장
		    		
		    		var now = ""+thisYear+thisMonth+thisDay+thisHour+thisMin;
		    		
		    		if(now - writeDay <1) {
		    			str += '<span class="badge badge-pill badge-danger">NEW</span>'
		    		}
					str += '<small class="text-muted">'+writeDate+'</small></div></div></div></div>'; 					
					count = data.count;
					$('#countList').text('총 '+count+'건의 게시물이 검색되었습니다.');
					searchPageNavigation(count, currPage);
				}
				
				$('#boardList').html(str);
				like_check();
		    }, 
		    
		});
	}
		
	// (3-2) 검색버튼 눌렀을 떄 - 지역검색 + 상세검색 + 검색어 페이징
	function searchPageNavigation(count, currPageInfo) {
		
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
		
		$('#page_navi').append('<li id="beginPage" class="page-item"><a class="page-link" onclick="searchList('+search_word+','+(beginPage-1)+')"aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
		if(beginPage == 1) {
			$('#beginPage').addClass("disabled");
		}
		for(var i=beginPage; i<= endPage; i++) {
			$('#page_navi').append('<li id="currPage_'+i+'" class="page-item"><a class="page-link" href="javascript:void(0);" onclick="searchList('+i+')">'+i+'</a></li>');
			if(i == currPage) {
				$('#currPage_'+i+'').addClass("active");
			}
		}
		$('#page_navi').append('<li id="endPage" class="page-item"><a class="page-link" onclick="searchList('+search_word+','+(endPage+1)+')"aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
		var lastPage = parseInt((totalCount-1)/12)+1;
		if(endPage >= lastPage) {
			$('#endPage').addClass("disabled");
		}
	}
	
	// 로드 시 좋아요
	function like_check() {
		    	
		var session_check = document.getElementById("session_check").value;
		
		if(session_check == "false") {
			return;
		} else {
		
			var xmlhttp = new XMLHttpRequest();
						
			xmlhttp.onreadystatechange = function() {
			
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var resultData = JSON.parse(xmlhttp.responseText);
	
					for(var data of resultData){
									
						var aa = data.locationboard_no;
						var like_icon = document.getElementById(aa);
						if(like_icon != null) {
							like_icon.setAttribute("class","fas fa-heart fa-lg");
						
						}
					}
				}
			}
						
		xmlhttp.open("post", "/locationboard/like_all_process.do", true);
		xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xmlhttp.send();
					
		};
	}

	// 좋아요, 좋아요 취소
    function like_func(clicked_id) {
    	
    	var session_check = document.getElementById("session_check").value;
    	if(session_check == "false") {
    			
    			alert('로그인 후 이용해주세요.');
        		location.href = "../member/login_page.do?uri=locationboard/lc_main_page.do";
    		
    	} else {
    		var locationboard_no = clicked_id;
        	var like_icon = document.getElementById(clicked_id);
        	var xmlhttp = new XMLHttpRequest();
        		
        	xmlhttp.onreadystatechange = function() {
        			
        		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    				var resultData = JSON.parse(xmlhttp.responseText);
    				
        			if(resultData.value == 'true') {	// 좋아요 이미 함 -> 빈 하트로
														// 바뀌어야 함
        				like_icon.setAttribute("class","far fa-heart fa-lg");
        				
        			} else {	// 좋아요한 적 없음 -> 빨간 하트로 바뀌어야 함
        				like_icon.setAttribute("class","fas fa-heart fa-lg");
        			}
    				like_check();
        		}
        	};
        		
        	xmlhttp.open("post", "/locationboard/like_process.do", true);
    		xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    		xmlhttp.send("locationboard_no=" + locationboard_no);
    	}
    }

 // 관심 확인
    function favorite_check(clicked_id) {
    	
    	var session_check = document.getElementById("session_check").value;
    	
    	if(session_check == "false") {
    		    			
    			alert('로그인 후 이용해주세요.');
    			location.href = "../member/login_page.do?uri=locationboard/lc_main_page.do";
    		
    	}else {

    		var form = new FormData();
    		var clickId = clicked_id;
    		var splitId = clickId.split('_');
    		
    		
    		
    		form.append("locationboard_no",splitId[1])
    		
    	}
    	
        $.ajax({
    		type	: "post", // 요청 메소드 타입
    		url		: "/locationboard/favorite_process.do", // 요청 경로
    		data  : form, // 요청 시 포함되어질 데이터
    		async : true,
    		processData : false, // 데이터를 컨텐트 타입에 맞게 변환 여부
    		cache : false, // 캐시 여부
    		contentType : false, // 요청 컨텐트 타입
									// "application/x-www-form-urlencoded;
									// charset=UTF-8"
    		dataType	: "json", // 응답 데이터 형식 명시하지 않을 경우 자동으로 추측
    		beforeSend  : function(){
    		// XHR Header를 포함해서 HTTP Request를 하기전에 호출됩니다.
    		},
    		success	: function(data, status, xhr){

    			if(data.returnCode == '0001') {
    			$('#favorite_' + data.locationboard_no).removeClass("far fa-flag myfavorite fa-lg").addClass("fas fa-flag myfavorite fa-lg");
    			}
    			
    			else if (data.returnCode == '0002') {
    				$('#favorite_' + data.locationboard_no).removeClass("fas fa-flag myfavorite fa-lg").addClass("far fa-flag myfavorite fa-lg");
    			}
    			
    		// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
    		// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
    		},
    		error	: function(xhr, status, error){
    		// 응답을 받지 못하였다거나 정상적인 응답이지만 데이터 형식을 확인할 수 없기 때문에 error 콜백이 호출될 수
			// 있습니다.
    		// 예를 들어, dataType을 지정해서 응답 받을 데이터 형식을 지정하였지만, 서버에서는 다른 데이터형식으로 응답하면
			// error 콜백이 호출되게 됩니다.
    		},
    		always : function(xhr, status){
    		// success와 error 콜백이 호출된 후에 반드시 호출됩니다.
    		// try - catch - finally의 finally 구문과 동일합니다.
    		}
    	});
    }