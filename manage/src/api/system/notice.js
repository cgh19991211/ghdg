import request from '@/utils/request'

export function getList(params) {
  return request({
    url: 'sys/notice/list',
    method: 'get',
    params
  })
}
