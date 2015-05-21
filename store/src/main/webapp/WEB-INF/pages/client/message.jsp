<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>tubevpn</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" />

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
                ${message}
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

    <!-- jQuery -->
	<%-- <script
		src="<c:url value="/resources/startbootstrap-business-casual/js/jquery.js"/>"></script> --%>

	<!-- Bootstrap Core JavaScript -->
	<%-- <script
		src="<c:url value="/resources/startbootstrap-business-casual/js/bootstrap.min.js"/>"></script> --%>

</body>

</html>
