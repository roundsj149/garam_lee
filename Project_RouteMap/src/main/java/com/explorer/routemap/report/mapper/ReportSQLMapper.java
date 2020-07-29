package com.explorer.routemap.report.mapper;

import org.apache.ibatis.annotations.Param;

public interface ReportSQLMapper {

	// 사용자 신고
	public void insertReportMember(@Param("reporting_member_no") int reporting_member_no,
			@Param("reported_member_no") int reported_member_no, @Param("report_category_no") int report_category_no);

	// 장소 게시물 신고
	public void insertReportLocation(@Param("member_no") int member_no,
			@Param("locationboard_no") int locationboard_no, @Param("report_category_no") int report_category_no);
	
	// 루트 게시물 신고
	public void insertReportRoute(@Param("member_no") int member_no,
			@Param("routeboard_no") int routeboard_no, @Param("report_category_no") int report_category_no);
}
