package com.explorer.routemap.popchat.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.popchat.service.PopchatServiceImpl;
import com.explorer.routemap.popchat.vo.PopchatVo;

@Controller
@RequestMapping("/*")
public class PopchatController {
   
   @Autowired
   PopchatServiceImpl popchatService;
   
   // 채팅창 - 채팅 쓰기 처리
   @ResponseBody
   @RequestMapping("popchat/pc_write_chat_process.do")
   public void writeChatProcess(HttpSession session, String popchat_content, MultipartFile[] upload_files) {
      
      // 유효성 검사
      String check_popchat_content = StringEscapeUtils.escapeHtml4(popchat_content);
      // 예외 처리
      if(check_popchat_content == null) {
         check_popchat_content = "";
      }
      
      MemberVo memberVo = (MemberVo)session.getAttribute("sessionUser");
      PopchatVo popchatVo = new PopchatVo();
      popchatVo.setMember_no(memberVo.getMember_no());
      popchatVo.setPopchat_content(check_popchat_content);
      
      popchatService.writeContet(popchatVo);
      
      // 파일 업로드
      if(upload_files != null) {
         
         for (MultipartFile file : upload_files) {
            if (file.getOriginalFilename().isEmpty()) {

               continue;

            } else {
               
               popchatService.uploadfiles(file, popchatVo.getPopchat_no());
               
            }
         }
      }
   }
   // 채팅창 - 파일 업로드 처리
   @ResponseBody
   @RequestMapping("popchat/pc_upload_file_process.do")
   public void uploadFileProcess(HttpSession session, String popchat_content, MultipartFile[] upload_files) {
      
      // 유효성 검사
      String check_popchat_content = StringEscapeUtils.escapeHtml4(popchat_content);
      
      MemberVo memberVo = (MemberVo)session.getAttribute("sessionUser");
      PopchatVo popchatVo = new PopchatVo();
      popchatVo.setMember_no(memberVo.getMember_no());
      popchatVo.setPopchat_content(check_popchat_content);
      
      popchatService.writeContet(popchatVo);
      
   }
   // 채팅창 - 채팅 목록
   @ResponseBody
   @RequestMapping("popchat/pc_get_chat_list.do")
   public List<Map<String, Object>> getChatList() {
      
      List<Map<String, Object>> list = popchatService.getChatList();
      
      return list;
      
   }
}