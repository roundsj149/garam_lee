function refreshBtn(type) {
	var rand = Math.random();
	var url = "/member/captchaImg.do?rand=" + rand;
	$("#captchaImg").attr("src", url);
}
