$(document).ready(function(){ // html이 로드되면 실행됨 
	// 각 버튼에 click 이벤트 설정
	$(".startTimeBox").click(ck_startTimeBox);
	$(".timeBox").click(ck_timeBox);
	// $(".typeBtn").click(typeBtnClick); // 예약 수정 시 trigger("click")함수가 작동이 안되서 html에 onclick 직접 작성함
	$(".reservationBtn").click(reservationBtnClick);
	
	drawMap(); // 구글 맵 불러오기
});

function drawMap() {
	if (coordinates != null) {
		var lat;
		var lng;
		
		lat = coordinates.latitude;
		lng = coordinates.longitude;
		
		var LatLng = new google.maps.LatLng(lat, lng);
		var mapProp = {
			center: LatLng, // 지도 중심
			zoom: 17 // 확대 배율
		};
	
		var map = new google.maps.Map(document.getElementById("office_map"), mapProp);
	
		var marker = new google.maps.Marker({
			position: LatLng,
			map: map
		});
		
		google.maps.event.addDomListener(window, "resize", function() { //리사이즈에 따른 마커 위치
	        var center = map.getCenter();
	        google.maps.event.trigger(map, "resize");
	        map.setCenter(center); 
    	});
	}
}

/**
 * @brief   날짜 클릭 시
 * @details 선택 가능한 시작시간을 출력한다.
 */
function calendarChoiceDate() {
	var today = new Date();
	var choiceDate = $("#choiceDate").val();
    var contentDay = choiceDate.split('-')[2];
    
    // startTimeBox 초기화
	var classes = document.getElementsByClassName("startTimeBox");
	Array.from(classes).forEach(function(c, i) {
		$(c).css("background", "#EAEAEA");
		$(c).css("border", "1px solid #EAEAEA");
		$(c).css("cursor", "");
		$(c).children("a").css("color", "black");
		$(c).attr("isDisabled", "true");
		$(c).off("click");
	});
	
	// startTimeBox onclick 이벤트 제어
	Array.from(consultableTimes).forEach(function(consultableTime, i) {
		var ableDate = consultableTime.ableDate;
		var ableTime = consultableTime.ableTime;
		
		let doMonth = new Date(today.getFullYear(), today.getMonth(), 1);
		var dateObj = new Date(doMonth.getFullYear(), doMonth.getMonth(), Number(contentDay));
		
		// 상담 가능 일자에 대하여
		if(ableDate != null) {
			if (dateObj.getDay() == ableDate) {			
				var able_timeArr = ableTime.split("~"); // 상담 가능 시간 배열
				var j = 0;
				
				while (classes.length > j) {
					if($(classes[j]).attr("id") == able_timeArr[0])
						break;
					j++;
				}
				
				while (classes.length > j) {
					if($(classes[j]).attr("id") == able_timeArr[1])
						break;
					$(classes[j]).css("background", "#FFFFFF");
					$(classes[j]).attr("isDisabled", "false");
					j++;
				}
			}
		}
	});
	
	Array.from(reservations).forEach(function(reservation, i) {
		var startDate = reservation.startDate;
		// 현재 수정하려는 예약일자와 같은 경우 넘어감
		if (reservationInfo != null && startDate == reservationInfo['startDate']) {
			return;
		}
		
		var endDate = reservation.endDate;
		var startTime = startDate.split(" ")[1].substring(0, 5);
		var endTime = endDate.split(" ")[1].substring(0, 5);
		let doMonth = new Date(today.getFullYear(), today.getMonth(), 1);
			
		var disable_dateArr = startDate.split(" ")[0].split("-"); // 예약이 차 있는 날짜 배열
			
		if ((doMonth.getMonth() + 1) == Number(disable_dateArr[1]) && Number(contentDay) == Number(disable_dateArr[2])) { 			
			// 불가능 시작 시간
			var j = 0;
				
			while (classes.length > j) {
				if($(classes[j]).attr("id") == startTime)
					break;
				j++;
			}
				
			while (classes.length > j) {
				if($(classes[j]).attr("id") == endTime)
					break;
				$(classes[j]).attr("isDisabled", "true");
				classes[j].style.backgroundColor = "#EAEAEA";
				j++;
			}
		}
	});
	
	// 각 시작시간 항목의 isDisabled속성이 false인 경우 클릭 이벤트 생성
	Array.from(classes).forEach(function(element, i) {
		if($(element).attr("isDisabled") == "false") {
			$(element).click(startTimeBoxClick);
			$(element).css("cursor", "pointer");
		}
	});
	
	// 예약정보가 있다면 해당하는 버튼이 선택되도록 설정
	if (reservationInfo != null) {
    	var start = reservationInfo['startDate'].substring(11, 16);
    	var end = reservationInfo['endDate'].substring(11, 16);
    	var time = end.substring(0,2) - start.substring(0,2);
	    $("div.startTimeBox:contains(" + start + ")").trigger("click");    
	    $("div.timeBox:contains(" + time + ")").trigger("click");
	    
	    var reason = reservationInfo['reason'];
	    var radio_ck = $("label:contains(" + reason + ")").prop('tagName');
	    if(radio_ck == null) {
	    	 $("label[for='radio5']").trigger("click");
	    	 $("#anotherReason").val(reason);
	    }
	    else {
	    	$("label:contains(" + reason + ")").trigger("click");
	    }
	    
	    var type = reservationInfo['type'];
	    console.log(type);
	    if(type == 0) {
	    	$("#typeBtnOff").trigger("click");
	    }
	    else {
	    	$("#typeBtnOn").trigger("click");
    	}
    }
}

/**
 * @brief   시작시간 클릭 시
 * @details 사용자가 선택한 시작시간에 체크표시를 남기고, 선택 가능한 상담시간대를 출력한다.
 */
function startTimeBoxClick() {
	var today = new Date();
	
	// timeBox 초기화
	$(".timeBox").css("background", "#FFFFFF");
	$(".timeBox").css("border", "1px solid #EAEAEA");
	$(".timeBox").css("cursor", "");
	$(".timeBox").children("a").css("color", "black");
	$(".timeBox").attr("isDisabled", "false"); 
	$(".timeBox").off("click");
		
	var startTimeClasses = document.getElementsByClassName("startTimeBox");
	
	Array.from(startTimeClasses).forEach(function(c, i) {
		if($(c).attr("isDisabled") == "false") {
			$(c).css("background", "#FFFFFF");
			$(c).css("border", "1px solid #EAEAEA");
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
	
	var content = $(startTimeBox).children().first().text();
	
	$(newInputElement).attr("value", content);
	
	$("#startTimeTitle").append(newInputElement);
	
	// startTimeBox onclick 이벤트 제어
	var timeClasses = document.getElementsByClassName("timeBox");
	var click_time = $(startTimeBox).attr("id").substring(0,2);
	var next_time = Number(click_time) + 1;

	if ($("div.startTimeBox:contains(" + next_time + ":00)").attr("isDisabled") == "true") { // 다음 startTime이 isDisabled == true면
		$(timeClasses[1]).attr("isDisabled", "true"); // 2시간 block (1시간만 상담 가능)
	}
	
	if (consultableTimes != null && ($(startTimeBox).attr("id") == "16:00" || $(startTimeBox).attr("id") == "17:00")) { // 17시 이후 교수 불가능한 시간대 계산
		Array.from(consultableTimes).forEach(function(consultableTime, i) {
			var ableDate = consultableTime.ableDate;
			var ableTime = consultableTime.ableTime;
			
			let doMonth = new Date(today.getFullYear(), today.getMonth(), 1);
			var contentDay = $("input#choiceDate").val();
			var dateObj = new Date(doMonth.getFullYear(), doMonth.getMonth(), Number(contentDay));
			
			// 상담 가능 일자에 대하여
			if(ableDate != null) {
				if (dateObj.getDay() == ableDate) {			
					var able_timeArr = ableTime.split("~"); // 상담 가능 시간 배열
					
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
			$(c).css("border", "1px solid #EAEAEA");
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
	
	var content = $(timeBox).children().first().text();
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
		$("div#typeBtnOn").css("border", "1px solid  #EAEAEA");
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
	
	var content = $(typeBtn).children().first().text();
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
	if(!$("#choiceDate").val()) {
		alert("상담 날짜를 먼저 선택해 주세요.");
		return false;
	}
}

function ck_timeBox() {
	if(!$("#choiceDate").val()) {
		alert("상담 날짜를 먼저 선택해 주세요.");
		return false;
	}

	if(!$("#startTime").attr("value")) {
		alert("상담 시작 시간을 먼저 선택해 주세요.");
		return false;
	}
}

function ck_reservation_form() {
	if(!$("#choiceDate").val()) {
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