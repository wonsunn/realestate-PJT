const state = {
  articleno: '',
  userid: '',
  subject: '',
  views: '',
  regtime: '',
  content: '',
};

const getters = {
  fetchedArticleno: state => state.articleno,
  fetchedUserid: state => state.userid,
  fetchedSubject: state => state.subject,
  fetchedViews: state => state.views,
  fetchedRegtime: state => state.regtime,
  fetchedContent: state => state.content,
};

const mutations = {
  SET_ARTICLENO(state, articleno) {
    state.articleno = articleno;
  },
  SET_USERID(state, userid) {
    state.userid = userid;
  },
  SET_SUBJECT(state, subject) {
    state.subject = subject;
  },
  SET_VIEWS(state, views) {
    state.views = views;
  },
  SET_REGTIME(state, regtime) {
    state.regtime = regtime;
  },
  SET_CONTENT(state, content) {
    state.content = content;
  },
    
}

const actions = {
  get_articleno({ commit}, data) {
    commit("SET_ARTICLENO", data);
  },
  get_userid({ commit}, data) {
    commit("SET_USERID", data);
  },
  get_subject({ commit}, data) {
    commit("SET_SUBJECT", data);
  },
  get_views({ commit}, data) {
    commit("SET_VIEWS", data);
  },
  get_regtime({ commit}, data) {
    commit("SET_REGTIME", data);
  },
  get_content({ commit}, data) {
    commit("SET_CONTENT", data);
  },
}


export default {
  strict: process.env.NODE_ENV !== "production",
  state: {
    ...state
  },
  getters,
  mutations,
  actions
}