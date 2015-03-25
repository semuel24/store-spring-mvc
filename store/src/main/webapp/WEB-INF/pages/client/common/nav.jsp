<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<!-- navbar-brand is hidden on larger screens, but visible when the menu is collapsed -->
			<!-- <a class="navbar-brand" href="index.html">Business Casual</a> -->
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="home">主页</a></li>
				<!-- <li><a href="">产品</a></li> -->
				<c:choose>
					<c:when test="${not empty loggedin}">
						<li><a href="account">帐户</a></li>
						<li><a href="logout">登出</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="login">登陆</a></li>
						<li><a href="signup">注册</a></li>
					</c:otherwise>
				</c:choose>
				<li><a href="contact">联系我们</a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container -->
</nav>