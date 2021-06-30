<template>
  <b-list-group-item href="#" v-model="dong" @click="centerSearch(dong)">
    {{this.areaname}}
    <b-button v-if="selected" v-b-toggle.sidebar-right>상세</b-button>
  </b-list-group-item>
</template>

<script>
import { mapGetters } from 'vuex';
import { mapActions } from 'vuex';

export default {
    name: 'interestarea',
    data() {
      return{
        areaname: '',
        dong: '',
        selected: false,
      }
    },
    props: {
      interestarea: Object,
    },

  computed:{
    ...mapGetters(['fetchedInterestAreaList','fetchedSessionUser','isCenterCheck',])
  },
  created(){
    this.areaname = this.interestarea.city+" "+this.interestarea.gugun+" "+this.interestarea.dong;
    this.dong = this.interestarea.dong;
  },
  methods:{
    ...mapActions(['centerDong']),

    centerSearch(centerDong){
      console.log("관심지역 동 누르면 시작 하는 부분");
      this.selected = !this.selected;
      this.centerDong({
        dong: centerDong,
        check: !this.isCenterCheck,
      })
    },
  }


}
</script>

<style>

</style>