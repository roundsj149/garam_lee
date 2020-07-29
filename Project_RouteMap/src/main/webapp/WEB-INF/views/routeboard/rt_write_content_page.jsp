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
<!-- CSS File -->
<link
	href="${pageContext.request.contextPath}/resources/css/routeboard/rt_write_content_page.css"
	rel="stylesheet" />
<title>루트 글쓰기</title>
<!-- 업로드 파일명 출력 -->
<script>
      $(document).on('change', '.custom-file-input', function (event) {
         var files = [];
         for (var i = 0; i < $(this)[0].files.length; i++) {
            files.push($(this)[0].files[i].name);
            $(this).next('.custom-file-label').html(files.join(', '));
         }
      })
   </script>
</head>

<body>
	<div class="wrap pb-5 bg-light" style="min-height: 700px">
		<!-- 컨테이너 시작 -->
		<div class="container py-5" id="container">
			<!-- 컨테이너 -->
			<div class="row text-left">
				<div class="col"
					style="padding-bottom: 20px; font-size: 20px; font-weight: bold">루트공유
					글쓰기</div>
			</div>
			<div class="row pt-5 px-5"
				style="border-radius: 20px; background-color: white; border: 1px solid #eaeaea">

				<!-- 본문 시작 -->
				<div class="col">
					<!-- 본문 내용 -->
					<form id="frm"
						action="${pageContext.request.contextPath }/routeboard/rt_write_content_process.do">
						<input id="session_check" type="hidden" value="${sessionUser }">
						<div class="row mt-3 mb-5 pb-3"
							style="border-bottom: 1px solid #ccc">
							<!-- 루트 제목 -->
							<div class="nameTag col-1">
								<!-- 좌측칸-->
								제목
							</div>
							<div class="col">
								<!-- 우측 루트 제목 내용칸-->
								<input type="text" name="routeboard_title" class="form-control"
									id="routeboard_title" placeholder="루트 제목을 입력하세요."
									onfocus="this.placeholder=''"
									onblur="this.placeholder='루트 제목을 입력하세요.'"
									<c:if
                           test="${!empty routeboardData}">value="${routeboardData.routeboardVo.routeboard_title }"
                        </c:if>>
							</div>
						</div>
						<!-- 장소 시작-->
						<div class="row">
							<div class="col-1">
								<!-- 장소 왼쪽 여백 -->
							</div>
							<div class="col">
								<!-- 장소 본문 내용 -->
								<div class="row">
									<!-- 관심 장소 추가하기된 폼의 목록-->
									<div class="col locationboard_form_list"
										id="locationboard_form_list">
										<!-- 관심 장소 폼 -->

									</div>
								</div>
								<div class="row mt-2"
									style="height: 50px; display: flex; align-items: center">
									<!-- 알림창 -->
									<div
										class="col text-center mb-0 alert alert-secondary alert-dismissible fade show"
										role="alert">
										오른쪽의 장소 추가 버튼을 눌러 관심 등록한 장소를 추가해주세요
										<button type="button" class="close" data-dismiss="alert"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<!-- 장소 버튼-->
									<div class="col-2 pr-0">
										<!-- 관심 장소 가져오기 버튼 : 모달(modal) -->
										<input type="button" class="btn btn-success"
											id="call_lc_myfavorite" value="장소 추가" data-toggle="modal"
											data-target="#myfavoriteLocationModalScrollable">

										<!-- Modal -->
										<div class="modal fade" id="myfavoriteLocationModalScrollable"
											tabindex="-1" role="dialog"
											aria-labelledby="exampleModalScrollableTitle"
											aria-hidden="true">
											<div class="modal-dialog modal-dialog-scrollable"
												role="document">
												<div class="modal-content">
													<div class="modal-header">
														<h5 class="modal-title" id="exampleModalScrollableTitle">관심
															장소 목록</h5>
														<button type="button" class="close" data-dismiss="modal"
															aria-label="Close">
															<span aria-hidden="true">&times;</span>
														</button>
													</div>
													<div class="modal-body container">
														<div class="row">
															<!-- 모달 내용 -->
															<div class="col" id="myfavoriteLocationList">
																<!-- 관심 장소 목록 -->
																<c:forEach items="${myfavoriteLocationboardList}"
																	var="data">
																	<div
																		class="row mt-1 button_add_myfavorite_locationboard_no_${data.locationboardVo.locationboard_no }">
																		<div class="col">
																			<!-- 관심 장소 가져오기 버튼-->
																			<input type="button"
																				onclick="buttonAddMyfavoriteLocation(${data.locationboardVo.locationboard_no })"
																				class="btn btn-outline-success btn-block"
																				value="${data.locationboardVo.locationboard_title }"
																				data-dismiss="modal">
																		</div>
																	</div>
																</c:forEach>
																<!-- 관심 장소가 없을 경우 -->
																<c:if test="${empty myfavoriteLocationboardList}">
																	<div class="row mt-1">
																		<!-- 알림창 -->
																		<div
																			class="col text-center mb-0 alert alert-secondary alert-dismissible fade show"
																			role="alert">
																			여행지/맛집에서 관심 장소를 추가해주세요
																			<button type="button" class="close"
																				data-dismiss="alert" aria-label="Close">
																				<span aria-hidden="true">&times;</span>
																			</button>
																		</div>
																	</div>
																</c:if>
															</div>
														</div>
													</div>
													<div class="modal-footer">
														<!-- 모달 선택 취소 버튼 -->
														<input type="button" class="btn btn-secondary btn-block"
															data-dismiss="modal" value="선택 취소">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-1">
								<!-- 장소 오른쪽 여백 -->
							</div>
						</div>
						<!-- 장소 끝 -->
						<div class="row mt-1">
							<!-- 루트 내용 입력-->
							<div class="col">
								<!-- 내용 입력 첫번째 줄-->
								<div class="row">
									<div class="nameTag col mt-5 py-3"
										style="border-top: 1px solid #ccc">내용</div>
								</div>
								<div class="row mt-1">
									<!-- 내용 입력 두번째 줄-->
									<div class="col">
										<!-- 내용 입력칸-->
										<textarea name="routeboard_content" class="form-control"
											id="routeboard_content" placeholder="루트 내용을 입력하세요"
											onfocus="this.placeholder=''"
											onblur="this.placeholder='루트 내용을 입력하세요'" rows="10"
											style="resize: none"></textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="row mt-2 mb-5" style="text-align: right">
							<!-- 루트 페이지 버튼-->
							<div class="col" style="display: flex; justify-content: flex-end">
								<input type="button" class="btn btn-success" value="글쓰기"
									onclick="writeContent()">
								<!-- 글쓰기 취소 버튼-->
								<a class="btn btn-outline-success ml-2"
									href="${pageContext.request.contextPath }/routeboard/rt_main_page.do">목록</a>
							</div>
						</div>
					</form>
				</div>
				<!-- 본문 끝 -->

			</div>
		</div>
		<!-- JS File -->
		<script
			src="${pageContext.request.contextPath}/resources/js/routeboard/rt_write_content_page.js"></script>
	</div>
</body>