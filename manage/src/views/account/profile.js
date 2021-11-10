import { updatePwd } from '@/api/login'

export default {
  data() {
    return {
      activeName: 'profile',//名称，性别，头像；手机，邮箱，
      user:{}
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init(){
      this.user = this.$store.state.user
      // console.log("profile")
      // console.log(this.user)
    },
    handleClick(tab, event){
      this.$router.push({ path: '/account/'+tab.name})
    }

  }
}
