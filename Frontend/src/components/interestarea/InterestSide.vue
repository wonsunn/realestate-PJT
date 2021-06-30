<template>
  <div>
    <!-- <div class="interestbar" id="insbar" style="height: 8%">
      <div class="search-input-area">
        <div class="search-input-liner1">
          <div class="input-wrap1">
            <input
              type="text"
              placeholder="아파트, 법정동으로 검색"
              id="word1"
              name="word"
              v-model="word"
              @keypress.enter="searchSimilarWords(word)"
            />
            <img
              id="input-close-button1"
              class="input-close"
              src="@/assets/img/close-button-icon-black.png"
              @click="resetWord"
            />
          </div>
          <div>
            <div class="btn-search-wrap left" id="search-wrap">
              <button id="btn-search1" name="search" class="btn-search1" @click="insertArea">
                <img id="searchImg" src="@/assets/img/search-icon.png" style="" />
              </button>
            </div>
            <div class="right" id="nowLocation">
              {{ this.fetchedDongName }}
            </div>
          </div>
        </div>
      </div>
    </div> -->
    <div id="interestinput" style="height: 50px; border: 1px solid lightgrey">
      <div style="display: flex; height: 100%; padding: 5px">
        <div style="flex-basis: 65%">
          <b-form-input
            type="text"
            v-model="fetchedDongName"
            name="word"
            id="word"
            placeholder="관심지역으로 추가할 동 이름을 검색하세요"
            style="width: 100%"
          />
        </div>
        <div style="flex-basis: 25%; text-align: right">
          <b-button
            v-b-toggle.chartbar
            @click="centerSearch(interest.dong)"
            style="height: 38px; color: #125970; border-color: #125970; background-color: #fff"
          >
            지역 분석
          </b-button>
        </div>
        <div style="flex-basis: 15%; text-align: right; padding-right: 10px">
          <b-button variant="" style="width: 91%; background-color: #125970" @click="insertArea"
            >추가</b-button
          >
        </div>
      </div>
    </div>
    <b-card-group deck style="height: calc(100vh - 170px); overflow: scroll">
      <div id="card">
        <div>
          <b-card no-body header="나의 관심 지역" style="font-weight: bold">
            <div style="width: auto; height: 20%">
              <b-button id="checkList" v-show="open" @click="showList">open</b-button>
              <b-button id="checkList" v-show="close" @click="closeList">close</b-button>
              <b-list-group
                v-show="close"
                flush
                v-for="(interest, idx) in fetchedInterestAreaList"
                :key="idx"
              >
                <b-list-group-item>
                  <div
                    @click="centerSearch(interest.dong)"
                    style="flex-basis: 80%; padding-top: 6px"
                  >
                    {{ interest.city }} {{ interest.gugun }} {{ interest.dong }}
                  </div>
                  <div style="flex-basis: 15%; text-align: right">
                    <b-img
                      type="button"
                      v-b-toggle.chartbar
                      @click="centerSearch(interest.dong)"
                      :src="imageUrl2"
                      style="border: 0px; width: 30px"
                    ></b-img>
                  </div>
                  <b-img
                    type="button"
                    :src="imageUrl"
                    thumbnail
                    class="trash"
                    style="border: 0px; padding-top: 10px"
                    @click="deleteArea(interest.dong)"
                  ></b-img>
                </b-list-group-item>
              </b-list-group>
            </div>
          </b-card>
        </div>
        <div style="width: auto; height: 80%">
          <b-card
            v-for="(pInfo, idx) in fetchedPlacesInfo.placesInfo"
            :key="idx"
            :header="pInfo.address_name"
            header-tag="header"
            :title="pInfo.place_name"
          >
            <b-card-text>{{ pInfo.distance }}m 내</b-card-text>
            <b-button
              :href="pInfo.place_url"
              target="_blank"
              style="background-color: #125970; border-color: #125970"
              variant="primary"
              >열기</b-button
            >
          </b-card>
        </div>
      </div>
    </b-card-group>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import http from '@/util/http-common';
const storage = window.sessionStorage;
export default {
  data() {
    return {
      interestList: [],
      open: true,
      close: false,
      placesInfo: this.fetchedPlacesInfo,
      imageUrl: '',
      imageUrl2: '',
      dong: '',
      word: this.fetchedDongName,
    };
  },

  computed: {
    ...mapGetters([
      'fetchedInterestAreaList',
      'fetchedSessionUser',
      'interestAreaList',
      'fetchedPlacesInfo',
      'fetchedDongName',
    ]),
  },
  watch: {
    placesInfo: function (infos) {
      console.log(infos);
    },
  },
  created() {
    this.imageUrl = require('@/assets/img/trashcan.png');
    this.imageUrl2 = require('@/assets/img/analysis.png');
    this.getInterestAreaList(this.fetchedSessionUser.useridx);
    this.interestList = this.fetchedInterestAreaList;
  },
  updated() {
    // this.getInterestAreaList(this.fetchedSessionUser.useridx);
    // this.interestList=this.fetchedInterestAreaList;
    // if(this.open){
    //   this.open = false;
    //   this.close = true;
    // }else{
    //   this.open = true;
    //   this.close = false;
    // }
  },
  methods: {
    ...mapActions(['getInterestAreaList', 'centerDong', 'removedDong']),
    showList() {
      if (storage.getItem('jwt-auth-token') == null) {
        alert('로그인이 필요합니다.');
      } else {
        this.open = false;
        this.close = true;
      }
    },
    closeList() {
      this.open = true;
      this.close = false;
    },
    centerSearch(centerDong) {
      this.centerDong({
        dong: centerDong,
        check: !this.isCenterCheck,
      });
    },
    deleteArea(dong) {
      this.removedDong(dong);

      let aaa = dong;
      http.delete('/interest/' + this.fetchedSessionUser.useridx + '/' + aaa).then(({ data }) => {
        if (data == 'SUCCESS') {
          alert('관심지역 삭제');
          this.getInterestAreaList(this.fetchedSessionUser.useridx);
        } else {
          alert('삭제 불가');
        }
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
    insertArea() {
      if (storage.getItem('jwt-auth-token') == null) {
        alert('로그인이 필요합니다.');
      } else {
        // if (this.word == '' || this.word == null) {
        //   alert('원하는 지역명 입력');
        // } else {
        //   console.log('word   ' + this.word);
        http
          .post('/interest/' + this.fetchedSessionUser.useridx + '/' + this.fetchedDongName)
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
    },
    resetWord() {
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
    },
  },
};
</script>

<style>
#checkList {
  position: absolute;
  top: 1px;
  right: 0;
}
interest-area-row {
  position: absolute;
  top: 39px;
  right: 0px;
}
.trash {
  position: absolute;
  right: 0px;
  width: 33px;
  top: 0;
  height: 43px;
}

.search-input-liner1 {
  width: 337px;
  height: 40px;
  position: absolute;
  top: 0px;
  left: 0px;
}
#id1 {
  height: 38px;
}
#input-close-button1 {
  width: 25px;
  position: absolute;
  left: 195px;
  margin-top: 8px;
}
#searchImg {
  width: 37px;
  height: 46px;
  margin-top: 2px;
}
#btn-search1.btn-search1 {
  height: 38px;
}
/* #card{
  margin-top: 18px;
} */
#word1 {
  height: 49px;
  width: 230px;
}
.input-wrap1 {
  width: 223px;
}
#search-wrap {
  border: 2px;
  border-radius: 5px;
  position: absolute;
  right: 74px;
  top: 0;
  height: 49px;
  width: 40px;
}
#nowLocation {
  position: absolute;
  top: 11px;
  right: -133px;
}
.list-group-flush > .list-group-item {
  display: flex;
}
</style>
