function toggleText() {
	const text = document.getElementById("accountWrap");
	if (text.style.display === "none") {
		text.style.display = "block";
	} else {
		text.style.display = "none";
	}
}

function viewReservation() {
	const consultId = event.currentTarget.closest("tr").id;
	const url = "/consult/viewReservation?consultId=" + consultId;

	window.open(url, "childform",
			"width=600; height=400; left=300; top=130; resizable=no;");
}

function check() {
	const buttonType = event.currentTarget.id;
	const consultId = event.currentTarget.closest("tr").id;
	let data;
	
	if(buttonType == "editBtn") {
		// 수정 페이지로 이동
	}
	else if(buttonType == "cancelBtn") {
		if (confirm("선택한 예약을 취소하시겠습니까?") == true) {
			data = {"status" : 4, "id" : consultId}
			addCancelMessage(data);
		}
		else 
			return;
	}
	else if(buttonType == "approvalBtn") {
		if (confirm("선택한 예약을 승인하시겠습니까?") == true) {
			data = {"status" : 1, "id" : consultId}
			changeReservationState(data);
		}
		else 
			return;
	}
	else if(buttonType == "rejectBtn") {
		if (confirm("선택한 예약을 반려하시겠습니까?") == true) {
			data = {"status" : 2, "id" : consultId}
			addCancelMessage(data);
		}
		else 
			return;
	}
	else if(buttonType == "consultedBtn") {
		if (confirm("선택한 상담을 진행하셨습니까?") == true) {
			data = {"status" : 3, "id" : consultId}
			changeReservationState(data);
		}
		else 
			return;
	}
	else if(buttonType == "consultationRecordBtn") {
		// 상담기록 페이지로 이동
	}
}

function changeReservationState(data) {
	$.ajax({
		type: "GET",
		url: "/consult/changeStatus",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: data,
		dataType: "text",
		success: function() {
			if(data["status"] == 1) {
				alert("승인되었습니다.");
				location.reload();
			}
			else if(data["status"] == 2) {
				alert("반려되었습니다.");
				location.reload();
			}
			else if(data["status"] == 4) {
				alert("취소되었습니다.");
				location.reload();
			}
			else if(data["status"] == 3) {
				if (confirm("상담일지를 지금 기록하시겠습니까? 기록한 내역은 학교측에 전달됩니다.\n[마이페이지]-[상담완료]에서 작성 및 수정이 가능합니다.") == true) {
					// 상담기록 페이지 이동
				}
				else {
					alert("완료되었습니다.");
					location.reload();
				}
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			var message = jqXHR.getResponseHeader("Status");
			if ((message == null) || (message.length <= 0)) {
				alert("Error! Request status is " + jqXHR.status);
			} else {
				alert(message);	
			}
		}
	});
}

function addCancelMessage(data) {
	swal({
		buttons: {
			cancel: "닫기",
		    confirm: "확인"
		},
		content: {
			element: "textarea",
			attributes: {
				id: "cancelMsg",
				placeholder: "사유를 입력하세요."
			},
		},
		closeOnClickOutside: false
	}).then(function(click) {
		if($("#cancelMsg").val() == "" && click != null) {
			swal({
				text: "사유를 입력하세요.",
				button: "확인"
			}).then(function() {
				addCancelMessage(data);
			});
		}
		else {
			let sender = $("#dropdown-button").text();
			sender = sender.substring(0, 4) + "의 메시지";
			let cancelMsg = $("#cancelMsg").val();
			data["cancelMsg"] = cancelMsg;
			changeReservationState(data);
		}
	});
}