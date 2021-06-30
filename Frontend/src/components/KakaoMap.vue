<template>
  <div id="main" style="position: relative; overflow: hidden">
    <nav-sub-header></nav-sub-header>
    <div class="searchbar">
      <div class="search-input-area">
        <div class="search-input-liner">
          <div class="input-wrap">
            <input
              type="text"
              placeholder="아파트, 법정동으로 검색"
              id="word"
              name="word"
              v-model="word"
              @keypress.enter="searchSimilarWords(word)"
            />
            <img
              id="input-close-button"
              class="input-close"
              src="../assets/img/close-button-icon-black.png"
              @click="resetInputArea"
            />
          </div>
          <div class="btn-search-wrap">
            <button id="btn-search" name="search" class="btn-search" @click="searchHouseFromBtn">
              <img src="../assets/img/search-icon.png" style="width: 24px" />
            </button>
          </div>
        </div>
      </div>
      <div class="search-result-area" id="searchResultArea">
        <div class="search-result-inner dong-inner">
          <p class="search-result-title">지역</p>
          <b-list-group>
            <b-list-group-item
              v-for="(item, index) in similarwordsdong"
              :key="'wish' + index"
              class="border-0"
              @click="searchHouseFromDong(item.main_data)"
            >
              <div style="font-size: 15px">{{ item.main_data }}</div>
              <span style="font-size: 13px">{{ item.sub_data }}</span>
            </b-list-group-item>
          </b-list-group>
        </div>
        <div class="search-result-inner apart-inner">
          <p class="search-result-title">아파트</p>
          <b-list-group>
            <b-list-group-item
              v-for="(item, index) in similarwordsapart"
              :key="'wish' + index"
              class="border-0"
              @click="searchHouseDetail(item.sub_data + ' ' + item.jibun, item.no)"
            >
              <div style="font-size: 15px">{{ item.main_data }}</div>
              <span style="font-size: 13px">{{ item.sub_data }}</span>
            </b-list-group-item>
          </b-list-group>
        </div>
      </div>
      <div class="search-filter-area">
        <div class="filter-text-wrap">
          <div id="filter_all_selected" class="filter-text">검색 조건을 설정해 주세요.</div>
        </div>
        <div class="filter-button">
          <div id="filter-button" class="filter-button-text" @click="togglefilter">필터</div>
        </div>
      </div>
      <div id="filter-entire-area" class="filter-contents-wrap" style="display: none">
        <div class="filter-contents-header" style="height: 50px">
          <img
            id="filter-close-button"
            class="filter-contents-close"
            src="@/assets/img/close-button-icon-black.png"
            @click="closefilter"
          />

          <div class="filter-contents-title">필터</div>
          <div class="filter-contents-reset" @click="resetFilter">모두 초기화</div>
        </div>
        <div class="filter-contents-area">
          <div id="filterYear" class="filter-contents">
            <div class="filter-title">입주년차</div>
            <div v-if="filterParams.yearVal == 30" class="filter-selected">전체</div>
            <div v-else class="filter-selected">입주 {{ filterParams.yearVal }}년 이내</div>
            <vue-slider
              v-model="filterParams.yearVal"
              v-bind="options"
              :min="0"
              :max="30"
              :marks="yearMarks"
              @drag-end="dragEnd"
            ></vue-slider>
          </div>
          <div id="filterPrice" class="filter-contents">
            <div class="filter-title">매매가</div>
            <div v-if="filterParams.priceVal[1] != 40" class="filter-selected">
              {{ filterParams.priceVal[0] }}억 ~ {{ filterParams.priceVal[1] }}억
            </div>
            <div v-else class="filter-selected">{{ filterParams.priceVal[0] }}억 ~ 무제한</div>
            <vue-slider
              v-model="filterParams.priceVal"
              v-bind="options"
              :min="priceMin"
              :max="priceMax"
              :marks="priceMarks"
              @drag-end="dragEnd"
            ></vue-slider>
          </div>
          <div id="filterArea" class="filter-contents">
            <div class="filter-title">면적</div>
            <div v-if="filterParams.areaVal[1] != 200" class="filter-selected">
              {{ filterParams.areaVal[0] }}m²({{ Math.round(filterParams.areaVal[0] * 0.3025) }}평)
              ~ {{ filterParams.areaVal[1] }}m²({{
                Math.round(filterParams.areaVal[1] * 0.3025)
              }}평)
            </div>

            <div v-else class="filter-selected">
              {{ filterParams.areaVal[0] }}m²({{ Math.round(filterParams.areaVal[0] * 0.3025) }}평)
              ~ 무제한
            </div>
            <vue-slider
              v-model="filterParams.areaVal"
              v-bind="options"
              :min="areaMin"
              :max="areaMax"
              :marks="areaMarks"
              @drag-end="dragEnd"
            ></vue-slider>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div id="map-section" class="col" style="height: calc(100vh - 120px)">
        <div id="map" style="width: 100%; height: 100%"></div>
      </div>
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
import 'vue-slider-component/theme/antd.css';
import VueSlider from 'vue-slider-component';

export default {
  name: 'KakaoMap',
  data() {
    return {
      word: '',
      similarwordsapart: [],
      similarwordsdong: [],
      markers: [],
      points: [],
      bounds: '',
      moveLa: '',
      moveLo: '',
      customOverlays: [],
      apartIdx: '',
      filterParams: {
        yearVal: 30,
        priceVal: [0, 40],
        areaVal: [0, 200],
      },
      yearMarks: {
        0: 0,
        10: '10년',
        20: '20년',
        30: '30년',
      },
      priceMin: 0,
      priceMax: 40,
      priceMarks: {
        0: 0,
        20: '20억',
        40: '무제한',
      },
      areaMin: 0,
      areaMax: 200,
      areaMarks: {
        0: 0,
        100: '100m²(' + Math.round(100 * 0.3025) + '평)',
        200: '무제한',
      },
      options: {
        dotSize: 15,
        width: 310,
        height: 6,
        interval: 1,
        duration: 0.5,
        enableCross: false,
        tooltip: 'none',
        clickable: false,
      },
    };
  },
  watch: {
    word: function (newVal) {
      if (newVal == '') {
        document.getElementById('searchResultArea').style.display = 'none';
      }
    },
  },
  components: {
    NavSubHeader,
    VueSlider,
  },
  computed: {
    ...mapGetters(['fetchedAptList', 'fetchedMap', 'fetchedSessionUser']),
  },
  created() {
    // this.min = 500;
    // this.max = 1000;
    // this.enableCross = false;
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
    // this.initMap();
  },
  methods: {
    ...mapActions([
      'fetchMap',
      'getApartList',
      'getApartDetail',
      'getApartListFromLoc',
      'fetchSchoolList',
      'fetchSubwayList',
    ]),

    searchHouse() {
      this.getApartListFromLoc({
        bounds: this.fetchedMap.getBounds(),
        params: this.filterParams,
      });
      this.getHouseGeocoder();
    },
    initMap() {
      var container = document.getElementById('map');
      var options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 4,
      };

      this.fetchMap({
        map: new kakao.maps.Map(container, options),
      });
      this.geocoder = new kakao.maps.services.Geocoder();
      this.bounds = new kakao.maps.LatLngBounds();
      this.ps = new kakao.maps.services.Places(this.fetchedMap);

      this.fetchedMap.setMaxLevel(4); // 줌 축소 막기

      // 처음 뜨는 화면
      setTimeout(() => {
        this.searchHouse();
      }, 300);

      // 지도 드래그 끝날 때 현재 지도의 범위 내에 있는 매물 가져오기
      kakao.maps.event.addListener(this.fetchedMap, 'dragend', () => {
        this.searchHouse();
      });

      // 지도의 줌을 바꿀 때 지도 범위 내에 있는 매물 가져오기
      kakao.maps.event.addListener(this.fetchedMap, 'zoom_changed', () => {
        this.searchHouse();
      });

      // 지도 드래그하면 아파트 상세 사이드바 다시 들어가게 하기
      kakao.maps.event.addListener(this.fetchedMap, 'dragstart', () => {
        document.getElementById('detailbar').style.display = 'none';
        document.getElementById('searchResultArea').style.display = 'none';
        this.apartIdx = '';
      });
    },
    // 필터 초기화
    resetFilter() {
      this.filterParams.yearVal = 30;
      this.filterParams.priceVal = [0, 40];
      this.filterParams.areaVal = [0, 200];
      this.dragEnd();
    },
    // 필터 조정하면 수행되는 함수
    dragEnd() {
      this.searchHouse();
      document.getElementById('detailbar').style.display = 'none';
    },
    togglefilter() {
      let filterDisplay = document.getElementById('filter-entire-area').style.display;
      if (filterDisplay == 'none') {
        document.getElementById('filter-entire-area').style.display = 'block';
        this.show = true;
      } else document.getElementById('filter-entire-area').style.display = 'none';
    },
    closefilter() {
      document.getElementById('filter-entire-area').style.display = 'none';
    },
    // 검색한 단어가 포함된 동, 아파트 이름 뿌려주기
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
    resetInputArea() {
      this.word = '';
    },
    // 동으로 검색할 때
    searchHouseFromDong(dong) {
      this.geocoder.addressSearch(dong, (result, status) => {
        if (status === window.kakao.maps.services.Status.OK) {
          var coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
          this.fetchedMap.setCenter(coords);
        }
      });

      setTimeout(() => {
        this.searchHouse();
      }, 100);
    },
    // 아파트명으로 검색할 때
    searchHouseDetail(aptaddr, apartno) {
      this.apartIdx = apartno;
      this.searchHouseFromDong(aptaddr);

      setTimeout(() => {
        this.getApartDetail({
          no: apartno,
          addrName: aptaddr,
          userIdx: this.fetchedSessionUser == null ? '' : this.fetchedSessionUser.useridx,
        });
        document.getElementById('detailbar').style.display = 'flex';
      }, 500);
    },
    // 검색 버튼 눌렀을 때
    searchHouseFromBtn() {
      this.searchHouse();
    },
    getHouseGeocoder() {
      this.points = [];

      setTimeout(() => {
        console.log(this.fetchedAptList.length);
        if (this.fetchedAptList.length == 0) {
          if (typeof this.markers != 'undefined') {
            // console.log('markers length ' + this.markers.length);
            this.markers.forEach((marker) => {
              marker.setMap(null);
            });
            this.markers = [];
          }
          if (typeof this.customOverlays != 'undefined') {
            this.customOverlays.forEach((overlay) => {
              overlay.setMap(null);
            });
            this.customOverlays = [];
          }
          alert('등록된 매물이 없습니다.');
          return;
        }

        let total = this.fetchedAptList.length;
        let counter = 0;
        this.fetchedAptList.forEach((apt) => {
          this.geocoder.addressSearch(`${apt.dong} ${apt.jibun}`, (result, status) => {
            if (status === kakao.maps.services.Status.OK) {
              let info = {};
              info.content = apt.aptName;
              info.no = apt.no;
              info.addrName = result[0].address_name;
              info.latlng = new kakao.maps.LatLng(result[0].y, result[0].x);

              this.points.push(info);
            }
            counter++;
            // 모든 아파트 매물의 주소를 얻었다면, 마커 찍으러 가기
            if (counter == total) {
              this.getMarkers();
            }
          });
        });
      }, 100);
    },
    getMarkers() {
      if (typeof this.markers != 'undefined') {
        // console.log('markers length ' + this.markers.length);
        this.markers.forEach((marker) => {
          marker.setMap(null);
        });
        this.markers = [];
      }
      if (typeof this.customOverlays != 'undefined') {
        this.customOverlays.forEach((overlay) => {
          overlay.setMap(null);
        });
        this.customOverlays = [];
      }

      // this.bounds = new kakao.maps.LatLngBounds();
      this.points.forEach((point, index) => {
        this.addMarkers(point, index); // 마커 추가
        this.addCustomOverlays(point); // 오버레이 추가
      });

      // this.setBounds();
    },
    panTo() {
      var moveLatLon = new kakao.maps.LatLng(this.moveLa, this.moveLo);
      this.fetchedMap.panTo(moveLatLon);
    },
    addMarkers(point, idx) {
      let map = this.fetchedMap;
      let unselMarkerImg = new kakao.maps.MarkerImage(
        require('@/assets/img/unSelMarker.png'),
        new kakao.maps.Size(32, 29),
        { offset: new kakao.maps.Point(27, 69) }
      );
      let selMarkerImg = new kakao.maps.MarkerImage(
        require('@/assets/img/selMarker.png'),
        new kakao.maps.Size(35, 43),
        { offset: new kakao.maps.Point(27, 77) }
      );
      let marker = new kakao.maps.Marker({
        map: map,
        position: point.latlng,
        image: unselMarkerImg,
      });

      // 검색한 아파트 마커이미지 처리
      if (point.no == this.apartIdx) {
        marker.setImage(selMarkerImg);
        this.selMarker = idx;
      }

      kakao.maps.event.addListener(marker, 'click', () => {
        this.moveLa = point.latlng.Ma;
        this.moveLo = point.latlng.La;

        // 현재 선택된 마커 제외한 다른 마커들 원래 이미지 처리
        this.markers.forEach((m) => {
          m.setImage(unselMarkerImg);
        });
        // 현재 선택된 마커 이미지 처리
        marker.setImage(selMarkerImg);

        this.panTo(); // 마커 이동
        console.log(point.no + ' ' + point.addrName);

        // 아파트 상세정보 띄우기
        this.getApartDetail({
          no: point.no,
          addrName: point.addrName,
          userIdx: this.fetchedSessionUser == null ? '' : this.fetchedSessionUser.useridx,
        });
        document.getElementById('detailbar').style.display = 'flex';
      });

      // 학군정보 가져오기
      this.ps.categorySearch(
        'SC4',
        (data, status) => {
          if (status === kakao.maps.services.Status.OK) {
            this.fetchSchoolList(data);
          }
        },
        {
          location: point.latlng,
          radius: 2000,
          sort: kakao.maps.services.SortBy.DISTANCE,
        }
      );

      // 교통정보 가져오기
      this.ps.categorySearch(
        'SW8',
        (data, status) => {
          if (status === kakao.maps.services.Status.OK) {
            this.fetchSubwayList(data);
          }
        },
        {
          location: point.latlng,
          radius: 1000,
          sort: kakao.maps.services.SortBy.DISTANCE,
        }
      );

      // 마커 배열에 추가
      this.markers.push(marker);
    },
    addCustomOverlays(point) {
      let map = this.fetchedMap;
      let tmpContent =
        point.content.length > 7 ? point.content.substring(0, 7) + '···' : point.content;

      let customOverlay = new kakao.maps.CustomOverlay({
        // map: map,
        position: point.latlng,
        content:
          '<div style="background-color:rgba(18,89,112,0.75); color:white; font-size:11px; bottom:28px; right:9px; position:relative">' +
          tmpContent +
          '</div>',
      });

      this.customOverlays.push(customOverlay);
      customOverlay.setMap(map);
    },
    // getHouseList
  },
};
</script>

<style>
@import '../assets/css/custom.css';

.search-result-area {
  position: absolute;
  width: 307px;
  height: 380px;
  background-color: #fff;
  top: 40px;
  border: 1px solid lightgrey;
  left: 10px;
  z-index: 10;
  overflow-y: auto;
  display: none;
}
.search-result-inner {
  margin-bottom: 20px;
}
.search-result-title {
  color: #125970;
  margin: 7px 10px;
}
.list-group {
  border-radius: 0;
}
.list-group-item {
  line-height: 1.2;
  padding: 10px;
  cursor: pointer;
}
.list-group-item:hover {
  background-color: #f7f7f7;
}
.filter-contents-reset {
  position: absolute;
  top: 10px;
  right: 13px;
  font-size: 16px;
  cursor: pointer;
}
.vue-range-slider {
  margin-top: 16px;
}
.vue-slider {
  margin-left: 20px;
  padding: 32.5px 0 !important;
}
.vue-slider-process {
  background-color: #125970;
}
.vue-slider-dot-handle {
  border: 2px solid #f7f7f7;
  box-shadow: 3px 3px 3px silver;
}
.vue-slider-mark-step-active {
  box-shadow: 3px 3px 3px silver;
}
.vue-slider:hover .vue-slider-process {
  background-color: rgba(18, 89, 112, 0.75);
}
.vue-slider:hover .vue-slider-dot-handle {
  border: 2px solid #f7f7f7;
  box-shadow: 3px 3px 3px silver;
}
.vue-slider:hover .vue-slider-dot-handle-focus {
  border-color: #f7f7f7;
}
.vue-slider-dot-handle:hover {
  border-color: #f7f7f7;
}
.vue-slider:hover .vue-slider-dot-handle:hover {
  border-color: #f7f7f7;
}
.vue-slider:hover .vue-slider-mark-step-active {
  border-color: #f7f7f7;
  box-shadow: 3px 3px 3px silver;
}
</style>
