import { remove, getList, save, savePermissons,getShowList } from '@/api/system/role'
import { menuTreeListByRoleId } from '@/api/system/menu'
import moment from 'moment'

export default {
  data() {
    return {
      formVisible: false,
      formTitle: '添加角色',
      roleList: [],
      isAdd: true,
      checkedPermissionKeys: [],
      permissons: [],
      defaultProps: {
        id: 'id',
        label: 'name',
        children: 'children'
      },
      permissonVisible: false,
      roleTree: {
        show: false,
        defaultProps: {
          id: 'id',
          label: 'name',
          children: 'children'
        }
      },

      form: {
        code: '',
        name: '',
        pid: 0,
        id: '',
        version: '',
        pName: '',
        num: 1,
        lastModifiedDate: null,
        remark: ''
      },
      rules: {
        code: [
          { required: true, message: '请输入角色编码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ]
      },
      listQuery: {
        name: undefined
      },
      total: 0,
      list: null,
      listLoading: true,
      selRow: {}
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
    init() {
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      getShowList(this.listQuery).then(response => {
        console.log("role fetch data")
        console.log(response.data)
        this.list = response.data.records
        this.listLoading = false
        this.total = response.data.total
      })
    },
    search() {
      this.fetchData()
    },
    reset() {
      this.listQuery.name = ''
      this.fetchData()
    },
    handleFilter() {
      this.getList()
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
      this.selRow = currentRow
    },
    resetForm() {
      this.form = {
        code: '',
        name: '',
        pid: 0,
        id: '',
        version: '',
        pName: '',
        num: 1,
        remark: ''

      }
    },
    add() {
      this.resetForm()
      this.formTitle = '添加角色'
      this.formVisible = true
      this.isAdd = true
    },
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          save({
            id: this.form.id,
            displaySeq: this.form.num,
            parent: this.form.pid,
            roleName: this.form.name,
            roleCode: this.form.code,
            remark: this.form.remark
          }).then(response => {
            this.$message({
              message: '提交成功',
              type: 'success'
            })
            this.fetchData()
            this.formVisible = false
          })
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
        this.form.status = this.selRow.statusName === '启用'
        this.form.password = ''
        this.formTitle = '修改角色'
        this.formVisible = true
      }
    },
    remove() {
      if (this.checkSel()) {
        const id = this.selRow.id
        this.$confirm('确定删除该记录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          remove(id).then(response => {
            this.$message({
              message: '提交成功',
              type: 'success'
            })
            this.fetchData()
          }).catch( err=> {
            this.$notify.error({
              title: '错误',
              message:err,
            })
          })
        }).catch(() => {
        })
      }
    },
    openPermissions() {
      if (this.checkSel()) {
        menuTreeListByRoleId(this.selRow.id).then(response => {
          console.log("角色菜单树")
          console.log(response)
          this.permissons = response.data.treeData
          this.checkedPermissionKeys = response.data.checkedIds
          this.permissonVisible = true
        })
      }
    },
    savePermissions() {
      let checkedNodes = this.$refs.permissonTree.getCheckedNodes(false,true)
      let permissionIds = ''
      console.log("checkedNodes")
      console.log(checkedNodes)//null???
      for (var index in checkedNodes) {
          permissionIds += checkedNodes[index].permissionId+','
      }
      const data = {
        roleId: this.selRow.id,
        permissionIds: permissionIds
      }
      console.log("save permissions params:checkNodes")
      console.log(data)
      savePermissons(data).then(response => {
        this.permissonVisible = false
        this.$message({
          message: '提交成功',
          type: 'success'
        })
      })
    },
    handleRoleNodeClick(data, node) {
      this.form.pid = data.id
      this.form.pName = data.name
      this.roleTree.show = false
    },
    dateFormat(row, column) {
      var date = row[column.property];
      if (date == undefined) {
        return "";
      }
      return moment(date).format("YYYY-MM-DD HH:mm:ss");
    },
    buttonType(){
      if (this.selRow && this.selRow.id) {
        return true
      }
      return false
    }


  }
}
