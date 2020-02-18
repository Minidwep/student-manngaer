import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Login from './views/Login.vue'
import ManagementHome from './components/ManagementHome.vue'
import Staff from './components/Staff.vue'
import Student from './components/Student.vue'
import Subject from './components/Subject.vue'
import store from './store'

Vue.use(Router)

// 页面刷新时，重新赋值token  
if (sessionStorage.getItem('token')) {  
  store.commit('setToken', sessionStorage.getItem('token'));
}  

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  linkActiveClass: 'active',
  routes: [    
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/',
      name: 'home',
      meta: {
        requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
      },
      component: Home,
      children: [
        { path: '', name: 'managementHome', component: ManagementHome },
        { path: 'students', name: 'students', component: Student },
        { path: 'staff', name: 'staff', component: Staff },
        { path: 'subjects', name: 'subjects', component: Subject }
      ]
    }
  ],
});

router.beforeEach((to, from, next) => {  
  if (to.matched.some(r => r.meta.requireAuth)) {           //这里的requireAuth为路由中定义的 meta:{requireAuth:true}，意思为：该路由添加该字段，表示进入该路由需要登陆的  
    if (store.state.token) {  
      next();  
    }  
    else {  
      next({  
        path: '/login',  
        query: {redirect: to.fullPath}  
      })  
    }  
  }  
  else {  
    next();  
  }  
});

export default router;
