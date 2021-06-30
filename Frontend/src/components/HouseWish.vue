<template>
  <div>
    <b-sidebar v-if="fetchedWishList.length > 0" id="wishbar">
      <div class="wish-header-area">
        <div class="wish-header">
          <div class="wish-header-title">관심 매물 리스트 {{ fetchedWishList.length }}개</div>
        </div>
      </div>
      <div v-for="(item, index) in fetchedWishList" :key="'wish' + index">
        <div class="wish-house-body">
          <div class="wish-house-image-area" @click="showApartDetail(item.no, item.addr)">
            <img
              class="wish-house-image"
              :src="require('@/assets/img/house' + (Math.floor(Math.random() * 17) + 1) + '.jpg')"
            />
          </div>
          <div class="wish-house-contents-area">
            <div class="wish-house-contents-price">
              매매 {{ item.minPrice }} ~<br />
              {{ item.maxPrice }}
            </div>
            <p style="font-size: 13px; margin-bottom: 0">아파트·{{ item.name }}</p>
            <p style="font-size: 12px; margin-bottom: 0">{{ item.addr }}</p>
            <div class="wish-house-delete-button-area">
              <button
                @click="goDelete(item.no)"
                type="button"
                class="delete-btn btn btn-outline-danger wish-delete-btn"
                style="line-height: 17px"
              >
                삭제
              </button>
            </div>
          </div>
        </div>
      </div>
    </b-sidebar>
    <b-sidebar v-else id="wishbar">
      <div class="wish-header-area">
        <div class="wish-header">
          <div class="wish-header-title">관심 매물 리스트 0개</div>
        </div>
      </div>
      <div style="position: relative">
        <img class="nowish_img" src="@/assets/img/wishhouse.png" />
      </div>
      <div class="nowish_text">
        등록된 관심 매물이 없습니다.<br />
        HAPPY HOUSE에서 살고 싶은 집을 찾아보세요!
      </div>
    </b-sidebar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { mapActions } from 'vuex';
import http from '@/util/http-common';

export default {
  name: 'HouseWish',
  computed: {
    ...mapGetters(['fetchedWishList', 'fetchedApt', 'fetchedSessionUser']),
  },
  methods: {
    ...mapActions(['fetchWishList', 'getApartDetail']),
    goDelete(apartno) {
      if (confirm('삭제하시겠습니까?')) {
        http.delete('/wish/' + this.fetchedSessionUser.useridx + '/' + apartno).then(() => {
          this.fetchWishList(this.fetchedSessionUser.useridx).then(() => {
            if (this.fetchedApt.apartno == apartno) {
              document.getElementById('houselike' + apartno).style.display = 'none';
              document.getElementById('houseunlike' + apartno).style.display = 'block';
            }
          });
        });
      }
    },
    showApartDetail(apartno, addr) {
      this.getApartDetail({
        no: apartno,
        addrName: addr,
        userIdx: this.fetchedSessionUser.useridx,
      });
      document.getElementById('detailbar').style.display = 'flex';
    },
  },
};
</script>

<style>
#wishbar {
  width: 400px;
  margin-top: 120px;
  padding-bottom: 7%;
}
.wish-delete-btn {
  width: 75px;
  height: 34px;
}
.nowish_img {
  margin-top: 40%;
  margin-left: 35%;
  width: 30%;
}
.nowish_text {
  margin-top: 10%;
  text-align: center;
  color: #125970;
  font-size: 18px;
}
.b-sidebar > .b-sidebar-header .close {
  z-index: 2;
  color: #000 !important;
  font-size: 2rem !important;
  padding-top: 38px;
  font-size: 2rem;
}
</style>
