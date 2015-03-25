$("#change-pass-form").validate({
	rules : {
		oldpass : {
			required : true,
			rangelength : [ 6, 30 ]
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
		oldpass : {
			required : "请填写旧密码",
			rangelength : "合法密码长度为6-30字符"
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
