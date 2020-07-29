package com.explorer.routemap.note.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.note.service.NoteServiceImpl;
import com.explorer.routemap.note.vo.NoteVo;

@Controller
@RequestMapping("/note/*")
public class NoteController {
   
   @Autowired
   NoteServiceImpl noteService;

   // 쪽지 쓰기 페이지
   @RequestMapping("/nt_main_page.do")
   public String notePage(){
      return "note/nt_main_page";
   }
   
   // 쪽지 보내기 처리
   @RequestMapping("/nt_send_process.do")
   public String noteSendProcess(HttpSession session, NoteVo noteVo) {
      MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
      noteService.insertBySendNote(memberVo, noteVo);
      return "redirect:/note/nt_main_page.do";
   }
   
   // 받은 쪽지함 페이지 Ajax Rest처리
   @ResponseBody
   @RequestMapping("/nt_recv_page_rest.do")
   public List<NoteVo> noteRecvPageRestful(HttpSession session, 
         Model model,
         @RequestParam(value="currPage", required=false, defaultValue="1") int currPage) {
            
      try{
         
      }catch (Exception e) {
         // TODO: handle exception
         System.out.println("쪽지 예외처리 : "+e.getMessage());
      }
      
      MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
      if(memberVo == null) {
         return null;
      }

      List<NoteVo> list = noteService.selectByRecvNote(memberVo.getMember_no(), currPage);
      // 받은 쪽지함, 보낸 쪽지함 글 삭제 모두 Y표시일때 데이터상의 글 삭제
      noteService.deleteByNoteList();
      
      return list;
   }
   // 받은 쪽지함 전체 total rest처리
   @ResponseBody
   @RequestMapping("/nt_recv_totalcount_rest.do")
   public HashMap<String,Object> noteRecvTotalCount(HttpSession session) {
      MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
      int count = noteService.getRecvNoteDataCount(memberVo.getMember_no());
      
      HashMap<String,Object> map = new HashMap<String,Object>();
      
      map.put("count", count);
      
      return map;
   }
      
   // 받은 쪽지함 페이지
   @RequestMapping("/nt_recv_page.do")
   public String noteRecvPage(HttpSession session, 
         Model model,
         @RequestParam(value="currPage", required=false, defaultValue="1") int currPage) {
      
      MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
      if(memberVo != null) {
         List<NoteVo> list = noteService.selectByRecvNote(memberVo.getMember_no(), currPage);
         // 받은 쪽지함, 보낸 쪽지함 글 삭제 모두 Y표시일때 데이터상의 글 삭제
         noteService.deleteByNoteList();
         
         //받은 쪽지함 전체 글 수
         int totalCount = noteService.getRecvNoteDataCount(memberVo.getMember_no());
         
         int beginPage = ((currPage-1)/10)*10+1;
         int endPage = beginPage+9;
         
         endPage = (endPage > ((currPage-1)/10)*10+1) ? ((totalCount-1)/10)+1 : endPage;
         
         model.addAttribute("beginPage", beginPage);
         model.addAttribute("endPage", endPage);
         model.addAttribute("currPage", currPage);
         model.addAttribute("totalCount", totalCount);
         model.addAttribute("recvList", list);
      }
      
      return "note/nt_recv_page";
   }
      
   // 보낸 쪽지함 페이지
   @RequestMapping("/nt_send_page.do")
   public String noteSendPage(HttpSession session, 
         Model model,
         @RequestParam(value="currPage", required=false, defaultValue="1") int currPage
         ) {
      
      
      MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
      if(memberVo != null) {
         
         System.out.println("보낸 쪽지함 페이지 : "+memberVo.getMember_no());
         List<NoteVo> list = noteService.selectBySendNote(memberVo.getMember_no(), currPage);
         // 받은 쪽지함, 보낸 쪽지함 글 삭제 모두 Y표시일때 데이터상의 글 삭제
         noteService.deleteByNoteList();
         
         //보낸 쪽지함 전체 글 수
         int totalCount = noteService.getSendNoteDataCount(memberVo.getMember_no());
         
         int beginPage = ((currPage-1)/10)*10+1; //ex.currPage = 1일 경우 / 결과값 : 1 / currPage가 1~10인 경우 ((currPage-1)/10) 이 부분이 내부적으로 1이다..
         int endPage = beginPage+9;    //(((currPage-1)/10)+1)*10; //11

         endPage = (endPage >((totalCount-1)/10)+1) ? ((totalCount-1)/10)+1 : endPage;
               
         model.addAttribute("beginPage", beginPage);
         model.addAttribute("endPage", endPage);
         model.addAttribute("currPage", currPage);
         model.addAttribute("totalCount", totalCount);
         model.addAttribute("sendList", list);
      }
      
      return "/note/nt_send_page";
   }
   
   // 보낸 쪽지함 발송 취소 처리
   @RequestMapping("/nt_send_delete_process.do")
   public String noteSendDeleteProcess(int note_no) {
      noteService.deleteBySendNote(note_no); // 발송 취소
      System.out.println("발송 취소 controller : "+note_no);
   
      return "redirect:/note/nt_send_page.do";
   }
   
   // 받은 쪽지함 읽기 페이지
   @RequestMapping("/nt_recv_read_page.do")
   public String noteRecvReadPage(int note_no, Model model) {
      Map<String, Object> map = noteService.selectByRecvReadNote(note_no);
      System.out.println("Notecontroller 받은 쪽지 읽기 페이지 : "+note_no);
      
      model.addAttribute("recvReadNote", map);
      System.out.println("ddddd1 : "+map.size());

      return "note/nt_recv_read_page";
   }
   
   // 보낸 쪽지함 읽기 페이지
   // 출력되는지 체크해야함
   @RequestMapping("/nt_send_read_page.do")
   public String noteSendReadPage(int note_no, Model model) {
      NoteVo noteVo = noteService.selectBySendReadNote(note_no);
      
      model.addAttribute("sendReadNote", noteVo);
      return "note/nt_send_read_page";
   }
   
   
   //보낸 쪽지함 글 삭제 시 NOTE_SEND_DELETE Y로 변경 
   @RequestMapping("nt_update_send_note_delete_process.do")
   public String updateBySendNoteList(@RequestParam(value="note_no", required=false, defaultValue="0") int[] note_no) {
      System.out.println("보낸 쪽지함 삭제 업데이트 처리 controller : "+note_no.length);
      System.out.println("보낸 쪽지함 삭제 업데이트 처리 controller : "+note_no[0]);   


      // 보낸 쪽지함 글 삭제 Y표시로 업데이트
      noteService.updateBySendNoteList(note_no);
      return "redirect:/note/nt_send_page.do";
   }
   
   //받은 쪽지함 글 삭제 시 NOTE_RECV_DELECT Y로 변경
   @RequestMapping("nt_update_recv_note_delete_process.do")
   @ResponseBody
   public String updateByRecvNoteList(@RequestParam(value="note_no", required=false, defaultValue="0") int[] note_no) {
      System.out.println("받은 쪽지함 삭제 업데이트 처리 controller : "+ note_no.length);
      System.out.println("받은 쪽지함 삭제 업데이트 처리 controller : "+ note_no[0]);
      noteService.updateByRecvNoteList(note_no);
      return "redirect:/note/nt_recv_page.do";
   }
   
}

    