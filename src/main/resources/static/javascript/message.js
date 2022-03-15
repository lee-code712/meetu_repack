$(document).ready(function() { // html이 로드되면 실행됨 
  	$(".tab").click(getMessages);
  	var memMsgName;
  	var memMsgId;
});

function getMessages() {
	memMsgName = $(this).text();
	memMsgId = $('#memMsgId').val();
	$('#screen').children().remove();
	
	location.href = "/message/searchMessages?memMsgId=" + memMsgId + "&memMsgName=" + memMsgName;
}

function updatePage(responseText) {		
	for(var key in messages) {
		var sentDate = messages[key].sentDate;
		var content = messages[key].content;
		var temp_html = '';
		
		if(role == "0") {
			// 시간대 하이퍼링크
			if (content.indexOf("9시") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?time=9&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("10시") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?time=10&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("11시") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?time=11&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("12시") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?time=12&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("1시") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?time=13&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("2시") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?time=14&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("3시") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?time=15&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("4시") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?time=16&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("5시") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?time=17&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("31일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=31&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("30일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=30&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("29일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=29&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("28일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=28&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("27일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=27&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("26일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=26&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("25일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=25&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("24일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=24&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("23일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=23&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("22일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=22&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("21일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=21&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("20일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=20&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("19일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=19&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("18일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=18&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("17일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=17&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("16일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=16&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("15일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=15&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("14일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=14&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("13일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=13&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("12일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=12&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("11일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=11&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("10일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=10&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("9일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=09&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("8일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=08&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("7일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=07&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("6일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=06&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("5일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=05&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("4일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=04&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("3일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=03&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("2일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=02&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("1일") > -1) {
				content = "<a href=\"/reservationUpdateForm.do?date=01&res_id=" + $("#mem_usr_res_id").val() + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
		}
		
		if(messages[key].recvId == user_id) {
			temp_html += "<div id=\"receiveMessage\"> <div id=\"receiveMsg\">" + mem_usr_name + "</div>";
			temp_html += "<div id=\"receiveMessageText\"> <div id=\"messageContent\">" + content + "</div></div>";
			temp_html += "<div id=\"receiveDate\">" + sentDate + "</div>";
			temp_html += "</div>";
		}
		else {
			temp_html += "<div id=\"sendMessage\">";
			temp_html += "<div id=\"sendMessageText\"> <div id=\"messageContent\">" + content + "</div></div>";
			temp_html += "<div id=\"sendDate\">" + sentDate;
			if(messages[key].isRead == 1) {	
				temp_html += " 읽음</div>";
			}
			else {
				temp_html += " 읽지않음</div>";
			}
			temp_html += "</div>";
		}
		// console.log(temp_html);
		$('#screen').append(temp_html);
	}
	
}

function sendMessage() {
	var msg = $('#chat').val();
	
	if(msg == "") {
		alert("전송할 메시지 내용이 없습니다.");
		return false;
	}
}

function updateMessages() {
	$('#messageText').val("");
	$('#messageResult').children().remove();
	
	$.ajax({
	 	type: "GET",
		url: "/message/searchMessages.jsp?mem_usr_name=" + mem_usr_name,
		dataType: "text",
		success: updatePage,
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

//알림 내역 확인
function readNewAlerts() {
	window.open("alert.do", "childform", "width=400; height=260; left=400; top=180; resizable = no;");
	$("#noticeCount").text(0);
}

//알림 내역에서 클릭한 바로가기 페이지 url로 이동
window.movePage = function(url) {
	location.href = url;
}