    package com.explorer.routemap.clientservice.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.explorer.routemap.board.vo.LocationboardVo;
import com.explorer.routemap.clientservice.service.ClientServiceServiceImpl;
import com.explorer.routemap.clientservice.vo.ClientServiceFaqVo;
import com.explorer.routemap.clientservice.vo.ClientServiceInquiryUploadFileVo;
import com.explorer.routemap.clientservice.vo.ClientServiceInquiryVo;
import com.explorer.routemap.clientservice.vo.ClientServiceNoticeVo;
import com.explorer.routemap.clientservice.vo.ClientServicePageNavigatorVo;
import com.explorer.routemap.member.vo.MemberVo;


@Controller
@RequestMapping("/clientservice/*")
public class ClientServiceController {
	
	final static int countPerPage = 10;
	final static int pagePerGroup = 5;
	
	@Autowired
	private ClientServiceServiceImpl clientServiceService; 
	
	@RequestMapping(value = "/notice_page.do" , method={RequestMethod.POST, RequestMethod.GET})
	public String noticePage(String noticeSearchTitle, HttpServletRequest request, 
			HttpServletResponse response, Model model, 
			@RequestParam(value = "currPage", required = false , defaultValue = "1") int currPage) 
			throws Exception {
		
		ClientServicePageNavigatorVo clientServicePageNavigatorVo = new ClientServicePageNavigatorVo(countPerPage, pagePerGroup, currPage, clientServiceService.getNoticeCount(noticeSearchTitle));
		
		System.out.println(clientServicePageNavigatorVo.getCurrentPage());
		
		List<ClientServiceNoticeVo> noticeData = clientServiceService.getNoticeList(noticeSearchTitle, clientServicePageNavigatorVo);
		
	
		model.addAttribute("noticeData",noticeData);
		model.addAttribute("noticePageData",clientServicePageNavigatorVo);
		model.addAttribute("noticeSearchTitle",noticeSearchTitle);
		
		
		return "clientservice/notice_page";
	}
	
	
	@RequestMapping("/faq_page.do")
	public String faqPage(String faqCategoryNo, String faqSearchTitle, Model model,
			@RequestParam(value = "currPage", required = false , defaultValue = "1") int currPage) throws Exception{
		
		ClientServicePageNavigatorVo clientServicePageNavigatorVo = new ClientServicePageNavigatorVo(countPerPage, pagePerGroup, currPage, clientServiceService.getFaqCount(faqCategoryNo, faqSearchTitle));
		
		System.out.println(clientServicePageNavigatorVo.getCurrentPage());
		System.out.println(clientServicePageNavigatorVo.getEndPageGroup());
		System.out.println(clientServicePageNavigatorVo.getTotalRecordsCount());
		
		
		List<ClientServiceFaqVo> faqList = clientServiceService.getFaqList(faqCategoryNo,faqSearchTitle, clientServicePageNavigatorVo);
		
		for(ClientServiceFaqVo clientServiceFaqVo : faqList) {

			clientServiceFaqVo
					.setFaq_content(clientServiceFaqVo.getFaq_content().replace("\r\n", "<br>"));
		}
		
		model.addAttribute("faqList",faqList);
		model.addAttribute("faqPageData",clientServicePageNavigatorVo);
		model.addAttribute("faqCategoryNo",faqCategoryNo);
		model.addAttribute("faqSearchTitle",faqSearchTitle);
		
		return "clientservice/faq_page";
	}
	
	@RequestMapping("/inquiry_page.do")
	public String inquiryPage(HttpSession session) {
		
		MemberVo memberVo = (MemberVo)session.getAttribute("sessionUser");
		
		if(memberVo == null) {
			
			return "redirect:/member/login_page.do";
			
		} else {
			
			return "clientservice/inquiry_page";
		}
		
	}
	
	@RequestMapping("/myInquiry_page.do")
	public String myInquiryPage(HttpSession session, Model model,
			@RequestParam(value = "currPage" , required = false, defaultValue = "1") int currPage) throws Exception{

			
			MemberVo memberVo = (MemberVo)session.getAttribute("sessionUser");
			if(memberVo == null) {
				
				return "redirect:/member/login_page.do";

			} else {
				
				ClientServicePageNavigatorVo clientServicePageNavigatorVo = new ClientServicePageNavigatorVo(countPerPage, pagePerGroup, currPage, clientServiceService.getInquiryCount(memberVo.getMember_no()));

				int member_no = memberVo.getMember_no();
				
				List<Map<String,Object>> dataList = clientServiceService.getMyInquiry(member_no, clientServicePageNavigatorVo);
				
				model.addAttribute("dataList", dataList);
				model.addAttribute("inquiryPageData",clientServicePageNavigatorVo);
				
				return "clientservice/myinquiry_page";
			}
	}
	
	@RequestMapping(value = "/inquiryDelete_process.do", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> inquiryDeleteProcess(HttpServletRequest request) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		clientServiceService.deleteInquiry(Integer.parseInt(request.getParameter("deleteInquiryNo")));
		
		ClientServiceInquiryUploadFileVo inquiryUploadFileVo = 
				clientServiceService.getMyInquiryUploadFile(Integer.parseInt(request.getParameter("deleteInquiryNo")));
		
		if(inquiryUploadFileVo != null) {
			
			String realPath = inquiryUploadFileVo.getInquiry_file_real_path();
			
			File file = new File(realPath);
			
			if(file.exists()) {
				
				try { 
					file.delete();
				} catch (Exception e) {
					e.printStackTrace();
					map.put("returnCode", "E004");
					map.put("returnMsg",e.getMessage());
					
					return map;
					
				}
		
			} else {
				map.put("returnCode", "E005");
				map.put("returnMsg", "파일이 존재하지 않습니다");
				
				return map;
			}
			
		}
		
		map.put("returnCode", "0000");
		map.put("returnMsg", "삭제되었습니다.");
		
		return map;
		
	}
	
	@RequestMapping("/noticeContent_page.do")
	public String noticeContentPage(int notice_no, Model model) {
		
		ClientServiceNoticeVo noticeContentVo = clientServiceService.getNoticeContent(notice_no);
		
		model.addAttribute("noticeContentVo",noticeContentVo);
		
		return "clientservice/notice_content_page";
	}
	
	@RequestMapping("/myInquiry_content_page.do")
	public String myInquiryContentPage(int inquiry_no, Model model) {
		
		Map<String,Object> map = clientServiceService.getMyInquiryDetail(inquiry_no);
		model.addAttribute("inquiryData",map);
		
		return "clientservice/myinquiry_content_page";
		
	}
		
	@RequestMapping("/myInquiry_update_page.do")
	public String myInquiryUpdatePage(int inquiry_no, Model model) {
		
		Map<String,Object> map = clientServiceService.getMyInquiryDetail(inquiry_no);
		model.addAttribute("inquiryData", map);
		
		return "clientservice/myinquiry_update_page";
	}
	
	@RequestMapping(value = "/myInquiry_update_process.do", method = {RequestMethod.POST,RequestMethod.GET}, consumes = {"multipart/form-data"})
	public @ResponseBody Map<String, Object> myInquiryUpdateProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "upload_files") MultipartFile[] multiFile) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		// 제목 유효성 체크
		if ("".equals(request.getParameter("cs_inquiry_title")) || request.getParameter("cs_inquiry_title") == null) {
			map.put("returnCode", "E001");
			map.put("returnMsg", "제목은 필수입력 항목입니다.");
			
			return map;
		}
		
		// 내용 유효성 체크
		if ("".equals(request.getParameter("cs_inquiry_content")) || request.getParameter("cs_inquiry_content") == null) {
			map.put("returnCode", "E002");
			map.put("returnMsg", "내용은 필수입력 항목입니다.");
			
			return map;
		}
				System.out.println(request.getParameter("upload_delete_key"));
		//문의글VO 객체생성
				ClientServiceInquiryVo clientServiceInquiryVo = new ClientServiceInquiryVo();
				//문의글VO에 제목 Value 주입
				clientServiceInquiryVo.setInquiry_title(request.getParameter("cs_inquiry_title"));
				//문의글VO에 내용 Value 주입
				clientServiceInquiryVo.setInquiry_content(request.getParameter("cs_inquiry_content"));
				//문의글Vo에 카테고리번호 Value 주입
				clientServiceInquiryVo.setInquiry_category_no(Integer.parseInt(request.getParameter("cs_inquiry_category_no")));
			
				clientServiceInquiryVo.setInquiry_no(Integer.parseInt(request.getParameter("cs_inquiry_no")));
				
				clientServiceService.updateInquiry(clientServiceInquiryVo);
				
				String uploadDeleteKey = request.getParameter("upload_delete_key");
				
				ClientServiceInquiryUploadFileVo clientServiceInquiryUploadFileVo = 
						new ClientServiceInquiryUploadFileVo();
				
				clientServiceInquiryUploadFileVo.setInquiry_no(Integer.parseInt(request.getParameter("cs_inquiry_no")));
				
				//첨부파일 삭제 (파일까지 삭제)
				if(uploadDeleteKey.equals("Y")) {
					ClientServiceInquiryUploadFileVo inquiryUploadFileVo = 
							clientServiceService.getMyInquiryUploadFile(clientServiceInquiryUploadFileVo.getInquiry_no());
					
					clientServiceService.deleteInquiryUpload(clientServiceInquiryUploadFileVo);
					
					String realPath = inquiryUploadFileVo.getInquiry_file_real_path();
					File file = new File(realPath);
					
					if(file.exists()) {
						
						try { 
							file.delete();
						} catch (Exception e) {
							e.printStackTrace();
							map.put("returnCode", "E004");
							map.put("returnMsg",e.getMessage());
							
							return map;
							
						}
					} else {
						map.put("returnCode", "E005");
						map.put("returnMsg", "파일이 존재하지 않습니다");
						
						return map;
					}
					
				}
				
				if(multiFile != null) {
					
					String localFolderName = "/var/storage/routemap/upload/clientservice_file_upload/";
					
					Date date = new Date();
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					String todayFolderName = df.format(date);
					
					String saveFolderName = localFolderName + todayFolderName;
					
					File saveFolder = new File(saveFolderName);
					
					if(!saveFolder.exists()) {
						saveFolder.mkdirs();
					}
					
					
					for(MultipartFile uploadFile : multiFile) {
						
						if(uploadFile.getSize() <= 0) {
							continue;
						}
						System.out.println(uploadFile);
						String fileOriginName = uploadFile.getOriginalFilename();
						
						String fileRandomName = UUID.randomUUID().toString();
						
						String saveFileName = 
								fileRandomName + fileOriginName.substring(fileOriginName.lastIndexOf("."));
						
						String realPath = saveFolderName + "/" + saveFileName;
						
						try {
							uploadFile.transferTo(new File(realPath));
						} catch(Exception e) {
							e.printStackTrace();
							map.put("returnCode", "E003");
							map.put("returnMsg", e.getMessage());
							
							return map;
						}
						
						clientServiceInquiryUploadFileVo.setInquiry_file_link_path(todayFolderName + "/" + saveFileName);
						clientServiceInquiryUploadFileVo.setInquiry_file_real_path(saveFolderName + "/" + saveFileName);
						
						clientServiceService.updateInquiryUpload(clientServiceInquiryUploadFileVo);
					}
					
				}
				
				
				
				map.put("returnCode", "0000");
				map.put("returnMsg", "수정되었습니다.");
				
				return map;
		
	}

	
	@RequestMapping(value = "/inquiry_process.do", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	public @ResponseBody Map<String, Object> inquiryProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "upload_files") MultipartFile[] multiFile
			) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 제목 유효성 체크
		if ("".equals(request.getParameter("cs_inquiry_title")) || request.getParameter("cs_inquiry_title") == null) {
			map.put("returnCode", "E001");
			map.put("returnMsg", "제목은 필수입력 항목입니다.");
			
			return map;
		}
		
		// 내용 유효성 체크
		if ("".equals(request.getParameter("cs_inquiry_content")) || request.getParameter("cs_inquiry_content") == null) {
			map.put("returnCode", "E002");
			map.put("returnMsg", "내용은 필수입력 항목입니다.");
			
			return map;
		}
		
		System.out.println(request.getParameter("cs_inquiry_category_no"));
		System.out.println(request.getParameter("cs_inquiry_title"));
		
		//문의글VO 객체생성
		ClientServiceInquiryVo clientServiceInquiryVo = new ClientServiceInquiryVo();
		//문의글VO에 제목 Value 주입
		clientServiceInquiryVo.setInquiry_title(request.getParameter("cs_inquiry_title"));
		//문의글VO에 내용 Value 주입
		clientServiceInquiryVo.setInquiry_content(request.getParameter("cs_inquiry_content"));
		//문의글Vo에 카테고리번호 Value 주입
		clientServiceInquiryVo.setInquiry_category_no(Integer.parseInt(request.getParameter("cs_inquiry_category_no")));
		
		//세션에서 memberVo 받기
		MemberVo memberVo = (MemberVo)session.getAttribute("sessionUser");
		//문의글VO에 멤버 Value 주입
		clientServiceInquiryVo.setMember_no(memberVo.getMember_no());
		
		int inquiryNo = clientServiceService.writeInquiry(clientServiceInquiryVo);
		
		
		String localFolderName = "/var/storage/routemap/upload/clientservice_file_upload/";
		
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String todayFolderName = df.format(date);
		
		String saveFolderName = localFolderName + todayFolderName;
		
		File saveFolder = new File(saveFolderName);
		
		if(!saveFolder.exists()) {
			saveFolder.mkdirs();
		}
		
		
		for(MultipartFile uploadFile : multiFile) {
			
			if(uploadFile.getSize() <= 0) {
				continue;
			}
			System.out.println(uploadFile);
			String fileOriginName = uploadFile.getOriginalFilename();
			
			String fileRandomName = UUID.randomUUID().toString();
			
			String saveFileName = 
					fileRandomName + fileOriginName.substring(fileOriginName.lastIndexOf("."));
			
			String realPath = saveFolderName + "/" + saveFileName;
			
			try {
				uploadFile.transferTo(new File(realPath));
			} catch(Exception e) {
				e.printStackTrace();
				map.put("returnCode", "E003");
				map.put("returnMsg", e.getMessage());
				
				return map;
			}
			
			ClientServiceInquiryUploadFileVo ClientServiceInquiryUploadFileVo = 
					new ClientServiceInquiryUploadFileVo();
			
			ClientServiceInquiryUploadFileVo.setInquiry_no(inquiryNo);
			ClientServiceInquiryUploadFileVo.setInquiry_file_link_path(todayFolderName + "/" + saveFileName);
			ClientServiceInquiryUploadFileVo.setInquiry_file_real_path(saveFolderName + "/" + saveFileName);
			
			clientServiceService.writeInquiryUpload(ClientServiceInquiryUploadFileVo);
		}
		
		map.put("returnCode", "0000");
		map.put("returnMsg", "등록되었습니다.");
		
		return map;
	}

	/* 비동기식
	 * @RequestMapping(value = "/notice_page_sample.do" ,
	 * method={RequestMethod.GET}) public String noticePageSample(Model model)
	 * throws Exception { return "clientservice/notice_page_sample"; }
	 * 
	 * @RequestMapping(value = "/notice_page_list.do" , method={RequestMethod.POST})
	 * 
	 * @ResponseBody public Map<String, Object>
	 * noticePageListSample(HttpServletRequest request,HttpServletResponse response)
	 * throws Exception { Map<String, Object> map = new HashMap<String, Object>();
	 * map.put("returnCode", "0000"); map.put("returnMsg", "");
	 * 
	 * String noticeSearchTitle = request.getParameter("noticeSearchTitle"); String
	 * strCurrPage = request.getParameter("currPage");
	 * 
	 * System.out.println(noticeSearchTitle); System.out.println(strCurrPage); int
	 * currPage = 1;
	 * 
	 * if (!"".equals(strCurrPage) && strCurrPage != null) { currPage =
	 * Integer.parseInt(strCurrPage); }
	 * 
	 * System.out.println(noticeSearchTitle); System.out.println(currPage);
	 * 
	 * ClientServicePageNavigatorVo clientServicePageNavigatorVo = new
	 * ClientServicePageNavigatorVo(countPerPage, pagePerGroup, currPage,
	 * clientServiceService.getNoticeCount(noticeSearchTitle));
	 * List<ClientServiceNoticeVo> noticeData =
	 * clientServiceService.getNoticeList(noticeSearchTitle,
	 * clientServicePageNavigatorVo);
	 * 
	 * map.put("data", noticeData); map.put("page", clientServicePageNavigatorVo);
	 * 
	 * return map; }
	 */
}

    
