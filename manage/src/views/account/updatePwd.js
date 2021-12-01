import { updatePwd } from '@/api/login'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
    return {
      form: {
        oldPassword: '',
        password: '',
        rePassword: ''
      },
      activeName: 'updatePwd',
      user: {}
    }
  },

  computed: {
    rules() {
      return {
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 5, max: 100, message: '密码长度不能小于5', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init(){
      this.user = this.$store.state

    },
    handleClick(tab, event){
      this.$router.push({ path: '/account/'+tab.name})
    },
    updatePwd() {
      if(this.form.password!==this.form.rePassword){
        console.log("看看密码")
        console.log(this.form.password)
        console.log(this.form.rePassword)
        console.log(this.form.rePassword!==this.form.password)
        this.$message({
          message: '请确认新密码与重复密码是相同的',
          type: 'error'
        })
        return false
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          updatePwd({
            oldPassword: this.form.oldPassword,
            password: this.form.password
            // rePassword: this.form.rePassword
          }).then(response => {
            this.$message({
              message: '密码修改成功',
              type: 'success'
            })
            this.$store.dispatch('LogOut').then(() => {
              location.reload() // 为了重新实例化vue-router对象 避免bug
            })
          }).catch((err) => {
            this.$message({
              message: err,
              type: 'error'
            })
          })
        } else {
          return false
        }
      })
    }

  }
}
