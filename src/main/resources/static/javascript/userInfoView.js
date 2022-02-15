//이메일 수정 버튼 이벤트
function emailMfBtn() {
	swal({
		buttons : {
			cancel : "닫기",
			confirm : "수정"
		},
		content : {
			element : "input",
			attributes : {
				id : "editText",
				placeholder : "이메일을 작성해주세요.",
				value : $("#profEmailMfText").text()
			},
		},
		closeOnClickOutside : false
	}).then(function(click) {
		if ($("#editText").val() == "" && click != null) {
			swal({
				text : "이메일을 작성해주세요.",
				button : "확인"
			}).then(function() {
				emailMfBtn();
			});
		}

		if (click) {
			var param = "item=email";
			param += "&value=" + $("#editText").val();
			location.href = "/user/my/changeInfo?" + param;
		}
	});
}

// 전공 수정 버튼 이벤트
function majorMfBtn() {
	swal({
		buttons : {
			cancel : "닫기",
			confirm : "수정"
		},
		content : {
			element : "input",
			attributes : {
				id : "editText",
				placeholder : "전공을 작성해주세요.",
				value : $("#profMajorMfText").text()
			},
		},
		closeOnClickOutside : false
	}).then(function(click) {
		if ($("#editText").val() == "" && click != null) {
			swal({
				text : "전공을 작성해주세요.",
				button : "확인"
			}).then(function() {
				majorMfBtn();
			});
		}

		if (click) {
			var param = "item=major";
			param += "&value=" + $("#editText").val();
			location.href = "/user/my/changeInfo?" + param;
		}
	});
}


