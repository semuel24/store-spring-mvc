$("#contact-form").validate({
	rules : {
		name : {
			required : true,
			rangelength : [ 1, 10 ]
		},
		email : {
			required : true,
			email : true,
			rangelength : [ 5, 30 ]
		},
		qq : {
			number : true,
			rangelength : [ 4, 20 ]
		},
		content : {
			required : true,
			rangelength : [ 1, 1000 ]
		}
	},
	messages : {
		name : {
			required : "请填写名字",
			rangelength : "合法名字长度为1-10字符"
		},
		email : {
			required : "请填写邮箱",
			email : "请填写合法邮箱",
			rangelength : "合法邮箱长度为5-30字符"
		},
		qq : {
			number : "请填写合法QQ号",
			rangelength : "合法QQ长度为4-20字符"
		},
		content : {
			required : "请填写内容",
			rangelength : "合法长度为1-1000字符"
		}
		
	},
	submitHandler : function(form) {
		form.submit();
	}
});
