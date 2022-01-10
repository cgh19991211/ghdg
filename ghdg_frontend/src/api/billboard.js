import request from '@/utils/request'

export function getBillboard() {
  return request({
    url: '/business/billboard/show',
    method: 'get'
  })
}

