$(function() {
	
//	var parameter = {};
//	var url = document.location.href;
//    var qs = url.substring(url.indexOf('?') + 1).split('&');
//    for(var i = 0, result = {}; i < qs.length; i++){
//        qs[i] = qs[i].split('=');
//        //alert(qs[i]);
//        result[qs[i][0]] = decodeURIComponent(qs[i][1]);
//        //alert(qs[i][1]);
//        //alert(qs[i][2]);
//        parameter[i] = decodeURIComponent(qs[i][1]);
//        if(qs[i][2] !=undefined) {
//        	parameter[i] += "="+decodeURIComponent(qs[i][2]);
//        }
//        //alert(parameter[i]);
//        
//    }
	
	$("#report_user").on('click', function() {
		
		if(confirm("신고하시겠습니까?")) {
			
			$("#frm").submit();
			
//			location.href="/routemap/report/report_user_process.do?member_no="+parameter[0]+"&uri="+parameter[1];

			return true;

		} else {

			return false;
		}
	});
});