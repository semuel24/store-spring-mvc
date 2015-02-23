$("#register-form").validate({
    rules: {
         email: {
             required: true,
             email: true,
             rangelength:[5,30],
             remote: {
            	 onkeyup: false,
                 url: "checkemailexistence",
                 type: "post",
                 data: {
                   email: function() {
                     return $( "#email" ).val();
                   }
                 }
               }
         },
         confirmedemail: {
             equalTo: '#email'
         },
         password: {
             required: true,
             rangelength:[6,30]
         },
         confirmedpassword: {
             equalTo: '#password'
         },
        agree: "required"
    },
    messages: {
    	email: {
    		required: "请填写邮箱",
            email: "请填写合法邮箱",
            rangelength:"合法邮箱长度为5-30字符",
            remote:"该邮箱已注册，请选择另一个邮箱"
        },
        confirmedemail: {
            equalTo: "请填写同一邮箱"  
        },
        password: {
            required: "请填写密码",
            rangelength: "合法密码长度为6-30字符"
        },
        confirmedpassword: {
            equalTo: "请填写同一密码"
        },
        agree: "提交前请选择接受条款"
    },
    submitHandler: function(form) {
        form.submit();
    }
});
