import request from '@/utils/request'

export function getList() {
  return request({
    url: 'sys/menu/list',
    method: 'get'
  })
}

export function save(params) {
  return request({
    url: 'sys/menu/save',
    method: 'post',
    params: params
  })
}

export function delMenu(id) {
  return request({
    url: 'sys/menu/delete',
    method: 'post',
    params: {
      id: id
    }
  })
}
export function menuTreeListByRoleId(roleId) {
  return request({
    url: 'sys/menu/menuTree4Role',
    method: 'get',
    params: {
      roleId: roleId
    }
  })
}
