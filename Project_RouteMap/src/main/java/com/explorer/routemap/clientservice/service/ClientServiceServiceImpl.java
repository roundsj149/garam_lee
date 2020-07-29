    package com.explorer.routemap.clientservice.service;

import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.clientservice.mapper.ClientServiceSQLMapper;
import com.explorer.routemap.clientservice.vo.ClientServiceFaqVo;
import com.explorer.routemap.clientservice.vo.ClientServiceInquiryCategoryVo;
import com.explorer.routemap.clientservice.vo.ClientServiceInquiryUploadFileVo;
import com.explorer.routemap.clientservice.vo.ClientServiceInquiryVo;
import com.explorer.routemap.clientservice.vo.ClientServiceNoticeVo;
import com.explorer.routemap.clientservice.vo.ClientServicePageNavigatorVo;

@Service
public class ClientServiceServiceImpl {
	
	@Autowired
	private ClientServiceSQLMapper clientServiceSQLMapper;
	//문의등록
	public int writeInquiry(ClientServiceInquiryVo clientServiceInquiryVo) {
		
		int inquiryNo = clientServiceSQLMapper.createKey();
		
		clientServiceInquiryVo.setInquiry_no(inquiryNo);
		clientServiceSQLMapper.insert(clientServiceInquiryVo);
		
		return inquiryNo;
	}
	//문의등록 첨부파일
	public void writeInquiryUpload(ClientServiceInquiryUploadFileVo clientServiceInquiryUploadFileVo) {
		
		clientServiceSQLMapper.insertUploadFile(clientServiceInquiryUploadFileVo);
	}
	//나의 문의내역 리스트 조회
	public List<Map<String,Object>> getMyInquiry(int member_no, ClientServicePageNavigatorVo clientServicePageNavigatorVo) {
		
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		
		List<ClientServiceInquiryVo> inquiryList = new ArrayList<ClientServiceInquiryVo>();
		
		inquiryList = null;
		
		try {
		RowBounds rb = new RowBounds(clientServicePageNavigatorVo.getStratRecord(), clientServicePageNavigatorVo.getCountPerPage());
		inquiryList = clientServiceSQLMapper.selectByMember_no(member_no, rb);
		 
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
		 
		for(ClientServiceInquiryVo inquiryVo : inquiryList) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			ClientServiceInquiryUploadFileVo uploadVo = clientServiceSQLMapper.selectByInquiry_no(inquiryVo.getInquiry_no());
			
			
			map.put("uploadVo", uploadVo);
			map.put("inquiryVo", inquiryVo);
			
			dataList.add(map);
		}
		
		return dataList;
	
	}
	
	public int getInquiryCount(int member_no) {
		
		return clientServiceSQLMapper.selectInquiryCount(member_no);
		
	}
	//나의 문의내역 상세조회
	public Map<String,Object> getMyInquiryDetail(int inquiry_no) {
		
		ClientServiceInquiryVo inquiryVo = clientServiceSQLMapper.selectByInquiryNoDetail(inquiry_no);
		ClientServiceInquiryUploadFileVo inquiryUploadFileVo = 
				clientServiceSQLMapper.selectByInquiry_no(inquiryVo.getInquiry_no());
		
		String inquiryCategoryName = clientServiceSQLMapper.selectInquiryCategory(inquiryVo.getInquiry_category_no());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("inquiryVo",inquiryVo);
		map.put("inquiryUploadFileVo",inquiryUploadFileVo);
		map.put("inquiryCategoryName",inquiryCategoryName);
		
		return map;
	}
	
	//나의 문의내역 파일업로드조회
	public ClientServiceInquiryUploadFileVo getMyInquiryUploadFile(int inquiry_no) {
		
		ClientServiceInquiryUploadFileVo inquiryUploadFileVo = clientServiceSQLMapper.selectByInquiry_no(inquiry_no);
		
		return inquiryUploadFileVo;
		
	}
	
	public List<ClientServiceNoticeVo> getNoticeList(String searchTitle, ClientServicePageNavigatorVo clientServicePageNavigatorVo) {
		
		List<ClientServiceNoticeVo> noticeList = null;
		
		if(searchTitle == null) {
			
			try {
				RowBounds rb = new RowBounds(clientServicePageNavigatorVo.getStratRecord(), 
						clientServicePageNavigatorVo.getCountPerPage());
				
				noticeList = clientServiceSQLMapper.selectNotice(rb);
				
			} catch(Exception e) {
				e.printStackTrace();
				return noticeList;
			}
			
			return noticeList;
			
		}else {
			
			try {
				RowBounds rb = new RowBounds(clientServicePageNavigatorVo.getStratRecord(), 
						clientServicePageNavigatorVo.getCountPerPage());
				
				noticeList = clientServiceSQLMapper.selectNoticeByTitle(searchTitle, rb);
				
			} catch(Exception e) {
				e.printStackTrace();
				return noticeList;
			}
		
			return noticeList;
		}
		
	}
	
	public ClientServiceNoticeVo getNoticeContent(int notice_no) {
		
		ClientServiceNoticeVo noticeContentVo 
		= clientServiceSQLMapper.selectNoticeByNo(notice_no);
		
		return noticeContentVo;
	}
	
	public List<ClientServiceFaqVo> getFaqList(String faqCategoryNo, String faqSearchTitle, ClientServicePageNavigatorVo clientServicePageNavigatorVo) {

		List<ClientServiceFaqVo> faqList = null;
		
		try {
				RowBounds rb = new RowBounds(clientServicePageNavigatorVo.getStratRecord(), clientServicePageNavigatorVo.getCountPerPage());
				faqList = clientServiceSQLMapper.selectFaqList(faqCategoryNo, faqSearchTitle, rb);
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
		return faqList;
	}
	
	//자주묻는 질문 글 등록
	public void writeFaq(ClientServiceFaqVo clientServiceFaqVo) {
		clientServiceSQLMapper.insertFaq(clientServiceFaqVo);
	}
	
	public int getNoticeCount(String searchTitle) {
		
		if(searchTitle == null) {
			
			return clientServiceSQLMapper.selectNoticeAllCount();
			
		} else {
			
			return clientServiceSQLMapper.selectNoticeCountByTitle(searchTitle);
			
		}
	}
	
	public void deleteInquiry(int deleteInquiryNo) {
		clientServiceSQLMapper.deleteInquiry(deleteInquiryNo);
	}
	
	public int getFaqCount(String faqCategoryNo, String faqSearchTitle) {
		
		return clientServiceSQLMapper.selectFaqCount(faqCategoryNo, faqSearchTitle);

	}
	
	public void updateInquiry(ClientServiceInquiryVo clientServiceInquiryVo) {
		clientServiceSQLMapper.updateInquiry(clientServiceInquiryVo);
	}
	
	public void updateInquiryUpload(ClientServiceInquiryUploadFileVo clientServiceInquiryUploadFileVo) {
		
		ClientServiceInquiryUploadFileVo fileVo = clientServiceSQLMapper.selectByInquiry_no(clientServiceInquiryUploadFileVo.getInquiry_no());
		
		if(fileVo != null) {
			clientServiceSQLMapper.updateInquiryUpload(clientServiceInquiryUploadFileVo);
		} else {
			clientServiceSQLMapper.insertUploadFile(clientServiceInquiryUploadFileVo);
		}
	}
	
	public void deleteInquiryUpload(ClientServiceInquiryUploadFileVo clientServiceInquiryUploadFileVo) {
		
		clientServiceSQLMapper.deleteInquiryUpload(clientServiceInquiryUploadFileVo.getInquiry_no());
	
	}
	
	public ClientServiceFaqVo getFaqContent(int faqNo) {
		ClientServiceFaqVo clientServicefaqVo = clientServiceSQLMapper.selectByfaqNo(faqNo);
		return clientServicefaqVo;
	}
	
	public void updateFaq(ClientServiceFaqVo clientServiceFaqVo) {
		clientServiceSQLMapper.updateFaq(clientServiceFaqVo);
	}
	
	public void deleteFaq(int faqNo) {
		clientServiceSQLMapper.deleteFaq(faqNo);
	}
}

    
