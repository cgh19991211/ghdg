import request from '@/utils/request'

export function getList(params) {
  return request({
    url: 'sys/role/roleList',
    method: 'post',
    params
  })
}

export function getShowList(params){
  return request({
    url: 'sys/role/list',
    method: 'get',
    params
  })
}

export function save(params) {
  return request({
    url: 'sys/role/save',
    method: 'post',
    params
  })
}

export function remove(id) {
  return request({
    url: 'sys/role/delete/',
    method: 'get',
    params: {
      id:id
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
  console.log('params')
  console.log(params)
  return request({
    url: 'sys/role/setMenusAndPermissions',
    method: 'post',
    params
  })
}
