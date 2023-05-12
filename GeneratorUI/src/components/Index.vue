<template>
    <div>
        <div class="text-xxl text-bold text-center title">
            🎉代码自动生成器🎉
        </div>
        <div class="padding-xl detail">
            <el-steps :active="active" finish-status="success" simple>
                <el-step title="链接数据库" :icon="Link" />
                <el-step title="选择数据表" :icon="Pointer" />
                <el-step title="配置表信息" :icon="Edit" />
                <el-step title="生成代码" :icon="Download" />
            </el-steps>
        </div>
        <div class="padding-xl">
            <div v-if="active == 0">
                <ConnectDatasorce @callback="active = 1" />
            </div>
            <div v-if="active == 1">
                <SelectTable
                    @preClick="active = 0"
                    @nextClick="onSelectTable"
                />
            </div>
            <div v-if="active == 2">
                <EditTable
                    :tableId="selectTable.tableId"
                    @preClick="active = 1"
                    @nextClick="active = 3"
                />
            </div>
            <div v-if="active == 3">
                <DownTable
                    :tableId="selectTable.tableId"
                    :tableName="selectTable.tableName"
                    @preClick="active = 2"
                />
            </div>
        </div>
    </div>
</template>

<script setup>
import ConnectDatasorce from "./ConnectDatasource.vue";
import SelectTable from "./SelectTable.vue";
import EditTable from "./EditTable.vue";
import DownTable from "./DownTable.vue";
import { Link, Pointer, Edit, Download } from "@element-plus/icons-vue";
import { ref, reactive, toRefs } from "vue";

const active = ref(0);
const data = reactive({
    selectTable: {},
});

const { selectTable } = toRefs(data);

const onSelectTable = (item) => {
    selectTable.value = item;
    active.value = 2;
};
</script>

<style lang="scss" scoped>
.title {
    font-size: 24px;
    letter-spacing: 16px;
}
</style>
