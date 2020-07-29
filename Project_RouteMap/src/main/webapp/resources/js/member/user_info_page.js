$(function() {
	if($("#session_check").val() == "") {
		location.href="/error/no_authority_page.do";
		return;
	}
});  

   // 닉네임, 생년월일, 휴대전화 수정
   $(function() {
	   // 닉네임
	   $("#nicknameUpdateBtn").on("click", function() {
		   $("#nicknameUpdate").show();
		   $("#nicknameUpdateBtn").hide();
	   });
	   
	   $("#cancelUpdateNickname").on("click", function() {
		   $("#updateNick").val("");
		   $("#updateNick").attr("class","form-control form-control-sm");
		   $("#nicknameUpdateBtn").show();
		   $("#nicknameUpdate").hide();
	   });
	   
	// 생년월일
	   $("#birthdayUpdateBtn").on("click", function() {
		   checkToday();
		   $("#birthdayUpdate").show();
		   $("#birthdayUpdateBtn").hide();
	   });
	   
	   $("#cancelUpdateBirthday").on("click", function() {
		   
		   $("#birthday").empty();
		   $("#birthdayUpdateBtn").show();
		   $("#birthdayUpdate").hide();
	   });
	   
	   // 휴대전화
	   $("#phoneUpdateBtn").on("click", function() {
		   $("#phoneUpdate").show();
		   $("#phoneUpdateBtn").hide();
		   
	   });
	   
	   $("#cancelUpdatePhone").on("click", function() {
		   $("#updatePhone").val("");
		   $("#updatePhone").attr("class","form-control form-control-sm");
		   $("#phoneUpdateBtn").show();
		   $("#phoneUpdate").hide();
	   });
   
   
   
   	// 닉네임 유효성 검사
	 var nick = $('#updateNick');
	 var validationNick = $('#validationNick');
	 
	 nick.keyup(function() {
		 
		 var nickCheck = nick.val();
		 var nickRegExp = /^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]{2,10}$/;
		
		 $.ajax({
		
			 url: "/member/checkNick_process.do",
			 
			 type:"post",
			 
			 data: {"nick": nickCheck},
			 
			 dataType:"json",
			 
			 success:function(result){
			 
				 if(result == true) {	// 중복된 닉네임이 없을 때
					 if (nickCheck == "") {
						 nick.attr('class','form-control form-control-sm');
					 }  else if (nickCheck.match(nickRegExp) != null) {
						 nick.attr('class','form-control form-control-sm is-valid');
						 validationNick.attr('class','valid-feedback');
						 validationNick.text('올바른 닉네임입니다');
					 }  else {
						 nick.attr('class','form-control form-control-sm is-invalid');
						 validationNick.attr('class','invalid-feedback');
						 validationNick.text('닉네임은 한글,영문,숫자 포함 2~10자리로 입력하셔야합니다');
						 return;
					 }
				 } else if(result == false){	// 중복된 닉네임이 있을 때
					 nick.attr('class','form-control form-control-sm is-invalid');
					 validationNick.attr('class','invalid-feedback');
					 validationNick.text('중복된 닉네임입니다');
					 return;
				 }
			 }
		 });
	 });
	 
	// 휴대전화 유효성 검사
	 var phone = $('#updatePhone');
	 var validationPhone = $('#validationPhone');
	 
	 phone.keyup(function() {
		 
		 var phoneCheck = phone.val();
		 var phoneRegExp = /^\d{9,11}$/;

		 // 검증에 사용할 정규식 변수 regExp에 저장
		 if (phoneCheck == "") {
			 phone.attr('class','form-control form-control-sm');
		 } else if (phoneCheck.match(phoneRegExp) != null) {
			 phone.attr('class','form-control form-control-sm is-valid');
			 validationPhone.attr('class','valid-feedback');
			 validationPhone.text('올바른 전화번호입니다');
		  }
		  else {
			  phone.attr('class','form-control form-control-sm is-invalid');
			  validationPhone.attr('class','invalid-feedback');
			  validationPhone.text('전화번호는 9~11자리의 숫자만 입력하셔야 합니다');
			  return;
		  }
	 });	
   });
   
	 function checkToday() {
		// $("#birthday").html('');
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
	  				docForm.day.options[i] = new Option('0'+(i+1));
	  			} else {
	  				docForm.day.options[i] = new Option(i+1);
	  			}
	  	}
	  		docForm.day.options[0].selected = true;
	  }
	 
	  // 초기값 오늘 날짜로 세팅
	  var str ="";
	  
	  function Today(year,mon,day){
		   str ="";	// 전역변수이므로 이전값이 남아있음. 따라서 비워줘야함
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
	   
	    str += "<div><label for='year'>생년</label><br><select class='form-control form-control-sm ui search dropdown' name='year' size=1 onChange='dateSelect(this.form,this.form.month.selectedIndex);'>";
	       for(i=this_year-90;i<=this_year;i++){
	           if(i==this_year) {
	           	str += "<OPTION VALUE="+i+ " selected >" +i; 
	           }
	           else {
	           	str += "<OPTION VALUE="+i+ ">" +i; 
	           }
	       }    
	       str += "</select></div>";      

	       str += "<div><label for='month'>월</label><br><select class='form-control form-control-sm ui search dropdown' name='month' size=1 onChange='dateSelect(this.form,this.selectedIndex);'>";
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
	   
	       str += "<div><label for='day'>일</label><br><select class='form-control form-control-sm ui search dropdown' name='day' size=1>";
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

		 
	  $(function() {
			
			// 닉네임 변경
			$("#updateNickBtn").click(function() {
				 if ($("#updateNick").val() == "") {
						alert('새 닉네임을 입력하세요');
						return;
					}
					
					if($("#updateNick").attr('class') == "form-control form-control-sm is-invalid") {
						alert('닉네임을 확인해주세요');
						return;
					}
					
					
					if(document.querySelector('.form-control form-control-sm is-invalid') == null) {
						if(confirm("닉네임을 변경하시겠습니까?")){
					        $("#nicknameFrm").submit();
					        return true;
					    } else {
					        return false;
					    }
					}
			  });
			
			// 생년월일 변경
			$("#updateBirthdayBtn").click(function() {
					if(confirm("생년월일을 변경하시겠습니까?")){
				        $("#birthdayFrm").submit();
				        return true;
				    } else {
				        return false;
				}
			});
			
			 $("#updatePhoneBtn").click(function() {
				 if ($("#updatePhone").val() == "") {
						alert('새 휴대전화번호를 입력하세요');
						return;
					}
				 if($("#updatePhone").attr('class') == "form-control form-control-sm is-invalid") {
						alert('휴대전화번호 형식을 확인해주세요');
						return;
					}
				 if(document.querySelector('.form-control form-control form-control-sm is-invalid') == null) {
					if(confirm("휴대전화번호를 변경하시겠습니까?")){
				        $("#phoneFrm").submit();
				        return true;
				    } else {
				        return false;
				}
				 }
		});
			
		});
   
   
   
   
   
   
   
   
   
   
   
   
   