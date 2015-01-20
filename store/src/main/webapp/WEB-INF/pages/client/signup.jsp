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
    <link href="<c:url value="/resources/startbootstrap-business-casual/css/bootstrap.min.css" />" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:url value="/resources/startbootstrap-business-casual/css/business-casual.css" />" rel="stylesheet">

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
                        <strong>创建新用户</strong>
                    </h2>
                    <hr>
                    <form:form action="signup" method="post" commandName="signupForm" role="form">
                        <!-- <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>昵称</label>
                                <input name="name" id="name" type="text" class="form-control">
                            </div>
                        </div> -->
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>电子邮件(用户名)</label>
                                <input name="email" id="email" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>确认电子邮件</label>
                                <input name="confirmedemail" id="confirmedemail" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>密码</label>
                                <input name="password" id="password" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>确认密码</label>
                                <input name="confirmedpassword" id="confirmedpassword" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <input type="hidden" name="save" value="contact">
                                <button type="submit" class="btn btn-warning btn-block">创建您的账户</button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>

    </div>
    <!-- /.container -->

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <p>Copyright &copy; www.tubevpn.com 2014</p>
                </div>
            </div>
        </div>
    </footer>

    <!-- jQuery -->
    <script src="<c:url value="/resources/startbootstrap-business-casual/js/jquery.js"/>" ></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="/resources/startbootstrap-business-casual/js/bootstrap.min.js"/>" ></script>

</body>

</html>
