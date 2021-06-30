import Vue from 'vue';
import VueRouter from 'vue-router';
// import Home from '../views/Home.vue';
import Notice from '@/components/notice/NoticeBoard.vue';
import Regist from '@/components/notice/NoticeRegist.vue';
import Read from '@/components/notice/NoticeRead.vue';
import Update from '@/components/notice/NoticeUpdate.vue';
import Delete from '@/components/notice/NoticeDelete.vue';
import Home from '@/views/Home.vue';
import InterestAreaPractice from '@/components/interestarea/InterestAreaPractice.vue';
Vue.use(VueRouter);

const routes = [
  {
    path: '/index.html',
    name: 'Index',
    redirect: '/',
  },
  {
    path: '/',
    name: 'home',
    component: Home,
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue'),
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
  },
  {
    path: '/notice',
    name: 'notice',
    component: Notice,
  },
  {
    path: '/regist',
    name: 'regist',
    component: Regist,
  },
  {
    path: '/read',
    name: 'read',
    component: Read,
  },
  {
    path: '/update',
    name: 'update',
    component: Update,
  },
  {
    path: '/delete',
    name: 'delete',
    component: Delete,
  },
  {
    path: '/interestareapractice',
    name: 'intereinterestareapracticestarea',
    component: InterestAreaPractice,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
