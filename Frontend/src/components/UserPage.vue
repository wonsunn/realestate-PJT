<template>
  <div>
    <b-modal id="user-modal" title="회원정보 수정">
      <b-form-group id="userMid-group" class="mb-3" label="아이디" label-for="userMid">
        <b-form-input
          type="text"
          class=""
          id="userMid"
          ref="userMid"
          name="userMid"
          v-model="user.userid"
          readonly
        />
      </b-form-group>
      <b-form-group id="userMname-group" class="mb-3" label="이름" label-for="userMname">
        <b-form-input
          type="text"
          class=""
          id="userMname"
          v-model="user.username"
          ref="userMname"
          name="userMname"
        />
      </b-form-group>

      <b-form-group id="userMemail-group" class="mb-3" label="이메일" label-for="userMemail">
        <b-form-input
          type="email"
          class=""
          id="userMemail"
          v-model="user.useremail"
          ref="userMemail"
          name="userMemail"
        />
      </b-form-group>

      <button
        @click="doModify"
        class="btn btn-primary"
        style="background-color: #896872; border-color: #896872"
        id="modify"
      >
        수정하기
      </button>
    </b-modal>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { mapActions } from 'vuex';
import http from '@/util/http-common';

export default {
  name: 'UserPage',
  data() {
    return {
      user: {
        userid: '',
        username: '',
        useremail: '',
      },
    };
  },
  computed: {
    ...mapGetters(['fetchedUserName', 'fetchedUserEmail', 'fetchedUserId', 'fetchedSessionUser']),
  },
  mounted() {
    this.user.userid =
      this.fetchedSessionUser == null ? this.fetchedUserId : this.fetchedSessionUser.userid;
    this.user.username =
      this.fetchedSessionUser == null ? this.fetchedUserName : this.fetchedSessionUser.username;
    this.user.useremail =
      this.fetchedSessionUser == null ? this.fetchedUserEmail : this.fetchedSessionUser.useremail;
  },
  methods: {
    ...mapActions(['fetchUserInfo']),
    doModify() {
      http.put('/user/', this.user).then(() => {
        this.fetchUserInfo(this.user);
        alert('수정이 완료되었습니다.');
      });
    },
  },
};
</script>

<style></style>
