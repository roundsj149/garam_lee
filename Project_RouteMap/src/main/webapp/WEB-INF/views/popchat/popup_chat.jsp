<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- popup_chat의 css -->
<link rel="stylesheet"
   href="${pageContext.request.contextPath}/resources/css/popchat/popup_chat.css">
   
<!-- 채팅창 팝업 버튼  -->
<div
   class="text-center position-sticky d-flex justify-content-end">
   <div class="row">
      <div class="round hollow text-center">
         <a href="javascript:void(0);" id="btn_open_chat"><span>Open
               in chat</span></a>
      </div>
   </div>
</div>

<div class="popup-box chat-popup" id="popup-box">
   <div class="popup-head">
      <div class="popup-head-left pull-left">
         <!-- 채팅창 로고 -->
         <div>
            <img
               src="${pageContext.request.contextPath}/resources/img/popupchat_logo.png"
               alt="iamgurdeeposahan">
            <!-- 채팅창 타이틀 -->
            <span>RouteMap Chat</span>
            <!-- 세션 체크 -->
            <input type="hidden" id="session_check"
               value="${!empty sessionUser }">
            <!-- 채팅창 종료 버튼 -->
            <span> <a href="javascript:void(0);"><i
                  class="far fa-times-circle fa-lg" data-widget="remove"
                  id="btn_close_chat" style="color: #f06595; float: right;"></i></a>
            </span>
         </div>
      </div>
   </div>
   <!-- 채팅 목록 시작 -->
   <div id="chat_list" class="popup-messages">
      <!-- 채팅 생성 시작 -->
      <div id="chat_box" class="direct-chat-messages">
         <div class="direct-chat-msg">
            <div class="direct-chat-info clearfix">
               <!-- 닉네임 -->
               <span class="direct-chat-name pull-left"></span>
            </div>
            <!-- 작성 내용 -->
            <div class="direct-chat-text text-break"></div>
            <div class="direct-chat-info clearfix">
               <div class="d-flex justify-content-end">
                  <!-- 작성 시간 -->
                  <span class="direct-chat-timestamp pull-right"></span>
               </div>
            </div>
         </div>
      </div>
      <!-- 채팅 생성 끝 -->
   </div>
   <!-- 채팅 목록 끝 -->
   <div class="popup-messages-footer">
      <textarea id="popchat_content" class="popchat_content form-control"
         placeholder="   채팅을 입력해주세요" onfocus="this.placeholder=''"
         onblur="this.placeholder='   채팅을 입력해주세요'" rows="10" cols="40"
         name="popchat_content"></textarea>
      <div class="btn-footer">
         <div style="vertical-align: middle;">
            <div>
               <!-- 이미지 업로드 버튼 -->
               <a class="upload_box" href="javascript:void(0);" id="btn_Upload_files"><label
                  for="popchat_upload_files"><i class="far fa-image fa-2x"
                     style="color: #f06595; float: left;"></i></label><input type="file"
                  name="popchat_upload_files" id="popchat_upload_files" multiple accept="image/*"></a>
               <!-- 채팅 전송 버튼 -->
               <span class="btn_write_chat float-right d-flex align-items-center">
                  <a href="javascript:void(0);" id="btn_write_chat">전송</a>
               </span>
            </div>
         </div>
      </div>
   </div>
</div>
<!-- popup_chat의 js -->
<script
   src="${pageContext.request.contextPath}/resources/js/popchat/popup_chat.js"></script>