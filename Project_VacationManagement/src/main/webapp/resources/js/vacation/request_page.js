$(function(){
	// date format 설정
	function getFormatDate(date) {
	    var year = date.getFullYear();              // yyyy
	    var month = (1 + date.getMonth());          // MM
	    month = month >= 10 ? month : '0' + month;  // 1~9월 두 자리로
	    var day = date.getDate();                   // dd
	    day = day >= 10 ? day : '0' + day;          // 1~9일 두 자리로
	    return  year + "-" + month + "-" + day;       //'-' 추가하여 yyyy-MM-dd 포맷으로
	}
	
	var currDate = new Date();
	
	// 신청하려는 날이 토요일인 경우 +2, 일요일인 경우 +1 해서 휴가 시작일이 돌아오는 월요일로 자동 설정 되게 함
	if(currDate.getDay() == 6) {
		currDate.setDate(currDate.getDate()+2);
		console.log(currDate);
	} else if(currDate.getDay() == 0) {
		currDate.setDate(currDate.getDate()+1);
		console.log(currDate);
	}
	
	// text box 표시
	$("#startDate").val(getFormatDate(currDate));
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
	// 시작일을 오늘 날짜로 자동 선택
	$("#startDate").datepicker("setDate", currDate);
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
			$("#endDate").datepicker("setStartDate",currDate);
			
		}
	});	
	
	$("#endDate").datepicker({
		format : "yyyy-mm-dd", // 달력에서 클릭시 표시할 값 형식
		//endDate: "+2m",
		todayHighlight: true,
		todayBtn: 'linked',
		daysOfWeekDisabled : [0,6],
		autoclose:true,
		datesDisabled : ['2020-12-25','2020-12-31', '2021-01-01', '2021-02-11', '2021-02-12', '2021-03-01', '2021-05-05', '2021-05-19', '2021-09-20', '2021-09-21', '2021-09-22', '2021-12-31']
	})
	.on("changeDate", function(e) {
		var maxDate = new Date(e.date.valueOf());
		$("#startDate").datepicker("setEndDate",maxDate);
		if($("#startDate").val() == $("#endDate").val()) {
			$("#endRadioBtn").css("visibility","hidden");
		} else {
			$("#endRadioBtn").css("visibility","visible");
			
		}
	});
	
	// 신청하기 버튼 눌렀을 때 동작
	$("#requestBtn").on("click", requestProcess);
		
	// 신청하기 버튼 눌렀을 때 호출할 함수
	function requestProcess() {
	
		if($("input:radio[name=startType]:checked").length==0) {
			$('.modal').modal('show');
			return;
		}
		if(($("input:radio[name=startType]:checked").val() != "MORNING") && ($("#startDate").val() != $("#endDate").val()) && $("input:radio[name=endType]:checked").length==0) {
			$('.modal').modal('show');
			return;
		}

		var formData = {"startDt": $("#startDate").val(),
						"startType": $("input:radio[name=startType]:checked").val(),
						"endDt": $("#endDate").val(),
						"endType": $("input:radio[name=endType]:checked").val()
						};
		
		$.ajax({
            url : "/vacation/request_process",
            type : 'POST',
			contentType:"application/json",
            data : JSON.stringify(formData),
            dataType:"json",
            success : function(data) {
				console.log("휴가 신청 결과: "+data.baseResult.returnCode);
				if(data.baseResult.returnCode == "S10001") {
					location.href = "/vacation/list";
				} else if(data.baseResult.returnCode == "F11111") {
					console.log("에러");
					location.href = "/main/errorPage";
				} else {
					console.log("에러 코드: "+data.baseResult.returnCode);
				}
            },
			error : function(xhr, status) {
                document.write(xhr + " : " + status);
            }
		});		
		
	}
	
});












