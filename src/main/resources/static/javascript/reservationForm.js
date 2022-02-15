$(document).ready(function(){ // html이 로드되면 실행됨 
	// 각 버튼에 click 이벤트 설정
	$(".startTimeBox").click(ck_startTimeBox);
	$(".timeBox").click(ck_timeBox);
	$(".typeBtn").click(typeBtnClick);
	$(".reservationBtn").click(reservationBtnClick);
	
	buildCalendar(); // 캘린더 호출
	
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

var today = new Date(); // @param 전역 변수, 오늘 날짜 / 내 컴퓨터 로컬을 기준으로 today에 Date 객체를 넣어줌
var date = new Date();  // @param 전역 변수, today의 Date를 세어주는 역할

/**
 * @brief   캘린더 오픈
 * @details 날짜 값을 받아 캘린더 폼을 생성하고, 날짜값을 채워넣는다. 이전 달, 다음 달 클릭 시 today를 변경해 호출한다.
 */
function buildCalendar() {
	if(event != null) {
		var click_month = event.currentTarget.closest("td").id;
		if(click_month == "prevMonth") {
			this.today = new Date(today.getFullYear(), today.getMonth() - 1, 1); // 전달
		}
		else if(click_month == "nextMonth") {
			this.today = new Date(today.getFullYear(), today.getMonth() + 1, 1); // 다음달
		}
	}

    let doMonth = new Date(today.getFullYear(), today.getMonth(), 1);
    let lastDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);

    let tbCalendar = document.querySelector(".scriptCalendar > tbody");

    document.getElementById("calYear").innerText = today.getFullYear();		// @param YYYY월
    document.getElementById("calMonth").innerText = autoLeftPad((today.getMonth() + 1), 2);   // @param MM월

    // @details 이전 캘린더의 출력결과가 남아있다면, 이전 캘린더를 삭제한다.
    while (tbCalendar.rows.length > 0) {
        tbCalendar.deleteRow(tbCalendar.rows.length - 1);
    }

    // @param 첫번째 개행
    let row = tbCalendar.insertRow();

    // @param 날짜가 표기될 열의 증가값
    let dom = 1;

    // @details 시작일의 요일값( doMonth.getDay() ) + 해당월의 전체일( lastDate.getDate())을  더해준 값에서
    // 7로 나눈값을 올림( Math.ceil() )하고 다시 시작일의 요일값( doMonth.getDay() )을 빼준다.

    let daysLength = (Math.ceil((doMonth.getDay() + lastDate.getDate()) / 7) * 7) - doMonth.getDay();

    // @param 달력 출력

    // @details 시작값은 1일을 직접 지정하고 요일값( doMonth.getDay() )를 빼서 마이너스( - )로 for문을 시작한다.
    for (let day = 1 - doMonth.getDay(); daysLength >= day; day++) {

        let column = row.insertCell();

        // @param 평일( 전월일과 익월일의 데이터 제외 )
        if (Math.sign(day) == 1 && lastDate.getDate() >= day) {
            // @param 평일 날짜 데이터 삽입
            column.innerText = autoLeftPad(day, 2);

            // @param 일요일인 경우
            if (dom % 7 == 1) {
                column.style.color = "#FF4D4D";
            }

            // @param 토요일인 경우
            if (dom % 7 == 0) {
                column.style.color = "#4D4DFF";
                row = tbCalendar.insertRow();   // @param 토요일이 지나면 다시 가로 행을 한줄 추가한다.
            }

        }
        // @param 평일 전월일과 익월일의 데이터 날짜변경
        else {
            let exceptDay = new Date(doMonth.getFullYear(), doMonth.getMonth(), day);
            column.innerText = autoLeftPad(exceptDay.getDate(), 2);
            column.style.color = "#E5E5E5";
        }

        // @brief   전월, 명월 음영처리
        // @details 현재년과 선택 년도가 같은경우
        if (today.getFullYear() == date.getFullYear()) {

            // @details 현재월과 선택월이 같은경우
            if (today.getMonth() == date.getMonth()) {

                // @details 현재일보다 이전인 경우이면서 현재월에 포함되는 일인경우
                if (date.getDate() > day && Math.sign(day) == 1) {
                	column.style.color = "#E5E5E5";
                }               
                // @details 현재일보다 이후이면서 현재월에 포함되는 일인경우
                else if (date.getDate() < day && lastDate.getDate() >= day) {
					// 교수 가능 일자만 click event 활성화
					for(var key in schedules) {
						var able_date = schedules[key].able_date;
						var able_time = schedules[key].able_time;
						var size = Object.keys(schedules).length;
						
						var dateObj = new Date(doMonth.getFullYear(), doMonth.getMonth(), Number(day));
						
						if (dateObj.getDay() == able_date) { // 가능 일자이면 
							column.style.backgroundColor = "#FFFFFF";
                    		column.style.cursor = "pointer";
                    		column.onclick = function () {
                        		calendarChoiceDay(this, schedules);
                    		}
							break;
						}
						else if (key == Object.keys(schedules)[size - 1]) {
							// alert("getDay(): " + (dateObj.getDay()) + ", disable_date: " + disable_date + ", disable_time: " + disable_time);
							column.style.color = "#E5E5E5";
						}
						else {
							continue;
						}
					}
                }
                // @details 현재일인 경우
                else if (date.getDate() == day) {
					column.style.backgroundColor = "#7195F1";
					 column.style.color = "#E5E5E5";
                }
                // @details 현재월보다 이전인경우
            } else if (today.getMonth() < date.getMonth()) {
                if (Math.sign(day) == 1 && day <= lastDate.getDate()) {
                    column.style.color = "#E5E5E5";
                }
            }
            // @details 현재월보다 이후인경우
            else {
                if (Math.sign(day) == 1 && day <= lastDate.getDate()) {
                	// 교수 가능 일자만 click event 활성화
					for(var key in schedules) {
						var able_date = schedules[key].able_date;
						var able_time = schedules[key].able_time;
						var size = Object.keys(schedules).length;
						
						var dateObj = new Date(doMonth.getFullYear(), doMonth.getMonth(), Number(day));
						
						if (dateObj.getDay() == able_date) { // 가능 일자이면
							column.style.backgroundColor = "#FFFFFF";
                    		column.style.cursor = "pointer";
                    		column.onclick = function () {
                        		calendarChoiceDay(this, schedules);
                    		}
							break;
						}
						else if (key == Object.keys(schedules)[size - 1]) {
							// alert("getDay(): " + (dateObj.getDay()) + ", disable_date: " + disable_date + ", disable_time: " + disable_time);
							column.style.color = "#E5E5E5";
						}
						else {
							continue;
						}
					}
                }
            }
        }
        // @details 선택한년도가 현재년도보다 작은경우
        else if (today.getFullYear() < date.getFullYear()) {
            if (Math.sign(day) == 1 && day <= lastDate.getDate()) {
                column.style.backgroundColor = "#E5E5E5";
            }
        }
        // @details 선택한년도가 현재년도보다 큰경우 - 어떻게 년도를 찾아야 할지 모르겠음... > 다음 년도는 캘린더에서 넘어가지 않도록
        else {
//            if (Math.sign(day) == 1 && day <= lastDate.getDate()) {
//                column.style.backgroundColor = "#FFFFFF";
//                column.style.cursor = "pointer";
//                column.onclick = function () {
//                    calendarChoiceDay(this, schedules);
//                }
//            }
        }
        
        dom++;
    }
}

/**
 * @brief   숫자 두자릿수( 00 ) 변경
 * @details 자릿수가 한지라인 ( 1, 2, 3등 )의 값을 10, 11, 12등과 같은 두자리수 형식으로 맞추기위해 0을 붙인다.
 * @param   num     앞에 0을 붙일 숫자 값
 * @param   digit   글자의 자릿수를 지정 ( 2자릿수인 경우 00, 3자릿수인 경우 000 … )
 */
function autoLeftPad(num, digit) {
    if (String(num).length < digit) {
        num = new Array(digit - String(num).length + 1).join("0") + num;
    }

    return num;
}

/**
 * @brief   날짜 클릭 시
 * @details 사용자가 선택한 날짜에 체크표시를 남기고, 선택 가능한 시작시간을 출력한다.
 */
function calendarChoiceDay(column, schedules) {
    // @param 기존 선택일이 존재하는 경우 기존 선택일의 표시형식을 초기화 한다.
    if ($("#choiceDay").length > 0) {
    	if(document.getElementsByClassName("choiceDay")[0]) {
	        document.getElementsByClassName("choiceDay")[0].style.backgroundColor = "#FFFFFF";
	        document.getElementsByClassName("choiceDay")[0].classList.remove("choiceDay");
    	}
        // 추가 - 기존 선택일이 존재하는 경우 hidden 값 삭제
        $("#choiceDay").remove();
        $("#choiceMonth").remove();
    }

    // @param 선택일 체크 표시
    column.style.backgroundColor = "#1abc9c";

    // @param 선택일 클래스명 변경
    column.classList.add("choiceDay");

    // 추가 - hidden 값 생성
    // 선택한 일
    var newInputDayElement = document.createElement("input");
    $(newInputDayElement).attr("type", "hidden");
    $(newInputDayElement).attr("id", "choiceDay");
    $(newInputDayElement).attr("name", "choiceDay");

    var contentDay = column.innerHTML;
    $(newInputDayElement).attr("value", contentDay);

    $("#navRContentWrap").append(newInputDayElement);

    // 선택한 월
    var newInputMonthElement = document.createElement("input");
    $(newInputMonthElement).attr("type", "hidden");
    $(newInputMonthElement).attr("id", "choiceMonth");
    $(newInputMonthElement).attr("name", "choiceMonth");

    var contentMonth = document.getElementById("calMonth").innerHTML;
    $(newInputMonthElement).attr("value", contentMonth);

    $("#navRContentWrap").append(newInputMonthElement);

    // startTimeBox 초기화
	var classes = document.getElementsByClassName("startTimeBox");
	Array.from(classes).forEach(function(c, i) {
		$(c).css("background", "#E5E5E5");
		$(c).css("border", "1px solid #C4C4C4");
		$(c).css("cursor", "");
		$(c).children("a").css("color", "black");
		$(c).attr("isDisabled", "true");
		$(c).off("click");
	});
	
	// startTimeBox onclick 이벤트 제어
	Array.from(schedules).forEach(function(schedule, i) {
		var able_date = schedule.able_date;
		var able_time = schedule.able_time;
		var disable_day = schedule.disable_day;
		var disable_date = schedule.disable_date;
		var disable_time = schedule.disable_time;
		
		let doMonth = new Date(today.getFullYear(), today.getMonth(), 1);
		var dateObj = new Date(doMonth.getFullYear(), doMonth.getMonth(), Number(contentDay));
		
		// 상담 가능 일자에 대하여
		if(able_date != null) {
			if (dateObj.getDay() == able_date) {			
				var able_timeArr = able_time.split("~"); // 상담 가능 시간 배열
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
		// 상담 불가능 일자(이미 예약이 있는 경우)에 대하여
		else {
			var disable_dateArr = disable_day.split("-"); // 예약이 차 있는 날짜 배열
			
			if (dateObj.getDay() == disable_date && (doMonth.getMonth() + 1) == disable_dateArr[0] && Number(contentDay) == disable_dateArr[1]) { 			
				// 불가능 시작 시간
				var disable_timeArr = disable_time.split("~"); // 예약이 차 있는 시간 배열
				var j = 0;
				
				while (classes.length > j) {
					if($(classes[j]).attr("id") == disable_timeArr[0])
						break;
					j++;
				}
				
				while (classes.length > j) {
					if($(classes[j]).attr("id") == disable_timeArr[1])
						break;
					$(classes[j]).attr("isDisabled", "true");
					classes[j].style.backgroundColor = "#E5E5E5";
					j++;
				}
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