import request from '@/utils/request'

export function getBloggerInfo(id) {
	return request({
		url: '/business/bloggerInfo/findByBloggerId',
		method: 'get',
		params: {
			id: id
		}
	})
}

export function addInfo(bloggerInfo) {
  return request({
    url: '/business/bloggerInfo/add',
    method: 'post',
    params: {
      bloggerInfo: bloggerInfo
    }
  })
}

export function updateInfo(bloggerInfo) {
  return request({
    url: '/business/bloggerInfo/update',
    method: 'post',
    params: {
      bloggerInfo: bloggerInfo
    }
  })
}

export function deleteInfo(bloggerInfo) {
  return request({
    url: '/business/bloggerInfo/delete',
    method: 'post',
    params: {
      bloggerInfo: bloggerInfo
    }
  })
}

export function addFavoriteCategories(cids){
	return request({
		url: '/business/bloggerInfo/addCategories',
		method: 'post',
		params: {
			ids: cids
		}
	})
}

export function removeFavoriteCategories(cids){
	return request({
		url: '/business/bloggerInfo/removeCategories',
		method: 'post',
		params: {
			ids: cids
		}
	})
}

export function addFavoriteBlogs(bids){
	return request({
		url: '/business/bloggerInfo/addBlogs',
		method: 'post',
		params: {
			ids: bids
		}
	})
}

export function removeFavoriteBlogs(bids){
	return request({
		url: '/business/bloggerInfo/removeBlogs',
		method: 'post',
		params: {
			ids: bids
		}
	})
}

export function addFavoriteBloggers(bgids){
	return request({
		url: '/business/bloggerInfo/addBloggers',
		method: 'post',
		params: {
			ids: bgids
		}
	})
}

export function removeFavoriteBloggers(bgids){
	return request({
		url: '/business/bloggerInfo/removeBloggers',
		method: 'post',
		params: {
			ids: bgids
		}
	})
}