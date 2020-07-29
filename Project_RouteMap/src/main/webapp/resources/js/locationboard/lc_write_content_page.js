//글 작성 중 새로고침, 뒤로가기 등등 눌렀을 때 경고창 띄우기
var checkUnload = true;
$(window).on("beforeunload", function(){
    if(checkUnload) return "이 페이지를 벗어나면 작성된 내용은 저장되지 않습니다.";
});


// 지도 

var markers = [];

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();  

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({zIndex:1});

// 키워드로 장소를 검색합니다
searchPlaces();

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {

    var keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
    	$("#keyword").focus();
        return false;
    }

    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch( keyword, placesSearchCB); 
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {

        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        displayPlaces(data);

        // 페이지 번호를 표출합니다
        displayPagination(pagination);

    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

        alert('검색 결과가 존재하지 않습니다.');
        return;

    } else if (status === kakao.maps.services.Status.ERROR) {

        alert('검색 결과 중 오류가 발생했습니다.');
        return;

    }
}

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {

    var listEl = document.getElementById('placesList'), 
    menuEl = document.getElementById('menu_wrap'),
    fragment = document.createDocumentFragment(), 
    bounds = new kakao.maps.LatLngBounds(), 
    listStr = '';
    
    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();
    
    for ( var i=0; i<places.length; i++ ) {

        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i), 
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function(marker, title, id) {
            kakao.maps.event.addListener(marker, 'click', function() {
                displayInfowindow(marker, title, id);
            });

            
        })(marker, places[i].place_name, places[i].id);
        
        // 선택
        (function(id, name, address, phone, y, x) {
            $(document).on('click',"#"+id+"", function() {
                choosePlace(id, name, address, phone, y, x);
            });

            
        })(places[i].id, places[i].place_name, places[i].road_address_name, places[i].phone, places[i].y, places[i].x);
        
        
        fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
}

// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, places) {

    var el = document.createElement('li'),
    itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
                '<div id="list_' + (index+1) + '" class="info">' +
                '   <h5>' + places.place_name + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
                    '   <span class="jibun gray">' +  places.address_name  + '</span>';
    } else {
        itemStr += '    <span>' +  places.address_name  + '</span>'; 
    }
                 
      itemStr += '  <span class="tel">' + places.phone  + '</span>' +
                '</div>';           

    el.innerHTML = itemStr;
    el.className = 'item';
    
    // 목록 선택 시 지도 이동
    $(document).on('click',"#list_" + (index+1) + "", function() {
    	
    	setCenter(places.id, places.place_name, places.road_address_name, places.phone, places.y, places.x);
    });
    
    function setCenter(id, name, address, phone, y, x) {            
	    // 이동할 위도 경도 위치를 생성합니다 
	    var moveLatLon = new kakao.maps.LatLng(y, x);
	    
	    // 지도 중심을 이동 시킵니다
	    map.setCenter(moveLatLon);
	    
	    choosePlace(id, name, address, phone, y, x);
	}

    return el;
}



// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage 
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for ( var i = 0; i < markers.length; i++ ) {
        markers[i].setMap(null);
    }   
    markers = [];
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment(),
        i; 

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild (paginationEl.lastChild);
    }

    for (i=1; i<=pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i===pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function(i) {
                return function() {
                    pagination.gotoPage(i);
                }
            })(i);
        }

        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title, id) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';
    var btn = '<button id="'+id+'" type="button">선택</button>';
    infowindow.setContent(content+btn);
    infowindow.open(map, marker);
}

 // 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {   
    while (el.hasChildNodes()) {
        el.removeChild (el.lastChild);
    }
}

function choosePlace(id, name, address, phone, y, x) {
	$("#locationboard_storename").val(name);
	$("#locationboard_address").val(address);
	$("#locationboard_storenumber").val(phone);
	if(phone == "") {
		$("#locationboard_storenumber").val('-');
	}
	$("#locationboard_coordinate").val(y+"-"+x);
	infowindow.close();
}

////////////////////////////////////////////////////////////////////////////////////////////////////////

// 글쓰기 - 첨부파일 이름 들어가게 하기
$(document).on('change', '.custom-file-input', function(event) {
	var files = [];
	for (var i = 0; i < $(this)[0].files.length; i++) {
		files.push($(this)[0].files[i].name);
		$(this).next('.custom-file-label').html(files.join(', '));
	}
})

// 글쓰기
function writeContent() {

	var frm = $('#frm');

	// 제목 유효성 체크
	var title = $('#location_title');

	if (title.val() == null || title.val() == "") {
		alert('제목을 입력해주세요');
		title.focus();
		return;
	}

	// 여행지/맛집(체크박스) 유효성 체크
	var locationCategory = $('#location_category');

	if (locationCategory.val() == 0) {
		alert('여행지 또는 맛집을 선택해주세요');
		locationCategory.focus();
		return;
	}
	
	// 장소 선택 유효성 체크
	if ($("#locationboard_storename").val() == ""){
		alert('장소를 선택해주세요');
		return;
	}

	// 비용 유효성 체크
	var costReg = /^\d{0,6}$/; // 6자리 이하의 숫자
	var cost = $('#cost');

	if (cost.val() == null || cost.val() == "") {
		alert('비용을 입력해주세요');
		cost.focus();
		return;
	}

	if (!costReg.test(cost.val())) {
		alert("비용은 6자리 이하의 숫자만 입력하셔야 합니다");
		cost.val('');
		cost.focus();
		return;
	}

	// 소요시간 - 시간 유효성 체크
	var hourReg = /^([0-9]|1[0-9]|2[0-4])$/; // 2자리 이하의 숫자
	var hour = $('#hour');

	if (hour.val() == null || hour.val() == "") {
		alert('시간을 입력해주세요');
		hour.focus();
		return;
	}

	if (!hourReg.test(hour.val())) {
		alert("시간은  0~24 사이의 숫자만 입력하셔야 합니다");
		hour.val('');
		hour.focus();
		return;
	}

	// 소요시간 - 분 유효성 체크
	var minuteReg = /^([0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])$/; // 2자리 이하의 숫자
	var minute = $('#minute');

	if (minute.val() == null || minute.val() == "") {
		alert('분을 입력해주세요');
		minute.focus();
		return;
	}

	if (!minuteReg.test(minute.val())) {
		alert("분은  0~59 사이의 숫자만 입력하셔야 합니다");
		minute.val('');
		minute.focus();
		return;
	}

	// 유형 유효성 체크
	var type_category = $('input[name="type_category_no"]:checked').val();

	if (type_category == undefined) {
		alert('유형을 선택해주세요');
		return;
	}

	// 유형 유효성 체크
	var style_category = $('input[name="mood_category_no"]:checked').val();

	if (style_category == undefined) {
		alert('스타일을 선택해주세요');
		return;
	}

	// 내용 유효성 체크
	var content = $('#content');

	if (content.val() == null || content.val() == "") {
		alert('내용을 입력해주세요');
		content.focus();
		return;
	}
	// 첨부파일 유효성 체크
	if($('.custom-file-input').val() == "") {
		alert('첨부파일은 필수입니다');
	    $(".custom-file-input").focus();
	    return;
	}
	
	checkUnload = false;
	frm.submit();
}