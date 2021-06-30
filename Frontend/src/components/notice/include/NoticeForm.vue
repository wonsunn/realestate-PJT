<template>
  <div>
    <div class="shadow-lg entire-notice-container">
      <!-- < class="container"> -->
      <div class="notice-table-area">
        <div  v-if="type == 'update'">
          <h4 style="text-align: center; color: rgb(18, 89, 112)">
            수정하기
          </h4>
          <div class="notice-wrap" style="margin-top: 6%">
            <div class="form-group">
              <label for="title">제목</label>
              <input
                type="text"
                class="form-control"
                id="subject"
                ref="subject"
                v-model="subject"
              />
            </div>
            <div class="form-group">
              <label for="content">내용</label>
              <textarea
                type="text"
                class="form-control"
                id="contnet"
                ref="content"
                cols="30"                
                v-model="content"
              ></textarea>
            </div>
            <div class="mt-3" style="text-align: center">           
              <button class="btn btn-warning" style="margin-right: 10px" @click="checkHandler">
                수정
              </button>
              <button class="btn btn-secondary" style="margin-right: 10px" @click="moveNotice">
                취소
              </button>
            </div>
          </div>
        </div>

        <div v-else>
          <h4 style="text-align: center; color: rgb(18, 89, 112)">
            등록하기
          </h4>
          <div class="notice-wrap" style="margin-top: 6%">
            <div class="form-group">
              <label for="title">제목</label>
              <input
                type="text"
                class="form-control"
                id="subject"
                ref="subject"
                placeholder="제목을 입력하세요"
                v-model="subject"
              />
            </div>
            <div class="form-group">
              <label for="content">내용</label>
              <textarea
                type="text"
                class="form-control"
                id="contnet"
                ref="content"
                cols="30"
                placeholder="내용을 입력하세요"
                v-model="content"
              ></textarea>
            </div>
            <div class="mt-3" style="text-align: center">
              <button 
                class="btn btn-primary"
                style="margin-right: "
                @click="checkHandler"
              >
                등록
              </button>
              <button class="btn btn-secondary" style="margin-right: 10px" @click="moveNotice">
                취소
              </button>
            </div>
          </div>
        </div>


      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import http from '@/util/http-common';

export default {
  name: 'board-form',
  props: {
    notice: Object,
    type: { type: String },
  },
  data: function () {
    return {
      articleno: '',
      regtime: '',
      userid: '',
      subject: '',
      content: '',
      views: '',
    };
  },
  computed:{
    
    ...mapGetters(['fetchedUserId','fetchedSessionUser']),
  },
  created() {
   
    if (this.type === 'update') {
      http.get(`/notice/read/${this.$route.query.no}`).then(({ data }) => {
        this.articleno = data.articleno;
        this.userid = data.userid;
        this.subject = data.subject;
        this.content = data.content;
        this.views = data.views;
        this.regtime = data.regtime;
      });
    }
  },
  methods: {
    checkHandler() {
      let err = true;
      let msg = '';
      !this.subject && ((msg = '제목을 입력하세요'), (err = false), this.$refs.subject.focous());
      err &&
        !this.content &&
        ((msg = '내용을 입력하세요'), (err = false), this.$refs.content.focus());
      if (!err) alert(msg);
      else this.type == 'regist' ? this.registHandler() : this.updateHandler();
    },
    registHandler() {

      console.log("유저 아이디",this.fetchedSessionUser.userid);
      http
        .post('/notice', {
          articleno: this.articleno,
          username: this.fetchedSessionUser.username,
          subject: this.subject,
          content: this.content,
          views: this.views,
          regtime: this.regtime,
        })
        .then(() => {
          let msg = '등록이 완료되었습니다.';
          alert(msg);
          this.moveNotice();
        })
        .catch(() => {
          console.log('에러가 발생?');
          alert('등록 처리시 에러 발생');
        });
    },
    updateHandler() {
      http
        .put(`/notice/update/${this.articleno}`, {
          articleno: this.articleno,
          regtime: this.regtime,
          userid: this.userid,
          subject: this.subject,
          views: this.views,
          content: this.content,
        })
        .then(() => {
          let msg = '수정이 완료되었습니다..';
          alert(msg);
          this.moveNotice();
        })
        .catch(() => {
          alert('수정 처리시 에러 발생');
        });
    },
    moveNotice() {
      this.$router.push('/notice');
    },
  },
};
</script>

<style>
textarea {
  height: 300px;
  resize: none;
}
</style>
