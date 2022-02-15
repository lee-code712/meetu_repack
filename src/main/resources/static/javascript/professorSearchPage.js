$(document).ready(function(){ // html이 로드되면 실행됨 
	if (isReservated) {
		alert('이미 상담 예약이 신청된 교수님입니다.');
	}
	if (hasConsultableTime == 0) {
		alert('상담이 가능한 시간대가 없습니다.');
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

	$.ajax({
		url: '/consult/professorSearch',
		type: "POST",
		data: {
			deptNo: deptNo
		},
		dataType: "JSON",
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

function searchProfessorByKeyword() {
	var searchText = $("#searchText").val();
	$("#searchResult").children().remove();

	location.href = "professorSearch?keyword=" + searchText;
}

function updatePage(professors) {
	if (jQuery.isEmptyObject(professors)) {
		alert("검색 결과가 없습니다.");
		return;
	}
	console.log(professors);
	var idx = 1;
	for (key in professors) {
		var profNo = professors[key].memberNo;
		var name = professors[key].member.name;
		var email = professors[key].member.email;
		var dept = professors[key].member.deptInfo.deptName;
		var major = professors[key].major;
		var office = professors[key].officeNo;
		var courses = professors[key].courses;
		var is_member = professors[key].user;

		var newTrElement = document.createElement("tr");
		// tr의 id로 교수 id 사용
		$(newTrElement).attr("id", profNo);

		// 번호
		var newCountTdElement = document.createElement("td");
		newCountTdElement.innerHTML = idx++;

		// 교수 이름
		var newNameTdElement = document.createElement("td");
		newNameTdElement.innerHTML = name;

		// 교수 소속 학과
		var newMajorTdElement = document.createElement("td");
		newMajorTdElement.innerHTML = dept;

		// div를 자식으로 갖는 info td
		var newInfoTdElement = document.createElement("td");

		var newInfoDivElement = document.createElement("div");
		$(newInfoDivElement).attr("id", "infoBox");

		// info div의 자식 img infoBtn
		var newInfoImgElement = document.createElement("img");
		$(newInfoImgElement).attr("id", "infoBtn");
		$(newInfoImgElement).attr("src", "/images/info.svg");

		// info div의 자식 infoBox div
		var newInfoBoxDivElement = document.createElement("div");
		$(newInfoBoxDivElement).attr("id", "infoBox-content");

		// infoBox div의 자식 p
		// 전공 infoTitle
		var newMajorTitlePElement = document.createElement("div");

		var newMajorTitleSpanElement = document.createElement("span");
		$(newMajorTitleSpanElement).attr("id", "infoTitle");
		newMajorTitleSpanElement.innerHTML = "전공";

		$(newMajorTitlePElement).append(newMajorTitleSpanElement);

		// 전공명
		var newMajorPElement = document.createElement("div");
		newMajorPElement.innerHTML = major;

		// 담당과목 infoTitle
		var newCourseTitlePElement = document.createElement("div");

		var newCourseTitleSpanElement = document.createElement("span");
		$(newCourseTitleSpanElement).attr("id", "infoTitle");
		newCourseTitleSpanElement.innerHTML = "담당과목";

		$(newCourseTitlePElement).append(newCourseTitleSpanElement);

		// 담당과목명
		var newCoursePElement = document.createElement("div");
		for (k in courses) {
			title = courses[k].title;
			var newCourseDivElement = document.createElement("div");
			newCourseDivElement.innerHTML += title;
			newCoursePElement.append(newCourseDivElement);
		}
		
		// 이메일 infoTitle
		var newEmailTitlePElement = document.createElement("div");

		var newEmailTitleSpanElement = document.createElement("span");
		$(newEmailTitleSpanElement).attr("id", "infoTitle");
		newEmailTitleSpanElement.innerHTML = "이메일";

		$(newEmailTitlePElement).append(newEmailTitleSpanElement);

		// 이메일
		var newEmailPElement = document.createElement("div");
		newEmailPElement.innerHTML = email;

		// 연구실 infoTitle
		var newOfficeTitlePElement = document.createElement("div");

		var newOfficeTitleSpanElement = document.createElement("span");
		$(newOfficeTitleSpanElement).attr("id", "infoTitle");
		newOfficeTitleSpanElement.innerHTML = "연구실 위치";

		$(newOfficeTitlePElement).append(newOfficeTitleSpanElement);

		// 연구실 위치
		var newOfficePElement = document.createElement("div");
		newOfficePElement.innerHTML = office;

		// infoBox-content div에 p들 추가
		$(newInfoBoxDivElement).append(newMajorTitlePElement);
		$(newInfoBoxDivElement).append(newMajorPElement);
		$(newInfoBoxDivElement).append(document.createElement("p"));
		$(newInfoBoxDivElement).append(newCourseTitlePElement);
		$(newInfoBoxDivElement).append(newCoursePElement);
		$(newInfoBoxDivElement).append(document.createElement("p"));
		$(newInfoBoxDivElement).append(newEmailTitlePElement);
		$(newInfoBoxDivElement).append(newEmailPElement);
		$(newInfoBoxDivElement).append(document.createElement("p"));
		$(newInfoBoxDivElement).append(newOfficeTitlePElement);
		$(newInfoBoxDivElement).append(newOfficePElement);

		// infoBox div에 infoBox-content div, infoImg 추가
		$(newInfoDivElement).append(newInfoImgElement);
		$(newInfoDivElement).append(newInfoBoxDivElement);

		// infoBox td에 infoBox div 추가
		$(newInfoTdElement).append(newInfoDivElement);

		// checkBtn
		var newCheckTdElement = document.createElement("td");

		if (is_member) { // 회원인 경우에만 선택하기 출력
			var newCheckBtnElement = document.createElement("button");
			$(newCheckBtnElement).attr("id", "checkBtn");
			newCheckBtnElement.innerHTML = "선택하기";
			$(newCheckTdElement).append(newCheckBtnElement);
			$(newCheckBtnElement).click(selectProf);
		}

		// tr에 td들 추가
		$(newTrElement).append(newCountTdElement);
		$(newTrElement).append(newNameTdElement);
		$(newTrElement).append(newMajorTdElement);
		$(newTrElement).append(newInfoTdElement);
		$(newTrElement).append(newCheckTdElement);

		// tbody에 tr 추가
		$("#searchResult").append(newTrElement);
	};
}

function selectProf() {
	var profNo = event.currentTarget.closest("tr").id;
	location.href = "reservationForm?profNo=" + profNo;
}