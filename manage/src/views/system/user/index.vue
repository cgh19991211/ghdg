<template>
  <div class="app-container">
    <div class="block">
      <el-row  :gutter="20">
        <el-col :span="6">
          <el-input v-model="listQuery.username" placeholder="请输入帐号名"></el-input>
        </el-col>
        <el-col :span="6">
          <el-input v-model="listQuery.nickname" placeholder="请输入昵称"></el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="success" icon="el-icon-search" @click.native="search">{{ $t('button.search') }}</el-button>
          <el-button type="primary" icon="el-icon-refresh" @click.native="reset">{{ $t('button.reset') }}</el-button>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="24">
          <el-button type="success" icon="el-icon-plus" @click.native="add" v-permission="['/user/edit']">
            {{$t('button.add') }}
          </el-button>
          <el-button type="primary" icon="el-icon-edit" @click.native="edit" v-permission="['/user/edit']">
            {{$t('button.edit') }}
          </el-button>
          <el-button type="danger" icon="el-icon-delete" @click.native="remove" v-permission="['/user/edit']">
            {{$t('button.delete') }}
          </el-button>
          <el-button type="info" icon="el-icon-role" @click.native="openRole">角色分配</el-button>
        </el-col>
      </el-row>
    </div>


    <el-table :data="list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row
    @current-change="handleCurrentChange">

      <el-table-column label="账号名" prop="username">

      </el-table-column>
      <el-table-column label="昵称">
        <template slot-scope="scope">
          {{scope.row.nickname}}
        </template>
      </el-table-column>
      <el-table-column label="性别">
        <template slot-scope="scope">
          {{scope.row.gender}}
        </template>
      </el-table-column>
      <el-table-column label="角色">
        <template slot-scope="scope">
          <span v-for="(item,index) in scope.row.rolename">
            {{ item }} <span v-if="index<scope.row.rolename.length-1">,&nbsp;</span>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱">
        <template slot-scope="scope">
          {{scope.row.email}}
        </template>
      </el-table-column>
      <el-table-column label="电话">
        <template slot-scope="scope">
          {{scope.row.phone}}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createdDate" :formatter="dateFormat"/>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <span v-if="scope.row.status===1">生效</span>
          <span v-else-if="scope.row.status===0">失效</span>
        </template>
      </el-table-column>
      <el-table-column prop="lastLoginDate" label="上次登陆时间" :formatter="dateFormat"/>
      <el-table-column label="备注">
        <template slot-scope="scope">
          {{scope.row.remark}}
        </template>
      </el-table-column>

    </el-table>

    <el-pagination
      background
      layout="total, sizes, prev, pager, next, jumper"
      :page-sizes="[10, 20, 50, 100,500]"
      :page-size="listQuery.limit"
      :total="total"
      @size-change="changeSize"
      @current-change="fetchPage"
      @prev-click="fetchPrev"
      @next-click="fetchNext">
    </el-pagination>

    <el-dialog
      :title="formTitle"
      :visible.sync="formVisible"
      width="70%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="账户名">
              <el-input v-model="form.username" minlength=1></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="form.nickname"  minlength=1></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="性别">
              <el-radio-group v-model="form.gender">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
                <el-radio label="保密">保密</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-show="isAdd">
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password"  type="password"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-show="isAdd">
            <el-form-item label="确认密码" prop="rePassword">
              <el-input v-model="form.rePassword"  type="password"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="phone">
              <el-input v-model="form.phone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否启用" prop="status">
              <el-switch v-model="form.status" :active-value="1" :inactive-value="0"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="saveUser">{{ $t('button.submit') }}</el-button>
          <el-button @click="cancelSubmit">{{ $t('button.cancel') }}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog
      title="角色分配"
      :visible.sync="roleDialog.visible"
      width="25%">
      <el-form>
        <el-row>
          <el-col :span="12">
            <el-tree
              :data="roleDialog.roles"
              ref="roleTree"
              show-checkbox
              node-key="id"
              :default-checked-keys="roleDialog.checkedRoleKeys"
              :props="roleDialog.defaultProps">
            </el-tree>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="setRole">{{ $t('button.submit') }}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>


<script src="./user.js"></script>
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>
