// (1) 회원정보 수정
function update(userId, event) {
	event.preventDefault(); //폼 태그 액션을 막는 역할을 한다.
	//alert("!");
	let data = $("#profileUpdate").serialize();
	console.log(userId);
	console.log(data);

	$.ajax({
		type: "put",
		url: `/api/user/${userId}`, //백틱임에 주의한다.
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=uft-8",
		dataType: "json"
	}).done(res => {
		console.log("성공", res);
		location.href = `/user/${userId}`;
	}).fail(error => {
		if (error.data == null) {
			alert(JSON.stringify(error.responseJSON.message));
		} else {
			alert(JSON.stringify(error.responseJSON.data));
		}

	});

}