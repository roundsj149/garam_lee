package com.explorer.routemap.note.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.member.mapper.MemberSQLMapper;
import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.note.mapper.NoteSQLMapper;
import com.explorer.routemap.note.vo.NoteVo;

@Service
public class NoteServiceImpl {
   
   @Autowired
   NoteSQLMapper noteSQLMapper;
   @Autowired
   MemberSQLMapper memberSQLMapper;
   
   // 쪽지 쓰기
   public void insertBySendNote(MemberVo memberVo, NoteVo noteVo) {
      //리턴 시키기 위해 변수 선언
      int recvMemberNo = memberSQLMapper.selectByMemberId(noteVo.getRecv_member_id()); // 받는 사람 아이디를 조회해서 회원번호를 가지고 옴
      noteVo.setRecv_member_no(recvMemberNo);
      
      noteSQLMapper.insertBySendNote(memberVo, noteVo, recvMemberNo);
   }
   
   // 테이블 조인을 사용할 때 Map 사용해서 조인 시켜줌
   
   // 받은 쪽지함 리스트 출력
   public List<NoteVo> selectByRecvNote(int recvMemberNo, int currPage) {
      List<NoteVo> recvNoteList = noteSQLMapper.selectByRecvNote(recvMemberNo, currPage);
      
      System.out.println("받은 쪽지함 리스트 출력 service : "+recvMemberNo);
      return recvNoteList;
   }
   
   // 보낸 쪽지함 리스트 출력
   public List<NoteVo> selectBySendNote(int sendMemberNo, int currPage){
      List<NoteVo> sendNoteList = noteSQLMapper.selectBySendNote(sendMemberNo, currPage);
      
      System.out.println("보낸 쪽지함 리스트 출력 service : "+sendNoteList);
      //noteSQLMapper.delectBySendNote();
      
      return sendNoteList;
   }
      
   // 받은 쪽지함 쪽지 읽기
   public Map<String, Object> selectByRecvReadNote(int noteNo){
      Map<String, Object> map = new HashMap<String, Object>();
      
      NoteVo noteVo = noteSQLMapper.selectByRecvReadNote(noteNo);
      // 받은 사람이 읽었을때 날짜 업데이트 적용
      noteSQLMapper.updateByRecvReadDate(noteNo);
      
      map.put("noteVo", noteVo);
      
      return map;
   }
   
   // 받은 쪽지한 글 전체 갯수 처리
   public int getRecvNoteDataCount(int recvMemberNo) {
      return noteSQLMapper.selectByRecvCount(recvMemberNo);
   }
   
   // 받은 쪽지함 글 삭제 Y표시로 업데이트
   public void updateByRecvNoteList(int[] note_no) {
      System.out.println("받은 쪽지함 삭제 업데이트 처리 service : "+note_no.length);
      noteSQLMapper.updateByRecvNoteList(note_no);
   }
   
   // 보낸 쪽지함 쪽지 읽기
   public NoteVo selectBySendReadNote(int noteNo) {
      NoteVo noteVo = noteSQLMapper.selectBySendReadNote(noteNo);
      
      return noteVo;
   }
   
   // 보낸 쪽지함 글 전체 갯수 처리
   public int getSendNoteDataCount(int sendMemberNo) {
      System.out.println("보낸 쪽지함 글 갯수 출력 service : "+sendMemberNo);
      
      return noteSQLMapper.selectBySendCount(sendMemberNo);
   }

   // 보낸 쪽지함 발송 취소
   public void deleteBySendNote(int noteNo) {
      System.out.println("발송 취소 service : "+noteNo);
      noteSQLMapper.deleteBySendNote(noteNo);
   }
   
   // 보낸 쪽지함 글 삭제 Y표시로 업데이트
   public void updateBySendNoteList(int[] note_no) {
      System.out.println("보낸 쪽지함 삭제 업데이트 처리 service : "+note_no.length);
      noteSQLMapper.updateBySendNoteList(note_no);
   }
   
   // 받은 쪽지함, 보낸 쪽지함 글 삭제 모두 Y표시일때 데이터상의 글 삭제
   public void deleteByNoteList() {
      noteSQLMapper.deleteByNoteList();
   }
   
}
   