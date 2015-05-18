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
    <link rel="shortcut icon" href="<c:url value="/resources/images/bg.jpg" />" />

    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/resources/startbootstrap-business-casual/css/bootstrap.min.css" />" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:url value="/resources/startbootstrap-business-casual/css/business-casual.css" />" rel="stylesheet">
    
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
                        <strong>创建新用户</strong>
                    </h2>
                    <hr>
                    <form:form action="signup" method="post" commandName="signupForm" role="form" id="register-form">
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
                                <input name="password" id="password" type="password" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>确认密码</label>
                                <input name="confirmedpassword" id="confirmedpassword" type="password" class="form-control">
                            </div>
                        </div>
                        <!-- <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <label>邀请码</label>
                                <input name="invitationcode" id="invitationcode" type="text" class="form-control">
                            </div>
                        </div> -->
                        <div class="row">
                            <div class="form-group col-lg-4">

                            </div>
                            <div class="form-group col-lg-4">
                                <div>
                                <span style="span-bottom:10px;">禁止使用VPN网络加速器的服务器进行以下行为：</span><br/>

                                A. 滥发Spam电子邮件，在论坛或留言版批量发送垃圾信息。<br/>
                                B.使用快车，迅雷，旋风等其它P2P下载软件大流量下载任何文件。<br/>
                                C.使用香港，台湾或韩国服务器进行大流量下载<br/>
                                D.进行非法探测，扫描、测试卓越VPN网络加速器系统或其它网络系统以及破坏他人系统等行为。<br/>
                                E.发布传播色情交易、政治煽动、威胁、诽谤、种族歧视以及虐待儿童等言论。<br/>
                                F.禁止私自分享，传播以及公开自己的帐号信息。<br/>
                                如果客户违反以上相关条款，我们有权停止或者删除你的账号，并拒绝任何方式的退款
                                </div>
                                <input type="checkbox" name="agree" id="agree" value="yes" class="form-control">
                                <label>同意</label>
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
	<%-- <script src="<c:url value="/resources/js/jquery.validate.js" />" ></script> --%>
	<script src="<c:url value="/resources/js/signup.validation.js" />" ></script>
</body>

</html>
