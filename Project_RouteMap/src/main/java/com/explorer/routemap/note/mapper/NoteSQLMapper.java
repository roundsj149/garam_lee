package com.explorer.routemap.note.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.note.vo.NoteVo;

public interface NoteSQLMapper {
      
   // 쪽지 보내기 처리
   public void insertBySendNote(@Param("memberVo") MemberVo memberVo, 
         @Param("noteVo")NoteVo noteVo, 
         @Param("recv_member_no") int recvMemberNo
         );
   
   // 받은 쪽지함 리스트 출력
   public List<NoteVo> selectByRecvNote(
         @Param("recvMemberNo")int recvMemberNo,
         @Param("currPage")int currPage);
      
   // 보낸 쪽지함 리스트 출력
   public List<NoteVo> selectBySendNote(
         @Param("sendMemberNo")int sendMemberNo, 
         @Param("currPage")int currPage);
   
   // 받은 쪽지함 쪽지 읽기
   public NoteVo selectByRecvReadNote(int noteNo);
   
   // 받은 쪽지함 글 전체 갯수
   public int selectByRecvCount(int recvMemberNo);
   
   // 받은 쪽지함 읽으면 받은 날짜 업데이트
   public void updateByRecvReadDate(int noteNo); 
   
   // 받은 쪽지함 글 삭제 Y표시로 업데이트
   public void updateByRecvNoteList(@Param("note_no") int[] note_no);
   
   // 보낸 쪽지함 쪽지 읽기
   public NoteVo selectBySendReadNote(int noteNo);
   
   // 보낸 쪽지함 글 전체 갯수
   public int selectBySendCount(int sendMemberNo);
   
   // 보낸 쪽지함에서 받은 사람이 읽지 않았을 경우 발송 취소 
   public void deleteBySendNote(int noteNo);
   
   // 보낸 쪽지함 글 삭제 Y표시로 업데이트
   public void updateBySendNoteList(@Param("note_no") int[] note_no);
   
   // 받은 쪽지함, 보낸 쪽지함 글 삭제 모두 Y표시일때 데이터상의 글 삭제
   public void deleteByNoteList();
   
}