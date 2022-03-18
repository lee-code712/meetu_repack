$(document).ready(function() { // html이 로드되면 실행됨 
  	$(".tab").click(getMessages);
  	var memMsgName;
  	var memMsgId;
  	
  	if(role == "0") {
		var messages = document.getElementsByClassName("content");
		
		for (var i = 0; i < messages.length; i++ ) {
			var message = messages.item(i);
			var content = message.innerHTML;
			
			// 시간대 하이퍼링크
			if (content.indexOf("9시") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?time=09&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("10시") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?time=10&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("11시") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?time=11&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("12시") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?time=12&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("1시") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?time=13&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("2시") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?time=14&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("3시") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?time=15&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("4시") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?time=16&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("5시") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?time=17&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("31일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=31&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("30일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=30&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("29일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=29&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("28일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=28&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("27일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=27&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("26일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=26&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("25일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=25&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("24일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=24&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("23일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=23&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("22일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=22&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("21일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=21&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("20일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=20&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("19일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=19&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("18일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=18&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("17일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=17&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("16일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=16&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("15일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=15&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("14일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=14&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("13일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=13&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("12일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=12&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("11일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=11&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("10일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=10&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("9일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=09&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("8일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=08&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("7일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=07&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("6일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=06&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("5일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=05&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("4일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=04&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("3일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=03&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("2일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=02&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
			else if (content.indexOf("1일") > -1) {
				message.innerHTML = "<a href=\"/consult/updateReservationForm?date=01&consultId=" + consultId + "\" style=\"text-decoration: underline;\">" + content + "</a>";
			}
		}
	}
});

function getMessages() {
	memMsgName = $(this).text();
	memMsgId = $(this).attr("id");
	var consultId = $(this).children("input:eq(2)").attr("id");
	$('#screen').children().remove();
	
	location.href = "/message/searchMessages?memMsgId=" + memMsgId + "&consultId=" + consultId + "&memMsgName=" + memMsgName;
}

function updatePage(responseText) {		
	for(var key in messages) {
		var sentDate = messages[key].sentDate;
		var content = messages[key].content;
		var temp_html = '';
		
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
	
	var consultHidden = $("li[id=" + memMsgId +"]").children("input:eq(2)").clone();
	$("#chatWrap").append(consultHidden);
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