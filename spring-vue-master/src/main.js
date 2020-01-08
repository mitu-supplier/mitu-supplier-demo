import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios';
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
import { messages } from './components/common/i18n';
//import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
import './assets/css/theme-green/index.css';  // 浅绿色主题
import './assets/css/icon.css';
import './components/common/directives';
import "babel-polyfill";
import '../zTree_v3/js/jquery-1.4.4.min.js';
import '../zTree_v3/css/zTreeStyle/zTreeStyle.css';
import '../zTree_v3/js/jquery.ztree.core.js';
import '../zTree_v3/js/jquery.ztree.excheck.js';
import '../zTree_v3/js/jquery.ztree.exedit.js';
import http from '@/utils/request.js';
import qs from 'qs'
Vue.prototype.$qs = qs;


Vue.config.productionTip = false
Vue.use(VueI18n);
Vue.use(http);
Vue.use(ElementUI, {
    size: 'small'
});
Vue.prototype.$axios = axios;

const i18n = new VueI18n({
    locale: 'zh',
    messages
})

axios.interceptors.request.use(
    config => {
      let url = config.url;
      if(url.indexOf("login/do")==-1){
        const token = localStorage.getItem('forestToken');
        if(token){
            config.headers.token = token;
        }
      }
      return config;
    },
    err => {
      return Promise.reject(err);
    });
 
axios.interceptors.response.use( response => {
    return response;
   },
   error => {
    if(error.response.status==503){
        Vue.prototype.$message(error.response.data);
        setTimeout(() => {                    
            router.replace({                   
                path: "/login",                    
                query: {                
                  redirect: router.currentRoute.fullPath               
                }                    
              });                   
            }, 1000);
       
    }
    
   }
)

//使用钩子函数对路由进行权限跳转
 router.beforeEach((to, from, next) => {
    if(to.path !== "/registed"){
      localStorage.removeItem('newActive');
      localStorage.removeItem('newUserId');
      localStorage.removeItem('newSupplierId');
    }
     const role = localStorage.getItem('forestToken');
     if (!role && to.path.indexOf("/login") ==-1 && to.path.indexOf("/registed") ==-1 && to.path.indexOf("/toexamine") ==-1) {
         next('/login');
     } else {
         next();
     }
    
 })


new Vue({
    router,
    i18n,
    render: h => h(App)
}).$mount('#app')