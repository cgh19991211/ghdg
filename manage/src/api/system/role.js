import request from '@/utils/request'

export function getList(params) {
  return request({
    url: 'sys/role/list',
    method: 'get',
    params
  })
}


export function save(params) {
  return request({
    url: 'sys/role',
    method: 'post',
    params
  })
}

export function remove(roleId) {
  return request({
    url: 'sys/role',
    method: 'delete',
    params: {
      roleId: roleId
    }
  })
}

/**
 * 返回角色根列表，每一行里面包括：id,pid,name,children,checked
 * @param {Object} idUser
 */
export  function roleTreeListByIdUser(userId){
  return request({
    url: 'sys/role/roleList',
    method: 'post',
    params: {
      userId: userId
    }
  })
}

export function rootRoleTree(){
  return request({
    url: 'sys/role/tree',
    method: 'get',
  })
}


export function savePermissons(params) {
  return request({
    url: 'sys/role/savePermisson',
    method: 'post',
    params
  })
}
