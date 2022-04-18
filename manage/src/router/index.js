import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
 * 路由权限数据
 *
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/
export const constantRouterMap = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/',//路由的路径
    component: Layout,//路由映射的组件，  这里的layout作为父组件
    redirect: '/dashboard',//重定向路径
    name: 'dashboard',
    hidden: false,
    //children是子组件，子组件会全部渲染到父组件中     此处既是二级路由
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: 'dashboard', icon: 'dashboard', noCache: true}
    },
      {
        path: '/account/profile',
        name: '个人资料',
        component: () => import('@/views/account/profile.vue'),
        hidden: true,
        meta: {title: '个人资料'}

      },
      {
        path: '/account/timeline',
        name: '最近活动',
        component: () => import('@/views/account/timeline.vue'),
        hidden: true,
        meta: {title: '最近活动'}

      },
      {
        path: '/account/updatePwd',
        name: '修改密码',
        component: () => import('@/views/account/updatePwd.vue'),
        hidden: true,
        meta: {title: '修改密码'}

      }
    ]
  },
  //404一定要在最后再加载
  {path: '/404', component: () => import('@/views/404'), hidden: true}
]
const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

const router = createRouter()

export default router

export const asyncRouterMap = [
  {
    path: '/business',
    component: Layout,
    redirect: '#',
    name: 'Bussiness',
    alwaysShow: true,
    meta: {
      roles: ['administrator','group_admin'],
      title: 'businessMgr',
      icon: 'shopping'
    },
    children: [

      {
        // path: 'sdetail',
        // name: 'sdetail',
        // component: () => import('@/views/business/sdetail/index'),
        // meta: {title: 'sdetail'}
        path: '/blogger',
        name: 'Blogger',
        component: () => import('@/views/business/blogger/index'),
        meta: {
          title: 'bloggerMgr'
        }
      },
      {
        path: '/blog',
        name: 'Blog',
        component: () => import('@/views/business/blog/index'),
        meta:{
          title: 'blogMgr'
        }
      },
      {
        path: '/category',
        name: 'Category',
        component: () => import('@/views/business/category/index'),
        meta: {
          title: 'categoryMgr'
        }
      },
      {
        path: '/label',
        name: 'Label',
        component: ()=>import('@/views/business/label/index'),
        meta: {
          title: 'labelMgr'
        }
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '#',
    name: 'System',
    alwaysShow: true,
    meta: {
      roles: ['administrator'],
      title: 'systemMgr',
      icon: 'edit'
    },
    children: [
      {
        path: '/menu',
        name: 'Menu',
        component: () => import('@/views/system/menu/index'),
        meta: {
          title: 'menuMgr'
        }
      },
      //部门
      // {
      //   path: 'dept',
      //   name: 'Department',
      //   component: () => import('@/views/system/dept/index'),
      //   meta: {
      //     title: 'deptMgr'
      //   }
      // },
      //用户管理
      {
        path: 'user',
        name: 'Account',
        component: () => import('@/views/system/user/index'),
        meta: {title: 'userMgr'}
      },
      //角色管理
      {
        path: 'role',
        name: 'roleMgr',
        component: () => import('@/views/system/role/index'),
        meta: {title: 'roleMgr'}
      },
      //权限管理
      {
        path: 'permission',
        name: 'permissionMgr',
        component: () => import('@/views/system/permission/index'),
        meta: {title: 'permissionMgr'}
      },
      // {
      //   path: 'task',
      //   name: 'Task',
      //   component: () => import('@/views/system/task/index'),
      //   meta: {title: 'taskMgr'}
      // },
      // {
      //   path: 'taskLog',
      //   name: 'taskLog',
      //   component: () => import('@/views/system/task/taskLog.vue'),
      //   hidden: true,
      //   meta: {title: 'taskLog'}

      // },
      // {
      //   path: 'dict',
      //   name: 'Dict',
      //   component: () => import('@/views/system/dict/index'),
      //   meta: {title: 'dictMgr'}
      // },
      // {
      //   path: 'cfg',
      //   name: 'Config',
      //   component: () => import('@/views/system/cfg/index'),
      //   meta: {
      //     title: 'configMgr'
      //   }
      // }
    ]
  },

  {
    path: '/optionMgr',
    component: Layout,
    redirect: '#',
    name: 'optionMgr',
    alwaysShow: true,
    meta: {
      roles: ['administrator', 'developer'],
      title: 'optionMgr',
      icon: 'example'
    },
    children: [
      {
        path: 'druid',
        name: 'druid',
        component: () => import('@/views/operation/druid/index'),
        meta: {title: 'druid'}
      },
      {
        path: 'swagger',
        name: 'swagger',
        component: () => import('@/views/operation/api/index'),
        meta: {title: 'swagger'}
      },
      {
        path: 'loginLog',
        name: 'Login Log',
        component: () => import('@/views/system/loginLog/index'),
        meta: {title: 'loginLog'}
      },
      {
        path: 'log',
        name: 'Bussiness Log',
        component: () => import('@/views/system/log/index'),
        meta: {title: 'bussinessLog'}
      }
    ]
  },
  {
    path: '/message',
    component: Layout,
    redirect: '#',
    name: 'messageMgr',
    alwaysShow: true,
    meta: {
      roles: ['administrator', 'developer'],
      title: 'messageMgr',
      icon: 'message'
    },
    children: [
      {
        path: 'history',
        name: 'message',
        component: () => import('@/views/message/message/index'),
        meta: {title: 'historyMessage'}
      },
      {
        path: 'template',
        name: 'template',
        component: () => import('@/views/message/template/index'),
        meta: {title: 'messageTemplate'}
      },
      {
        path: 'sender',
        name: 'Message Sender',
        component: () => import('@/views/message/sender/index'),
        meta: {title: 'messageSender'}
      }
    ]
  },
  {path: '/404', component: () => import('@/views/404'), hidden: true}
]
