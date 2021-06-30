<template>
  <div class="">
    <div class="shadow-lg entire-notice-container">
      <!-- < class="container"> -->
      <div class="notice-table-area">
        <h4 style="text-align: center; color: rgb(18, 89, 112)">공지사항</h4>
        <div class="notice-wrap" style="margin-top: 6%">
          <div class="button mb-2" style="">
            <router-link type="button" v-if="auth == 1" class="btn btn-warning" to="/regist"> 등록 </router-link>
          </div>
          <b-table
            id="noticeTable"
            striped
            :items="notices"
            :keys="notices"
            selectable
            @row-clicked="readNotice"
          ></b-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
const storage = window.sessionStorage;

export default {
  name: 'notice',
  components: {
  },
  data(){
    return{
      userid: '',
      auth: 0,
    }
  },
  computed: {
    ...mapGetters(['notices','fetchedUserId','fetchedSessionUser']),
  },
  created() {
    let user = JSON.parse(storage.getItem('login_user'));
    if(user){
      console.log("현재 사용자 :",user);
      this.userid=user.userid;
      console.log(this.userid);
      this.auth = this.fetchedSessionUser.userauth;
    }

    this.$store.dispatch('getNotices');
  },
  methods: {
    makeToast(variant = null) {
        this.$bvToast.toast('Toast body content', {
          title: `Variant ${variant || 'default'}`,
          variant: variant,
          solid: true
        })
      },


    readNotice(record) {

      // this.get_articleno(record['번호']);
      // this.get_userid(record['작성자']);
      // this.get_subject(record['제목']);
      // this.get_views(record['조회수']);
      // this.get_regtime(record['등록일']);
      // this.get_content(record['제목']);
      
      console.log(record['번호']);
      console.log("여기로???들어옴");
      // console.log(items.articleno);
      this.$store.dispatch('readNotice', '/notice/read/' + record['번호']);
      this.$router.push(`/read?no=` + `record['번호']`);
    },
  },
};
</script>

<style scoped>
/* table{
  border: 2px solid; border-collapse: collapse;
} */
th {
  background-color: teal;
}
.entire-notice-container {
  width: 60%;
  margin-left: 20%;
  margin-top: 4%;
  height: 700px;
}
.h100 {
  height: 100%;
}
#noticeTable {
  /* margin-top: 6%; */
  text-align: center;
}
#noticeTable > thead > tr > th {
  color: #fff;
}
.notice-table-area {
  padding: 15px;
}
</style>
