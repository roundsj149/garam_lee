let intervalRefresh = null;

// 입력 - 엔터키
const keydownEnter = function() {
    if (event.keyCode == 13) {
        writeChat();
    }
}

// 채팅창 - onload
const popchatOnload = function() {
    // 채팅 목록 갱신
    refreshChatList();
    // 목록 갱신 인터벌 세팅
    intervalRefresh = setInterval(refreshChatList, 700);
};

// 기능 - 채팅창 스크롤 아래로 내리기
const scrollSet = function() {
    // 채팅 스크롤 맨 아래로
    let chatList = document.querySelector(`#chat_list`);
    if (chatList.scrollTop != chatList.scrollHeight) {
        chatList.scrollTop = chatList.scrollHeight;
    }
}

// 기능 - 클릭 : 채팅 오픈
const openChat = function() {

    const popupBox = document.querySelector(`#popup-box`);

    popupBox.classList.add('popup-box-on');
    btnOpenChat.classList.add('invisible');
    // 채팅 스크롤 맨 아래로
    scrollSet();
}

// 기능 - 클릭 : 채팅 종료
const closeChat = function() {

    const popupBox = document.querySelector(`#popup-box`);

    popupBox.classList.remove('popup-box-on');
    btnOpenChat.classList.remove('invisible');

}

// 폼 - 클릭 : 채팅 입력창에서 로그인 확인
const checkLoginForPopchat = function() {
    // 세션 값
    const session_check = document.getElementById("session_check").value;

    if (session_check == "false" || session_check == "") {
        alert('로그인 후 이용해주세요.');
        location.href = "../member/login_page.do";
        return;
    }
}

// 버튼 - 클릭 : 전송(채팅 쓰기)
const writeChat = function() {
    // 로그인 확인
    checkLoginForWriteChat();
}

// 확인 - 채팅 쓰기 전에 로그인 확인
const checkLoginForWriteChat = function() {
    // 세션 값
    const session_check = document.getElementById("session_check").value;

    if (session_check == "false" || session_check == "") {
        alert('로그인 후 이용해주세요.');
        location.href = "../member/login_page.do";
        return;
    } else {
        // 채팅 내용을 확인
        checkChatContentForWriteChat();
    }
}

// 확인 - 채팅 내용(스페이스, 엔터 등의 공백)
const checkChatContentForWriteChat = function() {

    const chatContentBox = document.querySelector(`.popchat_content`);
    let chatContent = chatContentBox.value;

    const blankContent = "";
    const blankPattern = /^\s+|\s+$/g;
    chatContent = chatContent.replace(blankPattern, "");
    // 특수문자 치환
    chatContent = encodeURIComponent(chatContent);

    if (chatContent != blankContent) {
        // 채팅 쓰기
        doWriteChat(chatContent);
    } else {
        // 채팅 내용이 빈칸일 경우
        alert('채팅 내용을 입력하지 않았습니다.');
        // 채팅 작성칸 비우기
        chatContentBox.value = "";
    }
}

// 기능 - 채팅 쓰기
const doWriteChat = function(chatContent) {
    // var frm_WriteChat = document.getElementById("frm_WriteChat");
    // frm_WriteChat.submit(); // 채팅 작성 버튼 눌렀을 때 전송됨
    var xmlHttpRequest = new XMLHttpRequest();

    const chatContentBox = document.querySelector(`#popchat_content`);
    // const chatContent = chatContentBox.value;

    xmlHttpRequest.onreadystatechange = function () {

        if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
            // 채팅 작성칸 비우기
            chatContentBox.value = "";
            // 채팅 목록 갱신
            refreshChatList();
        }
        // 채팅 스크롤 맨 아래로
        scrollSet();

    };
    xmlHttpRequest.open("post", "/popchat/pc_write_chat_process.do", true);
    xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttpRequest.send("popchat_content=" + chatContent);
}

// 버튼 - 클릭 : 파일 전송
const uploadFiles = function() {
   // 로그인 확인
   checkLoginForUploadFiles();
}

// 확인 - 파일 전송 전에 로그인 확인
const checkLoginForUploadFiles = function() {
   // 세션 값
   const session_check = document.getElementById("session_check").value;

   if (session_check == "false" || session_check == "") {
      alert('로그인 후 이용해주세요.');
      location.href = "../member/login_page.do";
      return;
   } else {
      // 파일을 확인
      checkChatContentForUploadFiles();
   }
}

// 확인 - 파일 업로드
const checkChatContentForUploadFiles = function() {
   const popchatUploadFiles = document.querySelectorAll(`#popchat_upload_files`);
   let popchatUploadFile = popchatUploadFiles[0].files[0];

   if (popchatUploadFile != null) {
      // 파일 업로드
      douploadFiles(popchatUploadFile);
   } else {
      // 채팅 내용이 빈칸일 경우
      alert('파일을 다시 업로드해 주시기 바랍니다');
      // 채팅 작성칸 비우기
      chatContentBox.value = "";
   }

}

// 기능 - 파일 업로드 처리
const douploadFiles = function(uploadFile) {

    const xmlHttpRequest = new XMLHttpRequest();
    const formData = new FormData();

    const chatContentBox = document.querySelector(`.popchat_content`);
    let chatContent = chatContentBox.value;
    // 이미지만 업로드하기 위해서 채팅 작성칸 비우기
    chatContent = "";
    
    // formData 추가
    formData.append("upload_files", uploadFile);
    formData.append("popchat_content", chatContent);

    xmlHttpRequest.onreadystatechange = function () {

        if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
            // 채팅 작성칸 비우기
            chatContentBox.value = "";
            // 채팅 목록 갱신
            refreshChatList();
        }
        // 채팅 스크롤 맨 아래로
        scrollSet();
    };
    xmlHttpRequest.open("post", "/popchat/pc_write_chat_process.do", true);
    xmlHttpRequest.send(formData);
}

// 기능 - 채팅 목록 갱신
const refreshChatList = function() {

    var xmlHttpRequest = new XMLHttpRequest();

    xmlHttpRequest.onreadystatechange = function () {
        if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
            // 자바 스크립트로 동적 UI 꾸미기
            var resultData = JSON.parse(xmlHttpRequest.responseText);
            // 채팅 박스 안에 내용 지우기
            const chatBox = document.querySelector(`#chat_box`);
            if (chatBox.childNodes != null) {
                const length = chatBox.childNodes.length;
                for (var i = 0; i < length; i++) {
                    chatBox.removeChild(chatBox.childNodes[0]);
                }
            }

            let str = "";
            for (data of resultData) {
                /* 채팅 박스 - 시작 */
                // 채팅 생성 시작
                str += "<div class='direct-chat-msg'>";
                str += "<div class='direct-chat-info clearfix'>";
                // 레벨
                let level = data.levelCategoryVo.level_category_no;
                level = level >= 10 ? level : '0' + level;
                str += "<div class='direct-chat-name'>";
                str += "<div class='badge badge-pill badge-danger level_badge'>Lv.";
                str += level;
                str += "</div>";
                // 닉네임
                str += "<div>";
                str += data.memberVo.member_nickname;
                str += "</div>";
                str += "</div>";
                str += "</div>";
                // 작성 내용
                // 텍스트
                if (data.popchatUploadFileVoList.length == 0) {
                    str += "<div class='direct-chat-text text-break'>" + data.popchatVo.popchat_content + "</div>";
                    str += "<div class='direct-chat-info clearfix'>";
                    str += "<div class='d-flex justify-content-end'>";
                }
                // 이미지
                if (data.popchatUploadFileVoList.length > 0) {
                    const imagePath = "../../routemap/storage/popchat/"+data.popchatUploadFileVoList[0].popchat_file_link_path;
                    str += "<div class='direct-chat-text text-break'>";
                    str += "<img class='popchat_img d-block' src='"+imagePath+"'";
                    str += "alt='이미지를 불러올 수 없습니다.' style='width: 100%; height: 175px;'>";
                    str += "</div>";
                    str += "<div class='direct-chat-info clearfix'>";
                    str += "<div class='d-flex justify-content-end'>";
                }
                // 작성 시간
                const milliseconds = data.popchatVo.popchat_writedate;
                var date = new Date(milliseconds);
                var year = date.getFullYear();             // yyyy
                year = year.toString();
                year = year.substring(2, 4);               // yy 두자리로 저장
                var month = (1 + date.getMonth());         // M
                month = month >= 10 ? month : '0' + month; // MM 두자리로 저장
                var day = date.getDate();                  // d
                day = day >= 10 ? day : '0' + day;         // dd 두자리로 저장
                var hour = date.getHours();                // h
                hour = hour >= 10 ? hour : '0' + hour;     // hh 두자리로 저장
                var min = date.getMinutes();               // m
                min = min >= 10 ? min : '0' + min;         // mm 두자리로 저장
                // var sec = date.getSeconds(); // s
                // sec = sec >= 10 ? sec : '0' + sec; // ss 두자리로 저장
                // yy.MM.dd hh:mm:ss
                str += "<span class='direct-chat-timestamp pull-right'>작성일 " + year + "." + month + "." + day + " " + hour + ":" + min + "</span>";
                str += "</div>";
                str += "</div>";
                str += "</div>";
                // 채팅 생성 끝
                /* 채팅 박스 - 끝 */
            }
            // 채팅 박스 안에 내용 채우기
            $('#chat_box').html(str);
        }
    };
    xmlHttpRequest.open("get", "/popchat/pc_get_chat_list.do", true);
    xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttpRequest.send();
}

// 이벤트 객체 생성
const btnOpenChat = document.querySelector(`#btn_open_chat`);
const btnCloseChat = document.querySelector(`#btn_close_chat`);
const formPopchatContent = document.querySelector(`#popchat_content`);
const btnWriteChat = document.querySelector(`#btn_write_chat`);
const btnOpenFiles = document.querySelector(`#popchat_upload_files`);

// 이벤트 실행
window.addEventListener('load', popchatOnload);
btnOpenChat.addEventListener('click', openChat);
btnCloseChat.addEventListener('click', closeChat);
formPopchatContent.addEventListener('click', checkLoginForPopchat);
btnWriteChat.addEventListener('click', writeChat);
btnOpenFiles.addEventListener('change', uploadFiles);
formPopchatContent.addEventListener('keydown', keydownEnter);