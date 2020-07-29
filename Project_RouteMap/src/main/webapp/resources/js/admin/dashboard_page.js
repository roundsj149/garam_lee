window.onload = dashboardProcess();
	
<!-- page script -->
  //오늘 도넛차트 출력
  function donut(data) {
	  
	  html = '';
	  html += data.lcBoardCount
	  $('#totalCount').html(html);
	  
	  var count = (data.lcBoardCount-data.yesterLcBoardCount)/data.yesterLcBoardCount * 100
	  var count2 = count.toFixed(2);
	  
	  html2 = '';
	  
	  if (count > 0) {
		  html2 += '<i class="fas fa-arrow-up"></i>' + '+' + count2 + '%';
	  } else {
		  $('#testtest').removeClass('text-success').addClass('text-danger');
		  html2 += '<i class="fas fa-arrow-down"></i>' + count2 + '%';
	  }
	  
	  console.log(html2);
	  
	  $('#testtest').html(html2);

    //- DONUT CHART -
    //-------------
    // Get context with jQuery - using jQuery's .get() method.
    var donutChartCanvas = $('#donutChart').get(0).getContext('2d')
    var donutData        = {
      labels: [
          data.lcProList[0],
          data.lcProList[1],
          data.lcProList[2],
          data.lcProList[3],
          data.lcProList[4],
          '기타',
      ],
      datasets: [
        {
          data: [
	        	  data.lcProCountList[0],
	        	  data.lcProCountList[1],
	        	  data.lcProCountList[2],
	        	  data.lcProCountList[3],
	        	  data.lcProCountList[4],
	        	  data.lcBoardCount-(data.lcProCountList[0]+data.lcProCountList[1]+data.lcProCountList[2]+data.lcProCountList[3]+data.lcProCountList[4])
        	  	],
          backgroundColor : ['#f56954', '#00a65a', '#f39c12', '#00c0ef', '#3c8dbc', '#d2d6de'],
        }
      ]
    }
    var donutOptions     = {
      maintainAspectRatio : false,
      responsive : true,
    }
    
    
    //Create pie or douhnut chart
    // You can switch between pie and douhnut using the method below.
    var donutChart = new Chart(donutChartCanvas, {
      type: 'doughnut',
      data: donutData,
      options: donutOptions      
    })

  }
  
//어제 도넛차트 출력
  function yesterDonut(data) {
	  
	  html = '';
	  html += data.yesterLcBoardCount
	  $('#totalCount2').html(html);
	  
	  var count = (data.yesterLcBoardCount-data.yester2LcBoardCount)/data.yester2LcBoardCount * 100
	  var count2 = count.toFixed(2);
	  
	  html2 = '';
	  
	  if (count > 0) {
		  html2 += '<i class="fas fa-arrow-up"></i>' + '+' + count2 + '%';
	  } else {
		  $('#testtest2').removeClass('text-success').addClass('text-danger');
		  html2 += '<i class="fas fa-arrow-down"></i>' + count2 + '%';
	  }
	  
	  console.log(html2);
	  
	  $('#testtest2').html(html2);
	  
    //- DONUT CHART -
    //-------------
    // Get context with jQuery - using jQuery's .get() method.
    var donutChartCanvas = $('#donutChart2').get(0).getContext('2d')
    var donutData        = {
      labels: [
          data.yesterLcProList[0],
          data.yesterLcProList[1],
          data.yesterLcProList[2],
          data.yesterLcProList[3],
          data.yesterLcProList[4],
          '기타',
      ],
      datasets: [
        {
          data: [
	        	  data.yesterLcProCountList[0],
	        	  data.yesterLcProCountList[1],
	        	  data.yesterLcProCountList[2],
	        	  data.yesterLcProCountList[3],
	        	  data.yesterLcProCountList[4],
	        	  data.yesterLcBoardCount-(data.yesterLcProCountList[0]+data.yesterLcProCountList[1]+data.yesterLcProCountList[2]+data.yesterLcProCountList[3]+data.yesterLcProCountList[4])
        	  	],
          backgroundColor : ['#f56954', '#00a65a', '#f39c12', '#00c0ef', '#3c8dbc', '#d2d6de'],
        }
      ]
    }
    var donutOptions     = {
      maintainAspectRatio : false,
      responsive : true,
    }
    
    
    //Create pie or douhnut chart
    // You can switch between pie and douhnut using the method below.
    var donutChart = new Chart(donutChartCanvas, {
      type: 'doughnut',
      data: donutData,
      options: donutOptions      
    })

  }
  
  function viewDonut2() {
	  $('#donutChart').css('display','none');
	  $('#donutChart2').css('display','block');
	  $('#totalCount').css('display','none');
	  $('#totalCount2').css('display','block');
	  $('#testtest').css('display','none');
	  $('#testtest2').css('display','block');
	  
	  
  }
  
  function viewDonut1() {
	  $('#donutChart2').css('display','none');
	  $('#donutChart').css('display','block');
	  $('#totalCount2').css('display','none');
	  $('#totalCount').css('display','block');
	  $('#testtest2').css('display','none');
	  $('#testtest').css('display','block');
	  
  }
  
  //---------------------
  //- STACKED BAR CHART -
  //---------------------
  
  //이번주 스택바 차트
  function stackBar(data){
	  
	var thisLoginCount = data.thisWeekLoginCount;
	var lastLoginCount = data.lastWeekLoginCount;
	$('#thisLoginTotalCount').html(thisLoginCount);
	var thisInc = (thisLoginCount - lastLoginCount)/lastLoginCount * 100;
	var thisInc2 = thisInc.toFixed(2);
	var html = '';
	
	if(thisInc > 0) {
		html += '<i class="fas fa-arrow-up"></i>' + '+' + thisInc2 + '%';
	} else {
		$('#thisStackInc').removeClass().addClass('text-danger');
		html += '<i class="fas fa-arrow-down"></i>' + thisInc2 + '%';	
	}
	
	
	$('#thisStackInc').html(html);
	
	console.log(data.countList);
	
  var areaChartData = {
      labels  : [data.list[6], data.list[5], data.list[4], data.list[3], data.list[2], data.list[1], data.list[0]],
      datasets: [
        {
          label               : '로그인 유저',
          backgroundColor     : 'rgba(60,141,188,0.9)',
          borderColor         : 'rgba(60,141,188,0.8)',
          pointRadius          : false,
          pointColor          : '#3b8bba',
          pointStrokeColor    : 'rgba(60,141,188,1)',
          pointHighlightFill  : '#fff',
          pointHighlightStroke: 'rgba(60,141,188,1)',
          data                : [data.countList[6],data.countList[5],data.countList[4],data.countList[3],data.countList[2],data.countList[1],data.countList[0]]
        },
//        {
//          label               : '비로그인 유저',
//          backgroundColor     : 'rgba(210, 214, 222, 1)',
//          borderColor         : 'rgba(210, 214, 222, 1)',
//          pointRadius         : false,
//          pointColor          : 'rgba(210, 214, 222, 1)',
//          pointStrokeColor    : '#c1c7d1',
//          pointHighlightFill  : '#fff',
//          pointHighlightStroke: 'rgba(220,220,220,1)',
//          data                : [65, 59, 80, 81, 56, 55, 40]
//        },
      ]
    }
  
  var stackedBarChartCanvas = $('#stackedBarChart').get(0).getContext('2d')
  var barChartData = jQuery.extend(true, {}, areaChartData)
  var stackedBarChartData = jQuery.extend(true, {}, barChartData)

  var stackedBarChartOptions = {
    responsive              : true,
    maintainAspectRatio     : false,
    scales: {
      xAxes: [{
        stacked: true,
      }],
      yAxes: [{
        stacked: true
      }]
    }
  }

  var stackedBarChart = new Chart(stackedBarChartCanvas, {
    type: 'bar', 
    data: stackedBarChartData,
    options: stackedBarChartOptions
  })
  }
  
  //저번주 스택바 차트
  function laststackBar(data){
	  
		var lastLoginCount = data.lastWeekLoginCount;
		var last2LoginCount = data.last2WeekLoginCount;
		console.log(lastLoginCount);
		console.log(last2LoginCount);
		$('#lastLoginTotalCount').html(lastLoginCount);

		var lastInc = (lastLoginCount-last2LoginCount)/last2LoginCount * 100;
		var lastInc2 = lastInc.toFixed(2);

		var html = '';
		
		if(lastInc > 0) {
			html += '<i class="fas fa-arrow-up"></i>' + '+' + lastInc2 + '%';
		} else {
			$('#lastStackInc').removeClass().addClass('text-danger');
			html += '<i class="fas fa-arrow-down"></i>' + lastInc2 + '%';	
		}
		
		
		$('#lastStackInc').html(html);
		  
	  var areaChartData = {
	      labels  : [data.lastList[6],data.lastList[5],data.lastList[4],data.lastList[3],data.lastList[2],data.lastList[1],data.lastList[0]],
	      datasets: [
	        {
	          label               : '로그인 유저',
	          backgroundColor     : 'rgba(60,141,188,0.9)',
	          borderColor         : 'rgba(60,141,188,0.8)',
	          pointRadius          : false,
	          pointColor          : '#3b8bba',
	          pointStrokeColor    : 'rgba(60,141,188,1)',
	          pointHighlightFill  : '#fff',
	          pointHighlightStroke: 'rgba(60,141,188,1)',
	          data                : [data.lastCountList[6],data.lastCountList[5],data.lastCountList[4],data.lastCountList[3],data.lastCountList[2],data.lastCountList[1],data.lastCountList[0]]
	        },
//	        {
//	          label               : '비로그인 유저',
//	          backgroundColor     : 'rgba(210, 214, 222, 1)',
//	          borderColor         : 'rgba(210, 214, 222, 1)',
//	          pointRadius         : false,
//	          pointColor          : 'rgba(210, 214, 222, 1)',
//	          pointStrokeColor    : '#c1c7d1',
//	          pointHighlightFill  : '#fff',
//	          pointHighlightStroke: 'rgba(220,220,220,1)',
//	          data                : [65, 59, 80, 81, 56, 55, 40]
//	        },
	      ]
	    }
	  
	  var stackedBarChartCanvas = $('#stackedBarChart2').get(0).getContext('2d')
	  var barChartData = jQuery.extend(true, {}, areaChartData)
	  var stackedBarChartData = jQuery.extend(true, {}, barChartData)
	
	  var stackedBarChartOptions = {
	    responsive              : true,
	    maintainAspectRatio     : false,
	    scales: {
	      xAxes: [{
	        stacked: true,
	      }],
	      yAxes: [{
	        stacked: true
	      }]
	    }
	  }
	
	  var stackedBarChart = new Chart(stackedBarChartCanvas, {
	    type: 'bar', 
	    data: stackedBarChartData,
	    options: stackedBarChartOptions
	  })
  }
  
  //스택바 저번주 클릭
  function LastStack() {
	  $('#thisLoginTotalCount').css('display','none');
	  $('#lastLoginTotalCount').css('display','block');
	  $('#stackedBarChart').css('display','none');
	  $('#stackedBarChart2').css('display','block');
	  $('#thisStackInc').css('display','none');
	  $('#lastStackInc').css('display','block');
  }

  //스택바 이번주 클릭
  function thisStack() {
	  $('#lastLoginTotalCount').css('display','none');
	  $('#thisLoginTotalCount').css('display','block');
	  $('#stackedBarChart2').css('display','none');
	  $('#stackedBarChart').css('display','block');
	  $('#lastStackInc').css('display','none');
	  $('#thisStackInc').css('display','block');
  }
  
  //ajax 호출
   	function dashboardProcess() {
   		
   		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url : "/admin/dashboard_process.do",
			processData: false,
			contentType:false,
			dataType: "json",
			async: true,
			success: function(data, status, xhr){
				if (data) {
					donut(data.donutMap)
					yesterDonut(data.donutMap)
					likeChart(data.lcMap)
					likertChart(data.rtMap)
					stackBar(data.stackMap)
					laststackBar(data.stackMap);
					
					//실시간 차트 실시간 ajax 호출
					setInterval(function(){	
				 		$.ajax({
				  			type: "POST",
				  			enctype: 'multipart/form-data',
				  			url : "/admin/flot_process.do",
				  			processData: false,
				  			contentType:false,
				  			dataType: "json",
				  			async: true,
				  			success: function(data, status, xhr){
				  				if (data) {		      
				  			        animateRenewal(new_option, data.x);
				  				}
				  				
				  			}
				  				// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
				  				// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
				 		});
			    	}, 2000);
				}
				
			}
				// 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
				// 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
		});
	}

   	
   	//여행지/맛집 인기코스 차트
   	function likeChart(data) {
   		
   		var html = '';
   		var no = 0;
   		
   		console.log(data.locationboardVo);
   		$.each(data.locationboardVo,function(index,value) {
   			
   			++no;
   			
   			html += '<tr>' +
   						'<td>' + no + '</td>' +
   						'<td>' + value.locationboard_title + '</td>' +
   						'<td><small class="text-danger mr-1"><i class="fab fa-gratipay"></i>' + data.count[index] + '</small></td>' +
   						'<td><a href="/locationboard/lc_read_content_page.do?locationboard_no='+ value.locationboard_no + '"' + 'class="text-muted"><i class="fas fa-search"></i></a></td>';
   			
   		});
   		$('#likeBoard').html(html);
   	}
   	
   	//루트 인기코스 차트
   	function likertChart(data) {
   		var html = '';
   		var no = 0;
   		
   		console.log(data.routeboardVo);
   		$.each(data.routeboardVo,function(index,value) {
   			
   			++no;
   			
   			html += '<tr>' +
   						'<td>' + no + '</td>' +
   						'<td>' + value.routeboard_title + '</td>' +
   						'<td><small class="text-danger mr-1"><i class="fab fa-gratipay"></i>' + data.count[index] + '</small></td>' +
   						'<td><a href="/routeboard/rt_read_content_page.do?routeboard_no=' + value.routeboard_no + '"' + 'class ="text-muted"><i class="fas fa-search"></i></a></td>';
   			
   		});
   		$('#likeBoard2').html(html);
   	}
   	
   	//인기차트에서 루트 클릭시 
   	function hideLcChart() {
   		$('#likeBoard').css('display','none');
   		$('#likeBoard2').css('display','table-row-group');
   	}
   	
   	//인기차트에서 여행지/맛집 클릭시
   	function hideRTChart() {
   		$('#likeBoard2').css('display','none');
   		$('#likeBoard').css('display','table-row-group');
   	}
   	
   	//실시간 차트 (구글)
	google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(function(){ drawChart(new_option)});
    
      var chartOption = function(target, maxValue, color, name){
        this.name = name;
        this.target = target;
        this.data = null;
        this.chart = null;
        this.options = {
          legend: { position: 'none' },
          vAxis: {minValue:0, maxValue:maxValue},
          hAxis: {
            textStyle: {
              fontSize: 11
            }
          },
          colors: [color],
          animation: {
            duration: 500,
            easing: 'in',
            startup: true
          }
        }
        
      }

      var new_option = new chartOption('chart', 80, '#3C8BBA', '접속자 수');
      
      function drawChart(option) {
        var o = option;
        if(o != null){
          //초기값일때만 처리
          if(o.chart == null && o.data == null){
            o.data = new google.visualization.DataTable();
            o.data.addColumn('string', 'time');
            o.data.addColumn('number', o.name);
            o.data.addRow(['', 0]);
            o.chart = new google.visualization.LineChart(document.getElementById(o.target));
          }

          o.chart.draw(o.data, o.options);
        }
      }

      function animateRenewal(option,data){
        var o = option;
        if (o.data.getNumberOfRows() >= 10) {
          o.data.removeRow(0);
        }


        var value = 0;
        value = data
        console.log(data);
        o.data.insertRows(o.data.getNumberOfRows(), [[getNowTime(), value]]);
        drawChart(o);
      }


      function getNowTime(){
        var d = new Date();
        var sep = ":";
        var hh = d.getHours();
        var mm = d.getMinutes();
        var ss = d.getSeconds();
        return hh + sep + mm + sep + ss;
      }
