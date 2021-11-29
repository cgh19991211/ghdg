import {
  deleteUser,
  getList,
  saveUser,
  remove,
  setRole,
  modifyUser
} from '@/api/system/user'
import {
  list as deptList
} from '@/api/system/dept'
import {
  parseTime
} from '@/utils/index'
import {
  roleTreeListByIdUser,
  rootRoleTree
} from '@/api/system/role'
import moment from 'moment'
// 权限判断指令
import permission from '@/directive/permission/index.js'
import {isvalidPass} from '@/utils/validate.js'

export default {
  directives: {
    permission
  },
  data() {
    return {
      roleDialog: {
        /**
         * 整棵树包含的数据是一个数组[],每一项包含id，lable，children，数组顺序按照树的层次遍历来存放。按照下标[1]就能取到数组中第一个数据
         */
        visible: false,
        roles: [],//rootRoleList: id,pid,name,children,checked
        roleTree: [],
        checkedRoleKeys: [],//默认选中的角色的id
        defaultProps: {//对话框的属性
          id: 'id',
          label: 'name',
          children: 'children'
        }
      },
      assignedRoles: {
        visible: false,
        roles: [],//树列表
        roleTree: [],//
        defaultProps: {
          id: 'id',
          lable: 'name',
          children: 'children'
        }
      },
      formVisible: false,
      formTitle: '添加用户',
      // deptTree: {
      //   show: false,
      //   data: [],
      //   defaultProps: {
      //     id: 'id',
      //     label: 'simplename',
      //     children: 'children'
      //   }
      // },
      isAdd: true,
      form: {
        id: '',
        username: '',
        nickname: '',
        gender: '保密',
        email: '',
        status: 1,
        avatar: '',
        phone: '',
        remark: '',
        createdDate: '',
        lastLoginDate: ''
      },
      rules: {
        username: [{
            required: true,
            message: '请输入登录账号',
            trigger: 'blur'
          },
          {
            min: 2,
            max: 20,
            message: '长度在 3 到 40 个字符',
            trigger: 'blur'
          }
        ],
        nickname: [{
            required: true,
            message: '请输入用户名',
            trigger: 'blur'
          },
          {
            min: 2,
            max: 20,
            message: '长度在 2 到 40 个字符',
            trigger: 'blur'
          }
        ],
        email: [{
          required: true,
          message: '请输入email',
          trigger: 'blur'
        }],
        password: [{
          min: 8,
          max: 40,
          validator: this.validatePass,
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }]
      },
      listQuery: {
        page: 1,
        limit: 20,
        username: undefined,
        nickname: undefined
      },
      total: 0,
      list: [],
      listLoading: true,
      selRow: {},
      tmpForm: {
        id: '',
        username: '',
        nickname: '',
        gender: '保密',
        email: '',
        status: 1,
        phone: '',
        remark: ''
      }
    }
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() { //当RefreshToken过期后，数据被清楚，这里的init会为undefined，控制台会报错。
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      getList(this.listQuery).then(response => {
        console.log("fetch data response")
        this.list = response.data.records
        console.log(response)
        this.listLoading = false
        this.total = response.data.total
      }).catch(() => {

      })
    },
    search() {
      this.fetchData()
    },
    reset() {
      this.listQuery.username = ''
      this.listQuery.nickname = ''
      this.fetchData()
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleClose() {

    },
    fetchNext() {
      this.listQuery.page = this.listQuery.page + 1
      this.fetchData()
    },
    fetchPrev() {
      this.listQuery.page = this.listQuery.page - 1
      this.fetchData()
    },
    fetchPage(page) {
      this.listQuery.page = page
      this.fetchData()
    },
    changeSize(limit) {
      this.listQuery.limit = limit
      this.fetchData()
    },
    handleCurrentChange(currentRow, oldCurrentRow) {
      console.log("选中行 1。新 2.舊")
      console.log(currentRow)
      console.log(oldCurrentRow)
      this.selRow = currentRow
    },
    resetForm() {
      this.tmpForm = {
        id: '',
        username: '',
        nickname: '',
        gender: '保密',
        email: '',
        status: 1,
        avatar: '',
        phone: '',
        remark: '',
        createdDate: '',
        password: '',
        rePassword: '',
        lastLoginDate: null
      }
    },
    add() {
      this.resetForm()
      this.formTitle = '添加用户'
      this.formVisible = true
      this.isAdd = true
    },
    validPasswd() {
      if (!this.isAdd) {
        return true
      }
      if (this.tmpForm.password !== this.tmpForm.rePassword) {
        return false
      }
      if (this.tmpForm.password === '' || this.tmpForm.rePassword === '') {
        return false
      }
      return true
    },
    saveUser() {
      var self = this
      this.$refs['tmpForm'].validate((valid) => {
        if (valid) {
          if (this.validPasswd()) {
            var saveForm = self.tmpForm
            saveForm.rolename = null
            console.log("save user form：")
            console.log(saveForm)
            saveUser(saveForm).then(response => {
                this.$message({
                  message: '提交成功',
                  type: 'success'
                })
                this.fetchData()
                this.formVisible = false
              })

          } else {
            this.$message({
              message: '提交失败',
              type: 'error'
            })
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    checkSel() {
      if (this.selRow && this.selRow.id) {
        return true
      }
      this.$message({
        message: '请选中操作项',
        type: 'warning'
      })
      return false
    },
    edit() {
      if (this.checkSel()) {
        this.isAdd = false
        this.tmpForm = JSON.parse(JSON.stringify(this.selRow))//深拷贝
        // this.tmpForm.status = this.selRow.status
        // this.tmpForm.password = ''
        this.formTitle = '修改用户'
        this.formVisible = true
      }
    },
    remove() {
      if (this.checkSel()) {
        var id = this.selRow.id

        this.$confirm('确定删除该记录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          remove(id).then(response => {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            this.fetchData()
          }).catch(err => {
            this.$notify.error({
              title: '错误',
              message: err,
            })
          })
        }).catch(() => {})
      }
    },
    // handleNodeClick(data, node) {
    //   this.form.deptid = data.id
    //   this.form.deptName = data.simplename
    //   this.deptTree.show = false
    // },

    openRole() {
      if (this.checkSel()) {
        console.log('康康当前行id')
        console.log(this.selRow.id)
        roleTreeListByIdUser(this.selRow.id).then(response => {//返回根树列表：每一行包括：id,pid,name,children,checked
          console.log("sel role data")
          console.log(response)
          this.roleDialog.roles = response.data.treeData
          this.roleDialog.checkedRoleKeys = response.data.checkedIds//根据roles里角色数据得checked获取要勾选的id
          console.log(this.roleDialog.checkedRoleKeys)
          this.roleDialog.visible = true
        })
      }
    },
    setRole() {
      var checkedRoleKeys = this.$refs.roleTree.getCheckedKeys()
      var roleIds = ''//要给当前选中行的用户分配的roleIds
      console.log("从tree-table获取来的roleTree")
      console.log(this.$refs.roleTree)
      for (var id in checkedRoleKeys) {
        roleIds += checkedRoleKeys[id] + ','
      }
      var data = {
        userId: this.selRow.id,
        roleIds: roleIds
      }
      console.log("set role")
      console.log(data)
      setRole(data).then(response => {
        this.roleDialog.visible = false
        this.fetchData()
        this.$message({
          message: '提交成功',
          type: 'success'
        })
      })
    },
    dateFormat(row, column) {
      var date = row[column.property];
      if (date == undefined) {
        return "";
      }
      console.log("see see moment ")
      console.log(moment)
      return moment(date).format("YYYY-MM-DD HH:mm:ss");
    },
    cancelSubmit() {
      // console.log("cancel submit")
      // console.log(this.tmpForm)
      // this.form = this.tmpForm
      this.formVisible = false
    },
    getCheckedId(){
      console.log("this.roleDialog")
      console.log(this.roleDialog)
      let ids = []
      let roles = this.roleDialog.roles
      for(let r=0; r<roles.length; ++r ){
        if(roles[r].checked){
          ids.push(roles[r].id)
          ids.push.apply(ids,this.recurChildren(roles[r].children,[]))
        }
      }
      return ids
    },
    recurChildren(roleChildren,ids){
      for(let index in roleChildren){
        let each = roleChildren[index]
        if(each.checked){
          ids.push(each.id)
        }
        if(each.children){
          ids.push.apply(ids,this.recurChildren(each.children,[]))
        }
      }
      return ids
    },
    validatePass(rule, value, callback){
          if (value === "") {
            callback(new Error("请输入密码"));
          } else if (!isvalidPass(value)) {
            callback(
              new Error("密码以字母开头 长度在8~18之间 只能包含字母、数字和下划线")
            );
          } else {
            if (this.tmpForm.rePassword !== "") {
              this.$refs.tmpForm.validateField("rePassword");
            }
            callback();
          }
        }

  }
}
