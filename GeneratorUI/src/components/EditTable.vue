<template>
    <el-card class="box-card" style="margin: 0 auto; max-width: 90%">
        <template #header>
            <div class="card-header">
                <el-icon class="margin-right-sm"><Edit /></el-icon
                ><span>配置表信息</span>
                <span class="fr">
                    <el-button :icon="RefreshLeft" @click="emit('preClick')"
                        >重新选择表</el-button
                    >
                    <el-button
                        :icon="Finished"
                        type="primary"
                        @click="onSubmit(tableForm)"
                    >
                        保存并生成代码
                    </el-button>
                </span>
            </div>
        </template>
        <el-form
            ref="tableForm"
            :model="tableInfo"
            label-width="120"
            :rules="rule"
            v-loading="loadingFlag"
        >
            <el-divider content-position="left">基本信息</el-divider>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="表名称" prop="tableName">
                        <el-input
                            placeholder="请输入仓库名称"
                            v-model="tableInfo.tableName"
                        />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="表描述" prop="tableComment">
                        <el-input
                            placeholder="请输入"
                            v-model="tableInfo.tableComment"
                        />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="实体类名称" prop="className">
                        <el-input
                            placeholder="请输入"
                            v-model="tableInfo.className"
                        />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="作者" prop="functionAuthor">
                        <el-input
                            placeholder="请输入"
                            v-model="tableInfo.functionAuthor"
                        />
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="备注" prop="remark">
                        <el-input
                            type="textarea"
                            v-model="tableInfo.remark"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-divider content-position="left">生成信息</el-divider>
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="packageName">
                        <template #label>
                            <span>
                                生成包路径
                                <el-tooltip
                                    content="生成在哪个Java包下，例如 com.gavin.system"
                                    placement="top"
                                >
                                    <el-icon><QuestionFilled /></el-icon>
                                </el-tooltip>
                            </span>
                        </template>
                        <el-input v-model="tableInfo.packageName" />
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item prop="moduleName">
                        <template #label>
                            <span>
                                生成模块名
                                <el-tooltip
                                    content="可理解为子系统名，例如 system"
                                    placement="top"
                                >
                                    <el-icon><QuestionFilled /></el-icon>
                                </el-tooltip>
                            </span>
                        </template>
                        <el-input v-model="tableInfo.moduleName" />
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item prop="businessName">
                        <template #label>
                            <span>
                                生成业务名
                                <el-tooltip
                                    content="可理解为功能英文名，例如 user"
                                    placement="top"
                                >
                                    <el-icon><QuestionFilled /></el-icon>
                                </el-tooltip>
                            </span>
                        </template>
                        <el-input v-model="tableInfo.businessName" />
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item prop="functionName">
                        <template #label>
                            <span>
                                生成功能名
                                <el-tooltip
                                    content="用作类描述，例如 用户"
                                    placement="top"
                                >
                                    <el-icon><QuestionFilled /></el-icon>
                                </el-tooltip>
                            </span>
                        </template>
                        <el-input v-model="tableInfo.functionName" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-divider content-position="left">字段信息</el-divider>
            <div>
                <el-button
                    class="fr"
                    type="primary"
                    :icon="Operation"
                    @click="drawerVisible = true"
                    >维护数据字典</el-button
                >
                <DictDrawer v-model:visible="drawerVisible" />
            </div>
            <EditTableColumn
                :dictOptions="dictOptions"
                class="padding-top-sm"
                :info="tableColumn"
            />
        </el-form>
    </el-card>
</template>

<script lang="ts" setup>
import EditTableColumn from "./EditTableColumn.vue";
import DictDrawer from "./DictDrawer.vue";
import {
    Delete,
    Edit,
    RefreshLeft,
    Finished,
    Operation,
} from "@element-plus/icons-vue";
import { reactive, ref, toRefs, watch } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { getGenTable, updateGenTable } from "@/api/generator";
import { optionselect } from "@/api/dictType";
const emit = defineEmits(["preClick", "nextClick"]);

const props = defineProps({
    tableId: Number,
});
const loadingFlag = ref(false);
const btnLoading = ref(false);
const drawerVisible = ref(false);
const resData = reactive({
    tableInfo: {},
    tableColumn: [],
    dictOptions: [],
});
const { tableInfo, tableColumn, dictOptions } = toRefs(resData);
const tableForm = ref<FormInstance>();

const rule = reactive<FormRules>({
    tableName: [{ required: true, message: "请输入表名称", trigger: "blur" }],
    tableComment: [
        { required: true, message: "请输入表描述", trigger: "blur" },
    ],
    className: [
        { required: true, message: "请输入实体类名称", trigger: "blur" },
    ],
    functionAuthor: [
        { required: true, message: "请输入作者", trigger: "blur" },
    ],
    packageName: [
        { required: true, message: "请输入生成包路径", trigger: "blur" },
    ],
    moduleName: [
        { required: true, message: "请输入生成模块名", trigger: "blur" },
    ],
    businessName: [
        { required: true, message: "请输入生成业务名", trigger: "blur" },
    ],
    functionName: [
        { required: true, message: "请输入生成功能名", trigger: "blur" },
    ],
});

const getTableInfo = () => {
    loadingFlag.value = true;
    getGenTable(props.tableId)
        .then((res) => {
            let result = res.data.info;
            tableColumn.value = result.columns;
            tableInfo.value = result;
            loadingFlag.value = false;
        })
        .catch((res) => {
            loadingFlag.value = false;
        });
};
getTableInfo();

watch(drawerVisible, (val) => {
    if (!val) {
        getDictOptions();
    }
});

const getDictOptions = () => {
    optionselect().then((res) => {
        dictOptions.value = res.data;
    });
};
getDictOptions();

const onSubmit = async (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    await formEl.validate((valid, fields) => {
        if (valid) {
            btnLoading.value = true;
            updateGenTable({
                ...tableInfo.value,
                columns: tableColumn.value,
            })
                .then((res) => {
                    emit("nextClick");
                    loadingFlag.value = false;
                })
                .catch((res) => {
                    loadingFlag.value = false;
                });
        }
    });
};
</script>

<style lang="scss" scoped></style>
