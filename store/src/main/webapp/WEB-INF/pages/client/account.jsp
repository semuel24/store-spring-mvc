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
    <link href="<c:url value="css/bootstrap.min.css" />" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:url value="css/business-casual.css" />" rel="stylesheet">

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
    <nav class="navbar navbar-default" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!-- navbar-brand is hidden on larger screens, but visible when the menu is collapsed -->
                <!-- <a class="navbar-brand" href="index.html">Business Casual</a> -->
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="index.html">主页</a>
                    </li>
                    <li>
                        <a href="product.html">产品</a>
                    </li>
                    <li>
                        <a href="account.html">帐户</a>
                    </li>
                    <li>
                        <a href="login.html">登陆</a>
                    </li>
                    <li>
                        <a id="logout" href="">登出</a>
                    </li>
                    <li>
                        <a href="signup.html">注册</a>
                    </li>
                    <li>
                        <a href="contact.html">联系我们</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <div class="container">
        <div class="row">
            <div class="box">
                <div class="col-lg-12">
                    <hr>
                    <h2 class="intro-text text-center">Your
                        <strong>account</strong>
                    </h2>
                    <hr>
                    
                    <form role="form">
                        <div class="row">
                            <div class="form-group col-lg-6">
                                <label>姓名:</label><span>Sam</span>
                            </div>
                            <div class="form-group col-lg-6">
                                <button type="submit" class="btn btn-default">编辑</button>
                            </div>
                            <div class="form-group col-lg-6">
                                <label>用户名:</label><span>Sam</span>
                            </div>
                            <div class="form-group col-lg-6">
                                <button type="submit" class="btn btn-default">编辑</button>
                            </div>
                            <div class="form-group col-lg-6">
                                <label>电子邮件:</label><span>sam@tubevpn.com</span>
                            </div>
                            <div class="form-group col-lg-6">
                                <button type="submit" class="btn btn-default">编辑</button>
                            </div>

                            <div class="form-group col-lg-6">
                                <label>密码:</label><span>******</span>
                            </div>
                            <div class="form-group col-lg-6">
                                <button type="submit" class="btn btn-default">编辑</button>
                            </div>

                            <div class="form-group col-lg-6">
                                <label>当前套餐:</label><span>免费试用</span>
                            </div>
                            <div class="form-group col-lg-6">
                                <button type="submit" class="btn btn-default">编辑</button>
                            </div>
                        </div>
                    </form>
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
    <script src="<c:url value="js/jquery.js"/>" ></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="js/bootstrap.min.js"/>" ></script>

</body>

</html>
