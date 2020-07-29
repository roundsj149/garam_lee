//글 작성 중 새로고침, 뒤로가기 등등 눌렀을 때 경고창 띄우기
var checkUnload = true;
$(window).on("beforeunload", function(){
    if(checkUnload) return "이 페이지를 벗어나면 작성된 내용은 저장되지 않습니다.";
});

$(function () {
	 // 아이디 유효성 검사
	 var id = $('#joinId');
	 var validationId = $('#validationId');
	 
	 id.blur(function() {
		 
		var idCheck = id.val();
		// alert(idCheck);
		// 검증에 사용할 정규식 변수 regExp에 저장
		var idRegExp = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		 
		 $.ajax({
		    	url: "/member/checkAdminId_process.do",
		    	type:"post",
		    	data: {"id": idCheck},
		    	dataType:"json",
		    	success:function(result){
		    		if(result == true) {	// 중복된 아이디가 없을 때
		    			if (idCheck == "") {
		    				 id.attr('class','form-control');
		    			 } else if (idCheck.match(idRegExp) != null) {
		    				 id.attr('class','form-control is-valid');
		    				 validationId.attr('class','valid-feedback');
		    				 validationId.text('사용 가능한 이메일입니다');
		    			 } else {
		    				id.attr('class','form-control is-invalid');
		    				validationId.attr('class','invalid-feedback');
		    				validationId.text('이메일 형식을 잘못 입력하셨습니다');
			    			
			    			return;
		    			 }
		    		} else if(result == false){	// 중복된 아이디가 있을 때
		    			id.attr('class','form-control is-invalid');
		    			validationId.attr('class','invalid-feedback');
	    				validationId.text('중복된 이메일입니다');
		    			return;
		    		}
		    	}
		 });
		 
	 });
	 
	 // 비밀번호 유효성 검사
	 var pw = $('#joinPw');
	 var validationPw = $('#validationPw');
	 
	 pw.keyup(function() {
		 
		 var pwCheck = pw.val();
		 var pwRegExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,15}$/;
		 pw2.keyup();
		 // 검증에 사용할 정규식 변수 regExp에 저장
		 if (pwCheck == "") {
			 pw.attr('class','form-control');
		 } else if (pwCheck.match(pwRegExp) != null) {
			 pw.attr('class','form-control is-valid');
			 validationPw.attr('class','valid-feedback');
			 validationPw.text('안전한 비밀번호입니다');
		  } else {
			  pw.attr('class','form-control is-invalid');
			  validationPw.attr('class','invalid-feedback');
			  validationPw.text('비밀번호는 영문,숫자,특수문자 포함 8~15자리로 입력하셔야합니다');
			  return;
		  }
	 });
	 
	 // 비밀번호 확인
	 var pw = $('#joinPw');
	 var pw2 = $('#joinPw2');
	 var validationPw2 = $('#validationPw2');
	 
	 pw2.keyup(function() {
		 
		 var pwCheck = pw.val();
		 var pw2Check = pw2.val();

		 // 검증에 사용할 정규식 변수 regExp에 저장
		 if (pw2Check == "") {
			 pw2.attr('class','form-control');
		 } else if (pw2Check == pwCheck) {
			 pw2.attr('class','form-control is-valid');
			 validationPw2.attr('class','valid-feedback');
			 validationPw2.text('비밀번호가 일치합니다.');
		  } else if (pw2Check != pwCheck) {
			  pw2.attr('class','form-control is-invalid');
			  validationPw2.attr('class','invalid-feedback');
			  validationPw2.text('비밀번호가 다릅니다');
			  return;
		  }
	 });
	 
	 // 닉네임 유효성 검사
	 var nick = $('#joinNick');
	 var validationNick = $('#validationNick');
	 
	 nick.keyup(function() {
		 
		 var nickCheck = nick.val();
		 var nickRegExp = /^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]{2,10}$/;

		
		 $.ajax({
		
			 url: "/member/checkAdminNick_process.do",
			 
			 type:"post",
			 
			 data: {"nick": nickCheck},
			 
			 dataType:"json",
			 
			 success:function(result){
			 
				 if(result == true) {	// 중복된 닉네임이 없을 때
					 if (nickCheck == "") {
						 nick.attr('class','form-control');
					 }  else if (nickCheck.match(nickRegExp) != null) {
						 nick.attr('class','form-control is-valid');
						 validationNick.attr('class','valid-feedback');
						 validationNick.text('올바른 닉네임입니다');
					 }  else {
						 nick.attr('class','form-control is-invalid');
						 validationNick.attr('class','invalid-feedback');
						 validationNick.text('닉네임은 한글,영문,숫자 포함 2~10자리로 입력하셔야합니다');
						 return;
					 }
				 } else if(result == false){	// 중복된 닉네임이 있을 때
					 nick.attr('class','form-control is-invalid');
					 validationNick.attr('class','invalid-feedback');
					 validationNick.text('중복된 닉네임입니다');
					 return;
				 }
			 }
		 });
	 });
	 	 
	 // 이름 유효성 검사
	 var fullname = $('#joinFullname');
	 var validationFullname = $('#validationFullname');
	 
	 fullname.keyup(function() {
		 
		 var fullnameCheck = fullname.val();
		 var fullnameRegExp = /^[가-힣]{2,6}$/;

		 // 검증에 사용할 정규식 변수 regExp에 저장
		 if (fullnameCheck == "") {
			 fullname.attr('class','form-control');
		 } else if (fullnameCheck.match(fullnameRegExp) != null) {
			 fullname.attr('class','form-control is-valid');
			 validationFullname.attr('class','valid-feedback');
			 validationFullname.text('올바른 이름입니다');
		  }
		  else {
			  fullname.attr('class','form-control is-invalid');
			  validationFullname.attr('class','invalid-feedback');
			  validationFullname.text('이름은 한글 2~6자리로 입력하셔야합니다');
			  return;
		  }
	 });
	 // 성별 유효성 검사
	 var gender_category = $('input[name="admin_gender"]:checked').val();

		if (gender_category == undefined) {
			alert('성별을 선택해주세요');
			return;
		}
	 // 휴대전화 유효성 검사
		 var phone = $('#joinPhone');
		 var validationPhone = $('#validationPhone');
		 
		 phone.keyup(function() {
			 
			 var phoneCheck = phone.val();
			 var phoneRegExp = /^\d{9,11}$/;

			 // 검증에 사용할 정규식 변수 regExp에 저장
			 if (phoneCheck == "") {
				 phone.attr('class','form-control');
			 } else if (phoneCheck.match(phoneRegExp) != null) {
				 phone.attr('class','form-control is-valid');
				 validationPhone.attr('class','valid-feedback');
				 validationPhone.text('올바른 전화번호입니다');
			  }
			  else {
				  phone.attr('class','form-control is-invalid');
				  validationPhone.attr('class','invalid-feedback');
				  validationPhone.text('전화번호는 9~11자리의 숫자만 입력하셔야 합니다');
				  return;
			  }
		 });			
 });
 
$('#joinBtn').click(function() {
	if($("#joinId").val() =="") {
		alert('아이디를 입력하세요');
		return;
	}
	if($("#joinPw").val() =="") {
		alert('비밀번호를 입력하세요');
		return;
	}
	if($("#joinPw2").val() =="") {
		alert('비밀번호를 확인하세요');
		return;
	}
	if($("#joinNick").val() =="") {
		alert('닉네임을 입력하세요');
		return;
	}
	if($("#joinFullname").val() =="") {
		alert('이름을 입력하세요');
		return;
	}
	if($("#joinPhone").val() =="") {
		alert('전화번호를 입력하세요');
		return;
	}
	if($("#joinId").attr('class') == "form-control is-invalid") {
		alert('아이디 형식 및 중복여부를 확인해주세요');
		return;
	}
	if($("#joinPw").attr('class') == "form-control is-invalid") {
		alert('비밀번호 형식을 확인해주세요');
		return;
	}
	if($("#joinPw2").attr('class') == "form-control is-invalid") {
		alert('비밀번호를 확인해주세요');
		return;
	}
	if($("#joinNick").attr('class') == "form-control is-invalid") {
		alert('닉네임 형식 및 중복여부를 확인해주세요');
		return;
	}
	if($("#joinFullname").attr('class') == "form-control is-invalid") {
		alert('이름 형식을 확인해주세요');
		return;
	}
	if($("#joinPhone").attr('class') == "form-control is-invalid") {
		alert('전화번호 형식을 확인해주세요');
		return;
	}
	if(document.querySelector('.form-control is-invalid') == null) {
		if(confirm("입력하신 정보로 회원가입을 하시겠습니까?")){
			//글 작성 중 새로고침, 뒤로가기 등등 눌렀을 때 경고창 띄우기
			checkUnload = false;
			$('#frm').submit();
	        return true;
	    } else {
	        return false;
	    }
	}
 });
 
function checkToday() {
	 
	 Today('null','null','null');
	 
}

 // 날짜 선택
 function dateSelect(docForm,selectIndex) {
 	watch = new Date(docForm.year.options[docForm.year.selectedIndex].text, docForm.month.options[docForm.month.selectedIndex].value,1);
 	
 	hourDiffer = watch - 86400000;
 	calendar = new Date(hourDiffer);

 	var daysInMonth = calendar.getDate();
 	
 		for (var i = 0; i < docForm.day.length; i++) {
 			docForm.day.options[0] = null;
 		}
 		for (var i = 0; i < daysInMonth; i++) {
 			if (i<9) {
 				docForm.day.options[i] = new Option('0'+(i+1));	// 1~9 앞에 0 넣어주기
 			} else {
 				docForm.day.options[i] = new Option(i+1);
 			}
 			
 	}
 		docForm.day.options[0].selected = true;
 }

 // 초기값 오늘 날짜로 세팅
 var str ="";
 function Today(year,mon,day){
      if(year == "null" && mon == "null" && day == "null"){       
      today = new Date();
      this_year=today.getFullYear();
      this_month=today.getMonth();
      this_month+=1;
      if(this_month <10) this_month="0" + this_month;
  
      this_day=today.getDate();
      if(this_day<10) {
      	this_day="0" + this_day;     
      }
  }
  else{  
      var this_year = eval(year);
      var this_month = eval(mon); 
      var this_day = eval(day);
      }
  
   montharray=new Array(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31); 
   maxdays = montharray[this_month-1]; 
   
 // 윤달 구하기
   if (this_month==2) { 
       if ((this_year/4)!=parseInt(this_year/4)) {
      	 maxdays=28; 
       }
       else maxdays=29; 
   } 
  
   str += "<div class='col-4'><label for='year'>생년</label><br><select class='form-control ui search dropdown' name='year' size=1 onChange='dateSelect(this.form,this.form.month.selectedIndex);'>";
      for(i=this_year-90;i<=this_year;i++){
          if(i==this_year) {
          	str += "<OPTION VALUE="+i+ " selected >" +i; 
          }
          else {
          	str += "<OPTION VALUE="+i+ ">" +i; 
          }
      }    
      str += "</select></div>";      

      str += "<div class='col-4'><label for='month'>월</label><br><select class='form-control ui search dropdown' name='month' size=1 onChange='dateSelect(this.form,this.selectedIndex);'>";
      for(i=1;i<=12;i++){ 
          if(i<10){
              if(i==this_month) {
              	str += "<OPTION VALUE=0" +i+ " selected >0"+i; 
              }
              else {
              	str += "<OPTION VALUE=0" +i+ ">0"+i;
              }
          }         
          else{
              if(i==this_month) {
              	str += "<OPTION VALUE=" +i+ " selected >" +i;  
              }
              else {
              	str += "<OPTION VALUE=" +i+ ">" +i;  
              }
          }                     
     }         
      str += "</select></div>";
  
      str += "<div class='col-4'><label for='day'>일</label><br><select class='form-control ui search dropdown' name='day' size=1>";
      for(i=1;i<=maxdays;i++){ 
          if(i<10){
              if(i==this_day) {
              	str += "<OPTION VALUE=0" +i+ " selected >0"+i; 
              }
              else {
              	str += "<OPTION VALUE=0" +i+ ">0"+i; 
              }
          }
          
          else{
              if(i==this_day) {
              	str += "<OPTION VALUE=" +i+ " selected } >"+i; 
              }
              else {
              	str += "<OPTION VALUE=" +i+ ">" +i;  
              }
          }                     
     }         
      str += "</select></div>"; 
      $("#birthday").html(str);
 }