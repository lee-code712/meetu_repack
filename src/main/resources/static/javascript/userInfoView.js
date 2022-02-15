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

//담당과목 추가 버튼 이벤트
function subjectAddBtn() {
	var select = document.createElement("select");
	$(select).attr("id", "courseList");
	
	var option = document.createElement('option');
	option.innerHTML = '과목을 선택하세요.';
	select.appendChild(option);
	
	// 교수가 속한 학과의 과목들을 옵션에 추가
	Array.from(courses).forEach(function(course) {
		var course_no = course.courseNo;
		var title = course.title;

		if(title != null && $("#profSubjectMfText:contains("+ title + ")").attr("id") == undefined) { // 현재 추가되어있는 항목이 아닌 경우
			option = document.createElement('option');
			option.innerHTML = title;
			option.value = course_no;
			select.appendChild(option);
		}
	});
	
	swal({
		buttons: {
			cancel: "닫기",
		    confirm: "추가"
		},
		content: select,
		closeOnClickOutside: false
	}).then(function(click) {
		if(click) {
			if($("#courseList").val() == "과목을 선택하세요.") {
				swal({
					text: "과목을 선택하세요.",
					button: "확인"
				}).then(function() {
					subjectAddBtn();
				});
			}
			else {
				var param = "type=add";
				param += "&courseNo=" + $("#courseList").val();
				location.href="/user/my/class?" + param;
			}
		}
	});
}

// 담당과목 삭제 버튼 이벤트
function classRemoveBtn() {
	var courseNo = event.currentTarget.closest("div").id;
	
	swal({
		text: "선택한 과목을 삭제하시겠습니까?",
		buttons: {
			cancel: "닫기",
		    confirm: "삭제"
		},
		closeOnClickOutside: false
	}).then(function(click) {
		if(click) {
			var param = "type=remove";
			param += "&courseNo=" + courseNo;
			location.href="/user/my/class?" + param;
		}

	});
}
