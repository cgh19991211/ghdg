import request from '@/utils/request'

export function getList(params) {
  return request({
    url: 'sys/user/list',
    method: 'get',
    params
  })
}

export function saveUser(params) {
  return request({
    url: 'sys/user/save',
    method: 'post',
    params
  })
}

export function modifyUser(params) {
  return request({
    url: 'sys/user/modifyInfo',
    method: 'post',
    params
  })
}

export function remove(userId) {
  return request({
    url: 'sys/user/delete'+'/'+ userId,
    method: 'post',
    params: {

    }
  })
}

export function setRole(params) {
  return request({
    url: 'sys/user/setRoles',
    method: 'post',
    params
  })
}

export function userDeleteRoles(userId,roleIds){
  return requst({
    url: 'sus/user/deleteRoles',
    method: 'post',
    params: {
      id: userId,
      roleIds: roleIds
    }
  })
}
