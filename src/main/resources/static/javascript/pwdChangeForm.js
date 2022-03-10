function ck_form() {
	if (!$("#currentPwdText").val()) {
		alert("현재 비밀번호를 입력해 주세요.");
		return false;
	}

	if (!$("#newPwdText").val()) {
		alert("새 비밀번호를 입력해주세요.");
		return false;
	}

	if (!$("#newPwdCheckText").val()) {
		alert("새 비밀번호 확인을 입력해주세요.");
		return false;
	}
}
