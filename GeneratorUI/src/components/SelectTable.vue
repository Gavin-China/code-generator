<template>
    <div>
        <el-card class="box-card" style="margin: 0 auto; max-width: 75%">
            <template #header>
                <div class="card-header">
                    <el-icon class="margin-right-sm"><Pointer /></el-icon
                    ><span>选择数据表</span>
                    <el-button class="fr" @click="emit('preClick')" :icon="Link"
                        >重新链接数据库</el-button
                    >
                </div>
            </template>
            <div>
                <div class="margin-bottom-xs">
                    <el-button
                        type="primary"
                        :icon="ArrowDownBold"
                        @click="dialogVisible = true"
                        >导入</el-button
                    >
                </div>
                <el-table
                    :data="list"
                    border
                    stripe
                    empty-text="无表信息，请先导入"
                    v-loading="loadingFlag"
                >
                    <el-table-column prop="tableName" label="表名称" />
                    <el-table-column prop="tableComment" label="表描述" />
                    <el-table-column prop="className" label="实体" />
                    <el-table-column
                        prop="createTime"
                        label="创建时间"
                        width="170"
                        align="center"
                    />
                    <el-table-column
                        prop="updateTime"
                        label="更新时间"
                        width="170"
                        align="center"
                    />
                    <el-table-column
                        fixed="right"
                        label="操作"
                        width="130"
                        align="center"
                    >
                        <template #default="scope">
                            <el-button
                                link
                                type="primary"
                                size="small"
                                :icon="Pointer"
                                @click="handleSelect(scope.row)"
                                >选中</el-button
                            >
                            <el-popconfirm
                                :title="
                                    '是否确认删除' +
                                    scope.row.tableName +
                                    '表信息？'
                                "
                                width="300"
                                @confirm="handleDelete(scope.row)"
                            >
                                <template #reference>
                                    <el-button
                                        link
                                        type="primary"
                                        size="small"
                                        :icon="Delete"
                                        >删除</el-button
                                    >
                                </template>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination
                    size="small"
                    v-model:current-page="pageParams.pageNum"
                    class="fr margin-tb"
                    v-model:page-size="pageParams.pageSize"
                    :page-sizes="[10, 20, 30, 40]"
                    layout="total, sizes, prev, pager, next"
                    :total="pageParams.total"
                    @current-change="getList"
                    @size-change="getList"
                />
            </div>
        </el-card>
        <SelectTableDailog
            v-model:visible="dialogVisible"
            @callback="getList"
        />
    </div>
</template>

<script setup>
import SelectTableDailog from "./SelectTableDailog.vue";
import { listTable, delTable } from "@/api/generator";
import {
    ArrowDownBold,
    Refresh,
    Link,
    Delete,
    Pointer,
} from "@element-plus/icons-vue";
import { reactive, ref, toRefs } from "vue";

const emit = defineEmits(["preClick", "nextClick"]);

const loadingFlag = ref(false);
const dialogVisible = ref(false);

const data = reactive({
    list: [],
    pageParams: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
    },
});

const { list, pageParams } = toRefs(data);

const getList = () => {
    loadingFlag.value = true;
    listTable(pageParams.value)
        .then((res) => {
            list.value = res.rows;
            pageParams.value.total = res.total;
            loadingFlag.value = false;
        })
        .catch((res) => {
            loadingFlag.value = false;
        });
};
getList();

const handleDelete = (item) => {
    loadingFlag.value = true;
    delTable(item.tableId)
        .then((res) => {
            getList();
        })
        .catch((res) => {
            loadingFlag.value = false;
        });
};

const handleSelect = (item) => {
    emit("nextClick", item);
};
</script>

<style lang="scss" scoped></style>
