<template>
  <div>
    <!-- 搜索功能 -->
    <el-row :gutter="20" align="middle">
      <el-col :span="3">
        <el-button type="primary" @click="handleAdd">新增</el-button>

      </el-col>
      <el-col :span="10">
        <el-input v-model.trim="search" @change="handleSearch" placeholder="通过分类名称查找" class="input-with-select"
          style="width:500px;margin:10px 0">
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
      </el-col>
    </el-row>


    <!-- 表格部分 -->
    <el-table :data="tableData" stripe style="width: 100%;margin-bottom: 20px;" v-loading="loading" table-layout="auto">
      <el-table-column label="CID" prop="cid" sortable />
      <el-table-column label="分类名称" prop="ccname" />

      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页功能 -->
    <div class="demo-pagination-block">
      <el-pagination v-model:currentPage="currentPage" v-model:page-size="pageSize" style="justify-content: center;"
        layout="total, prev, pager, next, jumper" :total="total" @current-change="handleCurrentChange" />
    </div>


    <!-- 新增弹窗 -->
    <el-dialog v-model="dialogFormVisible" :title="formTitle">
      <el-form ref="ruleFormRef" :model="form" label-width="100px">
        <el-form-item label="分类名称" prop="ccname" :rules="[{ required: true, message: '分类名称不能为空', trigger: 'blur' }]">
          <el-input v-model="form.ccname" style="width:80%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="confirm(ruleFormRef)">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script  setup>
import request from '../utils/request.js'
import { onMounted, reactive, ref, toRefs } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { ElNotification, ElMessageBox } from 'element-plus'

const { uid } = JSON.parse(localStorage.getItem('user'))

// 分页信息
const pageInfo = reactive({
  currentPage: 1, // 当前页
  pageSize: 5, // 每页多少条
  total: 10 // 总数
})

// 将分页信息响应式
const { currentPage, pageSize, total } = toRefs(pageInfo)

// 处理当前页
const handleCurrentChange = (val) => {
  console.log(val);
  currentPage.value = val
  getTableData()
}

const tableData = ref([]) // 表格数据


// 获取5条表格数据
const getTableData = () => {
  request.get('classify/page', {
    params: {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      id: uid
    }
  }).then(({ data }) => {
    // console.log(data);
    tableData.value = data.records
    total.value = data.total
  }).catch(() => {
    ElNotification({ message: '获取tableData数据失败', type: 'warning', })
  })

}


// 页面挂载前获取表格数据
onMounted(async () => {
  getTableData()
})


const search = ref('') // 搜索

// 处理搜索
const handleSearch = async () => {
  const res = await request.get('classify/search', {
    params: {
      id: uid,
      name: search.value
    }
  })
  tableData.value = res.data
}


const { form } = toRefs(reactive({ // 表单数据
  form: {}
}))

const formTitle = ref('') // 表单标题
const dialogFormVisible = ref(false) // 是否显示表单控件

// 处理添加
const handleAdd = () => {
  dialogFormVisible.value = true
  formTitle.value = '新增'
  form.value = {}
}

// 处理修改
const handleEdit = (index, row) => {
  dialogFormVisible.value = true
  formTitle.value = '编辑'
  form.value = row
}

// 处理删除
const handleDelete = (index, row) => {
  ElMessageBox.confirm(
    '你确认删除它吗?',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      const res = await request.post('classify/delete', row)
      if (res.code == 200) {
        ElNotification({ message: '删除成功', type: 'success', })
      }
    } catch (error) {
      ElNotification({ message: '删除失败', type: 'warning', })
    }
    getTableData()
  }).catch(() => {
  })
}

const ruleFormRef = ref() // 表单验证
const loading = ref(false) // 加载

const confirm = () => {
  ruleFormRef.value.validate(async (valid) => {
    if (valid) { // 如果验证成功就提交
      loading.value = true
      form.value.uid = uid
      console.log(form.value);
      if (formTitle.value === '新增') { // 如果标题是新增就发送新增请求
        try {
          const res = await request.post('classify/add', form.value)
          if (res.code == 200) {
            ElNotification({ message: '添加成功', type: 'success', })
          } else {
            ElNotification({ message: res.msg, type: 'warning', })
          }
        } catch (error) {
          ElNotification({ message: '添加失败', type: 'warning', })
        }
      } else { // 否则是编辑就发送编辑请求
        try {
          const res = await request.post('classify/update', form.value)
          if (res.code == 200) {
            ElNotification({ message: '更新成功', type: 'success', })
          }
        } catch (error) {
          ElNotification({ message: '更新失败', type: 'warning', })
        }
      }
      getTableData()
      dialogFormVisible.value = false
      setTimeout(() => {
        loading.value = false
      }, 500);
    } else {
      console.log('error submit!!')
      return false
    }
  })
}

</script>

<style scoped>
.el-button--text {
  margin-right: 15px;
}

.el-select {
  width: 150px;
}

.el-input {
  width: 150px;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>