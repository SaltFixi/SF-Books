<template>
  <el-menu router default-active="user" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"
    style="min-height: calc(100vh - 50px);">
    <el-menu-item index="user" router>
      <el-icon>
        <location />
      </el-icon>
      <span>用户管理</span>
    </el-menu-item>
    <el-menu-item index="classify">
      <el-icon>
        <icon-menu />
      </el-icon>
      <span>图书分类管理</span>
    </el-menu-item>
    <el-menu-item index="book">
      <el-icon>
        <setting />
      </el-icon>
      <span>图书管理</span>
    </el-menu-item>
  </el-menu>
</template>

<script  setup>
import {
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'


const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}
</script>

<style>
</style>