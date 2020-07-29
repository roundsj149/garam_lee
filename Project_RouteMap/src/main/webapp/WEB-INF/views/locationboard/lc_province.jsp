<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%--bootstrap CSS 코드 --%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<title>여행지/맛집 주소</title>
<script>
 function province() {
		
	// 서울특별시
	var boxcol = document.createElement("div");
		boxcol.setAttribute("class","col-9 ml-5");
	var boxa1 = document.createElement("a");
		boxa1.href = "http://google.com"
	var boxa1Text = document.createTextNode("서울특별시");
		boxcol.appendChild(boxa1).appendChild(boxa1Text);
				
	// 부산광역시
	var boxa2 = document.createElement("a");
	boxa1.href = "http://google.com"
	var boxa2Text = document.createTextNode("부산광역시");
	boxcol.appendChild(boxa2).appendChild(boxa2Text);
			
	// 인천광역시
	var boxa3 = document.createElement("a");
		boxa3.href = "http://google.com"
	var boxa3Text = document.createTextNode("인천광역시");
		boxcol.appendChild(boxa3).appendChild(boxa3Text);
				
	// 대구광역시
	var boxa4 = document.createElement("a");
		boxa4.href = "http://google.com"
	var boxa4Text = document.createTextNode("대구광역시");
		boxcol.appendChild(boxa4).appendChild(boxa4Text);
				
	// 대전광역시
	var boxa5 = document.createElement("a");
		boxa5.href = "http://google.com"
	var boxa5Text = document.createTextNode("대전광역시");
		boxcol.appendChild(boxa5).appendChild(boxa5Text);
				
	// 광주광역시
	var boxa6 = document.createElement("a");
		boxa6.href = "http://google.com"
	var boxa6Text = document.createTextNode("광주광역시");
		boxcol.appendChild(boxa6).appendChild(boxa6Text);
		
	// 울산광역시
	var boxa7 = document.createElement("a");
		boxa7.href = "http://google.com"
	var boxa7Text = document.createTextNode("울산광역시");
		boxcol.appendChild(boxa7).appendChild(boxa7Text);
				
	// 경기도
	var boxa8 = document.createElement("a");
		boxa8.href = "http://google.com"
	var boxa8Text = document.createTextNode("울산광역시");
		boxcol.appendChild(boxa8).appendChild(boxa8Text);
				
	// 강원도
	var boxa9 = document.createElement("a");
		boxa9.href = "http://google.com"
	var boxa9Text = document.createTextNode("울산광역시");
		boxcol.appendChild(boxa9).appendChild(boxa9Text);
				
	// 충청도
	var boxa10 = document.createElement("a");
		boxa10.href = "http://google.com"
	var boxa10Text = document.createTextNode("울산광역시");
		boxcol.appendChild(boxa10).appendChild(boxa10Text);
				
	// 전라도
	var boxa11 = document.createElement("a");
		boxa7.href = "http://google.com"
	var boxa11Text = document.createTextNode("울산광역시");
		boxcol.appendChild(boxa11).appendChild(boxa11Text);
				
	// 경상도
	var boxa12 = document.createElement("a");
		boxa12.href = "http://google.com"
	var boxa12Text = document.createTextNode("울산광역시");
		boxcol.appendChild(boxa12).appendChild(boxa12Text);
				
	//제주도
	var boxa13 = document.createElement("a");
		boxa13.href = "http://google.com"
	var boxa13Text = document.createTextNode("울산광역시");
		boxcol.appendChild(boxa13).appendChild(boxa13Text);
				
	//세종시
	var boxa14 = document.createElement("a");
		boxa14.href = "http://google.com"
	var boxa14Text = document.createTextNode("울산광역시");
		boxcol.appendChild(boxa14).appendChild(boxa14Text);
			
	var emptycol = document.createElement("div");
		emptycol.setAttribute("class","col");
		boxcol.appendChild(emptycol);
}
</script>
<%--bootstrap JS 코드 --%>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>