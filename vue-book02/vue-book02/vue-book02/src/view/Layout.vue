<template>
  <el-container>
    <el-header style="padding: 0;">
      <Header />
    </el-header>
    <el-container>
      <el-aside width="200px">
        <Aside />
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import Header from '../components/Header.vue'
import Aside from '../components/Aside.vue'
</script>

<style scoped>
</style>
