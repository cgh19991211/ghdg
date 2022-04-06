import request from '@/utils/request'

// 用户主页
export function getInfoByName(username) {
  return request({
    url: '/business/bloggerInfo/findByName',
    method: 'get',
    params: {
	  name: username
    }
  })
}
// 用户主页
export function getInfo() {
  return request({
    url: '/blogger/login/curBlogger',
    method: 'get'
  })
}
// 更新
export function update(user) {
  return request({
    url: '/business/bloggerInfo/update',
    method: 'post',
    data: user
  })
}


