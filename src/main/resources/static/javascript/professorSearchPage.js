$(document).ready(function(){ // html이 로드되면 실행됨 
	if (isReservated == 1) {
		alert('이미 상담 예약이 신청된 교수님입니다.');
	}
	if (hasConsultableTime == 0) {
		alert('선택한 교수에 대한 상담 가능 시간대가 없습니다.');
	}
	if (date_check == false) {
		alert('동일한 시간대에 예약 내역이 존재합니다.');
	}
	if (is_added == false) {
		alert('예약에 실패했습니다.');
	}
});

// college 클릭 시 slideUp/Down 이벤트 추가
$('.college').click(function() {
	$('.deptList').slideUp();
	if ($(this).children('.deptList').is(':visible')) {
		$(this).children('.deptList').slideUp();
	} else {
		$(this).children('.deptList').slideDown();
	}
});

function searchProfessorByDept(deptNo) {
	$("#searchResult").children().remove();
	location.href = "professorSearchByDeptNo?deptNo=" + deptNo;
}

function searchProfessorByKeyword() {
	var searchText = $("#searchText").val();
	$("#searchResult").children().remove();

	location.href = "professorSearch?keyword=" + searchText;
}

function selectProf() {
	var profNo = event.currentTarget.closest("tr").id;
	location.href = "reservationForm?profNo=" + profNo;
}