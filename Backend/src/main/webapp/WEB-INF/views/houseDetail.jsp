<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/top.jsp" %>

    <!-- Favicons -->
    <style>
    	.apartDetail {
    		padding: 100px 0;
    	}
    	.sub-content {
    		color:#2D60A3;
    		font-size:25px;
    		font-weight:bold;
    	}
    	.price-table > thead > tr > th {
    		position:sticky;
    		top:0;
    		background-color: lightgrey !important;
    	}
    </style>
    
    <!-- ======= About Section ======= -->
      <section id="apartDetail" class="apartDetail">
        <div class="container">
          <div class="apart-title mb-5">
          	<h3>아파트&nbsp;·&nbsp;<b>${name}</b></h3>
          </div>
          
          <div class="section-title">
            <h2>PRICE</h2>
            <p>시세 정보</p>
          </div>
		  <div class="sub-title mb-5">
          		<div class="sub-content" style="float:left">매매 ${minPrice} ~ ${maxPrice}</div>
          		<div class="sub-content" style="float:right">준공년도 : ${buildYear}년</div>
          </div>
          <div class="mb-5" style="width:100%; height:300px; overflow:auto">
          	
	        <table class="table mb-3 price-table">
	        	<thead>
	            	<tr style="background-color: lightgrey;">
	            		<th>번호</th>
	              		<th>계약일</th>
	              		<th>거래가격</th>
	              		<th>전용면적</th>
	              		<th>층수</th>
	            	</tr>
	          	</thead>
	          	<tbody>
	          		<c:forEach var="house" items="${houses}" varStatus="status">
	          			<tr>
	          				<th>${status.index + 1}</th>
	          				<th>${house.dealYear}년 ${house.dealMonth}월 ${house.dealDay}일</th>
	          				<th>${house.dealAmount2}</th>
	          				<th>${house.area}㎡</th>
	          				<th>${house.floor}층</th>
	          			</tr>
	          		</c:forEach>
	          	</tbody>
	          </table>
	       </div>
	       
	       <div class="section-title">
            	<h2>ENVIRONMENT</h2>
            	<p>환경 정보</p>
           </div>
           
           <script>
           	$(document).ready(function(){
           		$.ajax({
           			url: 'assets/en.json',
           			type: 'GET',
           			dataType: 'json',
           			success: function (data) {
           				$.each(data, function(idx, item) {
           					let msrste_nm = item.msrste_nm;
           					let infoStr = ``;
           					
           					if (msrste_nm == '${gugun}') {
           						infoStr += `<table class="table table-bordered" style="text-align-center">
           								<tbody>
        	        	            		<tr>
        	        	              			<td style="width: 200px; background-color: lightgrey; font-weight:bold">측정일자</td>
        	        	              			<td>` + data[idx].msrdt_de + `</td>              
        	        	            		</tr>
        	        	            		<tr>
        	        	              			<td style="background-color: lightgrey; font-weight:bold">일산화탄소농도(ppm)</td>
        	        	              			<td>` + data[idx].co + `</td>
        	        	            		</tr>
        	        	            		<tr>
        	        	              			<td style="background-color: lightgrey; font-weight:bold">이산화질소농도(ppm)</td>
        	        	              			<td>` + data[idx].no2 + `</td>
        	        	            		</tr>
        	        	            		<tr>
        	        	              			<td style="background-color: lightgrey; font-weight:bold">오존(ppm)</td>
        	        	              			<td>` + data[idx].o3 + `</td>
        	        	            		</tr>
        	        	            		<tr>
        	        	              			<td style="background-color: lightgrey; font-weight:bold">미세먼지(㎍/㎥)</td>
        	        	              			<td>` + data[idx].pm10 + `</td>
        	        	            		</tr>
        	        	            		<tr>
        	        	              			<td style="background-color: lightgrey; font-weight:bold">초미세먼지(㎍/㎥)</td>
        	        	              			<td>` + data[idx].pm25 + `</td>
        	        	            		</tr>
        	        	          		</tbody>
        	        	          	</table>`;
        	        	    $('#environmentTable').append(infoStr);
           					}
           				});
           			}
           		});
           	});
           </script>
           
          <div class="" id="environmentTable"></div>
	       
	       
        </div>
      </section>
      
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
    
    <!-- Vendor JS Files -->
    <script src="assets/vendor/php-email-form/validate.js"></script>
    <script src="assets/vendor/waypoints/jquery.waypoints.min.js"></script>
    <script src="assets/vendor/counterup/counterup.min.js"></script>
    <script src="assets/vendor/owl.carousel/owl.carousel.min.js"></script>
    <script src="assets/vendor/aos/aos.js"></script>
    
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=bcfb0cc7747cba9a89c33514eff6fd1c&libraries=services"></script>
    

</body>
</html>