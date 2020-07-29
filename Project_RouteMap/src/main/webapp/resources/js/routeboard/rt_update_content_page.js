/* 관심 장소 추가하기 - 시작 */

// 장소 목록 배열
const dataList = new Array;

window.onload = function () {
   
    // 자바 스크립트로 동적 UI 꾸미기
    const locationboardNoList = document.querySelectorAll(`.locationboard_no`);
    const locationboardTitleList = document.querySelectorAll(`.locationboard_title`);
    const locationCategoryNameList = document.querySelectorAll(`.location_category_name`);
    const locationboardStorenameList = document.querySelectorAll(`.locationboard_storename`);
    const locationboardCostList = document.querySelectorAll(`.locationboard_cost`);
    const locationboardTimeList = document.querySelectorAll(`.locationboard_time`);
    
    for(let index = 0; index < locationboardNoList.length; index++){
                   
       const locationboardVo = {
             locationboard_no : locationboardNoList[index].value,
             locationboard_title : locationboardTitleList[index].value,
             locationboard_storename : locationboardStorenameList[index].value,
             locationboard_cost : locationboardCostList[index].value,
             locationboard_time : locationboardTimeList[index].value
       };
       
       const locationCategoryVo = {
             location_category_name : locationCategoryNameList[index].value   
       }
       
       const data = {
          locationboardVo : locationboardVo,
          locationCategoryVo : locationCategoryVo
       };
       
        dataList.push(data);
        
    }
   console.log("dataList.length");
   console.log(dataList.length);
   
    // 장소 목록 폼 갱신
    refreshLocationboardFormList();
    // 관심 장소 목록 갱신
    refreshMyfavoriteLocationboardModalList();

};



// 버튼 - 장소 추가하기
function buttonAddMyfavoriteLocation(locationboard_no) {
    // 장소 폼 추가
    addMyfavoriteLocationForm(locationboard_no);
}
// 기능 - 장소 추가하기
function addMyfavoriteLocationForm(locationboard_no) {
    // Create XHR Object
    var xhr = new XMLHttpRequest();
    // open Method - Type, Url/File, Asynchronous
    xhr.open("get",
        "call_my_favorite_location_content_page.do?locationboard_no="
        + locationboard_no, true);
    
    
    xhr.send();

    xhr.onload = function () {
        // console.log("ReadyState Value : " + xhr.readyState);
        if (this.status == 200 && this.readyState == 4) {

            // 자바 스크립트로 동적 UI 꾸미기
            const data = JSON.parse(this.responseText);
            dataList.push(data);
            
            // 장소 목록 폼 갱신
            refreshLocationboardFormList();
            // 관심 장소 목록 갱신
            refreshMyfavoriteLocationboardModalList();

        } else if (this.status == 404) {
            document.getElementById("locationboard_form_list").innerHTML = "장소를 찾을 수 없습니다";
        }
    }

}

// 댓글 삭제 확인
function buttonDeleteReply(deleteByreplyNo) {

    if (confirm("댓글을 삭제하시겠습니까?")) {    // 확인

        deleteReply(deleteByreplyNo);

    } else {   // 취소
        return false;
    }
}

// 버튼 - 추가하기 취소
function buttonCancelAddMyfavoriteLocation(locationboard_no, index) {

    // 추가하기 취소 확인창
    if (confirm("장소 추가를 취소하시겠습니까?")) {    // 확인

        cancleMyfavoriteLocationForm(locationboard_no, index);

    } else {   // 취소
        return false;
    }

}

// 기능 - 취소 : 장소 추가하기
function cancleMyfavoriteLocationForm(locationboard_no, index) {

    // 데이터 가져온거 취소
    dataList.splice(index, 1);

    // 관심 장소 가져오기 목록을 동적으로
    const buttonAddMyfavoriteLocationboard = document.querySelector(`.button_add_myfavorite_locationboard_no_${locationboard_no}`);
    buttonAddMyfavoriteLocationboard.classList.toggle("d-none");

    refreshLocationboardFormList();

}

// 기능 - 비용, 소요 시간 값 유지
function keepValue(index, value_name) {

    const keep_input_value = document.querySelectorAll(`.${value_name}`);

    if (value_name === "input_link_cost") {
        dataList[index].locationboardVo.locationboard_cost = parseInt(keep_input_value[index].value);
    }

    if (value_name === "input_link_time") {

        const link_time_hour = document.querySelectorAll(`.link_time_hour`);
        const link_time_minute = document.querySelectorAll(`.link_time_minute`);

        dataList[index].locationboardVo.locationboard_time = parseInt(link_time_hour[index].value) * 60 + parseInt(link_time_minute[index].value);

    }
}

// 기능 - 장소 목록 갱신
function refreshLocationboardFormList() {

    /* 장소 목록 갱신 - 시작 */
    const locationboardFormList = document
        .querySelector(`.locationboard_form_list`);

    // 장소 목록 초기화
    var length = locationboardFormList.childNodes.length;
    for (var i = 0; i < length; i++) {
        locationboardFormList.removeChild(locationboardFormList.childNodes[0]);
    }

    for (var index = 0; index < dataList.length; index++) {

        const linkOrder = index + 1;
        const data = dataList[index];

        /* 장소 게시글 폼 추가 시작 */
        const locationboardFormWrap = document.createElement("div");
        locationboardFormWrap.setAttribute("class",
            `locationboard_form row border my-3`);
        locationboardFormWrap.setAttribute("style", "padding:20px; background-color:#f8f9fa; border-radius: 20px; box-shadow: 3px 3px 5px 5px #eaeaea;")

        const locationboardForm = document.createElement("div");
        locationboardForm.setAttribute("class",
            `locationboard_form col`);

        const locationboardFormHeader = document.createElement("div");
        locationboardFormHeader.setAttribute("class",
            `locationboard_form_header row`);

        const locationboardFormBody = document.createElement("div");
        locationboardFormBody.setAttribute("class",
            `locationboard_form_body row`);

        const locationboardFormFooter = document.createElement("div");
        locationboardFormFooter.setAttribute("class",
            `locationboard_form_footer row mt-2 mb-3`);

        const headerValueBox = document.createElement("div");
        headerValueBox.setAttribute("class", `header_value_box col`);

        const bodyContentBox = document.createElement("div");
        bodyContentBox.setAttribute("class", `body_content_box col-9`);

        const bodyButtonBox = document.createElement("div");
        bodyButtonBox.setAttribute("class", `body_button_box col`);
        bodyButtonBox.setAttribute("style","margin-top:50px; display:flex; flex-direction:column; align-self:center");

        const footerButtonBox = document.createElement("div");
        footerButtonBox.setAttribute("class", `footer_button_box col`);

        /* headerValueBox */
        // headerValueBox - Row1
        const headerValueBoxRow1 = document.createElement("div");
        headerValueBoxRow1.setAttribute("class", "row");
        // headerValueBoxRow1_col1 - 링크 오더
        const headerValueBoxRow1_col1 = document.createElement("div");
        headerValueBoxRow1_col1.setAttribute("class", "col");
        // 값 - 링크 오더
        const hiddenlinkOrder = document.createElement("input");
        hiddenlinkOrder.setAttribute("type", "hidden");
        hiddenlinkOrder.setAttribute("class", `link_order form-control`);
        hiddenlinkOrder.setAttribute("name", "link_order");
        hiddenlinkOrder.setAttribute("value", linkOrder);
        // headerValueBox - Row1 결합
        headerValueBoxRow1_col1.appendChild(hiddenlinkOrder);
        headerValueBoxRow1.appendChild(headerValueBoxRow1_col1);
        // headerValueBox - Row2
        const headerValueBoxRow2 = document.createElement("div");
        headerValueBoxRow2.setAttribute("class", "row");
        // headerValueBoxRow2_col1
        const headerValueBoxRow2_col1 = document.createElement("div");
        headerValueBoxRow2_col1.setAttribute("class", "col");
        // 값 - 장소 글 번호
        const hiddenLocationboardNo = document.createElement("input");
        hiddenLocationboardNo.setAttribute("type", "hidden");
        hiddenLocationboardNo.setAttribute("class", `locationboard_no form-control`);
        hiddenLocationboardNo.setAttribute("name", "locationboard_no");
        hiddenLocationboardNo.setAttribute("value",
            data.locationboardVo.locationboard_no);
        // headerValueBox - Row2 결합
        headerValueBoxRow2_col1.appendChild(hiddenLocationboardNo);
        headerValueBoxRow2.appendChild(headerValueBoxRow2_col1);

        /* bodyContentBox */
        // contentBoxRow1 - 장소 제목
        const contentBoxRow1 = document.createElement("div");
        contentBoxRow1.setAttribute("class", "row mt-3");
        // contentBoxRow1_col1 - 좌측칸 : 값의 이름(장소 제목)
        const contentBoxRow1_col1 = document.createElement("div");
        contentBoxRow1_col1.setAttribute("class", "col-3");
        contentBoxRow1_col1.setAttribute("style", "font-weight:bold");
        contentBoxRow1_col1.innerText = "제목";
        // contentBoxRow1_col2 - 우측칸 : 값(장소 제목)
        const contentBoxRow1_col2 = document.createElement("div");
        contentBoxRow1_col2.setAttribute("class", "col");
        contentBoxRow1_col2.innerText = data.locationboardVo.locationboard_title;
        // boxRow1 결합
        contentBoxRow1.appendChild(contentBoxRow1_col1);
        contentBoxRow1.appendChild(contentBoxRow1_col2);

        // contentBoxRow2 - 분류 : 여행지 / 맛집
        const contentBoxRow2 = document.createElement("div");
        contentBoxRow2.setAttribute("class", "row mt-3");
        // contentBoxRow2_col1 - 좌측칸 : 값의 이름(분류)
        const contentBoxRow2_col1 = document.createElement("div");
        contentBoxRow2_col1.setAttribute("class", "col-3");
        contentBoxRow2_col1.setAttribute("style", "font-weight:bold");
        contentBoxRow2_col1.innerText = "분류";
        // contentBoxRow2_col2 - 우측칸 : 값(분류)
        const contentBoxRow2_col2 = document.createElement("div");
        contentBoxRow2_col2.setAttribute("class", "col");
        contentBoxRow2_col2.innerText = data.locationCategoryVo.location_category_name;
        // contentBoxRow2 결합
        contentBoxRow2.appendChild(contentBoxRow2_col1);
        contentBoxRow2.appendChild(contentBoxRow2_col2);

        // contentBoxRow3 - 상호명
        const contentBoxRow3 = document.createElement("div");
        contentBoxRow3.setAttribute("class", "row mt-3");
        // contentBoxRow3_col1 - 좌측칸 : 값의 이름(상호명)
        const contentBoxRow3_col1 = document.createElement("div");
        contentBoxRow3_col1.setAttribute("class", "col-3");
        contentBoxRow3_col1.setAttribute("style", "font-weight:bold");
        contentBoxRow3_col1.innerText = "장소명";
        // contentBoxRow3_col2 - 우측칸 : 값(상호명)
        const contentBoxRow3_col2 = document.createElement("div");
        contentBoxRow3_col2.setAttribute("class", "col");
        contentBoxRow3_col2.innerText = data.locationboardVo.locationboard_storename;
        // contentBoxRow3 결합
        contentBoxRow3.appendChild(contentBoxRow3_col1);
        contentBoxRow3.appendChild(contentBoxRow3_col2);

        // contentBoxRow4 - 비용
        const contentBoxRow4 = document.createElement("div");
        contentBoxRow4.setAttribute("class", "row mt-3");
        // contentBoxRow4_col1 - 좌측칸 : 값의 이름(비용)
        const contentBoxRow4_col1 = document.createElement("div");
        contentBoxRow4_col1.setAttribute("class", "col-3");
        contentBoxRow4_col1.setAttribute("style", "font-weight:bold");
        contentBoxRow4_col1.innerText = "비용";
        // contentBoxRow4_col2 - 우측칸 : 값(비용)
        const contentBoxRow4_col2 = document.createElement("div");
        contentBoxRow4_col2.setAttribute("class", "col");
        const inputLinkCost = document.createElement("input");
        inputLinkCost.setAttribute("type", "text");
        inputLinkCost.setAttribute("class", `input_link_cost form-control form-control-sm`);
        inputLinkCost.setAttribute("onblur", `keepValue(${index}, 'input_link_cost')`);
        inputLinkCost.setAttribute("name", "link_cost");
        inputLinkCost.setAttribute("value",
            data.locationboardVo.locationboard_cost);
        // contentBoxRow4 결합
        contentBoxRow4_col2.appendChild(inputLinkCost);
        contentBoxRow4.appendChild(contentBoxRow4_col1);
        contentBoxRow4.appendChild(contentBoxRow4_col2);

        // contentBoxRow5 - 소요 시간
        // 링크 시간, 링크 분
        const link_time_hour = parseInt(data.locationboardVo.locationboard_time / 60);
        const link_time_minute = data.locationboardVo.locationboard_time % 60;

        const contentBoxRow5 = document.createElement("div");
        contentBoxRow5.setAttribute("class", "row mt-3");
        // contentBoxRow5_col1 - 좌측칸 : 값의 이름(소요 시간)
        const contentBoxRow5_col1 = document.createElement("div");
        contentBoxRow5_col1.setAttribute("class", "col-3");
        contentBoxRow5_col1.innerText = "소요 시간";

        // contentBoxRow5_col2 - 우측칸 : 값(소요 시간)
        const contentBoxRow5_col2 = document.createElement("div");
        contentBoxRow5_col2.setAttribute("class", "col");
        const inputLinkTimeHour = document.createElement("input");
        inputLinkTimeHour.setAttribute("type", "text");
        inputLinkTimeHour.setAttribute("id", "link_time_hour");
        inputLinkTimeHour.setAttribute("class", `link_time_hour form-control form-control-sm`);
        inputLinkTimeHour.setAttribute("onblur", `keepValue(${index}, 'input_link_time')`);
        inputLinkTimeHour.setAttribute("name", "link_time_hour");
        inputLinkTimeHour.setAttribute("value", link_time_hour);
        const contentBoxRow5_col3 = document.createElement("div");
        contentBoxRow5_col3.setAttribute("class", "col");
        contentBoxRow5_col3.innerHTML = "<label for='link_time_hour' style='font-weight:bold');>시간</label>";
        const contentBoxRow5_col4 = document.createElement("div");
        contentBoxRow5_col4.setAttribute("class", "col");
        const inputLinkTimeMinute = document.createElement("input");
        inputLinkTimeMinute.setAttribute("type", "text");
        inputLinkTimeMinute.setAttribute("id", "link_time_minute");
        inputLinkTimeMinute.setAttribute("class", `link_time_minute form-control form-control-sm`);
        inputLinkTimeMinute.setAttribute("onblur", `keepValue(${index}, 'input_link_time')`);
        inputLinkTimeMinute.setAttribute("name", "link_time_minute");
        inputLinkTimeMinute.setAttribute("value", link_time_minute);
        const contentBoxRow5_col5 = document.createElement("div");
        contentBoxRow5_col5.setAttribute("class", "col");
        contentBoxRow5_col5.innerHTML = "<label for='link_time_minute' style='font-weight:bold'>분</label>";
        // contentBoxRow5 결합
        contentBoxRow5_col2.appendChild(inputLinkTimeHour);
        contentBoxRow5_col4.appendChild(inputLinkTimeMinute);
        contentBoxRow5.appendChild(contentBoxRow5_col1);
        contentBoxRow5.appendChild(contentBoxRow5_col2);
        contentBoxRow5.appendChild(contentBoxRow5_col3);
        contentBoxRow5.appendChild(contentBoxRow5_col4);
        contentBoxRow5.appendChild(contentBoxRow5_col5);

        /* bodyButtonBox */
        // bodyButtonBoxRow1 - 링크 오더 위로 변경
        const bodyButtonBoxRow1 = document.createElement("div");
        bodyButtonBoxRow1.setAttribute("class",
            "row locationboard_form_footer mt-1");
        bodyButtonBoxRow1.setAttribute("style", "text-align: right");
        // bodyButtonBoxRow1_col1 - 버튼 링크 순서 변경
        const bodyButtonBoxRow1_col1 = document.createElement("div");
        bodyButtonBoxRow1_col1.setAttribute("class", "col");
        // 버튼 - 링크 순서 위로 변경
        const buttonMoveUpOrder = document.createElement("i");
        buttonMoveUpOrder.setAttribute("class",
        `button_move_up_order fas fa-chevron-up fa-2x`);
        buttonMoveUpOrder.setAttribute("style","padding:10px 0;color:green; cursor:pointer")
        buttonMoveUpOrder.setAttribute("onclick", `buttonMoveUpOrder(${index})`);
        buttonMoveUpOrder.setAttribute("value", "순서 위로 변경");
        // 예외처리 - 버튼 -링크 순서 위로 변경
        if (index === 0) {
            // 클래스 목록에서 클래스 존재 확인
            const hasClassInvisible = buttonMoveUpOrder.classList
                .contains("invisible");
            if (!hasClassInvisible) {
                buttonMoveUpOrder.setAttribute("class",
                    `button_move_up_order fas fa-chevron-down invisible`);
            } else {
                buttonMoveUpOrder.setAttribute("class",
                    `button_move_up_order fas fa-chevron-down`);
            }
        }
        // bodyButtonBoxRow1 결합
        bodyButtonBoxRow1_col1.appendChild(buttonMoveUpOrder);
        bodyButtonBoxRow1.appendChild(bodyButtonBoxRow1_col1);

        // bodyButtonBoxRow2 - 링크 오더 아래로 변경
        const bodyButtonBoxRow2 = document.createElement("div");
        bodyButtonBoxRow2.setAttribute("class",
            "row locationboard_form_footer mt-1");
        bodyButtonBoxRow2.setAttribute("style", "text-align: right");
        // bodyButtonBoxRow2_col1 - 버튼 링크 순서 변경
        const bodyButtonBoxRow2_col1 = document.createElement("div");
        bodyButtonBoxRow2_col1.setAttribute("class", "col");
        // 버튼 - 링크 순서 아래로 변경
        const buttonMoveDownOrder = document.createElement("i");
        buttonMoveDownOrder.setAttribute("class",
        `button_move_down_order fas fa-chevron-down fa-2x`);
        buttonMoveDownOrder.setAttribute("onclick",
            `buttonMoveDownOrder(${index})`);
        buttonMoveDownOrder.setAttribute("value", "순서 아래로 변경");
        buttonMoveDownOrder.setAttribute("style", "padding:10px 0; color:green; cursor:pointer;");
        // 예외처리 - 버튼 -링크 순서 아래로 변경
        if (index === 0 && index === dataList.length - 1 || index !== 0
            && index === dataList.length - 1) {
            // 클래스 목록에서 클래스 존재 확인
            const hasClassInvisible = buttonMoveDownOrder.classList
                .contains("invisible");
            if (!hasClassInvisible) {
                buttonMoveDownOrder.setAttribute("class",
                    `button_move_down_order fas fa-chevron-down fa-2x invisible`);
            } else {
                buttonMoveDownOrder.setAttribute("class",
                    `button_move_down_order fas fa-chevron-down fa-2x`);
            }
        }
        // bodyButtonBoxRow2 결합
        bodyButtonBoxRow2_col1.appendChild(buttonMoveDownOrder);
        bodyButtonBoxRow2.appendChild(bodyButtonBoxRow2_col1);

        /* footerButtonBox */
        // locationboardFormFooterRow1
        const footerButtonBoxRow1 = document.createElement("div");
        footerButtonBoxRow1.setAttribute("class",
            "row locationboard_form_footer mt-1");
        footerButtonBoxRow1.setAttribute("style", "text-align: center");
        // footerButtonBoxRow1_col1 - 장소 폼 버튼
        const footerButtonBoxRow1_col1 = document.createElement("div");
        footerButtonBoxRow1_col1.setAttribute("class", "col text-right");
        // 장소 가져오기 취소 버튼
        const cancelAddButton = document.createElement("i");
        cancelAddButton.setAttribute("type", "button");
        cancelAddButton.setAttribute("onclick",
            `buttonCancelAddMyfavoriteLocation(
                ${data.locationboardVo.locationboard_no}, ${index})`);
        cancelAddButton.setAttribute("class", `fas fa-times-circle fa-2x`);
        cancelAddButton.setAttribute("style", "cursor:pointer; color:grey");
        // locationboardFormFooterRow1 결합
        footerButtonBoxRow1_col1.appendChild(cancelAddButton);
        footerButtonBoxRow1.appendChild(footerButtonBoxRow1_col1);

        /* locationboardForm 조립하기 */
        /* locationboardFormHeader에 조립 */
        headerValueBox.appendChild(headerValueBoxRow1);
        headerValueBox.appendChild(headerValueBoxRow2);
        locationboardFormHeader.appendChild(headerValueBox);

        /* locationboardFormBody에 조립 */
        bodyContentBox.appendChild(contentBoxRow1);
        bodyContentBox.appendChild(contentBoxRow2);
        bodyContentBox.appendChild(contentBoxRow3);
        bodyContentBox.appendChild(contentBoxRow4);
        bodyContentBox.appendChild(contentBoxRow5);
        //
        bodyButtonBox.appendChild(bodyButtonBoxRow1);
        bodyButtonBox.appendChild(bodyButtonBoxRow2);
        //
        locationboardFormBody.appendChild(bodyContentBox);
        locationboardFormBody.appendChild(bodyButtonBox);

        /* locationboardFormFooter에 조립 */
        footerButtonBox.appendChild(footerButtonBoxRow1);
        locationboardFormFooter.appendChild(footerButtonBox);

        /* locationboardForm에 조립 */
        locationboardForm.appendChild(locationboardFormHeader);
        locationboardForm.appendChild(locationboardFormBody);
        locationboardForm.appendChild(locationboardFormFooter);

        /* locationboardFormList에 조립 */
        locationboardFormWrap.appendChild(locationboardForm)
        locationboardFormList.appendChild(locationboardFormWrap);
        /* 장소 게시글 폼 추가 끝 */
    }
    /* 장소 목록 갱신 - 시작 */
}

// 기능 - 관심 장소 목록 갱신
function refreshMyfavoriteLocationboardModalList() {
    /* 관심 장소 목록을 동적으로 시작 */
    // 장소 목록 폼의 장소 번호들 가져오기
    locationboardNoList = document.querySelectorAll(`.locationboard_no`);
    let index = 0;
    while (index < locationboardNoList.length) {
        // 관심 장소 글 번호
        const locationboard_no = locationboardNoList[index].value
        // 클래스 목록에서 클래스 존재 확인
        const buttonAddMyfavoriteLocationboard = document.querySelector(`.button_add_myfavorite_locationboard_no_${locationboard_no}`);
        const hasClassD_none = buttonAddMyfavoriteLocationboard.classList.contains("d-none");
        // 관심 장소 가져오기 목록에서 가져온 장소 숨기기
        if (!hasClassD_none) {
            buttonAddMyfavoriteLocationboard.setAttribute("class", `row mt-1 button_add_myfavorite_locationboard_no_${locationboard_no} d-none`);
        }

        index++;

    }
    /* 관심 장소 목록을 동적으로 끝 */
}

// 버튼 - 링크 순서 아래로 변경
function buttonMoveDownOrder(index) {

    // 순서 아래로 변경
    doMoveDownOrder(index);

}

// 기능 - 순서 아래로 변경
function doMoveDownOrder(index) {

    // 링크 오더 번호
    const upperNo = index;
    const downerNo = index + 1;

    tempData = dataList[downerNo];
    dataList[downerNo] = dataList[upperNo];
    dataList[upperNo] = tempData;
    // 장소 목록 갱신
    refreshLocationboardFormList();

}

// 버튼 - 링크 순서 위로 변경
function buttonMoveUpOrder(index) {

    // 순서 위로 변경
    doMoveUpOrder(index);

}

// 기능 - 순서 위로 변경
function doMoveUpOrder(index) {

    // 링크 오더 번호
    const upperNo = index - 1;
    const downerNo = index;
    tempData = dataList[downerNo];
    dataList[downerNo] = dataList[upperNo];
    dataList[upperNo] = tempData;
    // 장소 목록 갱신
    refreshLocationboardFormList();

}

// 기능 - 글쓰기
var checkDataIndex = 0;
function writeContent() {

    const frm = document.querySelector(`#frm`);

    // 제목 유효성 체크
    const routeboard_title = document.querySelector(`#routeboard_title`);
    if (routeboard_title.value == null || routeboard_title.value.replace(/^\s+|\s+$/g, "") == "") {
        alert('제목을 입력해주세요');
        routeboard_title.value = "";
        routeboard_title.focus();
        return;
    }

    // 내용 유효성 체크
    const routeboard_content = document.querySelector(`#routeboard_content`);
    if (routeboard_content.value == null || routeboard_content.value.replace(/^\s+|\s+$/g, "") == "") {
        alert('내용을 입력해주세요');
        routeboard_content.value = "";
        routeboard_content.focus();
        return;
    }

    // 장소 추가 체크 : 장소를 추가하지 않고 루트 등록을 누르나
    const call_lc_myfavorite = document.querySelector(`#call_lc_myfavorite`);
    if (dataList.length == 0) {
        alert('장소를 추가해주세요');
        call_lc_myfavorite.focus();
        return;
    }

    while (checkDataIndex < dataList.length) {
        // 비용 유효성 체크
        const costReg = /^\d{0,6}$/; // 6자리 이하의 숫자
        var input_link_cost = document.querySelectorAll(`.input_link_cost`);

        console.log("checkDataIndex");
        console.log(checkDataIndex);

        if (input_link_cost[checkDataIndex].value == null || input_link_cost[checkDataIndex].value.replace(/^\s+|\s+$/g, "") == "") {
            alert('비용을 입력해주세요');
            input_link_cost[checkDataIndex].value = "";
            input_link_cost[checkDataIndex].focus();
            checkDataIndex = 0;
            return;
        }

        if (!costReg.test(input_link_cost[checkDataIndex].value)) {
            alert("비용은 6자리 이하의 숫자만 입력하셔야 합니다");
            input_link_cost[checkDataIndex].value = "";
            input_link_cost[checkDataIndex].focus();
            checkDataIndex = 0;
            return;
        }

        // 소요시간 - 시간 유효성 체크
        const hourReg = /^([0-9]|1[0-9]|2[0-4])$/; // 2자리 이하의 숫자
        const link_time_hour = document.querySelectorAll(`.link_time_hour`);
        if (link_time_hour[checkDataIndex].value == null || link_time_hour[checkDataIndex].value.replace(/^\s+|\s+$/g, "") == "") {
            alert('시간을 입력해주세요');
            link_time_hour[checkDataIndex].value = "";
            link_time_hour[checkDataIndex].focus();
            checkDataIndex = 0;
            return;
        }
        if (!hourReg.test(link_time_hour[checkDataIndex].value)) {
            alert("시간은  0~24 사이의 숫자만 입력하셔야 합니다");
            link_time_hour[checkDataIndex].value = "";
            link_time_hour[checkDataIndex].focus();
            checkDataIndex = 0;
            return;
        }

        // 소요시간 - 분 유효성 체크
        // 2자리 이하의 숫자
        const minuteReg = /^([0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])$/;
        const link_time_minute = document.querySelectorAll(`.link_time_minute`);
        if (link_time_minute[checkDataIndex].value == null || link_time_minute[checkDataIndex].value.replace(/^\s+|\s+$/g, "") == "") {
            alert('분을 입력해주세요');
            link_time_minute[checkDataIndex].value = "";
            link_time_minute[checkDataIndex].focus();
            checkDataIndex = 0;
            return;
        }
        if (!minuteReg.test(link_time_minute[checkDataIndex].value)) {
            alert("분은  0~59 사이의 숫자만 입력하셔야 합니다");
            link_time_minute[checkDataIndex].value = "";
            link_time_minute[checkDataIndex].focus();
            checkDataIndex = 0;
            return;
        }
        checkDataIndex = checkDataIndex + 1;
    }
    checkUnload = false;
    frm.submit();
}

/* 관심 장소 추가하기 - 끝 */

//확인 - 글쓰기 중 새로고침, 뒤로가기 등등 눌렀을 때 경고창 띄우기
var checkUnload = true;
window.onbeforeunload = function () {
    if (checkUnload) return "이 페이지를 벗어나면 작성된 내용은 저장되지 않습니다.";
};