<template>
  <div class="app-container">
    <div class="block">
      <el-button type="success" icon="el-icon-plus" @click.native="add" v-permission="['/system/menu/add']">{{ $t('button.add') }}</el-button>
    </div>

    <tree-table :data="data" :expandAll="expandAll"
      :cell-style="{'text-align':'center'}" highlight-current-row border>

      <el-table-column label="名称">
        <template slot-scope="scope">
          <el-button type="text" @click="edit(scope.row)" v-permission="['/system/menu/edit']">{{scope.row.menuName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="编码">
        <template slot-scope="scope">
          <span>{{scope.row.menuCode}}</span>
        </template>
      </el-table-column>
      <el-table-column label="ID">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column label="类型">
        <template slot-scope="scope">
          <span>{{scope.row.type}}</span>
        </template>
      </el-table-column>
      <el-table-column label="tip">
        <template slot-scope="scope">
          <span>{{scope.row.tip}}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否生效">
        <template slot-scope="scope">
          <span>{{scope.row.status}}</span>
        </template>
      </el-table-column>
      <el-table-column label="顺序" prop="displaySeq">
        <template slot-scope="scope">
          <span>{{scope.row.displaySeq}}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createdDate" :formatter="dateFormat"/>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="remove(scope.row)" v-permission="['/system/menu/delete']">删除</el-button>
        </template>
      </el-table-column>
    </tree-table>

    <el-dialog :title="formTitle" :visible.sync="formVisible" width="70%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="名称" prop="menuName">
              <el-input v-model="form.menuName" minlength=1></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="tip" prop="tip">
              <el-input v-model="form.tip" minlength=0></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类型">
              <el-radio-group v-model="form.type">
                <el-radio v-model="form.type" label="menu">菜单</el-radio>
                <el-radio v-model="form.type" label="button">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否生效">
              <el-radio-group v-model="form.status">
                <el-radio v-model="form.status" label="生效">是</el-radio>
                <el-radio v-model="form.status" label="冻结">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单编码" prop="menuCode">
              <el-input v-model="form.menuCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图标">
              <el-input v-model="form.icon"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="displaySeq">
              <el-input type="number" v-model="form.displaySeq"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="父菜单">
              <el-input placeholder="请选择父菜单" v-model="form.pname" readonly="readonly"
                @click.native="showTree = !showTree">
              </el-input>
              <el-tree v-if="showTree" empty-text="暂无数据" :expand-on-click-node="false" :data="data"
                :props="defaultProps" @node-click="handleNodeClick" class="input-tree">
              </el-tree>
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

<script src="./menu.js"></script>
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>
