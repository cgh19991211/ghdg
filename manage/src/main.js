import Vue from 'vue'
import Cookies from 'js-cookie'
import ECharts from 'vue-echarts/components/ECharts.vue'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import 'default-passive-events'//页面滚动优化
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'
// import locale from 'element-ui/lib/locale/lang/en' // lang i18n
import i18n from './lang' // Internationalization

import '@/icons' // icon
// permission control
import './permission'

Vue.use(ElementUI, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
})
Vue.component('v-chart', ECharts)
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,//加载了constantRouterMap
  store,
  i18n,
  render: h => h(App)//将App.vue渲染到index.html中
})
