<template>
    <el-drawer
        v-model="drawerVisible"
        direction="rtl"
        :close-on-click-modal="false"
        append-to-body
        size="80%"
    >
        <template #header>
            <h4>维护数据字典</h4>
        </template>
        <template #default>
            <el-container>
                <el-aside width="400px">
                    <div class="display-flow padding-tb-xs padding-lr">
                        <el-button
                            :icon="Plus"
                            size="small"
                            type="primary"
                            class="fr"
                            @click="newDictType"
                            >新增类型
                        </el-button>
                    </div>
                    <div>
                        <el-tree
                            :data="dictTypeList"
                            :props="treeProps"
                            @node-click="handleNodeClick"
                        >
                            <template #default="{ data }">
                                <div class="text-bold">{{ data.dictName }}</div>
                                <div class="margin-left-xs text-gray">
                                    {{ data.dictType }}
                                </div>
                            </template>
                        </el-tree>
                    </div>
                </el-aside>
                <el-main>
                    <el-card
                        class="box-card"
                        v-if="Object.keys(selectItem).length > 0"
                    >
                        <template #header>
                            <div class="card-header">
                                <el-tag
                                    :type="
                                        selectItem.status == 0
                                            ? 'success'
                                            : 'danger'
                                    "
                                    effect="dark"
                                >
                                    {{
                                        selectItem.status == 0 ? "正常" : "停用"
                                    }}
                                </el-tag>
                                <span class="text-bold margin-left-xs">{{
                                    selectItem.dictName
                                }}</span>
                                <span class="margin-left-xs text-gray">{{
                                    selectItem.dictType
                                }}</span>

                                <span class="fr">
                                    <el-button
                                        type="primary"
                                        :icon="Edit"
                                        @click="editDictType"
                                        >修改</el-button
                                    >
                                    <el-popconfirm
                                        :title="
                                            '是否删除【' +
                                            selectItem.dictName +
                                            '】字典信息？'
                                        "
                                        width="256"
                                        @confirm="deleteDictType"
                                    >
                                        <template #reference>
                                            <el-button
                                                :icon="Delete"
                                                type="danger"
                                            >
                                                删除
                                            </el-button>
                                        </template>
                                    </el-popconfirm>
                                </span>
                            </div>
                        </template>
                        <div class="display-flow padding-tb-xs">
                            <el-button
                                :icon="Plus"
                                size="small"
                                type="primary"
                                class="fr"
                                @click="newDictData"
                                >新增明细
                            </el-button>
                        </div>

                        <el-table
                            v-loading="loadingFlag"
                            :data="dictDataList"
                            border
                            stripe
                        >
                            <el-table-column
                                label="字典编码"
                                align="center"
                                prop="dictCode"
                                width="85"
                            />
                            <el-table-column
                                label="字典标签"
                                align="center"
                                prop="dictLabel"
                                min-width="120"
                            >
                                <template #default="scope">
                                    <span
                                        v-if="
                                            scope.row.listClass == '' ||
                                            scope.row.listClass == 'default'
                                        "
                                        >{{ scope.row.dictLabel }}
                                    </span>
                                    <el-tag
                                        v-else
                                        :type="
                                            scope.row.listClass == 'primary'
                                                ? ''
                                                : scope.row.listClass
                                        "
                                        >{{ scope.row.dictLabel }}
                                    </el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column
                                label="字典键值"
                                align="center"
                                prop="dictValue"
                                min-width="120"
                            />
                            <el-table-column
                                label="字典排序"
                                align="center"
                                prop="dictSort"
                                width="85"
                            />
                            <el-table-column
                                label="状态"
                                align="center"
                                prop="status"
                            >
                                <template #default="scope">
                                    <el-tag
                                        :type="
                                            scope.row.status == 0
                                                ? 'success'
                                                : 'danger'
                                        "
                                        effect="dark"
                                    >
                                        {{
                                            scope.row.status == 0
                                                ? "正常"
                                                : "停用"
                                        }}
                                    </el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column
                                label="备注"
                                align="center"
                                prop="remark"
                                :show-overflow-tooltip="true"
                                min-width="120"
                            />
                            <el-table-column
                                label="创建时间"
                                align="center"
                                prop="createTime"
                                width="180"
                            >
                                <template #default="scope">
                                    <span>{{ scope.row.createTime }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column
                                label="操作"
                                align="center"
                                class-name="small-padding fixed-width"
                                width="130"
                                fixed="right"
                            >
                                <template #default="scope">
                                    <el-button
                                        type="primary"
                                        size="small"
                                        link
                                        :icon="Edit"
                                        @click="editDictData(scope.row)"
                                        >修改</el-button
                                    >
                                    <el-popconfirm
                                        :title="
                                            '是否删除【' +
                                            scope.row.dictLabel +
                                            '】字典标签？'
                                        "
                                        width="256"
                                        @confirm="deleteDictData(scope.row)"
                                    >
                                        <template #reference>
                                            <el-button
                                                type="primary"
                                                size="small"
                                                link
                                                :icon="Delete"
                                                @click=""
                                                >删除</el-button
                                            >
                                        </template>
                                    </el-popconfirm>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-card>
                    <el-empty description="无数据字典信息" v-else />
                </el-main>
            </el-container>
        </template>
        <template #footer>
            <div style="flex: auto">
                <el-button type="danger" @click="drawerVisible = false">
                    <el-icon class="el-icon--left"
                        ><CircleCloseFilled
                    /></el-icon>
                    关闭
                </el-button>
            </div>
        </template>
    </el-drawer>
    <DictTypeForm
        v-model:visible="dictTypeFormVisible"
        :form="dictTypeFormData"
        :isNew="dictTypeFormIsNew"
        @callback="getDictTypeList"
    />
    <DictDataForm
        v-model:visible="dictDataFormVisible"
        :form="dictDataFormData"
        :isNew="dictDataFormIsNew"
        @callback="getDictDataList"
    />
</template>

<script lang="ts" setup>
import DictTypeForm from "./DictTypeForm.vue";
import DictDataForm from "./DictDataForm.vue";
import { optionselect, delType } from "@/api/dictType";
import { listData, delData } from "@/api/dictData";
import { reactive, ref, computed, watch, toRefs } from "vue";
import { Plus, Edit, Delete } from "@element-plus/icons-vue";
const emit = defineEmits(["update:visible", "callback"]);
const props = defineProps({
    visible: {
        type: Boolean,
        default: false,
    },
});

const loadingFlag = ref(false);
const dictTypeFormVisible = ref(false);
const dictDataFormVisible = ref(false);
const dictTypeFormIsNew = ref(true);
const dictDataFormIsNew = ref(true);

const data = reactive({
    dictTypeList: [],
    selectItem: {},
    dictDataList: [],
    dictTypeFormData: {},
    dictDataFormData: {},
});

const {
    dictTypeList,
    selectItem,
    dictDataList,
    dictTypeFormData,
    dictDataFormData,
} = toRefs(data);

const treeProps = {
    children: "children",
    label: "dictName",
};

const drawerVisible = computed({
    get() {
        return props.visible;
    },
    set(value) {
        emit("update:visible", value);
    },
});

watch(drawerVisible, (val) => {
    if (val) {
        getDictTypeList();
    }
});

const getDictTypeList = () => {
    optionselect().then((res) => {
        dictTypeList.value = res.data;
        if (res.data.length > 0) {
            if (Object.keys(selectItem.value).length == 0) {
                selectItem.value = res.data[0];
            } else {
                res.data.forEach((item) => {
                    if (item.dictId == selectItem.value.dictId) {
                        selectItem.value = item;
                    }
                });
            }
            getDictDataList();
        }
    });
};

const getDictDataList = () => {
    loadingFlag.value = true;
    listData({
        dictType: selectItem.value.dictType,
    })
        .then((res) => {
            dictDataList.value = res.rows;
            loadingFlag.value = false;
        })
        .catch((res) => {
            loadingFlag.value = false;
        });
};

const handleNodeClick = (data) => {
    selectItem.value = data;
    getDictDataList();
};

const deleteDictType = () => {
    delType(selectItem.value.dictId).then((res) => {
        selectItem.value = {};
        getDictTypeList();
    });
};

const newDictType = () => {
    dictTypeFormIsNew.value = true;
    dictTypeFormData.value = {
        dictName: "",
        dictType: "",
        status: "0",
        remark: "",
    };
    dictTypeFormVisible.value = true;
};

const editDictType = () => {
    dictTypeFormIsNew.value = false;
    dictTypeFormData.value = JSON.parse(JSON.stringify(selectItem.value));
    dictTypeFormVisible.value = true;
};

const newDictData = () => {
    dictDataFormIsNew.value = true;
    dictDataFormData.value = {
        dictName: selectItem.value.dictName,
        dictType: selectItem.value.dictType,
        dictLabel: "",
        dictValue: "",
        cssClass: "",
        dictSort: "",
        listClass: "",
        status: "0",
        remark: "",
    };
    dictDataFormVisible.value = true;
};
const editDictData = (item) => {
    dictDataFormIsNew.value = false;
    dictDataFormData.value = {
        dictName: selectItem.value.dictName,
        ...JSON.parse(JSON.stringify(item)),
    };
    dictDataFormVisible.value = true;
};
const deleteDictData = (item) => {
    delData(item.dictCode).then((res) => {
        getDictDataList();
    });
};
</script>

<style lang="scss" scoped>
.el-drawer__header {
    margin-bottom: 0px;
}
.el-aside {
    height: calc(100vh - 256px);
}

.el-main {
    height: calc(100vh - 256px);
}
</style>