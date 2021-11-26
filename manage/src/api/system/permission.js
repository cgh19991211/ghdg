import request from '@/utils/request'

export function getList(params) {
  return request({
    url: 'sys/permission/list',
    method: 'get',
    params
  })
}

export function save(params) {
  return request({
    url: 'sys/permission/save',
    method: 'post',
    params
  })
}

export function remove(id) {
  return request({
    url: 'sys/permission/delete',
    method: 'get',
    params: {
      id: id
    }
  })
}

export function getMenuTree() {
  return request({
    url: 'sys/menu/tree',
    method: 'get'
  })
}