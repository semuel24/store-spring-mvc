$("#change-pass-withcode-form").validate({
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
		},
		code : {
			required : true,
			rangelength : [ 1, 30 ]
		},
		newpass : {
			required : true,
			rangelength : [ 6, 30 ]
		},
		newpassconfirm : {
			required : true,
			rangelength : [ 6, 30 ],
			equalTo : '#newpass'
		}
	},
	messages : {
		email : {
			required : "请填写邮箱",
			email : "请填写合法邮箱",
			rangelength : "合法邮箱长度为5-30字符",
			remote : "您填写的为无效邮箱"
		},
		code : {
			required : "请填写协助代码",
			rangelength : "合法协助代码长度为6-30字符"
		},
		newpass : {
			required : "请填写新密码",
			rangelength : "合法密码长度为6-30字符"
		},
		newpassconfirm : {
			required : "请再次填写新密码",
			rangelength : "合法密码长度为6-30字符",
			equalTo : "请保证新密码一致"
		}
	},
	submitHandler : function(form) {
		form.submit();
	}
});
