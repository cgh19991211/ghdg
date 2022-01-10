import request from '@/utils/request'

// 列表
export function getList(current, size, tab) {
  return request({
    url: '/business/blog/queryPage',
    method: 'get',
    params: { current: current, size: size, tab: tab }
  })
}

// 发布
export function post(topic) {
  return request({
    url: '/business/blog/add',
    method: 'post',
    data: topic
  })
}

// 浏览
export function getTopic(id) {
  return request({
    url: `/business/blog/findByBlogId`,
    method: 'get',
    params: {
      id: id
    }
  })
}
// 获取详情页推荐
export function getRecommendTopics(id,blogId) {
  return request({
    url: '/business/blog/recommend',
    method: 'get',
    params: {
      id: id,
	  blogId: blogId
    }
  })
}

export function update(topic) {
  return request({
    url: '/business/blog/add',
    method: 'post',
    data: topic
  })
}

export function deleteTopic(id) {
  return request({
    url: `/business/blog/delete`,
    method: 'post',
	params: {
		
	}
  })
}

