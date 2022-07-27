<template>
  <div class="bg">
    <div class="box">
      <div style="margin: 50px 40px">
        <div class="title">注册页面</div>
        <el-form ref="ruleFormRef" :model="ruleForm" status-icon label-width="80px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="uname" :rules="[{ required: true, message: '用户名不能为空', trigger: 'blur' }]">
            <el-input placeholder="请输入用户名" v-model="ruleForm.uname" type="text" />
          </el-form-item>
          <el-form-item label="密码" prop="upwd" :rules="[{ required: true, message: '密码不能为空', trigger: 'blur' }]">
            <el-input placeholder="请输入密码" v-model="ruleForm.upwd" type="password" />
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPwd"
            :rules="[{ required: true, message: '密码不能为空', trigger: 'blur' }]">
            <el-input placeholder="请确认密码" v-model="ruleForm.confirmPwd" type="password" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 70%;" @click="submitForm()">注册</el-button>
            <a href="javascript:void(0)" @click="$router.push('/login')">去登录</a>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import request from '../utils/request.js'
import { ElNotification } from 'element-plus'
export default {
  data () {
    return {
      ruleForm: {
        uname: '',
        upwd: '',
        confirmPwd: ''
      },
    }
  },
  methods: {
    submitForm () {
      this.$refs['ruleFormRef'].validate((valid) => {
        if (valid) {

          if (this.ruleForm.upwd !== this.ruleForm.confirmPwd) {
            ElNotification({ message: '两次密码输入的不一致！', type: 'warning', })
            return
          }

          request.post('user/add', this.ruleForm).then(res => {
            // console.log(res);
            if (res.code == 200) {
              ElNotification({ message: '注册成功', type: 'success', })
              this.$router.push('/login')
            } else {
              ElNotification({ message: res.msg, type: 'warning', })
            }
          })
        }
      })

    }
  }
}
</script>

<style scoped>
.bg {
  height: 100vh;
  width: 100%;
  overflow: hidden;
  background: #fd746c;
  /* fallback for old browsers */
  background: -webkit-linear-gradient(to right, #ff9068, #fd746c);
  /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(to right, #ff9068, #fd746c);
  /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}

.box {
  background-color: #fff;
  margin: 160px auto;
  width: 400px;
  border: 2px solid rgb(236, 173, 128);
  border-radius: 15px;
  box-shadow: 0 5px 10px #ccc;
}

.title {
  font-size: 25px;
  text-align: center;
  padding-bottom: 30px;
}
</style>