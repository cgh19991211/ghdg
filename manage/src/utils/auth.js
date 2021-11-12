import Cookies from 'js-cookie'

const AccessToken = 'AccessToken'
const RefreshToken = 'RefreshToken'
export function getAccessToken() {
  return Cookies.get(AccessToken)
}

export function setAccessToken(token) {
  return Cookies.set(AccessToken, token)
}

export function setRefreshToken(token){
  return Cookies.set(RefreshToken,token)
}

export function getRefreshToken(){
  return Cookies.get(RefreshToken)
}

export function removeToken() {
  Cookies.remove(AccessToken)
  return Cookies.remove(RefreshToken)
}
