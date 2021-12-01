import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '../store'
import router from '../router'
import { getAccessToken, getRefreshToken,setAccessToken,setRefreshToken,removeToken } from '@/utils/auth'
import {refreshToken} from "../api/login";
import path from "path";
// axios.defaults.withCredentials=true
// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 10000

})

// request拦截器 给每一个请求加上token，但是登陆请求是没有token的。
service.interceptors.request.use(
  config => {
    var token = getAccessToken()
    if (token) {
      config.headers['AccessToken'] = token // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    // config.headers.common['Content-Type'] = 'application/x-www-form-urlencoded'
    // config.data = true
    return config
  },
  error => {
    // Do something with request error
    console.log('error', error) // for debug
    Promise.reject(error)
  }
)

// respone拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非200是抛错 可结合自己业务进行修改
     */
    const res = response.data
    if (res.code !== 200) {
      // 50000:无效的token; 50014:请求token过期;  50015:刷新Token过期了;
      console.log(res.code)
      if(res.code === 50014){
        refreshToken(getRefreshToken()).then(response => {
          let data = response.data
          setAccessToken(data.AccessToken)
          setRefreshToken(data.RefreshToken)
          //成功刷新token就重新加载页面
          location.reload()
        })
        //失败会返回AccessToken过期码
          // .catch(error => {
          //   //刷新失败则跳转到登陆页面
          //   store.dispatch('LogOut').then(() => {
          //     location.reload()
          //   })
          //   // this.$router.push({path:'/'})
          // })
      }
      if ( res.code === 50015) {//刷新token也过期了
        MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
          confirmButtonText: 'Re-Login',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          removeToken()
          //TODO:返回登陆页面
          redirectLogin()
        })
      }
      if(res.code === 50000){//token非法

      }


      return Promise.reject(res.message)
    } else {
      return response.data
    }
  },
  error => {

    //debug
    if (error.response && error.response.data.errors) {
      Message({
        message: error.response.data.errors[0].defaultMessage,
        type: 'error',
        duration: 5 * 1000
      })
    } else {
      if (error.response && error.response.data.message) {
        Message({
          message: error.response.data.message,
          type: 'error',
          duration: 5 * 1000
        })
      } else {
        Message({
          message: error.message,
          type: 'error',
          duration: 5 * 1000
        })
      }
    }
    return Promise.reject(error)
  }
)

export default service

function redirectLogin () {
  // router.currentRoute 当前路由对象，和你在组件中访问的 this.$route 是同一个东西
  // query 参数的数据格式就是：?key=value&key=value
  //TODO:重定向到登陆页面
  console.log(router)
  console.log(router.currentRoute.fullPath)
  router.push('/login')
}
