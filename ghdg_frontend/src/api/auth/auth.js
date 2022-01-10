import request from '@/utils/request'

// 注册
export function userRegister(account,password) {
  return request({
    url: '/blogger/login/signIn',
    method: 'post',
    params: {
		account: account,
		password: password
	}
  })
}

//TODO：注销  如果以后使用了验证码之类的验证流程的话，注销需要验证码确认
export function bloggerSignOut(bloggerId){
	return request({
		url: '/blogger/login/signOut',
		method: 'post',
		params: {
			bloggerId: bloggerId
		}
	})
}
// 前台用户登录
export function login(account,password) {
  return request({
    url: '/blogger/login/login',
    method: 'post',
    params: {
		account: account,
		password: password
	}
  })
}
// 登录后获取前台用户信息
export function getUserInfo() {
  return request({
    url: '/blogger/login/curBlogger',
    method: 'get'
  })
}
// 前台用户登出
export function logout() {
  return request({
    url: '/blogger/login/logout'
  })
}