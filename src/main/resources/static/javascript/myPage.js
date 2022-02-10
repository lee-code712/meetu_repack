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
	const data;
	
	if(selected_button == "editBtn") {
		// 수정 페이지로 이동
	}
	else if(buttonType == "cancelBtn") {
		if (confirm("선택한 예약을 취소하시겠습니까?") == true)
			data = {"status" : 4, "consultId" : consultId}
			addRejectMessage();
		else 
			return;
	}
	else if(buttonType == "approvalBtn") {
		if (confirm("선택한 예약을 승인하시겠습니까?") == true) 
			data = {"status" : 1, "consultId" : consultId}
			changeReservationState(data);
		else 
			return;
	}
	else if(buttonType == "rejectBtn") {
		if (confirm("선택한 예약을 반려하시겠습니까?") == true) {
			data = {"status" : 2, "consultId" : consultId}
			addRejectMessage();
		}
		else 
			return;
	}
	else if(buttonType == "consultedBtn") {
		if (confirm("선택한 상담을 진행하셨습니까?") == true) {
			data = {"status" : 3, "consultId" : consultId}
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
		 type: "POST",
		url: "/consult/updateStatus",
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
					location.href="consultationRecordPage.do?res_id=" + res_id;
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