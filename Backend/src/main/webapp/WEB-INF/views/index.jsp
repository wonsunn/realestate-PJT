<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/top.jsp"%>
<!-- End Header -->

<div id="main" style="position: relative;">

	<!-- ======= Popular Courses Section ======= -->
	<div class="searchbar">
		<div class="search-input-area">
			<div class="search-input-liner">
				<div class="input-wrap">
					<input type="text" placeholder="아파트, 법정동으로 검색" id="word"
						name="word" value="">
						<img id="input-close-button" class="input-close" src="${root}/img/close-button-icon-black.png">
				</div>
				<div class="btn-search-wrap">
					<button id="btn-search" name="search" class="btn-search">
						<img src="${root}/img/search-icon.png" style="width: 24px;">
					</button>
				</div>
			</div>
		</div>
		<div class="search-filter-area">
			<div class="filter-text-wrap">
				<div id="filter_all_selected" class="filter-text">검색 조건을 설정해 주세요.</div>
			</div>
			<div class="filter-button">
				<div id="filter-button" class="filter-button-text">필터</div>
			</div>
		</div>
		<div id="filter-entire-area" class="filter-contents-wrap" style="display:none;">
			<div class="filter-contents-header" style="height:50px;">
				<img id="filter-close-button" class="filter-contents-close" src="${root}/img/close-button-icon-black.png">
				<div class="filter-contents-title">필터</div>
			</div>
			<div class="filter-contents-area">
				<div id="filter1" class="filter-contents">
					<div class="filter-title">면적</div>
					<div id="filter1_selected" class="filter-selected">전체</div>
					
					<div class="filter-row" style="width:100%">
						<div id="filter1_1" class="filter-col filter-sel" onclick="selectOpt(1);">전체</div>
						<div id="filter1_2" class="filter-col" style="border-left-style:none" onclick="selectOpt(2);">10평 이하</div>
						<div id="filter1_3" class="filter-col" style="border-left-style:none" onclick="selectOpt(3);">10평대</div>
						<div id="filter1_4" class="filter-col" style="border-left-style:none" onclick="selectOpt(4);">20평대</div>
					</div>
					<div class="filter-row" style="width:100%;margin-top:0">
						<div id="filter1_5" class="filter-col" style="border-top-style:none" onclick="selectOpt(5);">30평대</div>
						<div id="filter1_6" class="filter-col" style="border-left-style:none;border-top-style:none" onclick="selectOpt(6);">40평대</div>
						<div id="filter1_7" class="filter-col" style="border-left-style:none;border-top-style:none" onclick="selectOpt(7);">50평대</div>
						<div id="filter1_8" class="filter-col" style="border-left-style:none;border-top-style:none" onclick="selectOpt(8);">60평 이상</div>
					</div>
				</div>
				<div id="filter2" class="filter-contents" style="margin-bottom:0">
					<div class="filter-title">입주년차</div>
					<div class="filter-selected">전체</div>
					<div class="filter-row" style="width:100%">
						<div id="filter2_1" class="filter-col filter-sel">전체</div>
						<div id="filter2_2" class="filter-col" style="border-left-style:none">5년 이내</div>
						<div id="filter2_3" class="filter-col" style="border-left-style:none">10년 이내</div>
						<div id="filter2_4" class="filter-col" style="border-left-style:none">15년 이내</div>
					</div>
					<div class="filter-row" style="width:100%;margin-top:0">
						<div id="filter2_5" class="filter-col" style="border-top-style:none">20년 이내</div>
						<div id="filter2_6" class="filter-col" style="border-left-style:none;border-top-style:none">25년 이내</div>
						<div id="filter2_7" class="filter-col" style="border-left-style:none;border-top-style:none">30년 이내</div>
						<div id="filter2_8" class="filter-col" style="border-left-style:none;border-top-style:none">35년 이내</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>

	<div id="popular" class="courses" style="padding: 0;">
		<div class="">
			<div class="row">
				<div id="map-section" class="col" style="padding: 0; height: 870px;">
					<div id="map" style="width: 100%; height: 100%;"></div>
				</div>
				<div class="apart-detail w3-sidebar w3-bar-block w3-card w3-animate-right"
					style="display: none; right: 0;width:410px; padding-bottom:13%;" id="apartDetailInfo">
				</div>
				<div class="w3-sidebar w3-bar-block w3-card w3-animate-left"
					style="display: none; left: 0; width:400px; z-index:2; padding-bottom:13%;" id="likeHouseInfo">
				</div>
					<!--  <button onclick="closeRightMenu()"
						class="apart-detail-close-btn w3-bar-item w3-button w3-large" style="">&times;</button>-->
					<!--<div class="apart-detail-header-area">
						<div class="apart-detail-header">
							<img src="assets/img/close-button-icon.png"  class="apart-detail-close-btn" onclick="hideApartDetailInfo()">
							<div class="apart-detail-header-text">여의도 자이아파트</div>
							<div class="apart-detail-header-address">서울 영등포구 여의도동 343</div>
						</div>
					</div>
					<div class="apart-detail-result">
						<img src="assets/img/house1.jpg" class="img-fluid mt-3" >		
						<div class="result-buildyear">2008년 입주(20년차)</div>
						<div class="result-price-area mt-3">
							<div class="price-area-title">시세 정보</div>
							<div class="price-area-price-title">매매 10억 2,000만
							~ 20억 3000만</div>
							<div class="price-area-contents">
								<table class="table mb-3 price-table" style="border:0">
									<thead>
										<tr style="background-color: #f5f5f5;">
											<th>계약일</th>
											<th>거래가격</th>
											<th>전용면적</th>
											<th>층수</th>
										</tr>
									</thead>
									<tbody>
										<tr>
					          				<th>2011년 1월 3일</th>
					          				<th>4억 3,000만</th>
					          				<th>20㎡</th>
					          				<th>3층</th>
	          							</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>-->
				
			</div>
		</div>



		<!-- End Popular Courses Section -->

	</div>



	<!-- End #main -->
	<!-- ======= Footer ======= -->
	<!--<footer class="navbar navbar-expand-md justify-content-end">
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
    </footer>-->
	<!-- End Footer -->
	<a href="#" class="back-to-top"><i class="bx bx-up-arrow-alt"></i></a>
	<div id="preloader"></div>

	<!-- Vendor JS Files -->
	<script src="${root}/vendor/php-email-form/validate.js"></script>
	<script src="${root}/vendor/waypoints/jquery.waypoints.min.js"></script>
	<script src="${root}/vendor/counterup/counterup.min.js"></script>
	<script src="${root}/vendor/owl.carousel/owl.carousel.min.js"></script>
	<script src="${root}/vendor/aos/aos.js"></script>

	<!-- Template Main JS File -->
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=bcfb0cc7747cba9a89c33514eff6fd1c&libraries=services"></script>
	<!-- Template Main JS File -->
	<script src="${root}/js/main.js"></script>

	<script type="text/javascript">
		var contextPath = '${pageContext.request.contextPath}';
		var sessionUser = '${userinfo}';
		$(document).ready(function() {
			console.log(sessionUser);
			$("#main").css("height", window.innerHeight - 120);

			$("#map-section").css('height', window.innerHeight - 120);
			$("#apart-section").css('height', window.innerHeight - 120);
			
			$("#input-close-button").click(function() {
				$("#word").val('');
			});
			
			$("#filter-button").click(function() {
				if ($("#filter-entire-area").css("display") == 'none') {
					$("#filter-entire-area").css("display", "");
				} else {
					$("#filter-entire-area").css("display", "none");
				}
			});
			$("#filter-close-button").click(function() {
				$("#filter-entire-area").css("display", "none");
			});
			
		});
		var selIdx = 1;
		var multiselected = false;
		
		function selectOpt(idx) {
			if (multiselected && idx < selIdx) {
				for (var i = 1; i <= 8; i++) {
					$("#filter1_"+i).removeClass('filter-sel');
				}
				$("#filter1_"+idx).addClass('filter-sel');
			}
			
			if (selIdx == 1) {
				$("#filter1_"+selIdx).removeClass('filter-sel');
				$("#filter1_"+idx).addClass('filter-sel');
				
				$("#filter_all_selected").text($("#filter1_"+idx).text());
				$("#filter1_selected").text($("#filter1_"+idx).text());
				multiselected = false;
			} 
			else {
				if (idx > 1 && idx < selIdx) {
					$("#filter1_"+selIdx).removeClass('filter-sel');
					$("#filter1_"+idx).addClass('filter-sel');
					
					$("#filter_all_selected").text($("#filter1_"+idx).text());
					$("#filter1_selected").text($("#filter1_"+idx).text());
					multiselected = false;
				}
				else if (idx > selIdx) {
					for (var i = selIdx + 1; i <= idx; i++) {
						$("#filter1_"+i).addClass('filter-sel');
					}
					
					if (idx != 8) {
						if (multiselected) {
							$("#filter_all_selected").text($("#filter1_selected").text().substring(0,4) + " ~ " + $("#filter1_"+idx).text());
							$("#filter1_selected").text($("#filter1_selected").text().substring(0,4) + " ~ " + $("#filter1_"+idx).text());
							
						}
						else {
							$("#filter_all_selected").append(" ~ " + $("#filter1_"+idx).text());
							$("#filter1_selected").append(" ~ " + $("#filter1_"+idx).text());
					
						}	
					}
					else {
						$("#filter_all_selected").text($("#filter1_selected").text().substring(0,4) + " ~ ");
						$("#filter1_selected").text($("#filter1_selected").text().substring(0,4) + " ~ "); 
					}
					
					multiselected = true;
					
				}
			}
			
			selIdx = idx;
		}
		
		function showApartDetailInfo() {
			document.getElementById("apartDetailInfo").style.display = "block";
			$("#map-section").css("padding-right", "160px");
		} 

		function hideApartDetailInfo() {
			$("#map-section").css("padding-right", "0px");
			document.getElementById("apartDetailInfo").style.display = "none";
		}
		
		function clickLikeList() {
			if (sessionUser == '' || sessionUser == null) {
				alert('로그인이 필요합니다.');
				return;
			}
			if ($("#likeHouseInfo").css("display") == 'block')
				hideLikeHouseInfo();
			else
				showLikeHouseInfo();
		}
		
		function showLikeHouseInfo() {
			$("#likeHouseInfo").css("display", "block");
		} 
		
		function hideLikeHouseInfo() {
			$("#likeHouseInfo").css("display", "none");
			//$("#map-section").css("padding-right", "160px");
		} 
	</script>
	<script src="${root}/js/housemap.js"></script>
	<script src="${root}/js/housewish.js"></script>

	<script>
		function login() {
			var id = document.getElementById('LoginId').value;

			document.querySelector('#login1').style.display = 'none';
			document.querySelector('#login2').style.display = 'none';
			document.querySelector('#interest1').style.display = '';

			document.querySelector('#welcome').style.display = '';
			document.querySelector('#logoutbtn').style.display = '';
			document.querySelector('#infobtn').style.display = '';
			document.querySelector('#welcome').innerHTML = '[' + id + '님 ]';
		}

		function logout() {
			document.querySelector('#login1').style.display = '';
			document.querySelector('#login2').style.display = '';
			document.querySelector('#interest1').style.display = 'none';

			document.querySelector('#welcome').style.display = 'none';
			document.querySelector('#logoutbtn').style.display = 'none';
			document.querySelector('#infobtn').style.display = 'none';
		}
	</script>
	</body>
	</html>