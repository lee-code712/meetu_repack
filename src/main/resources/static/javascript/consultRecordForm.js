function updateContent() {
	var url = new URL(window.location.href);
	var urlParams = url.searchParams;
	var consult_id = urlParams.get('consultId');
	var content = $("#consultContent").val();
	
	if (content == '') {
		alert("상담한 내용을 입력해주세요.");
	}
	else {
		var data = {"id":consult_id, "content":content};
		$.ajax({
			 type: "POST",
			url: "/consult/changeRecord",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			data: data,
			dataType: "text",
			success: function() {
				alert("입력한 내용이 저장되었습니다.");
				location.reload();
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
}