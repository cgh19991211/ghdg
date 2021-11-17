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
  roleTreeListByIdUser
} from '@/api/system/role'
import moment from 'moment'
// 权限判断指令
import permission from '@/store'

export default {
  directives: {
    permission
  },
  data() {
    return {
      roleDialog: {
        visible: false,
        roles: [],
        roleTree: [],
        checkedRoleKeys: [],
        defaultProps: {
          id: 'id',
          label: 'nickname',
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
        lastLoginDate: null
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
        }]
      },
      listQuery: {
        page: 1,
        limit: 20,
        username: undefined,
        nickname: undefined
      },
      total: 0,
      list: null,
      listLoading: true,
      selRow: {},
      tmpForm: {}
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
      this.form = {
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
      if (this.form.password !== this.form.rePassword) {
        return false
      }
      if (this.form.password === '' || this.form.rePassword === '') {
        return false
      }
      return true
    },
    saveUser() {
      var self = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.validPasswd()) {
            var form = self.form
            console.log("save user form：")
            console.log(form)
            if (form.status === 1) {
              //启用
              form.status = '生效'
            } else {
              //冻结
              form.status = '失效'
            }
            // form.createdDate = new Date(form.createdDate)
            if (self.isAdd) {
              saveUser(form).then(response => {
                this.$message({
                  message: '提交成功',
                  type: 'success'
                })
                this.fetchData()
                this.formVisible = false
              })
            } else {
              modifyUser(form).then(response => {
                this.$message({
                  message: '修改成功',
                  type: 'success'
                })
                this.fetchData()
                this.formVisible = false
              })
            }

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

        this.form = this.selRow
        this.form.status = this.selRow.status
        this.form.password = ''
        this.formTitle = '修改用户'
        this.formVisible = true
        this.tmpForm = this.form//这里把两个form同步了。
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
        roleTreeListByIdUser(this.selRow.id).then(response => {
          this.roleDialog.roles = response.data.treeData
          this.roleDialog.checkedRoleKeys = response.data.checkedIds
          this.roleDialog.visible = true
        })
      }
    },
    setRole() {
      var checkedRoleKeys = this.$refs.roleTree.getCheckedKeys()
      var roleIds = ''
      for (var index in checkedRoleKeys) {
        roleIds += checkedRoleKeys[index] + ','
      }
      var data = {
        userId: this.selRow.id,
        roleIds: roleIds
      }
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
      return moment(date).format("YYYY-MM-DD HH:mm:ss");
    },
    cancelSubmit() {
      console.log("cancel submit")
      console.log(this.tmpForm)
      this.form = this.tmpForm
      this.formVisible = false
    }

  }
}
