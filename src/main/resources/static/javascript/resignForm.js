function ck_form() {
	if (!$("#secessionPwdText").val()) {
		alert("현재 비밀번호를 입력해 주세요.");
		return false;
	}
}