<template>
  <div>
    <kakao-map></kakao-map>
    <house-detail></house-detail>
    <house-wish></house-wish>
    <!-- <notice-board></notice-board> -->
    <user-page></user-page>
    <!-- <area-chart></area-chart> -->
  </div>
</template>

<script>
import KakaoMap from '@/components/KakaoMap.vue';
// import NoticeBoard from '@/components/notice/NoticeBoard.vue';
import HouseDetail from '@/components/HouseDetail.vue';
import HouseWish from '@/components/HouseWish.vue';
import UserPage from '@/components/UserPage.vue';
// import AreaChart from '@/components/AreaChart.vue';

export default {
  name: 'home',
  components: {
    KakaoMap,
    // NoticeBoard,
    HouseDetail,
    HouseWish,
    UserPage,
    // AreaChart,
  },
};
</script>

<style></style>
