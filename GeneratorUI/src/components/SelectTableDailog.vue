<template>
    <el-dialog
        v-model="dialogVisible"
        title="选择需要导入的数据表"
        :close-on-click-modal="false"
        width="60%"
    >
        <el-form :inline="true" :model="queryParams">
            <el-form-item label="表名称">
                <el-input
                    v-model="queryParams.tableName"
                    placeholder="表名称"
                    @change="getList"
                />
            </el-form-item>
            <el-form-item label="表描述">
                <el-input
                    v-model="queryParams.tableComment"
                    placeholder="表描述"
                    @change="getList"
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getList">查询</el-button>
                <el-button @click="reset">重置</el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="list"
            border
            stripe
            v-loading="loadingFlag"
            @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" />
            <el-table-column prop="tableName" label="表名称" />
            <el-table-column prop="tableComment" label="表描述" />
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
        </el-table>
        <div class="padding-tb">
            <el-pagination
                size="small"
                class="fr"
                v-model:current-page="pageParams.pageNum"
                v-model:page-size="pageParams.pageSize"
                :page-sizes="[10, 20, 30, 40]"
                layout="total, sizes, prev, pager, next"
                :total="pageParams.total"
                @current-change="getList"
                @size-change="getList"
            />
        </div>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button
                    type="primary"
                    @click="onSubmit"
                    :loading="btnLoading"
                >
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { listDbTable, importTable } from "@/api/generator";
import { reactive, ref, computed, watch, toRefs } from "vue";
const emit = defineEmits(["update:visible", "callback"]);
const props = defineProps({
    visible: {
        type: Boolean,
        default: false,
    },
});
const btnLoading = ref(false);
const loadingFlag = ref(false);

const data = reactive({
    list: [],
    selectList: [],
    pageParams: {
        pageNum: 1,
        pageSize: 10,
        total: null,
    },
    queryParams: {
        tableName: "",
        tableComment: "",
    },
});

const { list, selectList, pageParams, queryParams } = toRefs(data);

const dialogVisible = computed({
    get() {
        return props.visible;
    },
    set(value) {
        emit("update:visible", value);
    },
});

watch(dialogVisible, (val) => {
    if (val) {
        list.value = [];
        pageParams.value = {
            pageNum: 1,
            pageSize: 10,
            total: 0,
        };
        getList();
    }
});

const getList = () => {
    loadingFlag.value = true;
    selectList.value = [];
    listDbTable({
        ...pageParams.value,
        ...queryParams.value,
    })
        .then((res) => {
            list.value = res.rows;
            pageParams.value.total = res.total;
            loadingFlag.value = false;
        })
        .catch((res) => {
            loadingFlag.value = false;
        });
};

const reset = () => {
    queryParams.value = {
        tableName: "",
        tableComment: "",
    };
    getList();
};

const handleSelectionChange = (items) => {
    selectList.value = items;
};

const onSubmit = () => {
    let selectArray = [];
    selectList.value.forEach((item) => {
        selectArray.push(item.tableName);
    });

    importTable({
        tables: selectArray.join(","),
    })
        .then((res) => {
            emit("callback");
            dialogVisible.value = false;
            loadingFlag.value = false;
        })
        .catch((res) => {
            loadingFlag.value = false;
        });
};
</script>

<style lang="scss" scoped>
</style>