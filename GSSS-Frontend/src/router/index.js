import Vue from 'vue'
import Router from 'vue-router'
import Owner_ViewPurchases from '@/components/Owner_ViewPurchases'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Owner_ViewPurchases',
      component: Owner_ViewPurchases
    },
  ]
})
