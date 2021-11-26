// import {  getList, save, savePermissons,getShowList } from '@/api/system/permission'
import {
  remove,
  save,
  getList,
  getMenuTree
} from '@/api/system/permission'
import treeTable from '@/components/TreeTable'

export default {
  name: 'treeTableDemo',
  components: {
    treeTable
  },
  data() {
    return {
      showTree: false,
      listLoading: true,
      expandAll: true,
      formTitle: '',
      formVisible: false,
      isAdd: false,
      formVisible: false,
      formTitle: '添加权限',
      permissionList: [],
      isAdd: true,
      permissons: [],
      defaultProps: {
        id: 'id',
        label: 'name',
        children: 'children'
      },
      permissonVisible: false,
      permissionTree: {
        show: false,
        defaultProps: {
          id: 'id',
          label: 'name',
          children: 'children'
        }
      },
      menuTree: {
        id: '',
        menuName: '',
        children: null
      },
      menuShowTree: {
        show: false,
        defaultProps: {
          id: 'id',
          label: 'menuName',
          children: 'children'
        }
      },
      form: {
        id: '',
        name: '',
        code: '',
        pid: 0,
        pName: '',
        menuId: '',
        menuName: '',
        url: '',
        displaySeq: 1,
        remark: ''
      },
      list: {
        id: '',
        name: '',
        code: '',
        pid: 0,
        pName: '',
        menuId: '',
        menuName: '',
        url: '',
        displaySeq: 1,
        remark: ''
      },
      rules: {
        code: [{
            required: true,
            message: '请输入权限编码',
            trigger: 'blur'
          },
          {
            min: 3,
            max: 50,
            message: '长度在 3 到 50 个字符',
            trigger: 'blur'
          }
        ],
        name: [{
            required: true,
            message: '请输入角色名称',
            trigger: 'blur'
          },
          {
            min: 2,
            max: 50,
            message: '长度在 2 到 50 个字符',
            trigger: 'blur'
          }
        ]
      },
      listQuery: {
        name: undefined
      },
      total: 0,
      listLoading: true,
      data: []
    }
  },
  // filters: {
  //   statusFilter(status) {
  //     const statusMap = {
  //       published: 'success',
  //       draft: 'gray',
  //       deleted: 'danger'
  //     }
  //     return statusMap[status]
  //   }
  // },
  created() {
    this.init()
  },
  methods: {
    init() {
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      getList(this.listQuery).then(response => {
        console.log("permission fetch data")
        console.log(response.data)
        this.data = response.data
        this.list = response.data.records
        this.listLoading = false
        this.total = response.data.total
      })
      getMenuTree().then(response => {
        console.log("permission get menu tree")
        this.menuTree = response.data
        console.log(this.menuTree)
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
    resetForm() {
      this.form = {
        id: '',
        name: '',
        code: '',
        pid: '',
        pName: '',
        menuId: '',
        menuName: '',
        url: '',
        displaySeq: 1,
        remark: ''
      }
    },
    add() {
      this.resetForm()
      this.formTitle = '添加权限'
      this.formVisible = true
      this.isAdd = true
    },
    save() {
      let self = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          console.log("save data")
          let saveForm = self.form
          // saveForm.menu = null
          // saveForm.parent = null
          // saveForm.children = null
          // console.log(saveForm)
          save(saveForm).then(response => {
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
    generateSel(row) {
      console.log("row")
      console.log(row)
      /**
        id: '',
        name: '',
        code: '',
        pid: 0,
        pName: '',
        menuId: '',
        menuName: '',
        url: '',
        displaySeq: 1,
        remark: ''
       */
      this.form.id = row.id
      this.form.name = row.name
      this.form.code = row.code
      this.form.pid = row.pid
      this.form.pName = row.pName
      this.form.menuId = row.menuId
      this.form.menuName = row.menuName
      this.form.url = row.url
      this.form.displaySeq = row.displaySeq
      this.form.remark = row.remark
    },
    edit(row) {
      this.generateSel(row)
      this.isAdd = false
      this.formTitle = '修改权限'
      this.formVisible = true
    },
    remove(id) {
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
        }).catch(err => {
          this.$notify.error({
            title: '错误',
            message: err,
          })
        })
      })
    },
    handlePermissionNodeClick(data, node) {
      this.form.pid = data.id
      this.form.pName = data.name
      this.permissionTree.show = false
    },
    handleMenuTreeNodeClick(data, node) {
      console.log("node click")
      this.form.menuId = data.id
      this.form.menuName = data.menuName
      console.log(this.form)
      this.menuShowTree.show = false
    }
  }
}
