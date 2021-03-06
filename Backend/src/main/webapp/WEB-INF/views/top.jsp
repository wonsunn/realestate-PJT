<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>


<html lang="en">
<head>
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<title>Happy House</title>
<meta content="" name="description" />
<meta content="" name="keywords" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- Favicons -->
<link href="${root}/img/favicon.png" rel="icon" />
<link href="${root}/img/apple-touch-icon.png" rel="apple-touch-icon" />

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet" />

<!-- Vendor CSS Files -->
<link href="${root}/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${root}/vendor/icofont/icofont.min.css" rel="stylesheet" />
<link href="${root}/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet" />
<link href="${root}/vendor/remixicon/remixicon.css" rel="stylesheet" />
<link href="${root}/vendor/owl.carousel/assets/owl.carousel.min.css"
	rel="stylesheet" />
<link href="${root}/vendor/animate.css/animate.min.css" rel="stylesheet" />
<link href="${root}/vendor/aos/aos.css" rel="stylesheet" />
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

<!-- Template Main CSS File -->
<link href="${root}/css/style.css" rel="stylesheet" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link href="${root}/css/custom.css" rel="stylesheet" />
<script src="${root}/vendor/jquery/jquery.min.js"></script>
<script src="${root}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${root}/vendor/jquery.easing/jquery.easing.min.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>

<body>
	<div id="entire_area" style="height:100%;min-height:100%;min-width:1200px;position:relative;">
		<div id="header" style="height:70px;">
			<div class="container-fluid d-flex align-items-center" style="height:40px">
				<h1 class="logo mr-auto">
					<a href="/"><img id="logo_img"
						src="${root}/img/logo.png" width="60" alt="The SSAFY" /></a>
					<span class="logo-title">HAPPY HOUSE</span>
				</h1>
	
				<!--nav Start-->
				<nav class="nav-menu justify-content-start">
					<ul>
						<li><a href="${root}/notice/list">????????????</a></li>
						<li><a href="#">????????? ??????</a></li>
						<li><a href="javascript:void(0);" onclick="clickLikeList();">?????? ??????</a></li>
						<li class="drop-down" id="interest1" style="display: none"><a
							href="">????????????</a>
							<ul>
								<li><a href="#" data-toggle="modal"
									data-target="#interestarea">???????????? ??????</a></li>
								<li><a href="#">???????????? ????????????</a></li>
							</ul></li>
					</ul>
				</nav>
				<!-- .nav-menu -->
				<c:if test="${userinfo eq null }">
					<a href="login.html" class="get-started-btn" id="login1"
						data-toggle="modal" data-target="#loginModal">?????????</a>
	
					<a href="${root}/member/mvregist" class="get-started-btn" id="login2">????????????</a>
				</c:if>
				<c:if test="${userinfo ne null }">
					<div class="ml-2" id="welcome" style="">${userinfo.username}???
						???????????????</div>
	
					<a href="${root}/member/logout" class="get-started-btn"
						id="logoutbtn" style="">????????????</a>
					<a href="#" class="get-started-btn" id="infobtn" style=""
						data-toggle="modal" data-target="#userModal">????????????</a>
				</c:if>
			</div>
	
		</div>
		<div id="header-depth2" class="depth2" style="height:50px;">
			<a class="on">?????????</a>
			<a class="" >???????????????/??????</a>
		</div>




	<!-- ?????????????????? Modal -->
	<div class="modal fade" id="userModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">?????? ?????? ??????</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				
				<!-- Modal body -->
				<div class="modal-body" >
				<form class="user" method="post" action="${root}/member/modify">
						<input type="hidden" name="act" value="modify"/>

						<div class="form-group">
							<label>????????? : ${userinfo.userid}</label> <input type="hidden"
								name="userid" value="${userinfo.userid}" />
						</div>
						<div class="form-group">
							<label>???????????? : </label> 
							<input type="password"
								class="form-control form-control-user" id="LoginPwd"
								aria-describedby="emailHelp" placeholder="??????????????? ???????????? ????????????."
								name="userpwd" />

						</div>
						<div class="form-group">
							<label>?????? :  </label> <input type="text"
								class="form-control form-control-user" id="LoginName"
								aria-describedby="emailHelp" 
								name="username" value="${userinfo.username }" />
						</div>
						<div class="form-group">
							<label>?????? : </label>
							<input type="text"
								class="form-control form-control-user" id="LoginAddress"
								aria-describedby="emailHelp" 
								name="address" value="${userinfo.address }"/>
							
						</div>
						<div class="form-group">
							<label>???????????? : </label>
							<input type="text"
								class="form-control form-control-user" id="LoginPhone"
								aria-describedby="emailHelp" 
								name="phone"  value="${userinfo.phone }"/>
							
						</div>
						<button type="submit" class="btn btn-warning">??????</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">??????</button>
					</form>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
				<a  href="${root}/member/delete" class="btn btn-danger" >??????</a>
				</div>
				
			</div>
		</div>
	</div>
	<!--????????????modal end-->

	<!--????????? modal start-->
	<div class="modal fade" id="loginModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">LogIn</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->

				<div class="modal-body">
					<form class="user" action="${root}/member/login" method="post">
						<div class="form-group">
							<input type="text" class="form-control form-control-user"
								id="LoginId" aria-describedby="emailHelp"
								placeholder="Enter Id..." name="userId" /> <input
								type="hidden" name="act" value="login" />
						</div>
						<div class="form-group">
							<input type="password" class="form-control form-control-user"
								id="exampleInputPassword" placeholder="Password" name="userPwd" />
						</div>
						<div class="form-group">
							<div class="custom-control custom-checkbox small">
								<input type="checkbox" class="custom-control-input"
									id="customCheck" /> <label class="custom-control-label"
									for="customCheck">Remember Me</label>
								<button type="submit" class="btn btn-primary" id="LoginBtn"
									onclick="javascript: login()">?????????</button>
							</div>
						</div>

						<hr />
						<div class="text-center">
							<a class="small" data-toggle="modal" data-target="#searchModal">Forgot
								Password?</a>
						</div>
						<div class="text-center">
							<a class="small" href="register.html">Create an Account!</a>
						</div>

						<!--onclick="javascript: login();"   href="" -->
					</form>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer"></div>

			</div>
		</div>
	</div>
	<!--????????? modal end-->

	<!--???????????? ?????? modal start-->
	<div class="modal fade" id="searchModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">???????????? ??????</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form class="user">
						<label for="exampleInputId">?????????:</label>
						<div class="form-group">
							<input type="text" class="form-control form-control-user"
								id="exampleInputId" placeholder="??????" />
						</div>

						<div class="form-group">
							<label for="exampleInputEmail">?????????:</label> <input type="email"
								class="form-control form-control-user" id="exampleInputEmail"
								placeholder="Email Address" />
						</div>

						<div class="form-group">
							<label for="exampleInputPhone">????????????:</label> <input type="text"
								class="form-control form-control-user" id="exampleInputPhone"
								placeholder="010-xxxx-xxxx" />
						</div>

						<a href="index.html" class="btn btn-primary btn-user btn-block"
							id="join"> ???????????? ?????? </a>
					</form>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-warning" id="LoginBtn"
						onclick="javascript: login();" data-dismiss="modal"
						href="${root}/member?act=login">????????? ????????????
					</button>
				</div>
			</div>
		</div>
	</div>