function ck_joinAuth_form() {
	var form = document.joinAuth_form;
        
	if(!form.inputCode.value) {
		alert("인증 코드를 입력해 주세요.");
		form.input_code.focus();
		return false;
	}
}