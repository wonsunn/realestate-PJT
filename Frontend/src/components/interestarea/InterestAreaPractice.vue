<template>
  <div>
    <nav-sub-header></nav-sub-header>
    <div class="total">
      <div id="intmap" class="left">
        <!-- <div class="searchbar">
          <div class="search-input-area">
            <div class="search-input-liner">
              <div class="input-wrap">
                <input
                  type="text"
                  placeholder="아파트, 법정동으로 검색"
                  id="word"
                  name="word"
                  v-model="word"
                />
                <img
                  id="input-close-button"
                  class="input-close"
                  src="@/assets/img/close-button-icon-black.png"
                />
              </div>
              <div class="btn-search-wrap">
                <button id="btn-search" name="search" class="btn-search" @click="insertArea">
                  추가
                  <img src="@/assets/img/search-icon.png" style="width: 24px" />
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
      </div> -->

        <!-- <div id="mysearchbar" class="input-group md-form form-sm form-2 pl-0">
            <input class="form-control my-0 py-1 lime-border" type="text" placeholder="Search" aria-label="Search">
            <div class="input-group-append">
              <span class="input-group-text lime lighten-2" id="basic-text1">
                <mdbIcon icon="search"/>
              </span>
            </div>
          </div> -->

        <!-- 지도 생성 -->
        <div id="map-section" class="col" style="height: calc(100vh - 120px)">
          <div id="map" style="height: 100%; position: relative; overflow: hidden">
            <!-- 범위 크기 지정 -->
            <b-row id="arrange" @wheel.prevent="wheel">
              <b-col lg="6" aria-controls="overlay-background">
                <b-form-group
                  label="반경 0 ~ 4 km"
                  label-for="bg-opacity"
                  label-cols-sm="4"
                  label-cols-lg="12"
                >
                  <b-input-group>
                    <b-form-input
                      id="bg-opacity"
                      v-model="opacity"
                      type="range"
                      number
                      min="0"
                      max="4"
                      step="0.01"
                    ></b-form-input>
                    <b-input-group-append is-text class="text-monospace">
                      {{ opacity.toFixed(1) }}km
                    </b-input-group-append>
                  </b-input-group>
                </b-form-group>
              </b-col>
            </b-row>
          </div>

          <!-- 실시간 이용시설 표시 -->
          <div class="realTime" v-if="cateGoryFlag">
            <b-button
              v-b-toggle.collapse-1
              variant="primary"
              style="font-size: 15px; padding: 3px; line-height: 1"
            >
              {{ this.opacity.toFixed(1) }}km 이내 {{ this.cateGoryName }}
              {{ this.countInArea }} 개</b-button
            >
            <b-collapse id="collapse-1" class="mt-2">
              <b-card>
                <p class="card-text">Collapse contents Here</p>
                <b-button v-b-toggle.collapse-1-inner size="sm">Toggle Inner Collapse</b-button>
                <b-collapse id="collapse-1-inner" class="mt-2">
                  <b-card>Hello!</b-card>
                </b-collapse>
              </b-card>
            </b-collapse>
          </div>

          <!-- 카테고리 별 검색 -->
          <ul id="category">
            <li id="BK9" data-order="0">
              <span class="category_bg bank"></span>
              은행
            </li>
            <li id="MT1" data-order="1">
              <span class="category_bg mart"></span>
              대형마트
            </li>
            <li id="PM9" data-order="2">
              <span class="category_bg pharmacy"></span>
              약국
            </li>
            <li id="OL7" data-order="3">
              <span class="category_bg oil"></span>
              주유소
            </li>
            <li id="CE7" data-order="4">
              <span class="category_bg cafe"></span>
              카페
            </li>
            <li id="CS2" data-order="5">
              <span class="category_bg store"></span>
              편의점
            </li>
          </ul>
        </div>
      </div>

      <div>
        <b-sidebar id="sidebar-right" title="" right shadow>
          <div class="px-3 py-2">
            <b-alert class="title" show variant="info">
              {{ this.fetchedDongName }}{{ this.fetchedRemoveDong }} 주변</b-alert
            >
            <b-button @click="toast('b-toaster-bottom-left', true)" autohide="false" class="mb-2"
              >가까운 거리 순 보기</b-button
            >

            <b-img :src="imageUrl" fluid thumbnail style="border: 0px"></b-img>
          </div>
        </b-sidebar>
      </div>

      <interest-side class="right"> </interest-side>
      <area-chart></area-chart>
    </div>
  </div>
</template>

<script>
// import $ from 'jquery';
// import 'jquery-ui';
import { mapGetters } from 'vuex';
import { mapActions } from 'vuex';
import http from '@/util/http-common';
import NavSubHeader from '@/components/NavSubHeader.vue';
import InterestSide from '@/components/interestarea/InterestSide.vue';
import AreaChart from '@/components/AreaChart.vue';
// import { mdbIcon } from 'mdbvue';
const storage = window.sessionStorage;
// const storage = window.sessionStorage;
export default {
  name: 'KakaoMap',
  data() {
    return {
      // 원 크기 설정
      variant: 'light',
      opacity: 0.85,
      blur: '2px',
      variants: [
        'transparent',
        'white',
        'light',
        'dark',
        'primary',
        'secondary',
        'success',
        'danger',
        'warning',
        'info',
      ],
      blurs: [{ text: 'None', value: '' }, '1px', '2px', '5px', '0.5em', '1rem'],
      radius: '1000',
      word: '',
      markers: [],
      markersLocation: [],
      currCategory: '', // 현재 선택된 카테고리를 가지고 있을 변수입니다
      cateGoryFlag: false,
      cateGoryName: '',
      placeOverlay: {},
      contentNode: {},
      imageUrl: '',
      ps: {},
      dongname: this.fetchedDongName,
      centerSearchCheck: false,
      circle: '',
      circleY: '',
      circleX: '',
      countInArea: '',
      counter: 60,

      // 현재 카테고리에서 선택된 장소들
      selectedPlaces: [],

      marker: '',

      drawingFlag: false, // 원이 그려지고 있는 상태를 가지고 있을 변수입니다
      centerPosition: '', // 원의 중심좌표 입니다
      drawingCircle: '', // 그려지고 있는 원을 표시할 원 객체입니다
      drawingLine: '', // 그려지고 있는 원의 반지름을 표시할 선 객체입니다
      drawingOverlay: '', // 그려지고 있는 원의 반경을 표시할 커스텀오버레이 입니다
      drawingDot: '', // 그려지고 있는 원의 중심점을 표시할 커스텀오버레이 입니다
      nowCircle: '',

      circles: [],

      // 현재의 원이 관심지역 삭제된 것의 원인지 확인
      wantRemoveDong: this.fetchedRemoveDong,
    };
  },
  components: {
    NavSubHeader,
    InterestSide,
    AreaChart,
    // mdbIcon
  },
  watch: {
    // 마커 위치가 변경될 때
    // marker: function(){
    //   console.log("마커가 위치 변경 시");
    // if(this.currCategory){
    //     this.updatePlaceCnt(this.markers);
    // }
    // },

    currCategory: function (id) {
      if (id == 'BK9') {
        this.cateGoryName = '은행';
        this.imageUrl = require('@/assets/img/bank.jpg');
      } else if (id == 'MT1') {
        this.cateGoryName = '대형마트';
        this.imageUrl = require('@/assets/img/emart.png');
      } else if (id == 'PM9') {
        this.cateGoryName = '약국';
        this.imageUrl = require('@/assets/img/pharmacy.jpg');
      } else if (id == 'OL7') {
        this.cateGoryName = '주유소';
        this.imageUrl = require('@/assets/img/oilbank.jpg');
      } else if (id == 'CE7') {
        this.cateGoryName = '카페';
        this.imageUrl = require('@/assets/img/cafe.png');
      } else if (id == 'CS2') {
        this.cateGoryName = '편의점';
        this.imageUrl = require('@/assets/img/24242424.jpg');
      }
      if (this.circle) {
        console.log('카테고리가 변경되어서 들어올 경우');
        console.log(this.markers);
      }
    },
    markers: function (targets) {
      // 범위 내 장소들 개수 카운팅
      this.updatePlaceCnt(targets);
    },
    // 반경의 크기가 변할 때
    opacity: function (radius) {
      // 원 크기 조절하자
      console.log('원 크기 조절할 때');
      if (this.circle) {
        console.log('원 조절하러 if ㄱㄱ');
        let changeRadius = radius * 1000;
        // 그려지고 있는 원의 중심좌표와 반지름입니다
        if (changeRadius > 4000) {
          changeRadius = 4;
        }
        if (changeRadius < 0) {
          changeRadius = 0;
        }
        this.radius = changeRadius;
        var circleOptions = {
          center: this.centerPosition,
          radius: this.radius,
        };

        // 그려지고 있는 원의 옵션을 설정합니다
        this.circle.setOptions(circleOptions);
        this.circle.setMap(this.fetchedMap);
        // this.fetchedMap.setLevel(5);
        this.fetchedMap.panTo(this.centerPosition);
        console.log(this.circle);
        if (this.currCategory) {
          this.updatePlaceCnt(this.markers);
        }
        // this.opacity = this.radius;
      }
      if (this.opacity > 4) {
        this.opacity = 4;
      } else if (this.opacity < 0) {
        this.opacity = 0;
      }
    },

    dongname: function (dong) {
      console.log(' watch에서 ', dong);
      if (dong == '') {
        return;
      }
      if (this.circle) {
        if (this.marker) {
          this.marker.setMap(null);
        }
        this.circle.setMap(null);
        this.marker.setMap(null);
      }
      this.geocoder.addressSearch(dong, (result, status) => {
        if (status === window.kakao.maps.services.Status.OK) {
          var coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);

          this.circleY = result[0].y;
          this.circleX = result[0].x;
          // this.fetchedMap.setCenter(coords);
          // 지도에 표시할 원을 생성합니다
          this.marker = new kakao.maps.Marker({
            position: coords,
          });
          this.centerPosition = coords;
          this.marker.setMap(this.fetchedMap);
          this.circle = new kakao.maps.Circle({
            center: coords, // 원의 중심좌표 입니다
            radius: this.radius, // 미터 단위의 원의 반지름입니다
            strokeWeight: 1, // 선의 두께입니다
            strokeColor: '#75B8FA', // 선의 색깔입니다
            strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'solid', // 선의 스타일 입니다
            fillColor: '#CFE7FF', // 채우기 색깔입니다
            fillOpacity: 0.2, // 채우기 불투명도 입니다
          });
          // 지도에 원을 표시합니다
          this.circle.setMap(this.fetchedMap);
          this.fetchedMap.panTo(coords);
          if (this.currCategory) {
            this.updatePlaceCnt(this.markers);
          }
        }
      });
    },
  },
  computed: {
    ...mapGetters([
      'fetchedNo',
      'fetchedAptList',
      'fetchedMap',
      'fetchedUserToken',
      'fetchedSessionUser',
      'fetchedInterestAreaList',
      'fetchedDongName',
      'isCenterCheck',
      'fetchedRemoveMarCir',
      'fetchedRemoveDong',
    ]),
  },
  created() {
    this.dongname = this.fetchedDongName;
    this.wantRemoveDong = this.fetchedRemoveDong;
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement('script');
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=bcfb0cc7747cba9a89c33514eff6fd1c&libraries=services,clusterer,drawing';
      document.head.appendChild(script);
    }
  },
  updated() {
    // console.log("업데이트");

    this.dongname = this.fetchedDongName;
    this.wantRemoveDong = this.fetchedRemoveDong;

    if (this.dongname == this.wantRemoveDong) {
      // console.log("두개가 같으니");
      // console.log(this.dongname);
      this.marker.setMap(null);
      this.circle.setMap(null);
      // console.log("여기로 들어오나????????");
      // this.dongname='';
      // this.wantRemoveDong = '';
    }
    // console.log("선택한 동",this.dongname);
    // console.log("제거하고 싶은 동",this.wantRemoveDong);
  },
  methods: {
    ...mapActions([
      'fetchMap',
      'getApartList',
      'getApartDetail',
      'getInterestAreaList',
      'getPlacesInfo',
      'centerDong',
    ]),
    insertArea() {
      if (storage.getItem('jwt-auth-token') == null) {
        alert('로그인이 필요합니다.');
      } else {
        if (this.word == '' || this.word == null) {
          alert('원하는 지역명 입력');
        } else {
          console.log('word   ' + this.word);
          http
            .post('/interest/' + this.fetchedSessionUser.useridx + '/' + this.word)
            .then(({ data }) => {
              console.log(data);
              if (data == 'SUCCESS') {
                alert('관심지역 설정');
                this.getInterestAreaList(this.fetchedSessionUser.useridx);
              } else {
                alert('이미 등록한 지역');
              }
            });
        }
      }
    },

    initMap() {
      var container = document.getElementById('map');
      var initlatlng = new kakao.maps.LatLng(37.566826, 126.9786567);
      var options = {
        center: initlatlng,
        level: 5,
      };
      this.geocoder = new kakao.maps.services.Geocoder();
      this.bounds = new kakao.maps.LatLngBounds();
      this.fetchMap({
        map: new kakao.maps.Map(container, options),
      });

      this.marker = new kakao.maps.Marker({
        // 지도 중심좌표에 마커를 생성합니다
        position: initlatlng,
      });

      this.centerPosition = initlatlng;
      this.circleY = initlatlng.La;
      this.circleX = initlatlng.Ma;

      this.geocoder.coord2RegionCode(initlatlng.La, initlatlng.Ma, (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
          console.log('지역 명칭 : ' + result[0].address_name);
          let fullName = result[0].address_name.split(' ');
          console.log(fullName[2]);
          console.log(result[0]);
          this.centerDong({
            dong: fullName[2],
          });
          console.log('행정구역 코드 : ' + result[0].code);
        }
      });
      // this.circleY = result[0].y;
      // this.circleX = result[0].x;
      // this.fetchedMap.setCenter(coords);
      // 지도에 표시할 원을 생성합니다
      this.marker.setMap(this.fetchedMap);
      this.circle = new kakao.maps.Circle({
        center: initlatlng, // 원의 중심좌표 입니다
        radius: this.radius, // 미터 단위의 원의 반지름입니다
        strokeWeight: 1, // 선의 두께입니다
        strokeColor: '#75B8FA', // 선의 색깔입니다
        strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'solid', // 선의 스타일 입니다
        fillColor: '#CFE7FF', // 채우기 색깔입니다
        fillOpacity: 0.2, // 채우기 불투명도 입니다
      });
      // 지도에 원을 표시합니다
      this.circle.setMap(this.fetchedMap);
      this.fetchedMap.setLevel(5);
      this.fetchedMap.panTo(initlatlng);
      // 지도를 클릭한 위치에 표출할 마커입니다

      // 지도에 마커를 표시합니다
      // marker.setMap(this.fetchedMap);

      // 지도에 클릭 이벤트를 등록합니다
      // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
      kakao.maps.event.addListener(this.fetchedMap, 'click', (mouseEvent) => {
        console.log('클릭한 곳으로 들어오나');
        // 클릭한 위도, 경도 정보를 가져옵니다
        var latlng = mouseEvent.latLng;
        if (this.marker) {
          this.marker.setMap(null);
        }
        if (this.circle) {
          this.circle.setMap(null);
        }
        this.marker = new kakao.maps.Marker({
          // 지도 중심좌표에 마커를 생성합니다
          position: latlng,
        });

        // 마커 위치를 클릭한 위치로 옮깁니다
        this.centerPosition = latlng;
        this.circleY = latlng.La;
        this.circleX = latlng.Ma;

        this.geocoder.coord2RegionCode(latlng.La, latlng.Ma, (result, status) => {
          if (status === kakao.maps.services.Status.OK) {
            console.log('지역 명칭 : ' + result[0].address_name);
            let fullName = result[0].address_name.split(' ');
            console.log(fullName[2]);
            console.log(result[0]);
            this.centerDong({
              dong: fullName[2],
            });
            console.log('행정구역 코드 : ' + result[0].code);
          }
        });

        // this.circleY = result[0].y;
        // this.circleX = result[0].x;
        // this.fetchedMap.setCenter(coords);
        // 지도에 표시할 원을 생성합니다
        this.marker.setMap(this.fetchedMap);
        this.circle = new kakao.maps.Circle({
          center: latlng, // 원의 중심좌표 입니다
          radius: this.radius, // 미터 단위의 원의 반지름입니다
          strokeWeight: 1, // 선의 두께입니다
          strokeColor: '#75B8FA', // 선의 색깔입니다
          strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
          strokeStyle: 'solid', // 선의 스타일 입니다
          fillColor: '#CFE7FF', // 채우기 색깔입니다
          fillOpacity: 0.2, // 채우기 불투명도 입니다
        });
        // 지도에 원을 표시합니다
        this.circle.setMap(this.fetchedMap);
        this.fetchedMap.setLevel(5);
        this.fetchedMap.panTo(latlng);

        if (this.currCategory) {
          this.updatePlaceCnt(this.markers);
        }

        // var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
        // message += '경도는 ' + latlng.getLng() + ' 입니다';

        // var resultDiv = document.getElementById('clickLatlng');
        // resultDiv.innerHTML = message;
      });

      // 관심지역 주변 상권 데이터 start

      // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
      this.placeOverlay = new kakao.maps.CustomOverlay({ Zindex: 1 });
      this.contentNode = document.createElement('div'); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다

      // 장소 검색 객체를 생성합니다
      this.ps = new kakao.maps.services.Places(this.fetchedMap);

      // var map = new kakao.maps.Map(container, options);
      // 지도에 idle 이벤트를 등록합니다

      kakao.maps.event.addListener(this.fetchedMap, 'idle', this.searchPlaces);

      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
      this.contentNode.className = 'placeinfo_wrap';
      // console.log(this.contentNode);
      // console.log(this.contentNode.className);
      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(this.contentNode, 'mousedown', kakao.maps.event.preventMap);
      this.addEventHandle(this.contentNode, 'touchstart', kakao.maps.event.preventMap);

      // 커스텀 오버레이 컨텐츠를 설정합니다
      this.placeOverlay.setContent(this.contentNode);

      // 각 카테고리에 클릭 이벤트를 등록합니다
      this.addCategoryClickEvent();

      // 관심지역 주변 상권 데이터 end
    },

    // getHouseList

    addEventHandle(target, type, callback) {
      // console.log(tagrt);
      // console.log(type);
      // console.log(callback);
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent('on' + type, callback);
      }
    },

    // 카테고리 검색을 요청하는 함수입니다
    searchPlaces() {
      if (!this.currCategory) {
        return;
      }
      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);
      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();
      // console.
      // for(let i=1;i<=2;i++){
      this.ps.categorySearch(this.currCategory, this.placesSearchCB, {
        useMapBounds: true,
        location: new kakao.maps.LatLng(this.circleY, this.circleX),
        // radius: 2000,
        // sort: kakao.maps.services.SortBy.DISTANCE,
        // page: i,
      });
      // }
      // this.ps.categorySearch(
      //     'SC4',
      //     (data, status) => {
      //       if (status === kakao.maps.services.Status.OK) {
      //         this.fetchSchoolList(data);
      //         console.log(data);
      //       }
      //     },
      //     {
      //       location: point.latlng,
      //       radius: 2000,
      //       sort: kakao.maps.services.SortBy.DISTANCE,
      //     }
      //   );
      //   for (let i = 1; i <= 3; i++) {
      //   this.ps.categorySearch(
      //     'SW8',
      //     (data, status) => {
      //       if (status === kakao.maps.services.Status.OK) {
      //         console.log(data);
      //       }
      //     },
      //     {
      //       location: point.latlng,
      //       radius: 1000,
      //       sort: kakao.maps.services.SortBy.DISTANCE,
      //       page: i,
      //     }
      //   );
      //   }
    },
    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.displayPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        console.log('검색결과 없음?????????????????????');
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
        console.log('에러~~~~~~~~~~~');
      }
    },
    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      console.log('카테고리 별 장소들 :', places);
      // console.log("지도 표시 시작");
      // console.log(places);
      // console.log("지도 표시 끝");

      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var order = document.getElementById(this.currCategory).getAttribute('data-order');
      this.selectedPlaces = [];
      for (var i = 0; i < places.length; i++) {
        this.selectedPlaces.push(places[i]);
        // places가 마커들의 정보 객체
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);
        // console.log("카테고리에서 마커들 생성",this.markers);
        // 마커들 위치 정보
        let y = places[i].y;
        let x = places[i].x;
        this.markersLocation.push({ y, x });
        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        ((marker, place) => {
          kakao.maps.event.addListener(marker, 'click', () => {
            this.displayPlaceInfo(place);
          });
        })(marker, places[i]);

        // })(marker, places[i]);
      }
    },
    // makeClickListener(map,marker,infowindow){
    //   return function(){
    //     // this.placeOverlay.setMap(this.fetchedMap);
    //     infowindow.open(map,marker);
    //   }
    // },

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, order) {
      // console.log(position);
      let imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png'; // 마커 이미지 url, 스프라이트 이미지를 씁니다
      let imageSize = new kakao.maps.Size(27, 28); // 마커 이미지의 크기
      let imgOptions = {
        spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
        spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
        offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
      };
      let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
      let marker = new kakao.maps.Marker({
        position: position, // 마커의 위치
        image: markerImage,
      });

      marker.setMap(this.fetchedMap); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
    displayPlaceInfo(place) {
      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        '</a>';

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          '</span>' +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ')</span>';
      } else {
        content += '    <span title="' + place.address_name + '">' + place.address_name + '</span>';
      }

      content +=
        '    <span class="tel">' + place.phone + '</span>' + '</div>' + '<div class="after"></div>';
      this.contentNode.innerHTML = content;
      this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.fetchedMap);
    },

    // 각 카테고리에 클릭 이벤트를 등록합니다
    addCategoryClickEvent() {
      let category = document.getElementById('category');
      let children = category.children;
      for (var i = 0; i < children.length; i++) {
        children[i].onclick = this.onClickCategory;
      }
    },

    // 카테고리를 클릭했을 때 호출되는 함수입니다
    onClickCategory(e) {
      var id = e.currentTarget.id;
      var className = e.currentTarget.className;
      this.placeOverlay.setMap(null);
      if (className === 'on') {
        this.currCategory = '';
        this.changeCategoryClass();
        this.removeMarker();
        this.cateGoryFlag = false;
      } else {
        this.currCategory = id;
        this.changeCategoryClass(e.currentTarget);
        this.searchPlaces();
        this.cateGoryFlag = true;
      }
    },

    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
      this.markersLocation = [];
    },
    // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
    changeCategoryClass(el) {
      var category = document.getElementById('category'),
        children = category.children,
        i;

      for (i = 0; i < children.length; i++) {
        children[i].className = '';
      }

      if (el) {
        el.className = 'on';
      }
    },
    deg2rad(deg) {
      return deg * (Math.PI / 180);
    },
    toast(toaster, append = false) {
      this.counter++;

      this.$bvToast.toast(`Toast ${this.counter} body content`, {
        title: `Toaster ${toaster}`,
        toaster: toaster,
        solid: true,
        appendToast: append,
      });
    },
    wheel: function (ev) {
      if (ev.deltaY < 0) {
        this.opacity += 0.1;
      } else {
        this.opacity -= 0.1;
      }
    },

    updatePlaceCnt(targets) {
      this.countInArea = 0;
      let placesInRadius = [];
      console.log('마커 개수 업데이트하러 왔습니당.');
      console.log('타켓들', targets);
      for (let i = 0; i < targets.length; i++) {
        // 두 점의 위도 경도
        let lat1 = this.circleY,
          lng1 = this.circleX,
          lat2 = this.selectedPlaces[i].y,
          lng2 = this.selectedPlaces[i].x;
        let r = 6371; // 지구 반지름
        let dLat = this.deg2rad(lat2 - lat1);
        let dLon = this.deg2rad(lng2 - lng1);
        let a =
          Math.sin(dLat / 2) * Math.sin(dLat / 2) +
          Math.cos(this.deg2rad(lat1)) *
            Math.cos(this.deg2rad(lat2)) *
            Math.sin(dLon / 2) *
            Math.sin(dLon / 2);
        let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        let d = r * c; // Distance in km
        let dist = Math.round(d * 1000);

        if (dist <= this.radius) {
          this.selectedPlaces[i].distance = dist;
          placesInRadius.push(this.selectedPlaces[i]);

          this.countInArea++;
        }
      }
      this.getPlacesInfo({
        placesInfo: placesInRadius,
      });
    },
    searchSimilarWords(word) {
      document.getElementById('searchResultArea').scrollTop = 0;

      if (word.length < 2) {
        alert('두 글자 이상만 검색됩니다.');
        return;
      }

      http.get('/housemap/search/' + word).then(({ data }) => {
        this.similarwordsapart = [];
        this.similarwordsdong = [];
        if (data.length < 1) {
          alert('검색 내역이 없습니다.');
          return;
        }
        document.getElementById('searchResultArea').style.display = 'block';
        for (let l of data) {
          if (l.isApart) this.similarwordsapart.push(l);
          else this.similarwordsdong.push(l);
        }
      });
    },
  },
};
</script>

<style>
/* @import '../assets/css/custom.css'; */

div.total {
  width: 100%;
}
div.left {
  width: 70%;
  float: left;
}
div.right {
  width: 30%;
  float: right;
  box-sizing: border-box;
}
#intmap,
#intmap * {
  margin: 0;
  padding: 0;
  font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
  font-size: 12px;
}
#intmap {
  position: relative;
  width: 70%;
  height: 350px;
}
#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: '';
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png');
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
.info {
  position: relative;
  top: 5px;
  left: 5px;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  font-size: 12px;
  padding: 5px;
  background: #fff;
  list-style: none;
  margin: 0;
}
.info:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .label {
  display: inline-block;
  width: 50px;
}
.number {
  font-weight: bold;
  color: #00a0e9;
}
#sidebar-right {
  background-color: #ededed;
}
.title {
  position: absolute;
  top: 0px;
  right: 0px;
  width: 320px;
}
.placeCnt {
  position: absolute;
  top: 375px;
  right: 1px;
  width: 319px;
}
b-img {
  position: absolute;
  top: 59px;
  width: 316px;
  right: 1px;
  border: 0px;
}
#arrange {
  z-index: 10;
  position: absolute;
  top: 754px;
  right: -236px;
  width: 500px;
}
#map {
  z-index: 1;
  position: absolute;
}
.realTime {
  font-size: 40px;
  z-index: 10;
  position: absolute;
  top: 19px;
  right: 10px;
  width: 163px;
  height: 68px;
}
#insbar {
  height: 31px;
}
#mysearchbar {
  position: absolute;
  z-index: 10;
  width: 333px;
  height: 48px;
  top: 43px;
  left: 517px;
}
.input-group.md-form.form-sm.form-1 input {
  border: 1px solid #bdbdbd;
  border-top-right-radius: 0.25rem;
  border-bottom-right-radius: 0.25rem;
}
.input-group.md-form.form-sm.form-2 input {
  border: 1px solid #bdbdbd;
  border-top-left-radius: 0.25rem;
  border-bottom-left-radius: 0.25rem;
}
.input-group.md-form.form-sm.form-2 input.red-border {
  border: 1px solid #ef9a9a;
}
.input-group.md-form.form-sm.form-2 input.lime-border {
  border: 1px solid #cddc39;
}
.input-group.md-form.form-sm.form-2 input.amber-border {
  border: 1px solid #ffca28;
}
</style>
