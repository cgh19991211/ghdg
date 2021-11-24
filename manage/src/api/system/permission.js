import request from '@/utils/request'

export function getList(params) {
  return request({
    url: 'sys/permission/list',
    method: 'get',
    params
  })
}
