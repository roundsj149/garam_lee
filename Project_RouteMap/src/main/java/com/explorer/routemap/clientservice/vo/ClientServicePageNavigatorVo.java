    package com.explorer.routemap.clientservice.vo;

public class ClientServicePageNavigatorVo {

	private int countPerPage; //페이지당 글 갯수
	private int pagePerGroup; //그룹당 페이지 수
	private int currentPage; //현재 페이지 (최근 글이 1부터 시작)
	private int totalRecordsCount; //전체 글 수
	private int totalPageCount; //전체 페이지 수
	private int currentGroup; //현재 그룹 (최근 그룹이 0 부터시작)
	private int startPageGroup; //현재 그룹의 첫 페이지
	private int endPageGroup; //현재 그룹의 마지막 페이지
	private int startRecord; //전체 레코드 중 현제 페이지 첫글의 위치 (0부터시작)
	
	public ClientServicePageNavigatorVo(int countPerPage, int pagePerGroup, int currentPage, 
			int totalRecordsCount) {
	
		this.countPerPage = countPerPage;
		this.pagePerGroup = pagePerGroup;
		this.totalRecordsCount = totalRecordsCount;
		
		totalPageCount = (totalRecordsCount + countPerPage - 1) / countPerPage;
		
		if(currentPage < 1) {
			currentPage = 1;
		}
		
		if(currentPage > totalPageCount) {
			currentPage = totalPageCount;
		}
		
		this.currentPage = currentPage;
		
		currentGroup = (currentPage - 1) / pagePerGroup;
		
		startPageGroup = currentGroup * pagePerGroup + 1;
		
		startPageGroup = startPageGroup < 1 ? 1 : startPageGroup;
		
		endPageGroup = startPageGroup + pagePerGroup - 1;
		
		endPageGroup = endPageGroup < totalPageCount ? endPageGroup : totalPageCount;
		
		startRecord = (currentPage - 1) * countPerPage;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	public int getPagePerGroup() {
		return pagePerGroup;
	}

	public void setPagePerGroup(int pagePerGroup) {
		this.pagePerGroup = pagePerGroup;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}

	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getCurrentGroup() {
		return currentGroup;
	}

	public void setCurrentGroup(int currentGroup) {
		this.currentGroup = currentGroup;
	}

	public int getStartPageGroup() {
		return startPageGroup;
	}

	public void setStartPageGroup(int startPageGroup) {
		this.startPageGroup = startPageGroup;
	}

	public int getEndPageGroup() {
		return endPageGroup;
	}

	public void setEndPageGroup(int endPageGroup) {
		this.endPageGroup = endPageGroup;
	}

	public int getStratRecord() {
		return startRecord;
	}

	public void setStratRecord(int stratRecord) {
		this.startRecord = stratRecord;
	}

}

    
