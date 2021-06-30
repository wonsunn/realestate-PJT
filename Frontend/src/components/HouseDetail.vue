<template>
  <div>
    <b-sidebar id="detailbar" right shadow>
      <div class="apart-detail-header-area">
        <div class="apart-detail-header">
          <div class="apart-detail-header-text">{{ fetchedApt.name }}</div>
          <div class="apart-detail-header-address">{{ fetchedAddr }}</div>
        </div>
      </div>
      <div class="apart-detail-result">
        <img :src="houseImageUrl" class="img-fluid mt-3" />
        <div class="result-below">
          <div class="result-buildyear">
            {{ fetchedApt.buildYear }}년 입주({{ 2021 - fetchedApt.buildYear + 1 }}년차)
          </div>
          <img
            v-show="isLike"
            :id="'houselike' + fetchedNo"
            class="result-like like"
            src="../assets/img/like.png"
            @click="deleteWish"
          />
          <img
            v-show="isUnlike"
            :id="'houseunlike' + fetchedNo"
            class="result-like unlike"
            src="../assets/img/unlike.png"
            @click="insertWish"
          />
        </div>
        <div class="result-area mt-3">
          <div class="result-area-title">시세 정보</div>
          <div class="price-area-price-title">
            매매 {{ fetchedApt.minPrice }} ~ {{ fetchedApt.maxPrice }}
          </div>
          <div class="result-area-contents">
            <b-table sticky-header striped :items="fetchedAptPrices"></b-table>
          </div>
        </div>
        <div class="result-area mt-3">
          <div class="result-area-title">학군 정보</div>
          <span class="result-area-title-span">반경 2km 내</span>
        </div>
        <div class="result-area-contents">
          <div class="result2-wrap">
            <b-list-group v-for="(school, index) in fetchedSchoolList" :key="index">
              <b-list-group-item
                v-if="index < click1 * 3"
                class="border-0"
                style="border-bottom: 1px solid lightgrey !important"
              >
                <div class="result2-area">
                  <div class="result2-area-left">
                    <div>{{ school.name }}</div>
                    <div class="result2-area-left-down">
                      {{ school.addr }}
                    </div>
                  </div>
                  <div class="result2-area-right">
                    <div class="result2-area-right-up" v-if="school.distance < 1000">
                      {{ school.distance }}m
                    </div>
                    <div v-else class="result2-area-right-up">
                      {{ Math.round(school.distance / 1000, 1) }}km
                    </div>
                    <div class="result2-area-right-down">학교로부터</div>
                  </div>
                </div>
              </b-list-group-item>
            </b-list-group>
            <div
              v-if="click1 * 3 < fetchedSchoolList.length"
              class="result2-area-more"
              @click="nextPage1"
            >
              더보기
            </div>
          </div>
        </div>

        <div class="result-area mt-3">
          <div class="result-area-title">교통 정보</div>
          <span class="result-area-title-span">반경 1km 내</span>
        </div>
        <div class="price-area-contents">
          <div class="result2-wrap">
            <b-list-group v-for="(subway, index) in fetchedSubwayList" :key="index">
              <b-list-group-item
                v-if="index < click2 * 3"
                class="border-0"
                style="border-bottom: 1px solid lightgrey !important"
              >
                <div class="result2-area">
                  <div class="result2-area-left">
                    <div>{{ subway.name }}</div>
                    <div class="result2-area-left-down">
                      {{ subway.addr }}
                    </div>
                  </div>
                  <div class="result2-area-right">
                    <div v-if="subway.distance < 1000" class="result2-area-right-up">
                      {{ subway.distance }}m
                    </div>
                    <div v-else class="result2-area-right-up">
                      {{ Math.round(subway.distance / 1000, 1) }}km
                    </div>
                    <div class="result2-area-right-down">역으로부터</div>
                  </div>
                </div>
              </b-list-group-item>
            </b-list-group>
            <div
              v-if="click2 * 3 < fetchedSubwayList.length"
              class="result2-area-more"
              @click="nextPage2"
            >
              더보기
            </div>
          </div>
        </div>
      </div>
    </b-sidebar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { mapActions } from 'vuex';
import http from '@/util/http-common';
const storage = window.sessionStorage;

export default {
  name: 'HouseDetail',
  data() {
    return {
      isLike: '',
      isUnlike: '',
      click1: 1,
      click2: 1,
      aptno: '',
      houseImageUrl: '',
    };
  },
  computed: {
    ...mapGetters([
      'fetchedNo',
      'fetchedAddr',
      'fetchedApt',
      'fetchedAptPrices',
      'fetchedUserToken',
      'fetchedSessionUser',
      'fetchedSchoolList',
      'fetchedSubwayList',
    ]),
  },
  watch: {
    aptno: function () {
      this.click1 = 1;
      this.click2 = 1;
      this.houseImageUrl = require('@/assets/img/house' +
        (Math.floor(Math.random() * 17) + 1) +
        '.jpg');
    },
  },
  mounted() {
    var bt = document.querySelector('#detailbar button');
    bt.onclick = function () {
      document.getElementById('detailbar').style.display = 'none';
    };
  },
  updated() {
    if (this.fetchedApt.wish_img == 'like') {
      this.isLike = true;
      this.isUnlike = false;
    } else {
      this.isLike = false;
      this.isUnlike = true;
    }
    this.aptno = this.fetchedNo;
  },
  methods: {
    ...mapActions(['fetchWishList']),
    nextPage1() {
      this.click1 += 1;
    },
    nextPage2() {
      this.click2 += 1;
    },
    insertWish() {
      if (storage.getItem('jwt-auth-token') == null) {
        alert('로그인이 필요합니다.');
      } else {
        alert('관심매물 추가!');
        http
          .post('/wish/' + this.fetchedSessionUser.useridx + '/' + this.fetchedNo)
          .then(({ data }) => {
            if (data == 'SUCCESS') {
              this.fetchedApt.wish_img = 'like';
              this.isLike = true;
              this.isUnlike = false;
              this.fetchWishList(this.fetchedSessionUser.useridx);
            }
          });
      }
    },
    deleteWish() {
      alert('관심매물 삭제!');
      http
        .delete('/wish/' + this.fetchedSessionUser.useridx + '/' + this.fetchedNo)
        .then(({ data }) => {
          if (data == 'SUCCESS') {
            this.fetchedApt.wish_img = 'unlike';
            this.isLike = false;
            this.isUnlike = true;
            this.fetchWishList(this.fetchedSessionUser.useridx);
          }
        });
    },
  },
};
</script>

<style>
button {
  border: 0;
  background-color: transparent;
}
#detailbar {
  width: 410px;
  margin-top: 120px;
  padding-bottom: 7%;
}
.b-sidebar > .b-sidebar-header {
  padding: 0 !important;
  height: 0px;
}
.b-sidebar.b-sidebar-right > .b-sidebar-header .close {
  z-index: 11;
  color: #fff !important;
  padding-top: 51px;
  font-size: 2rem;
}
</style>
