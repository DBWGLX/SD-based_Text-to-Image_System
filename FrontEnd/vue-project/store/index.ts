// 导入 vuex 模块
import { createStore } from "vuex";
// 定义实体（存储结构）
export interface MyStore {
  // 控制侧边栏收缩
  collapse: boolean;
}

// 创建store对象
export const store = createStore<MyStore>({
  // 初始状态
  state: {
    collapse: false, // 侧边栏收缩
  },
  //  mutations 用于更改 Vuex 的 store 中的状态
  mutations: {
    toggleCollapse(state: MyStore) {
      state.collapse = !state.collapse;
    },
  },
  // actions 用于提交 mutations，可以包含异步操作
  actions: {
    toggleCollapse({ commit }) {
      commit("toggleCollapse");
    },
  },
  // getters 用于获取状态的某些派生数据
  getters: {
    // 侧边栏是否收缩
    isCollapse(state: MyStore) {
      return state.collapse;
    },
  },
});
// 暴露
export default store;
