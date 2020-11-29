
	// 연도 선택 박스
	var thisYear = new Date().getFullYear();
	var selectValue = "";
	var selectYear = $('<select></select>');
	
	for(var i=thisYear+1; i>thisYear-5; i--) {
		i == thisYear? selectValue += "<option value="+i+" selected>"+i+"</option>" : selectValue += "<option value="+i+">"+i+"</option>";
	}
	selectYear.append(selectValue);
	$("#menuBox").append(selectYear);
		
	// 초과근무 신청 목록 onload
	searchOvertime();
	
	// 연도 선택 변경 시
	var selectBox = $("select");
	selectBox.on("change", function() {
		
		searchOvertime();
	});

	// 세션 확인 후 관리자이면 검색 html 추가
	$.ajax({
		url : "/member/adminYN",
		type : 'POST', 
		success : function(data) {
			if(data.baseResult.returnCode == "S00007") {
				var searchBox = $('<div id="searchBox"></div>');
				var searchContent = $('<label>이름 </label><input type="text" id="searchForm" class="form-control"><input type="button" id="searchBtn" class="btn btn-outline-warning" value="검색">');
				$("#menuBox").append(searchBox.append(searchContent));
			} else if(data.baseResult.returnCode == "F11111") {
				console.log("에러");
				location.href = "/main/errorPage";
			} else {
				
			}
		},
		error : function(xhr, status) {
			document.write(xhr + " : " + status);
		}
	});	

	// 검색 버튼 클릭
	$(document).on("click", "#searchBtn", searchOvertime);
	
	// 엔터키 눌렀을 때 동작
	$(document).on("keypress", "#searchForm", enterCheck);

	// 엔터키 눌렀을 때 호출할 함수
	function enterCheck() {
		if(event.keyCode == 13) {
			// 검색 함수 호출
			searchOvertime();
		}
	}
	
	// 초과근무 신청 목록 호출
	function searchOvertime(pageInfo) {
		
		if(typeof(pageInfo) == "object") {
			pageInfo = undefined;
		}
		var searchWord = $("#searchForm").val();
		var str="";
		var allCount = 0;
		var currPage = pageInfo;
		
		if(currPage == undefined) {
			
			currPage = 1;
		}
		console.log("선택 연도: "+$("select").children("option:selected").val());
		// 총 게시물 수
		$.ajax({
			url : "/overtime/count_all_overtime",
			type : 'POST',
			contentType: "application/json",
			data : JSON.stringify({"searchWord":searchWord, "selectYear":$("select").children("option:selected").val()}),
			dataType:"json",
			success : function(data) {
				if(data.baseResult.returnCode == "S00000") {
					console.log("초과근무 총 게시물 수: "+data.returnData);
					allCount = data.returnData;
					// 초과근무 신청 목록
					$.ajax({
						url : "/overtime/search_overtime",
						type : 'POST', 
						contentType: "application/json",
						data : JSON.stringify({"searchWord":searchWord, "selectYear":$("select").children("option:selected").val(), "currPage":currPage}),
						dataType:"json",
						success : function(data) {
							console.log("조회 시작");
							for(result of data.returnData) {
								console.log("목록: "+result.applyDt);
								var applyNo = result.overtimeApplyNo;
								var startDate = getFormatDateTime(result.startDt);
								var endDate = getFormatDateTime(result.endDt);
								str += "<tr id="+applyNo+" class='vacation-list'><td>"+getFormatDate(result.applyDt)+"</td>";
								str += "<td>"+startDate+"</td>";
								str += "<td>"+endDate+"</td>";
								str += "<td>"+result.koreanCodeName+"</td>";
								str += "<td class='content-hidden'>"+result.overtimeContent+"</td>";
								str += "<td>"+result.fullname+"</td></tr>";
							}  
							$("tbody").html(str);
							console.log("페이징 전 전체 게시물 수: "+allCount+" 페이지: "+currPage);
							pageNavigation(allCount, currPage);
					    },
						error : function(xhr, status) {
							location.href="/vacation/list";
					    	
					    }
					});	
				} else {
					console.log("에러");
					location.href = "/main/errorPage";
				}
			}
		});
				
	}

	// 게시물 페이징
	function pageNavigation(count, currPageInfo) {
		$('#page_navi').empty();
		var currPage = currPageInfo;
		// 5 단위로 내비게이션 보이기
		var beginPage = parseInt((currPage-1)/5)*5+1;
		var endPage = parseInt((currPage-1)/5)*5+5;
		var totalCount = count;
		
		if(endPage >= parseInt(totalCount/10)+1) {
			if(totalCount%10 == 0) {
				endPage = parseInt(totalCount/10);
			} else {
				endPage = parseInt(totalCount/10)+1;
			}
		}
		
		$('#page_navi').append('<li id="beginPage" class="page-item"><a class="page-link" onclick="searchOvertime('+(beginPage-1)+')"aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
		if(beginPage == 1) {
			$('#beginPage').addClass("disabled");
		}
		for(var i=beginPage; i<= endPage; i++) {
			$('#page_navi').append('<li id="currPage_'+i+'" class="page-item"><a class="page-link" href="#" onclick="javascript:searchOvertime('+i+')">'+i+'</a></li>');
			if(i == currPage) {
				$('#currPage_'+i+'').addClass("active");
			}
		}
		$('#page_navi').append('<li id="endPage" class="page-item"><a class="page-link" onclick="searchOvertime('+(endPage+1)+')"aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
		var lastPage = parseInt((totalCount-1)/10)+1;
		if(endPage >= lastPage || endPage==0) {
			$('#endPage').addClass("disabled");
		}
	
	}
	
	// 휴가 선택
	$(document).on("click", ".vacation-list", function() {
		var applyNo = $(this).attr("id");
		var content = $(this).children(".content-hidden").html().replace(/\n/gi,"<br>");
		$(".modal p").html(content);
		$(".modal").modal("show");
		
	});
	
	// date format 설정(시간, 분까지)
	function getFormatDateTime(dateValue) {
		var date = new Date(dateValue);
		var year = date.getFullYear();              // yyyy
		var month = (1 + date.getMonth());          // MM
		month = month >= 10 ? month : '0' + month;  // 1~9월 두 자리로
		var day = date.getDate();                   // dd
		day = day >= 10 ? day : '0' + day;          // 1~9일 두 자리로
		var hour = date.getHours();
		hour = hour >= 10 ? hour : '0' + hour;      // hh 두자리로 저장
		var minute = date.getMinutes();
		minute = minute >= 10 ? minute : '0' + minute;           // mm 두자리로 저장
		return  year + "-" + month + "-" + day + " " + hour + ":" + minute;
	}
	
	// date format 설정(날짜만)
	function getFormatDate(dateValue) {
		var date = new Date(dateValue);
		var year = date.getFullYear();              // yyyy
		var month = (1 + date.getMonth());          // MM
		month = month >= 10 ? month : '0' + month;  // 1~9월 두 자리로
		var day = date.getDate();                   // dd
		day = day >= 10 ? day : '0' + day;          // 1~9일 두 자리로
		return  year + "-" + month + "-" + day;
	}