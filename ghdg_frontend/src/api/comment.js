import request from '@/utils/request'

export function fetchCommentsByTopicId(topic_Id) {
  return request({
    url: '/business/comment/findByBlogId',
    method: 'get',
    params: {
      id: topic_Id
    }
  })
}

export function fetchCommentsByBloggerId(blogger_Id) {
  return request({
    url: '/business/comment/findByBloggerId',
    method: 'get',
    params: {
      id: blogger_Id
    }
  })
}

export function pushComment(data) {
  return request({
    url: '/business/comment/add',
    method: 'post',
    params: data
  })
}

export function deleteComment(data){
	return request({
		url: '/business/comment/delete',
		method: 'post',
		params: data
	})
}

