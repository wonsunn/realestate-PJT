(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-895be9de"],{1204:function(e,s,r){"use strict";r("1f1e")},"1f1e":function(e,s,r){},"507f":function(e,s,r){e.exports=r.p+"img/joinimg.7d01cfe7.png"},"73cf":function(e,s,r){"use strict";r.r(s);var t=function(){var e=this,s=e.$createElement,r=e._self._c||s;return r("div",{},[r("div",{staticClass:"shadow-lg entire-join-container"},[r("div",{staticClass:"h100"},[r("div",{staticClass:"row h100"},[e._m(0),r("div",{staticClass:"col-lg-7"},[r("div",{staticStyle:{padding:"20% 6%"}},[e._m(1),r("b-form-group",{staticClass:"mb-3",attrs:{id:"userid-group",label:"아이디","label-for":"userid"}},[r("b-form-input",{ref:"userid",attrs:{type:"text",id:"userid",name:"userid",placeholder:"아이디를 입력하세요",valid:e.possiId,required:""},on:{focusout:function(s){return e.idCheck()},keyup:function(s){return e.idCheck()}},model:{value:e.user.userid,callback:function(s){e.$set(e.user,"userid",s)},expression:"user.userid"}}),e.possiId&&this.idState?r("div",{staticClass:"valid-feedback"},[e._v(" 가능한 아이디입니다. ")]):r("div",{staticClass:"invalid-feedback"},[e._v("중복된 아이디입니다.")])],1),r("b-form-group",{staticClass:"mb-3",attrs:{id:"username-group",label:"이름","label-for":"username"}},[r("b-form-input",{ref:"username",attrs:{type:"text",id:"username",name:"username",placeholder:"이름을 입력하세요",required:""},model:{value:e.user.username,callback:function(s){e.$set(e.user,"username",s)},expression:"user.username"}})],1),r("b-form-group",{staticClass:"mb-3",attrs:{id:"userpwd-group",label:"비밀번호","label-for":"userpwd"}},[r("b-form-input",{ref:"userpwd",attrs:{type:"password",id:"userpwd",name:"userpwd",placeholder:"비밀번호를 입력하세요",required:""},model:{value:e.user.userpwd,callback:function(s){e.$set(e.user,"userpwd",s)},expression:"user.userpwd"}})],1),r("b-form-group",{staticClass:"mb-3",attrs:{id:"useremail-group",label:"이메일","label-for":"useremail"}},[r("b-form-input",{ref:"useremail",attrs:{type:"email",id:"useremail",name:"useremail",placeholder:"이메일을 입력하세요",required:""},model:{value:e.user.useremail,callback:function(s){e.$set(e.user,"useremail",s)},expression:"user.useremail"}})],1),r("hr"),r("button",{staticClass:"btn btn-primary",staticStyle:{"background-color":"#896872","border-color":"#896872"},attrs:{id:"join"},on:{click:e.checkJoin}},[e._v(" 가입하기 ")]),r("router-link",{staticClass:"btn btn-secondary",staticStyle:{"margin-left":"10px"},attrs:{to:"/index.html"}},[e._v(" 뒤로가기 ")])],1)])])])])])},i=[function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("div",{staticClass:"col-lg-5 d-none d-lg-block bg-register-image h100"},[t("img",{staticClass:"rounded",attrs:{src:r("507f"),width:"100%",height:"100%"}})])},function(){var e=this,s=e.$createElement,r=e._self._c||s;return r("div",{staticClass:"text-center"},[r("h1",{staticClass:"h4 text-gray-900 mb-4",staticStyle:{"text-align":"left",color:"#125970"}},[e._v(" 회원가입 ")])])}],a=r("c21c"),u={name:"Register",data:function(){return{user:{userid:"",username:"",userpwd:"",useremail:""},possiId:!1,desc:""}},computed:{idState:function(){return this.possiId}},methods:{checkJoin:function(){console.log("cehck");var e=!0,s="";!this.user.userid&&(s="아이디를 입력해주세요",e=!1,this.$refs.user.userid.focus()),e&&!this.user.username&&(s="이름을 입력해주세요",e=!1,this.$refs.user.username.focus()),e&&!this.user.userpwd&&(s="비밀번호를 입력해주세요",e=!1,this.$refs.user.userpwd.focus()),e&&!this.user.useremail&&(s="이메일을 입력해주세요",e=!1,this.$refs.user.useremail.focus()),e?this.doJoin():alert(s)},doJoin:function(){var e=this;console.log("dfdfdfdf"),a["a"].post("/user/",this.user).then((function(){alert("회원가입이 완료되었습니다."),e.$router.push("/login")})).catch((function(e){console.dir(e)}))},idCheck:function(){var e=this,s=document.getElementById("userid-group");""!=this.user.userid&&(s.classList.remove("was-validated"),a["a"].get("/user/check/"+this.user.userid).then((function(r){var t=r.data;"NO"==t?(e.possiId=!1,e.user.userid=""):e.possiId=!0,s.classList.add("was-validated")})))}}},n=u,o=(r("1204"),r("2877")),l=Object(o["a"])(n,t,i,!1,null,"e6ef670a",null);s["default"]=l.exports}}]);
//# sourceMappingURL=chunk-895be9de.682c5636.js.map