package com.explorer.routemap.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.admin.mapper.AdminSQLMapper;
import com.explorer.routemap.report.mapper.ReportSQLMapper;

@Service
public class ReportServiceImpl {

	@Autowired
	ReportSQLMapper reportSQLMapper;
	@Autowired
	AdminSQLMapper adminSQLMapper;

	// 사용자 신고
	public void reportUser(int reporting_member_no, int reported_member_no, int report_category_no) {

		reportSQLMapper.insertReportMember(reporting_member_no, reported_member_no, report_category_no);

	}
	
	// 장소 게시물 신고
	public void reportLocation(int member_no, int locationboard_no, int report_category_no) {

		reportSQLMapper.insertReportLocation(member_no, locationboard_no, report_category_no);

	}

	// 루트 게시물 신고
	public void reportRoute(int member_no, int routeboard_no, int report_category_no) {

		reportSQLMapper.insertReportRoute(member_no, routeboard_no, report_category_no);

	}
}
