<template>
    <el-dialog width="600" v-model="dialogFormVisible" :title="title">
        <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="字典类型">
                <el-input v-model="form.dictType" :disabled="true" />
            </el-form-item>
            <el-form-item label="数据标签" prop="dictLabel">
                <el-input
                    v-model="form.dictLabel"
                    placeholder="请输入数据标签"
                />
            </el-form-item>
            <el-form-item label="数据键值" prop="dictValue">
                <el-input
                    v-model="form.dictValue"
                    placeholder="请输入数据键值"
                />
            </el-form-item>
            <el-form-item label="样式属性" prop="cssClass">
                <el-input
                    v-model="form.cssClass"
                    placeholder="请输入样式属性"
                />
            </el-form-item>
            <el-row :gutter="20">
                <el-col :span="12" :offset="0">
                    <el-form-item label="显示排序" prop="dictSort">
                        <el-input-number
                            v-model="form.dictSort"
                            controls-position="right"
                            :min="0"
                        />
                    </el-form-item>
                </el-col>
                <el-col :span="12" :offset="0">
                    <el-form-item label="回显样式" prop="listClass">
                        <el-select v-model="form.listClass">
                            <el-option
                                v-for="item in listClassOptions"
                                :key="item.value"
                                :label="item.label + '(' + item.value + ')'"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>

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
import { addData, updateData } from "@/api/dictData";
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
        dictLabel: [
            { required: true, message: "数据标签不能为空", trigger: "blur" },
        ],
        dictValue: [
            { required: true, message: "数据键值不能为空", trigger: "blur" },
        ],
        dictSort: [
            { required: true, message: "数据顺序不能为空", trigger: "blur" },
        ],
    },
    listClassOptions: [
        {
            value: "default",
            label: "默认",
        },
        {
            value: "primary",
            label: "主要",
        },
        {
            value: "success",
            label: "成功",
        },
        {
            value: "info",
            label: "信息",
        },
        {
            value: "warning",
            label: "警告",
        },
        {
            value: "danger",
            label: "危险",
        },
    ],
});

const { rules, listClassOptions } = toRefs(data);
const { form, visible, isNew } = toRefs(props);
const title = ref("新增明细");

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
                addData(form.value).then((res) => {
                    ElMessage.success("新增成功");
                    emit("callback");
                    dialogFormVisible.value = false;
                });
            } else {
                updateData(form.value).then((res) => {
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