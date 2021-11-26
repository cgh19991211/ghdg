<template>
  <div class="app-container">
    <div class="block">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input v-model="listQuery.name" placeholder="请输入权限名称"></el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="success" icon="el-icon-search" @click.native="search">{{ $t('button.search') }}</el-button>
          <el-button type="primary" icon="el-icon-refresh" @click.native="reset">{{ $t('button.reset') }}</el-button>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="24">
          <el-button type="success" icon="el-icon-plus" @click.native="add">{{ $t('button.add') }}</el-button>
          <!-- <el-button type="primary" icon="el-icon-edit" @click.native="edit">{{ $t('button.edit') }}</el-button> -->
          <!-- <el-button type="danger" icon="el-icon-delete" @click.native="remove">{{ $t('button.delete') }}</el-button> -->
        </el-col>
      </el-row>
    </div>


    <tree-table :data="list" :expandAll="expandAll" :cell-style="{'text-align':'center'}" v-loading="listLoading"
      element-loading-text="Loading" border fit highlight-current-row >

      <el-table-column label="名称">
        <template slot-scope="scope">
          <el-button type="text" @click="edit(scope.row)">{{scope.row.name}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="编码">
        <template slot-scope="scope">
          {{scope.row.code}}
        </template>
      </el-table-column>
      <el-table-column label="上级权限">
        <template slot-scope="scope">
          {{scope.row.pName}}
        </template>
      </el-table-column>
      <el-table-column label="资源路径">
        <template slot-scope="scope">
          {{scope.row.url}}
        </template>
      </el-table-column>
      <el-table-column label="所属菜单">
        <template slot-scope="scope">
          {{scope.row.menuName}}
        </template>
      </el-table-column>
      <el-table-column label="备注">
        <template slot-scope="scope">
          {{scope.row.remark}}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>

    </tree-table>


    <el-pagination background layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50, 100,500]"
      :page-size="listQuery.limit" :total="total" @size-change="changeSize" @current-change="fetchPage"
      @prev-click="fetchPrev" @next-click="fetchNext">
    </el-pagination>

    <el-dialog :title="formTitle" :visible.sync="formVisible" width="70%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="名称" prop="name">
              <el-input v-model="form.name" minlength=1></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="编码" prop="code">
              <el-input v-model="form.code" minlength=1></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="url" prop="url">
              <el-input v-model="form.url" minlength=5></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="上级权限">
              <el-input placeholder="请选择上级权限" v-model="form.pName" readonly="readonly"
                @click.native="permissionTree.show = !permissionTree.show">
              </el-input>
              <el-tree v-if="permissionTree.show" empty-text="暂无数据" :expand-on-click-node="false" :data="list"
                :props="permissionTree.defaultProps" @node-click="handlePermissionNodeClick" class="input-tree">
              </el-tree>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="菜单/按钮">
              <el-input placeholder="请选择所属菜单/按钮" v-model="form.menuName" readonly="readonly"
                @click.native="menuShowTree.show = !menuShowTree.show">
              </el-input>
              <el-tree v-if="menuShowTree.show" empty-text="暂无数据" :expand-on-click-node="false" :data="menuTree"
                :props="menuShowTree.defaultProps" @node-click="handleMenuTreeNodeClick" class="input-tree">
              </el-tree>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" minlength=2></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item>
          <el-button type="primary" @click="save">{{ $t('button.submit') }}</el-button>
          <el-button @click.native="formVisible = false">{{ $t('button.cancel') }}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script src="./permission.js"></script>
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>
