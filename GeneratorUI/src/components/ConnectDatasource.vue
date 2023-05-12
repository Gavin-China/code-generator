<template>
  <el-card class="box-card" style="margin: 0 auto; max-width: 50%">
    <template #header>
      <div class="card-header">
        <el-icon class="margin-right-sm"><Link /></el-icon
        ><span>填写数据库信息</span>
      </div>
    </template>
    <div>
      <el-form
        ref="formRef"
        label-position="right"
        label-width="120px"
        :model="datasorceInfo"
        class="padding-xl"
        :rules="rules"
      >
        <el-form-item label="数据库Host" prop="hostname">
          <el-input v-model="datasorceInfo.hostname" />
        </el-form-item>
        <el-form-item label="数据库Port" prop="port">
          <el-input v-model="datasorceInfo.port" />
        </el-form-item>
        <el-form-item label="数据库Schema" prop="schema">
          <el-input v-model="datasorceInfo.schema" />
        </el-form-item>
        <el-form-item label="数据库用户名" prop="username">
          <el-input v-model="datasorceInfo.username" />
        </el-form-item>
        <el-form-item label="数据库密码" prop="password">
          <el-input type="password" v-model="datasorceInfo.password" />
        </el-form-item>
        <el-form-item>
          <el-button
            :icon="Link"
            type="primary"
            @click="onSubmit(formRef)"
            :loading="loadingFlag"
            >链接数据库</el-button
          >
          <el-button :icon="RefreshLeft" @click="reset(formRef)"
            >重置</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { connect } from "@/api/datasource";
import { Link, RefreshLeft } from "@element-plus/icons-vue";
import { reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { ElMessage } from "element-plus";
const emit = defineEmits(["callback"]);

const formRef = ref<FormInstance>();
const loadingFlag = ref(false);
const datasorceInfo = reactive({
  hostname: "127.0.0.1",
  port: "3306",
  schema: "",
  username: "root",
  password: "",
});

const rules = reactive<FormRules>({
  hostname: [{ required: true, message: "请输入数据库Host", trigger: "blur" }],
  port: [{ required: true, message: "请输入数据库Port", trigger: "blur" }],
  schema: [{ required: true, message: "请输入数据库Schema", trigger: "blur" }],
  username: [
    { required: true, message: "请输入数据库用户名", trigger: "blur" },
  ],
  password: [{ required: true, message: "请输入数据库密码", trigger: "blur" }],
});

const onSubmit = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      loadingFlag.value = true;
      connect(datasorceInfo)
        .then((res) => {
          emit("callback");
          loadingFlag.value = false;
        })
        .catch((res) => {
          loadingFlag.value = false;
          ElMessage.error("请重新检查数据库配置！");
        });
    }
  });
};

const reset = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};
</script>

<style lang="scss" scoped></style>
