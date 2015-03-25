$("#login-form").validate({
    rules: {
         email: {
             required: true,
             email: true,
             rangelength:[5,30]
         },
         password: {
             required: true,
             rangelength:[6,30]
         }
    },
    messages: {
    	email: {
    		required: "请填写邮箱",
            email: "请填写合法邮箱",
            rangelength:"合法邮箱长度为5-30字符",
        },
        password: {
            required: "请填写密码",
            rangelength: "合法密码长度为6-30字符"
        }
    },
    submitHandler: function(form) {
        form.submit();
    }
});
