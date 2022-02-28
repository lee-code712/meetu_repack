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

function check(date) {
	const buttonType = event.currentTarget.id;
	const consultId = event.currentTarget.closest("tr").id;
	const today = new Date();
	const consultDay = new Date(date);
	let data;
	
	if(buttonType == "editBtn") {
		 if(today > consultDay) {
			 alert("상담일시가 지나 수정할 수 없습니다. 상담을 완료하시거나 취소하시기 바랍니다.");
		 }
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
		if(today > consultDay) {
			 alert("상담일시가 지나 승인할 수 없습니다. 상담을 반려하시기 바랍니다.");
		}
		else {
			if (confirm("선택한 예약을 승인하시겠습니까?") == true) {
				data = {"status" : 1, "id" : consultId}
				changeReservationState(data);
			}
			else 
				return;
		}
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
		if(today < consultDay) {
			 alert("상담일시가 지나지 않았습니다. 상담을 진행하시기 바랍니다.");
		 }
		else {
			if (confirm("선택한 상담을 진행하셨습니까?") == true) {
				data = {"status" : 3, "id" : consultId}
				changeReservationState(data);
			}
			else 
				return;
		}
	}
	else if(buttonType == "consultationRecordBtn") {
		location.href="/consult/recordConsult?consultId=" + consultId;
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
					location.href="/consult/recordConsult?consultId=" + data["id"];
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
			let sender = $("#dropdownBtn").text();
			sender = sender.slice(0, -3) + "의 메시지";
			let cancelMsg = $("#cancelMsg").val();
			data["cancelMsg"] = sender + "/" + cancelMsg;
			changeReservationState(data);
		}
	});
}

function readCancelMessage(msg) {
	var msgArr = msg.split("/");
	swal({
		title: msgArr[0],
		text: msgArr[1],
		buttons: {
			cancel: "닫기"
		},
		closeOnClickOutside: false
	});
}