package com.explorer.routemap.clientservice.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.explorer.routemap.clientservice.vo.ClientServiceFaqVo;
import com.explorer.routemap.clientservice.vo.ClientServiceInquiryUploadFileVo;
import com.explorer.routemap.clientservice.vo.ClientServiceInquiryVo;
import com.explorer.routemap.clientservice.vo.ClientServiceNoticeVo;

public interface ClientServiceSQLMapper {

	public void insert(ClientServiceInquiryVo clientServiceInquiryVo);

	public void insertUploadFile(ClientServiceInquiryUploadFileVo clientServiceInquiryUploadFileVo);

	public int createKey();

	public List<ClientServiceInquiryVo> selectByMember_no(int member_no, RowBounds rb);

	public int selectInquiryCount(int member_no);

	public ClientServiceInquiryUploadFileVo selectByInquiry_no(int inquiry_no);

	public ClientServiceInquiryVo selectByInquiryNoDetail(int inquiry_no);

	public List<ClientServiceNoticeVo> selectNotice(RowBounds rb);

	public List<ClientServiceNoticeVo> selectNoticeByTitle(@Param("searchTitle") String searchTitle, RowBounds rb);

	public ClientServiceNoticeVo selectNoticeByNo(int notice_no);

	public List<ClientServiceFaqVo> selectFaqList(@Param("faqCategoryNo") String faqCategoryNo,
			@Param("faqSearchTitle") String faqSearchTitle, RowBounds rb);

	public void insertFaq(ClientServiceFaqVo clientServiceFaqVo);

	public int selectNoticeAllCount();

	public int selectNoticeCountByTitle(String searchTitle);

	public void deleteInquiry(int deleteInquiryNo);

	public int selectFaqCount(@Param("faqCategoryNo") String faqCategoryNo,
			@Param("faqSearchTitle") String faqSearchTitle);

	public void updateInquiry(ClientServiceInquiryVo clientServiceInquiryVo);

	public void updateInquiryUpload(ClientServiceInquiryUploadFileVo clientServiceInquiryUploadFileVo);

	public String selectInquiryCategory(int inquiry_category_no);

	public void deleteInquiryUpload(int inquiry_no);

	public ClientServiceFaqVo selectByfaqNo(int faqNo);

	public void updateFaq(ClientServiceFaqVo clientServiceFaqVo);

	public void deleteFaq(int faqNo);

}
