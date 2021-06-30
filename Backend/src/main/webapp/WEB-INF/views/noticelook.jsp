<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/top.jsp" %>

    <!-- ======= Hero Section ======= -->
    <section id="hero" class="d-flex justify-content-center align-items-center">
      <div class="container position-relative" data-aos="zoom-in" data-aos-delay="100">
        <h2>Happy House</h2>
      </div>
    </section>
    <!-- End Hero -->

    <main id="main">
      <!-- ======= About Section ======= -->
      <section id="about" class="about">
        <div class="container" data-aos="fade-up">
         
                <div style="height: 60px"></div>
          <c:if test="${content.userid eq userinfo.userid}">
                <div class="button">
                  <button
                    id="modifyContentBtn"
                    type="button"
                    class="btn btn-warning"
                    data-toggle="modal"
                    data-target="#writeNotice"
                  >
                    수정하기   </button>
                
                  <a id="deleteContentBtn" type="button" class="btn btn-danger" href="${root}/notice/delete?articleno=${content.articleno}">삭제</a>
                </div>
          </c:if>
                <table class="table table-bordered mt-3">
                  <thead>
                    <tr style="text-align: center">
                      <th>제목</th>
                      <td colspan="5">${content.subject }</td>
                    </tr>
                  </thead>
                  <tbody>
                    <tr style="text-align: center">
                      <th>작성자</th>
                      <td>${content.userid }</td>
                      <th>조회수</th>
                      <td>${content.views }</td>
                      <th>작성일</th>
                      <td>${content.regtime }</td>
                    </tr>
                    <tr>
                      <td colspan="6" style="height: 300px">${content.content }</td>
                    </tr>
                  </tbody>
                </table>
          
                <div class="button" style="text-align: center">
                  <a
                    id="newBtn"
                    type="button"
                    class="btn btn-info"
                   href="${root}/notice?act=getlist"
                  >
                    목록
                  </a>

                </div>
           <div class="row">
                  <div class="mt-3 col-md-10">
                    <textarea
                      class="form-control"
                      rows="5"
                      id="comment"
                      placeholder="주제와 무관한 댓글, 악플은 삭제될 수 있습니다."
                    ></textarea>
                  </div>
                  <div class="mt-3 col-md-2">
                    <button
                      id="commentBtn"
                      type="button"
                      class="btn btn-primary btn-block"
                      style="height: 100%"
                    >작성
                    </button>
                  </div>
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
                      <h4 class="modal-title">공지사항 수정</h4>
                      <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
        
                    <!-- Modal body -->
                    <div class="modal-body">
                      <form action="${root}/notice/modify">
                        <div class="form-group">

                        <input type="hidden" class="form-control" name="articleno" value="${content.articleno}"/>
                      
                          <label for="title">제목:</label>
                          <input type="text" class="form-control" id="title" name="subject" value="${content.subject }"/>
                        </div>
                        <div class="form-group">
                          <label for="comment">내용:</label>
                          <textarea class="form-control" rows="10" id="comment" name="content" >${content.content }</textarea>
                        </div>
                        <div style="text-align: center">
                          <button id="writeText" type="submit" class="btn btn-primary">글수정</button>
                          
                          <a
                             
                            class="btn btn-secondary"
                          href="${root}/notice/list"
                          >
                          
                               목록
                          </a>
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