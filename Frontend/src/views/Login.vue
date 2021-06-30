<template>
  <div class="">
    <div class="shadow-lg entire-login-container">
      <div class="h100">
        <!-- Nested Row within Card Body -->
        <div class="row h100">
          <div class="col-lg-5 d-none d-lg-block bg-register-image h100">
            <img src="../assets/img/loginimg.png" class="rounded" width="100%" height="100%" />
          </div>
          <div class="col-lg-7">
            <div class="" style="padding: 30% 6%">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4" style="text-align: left; color: #125970">
                  로그인
                </h1>
              </div>
              <b-form-group id="inputLoginId-group" label="아이디" label-for="inputLoginId" />
              <b-form-input
                type="text"
                class="mb-3"
                id="inputLoginId"
                v-model="userid"
                name="inputLoginid"
                placeholder="아이디를 입력하세요"
                required
              />

              <b-form-group id="inputLoginPw-group" label="비밀번호" label-for="inputLoginPw" />
              <b-form-input
                type="password"
                class="mb-3"
                id="inputLoginPw"
                v-model="userpwd"
                name="inputLoginPw"
                placeholder="비밀번호를 입력하세요"
              />
              <hr />
              <button
                @click="doLogin"
                class="btn btn-primary"
                style="background-color: #896872; border-color: #896872"
                id="login"
              >
                로그인
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
import { mapActions } from 'vuex';

export default {
  name: 'Login',
  data() {
    return {
      userid: '',
      userpwd: '',
    };
  },
  methods: {
    ...mapActions(['fetchUser']),
    doLogin() {
      console.log(this.userid);
      this.fetchUser({
        userid: this.userid,
        userpwd: this.userpwd,
      });
    },
  },
};
</script>

<style scoped>
.entire-login-container {
  width: 60%;
  margin-left: 20%;
  margin-top: 4%;
  height: 700px;
}
.h100 {
  height: 100%;
}
</style>
