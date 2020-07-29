$(function() {
	if($("#session_check").val() == "") {
		location.href="/error/no_authority_page.do";
		return;
	}
});