<template>
  <div>
    <div id="header" style="height: 70px">
      <b-container fluid class="d-flex align-items-center">
        <h1 class="logo header-depth1-logo">
          <a href="/"
            ><img
              id="logo_img"
              src="../assets/img/mainhouse.png"
              width="65"
              alt="The SSAFY"
              style="position: absolute; top: 2px"
          /></a>
          <span class="logo-title" style="margin-left: 72px">HAPPY HOUSE</span>
        </h1>
        <nav class="nav-menu justify-content-start">
          <ul>
            <li><router-link to="/notice">공지사항</router-link></li>
            <li
              v-if="fetchedUserToken != '' || fetchedToken != null"
              class="navPointer"
              @click="showWishList"
            >
              <a v-b-toggle.wishbar>관심 매물</a>
            </li>
            <li
              v-if="fetchedUserToken != '' || fetchedToken != null"
              class="navbarName navPointer"
              v-b-modal.user-modal
            >
              {{ fetchedSessionUser == null ? fetchedUserName : fetchedSessionUser.username }}님
            </li>
          </ul>
        </nav>
        <div v-if="fetchedUserToken == '' && fetchedToken == null">
          <router-link to="/login" class="get-started-btn" id="goLogin">로그인</router-link>
          <router-link to="/register" class="get-started-btn" id="goJoin">회원가입</router-link>
        </div>
        <div v-else>
          <a @click="doLogout" class="get-started-btn navPointer" id="doLogout">로그아웃</a>
        </div>
      </b-container>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { mapActions } from 'vuex';

export default {
  name: 'NavHeader',
  computed: {
    ...mapGetters(['fetchedUserName', 'fetchedUserToken', 'fetchedToken', 'fetchedSessionUser']),
  },
  methods: {
    ...mapActions(['fetchOffUser', 'fetchWishList']),
    doLogout() {
      this.fetchOffUser().then(() => {
        alert('로그아웃!');
      });
    },
    showWishList() {
      this.fetchWishList(this.fetchedSessionUser.useridx);
    },
  },
};
</script>

<style scoped>
@import '../assets/css/style.css';
@import '../assets/css/custom.css';

#header {
  background-color: #faf9f2;
}
.container-fluid {
  padding-right: 35px;
  padding-left: 35px;
}
.navbarName {
  line-height: 1.24;
  color: #18263a;
  font-size: 17px;
  font-weight: bold;
}
.navPointer {
  cursor: pointer;
}
.nav-menu a:hover {
  color: #18263a;
}
</style>
