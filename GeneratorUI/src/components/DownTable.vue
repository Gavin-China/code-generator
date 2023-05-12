<template>
    <el-card class="box-card" style="margin: 0 auto; max-width: 90%">
        <template #header>
            <div class="card-header">
                <el-icon class="margin-right-sm"><Download /></el-icon>
                <span>生成代码</span>
                <div class="fr">
                    <el-button @click="emit('preClick')" :icon="RefreshLeft"
                        >重新配置表</el-button
                    >
                    <el-button
                        type="primary"
                        @click="downloadZip"
                        :icon="Download"
                    >
                        下载代码
                    </el-button>
                </div>
            </div>
        </template>
        <el-tabs tab-position="left">
            <el-tab-pane
                v-for="(key, i) in Object.keys(detail)"
                :key="i"
                :label="key"
            >
                <Codemirror
                    v-model:value="detail[key]"
                    :options="cmOptions"
                    border
                />
            </el-tab-pane>
        </el-tabs>
    </el-card>
</template>

<script lang="ts" setup>
import "codemirror/mode/javascript/javascript.js";
import "codemirror/addon/display/autoRefresh.js";
import "codemirror/theme/material.css";

import Codemirror from "codemirror-editor-vue3";
import type { EditorConfiguration } from "codemirror";
import { Download, RefreshLeft } from "@element-plus/icons-vue";
import { reactive, ref, getCurrentInstance } from "vue";
import { previewTable } from "@/api/generator";
const { proxy } = getCurrentInstance();
const emit = defineEmits(["preClick"]);

const props = defineProps({
    tableId: Number,
    tableName: String,
});

let detail = ref({});

previewTable(props.tableId).then((res) => {
    detail.value = res.data;
});

const cmOptions: EditorConfiguration = {
    mode: "text/javascript",
    theme: "material",
    autoRefresh: true,
    lineNumbers: true,
    maxHighlightLength: Infinity,
    lineWrapping: true,
};

const downloadZip = () => {
    proxy.download.zip(
        "/generator/batchGenCode?tables=" + props.tableName,
        "generator.zip"
    );
};
</script>

<style lang="scss" scoped></style>
