import Vue from 'vue';
import Vuex from 'vuex';
// import createPersistedState from 'vuex-persistedstate';
import http from '@/util/http-common';
import router from '../router';
import createdPersistedState from "vuex-persistedstate";
import moduleName from '@/store/modules/moduleName.js';

const storage = window.sessionStorage;
const localstore = window.localStorage;

Vue.use(Vuex);


const store = new Vuex.Store({
  state: {

    /* notice */
    articleno: '',
    userid: '',
    subject: '',
    views: '',
    regtime: '',
    content:'',

    /* map */
    map: '',
    aptList: [],
    apt: {},
    aptPriceList: [],
    selAddr: '',
    selNo: '',
    /* notice */
    notices: [],
    notice: {},
    /* user */
    userIdx: '',
    userId: '',
    userPwd: '',
    userName: '',
    userEmail: '',
    userAuthToken: '',
    userWishList: [],
    user: {},
    schoolList: [],
    subwayList: [],

    /* 관심지역 리스트 */
    interestAreaList: [],

    dongname: '',

    // 반경 내 장소 정보
    placesInfo: [],

    centerCheck: false,

    // 관심지역 삭제로 마커 원 지워야 하는지 체크
    removedDong: '',
  },
  modules: {
    moduleName : moduleName,
  },

  plugins: [
    createdPersistedState({
      
      paths: ["moduleName"],

      // SET_NOTICE: state => state.notice,
      // ARTICLENO: state => state.articleno,
      // USERID: state => state.userid,
      // SUBJECT: state => state.subject,
      // VIEWS: state => state.views,
      // REGTIME: state => state.regtime,
      // CONTENT: state => state.content,
    }),
  ],


  getters: {
    /* user */
    fetchedUser(state) {
      return state.user;
    },
    fetchedUserId(state) {
      return state.userId;
    },
    fetchedUserName(state) {
      return state.userName;
    },
    fetchedUserEmail(state) {
      return state.userEmail;
    },
    fetchedUserToken(state) {
      return state.userAuthToken;
    },
    fetchedWishList(state) {
      return state.userWishList;
    },
    fetchedToken() {
      return storage.getItem('jwt-auth-token');
    },
    fetchedSessionUser() {
      return JSON.parse(storage.getItem('login_user'));
    },
    /* map */
    fetchedAptList(state) {
      return state.aptList;
    },
    fetchedApt(state) {
      return state.apt;
    },
    fetchedAptPrices(state) {
      return state.aptPriceList;
    },
    fetchedNo(state) {
      return state.selNo;
    },
    fetchedAddr(state) {
      return state.selAddr;
    },
    fetchedMap(state) {
      return state.map;
    },
    fetchedSchoolList(state) {
      return state.schoolList;
    },
    fetchedSubwayList(state) {
      return state.subwayList;
    },
    /* notice */
    notices(state) {
      return state.notices;
    },
    notice(state) {
      return state.notice;
    },
    /* interestArea */
    fetchedInterestAreaList(state) {
      return state.interestAreaList;
    },
    fetchedDongName(state) {
      return state.dongname;
    },
    isCenterCheck(state) {
      return state.centerCheck;
    },
    fetchedPlacesInfo(state) {
      return state.placesInfo;
    },
    fetchedRemoveDong(state) {
      return state.removedDong;
    },
  },
  mutations: {
    /* user */
    SET_USER(state, data) {
      state.user = data;
      // state.userIdx = data.useridx;
      // state.userId = data.userid;
      // state.userName = data.username;
      // state.userEmail = data.useremail;
    },
    SET_USER_ID(state, id) {

      state.userId = id;
      console.log("set userId");
      console.log(state.userId);
    },
    SET_USER_NAME(state, name) {
      state.userName = name;
    },
    SET_USER_EMAIL(state, email) {
      state.userEmail = email;
    },
    SET_USER_AUTH_TOKEN(state, token) {
      state.userAuthToken = token;
    },
    SET_OFF_USER(state) {
      state.userIdx = '';
      state.userId = '';
      state.userName = '';
      state.userEmail = '';
      state.userAuthToken = '';
      storage.removeItem('jwt-auth-token');
      storage.removeItem('login_user');
      localstore.removeItem
      router.go(0);
    },
    GET_WISH_LIST(state, list) {
      state.userWishList = list;
    },
    /* map */
    SET_MAP(state, map) {
      state.map = map;
    },
    GET_APART_LIST(state, list) {
      state.aptList = list;
    },
    GET_APART_DETAIL(state, apt) {
      state.apt = apt;
    },
    SET_APART_DETAIL_PRICE(state, list) {
      state.aptPriceList = list;
    },
    SET_NO(state, no) {
      state.selNo = no;
    },
    SET_ADDR(state, addr) {
      state.selAddr = addr;
    },
    GET_SCHOOL_LIST(state, list) {
      state.schoolList = list;
    },
    GET_SUBWAY_LIST(state, list) {
      state.subwayList = list;
    },
    /* notice */
    SET_NOTICES(state, data) {
      state.notices = data;
    },
    READ_NOTICE(state, noticeRow) {
      state.notice = noticeRow;
    },
    REGIST_NOTICE(state, noticeRow) {
      noticeRow.articleno = state.notices.length + 1;
      state.notices.push(noticeRow);
    },
    UPDATE_NOTICE(state, noticeRow) {
      state.notices = state.notices.map((noticeItem) => {
        if (noticeItem.articleno == noticeRow.articleno) {
          return {
            username: noticeItem.username,
            subject: noticeRow.subject,
            articleno: noticeRow.articleno,
            content: noticeRow.content,
            views: noticeItem.views,
            regtime: noticeItem.regtime,
          };
        }
        return noticeItem;
      });
    },
    DELETE_NOTICE(state, noticeRow) {
      let idx = state.notices.indexOf(noticeRow);
      state.notices.splice(idx, 1);
    },
    GET_INTEREST_AREA_LIST(state, list) {
      state.interestAreaList = list;
    },
    SET_DONG_NAME(state, data) {
      state.dongname = data.dong;
      state.isCenterCheck = data.check;

      console.log('set 값 ', state.dongname);
    },
    SET_PLACES_INFO(state, places) {
      state.placesInfo = places;
    },
    REMOVE_DONG(state, dong) {
      state.removedDong = dong;
    },
  },
  actions: {
    /* user */
    fetchUser({ commit }, payload) {
      http
        .post('/user/login/', payload)
        .then((response) => {
          if (response.data == '') {
            alert('아이디와 비밀번호를 다시 확인해 주세요.');
          } else {
            console.log(response.data.userid);
            commit('SET_USER_ID', response.data.userid);
            console.log(response.data);
            commit('SET_USER', response.data.data);
            commit('SET_USER_AUTH_TOKEN', response.data.auth_token);
            storage.setItem('jwt-auth-token', response.headers['jwt-auth-token']);
            storage.setItem('login_user', JSON.stringify(response.data.data));
            // storage.setItem('login_user_idx', response.data.data.userid);
            alert('로그인 성공!');
            router.push('/');
            router.go(0);
          }
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    fetchOffUser({ commit }) {
      commit('SET_OFF_USER');
    },
    fetchUserInfo({ commit }, payload) {
      commit('SET_USER_NAME', payload.username);
      commit('SET_USER_EMAIL', payload.useremail);
    },
    /* map */
    fetchMap({ commit }, payload) {
      commit('SET_MAP', payload.map);
    },
    getApartList({ commit }, dong) {
      http
        .get('/housemap/' + dong)
        .then(({ data }) => {
          console.log(data);
          commit('GET_APART_LIST', data);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    getApartListFromLoc({ commit }, payload) {
      http
        .post('/housemap/', {
          ha: payload.bounds.ha,
          oa: payload.bounds.oa,
          qa: payload.bounds.qa,
          pa: payload.bounds.pa,
          year: payload.params.yearVal,
          minPrice: payload.params.priceVal[0],
          maxPrice: payload.params.priceVal[1],
          minArea: payload.params.areaVal[0],
          maxArea: payload.params.areaVal[1],
        })
        .then(({ data }) => {
          commit('GET_APART_LIST', data);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    getApartDetail({ commit }, payload) {
      http
        .get('/house/' + payload.no)
        .then(({ data }) => {
          console.log(data);
          let tmpList = [];
          for (let info of data.houses) {
            tmpList.push({
              계약일: info.dealYear - 2000 + '.' + info.dealMonth + '.' + info.dealDay,
              거래가격: info.dealAmount2,
              전용면적: Math.round(info.area * 0.3025) + '평',
              층수: info.floor + '층',
            });
          }
          commit('SET_APART_DETAIL_PRICE', tmpList);
          commit('SET_NO', payload.no);
          commit('SET_ADDR', payload.addrName);

          // 관심매물 여부 추가
          if (payload.userIdx != '') {
            http.get('/wish/' + payload.userIdx + '/' + payload.no).then((response) => {
              data.wish_img = response.data;
              commit('GET_APART_DETAIL', data);
            });
          } else {
            data.wish_img = 'unlike';
            commit('GET_APART_DETAIL', data);
          }
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    fetchWishList({ commit }, idx) {
      http.get('/wish/' + idx).then(({ data }) => {
        console.log(data);
        commit('GET_WISH_LIST', data);
      });
    },
    /* 아파트 주변 학군정보 */
    fetchSchoolList({ commit }, data) {
      let tmpList = [];

      for (let sch of data) {
        var tmp = sch.category_name;
        if (tmp.substring(tmp.length - 3) == '대학교') continue;
        tmpList.push({
          addr: sch.address_name,
          name: sch.place_name,
          distance: sch.distance,
        });
      }
      commit('GET_SCHOOL_LIST', tmpList);
    },
    fetchSubwayList({ commit }, data) {
      let tmpList = [];

      for (let sub of data) {
        tmpList.push({
          addr: sub.address_name,
          name: sub.place_name,
          distance: sub.distance,
        });
      }
      commit('GET_SUBWAY_LIST', tmpList);
    },
    /* notice */
    getNotices(context) {
      http
        .get('/notice')
        .then(({ data }) => {
          let tmpList = [];
          data.forEach((notice) => {
            tmpList.push({
              번호: notice.articleno,
              제목: notice.subject,
              작성자: notice.username,
              조회수: notice.views,
              등록일: notice.regtime,
            });
          });
          context.commit('SET_NOTICES', tmpList);
        })
        .catch(() => {
          alert('에러발생!');
        });
    },

    readNotice({ commit }, payload) {
      http
        .get(payload)
        .then(({ data }) => {
          console.log("공지사항 데이터 받아서 옴");
          console.log(data);
          localstore.getItem('noticedetail', data);
          localstore.setItem('nowNotice', data);
          localstore.setItem('articleno',data.articleno);
          localstore.setItem('subject',data.subject);
          localstore.setItem('username',data.username);
          localstore.setItem('views',data.views);
          localstore.setItem('regtime',data.regtime);
          localstore.setItem('articleno',data.articleno);
          localstore.setItem('content',data.content);
          commit('READ_NOTICE', data);
        })
        .catch(() => {
          alert('공지보기 에러 발생');
        });
    },
    /* interestarea */
    getInterestAreaList({ commit }, payload) {
      http
        .get('/interest/' + payload)
        .then(({ data }) => {
          console.log('리스트 갱신');
          console.log(data);
          // for (let i = 0; i < data.length; i++){
          //   list.
          // }
          commit('GET_INTEREST_AREA_LIST', data);
        })
        .catch(() => {
          alert('리스트보기 에러 발생');
        });
    },
    // 동 이름으로 중심좌표 찾기 위한 사전 작업
    centerDong({ commit }, payload) {
      commit('SET_DONG_NAME', payload);
    },

    // 반경 내 장소 정보 받기
    getPlacesInfo({ commit }, payload) {
      commit('SET_PLACES_INFO', payload);
    },
    removedDong({ commit }, payload) {
      commit('REMOVE_DONG', payload);
    },
  },

  // plugins: [createPersistedState()],
});


export default store;
