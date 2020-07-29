<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<%-- 뒤로가기 방지하는거  차선책 --%>
<head>
<meta charset="UTF-8">
<title>여행지/맛집 글수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/locationboard/lc_write_content_page.css">
<!-- 카카오 지도 -->
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=25f30bf0195e51648727ff628cea7c4c&libraries=services"></script>
<script>
//파일 삭제
function deleteFile(clicked_id) {

   // 파일 번호 가져오기
   var arr = new Array();
   <c:forEach var="item" items="${data.locationboardUploadFileVoList }">
   arr.push({
      fileNo : "${item.locationboard_file_no}"
   });
   </c:forEach>

   // 파일명과 삭제버튼을 담고있는 div박스
   var fileDelete = document.getElementById("div_" + clicked_id);
   // controller로 넘겨줄 값(삭제버튼 눌린 파일 선별)
   var fileHidden = document.getElementById("hidden_" + clicked_id);

   for (var i = 0; i < arr.length; i++) {
      if (arr[i].fileNo == clicked_id) { // 파일 번호와 삭제버튼의 id가 일치하면 숨기기 처리
         // 삭제버튼 눌린 파일만 name을 같게 설정 -> controller로 넘기기 위해. 값은 파일 번호로 설정
         document.getElementById("hidden_" + clicked_id).name = "delete";
         document.getElementById("hidden_" + clicked_id).value = clicked_id;
         // 삭제버튼 눌린 파일 숨기기
         fileDelete.style.display = "none";
         fileDelete.setAttribute("class","removeFiles");
      }
   }
}
</script>
</head>
<body>
<div class="wrap pb-5 bg-light" style="min-height:700px">
	<div class="container py-5">
	<div class="row text-left">
		<div class="col" style="padding-bottom:20px; font-size:20px; font-weight:bold">여행지/맛집 글수정</div>
	</div>
	<input id="session_check" type="hidden" value="${sessionUser }">
		<!-- 장소 시작-->
		<div class="row">
			<div class="write-box col px-5">
				<!-- 장소 본문 내용 -->
				<!-- 지도 시작 -->
				<br>
				<div class="row">
					<div class="col mb-2">
						<strong>* 아래의 지도에서 장소 검색 및 선택해주세요</strong>
					</div>
				</div>
				<div class="map_wrap">
					<div id="map"
						style="width: 100%; height: 100%; position: relative; overflow: hidden;"></div>
					<div id="menu_wrap" class="bg_white">
						<div class="option">
							<div>
								<form onsubmit="searchPlaces(); return false;">
									키워드 : <input type="text" value="${data.locationboardVo.locationboard_storename }" id="keyword" size="15">
									<button type="submit">검색하기</button>
								</form>
							</div>
						</div>
						<hr>
						<ul id="placesList"></ul>
						<div id="pagination"></div>
					</div>
				</div>
				<!-- 지도 끝 -->
				<form id="frm"
					action="${pageContext.request.contextPath}/locationboard/lc_update_content_process.do"
					method="post" enctype="multipart/form-data">
					<div class="row mt-3">
						<!-- 장소 제목 -->
						<div class="nameTag col-2">
							<!-- 좌측칸-->
							제목
						</div>
						<div class="col">
							<!-- 우측 장소 제목 내용칸-->
							<input type="text" name="locationboard_title"
								value="${data.locationboardVo.locationboard_title }"
								class="form-control" id="title" placeholder="장소 제목 입력"
								onfocus="this.placeholder=''"
								onblur="this.placeholder='장소 제목 입력'">
						</div>
					</div>
					<div class="row mt-2">
						<!-- 분류 선택 : 여행지 / 맛집 -->
						<div class="nameTag col-2">
							<!-- 좌측칸-->
							분류
						</div>
							<!-- 우측 분류 내용칸-->
							<select name="location_category_no" class="form-control" style="width:20%; margin:0 15px"
								id="location_category">
								<option value="0">분류</option>
								<option value="1"
									<c:if test="${data.locationCategoryVo.location_category_no ==1}">selected</c:if>>여행지</option>
								<option value="2"
									<c:if test="${data.locationCategoryVo.location_category_no ==2}">selected</c:if>>맛집</option>
							</select>
						<!-- 상호명-->
						<div class="nameTag col-2">
							<!-- 좌측칸-->
							장소명
						</div>
						<div class="col">
							<!-- 우측 상호명 내용칸-->
							<input type="text" name="locationboard_storename" value="${data.locationboardVo.locationboard_storename }"
								class="form-control" id="locationboard_storename" readonly>
						</div>
					</div>
					<div class="row mt-2">
						<!-- 주소 -->
						<div class="nameTag col-2">
							<!-- 좌측칸-->
							주소
						</div>
						<div class="col">
							<!-- 우측 주소 내용칸-->

							<div class="row">
								<!-- 주소(지도 api) -->
								<div class="col">
									<input type="text" name="locationboard_storeaddress" value="${data.locationboardVo.locationboard_storeaddress }"
										class="form-control" id="locationboard_address" readonly>
									<input type="hidden" name="locationboard_coordinate"
										id="locationboard_coordinate" value="${data.locationboardVo.locationboard_coordinate}">
								</div>
							</div>
						</div>
					</div>
					<div class="row mt-2">
						<!-- 전화번호-->
						<div class="nameTag col-2">
							<!-- 좌측칸-->
							전화번호
						</div>
						<div class="col">
							<!-- 우측 전화번호 내용칸-->
							<!-- 우측 전화번호 내용칸-->
							<input type="text" name="locationboard_storenumber" value="${data.locationboardVo.locationboard_storenumber }"
								class="form-control" id="locationboard_storenumber" readonly>
						</div>
					</div>
					<div class="row mt-2" style="display:flex; align-items:center">
						<!-- 비용-->
						<div class="nameTag col-2">
							<!-- 좌측칸-->
							비용
						</div>
							<!-- 우측 비용 내용칸-->
							<input type="text" name="locationboard_cost" style="width:20%; margin:0 15px"
								value="${data.locationboardVo.locationboard_cost }"
								class="form-control" id="cost" placeholder="비용 입력"
								onfocus="this.placeholder=''" onblur="this.placeholder='비용 입력'">
						<!-- 소요시간-->
						
						<div class="col-7" style="display:flex; align-items:center">
						<div class="nameTag col-3 pl-0 mr-4">
							<!-- 좌측칸-->
							소요시간
						</div>
							<!-- 우측 소요시간 내용칸-->
							<fmt:parseNumber var="hour"
								value="${data.locationboardVo.locationboard_time/60 }"
								integerOnly="true" />
									<input type="text" name="locationboard_time_hour"
										value="${hour != 0 ? hour : 0}" style="width:100px; margin-left:15px"
										class="form-control ml-2" id="hour" placeholder="0~24"
										onfocus="this.placeholder=''"
										onblur="this.placeholder='0~24'">
									<label class="nameTag">시간&nbsp;&nbsp;&nbsp;&nbsp;</label>
									<input type="text" name="locationboard_time_minute"
										value="${data.locationboardVo.locationboard_time%60 != 0 ? data.locationboardVo.locationboard_time%60 : 0}"
										class="form-control" id="minute" placeholder="0~59"
										onfocus="this.placeholder=''" style="width:100px"
										onblur="this.placeholder='0~59'">
									<label class="nameTag">분</label>
						</div>
					</div>
					<div class="row mt-3">
						<!-- 유형-->
						<div class="nameTag col-2">
							<!-- 좌측칸-->
							유형
						</div>
						<div class="col">
							<!-- 우측 유형 내용칸-->
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" name="type_category_no" value="1"
									<c:if test="${data.typeCategoryVo.type_category_no == 1 }">checked</c:if>
									id="radioTypeCategory1" class="custom-control-input"> <label
									class="custom-control-label" for="radioTypeCategory1">혼자
									여행</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" name="type_category_no" value="2"
									<c:if test="${data.typeCategoryVo.type_category_no == 2 }">checked</c:if>
									id="radioTypeCategory2" class="custom-control-input"> <label
									class="custom-control-label" for="radioTypeCategory2">커플
									여행</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" name="type_category_no" value="3"
									<c:if test="${data.typeCategoryVo.type_category_no == 3 }">checked</c:if>
									id="radioTypeCategory3" class="custom-control-input"> <label
									class="custom-control-label" for="radioTypeCategory3">가족
									여행</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" name="type_category_no" value="4"
									<c:if test="${data.typeCategoryVo.type_category_no == 4 }">checked</c:if>
									id="radioTypeCategory4" class="custom-control-input"> <label
									class="custom-control-label" for="radioTypeCategory4">친구
									여행</label>
							</div>
						</div>
					</div>
					<div class="row mt-3">
						<!-- 스타일-->
						<div class="nameTag col-2">
							<!-- 좌측칸-->
							스타일
						</div>
						<div class="col">
							<!-- 우측 스타일 내용칸-->
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" name="mood_category_no" value="1"
									<c:if test="${data.moodCategoryVo.mood_category_no==1 }">checked</c:if>
									id="radioMoodCategory1" class="custom-control-input"> <label
									class="custom-control-label" for="radioMoodCategory1">조용함</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" name="mood_category_no" value="2"
									<c:if test="${data.moodCategoryVo.mood_category_no==2 }">checked</c:if>
									id="radioMoodCategory2" class="custom-control-input"> <label
									class="custom-control-label" for="radioMoodCategory2">시끄러움</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" name="mood_category_no" value="3"
									<c:if test="${data.moodCategoryVo.mood_category_no==3 }">checked</c:if>
									id="radioMoodCategory3" class="custom-control-input"> <label
									class="custom-control-label" for="radioMoodCategory3">자연친화적</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" name="mood_category_no" value="4"
									<c:if test="${data.moodCategoryVo.mood_category_no==4 }">checked</c:if>
									id="radioMoodCategory4" class="custom-control-input"> <label
									class="custom-control-label" for="radioMoodCategory4">활동적</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" name="mood_category_no" value="5"
									<c:if test="${data.moodCategoryVo.mood_category_no==5 }">checked</c:if>
									id="radioMoodCategory5" class="custom-control-input"> <label
									class="custom-control-label" for="radioMoodCategory5">분위기
									좋음</label>
							</div>
						</div>
					</div>
					<div class="row mt-3">
						<!-- 장소 내용 입력-->
						<div class="col">
							<!-- 내용 입력 첫번째 줄-->
							<div class="row">
								<div class="nameTag col">장소 내용</div>
							</div>
							<div class="row mt-1">
								<!-- 내용 입력 두번째 줄-->
								<div class="col">
									<!-- 내용 입력칸-->
									<textarea name="locationboard_content" class="form-control"
										id="content" placeholder="장소 내용 입력"
										onfocus="this.placeholder=''"
										onblur="this.placeholder='장소 내용 입력'" rows="10"
										style="resize: none">${data.locationboardVo.locationboard_content }</textarea>
								</div>
							</div>
						</div>
					</div>
					<div class="row mt-2" style="display:grid; grid-template-columns:1fr 1fr 1fr">
						<!-- 첨부된 파일 목록-->
						<div class="nameTag col pr-0" style="align-self:center; grid-column:1/4">
							<!-- 좌측칸-->
							파일 목록
						</div>
							<!-- 우측 파일 목록 내용칸-->
						<c:forEach var="locationboardUploadFileVo" items="${data.locationboardUploadFileVoList }" varStatus="status">
							<div style="font-size:14px; margin-top:10px; padding-right:0px"
									id="div_${locationboardUploadFileVo.locationboard_file_no }" class="col existFiles">
									
									${locationboardUploadFileVo.locationboard_originalname }
							<span
										class="badge badge-secondary" style="margin-top:1px; cursor:pointer"
										id="${locationboardUploadFileVo.locationboard_file_no }"
										onclick="deleteFile(this.id)">삭제</span>
							<input type="hidden" name="" id="hidden_${locationboardUploadFileVo.locationboard_file_no }">
							</div>
						</c:forEach>
					</div>

					<div class="row mt-3" style="display:grid; grid-template-columns:auto 1fr">
						<!-- 파일 첨부-->
						<div class="nameTag col pr-0" style="align-self:center">
							<!-- 좌측칸-->
							파일 첨부
						</div>
						<div class="col">
							<!-- 우측 파일 첨부 내용칸-->
							<div class="input-group">
								<div class="custom-file">
									<input type="file" name="upload_files" multiple
										accept="image/*" class="custom-file-input"
										id="inputGroupFile02" /> <label class="custom-file-label"
										for="inputGroupFile02">사진 선택</label>
								</div>
							</div>
						</div>
					</div>
					<div class="row my-3" style="text-align: right">
						<!-- 장소 버튼 -->
						<div class="col">
							<input type="hidden" name="locationboard_no"
								value="${data.locationboardVo.locationboard_no}"> <input
								type="button" class="btn btn-success" value="수정완료"
								onclick="writeContent()"> <a class="btn btn-outline-success"
								href="${pageContext.request.contextPath }/locationboard/lc_main_page.do">목록</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- 장소 끝 -->
	</div>
	<!-- JS File -->
	<script
		src="${pageContext.request.contextPath}/resources/js/locationboard/lc_update_content_page.js"></script>
</div>
</body>