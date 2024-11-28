// 图标组件定义 Collaspe.vue

<script setup lang="ts">
// 导入usestore的方法
import { useStore } from "vuex";
// 获取当前vuex的store对象
const store = useStore();
// 导入计算
import { computed } from "vue";
// 获取store->state中的collapse值
const isCollapse = computed(() => {
  return store.getters["isCollapse"];
});
import { Fold, Expand } from "@element-plus/icons-vue";

// 点击切换收缩事件
const changeCollapse = () => {
  // 切换collapse的值
  store.commit("toggleCollapse");
};
// 使用三元表达式判断收缩图标，此时全局定义的icon图标将失效需重新导入
</script>

<template>
  <!-- 使用全局定义的折叠图标 -->
  <el-icon>
    <component
      class="icons"
      :is="isCollapse ? Fold : Expand"
      @click="changeCollapse"
    ></component>
  </el-icon>
</template>

<style scoped>
.el-icon {
  font-style: 22px;
  margin-right: 10px;
}
</style>
