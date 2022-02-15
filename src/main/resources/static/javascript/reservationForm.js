$(document).ready(function(){ // html이 로드되면 실행됨 
	// 각 버튼에 click 이벤트 설정
	$(".startTimeBox").click(ck_startTimeBox);
	$(".timeBox").click(ck_timeBox);
	$(".typeBtn").click(typeBtnClick);
	$(".reservationBtn").click(reservationBtnClick);
	
	buildMap(); // 구글 맵 불러오기
});

function buildMap() {
	$.ajax({
	 	type: "GET",
		url: "/reservation/getMapInfo.jsp?office=" + office,
		dataType: "text",
		success: drawMap,
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

function drawMap(responseText) {
	var coordinates = JSON.parse(responseText);

	if (!jQuery.isEmptyObject(coordinates)) {
		var lat;
		var lng;
		
		Array.from(coordinates).forEach(function(c, idx) {
			lat = c.latitude;
			lng = c.longitude;
		});
		
		console.log(lat, lng);
		
		var LatLng = new google.maps.LatLng(lat, lng);
		var mapProp = {
			center: LatLng, // 지도 중심
			zoom: 17, // 확대 배율
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
	
		var map = new google.maps.Map(document.getElementById("office_map"), mapProp);
	
		var marker = new google.maps.Marker({
			position: LatLng,
			map: map
		});
	}
}

/**
 * @brief   시작시간 클릭 시
 * @details 사용자가 선택한 시작시간에 체크표시를 남기고, 선택 가능한 상담시간대를 출력한다.
 */
function startTimeBoxClick() {
	// timeBox 초기화
	$(".timeBox").css("background", "#FFFFFF");
	$(".timeBox").css("border", "1px solid #C4C4C4");
	$(".timeBox").css("cursor", "");
	$(".timeBox").children("a").css("color", "black");
	$(".timeBox").attr("isDisabled", "false"); 
	$(".timeBox").off("click");
		
	var startTimeClasses = document.getElementsByClassName("startTimeBox");
	
	Array.from(startTimeClasses).forEach(function(c, i) {
		if($(c).attr("isDisabled") == "false") {
			$(c).css("background", "#FFFFFF");
			$(c).css("border", "1px solid #C4C4C4");
			$(c).children("a").css("color", "black");
		}
	});
	
	var startTimeBox = this;
	
	$(startTimeBox).css("border", "1px solid  #1abc9c");
	$(startTimeBox).css("background", "#1abc9c");
	$(startTimeBox).children("a").css("color", "white");
	
	$("#startTime").remove();
	
	var newInputElement = document.createElement("input");
	$(newInputElement).attr("type", "hidden");
	$(newInputElement).attr("name", "startTime");
	$(newInputElement).attr("id", "startTime");
	
	var content = startTimeBox.childNodes[0].innerHTML;
	
	$(newInputElement).attr("value", content);
	
	$("#startTimeTitle").append(newInputElement);
	
	// startTimeBox onclick 이벤트 제어
	var timeClasses = document.getElementsByClassName("timeBox");
	var click_time = $(startTimeBox).attr("id").substring(0,2);
	var next_time = Number(click_time) + 1;

	if ($("div.startTimeBox:contains(" + next_time + ":00)").attr("isDisabled") == "true") { // 다음 startTime이 isDisabled == true면
		$(timeClasses[1]).attr("isDisabled", "true"); // 2시간 block (1시간만 상담 가능)
	}
	
	if (schedules != null && ($(startTimeBox).attr("id") == "16:00" || $(startTimeBox).attr("id") == "17:00")) { // 17시 이후 교수 불가능한 시간대 계산
		Array.from(schedules).forEach(function(schedule, i) {
			var able_date = schedule.able_date;
			var able_time = schedule.able_time;
			
			let doMonth = new Date(today.getFullYear(), today.getMonth(), 1);
			var contentDay = $("input#choiceDay").val();
			var dateObj = new Date(doMonth.getFullYear(), doMonth.getMonth(), Number(contentDay));
			
			// 상담 가능 일자에 대하여
			if(able_date != null) {
				if (dateObj.getDay() == able_date) {			
					var able_timeArr = able_time.split("~"); // 상담 가능 시간 배열
					
					if($(startTimeBox).attr("id") == "16:00" && able_timeArr[1] == "17:00") {
						$(timeClasses[1]).attr("isDisabled", "true"); // 2시간 block (1시간만 상담 가능)
					}
					
					if($(startTimeBox).attr("id") == "17:00" && able_timeArr[1] == "18:00") {
						$(timeClasses[1]).attr("isDisabled", "true"); // 2시간 block (1시간만 상담 가능)
					}
				}
			}
		});
	}
	
	Array.from(timeClasses).forEach(function(time, i) {
		if($(time).attr("isDisabled") == "false") {
			$(time).click(timeBoxClick);
			$(time).css("cursor", "pointer");
		}
		else {
			$(time).css("backgroundColor", "#E5E5E5");
		}
	});
}

/**
 * @brief   상담시간 클릭 시
 * @details 사용자가 선택한 상담시간에 체크표시를 남긴다.
 */
function timeBoxClick() {
	var classes = document.getElementsByClassName("timeBox");
	
	Array.from(classes).forEach(function(c, i) {
		if($(c).attr("isDisabled") != "true") {
			$(c).css("background", "#FFFFFF");
			$(c).css("border", "1px solid #C4C4C4");
			$(c).children("a").css("color", "black");
		}
	});
	
	var timeBox = this;
	
	$(timeBox).css("border", "1px solid  #1abc9c");
	$(timeBox).css("background", "#1abc9c");
	$(timeBox).children("a").css("color", "white");
	
	$("#consultTime").remove();
	
	var newInputElement = document.createElement("input");
	$(newInputElement).attr("type", "hidden");
	$(newInputElement).attr("name", "consultTime");
	$(newInputElement).attr("id", "consultTime");
	
	var content = timeBox.innerHTML;
	$(newInputElement).attr("value", content);
	
	$("#timeTitle").append(newInputElement);
}

/**
 * @brief   상담유형 버튼 클릭 시
 */
function typeBtnClick() {
	var typeBtn = this;
	
	if($(typeBtn).attr('id') == "typeBtnOff") {
		$("div#typeBtnOff").css("border", "1px solid  #1abc9c");
		$("div#typeBtnOff").css("background", "#1abc9c");
		$("div#typeBtnOff > a").css("color", "white");
		$("div#typeBtnOn").css("border", "1px solid  #C4C4C4");
		$("div#typeBtnOn").css("background", "#FFFFFF");
		$("div#typeBtnOn > a").css("color", "black");
	}
	else {
		$("div#typeBtnOn").css("border", "1px solid  #1abc9c");
		$("div#typeBtnOn").css("background", "#1abc9c");
		$("div#typeBtnOn > a").css("color", "white");
		$("div#typeBtnOff").css("border", "1px solid  #C4C4C4");
		$("div#typeBtnOff").css("background", "#FFFFFF");
		$("div#typeBtnOff > a").css("color", "black");
	}

	$("#type").remove();
	
	var newInputElement = document.createElement("input");
	$(newInputElement).attr("type", "hidden");
	$(newInputElement).attr("name", "type");
	$(newInputElement).attr("id", "type");
	
	var content = typeBtn.innerHTML;
	$(newInputElement).attr("value", content);
	
	$(".typeBtnWrap").append(newInputElement);
}

/**
 * @brief   예약버튼 클릭 이벤트
 * @details 상담이유를 기타로 선택한 경우 입력된 이유를 value로 넣어 전달한다.
 */
function reservationBtnClick() {
	if($("input:radio[id='radio5']").is(":checked") && $("#anotherReason").val()) {
		$("#radio5").attr("value", $("#anotherReason").val());
	}
}

/**
 * @brief   버튼들을 클릭했는지 확인
 */
function ck_startTimeBox() {
	if(!$("#choiceDay").attr("value")) {
		alert("상담 날짜를 먼저 선택해 주세요.");
		return false;
	}
}

function ck_timeBox() {
	if(!$("#choiceDay").attr("value")) {
		alert("상담 날짜를 먼저 선택해 주세요.");
		return false;
	}

	if(!$("#startTime").attr("value")) {
		alert("상담 시작 시간을 먼저 선택해 주세요.");
		return false;
	}
}

function ck_reservation_form() {
	var form = document.join_form;
        
	if(!$("#choiceDay").attr("value")) {
		alert("상담 날짜를 선택해 주세요.");
		return false;
	}
    	
	if(!$("#startTime").attr("value")) {
		alert("상담 시작 시간을 선택해 주세요.");
		return false;
	}
    
	if(!$("#consultTime").attr("value")) {
		alert("상담 시간을 선택해 주세요.");
		return false;
	}
	
	if(!$("input:radio[name=radio]").is(":checked")) {
		alert("상담 신청 사유를 선택해 주세요.");
		return false;
	}
	
	if(!$("#type").attr("value")) {
		alert("상담 유형을 선택해 주세요.");
		return false;
	}
	
	if($("input:radio[id='radio5']").is(":checked") && !$("#anotherReason").val()) {
		alert("기타 사유를 입력해 주세요.");
		return false;
	}
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