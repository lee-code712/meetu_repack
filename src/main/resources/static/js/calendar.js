document.addEventListener('DOMContentLoaded', function () {
  var calendarEl = document.getElementById('calendar');
  var calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
    headerToolbar: { // 헤더에 표시할 툴 바
      start: 'prev next today',
      center: 'title',
      end: 'dayGridMonth,dayGridWeek,dayGridDay'
    },
    titleFormat: function (date) {
      return date.date.year + '년 ' + (parseInt(date.date.month) + 1) + '월';
    },
    //initialDate: '2021-07-15', // 초기 날짜 설정 (설정하지 않으면 오늘 날짜가 보인다.)
    selectable: true, // 달력 일자 드래그 설정가능
    droppable: true,
    editable: true,
    nowIndicator: true, // 현재 시간 마크
    locale: 'ko' // 한국어 설정
  });
  calendar.render();

  // console.log(schedules);
  for (key in schedules) {
	var name = ""
	
	if (role == 0) { // 교수: 0 일 때
		name = schedules[key].stuUser.memberInfo.name;	
	}
	else if (role == 1) { // 학생: 1
		name = schedules[key].profUser.memberInfo.name;
	}

    calendar.addEvent({
      title: name + "님과 상담",
      start: schedules[key].startDate,
      end: schedules[key].endDate,
      allDay: true
    })
  }

});
