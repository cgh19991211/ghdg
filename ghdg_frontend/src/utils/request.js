import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getAccessToken,getRefreshToken } from '@/utils/auth'

// 1.创建axios实例
const service = axios.create({
  // 公共接口--这里注意后面会讲,url = base url + request url
  baseURL: process.env.VUE_APP_SERVER_URL,
  // baseURL: 'https://api.example.com',
  // 超时时间 单位是ms，这里设置了5s的超时时间
  timeout: 5 * 1000
})

// 2.请求拦截器request interceptor
service.interceptors.request.use(
  config => {
    // 发请求前做的一些处理，数据转化，配置请求头，设置token,设置loading等，根据需求去添加
    // 注意使用token的时候需要引入cookie方法或者用本地localStorage等方法，推荐js-cookie
	// console.log("store.getters.BloggerAccessToken")
	// console.log(store.getters.BloggerAccessToken)
    if (store.getters.BloggerAccessToken) {//每个请求都加上，如果没有的话后台中需要登陆授权的接口就无法访问，会被Shiro过滤
      // config.params = {'token': token}    // 如果要求携带在参数中
      // config.headers.token = token;       // 如果要求携带在请求头中
      // bearer：w3c规范
      config.headers['BloggerAccessToken'] = getAccessToken()
    }
    return config
  },
  error => {
    // do something with request error
    // console.log(error) // for debug
    return Promise.reject(error)
  }
)

// 设置cross跨域 并设置访问权限 允许跨域携带cookie信息,使用JWT可关闭
service.defaults.withCredentials = true

service.interceptors.response.use(
  // 接收到响应数据并成功后的一些共有的处理，关闭loading等
  response => {
    const res = response.data
	// console.log(response)
    // 如果自定义代码不是200，则将其判断为错误。
    if (res.code !== 200) {
      // 50000: 非法Token; 50015: 刷新token过期
      if (res.code === 401 || res.code === 50000 || res.code===50015) {
        // 重新登录
        MessageBox.confirm('会话失效，您可以留在当前页面，或重新登录', '权限不足', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          window.location.href = '#/login'
        })
      } else if(res.code === 50014){
		  //AccessToken过期
		  //TODO:发送刷新token请求
		  
		  
	  }
	  else { // 其他异常直接提示
        Message({
          showClose: true,
          message: '⚠' + res.message || 'Error',
          type: 'error',
          duration: 3 * 1000
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    /** *** 接收到异常响应的处理开始 *****/
    // console.log('err' + error) // for debug
    Message({
      showClose: true,
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)
export default service