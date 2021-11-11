import { login, logout, getInfo } from '@/api/login'
import { getRefreshToken, getAccessToken, setAccessToken, removeToken, setRefreshToken } from '@/utils/auth'

const user = {
  state: {
    AccessToken: getAccessToken(),
    RefreshToken: getRefreshToken(),
    name: '',
    avatar: '',
    roles: [],
    phone: '',
    email: ''
  },

  mutations: {
    SET_ACCESS_TOKEN: (state, token) => {
      state.AccessToken = token
    },
    SET_REFRESH_TOKEN: (state, token) => {
      state.RefreshToken = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    SET_PHONE: (state, phone) => {
      state.phone = phone
    },
    SET_EMAIL: (state,email) => {
      state.email = email
    }

  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          const data = response.data
          setAccessToken(data.AccessToken)
          setRefreshToken(data.RefreshToken)
          commit('SET_ACCESS_TOKEN', data.AccessToken)
          commit('SET_REFRESH_TOKEN', data.RefreshToken)
          commit('SET_PHONE',data.phone)
          commit('SET_EMAIL',data.email)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.AccessToken).then(response => {
          const data = response.data
          console.log("后台返回的用户信息curInfo")
          console.log(data)
          if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', data.roles)
            commit('SET_PERMISSIONS',data.permissions)
          } else {
            reject('getInfo: roles must be a non-null array !')
          }
          commit('SET_NAME', data.account)
          commit('SET_AVATAR', data.avatar)
          commit('SET_PHONE',data.phone)
          commit('SET_EMAIL',data.email)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.AccessToken).then(() => {
          commit('SET_ACCESS_TOKEN', '')
          commit('SET_REFRESH_TOKEN','')
          commit('SET_ROLES', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_ACCESS_TOKEN', '')
        commit('SET_REFRESH_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
