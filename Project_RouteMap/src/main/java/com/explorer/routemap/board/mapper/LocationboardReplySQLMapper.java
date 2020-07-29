package com.explorer.routemap.board.mapper;

import java.util.*;

import com.explorer.routemap.board.vo.*;

public interface LocationboardReplySQLMapper {

   // 댓글 번호 생성
   public int createLocationboardReplyKey();

   // 댓글 쓰기
   public void insert(LocationboardReplyVo locationboardReplyVo);

   // 댓글 목록
   public List<LocationboardReplyVo> selectByLocationboardNo(int locationboard_no);
   
   // 댓글 작성자
   public LocationboardReplyVo selectByReplyNo(int locatinoboard_reply_no);

   // 댓글 수정
   public void update(LocationboardReplyVo locationboardReplyVo);

   // 댓글 삭제
   public void delete(int locatinoboard_reply_no);

   // 게시물 삭제 시 관련 댓글 삭제
   public void deleteByLocationboardNo(int locationboard_no);

}