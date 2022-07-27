import { createRouter, createWebHistory } from 'vue-router';
import Layout from '../view/Layout.vue'
import { getToken } from '../utils/auth.js'

const routes = [
  {
    path: '/',
    name: 'layout',
    component: Layout,
    redirect: '/user',
    children: [
      {
        path: 'user',
        name: 'user',
        component: () => import('@/view/User.vue')
      },
      {
        path: 'classify',
        name: 'classify',
        component: () => import('@/view/Classify.vue')
      },
      {
        path: 'book',
        name: 'book',
        component: () => import('@/view/Book.vue')
      },
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/view/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/view/Register.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由跳转前一定会走这里
router.beforeEach(async (to, from, next) => {
  const auth = getToken()
  if (auth) {
    // 已登录的话，不能再去登录页
    if (to.path === '/login') {
      return next('/')
    } else {
      next()
    }
  } else {
    // 没登陆的话，不在登录页强制跳转登录页
    let whiteList = ['/login', '/register']
    if (whiteList.includes(to.path)) {
      next()
    } else {
      return next('/login')
    }
  }
})

export default router
