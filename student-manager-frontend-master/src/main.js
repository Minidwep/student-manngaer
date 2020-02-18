import Vue from 'vue';
import axios from 'axios';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {AppConfig} from './app.config';
import store from './store';

axios.interceptors.response.use(undefined, (error) => {
  console.log("Error: " + error.response.status);
  if(error.response.status === 401){
    //全局登录过滤，当判读token失效或者没有登录时 返回登录页面
    router.replace({
        path: '/login',
        query: {redirect: router.currentRoute.fullPath}
    })
}
  return Promise.reject(error);
});

Vue.use(ElementUI);

Vue.config.productionTip = false;
Vue.prototype.$axios = axios;
Vue.prototype.$appConfig = AppConfig;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
