
import store from '@/store'

export default{
  inserted(el, binding, vnode) {
    const { value } = binding//vue页面v-permission绑定的内容
    const permissions = store.getters && store.getters.permissions//getter->store.user.permissions->login时获取
    // console.log("permissions")
    // console.log(permissions)
    if (value && value instanceof Array && value.length > 0) {
      const permissionRoles = value

      const hasPermission = permissions.some(permission => {
        return permissionRoles.includes(permission.url)//后端把permission封装成dto，把url发过来了，使用url也行，permission code也行。
      })

      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`need roles! Like v-permission="['admin','editor']"`)
    }
  }
}
