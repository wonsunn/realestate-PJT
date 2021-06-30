<template>
  <div id="noticeR">
    <tr>
      <td>{{ notice.articleno }}</td>
      <td>
        <router-link :to="`/read?no=${notice.articleno}`">{{
          notice.subject
        }}</router-link>
      </td>
      <td>{{ notice.userid }}</td>
      <td>{{ notice.views }}</td>
      <td>{{ notice.regtime }}</td>
    </tr>
  </div>
</template>

<script>
// import { mapActions } from 'vuex';
// const moduleName = 'modulName';
export default {
  name: 'notice_row',
  props: {
    notice: Object,
  },
  methods: {
    // ...mapActions(moduleName,['get_articleno',
    //                           'get_userid',
    //                           'get_subject',
    //                           'get_views',
    //                           'get_regtime',
    //                           'get_content',
    //                   ]),
    readNotice() {
      // this.get_articleno(this.notice.articleno);
      // this.get_userid(this.notice.userid);
      // this.get_subject(this.notice.subject);
      // this.get_views(this.notice.views);
      // this.get_regtime(this.notice.regtime);
      // this.get_content(this.notice.content);
      // console.log("Row에서");
      // this.$store.dispatch('readNotice', `/notice/read/${this.notice.articleno}`);

    },
  },
};
</script>

<style></style>
