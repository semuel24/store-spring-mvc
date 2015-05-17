<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>tubevpn</title>

    <!-- Bootstrap Core CSS -->
	<link
		href="<c:url value="/resources/startbootstrap-business-casual/css/bootstrap.min.css" />"
		rel="stylesheet">
	
	<!-- Custom CSS -->
	<link
		href="<c:url value="/resources/startbootstrap-business-casual/css/business-casual.css" />"
		rel="stylesheet">
    
    <link href="<c:url value="/resources/css/front.common.css" />" rel="stylesheet">

    <!-- Fonts -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Josefin+Slab:100,300,400,600,700,100italic,300italic,400italic,600italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div class="brand">tubevpn</div>

    <!-- Navigation -->
  	<%@ include file="common/nav.jsp"%>

    <div class="container">

        <div class="row">
            <div class="box">
                <div class="col-lg-12">
                    <hr>
                    <h2 class="intro-text text-center">
                        <strong>使用协助代码修改密码</strong>
                    </h2>
                    <hr>
                    
                    <form:form action="changepasswordwithcode" method="post" commandName="changePasswordWithCodeForm" role="form" id="change-pass-withcode-form">
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>邮箱</label>
                                <input id="email" name="email" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>请输入协助代码(如果您使用了忘记密码服务，代码在您收到的通知邮件当中)</label>
                                <input id="code" name="code" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>请输入新密码</label>
                                <input id="newpass" name="newpass" type="password" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>请再次输入新密码</label>
                                <input id="newpassconfirm" name="newpassconfirm" type="password" class="form-control">
                            </div>
                        </div>
                       
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <input type="hidden" name="save" value="contact">
                                <button type="submit" class="btn btn-warning btn-block">修改密码</button>
                            </div>
                        </div>
                    </form:form>  
                </div>
            </div>
        </div>

    </div>
    <!-- /.container -->

	<%@ include file="common/footer.jsp"%>
    <!-- <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <p>Copyright &copy; www.tubevpn.com 2014</p>
                </div>
            </div>
        </div>
    </footer> -->

    <!-- CDN js -->
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
	
	<!-- js -->
	<script src="<c:url value="/resources/js/change.pass.withcode.validation.js" />" ></script>

</body>

</html>
