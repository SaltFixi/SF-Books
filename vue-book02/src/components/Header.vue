<template>
  <header class="container">
    <div class="left">图书后台管理</div>
    <div style="flex:1"></div>
    <div class="right">
      <el-dropdown>
        <span class="el-dropdown-link">
          {{ user?.uname }}
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="handleUserInfo">个人信息</el-dropdown-item>
            <el-dropdown-item @click="handleLogout">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </header>


  <el-dialog v-model="dialogUserInfoVisible" title="个人信息" width="25%">
    <el-descriptions :column="1">
      <el-descriptions-item label="用户名:">{{ user.uname }}</el-descriptions-item>
      <el-descriptions-item label="密码:">{{ user.upwd }}</el-descriptions-item>
    </el-descriptions>

    <!-- <div>{{ user }}</div> -->
  </el-dialog>


</template>
<script>
import { removeToken } from '../utils/auth.js'
import { ArrowDown } from '@element-plus/icons-vue'
import request from '../utils/request.js'
export default {
  components: {
    ArrowDown
  },
  data () {
    return {
      user: {},
      dialogUserInfoVisible: false
    }
  },
  mounted () {
    const user = JSON.parse(localStorage.getItem('user'))
    console.log(user);
    this.user = user
  },
  methods: {
    async handleUserInfo () {
      const user = await request.get('user/all')
      user.data.forEach((item) => {
        if (item.uid === this.user.uid) {
          localStorage.setItem('user', JSON.stringify(item))
          this.user = item
        }
      })

      this.dialogUserInfoVisible = true
    },
    handleLogout () {
      localStorage.removeItem('user')
      removeToken()
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.container {
  height: 60px;
  line-height: 60px;
  border-bottom: 1px solid #ddd;
  display: flex;
  align-items: center;
}

.left {
  width: 200px;
  padding-left: 30px;
  font-weight: bold;
  color: dodgerblue;
}

.right {
  width: 100px;
  display: flex;
  align-items: center;
}
</style>