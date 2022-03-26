import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import SystemInformation from '@/components/SystemInformation'
import LoginPage from '@/components/LoginPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage
    },
    {
      path: '/systemInformation',
      name: 'SystemInformation',
      component: SystemInformation
    }
  ]
})
