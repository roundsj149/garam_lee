    package com.explorer.routemap.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.explorer.routemap.board.service.FreeboardServiceImpl;
import com.explorer.routemap.board.vo.FreeboardCommentVo;
import com.explorer.routemap.board.vo.FreeboardVo;
import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.uploadfile.vo.FreeboardUploadFileVo;

@Controller
@RequestMapping("/freeboard/*")
public class FreeboardController {

	@Autowired
	FreeboardServiceImpl freeboardService;

	@RequestMapping("/fb_main_page.do")
	public String freeboardMainPage(Model model, String word,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

		List<Map<String, Object>> list = freeboardService.getFreeboardList(word, currPage);

		int sumCnt = freeboardService.getFreeboardDataCount(word);
		int Spage = ((currPage - 1) / 10) * 10 + 1;
		int Epage = ((currPage - 1) / 10 + 1) * 10;

		if (Epage > ((sumCnt - 1) / 10) + 1) {
			Epage = ((sumCnt - 1) / 10) + 1;
		}
		model.addAttribute("Spage", Spage);
		model.addAttribute("Epage", Epage);
		model.addAttribute("currPage", currPage);
		model.addAttribute("dataList", list);
		model.addAttribute("sumCnt", sumCnt);

		return "freeboard/fb_main_page";

	}

	// 글쓰기; 자유게시판
	@RequestMapping("/fb_write_content_page.do")
	public String writeContentPage() {

		return "freeboard/fb_write_content_page";
	}

	// 글쓰기 처리
	@RequestMapping("/fb_write_content_process.do")
	public String writeContentProcess(MultipartFile[] upload_files, FreeboardVo freeboardVo, HttpSession session) {

		String uploadShelter = "C:/upload";

		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd");
		String nowShelter = df.format(now);

		String saveShelterName = uploadShelter + nowShelter;

		File saveShelter = new File(saveShelterName);

		if (!saveShelter.exists()) {
			saveShelter.mkdirs();
		}

		List<FreeboardUploadFileVo> fbFileVoList = new ArrayList<FreeboardUploadFileVo>();

		for (MultipartFile file : upload_files) {

			if (file.getSize() <= 0) {
				continue;
			}

			String rootFileName = file.getOriginalFilename();

			String saveFileName = UUID.randomUUID().toString();
			saveFileName += "/" + System.currentTimeMillis();
			saveFileName += rootFileName.substring(rootFileName.lastIndexOf("."));

			String saveRealPath = saveShelterName + ";" + saveFileName;

			try {
				file.transferTo(new File(saveRealPath));

			} catch (Exception e) {
				e.printStackTrace();
			}

			FreeboardUploadFileVo fbFileVo = new FreeboardUploadFileVo();

			fbFileVo.setFile_link_path(nowShelter + ";" + saveFileName);
			fbFileVo.setFile_real_path(saveRealPath);

			fbFileVoList.add(fbFileVo);

		}

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {
			return "redirect:/member/join_member_page.do";
		} else {
			freeboardVo.setMember_no(memberVo.getMember_no());
			freeboardService.writing(freeboardVo, fbFileVoList);

			return "redirect:/freeboard/fb_main_page.do ";
		}

	}

	// 글읽기
	@RequestMapping("/fb_read_content_page.do")
	public String readContentPage(int freeboard_no, Model model) {

		Map<String, Object> map = freeboardService.seeContents(freeboard_no);

		model.addAttribute("addCont", map);
		return "freeboard/fb_read_content_page";
	}

	// 글지우기
	@RequestMapping("/fb_delete_content_process.do")
	public String deleteContentProcess(int freeboard_no) {
		freeboardService.eraseContents(freeboard_no);
		return "redirect:/freeboard/fb_main_page.do ";
	}

	// 글업데이트
	@RequestMapping("/fb_update_content_page.do")
	public String updateContentPage(int freeboard_no, Model model) {

		model.addAttribute("news", freeboardService.seeContents(freeboard_no));

		return "freeboard/fb_update_content_page";
	}

	@RequestMapping("/fb_update_content_process.do")
	public String updateContentProcess(FreeboardVo freeboardVo) {
		freeboardService.updateContents(freeboardVo);
		return "redirect:/freeboard/fb_main_page.do";
	}

	// 자유게시판 댓글쓰기
	@RequestMapping("/fb_write_comment_process.do")
	public String writeCommentProcess(int freeboard_no, FreeboardCommentVo freeboardCommentVo, HttpSession session) {
		int member_no = ((MemberVo) session.getAttribute("sessionUser")).getMember_no();

		freeboardCommentVo.setMember_no(member_no);
		freeboardService.writeComment(freeboard_no, freeboardCommentVo);

		return "redirect:/freeboard/fb_main_page.do";

	}

}

    
