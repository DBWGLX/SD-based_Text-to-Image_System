//info.vue
<script lang="ts" setup>
import { ref, reactive } from "vue";
import { More, Edit, Delete } from "@element-plus/icons-vue";

// 定义存储集合
var Data = reactive({
  // 定义输入的查询条件
  q_str: ref(""),
  // 存储从后台获取的所有院系信息
  FacultyOptions: reactive([
    {
      value: 1,
      label: "计算机学院",
    },
    {
      value: 2,
      label: "外语学院",
    },
  ]),
  // 存储选择院系后的值
  FacultySelected: ref(""),

  // 存储从后台获取的所有专业信息
  MajorOptions: reactive([
    {
      value: 1,
      label: "计算机专业",
    },
    {
      value: 2,
      label: "外语专业",
    },
  ]),
  // 存储选择专业后的值
  MajorSelected: ref(""),

  // ===表格区域定义====
  students: reactive([
    {
      sno: "95001",
      name: "张武",
      gender: "男",
      birthday: "2001-10-10",
      faculty: "计算机学院",
      major: "计算机网络",
      mobile: "13514623594",
      email: "123@163.com",
      address: "郑州市金水区",
    },
    {
      sno: "95001",
      name: "张武",
      gender: "男",
      birthday: "2001-10-10",
      faculty: "计算机学院",
      major: "计算机网络",
      mobile: "13514623594",
      email: "123@163.com",
      address: "郑州市金水区",
    },
  ]),
  // =====分页====
  // 当前页
  currentsPage: ref(1),
  // 每页显示的数据量
  pageSize: ref(15),
  // 总数据量所有记录条数
  total: ref(0),
});

// 分页中修改每页的pageSize
const handleSizeChange = () => {};

// 分页中修改每页的currentsPage
const handleCurrentChange = () => {};
</script>

<template>
  <!-- 顶部查询区域   styple="display: flax;"横向显示-->
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="查询条件">
      <el-input v-model="Data.q_str" placeholder="请输入查询条件" clearable />
    </el-form-item>
    <!-- 动态获取院系信息 -->
    <el-form-item label="院系">
      <el-select v-model="Data.FacultySelected" placeholder="请选择院系">
        <el-option
          v-for="item in Data.FacultyOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-form-item>
    <!-- 动态获取专业信息 -->
    <el-form-item label="专业">
      <el-select v-model="Data.MajorSelected" placeholder="请选择专业">
        <el-option
          v-for="item in Data.MajorOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary">
        <!-- 引入方法1 -->
        <el-icon><component class="icons" is="Search"></component></el-icon>
        <span>查询</span></el-button
      >
      <el-button type="primary">
        <!-- 引入方法2 -->
        <el-icon><Finished /></el-icon>
        <span>全部</span></el-button
      >
      <el-button type="primary">
        <el-icon><Pointer /></el-icon>
        <span>添加</span></el-button
      >
    </el-form-item>
  </el-form>
  <!-- 2.表格信息部分 -->
  <el-table
    :data="Data.students"
    stripe
    border
    style="width: 100%"
    :header-cell-style="{
      backgroundColor: '#409EFF',
      color: '#FFF',
      FontSize: '14px',
    }"
  >
    <el-table-column label="序号" type="index" align="center" width="60" />
    <el-table-column prop="sno" label="学号" align="center" width="80" />
    <el-table-column prop="name" label="姓名" align="center" width="80" />
    <el-table-column prop="gender" label="性别" align="center" width="80" />
    <el-table-column
      prop="birthday"
      label="出生日期"
      align="center"
      width="180"
    />
    <el-table-column prop="faculty" label="院系" align="center" width="120" />
    <el-table-column prop="major" label="专业" align="center" width="120" />
    <el-table-column prop="mobile" label="电话" align="center" width="140" />
    <el-table-column prop="email" label="Email" align="center" width="180" />
    <el-table-column prop="address" label="地址" align="center" />
    <!-- 按钮区域 -->
    <el-table-column label="操作" align="center">
      <el-button type="primary" :icon="More" circle size="small" />
      <el-button type="warning" :icon="Edit" circle size="small" />
      <el-button type="danger" :icon="Delete" circle size="small" />
    </el-table-column>
  </el-table>
  <!-- 3.分页 currentPage4当前页 pageSize4每页大小 total记录条数 handleSizeChange改变每页大小 handleCurrentChange改变当前页  -->
  <el-pagination
    style="margin-top: 20px"
    background
    v-model:current-page="Data.currentsPage"
    v-model:page-size="Data.pageSize"
    :page-sizes="[10, 12, 15, 18, 20, 25, 40, 50]"
    layout="total, sizes, prev, pager, next, jumper"
    :total="Data.total"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
  />
</template>

<style scoped>
.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.demo-form-inline .el-select {
  --el-select-width: 220px;
}
</style>
