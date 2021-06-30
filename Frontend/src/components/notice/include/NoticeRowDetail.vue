
<template>
  <div id="notiD" class="shadow-lg entire-notice-container">
    <div class="notice-table-area">
      <table
        id="noticeDetailTable"
        class="table table-bordered mt-3"
        style="border: 1px solid dimgrey"
      >
        <thead>
          <tr style="text-align: center">
            <th>제목</th>
            <td colspan="5">{{this.subject}}</td>
          </tr>
        </thead>
        <tbody>
          <tr style="text-align: center">
            <th>작성자</th>
            <td>{{this.username}}</td>
            <th>조회수</th>
            <td>{{this.views}}</td>
            <th>작성일</th>
            <td>{{this.regtime}}</td>
          </tr>
          <tr>
            <td colspan="6" style="height: 300px">{{this.content}}</td>
          </tr>
        </tbody>
      </table>
      <div class="button" style="text-align: center">
        <router-link
          type="button"
          class="btn btn-secondary"
          style="margin-right: 10px"
          :to="`/notice`"
        >
          목록
          <div style="display: none">{{notice.articleno}}</div>

        </router-link>
  
          <router-link v-if="auth ==1"
            type="button"
            class="btn btn-warning"
            style="margin-right: 10px"
            :to="`/update?no=${notice.articleno}`"
          >
            수정</router-link
          >
          <router-link v-if="auth ==1"
            type="button"
            class="btn btn-danger"
            :to="`/delete?no=${notice.articleno}`"
            @click.native="noticeDelete"
          >
            삭제
          </router-link>
      </div>
    </div>
 
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import http from '@/util/http-common';
const localstore = window.localStorage;
const storage = window.sessionStorage;

export default {
  name: 'noticerowdetail',
  data(){
    return{
      subject: localstore.getItem('subject'),
      username: localstore.getItem('username'),
      views: localstore.getItem('views'),
      regtime: localstore.getItem('regtime'),
      content: localstore.getItem('content'),
      nowUser: '',
      auth: 0,
    };

  },
  updated(){
    console.log("여기ㅡㄹ");
    this.subject = this.notice.subject;
    this.username = this.notice.username;
    this.views = this.notice.views;
    this.regtime = this.notice.regtime;
    this.content= this.notice.content;
    console.log(this.subject);
  },
  created(){
    console.log("처음으로 여기?");
    console.log(storage.getItem('subject'));
    let user = JSON.parse(storage.getItem('login_user'));
    if(user){
      console.log("현재 사용자 :",user);
      this.nowUser=user.username;
      console.log(this.username);
      this.auth = this.fetchedSessionUser.userauth;
    }
    // this.subject = this.notice.subject;
    // this.userid = this.notice.userid;
    // this.views = this.notice.views;
    // this.regtime = this.notice.regtime;
    // this.content= this.notice.content;
    console.log("notice로 저장");
    console.log("나우 유져의 뭔가 : ",this.nowUser);
    console.log(this.subject);

  },
  computed: {
    ...mapGetters(          
                [ 'notice',
                  'fetchedUserName',
                  'fetchedSessionUser',]),         
  },
  methods: {
    noticeDelete() {
      http
        .delete(`/notice/${this.notice.articleno}`)
        .then(() => {
          alert('삭제되었습니다.');
          this.$router.push('/notice');
        })
        .catch(() => {
          alert('삭제시 문제 발생.');
        });
    },
   
  },
};
</script>

<style>
.entire-notice-container {
  width: 60%;
  margin-left: 20%;
  margin-top: 4%;
  height: 700px;
}
.notice-table-area {
  padding: 30px;
}
#noticeDetailTable > thead,
tbody > tr > th {
  color: #125970;
}
</style>
