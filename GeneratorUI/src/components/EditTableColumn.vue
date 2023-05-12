<template>
    <el-table ref="dragTable" :data="info" row-key="columnId">
        <el-table-column
            label="序号"
            type="index"
            min-width="5%"
            class-name="allowDrag"
            align="center"
        />
        <el-table-column
            label="字段列名"
            prop="columnName"
            min-width="10%"
            :show-overflow-tooltip="true"
        />
        <el-table-column label="字段描述" min-width="10%">
            <template #default="scope">
                <el-input v-model="scope.row.columnComment"></el-input>
            </template>
        </el-table-column>
        <el-table-column
            label="物理类型"
            prop="columnType"
            min-width="10%"
            :show-overflow-tooltip="true"
        />
        <el-table-column label="Java类型" min-width="11%">
            <template #default="scope">
                <el-select v-model="scope.row.javaType">
                    <el-option label="Long" value="Long" />
                    <el-option label="String" value="String" />
                    <el-option label="Integer" value="Integer" />
                    <el-option label="Double" value="Double" />
                    <el-option label="BigDecimal" value="BigDecimal" />
                    <el-option label="Date" value="Date" />
                    <el-option label="Boolean" value="Boolean" />
                </el-select>
            </template>
        </el-table-column>
        <el-table-column label="Java属性" min-width="10%">
            <template #default="scope">
                <el-input v-model="scope.row.javaField"></el-input>
            </template>
        </el-table-column>

        <el-table-column label="插入" min-width="5%">
            <template #default="scope">
                <el-checkbox
                    true-label="1"
                    false-label="0"
                    v-model="scope.row.isInsert"
                ></el-checkbox>
            </template>
        </el-table-column>
        <el-table-column label="编辑" min-width="5%">
            <template #default="scope">
                <el-checkbox
                    true-label="1"
                    false-label="0"
                    v-model="scope.row.isEdit"
                ></el-checkbox>
            </template>
        </el-table-column>
        <el-table-column label="列表" min-width="5%">
            <template #default="scope">
                <el-checkbox
                    true-label="1"
                    false-label="0"
                    v-model="scope.row.isList"
                ></el-checkbox>
            </template>
        </el-table-column>
        <el-table-column label="查询" min-width="5%">
            <template #default="scope">
                <el-checkbox
                    true-label="1"
                    false-label="0"
                    v-model="scope.row.isQuery"
                ></el-checkbox>
            </template>
        </el-table-column>
        <el-table-column label="查询方式" min-width="10%">
            <template #default="scope">
                <el-select v-model="scope.row.queryType">
                    <el-option label="=" value="EQ" />
                    <el-option label="!=" value="NE" />
                    <el-option label=">" value="GT" />
                    <el-option label=">=" value="GTE" />
                    <el-option label="<" value="LT" />
                    <el-option label="<=" value="LTE" />
                    <el-option label="LIKE" value="LIKE" />
                    <el-option label="BETWEEN" value="BETWEEN" />
                </el-select>
            </template>
        </el-table-column>
        <el-table-column label="必填" min-width="5%">
            <template #default="scope">
                <el-checkbox
                    true-label="1"
                    false-label="0"
                    v-model="scope.row.isRequired"
                ></el-checkbox>
            </template>
        </el-table-column>
        <el-table-column label="显示类型" min-width="12%">
            <template #default="scope">
                <el-select v-model="scope.row.htmlType">
                    <el-option label="文本框" value="input" />
                    <el-option label="文本域" value="textarea" />
                    <el-option label="下拉框" value="select" />
                    <el-option label="单选框" value="radio" />
                    <el-option label="复选框" value="checkbox" />
                    <el-option label="日期控件" value="datetime" />
                    <el-option label="图片上传" value="imageUpload" />
                    <el-option label="文件上传" value="fileUpload" />
                    <el-option label="富文本控件" value="editor" />
                </el-select>
            </template>
        </el-table-column>
        <el-table-column label="字典类型" min-width="12%">
            <template #default="scope">
                <el-select
                    v-model="scope.row.dictType"
                    clearable
                    filterable
                    placeholder="请选择"
                >
                    <el-option
                        v-for="dict in dictOptions"
                        :key="dict.dictType"
                        :label="dict.dictName"
                        :value="dict.dictType"
                    >
                        <span style="float: left">{{ dict.dictName }}</span>
                        <span
                            style="
                                float: right;
                                color: #8492a6;
                                font-size: 13px;
                            "
                            >{{ dict.dictType }}</span
                        >
                    </el-option>
                </el-select>
            </template>
        </el-table-column>
    </el-table>
</template>

<script setup>
import { reactive, ref, computed } from "vue";
const props = defineProps({
    info: {
        type: Array,
        default: () => [],
    },
    dictOptions: {
        type: Array,
        default: () => [],
    },
});
</script>

<style lang="scss" scoped>
</style>