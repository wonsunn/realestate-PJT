<template>
  <div>
    <div id="header-depth2" class="depth2" style="height: 50px">
      <router-link to="/" id="area0" class="on" @click.native="goInterestArea(0)"
        >아파트</router-link
      >
      <router-link to="/interestareapractice" id="area1" class="" @click.native="goInterestArea(1)"
        >관심 지역</router-link
      >
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
    

export default {
  name: 'NavSubHeader',
  data() {
    return {
      classes: [],
    };
  },
  computed: {
    ...mapGetters(['fetchedUserToken']),
    // selected(data){
    //   console.log(data);
    // },
  },
  methods: {
    goInterestArea(idx) {
      document.getElementById('area' + idx).classList.add('on');
      document.getElementById('area' + (1 - idx)).classList.remove('on');
    },
  },
};
</script>

<style></style>
