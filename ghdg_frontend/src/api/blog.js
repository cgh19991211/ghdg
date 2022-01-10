import request from '@/utils/request'

export function getBlogsById(id){
	return request({
		url: '/business/blog/findByBloggerId',
		method: 'get',
		params: {
			blogger_id: id
		}
	})
}

export function getBlogsByCategory(category){
	return request({
		url: '/business/blog/findByCategory',
		method: 'get',
		params: {
			category: category
		}
	})
}

export function getBlogsByLabel(labelId){
	return request({
		url: '/business/blog/findByLabel',
		method: 'get',
		params: {
			labelId: labelId
		}
	})
}

export function addBlog(blog){
	return request({
		url: '/business/blog/add',
		method: 'post',
		params: {
			blog: blog
		}
	})
}

export function deleteBlog(blog){
	return request({
		url: '/business/blog/delete',
		method: 'post',
		params: {
			blog: blog
		}
	})
}

export function addLabels(blogId, labelIds){
	return request({
		url: '/business/blog/addLabels',
		method: 'post',
		params: {
			blogId: blogId,
			ids: labelIds
		}
	})
}

export function removeLabels(blogId, labelIds){
	return request({
		url: '/business/blog/removeLabels',
		method: 'post',
		params: {
			blogId: blogId,
			ids: labelIds
		}
	})
}



