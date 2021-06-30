<template>
  <div>
    <notice-row-detail/>
  </div>
</template>

<script>
import NoticeRowDetail from '@/components/notice/include/NoticeRowDetail';
export default {
    name:'noticeread',
    components:{
      NoticeRowDetail
    },
    data: function(){
      return {
        notice:{},
      };
    },
}
</script>

<style>

</style>