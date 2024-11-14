<template>
  <div class="container">
    <nav class="side-menu">
      <ul>
        <li :class="{ active: activeTab === 'account-settings' }" @click="activeTab = 'account-settings'">
          <a href="#/account-settings"><i class="fas fa-cog"></i>账户设置</a>
        </li>
      </ul>
    </nav>
    
    <main class="content-area">
      <header class="page-header">
        <h2>用户中心</h2>
        <button class="close-button" @click="$emit('close')">关闭</button>
      </header>
      
      <section class="profile-section" v-if="activeTab === 'account-settings'">
        <div class="avatar-container">
          <img :src="userInfo.avatar" alt="User Avatar" />
          <label for="avatar-upload" class="upload-label">修改头像</label>
          <input type="file" id="avatar-upload" accept=".jpg,.jpeg,.png,.svg" @change="handleAvatarUpload" />
        </div>
        
        <dl class="profile-details">
          <dt>姓名</dt>
          <dd>{{ userInfo.name }} <span>(微信用户{{ userInfo.weixinId }})</span></dd>
          
          <dt>用户ID</dt>
          <dd>{{ userInfo.userId }}</dd>
          
          <dt>手机</dt>
          <dd>{{ userInfo.phone ? userInfo.phone : '未绑定' }}</dd>
          
          <dt>微信</dt>
          <dd>{{ userInfo.isWeixinBound ? '已绑定' : '未绑定' }}</dd>
          
          <dt>邮箱</dt>
          <dd>{{ userInfo.email ? userInfo.email : '未绑定' }}</dd>
        </dl>
      </section>
      
    </main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeTab: 'account-settings',
      userInfo: {},
    };
  },
  mounted() {
    this.fetchUserInfo();
  },
  methods: {
    fetchUserInfo() {
      // 获取用户信息，可以是从服务器获取，也可以是模拟数据
      // 示例：假设用户信息存储在localStorage中
      const isAuthenticated = localStorage.getItem('isAuthenticated');
      if (isAuthenticated) {
        this.userInfo = JSON.parse(localStorage.getItem('userInfo')) || {};
      }
    },
    handleAvatarUpload(event) {
      // 处理头像上传事件
      const file = event.target.files[0];
      if (!file) return;

      // 这里可以添加上传文件到服务器的逻辑
      console.log('Selected avatar:', file);
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
}

.side-menu {
  width: 200px;
  background: #f8fafc;
  padding: 20px;
  border-right: 1px solid #ddd;
}

.side-menu ul {
  list-style-type: none;
  padding: 0;
}

.side-menu li {
  margin-bottom: 10px;
}

.side-menu li.active {
  background: #e0e0e0;
}

.content-area {
  flex-grow: 1;
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.close-button {
  background: transparent;
  border: none;
  color: #aaa;
  font-size: 20px;
  cursor: pointer;
}

.avatar-container {
  position: relative;
  width: 100px;
  height: 100px;
  overflow: hidden;
  border-radius: 50%;
  margin-bottom: 20px;
}

.upload-label {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5);
  opacity: 0;
  transition: opacity 0.3s;
  color: white;
  cursor: pointer;
}

.upload-label:hover {
  opacity: 1;
}

.profile-details dt {
  font-weight: bold;
  margin-bottom: 5px;
}

.profile-details dd {
  margin-bottom: 10px;
}

</style>