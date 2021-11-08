import request from '@/utils/request'

export function login(username, password) {
  console.log("请求工具类中的login")
  return request({
    url: '/login',
    method: 'post',
    params: {
      "username": username,
      "password": password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/curUser',
    method: 'get'
  })
}

export function logout() {
  console.log('logout')
  return request({
    url: '/logout',
    method: 'post'
  })
}

export function updatePwd(params) {
  return request({
    url: '/account/updatePwd',
    method: 'post',
    params
  })
}
