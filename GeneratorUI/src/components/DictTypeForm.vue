<template>
    <el-dialog width="600" v-model="dialogFormVisible" :title="title">
        <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="字典名称" prop="dictName">
                <el-input
                    v-model="form.dictName"
                    placeholder="请输入字典名称"
                />
            </el-form-item>
            <el-form-item label="字典类型" prop="dictType">
                <el-input
                    v-model="form.dictType"
                    placeholder="请输入字典类型"
                />
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-radio-group v-model="form.status">
                    <el-radio label="0">正常</el-radio>
                    <el-radio label="1">停用</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
                <el-input
                    v-model="form.remark"
                    type="textarea"
                    placeholder="请输入内容"
                ></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="onSubmit(formRef)">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script lang="ts" setup>
import { addType, updateType } from "@/api/dictType";
import { reactive, ref, computed, watch, toRefs } from "vue";
import { ElMessage } from "element-plus";
import type { FormInstance, FormRules } from "element-plus";
const emit = defineEmits(["update:visible", "callback"]);
const props = defineProps({
    visible: {
        type: Boolean,
        default: false,
    },
    form: {
        type: Object,
        default: () => {},
    },
    isNew: {
        type: Boolean,
        default: true,
    },
});

const formRef = ref<FormInstance>();

const data = reactive({
    rules: {
        dictName: [
            { required: true, message: "字典名称不能为空", trigger: "blur" },
        ],
        dictType: [
            { required: true, message: "字典类型不能为空", trigger: "blur" },
        ],
    },
});

const { rules } = toRefs(data);
const { form, visible, isNew } = toRefs(props);
const title = ref("新增数据字典类型");

const dialogFormVisible = computed({
    get() {
        return props.visible;
    },
    set(value) {
        emit("update:visible", value);
    },
});

watch(isNew, (val) => {
    title.value = val
        ? "新增数据字典类型"
        : "修改【" + form.value.dictName + "】数据字典类型";
});

const onSubmit = async (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    await formEl.validate((valid, fields) => {
        if (valid) {
            if (isNew.value) {
                addType(form.value).then((res) => {
                    ElMessage.success("新增成功");
                    emit("callback");
                    dialogFormVisible.value = false;
                });
            } else {
                updateType(form.value).then((res) => {
                    ElMessage.success("修改成功");
                    emit("callback");
                    dialogFormVisible.value = false;
                });
            }
        }
    });
};
</script>

<style lang="scss" scoped>
</style>