import router from './router'
import store from './store'
import getPageTitle from '@/utils/get-page-title'

import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'
import {getAccessToken} from "@/utils/auth"; // progress bar style

NProgress.configure({showSpinner: false}) // NProgress Configuration

router.beforeEach(async (to, from, next) => {
    // start progress bar
    NProgress.start()
    // set page title
    document.title = getPageTitle(to.meta.title)
    // determine whether the user has logged in
    const hasToken = getAccessToken()

	//已经登录
    if (hasToken) {
		console.log(hasToken)
        if (to.path === '/login') {
            // 登录，跳转首页
            next({path: '/'})
            NProgress.done()
        } else {
            // 获取用户信息
            await store.dispatch('user/getInfo')
            next()
        }
    } 
	//未登录但是所请求的资源不需要权限
	else if (!to.meta.requireAuth)
    {
        next()
    }
	//未登录且请求需要权限，跳转到登陆页面
    else {
        next('/login')
    }
})

router.afterEach(() => {
    // finish progress bar
    NProgress.done()
})
