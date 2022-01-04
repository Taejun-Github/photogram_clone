// (1) 회원정보 수정
function update(userId) {
	//alert("!");
	let data = $("#profileUpdate").serialize();
	console.log(userId);
	console.log(data);

	$.ajax({
		type: "put",
		url: `/api/user/${userId}`, //백틱임에 주의한다.
		data: data,
		contentType:"application/x-www-form-urlencoded; charset=uft-8",
		dataType: "json"
	}).done(res => {
		console.log("성공");	
		location.href = `/user/${userId}`;
	}).fail(error => {
		console.log("실패");	
	});

}