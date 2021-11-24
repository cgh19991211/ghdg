import treeTable from '@/components/TreeTable'
import { getList, save, delMenu } from '@/api/system/menu'

export default {
  name: 'treeTableDemo',
  components: { treeTable },
  data() {
    return {
      showTree: false,
      defaultProps: {
        id: 'id',
        label: 'menuName',
        children: 'children'
      },

      listLoading: true,
      expandAll: true,
      formTitle: '',
      formVisible: false,
      isAdd: false,
      form: {
        id: '',
        parent: '',
        pname: '',
        menuName: '',
        menuCode: '',
        pcode: '',
        type: 'menu',
        displaySeq: 1,
        status: '生效',
        tip: ''
      },
      rules: {
        menuName: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 40 个字符', trigger: 'blur' }
        ],
        menuCode: [
          { required: true, message: '请输入编码', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 40 个字符', trigger: 'blur' }
        ],
        displaySeq: [
          { required: true, message: '请输入排序', trigger: 'blur' }
        ]
      },
      data: [],
      selRow: {}
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
      getList().then(response => {
        console.log("menu fetch data")
        console.log(response)
        this.data = response.data
        this.listLoading = false
      })
    },
    handleNodeClick(data, node) {
      this.form.pcode = data.menuCode
      this.form.pname = data.menuName
      this.form.parent = data.id
      this.showTree = false
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
    add() {
      this.form = {}
      this.formTitle = '添加菜单'
      this.formVisible = true
      this.isAdd = true
    },
    save() {
      var self = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          // const menuData = self.form
          // menuData.parent = self.form.parent.id
          // menuData.children = null
          // menuData.lastModifiedDate = null
          // menuData.createdDate = null

          const menuData = {}
          menuData['id'] = self.form.id
          menuData['menuName'] = self.form.menuName
          menuData['menuCode'] = self.form.menuCode
          menuData['type'] = self.form.type
          menuData['status']=self.form.status
          menuData['icon']=self.form.icon
          menuData['displaySeq']=self.form.displaySeq
          menuData['tip']=self.form.tip
          menuData['parent'] = self.form.parent

          console.log("menuData")
          console.log(menuData)
          console.log("self.form")
          console.log(self.form)
          // 把表单提交上去 -- parent与children会循环计算JSON，所以必须先置空
          console.log('menuData')
          console.log(menuData)
          save(menuData).then(response => {
            this.$message({
              message: '提交成功',
              type: 'success'
            })
            self.fetchData()
            self.formVisible = false
          })
        } else {
          return false
        }
      })
    },
    edit(row) {
      /**
       * id: '',
        parent: '',
        pname: '',
        menuName: '',
        menuCode: '',
        pcode: '',
        type: 'menu',
        displaySeq: 1,
        status: '生效',
        tip: ''
       */
      // this.form = row
      this.form.id = row.id
      this.form.parent = row.parent.id
      this.form.pname = row.name
      this.form.menuName = row.menuName
      this.form.menuCode = row.menuCode
      this.form.pcode = row.pcode
      this.form.type = row.type
      this.form.displaySeq = row.displaySeq
      this.form.status = row.status
      this.form.tip = row.tip
      console.log("看看row")
      console.log(row)
      this.form.children = null
      // if (row.type === 'menu') {
      //   this.form.type = 1
      // } else {
      //   this.form.type = 0
      // }
      // if (row.status === '生效') {
      //   this.form.status = 1
      // } else {
      //   this.form.status = 0
      // }
      if (row.parent) {
        this.form.pcode = row.parent.menuCode
        this.form.pname = row.parent.menuName
      }
      this.formTitle = '编辑菜单'
      this.formVisible = true
      this.isAdd = false
    },
    remove(row) {
      console.log(row)
      this.$confirm('确定删除该记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delMenu(row.id).then(response => {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.fetchData()
        }).catch(err =>{
          this.$notify.error({
            title: '错误',
            message:err,
          })
        })
      })
    }
  }
}
