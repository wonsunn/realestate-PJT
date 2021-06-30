<template>
  <div class="">
    <div class="shadow-lg entire-join-container">
      <div class="h100">
        <!-- Nested Row within Card Body -->
        <div class="row h100">
          <div class="col-lg-5 d-none d-lg-block bg-register-image h100">
            <img src="../assets/img/joinimg.png" class="rounded" width="100%" height="100%" />
          </div>
          <div class="col-lg-7">
            <div class="" style="padding: 20% 6%">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4" style="text-align: left; color: #125970">
                  회원가입
                </h1>
              </div>

              <b-form-group id="userid-group" class="mb-3" label="아이디" label-for="userid">
                <b-form-input
                  type="text"
                  class=""
                  id="userid"
                  v-model="user.userid"
                  ref="userid"
                  name="userid"
                  placeholder="아이디를 입력하세요"
                  :valid="possiId"
                  @focusout="idCheck()"
                  @keyup="idCheck()"
                  required
                />
                <div v-if="possiId && this.idState" class="valid-feedback">
                  가능한 아이디입니다.
                </div>
                <div v-else class="invalid-feedback">중복된 아이디입니다.</div>
              </b-form-group>
              <b-form-group id="username-group" class="mb-3" label="이름" label-for="username">
                <b-form-input
                  type="text"
                  class=""
                  id="username"
                  v-model="user.username"
                  ref="username"
                  name="username"
                  placeholder="이름을 입력하세요"
                  required
                />
              </b-form-group>

              <b-form-group id="userpwd-group" class="mb-3" label="비밀번호" label-for="userpwd">
                <b-form-input
                  type="password"
                  class=""
                  id="userpwd"
                  v-model="user.userpwd"
                  ref="userpwd"
                  name="userpwd"
                  placeholder="비밀번호를 입력하세요"
                  required
                />
              </b-form-group>

              <b-form-group id="useremail-group" class="mb-3" label="이메일" label-for="useremail">
                <b-form-input
                  type="email"
                  class=""
                  id="useremail"
                  v-model="user.useremail"
                  ref="useremail"
                  name="useremail"
                  placeholder="이메일을 입력하세요"
                  required
                />
              </b-form-group>
              <hr />
              <button
                @click="checkJoin"
                class="btn btn-primary"
                style="background-color: #896872; border-color: #896872"
                id="join"
              >
                가입하기
              </button>
              <router-link to="/" class="btn btn-secondary" style="margin-left: 10px">
                뒤로가기
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from '@/util/http-common';

export default {
  name: 'Register',
  data() {
    return {
      user: {
        userid: '',
        username: '',
        userpwd: '',
        useremail: '',
      },
      possiId: false,
      desc: '',
    };
  },
  computed: {
    idState() {
      return this.possiId;
    },
  },
  methods: {
    // ...mapActions(['fetchUser']),
    checkJoin() {
      console.log('cehck');
      let err = true;
      let msg = '';
      !this.user.userid &&
        ((msg = '아이디를 입력해주세요'), (err = false), this.$refs.user.userid.focus());
      err &&
        !this.user.username &&
        ((msg = '이름을 입력해주세요'), (err = false), this.$refs.user.username.focus());
      err &&
        !this.user.userpwd &&
        ((msg = '비밀번호를 입력해주세요'), (err = false), this.$refs.user.userpwd.focus());
      err &&
        !this.user.useremail &&
        ((msg = '이메일을 입력해주세요'), (err = false), this.$refs.user.useremail.focus());

      if (!err) alert(msg);
      else this.doJoin();
    },
    doJoin() {
      console.log('dfdfdfdf');
      http
        .post('/user/', this.user)
        .then(() => {
          alert('회원가입이 완료되었습니다.');
          this.$router.push('/login');
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    idCheck() {
      const joinForm = document.getElementById('userid-group');
      if (this.user.userid != '') {
        joinForm.classList.remove('was-validated');
        http.get('/user/check/' + this.user.userid).then(({ data }) => {
          if (data == 'NO') {
            this.possiId = false;
            this.user.userid = '';
          } else {
            this.possiId = true;
          }
          joinForm.classList.add('was-validated');
        });
      }

      // alert('id check!');
      // this.desc = '중복된 아이디입니다.';
    },
  },
};
</script>

<style scoped>
.entire-join-container {
  width: 60%;
  margin-left: 20%;
  margin-top: 4%;
  height: 700px;
}
.h100 {
  height: 100%;
}
</style>
