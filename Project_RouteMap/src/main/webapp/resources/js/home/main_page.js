// 라디오버튼에서 체크된 값 가져오기
function checkRadioButton() {

	var category_no = document.getElementsByName('category_no'); // 'name값' 
	var isChecked; // 여기에 선택된 radio 버튼의 값이 담기게 된다.
	for (var i = 0; i < category_no.length; i++) {
		if (category_no[i].checked) {
			isChecked = category_no[i].value;
		}
	}
	return isChecked;
}

function searchByTitle() {
	var search_word = document.getElementById("search_word").value;
	var category_no = checkRadioButton();

	if (category_no == 1) {
		location.href = "/locationboard/lc_main_page.do?search_word="
				+ search_word;

	} else {
		location.href = "/routeboard/rt_main_page.do?search_word="
				+ search_word;
	}

}
