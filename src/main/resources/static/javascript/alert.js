//알림 내역 확인
function readNewAlerts() {
	console.log("알림 열기");
//	window.open("alert.do", "childform", "width=400; height=260; left=400; top=180; resizable = no;");
//	$("#noticeCount").text(0);
}

// 알림 내역에서 클릭한 바로가기 페이지 url로 이동
window.movePage = function(url) {
	location.href = url;
}