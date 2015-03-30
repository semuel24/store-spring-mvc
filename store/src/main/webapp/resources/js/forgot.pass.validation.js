$("#forgot-pass-form").validate({
	rules : {
		email : {
			required : true,
			email : true,
			rangelength : [ 5, 30 ],
			remote : {
				onkeyup : false,
				url : "checkemailexistence",
				type : "post",
				data : {
					email : function() {
						return $("#email").val();
					},
					expectAlreadyExist : function() {
						return 1;//expect exist
					}
				}
			}
		}
	},
	messages : {
		email : {
			required : "请填写邮箱",
			email : "请填写合法邮箱",
			rangelength : "合法邮箱长度为5-30字符",
			remote : "您填写的为无效邮箱"
		}
	},
	submitHandler : function(form) {
		form.submit();
	}
});
