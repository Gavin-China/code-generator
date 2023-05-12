<template>
    <div>
        <Background />
        <div class="context">
            <div class="component">
                <Index class="padding-xl" />
            </div>
        </div>
        <el-backtop :right="100" :bottom="100" />
    </div>
</template>

<script setup>
import Background from "./components/Background.vue";
import Index from "./components/Index.vue";
import { useDark, useToggle } from "@vueuse/core";

const isDark = useDark({
    selector: "html",
    valueDark: "dark",
    valueLight: "light",
});
const toggleDark = useToggle(isDark);
</script>

<style scoped>
.context {
    position: absolute;
    top: 0;
}
.component {
    position: relative;
    min-width: 100vw;
    min-height: 100vh;
}
</style>
