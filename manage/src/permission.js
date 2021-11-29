import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import { Message } from 'element-ui'
import { getAccessToken } from '@/utils/auth' // 验权

const whiteList = ['/login'] // 不重定向白名单

// permission judge function
function hasPermission(roles, permissionRoles) {
  // console.log("hasPermission")
  // console.log(roles)
  if (roles.indexOf('"administrator"') >= 0) return true // admin permission passed directly
  if (!permissionRoles) return true
  return roles.some(role => permissionRoles.indexOf(role) >= 0)
}

/**
 * 路由拦截器
 */
router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getAccessToken()) {
    if (to.path === '/login'||to.path === '/') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      // console.log('路由拦截器 to from next')
      // console.log(to)
      // console.log(from)
      // console.log(next)
      if (store.getters.roles.length === 0) {//判断当前用户是否已拉取完用户信息
        store.dispatch('GetInfo').then(res => { // 拉取用户信息
          //原始的菜单列表数据
          const menus = res.data.menus
          //后台构造好的路由
          const routers = res.data.routers
          //角色列表
          const roles = res.data.roles
          //根据后台返回的路由信息调用GenerateRoutes方法生成路由表
          store.dispatch('GenerateRoutes', { roles:roles,routers:routers,menus:menus }).then(() => { // 根据roles权限生成可访问的路由表
            // 添加可访问路由表
            router.addRoutes(store.getters.addRouters)
            console.log('router')
            console.log(router)
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
          })
          // next()
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            // Message.error(err || 'Verification failed, please login again')
            next({ path: '/' })
          })
        })
      } else {
        // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
        if (hasPermission(store.getters.roles, to.meta.roles)) {
          next()
        } else {
          console.log(401)
          next({ path: '/401', replace: true, query: { noGoBack: true }})
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next('/login')
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
