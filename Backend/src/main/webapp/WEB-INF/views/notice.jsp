<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/top.jsp" %>

    <!-- End Header -->

    <!-- ======= Hero Section ======= -->
    <section id="hero" class="d-flex justify-content-center align-items-center">
      <div class="container position-relative" data-aos="zoom-in" data-aos-delay="100">
        <h1>공지사항</h1>
      </div>
    </section>
    <!-- End Hero -->

    <main id="main">
      <!-- ======= About Section ======= -->
      <section id="about" class="about">
        <div class="container" data-aos="fade-up">
          <div class="row">
            <div class="col-md-3">
              <nav class="navbar navbar-expand-sm float-left">
                <form class="form-inline" action="/action_page.php">
                <c:if test="${userinfo ne null}">
                  <button
                    class="btn btn-warning"
                    type="button"
                    data-toggle="modal"
                    data-target="#writeNotice"
                  >
                    글쓰기
                  </button>
                  </c:if>
                </form>
              </nav>
            </div>

            <div class="col-md-9">
              <nav class="navbar navbar-expand-sm float-right">
                <form class="form-inline" action="/action_page.php">
                  <button
                    type="button"
                    class="btn btn-light dropdown-toggle"
                    data-toggle="dropdown"
                  >
                    선택
                  </button>
                  <div class="dropdown-menu">
                    <a class="dropdown-item" href="#">아이디</a>
                    <a class="dropdown-item" href="#">글번호</a>
                    <a class="dropdown-item" href="#">제목</a>
                  </div>
                  <input class="form-control mr-sm-2" type="text" placeholder="검색어 입력" />
                  <button class="btn btn-secondary" type="submit">검색</button>
                </form>
              </nav>
              <br />
            </div>

            <!--Header 밑 nav End-->
            <!--table Start-->
            <table class="table table-hover mt-3" >
              <thead>
                <tr>
                  <th style="width: 15%">글번호</th>
                  <th style="width: 40%">제목</th>
                  <th style="width: 15%">작성자</th>
                  <th style="width: 15%">조회수</th>
                  <th style="width: 15%">작성일</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="item" items="${notices}"   >
                <tr onclick="location.href='${root}/notice/content?articleno=${item.articleno}'">
                  <td>${item.articleno }</td>
                  <td>${item.subject }</td>
                  <td>${item.userid }</td>
                  <td>${item.views }</td>
                  <td>${item.regtime }</td>
                </tr>
                
                <!-- <tr>
                  <td>3</td>
                  <td>javascript</td>
                  <td>김싸피</td>
                  <td>13</td>
                  <td>21.01.05</td>
                </tr> -->
                
                </c:forEach>
              </tbody>
            </table>

            <!--페이지 이동 버튼 start-->
            <div class="container">
              <ul class="pagination justify-content-center">
                <li class="page-item"><a class="page-link" href="#"><<</a></li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item"><a class="page-link" href="#">>></a></li>
              </ul>
            </div>
               ${navigation.navigator}
            <!--페이지 이동 버튼 end-->
          </div>
        </div>
      </section>
      <!-- End About Section -->

      <!-- The Modal -->
      <div class="modal fade" id="writeNotice">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">공지사항 작성</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
              <form action="${root}/notice/write">
                <div class="form-group">
                <input type ="hidden" name="act" value="write"/>
                  <label for="title">제목:</label>
                  <input type="text" class="form-control" id="subject" name="subject" />
                </div>
                <div class="form-group">
                  <label for="comment">내용:</label>
                  <textarea class="form-control" rows="10" id="comment" name="content"></textarea>
                </div>
                <div style="text-align: center">
                  <button id="writeText" type="submit" class="btn btn-primary">글작성</button>
                 
          
                </div>
              </form>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>
      <!--글쓰기 Modal end-->
    </main>
    <!-- End #main -->
    <!-- ======= Footer ======= -->
    <footer class="navbar navbar-expand-md justify-content-end">
      <div class="row">
        <div class="col-md-12">
          <ul class="navbar-nav">
            <li><a class="nav-link text-secondary" href="#">1544-9001</a></li>
            <li><a class="nav-link text-secondary" href="#">개인정보처리방침</a></li>
            <li><a class="nav-link text-secondary" href="#">이용약관</a></li>
            <li><label class="nav-link text-secondary">&copy; SSAFY Corp.</label></li>
          </ul>
        </div>
      </div>
    </footer>
    <!-- End Footer -->
    <a href="#" class="back-to-top"><i class="bx bx-up-arrow-alt"></i></a>
    <div id="preloader"></div>

    <!-- Vendor JS Files -->
    <script src="${root}/vendor/jquery/jquery.min.js"></script>
    <script src="${root}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="${root}/vendor/jquery.easing/jquery.easing.min.js"></script>
    <script src="${root}/vendor/php-email-form/validate.js"></script>
    <script src="${root}/vendor/waypoints/jquery.waypoints.min.js"></script>
    <script src="${root}/vendor/counterup/counterup.min.js"></script>
    <script src="${root}/vendor/owl.carousel/owl.carousel.min.js"></script>
    <script src="${root}/vendor/aos/aos.js"></script>
    <script type="text/javascript" src="${root}/js/board.js"></script>
    <!-- Template Main JS File -->
    <script src="${root}/js/main.js"></script>
  </body>
</html>