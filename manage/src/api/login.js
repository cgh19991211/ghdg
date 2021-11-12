import request from '@/utils/request'

export function login(username, password) {
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
    method: 'get'
  })
}

export function updatePwd(params) {
  return request({
    url: '/account/updatePwd',
    method: 'post',
    params
  })
}

export function refreshToken(refreshToken){
  console.log("refresh token")
  return request({
    url: '/refreshToken',
    method: 'get',
    params: refreshToken
  })
}
