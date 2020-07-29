window.onload = memberProcess();

//검색바 엔터키 눌러서 검색하기
function enterKey() {
    if (window.event.keyCode == 13) {

         // 엔터키가 눌렸을 때 실행할 내용
    	test();
    }
}

//검색바 클릭 시 검색카테고리,검색어 ajax로 넘기면서 호출!
function test() {
	var searchVal = $('#search_category').val();
	console.log(searchVal);
	var searchWord = $('#member_search_input').val();
	console.log(searchWord);
	
	memberProcess(1, searchVal, searchWord)
	
}

//회원 리스트 출력 ajax
function memberProcess(page, searchVal, searchWord) {
	
	$.ajax({
		url: "/admin/member_list_process.do",
    	type:"get",
    	data: {'currPage' : page, 'searchVal' : searchVal, 'searchWord' : searchWord},
    	dataType:"json",
    	success:function(result){
    		if(result) {
    			memberList(result.memberList);
    			setMemberListPagi(result);
    		}
    	}
	});
			
}

//회원 리스트 출력!
function memberList(data) {

	if(data.memberVo == null) {
	var	html =	'<tr>' +
					'<td class="text-center" colspan="8">데이터가 없습니다.</td>' +
				'</tr>';
		
		$('#member_List').html(html);
	}
	
	var html = '';
	
	$.each(data,function(index,value) {
		
		var member_status = '';
		
		//멤버 상태 변환
		if(value.memberVo.member_status == 'Y') {
			
			member_status += '<option value="1" actived>활동중</option>' +
							 '<option value="2">정지</option>' +
							 '<option value="3">탈퇴</option>';
			
		} else if(value.memberVo.member_status == 'N') {
			
			member_status += '<option value="2">정지</option>' +
							 '<option value="1">활동중</option>' +
							 '<option value="3">탈퇴</option>';
			
		} else {
			
			member_status += '<option value="3">탈퇴</option>' +
							 '<option value="1">활동중</option>' +
							 '<option value="2">정지</option>';
		}
		
		//핸드폰 번호 변환
		var firstNumber = value.memberVo.member_phone.substring(0,3);
		var secondNumber = value.memberVo.member_phone.substring(3,7);
		var finalNumber = value.memberVo.member_phone.substring(7,11);
		var phoneNumber = firstNumber + '-' + secondNumber + '-' + finalNumber;
		
		//날짜변환
		var milsec = value.memberVo.member_joindate;
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
		var joindate = year + '.' + month + '.' + day;
		
		html += '<tr>' +
					'<td>' + value.memberVo.member_no + '</td>' +
					'<td><div class="badge badge-pill badge-danger">LV.' + value.levelVo.level_category_no + '</div></td>' +
					'<td>' + value.memberVo.member_id + '</td>' +
					'<td>' + value.memberVo.member_fullname + '</td>' +
					'<td>' + value.memberVo.member_nickname + '</td>' +
					'<td>' + phoneNumber + '</td>' +
					'<td>' + joindate + '</td>' +
					'<td>' +
						'<select class="input-group selectbox" id="memberStatus_' + value.memberVo.member_no + '">' +
						member_status +
						'</select>' +
					'</td>' +
				'</tr>';

		
		$('#member_List').html(html);
		
		//상태값 변경 시 ajax 호출
		$('.selectbox').change(function() {
			
			var id = $(this).attr('id');
			var memberNo = id.split('_');
			
			var statusVal = $(this).val();
			alert("");
			
			setMemberStatus(memberNo[1],statusVal);
		});
	
	});
	
}

//회원 리스트 페이징!!

//페이징처리하기
function setMemberListPagi(data) {
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
			'<a class="page-link" href="javascript:void(0)" onclick="memberProcess(' + (data.pageVo.startPageGroup-1) + ')"><i class="fas fa-angle-double-left"></i></a>' + 
		'</li>' +
		// 이전
		'<li class="page-item ' + preCls + '">' + 
			'<a class="page-link" href="javascript:void(0)" onclick="memberProcess(' + (data.pageVo.currentPage - 1) + ')"><i class="fas fa-angle-left"></i></a>' + 
		'</li>';
	
	for (var i = data.pageVo.startPageGroup; i <= data.pageVo.endPageGroup; i++) {
		
		var pageCls = '';
		if (data.pageVo.currentPage == i) {
			pageCls = 'active';
		}
		
		html += 
			'<li class="page-item ' + pageCls + '">' + 
				'<a class="page-link" href="javascript:void(0)" onclick="memberProcess(' + i + ')">' + i + '</a>' + 
			'</li>';
	}

	html +=
		'<li class="page-item ' + nextCls + '"><a class="page-link" href="javascript:void(0)" onclick="memberProcess(' + data.pageVo.currentPage + 1 + ')"><i class="fas fa-angle-right"></i></a></li>' + 
		'<li class="page-item ' + nextCls + '"><a class="page-link" href="javascript:void(0)" onclick="memberProcess(' + data.pageVo.totalRecordsCount + ')"><i class="fas fa-angle-double-right"></i></a></li>';
	
	$('#cs_notice_pagination').html(html);
}
 
//회원들 상태 값 변경하기 ajax!
function setMemberStatus(memberNo,statusVal) {
	
	var form = new FormData();
	
	form.append('memberNo', memberNo);
	form.append('statusVal',statusVal);
	
	$.ajax({
		
		type: "POST",
		enctype: 'multipart/form-data',
		url: "/admin/member_status_process.do",
		processData: false,
		data: form,
		contentType:false,
		dataType: "json",
		async: true,
    	success:function(result){
    		if(result) {
    			
    		}
    	}
	});
	
}


