<template>
  <div class="user-images-page">
    <h1>用户历史图片记录</h1>
    <div class="image-container">
      <!-- 动态渲染图片 -->
      <div v-for="image in images" :key="image.id" class="image-item">
        <img :src="image.imageUrl" :alt="image.description || '图片'" />
        <p>{{ image.description || '暂无描述' }}</p>
      </div>
      <!-- 无数据时显示提示 -->
      <p v-if="!images.length">暂无历史记录</p>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      images: [], // 图片数据
    };
  },
  created() {
    this.fetchHistory(); // 页面加载时获取历史记录
  },
  methods: {
    // 获取历史记录
    async fetchHistory() {
      try {
        const token = localStorage.getItem("token"); // 从本地存储获取 JWT
      
        const response = await axios.get("/api/history", {
          headers: { token },
        });

        // 校验返回结果格式
        if (response.data.code) {
          this.images = response.data.data || []; // 确保 data 是数组
        } else {
          console.error("获取历史记录失败：", response.data.msg);
          alert(`获取历史记录失败：${response.data.msg}`);
        }
      } catch (error) {
        console.error("请求历史记录时出错：", error);
        alert("获取历史记录失败，请检查网络或重新登录");
      }
    },
  },
};
</script>
<style scoped>
.user-images-page {
  text-align: center;
  padding: 20px;
}

.image-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
}

.image-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 200px;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

img {
  max-width: 100%;
  height: auto;
  max-height: 150px;
  object-fit: cover;
  margin-bottom: 10px;
  border-radius: 4px;
}
</style>
