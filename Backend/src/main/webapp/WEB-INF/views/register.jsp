
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
  <head>
  
    <meta charset="utf-8" />
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />

    <title>Happy House</title>
    <meta content="" name="description" />
    <meta content="" name="keywords" />

    <!-- Favicons -->
    <link href="${root}/img/favicon.png" rel="icon" />
    <link href="${root}/img/apple-touch-icon.png" rel="apple-touch-icon" />

    <!-- Google Fonts -->
    <link
      href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
      rel="stylesheet"
    />

    <!-- Vendor CSS Files -->
    <link href="${root}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${root}/vendor/icofont/icofont.min.css" rel="stylesheet" />
    <link href="${root}/vendor/boxicons/css/boxicons.min.css" rel="stylesheet" />
    <link href="${root}/vendor/remixicon/remixicon.css" rel="stylesheet" />
    <link href="${root}/vendor/owl.carousel/${root}/owl.carousel.min.css" rel="stylesheet" />
    <link href="${root}/vendor/animate.css/animate.min.css" rel="stylesheet" />
    <link href="${root}/vendor/aos/aos.css" rel="stylesheet" />

    <!-- Template Main CSS File -->
    <link href="${root}/css/style.css" rel="stylesheet" />

    <!-- =======================================================
  * Template Name: Mentor - v2.2.1
  * Template URL: https://bootstrapmade.com/mentor-free-education-bootstrap-theme/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
  </head>

  <body class="bg-gradient-primary">
    <div class="container">
      <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
          <!-- Nested Row within Card Body -->
          <div class="row">
            <div class="col-lg-5 d-none d-lg-block bg-register-image">
                <img src="${root}/img/welcome.PNG" class="rounded" alt="Cinque Terre" width="100%" height="100%"> 
            </div>
            <div class="col-lg-7">
              <div class="p-5">
                <div class="text-center">
                  <h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
                </div>
                <form class="user" id="user" action="${root}/member/regist">
                  <label for="exampleInputId">아이디:</label>
                  	<input
                    type="hidden"
                    name="act"
                    value="register"
                  />
                    <input
                      type="text"
                      class="form-control form-control-user"
                      id="exampleInputId"
                      placeholder="Id"
                      name="userid"
                    />
                  <label for="exampleInputName">이름:</label>
                  
                    <input
                      type="text"
                      class="form-control form-control-user"
                      id="exampleInputName"
                      placeholder="Name"
                      name="username"
                    />

                  
                    <label for="exampleInputpwd" class="mt-3">비밀번호:</label>
                    <input
                      type="password"
                      class="form-control form-control-user"
                      id="exampleInputpwd"
                      placeholder="Password"
                      name="userpwd"
                    />
                
                  
                    <label for="exampleInputEmail" class="mt-3">이메일:</label>
                    <input
                      type="email"
                      class="form-control form-control-user"
                      id="exampleInputEmail"
                      placeholder="Email Address"
                      name="email"
                    />
                  

                 
                    <label for="exampleInputAddress" class="mt-3">주소:</label>
                    <input
                      type="text"
                      class="form-control form-control-user"
                      id="exampleInputAddress"
                      placeholder="Address"
                      name="address"
                    />
                 

                  
                    <label for="exampleInputPhone" class="mt-3">전화번호:</label>
                    <input
                      type="text"
                      class="form-control form-control-user"
                      id="exampleInputPhone"
                      placeholder="010-xxxx-xxxx"
                      name="phone"
                    />
                 

                  <hr />
                  <button
                type="submit"
                class="btn btn-primary"
                id="join"
               
               
               
              >회원가입</button>
                </form>

            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="${root}/vendor/jquery/jquery.min.js"></script>
    <script src="${root}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="${root}/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
     
      $('#join').on('click', function () {
        let $inputs = $('#user>input');
        //console.log($inputs);
        let flag = true;

        $.each($inputs, function (idx, item) {
          let val = $(item).val();
          if (!val) {
            flag = false;
            alert($(item).prev().text() + '가 없어요.'); //형=label
            return false;
          }
        });
      });
    </script>
  </body>
</html>
