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
                <div class="col-lg-12 text-center">
                    <div id="carousel-example-generic" class="carousel slide">
                        <!-- Indicators -->
                        <ol class="carousel-indicators hidden-xs">
                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner">
                            <div class="item active">
                                <!-- <img class="img-responsive img-full" src="img/slide-1.jpg" alt=""> -->
                                <img class="img-responsive img-full" src="<c:url value="/resources/startbootstrap-business-casual/img/internet.png" />" alt="">
                            </div>
                            <div class="item">
                                <img class="img-responsive img-full" src="<c:url value="/resources/startbootstrap-business-casual/img/slide-2.jpg" />" alt="">
                            </div>
                            <div class="item">
                                <img class="img-responsive img-full" src="<c:url value="/resources/startbootstrap-business-casual/img/slide-3.jpg" />" alt="">
                            </div>
                        </div>

                        <!-- Controls -->
                        <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                            <span class="icon-prev"></span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                            <span class="icon-next"></span>
                        </a>
                    </div>
                    <h2 class="brand-before">
                        <small>Welcome to</small>
                    </h2>
                    <h1 class="brand-name">tubevpn</h1>
                    <hr class="tagline-divider">
                    <h2>
                        <small>By
                            <strong>www.tubevpn.com</strong>
                        </small>
                    </h2>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="box">
                <div class="col-lg-12">
                    <hr>
                    <h2 class="intro-text text-center"><strong>vpn技术简介</strong></h2>
                    <hr>
                    <img class="img-responsive img-border img-left" src="<c:url value="/resources/startbootstrap-business-casual/img/intro-pic.jpg" />" alt="">
                    <hr class="visible-xs">
                    <p>虚拟专用网（英语：Virtual Private Network，简称VPN），是一种常用于连接中、大型企业或团体与团体间的私人网络的通讯方法。虚拟私人网络的讯息透过公用的网络架构（例如：互联网）来传送内联网的网络讯息。它利用已加密的通道协议（Tunneling Protocol）来达到保密、发送端认证、消息准确性等私人消息安全效果。这种技术可以用不安全的网络（例如：互联网）来发送可靠、安全的消息。需要注意的是，加密消息与否是可以控制的。没有加密的虚拟专用网消息依然有被窃取的危险。</p>
                    <p>以日常生活的例子来比喻，虚拟专用网就像：甲公司某部门的A想寄信去乙公司某部门的B。A已知B的地址及部门，但公司与公司之间的信不能注明部门名称。于是，A请自己的秘书把指定B所属部门的信（A可以选择是否以密码与B通信）放在寄去乙公司地址的大信封中。当乙公司的秘书收到从甲公司寄到乙公司的邮件后，该秘书便会把放在该大信封内的指定部门邮件以公司内部邮件方式寄给B。同样地，B会以同样的方式回信给A。</p>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="box">
                <div class="col-lg-12">
                    <hr>
                    <h2 class="intro-text text-center">安全性</h2>
                    <hr>
                    <p>安全的虚拟私人网络使用加密穿隧协议，通过阻止截听与嗅探来提供机密性，还允许发送者身份验证，以阻止身份伪造，同时通过防止信息被修改提供消息完整性。</p>
                    <p>某些虚拟私人网络不使用加密保护数据。虽然虚拟私人网络通常都会提供安全性，但未加密的虚拟私人网络严格来说是不“安全”或“可信”的。例如，一条通过GRE协议在两台主机间创建的隧道属于虚拟私人网络，但既不安全也不可信。 除以上的GRE协议例子外，本地的明文穿隧协议包括L2TP（不带IPsec时）和PPTP（不使用微软点对点加密(MPPE)时）。</p>
                </div>
            </div>
        </div>

        <%-- <div class="row">
            <div class="box">
                <div class="col-lg-12">
                    <hr>
                    <h2 class="intro-text text-center">第一次使用步骤(Mac)</h2>
                    <hr>
                    <p>1. 如果您还没有tubevpn帐户，请到<a href="signup">注册页面</a>注册一个新账户。注册完成之后，您将得到tubevpn免费试用帐户。您会收到一封确认邮件，并且在附件里面会包括一个profile文件。这个profile文件包括了最新的vpn服务器的地址信息。</p>
                    <p>2. 到<a href="signup">首页</a>下载符合您机器的客户端(windows/mac/android)，并安装。</p>
                    <p>3. 打开客户端程序，引入刚才的profile文件，并在弹出的页面输入您的注册邮箱和密码</p>
                    <p><img src="<c:url value="/resources/startbootstrap-business-casual/img/connect1.png" />"></p>
                    <p><img src="<c:url value="/resources/startbootstrap-business-casual/img/connect2.png" />"></p>
                    <p>4. 看到这个图片，说明您应该已经连上了vpn服务。并且在图表中显示出连接的时间长度。以后在使用tubevpn服务时只需要重复步骤3，4即可。</p>
                    <p><img src="<c:url value="/resources/startbootstrap-business-casual/img/connected1.png" />" ></p>
                    <p>5. 点disconnect断开服务</p>
                    <p><img src="<c:url value="/resources/startbootstrap-business-casual/img/disconnect1.png" />"></p>
                </div>
            </div>
        </div> --%>

        <!-- <div class="row">
            <div class="box">
                <div class="col-lg-12">
                	<hr>
                    <h2 class="intro-text text-center">使用文档下载</h2>
                    <hr>
                    <p><a href="http://pan.baidu.com/s/16NCy6" target="_blank">Mac使用文档下载</a></p>
                    <p><a href="http://pan.baidu.com/s/1eQD1itc" target="_blank">iPhone使用文档下载</a></p>
                    <p>Windows/Android用户请参考Mac文档</p>
                    <hr>
                    <h2 class="intro-text text-center">客户端下载</h2>
                    <hr>
                    <p><a href="http://pan.baidu.com/s/1mg3eQMk" target="_blank">windows客户端下载</a></p>
                    <p><a href="http://pan.baidu.com/s/1bnyD6oB" target="_blank">Mac客户端下载</a></p>
                    <p><a href="http://pan.baidu.com/s/1pJ9eynh" target="_blank">安卓客户端下载</a></p>
                    <p>想要下载iphone客户端，请在appstore里面搜索关键字"openvpn"</p>
                </div>
            </div>
        </div> -->

        <div class="row">
            <div class="box">
                <div class="col-lg-12">
                    <hr>
                    <h2 class="intro-text text-center">推荐网站</h2>
                    <hr>
                    <p>
                        <a href="http://www.instagram.com"><img src="<c:url value="/resources/startbootstrap-business-casual/img/inslogo.jpeg" />" ></a>
                        <a href="http://www.facebook.com"><img src="<c:url value="/resources/startbootstrap-business-casual/img/fblogo.jpeg" />" ></a>
                        <a href="http://www.twitter.com/?lang=en"><img src="<c:url value="/resources/startbootstrap-business-casual/img/ttlogo.jpeg" />" ></a>
                    </p>
                    <p>
                        <a href="http://www.youtube.com"><img src="<c:url value="/resources/startbootstrap-business-casual/img/youtubelogo.png" />"></a>
                        <a href="http://www.hulu.com"><img src="<c:url value="/resources/startbootstrap-business-casual/img/hulu.jpeg" />" ></a>
                        <a href="https://www.netflix.com/us/"><img src="<c:url value="/resources/startbootstrap-business-casual/img/netflix.jpeg" />"></a>
                    </p>
                    <p>
                        <a href="http://www.mitbbs.com"><img src="<c:url value="/resources/startbootstrap-business-casual/img/mitbbslogo.jpeg" />"></a>
                        <a href="http://www.6park.com/us.shtml"><img src="<c:url value="/resources/startbootstrap-business-casual/img/6park.jpeg" />"></a>
                        <a href="http://huaren.us/static/index.html"><img src="<c:url value="/resources/startbootstrap-business-casual/img/huarenlogo.jpeg" />"></a>
                        <a href="http://www.wenxuecity.com/"><img src="<c:url value="/resources/startbootstrap-business-casual/img/wenxuechenglogo.jpeg" />"></a>
                    </p>
                    <p>
                        <a href="http://www.coach.com/online/handbags/Home-10551-10051-en"><img src="<c:url value="/resources/startbootstrap-business-casual/img/coachlogo.png" />"></a>
                        <a href="http://www.amazon.com/"><img src="<c:url value="/resources/startbootstrap-business-casual/img/amazonlogo.jpeg" />"></a>
                        <a href="http://www.ebay.com/"><img src="<c:url value="/resources/startbootstrap-business-casual/img/ebaylogo.jpeg" />"></a>
                    </p>
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
    <%-- <script src="<c:url value="/resources/startbootstrap-business-casual/js/jquery.js"/>" ></script> --%>

	<!-- CDN js -->
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
	
    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="/resources/startbootstrap-business-casual/js/bootstrap.min.js"/>" ></script>

    <!-- Script to Activate the Carousel -->
    <script>
    $('.carousel').carousel({
        interval: 5000 //changes the speed
    })
    </script>

</body>

</html>
