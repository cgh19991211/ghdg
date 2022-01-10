import request from '@/utils/request'

export function getTodayTip() {
  return request({
    url: '/business/daily/today',
    method: 'get'
  })
}