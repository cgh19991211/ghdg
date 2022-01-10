import request from '@/utils/request'

// 关注
export function follow(id) {
  return request(({
    url: `/business/bloggerInfo/addBloggers`,
    method: 'post',
	params: {
		ids: id
	}
  }))
}

// 取消关注
export function unFollow(id) {
  return request(({
    url: `/business/bloggerInfo/removeBloggers`,
    method: 'post',
	params: {
		ids :ids
	}
  }))
}

// 验证是否关注
export function hasFollow(bloggerId) {
  return request(({
    url: `/business/bloggerInfo/isFallowed`,
    method: 'get',
	params: {
		id: bloggerId
	}
  }))
}
