<template>
  <div>
    <!-- 表格部分 -->
    <el-table :data="tableData" border style="width: 100%;margin-bottom: 20px;" v-loading="loading" table-layout="auto"
      default-expand-all>

      <el-table-column type="expand">
        <template #default="props">
          <el-table :data="props.row.bookList" stripe>
            <el-table-column label="BID" prop="bid" sortable />
            <el-table-column label="图书名" prop="bname" />
            <el-table-column label="出版社" prop="bpublisher" />
            <el-table-column label="ISBN" prop="bisbn" />
            <el-table-column label="价格" prop="bprice" />

            <el-table-column label="操作">
              <template #default="scope">
                <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>

      <el-table-column label="分类名称" prop="ccname" width="180" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button color="#626aef" size="small" @click="handleAdd(scope.$index, scope.row)">新增</el-button>
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
        <el-form-item label="图书名" prop="bname" :rules="[{ required: true, message: '用户名不能为空', trigger: 'blur' }]">
          <el-input v-model.trim="form.bname" style="width:80%" />
        </el-form-item>
        <el-form-item label="出版社" prop="bpublisher">
          <el-input v-model="form.bpublisher" style="width:80%" />
        </el-form-item>
        <el-form-item label="ISBN" prop="bisbn" :rules="[{ required: true, message: 'ISBN不能为空', trigger: 'blur' }]">
          <el-input v-model="form.bisbn" style="width:80%" />
        </el-form-item>
        <el-form-item label="价格" prop="bprice" :rules="[{ required: true, message: '价格不能为空', trigger: 'blur' },
        { type: 'number', message: '价格必须是数字' }]">
          <el-input v-model.number="form.bprice" type="text" style="width:80%" />
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
import { ElNotification, ElMessageBox } from 'element-plus'

const { uid } = JSON.parse(localStorage.getItem('user'))


// 分页信息
const pageInfo = reactive({
  currentPage: 1, // 当前页
  pageSize: 2, // 每页多少条
  total: 10 // 总数
})

// 将分页信息响应式
const { currentPage, pageSize, total } = toRefs(pageInfo)

// 处理当前页
const handleCurrentChange = (val) => {
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
    console.log(data);
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


const { form } = toRefs(reactive({ // 表单数据
  form: {}
}))

const formTitle = ref('') // 表单标题
const dialogFormVisible = ref(false) // 是否显示表单控件

// 处理添加
const handleAdd = (index, row) => {
  dialogFormVisible.value = true
  formTitle.value = '新增'
  form.value = {}
  console.log(form.value);
  form.value.cid = row.cid
}

// 处理修改
const handleEdit = (index, row) => {
  dialogFormVisible.value = true
  formTitle.value = '编辑'
  form.value = row
  console.log(form.value);
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
      const res = await request.post('book/delete', row)
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
      if (formTitle.value === '新增') { // 如果标题是新增就发送新增请求
        try {
          const res = await request.post('book/add', form.value)
          if (res.code == 200) {
            ElNotification({ message: '添加成功', type: 'success', })
          }
        } catch (error) {
          ElNotification({ message: '添加失败', type: 'warning', })
        }
      } else { // 否则是编辑就发送编辑请求
        try {
          const res = await request.post('book/update', form.value)
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