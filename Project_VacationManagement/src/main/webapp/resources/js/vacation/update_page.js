$(function() {

	// 날짜가 string타입이므로 date 타입으로 변환해주어야 함
	var strStartDate = $("#startDate").val();
	var strStartArr = strStartDate.split("-");
	var currStartDate = new Date(strStartArr[0], strStartArr[1]-1, strStartArr[2]);
	var strEndDate = $("#endDate").val();
	var strEndArr = strEndDate.split("-");
	var currEndDate = new Date(strEndArr[0], strEndArr[1]-1, strEndArr[2]);
	var calDate = (currEndDate.getTime() - currStartDate.getTime()) / (1000*60*60*24);
	var applyNo = $("#applyNo");

	// date format 설정
	function getFormatDate(date) {
	    var year = date.getFullYear();              //yyyy
	    var month = (1 + date.getMonth());          //M
	    month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
	    var day = date.getDate();                   //d
	    day = day >= 10 ? day : '0' + day;          //day 두자리로 저장
	    return  year + "-" + month + "-" + day;       //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
	}
	// 기존 시작일 불러오기
	$("#startDate").val(getFormatDate(currStartDate));
	
	// 시작일 달력 설정
	$("#startDate").datepicker({
		format : "yyyy-mm-dd", // 달력에서 클릭시 표시할 값 형식
		startDate : '-3d',
		todayHighlight: true,
		todayBtn: 'linked',
		daysOfWeekDisabled : [0,6],
		autoclose:true,
		datesDisabled : ['2020-12-25','2020-12-31', '2021-01-01', '2021-02-11', '2021-02-12', '2021-03-01', '2021-05-05', '2021-05-19', '2021-09-20', '2021-09-21', '2021-09-22', '2021-12-31']
	});

	// 기존 종료일 불러오기
	$("#endtDate").val(getFormatDate(currEndDate));
	
	$("#endDate").datepicker({
		format : "yyyy-mm-dd", // 달력에서 클릭시 표시할 값 형식
		//endDate: "+2m",
		todayHighlight: true,
		todayBtn: 'linked',
		daysOfWeekDisabled : [0,6],
		autoclose:true,
		datesDisabled : ['2020-12-25','2020-12-31', '2021-01-01', '2021-02-11', '2021-02-12', '2021-03-01', '2021-05-05', '2021-05-19', '2021-09-20', '2021-09-21', '2021-09-22', '2021-12-31']
	});

	// 기존 라디오 버튼 선택 및 종료일, 라디오 버튼 보이기 정보 가져오기
	if(strStartDate == strEndDate) {
		$("#endDate").css("visibility","visible");
		$("#endRadioBtn").css("visibility","hidden");
		if($("input:radio[name=startType]:checked").val() == "MORNING") {
			$("#endDate").prop("disabled",true);
		}
	} else if(calDate >= 1) {
		$("#endDate").css("visibility","visible");
		$("#endRadioBtn").css("visibility","visible");
	}

// 날짜 선택 시
	$("#startDate").on("changeDate", function(e) {
		currDate = new Date(e.date.valueOf());
		// 종료일 박스 및 일수 제거
		$("#endDate").val("").css("visibility","hidden");
		$("#countDate").text("");
		// 오전, 종일 선택 버튼 제거
		$("#endRadioBtn").css("visibility","hidden");
		// 날짜 선택 시 라디오 버튼 선택 해제(bootstrap 적용해서 active클래스 없애주어야 함)
		$("input:radio[name=startType]").prop("checked",false);
		$(".btn-group").find("label").removeClass("active");
	});

// 오전, 오후, 종일 선택 시
	$("input:radio[name=startType]").on("click", function() {
		// 오전 선택 시 종료일 및 타입 선택 못하게, 일수 0.5
		if($("input:radio[name=startType]:checked").val() == "MORNING") {
			$("#endRadioBtn").css("visibility","hidden");
			$("#endDate").prop("disabled",true).val($("#startDate").val());
			$("#endDate").css("visibility","visible");
			$("#countDate").text("0.5");
		// 오후, 종일 선택 시
		} else {
			$("#countDate").text("");
			$("#endDate").css("visibility","visible");
			$("#endDate").prop("disabled",false).val($("#startDate").val());
			$("#endDate").datepicker("setStartDate",currStartDate);
			
		}
	});	
	
	
	$("#endDate").on("changeDate", function(e) {
		var maxDate = new Date(e.date.valueOf());
		$("#startDate").datepicker("setEndDate",maxDate);
		if($("#startDate").val() == $("#endDate").val()) {
			$("#endRadioBtn").css("visibility","hidden");
		} else {
			$("#endRadioBtn").css("visibility","visible");
			
		}
	});

	// 수정하기 버튼 눌렀을 때 동작
	$("#updateBtn").on("click", updateProcess);
		
	// 수정하기 버튼 눌렀을 때 호출할 함수
	function updateProcess() {
		var formData = {"vacationApplyNo": applyNo.val(),
						"startDt": $("#startDate").val(),
						"startType": $("input:radio[name=startType]:checked").val(),
						"endDt": $("#endDate").val(),
						"endType": $("input:radio[name=endType]:checked").val()
						};
		$.ajax({
            url : "/vacation/request_process",
            type : 'POST',
			contentType : "application/json",
            data : JSON.stringify(formData),
            dataType:"json",
            success : function(data) {
				if(data.baseResult.returnCode == "S10001") {
					location.href = "/vacation/list";
				} else if(data.baseResult.returnCode == "F11111") {
					console.log("에러");
					location.href = "/main/errorPage";
				}else {
					console.log(data.baseResult.returnCode);
				}
            },
			error : function(xhr, status) {
                document.write(xhr + " : " + status);
            }
		});		
	}
});