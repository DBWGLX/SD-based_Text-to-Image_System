<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <el-menu router :default-active="$route.path" mode="vertical">
          <el-menu-item index="/account-settings">
            <span>
              <i class="el-icon-setting"></i>
              <el-text class="mx-1" type="info">账户设置</el-text>
            </span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header>
          用户中心
        </el-header>

        <el-main>
          <div class="profile-section">
            <div class="avatar-container">
              <!-- 头像 -->
              <img  style="width: 150px; height: 150px;">
              <el-button plain class="edit-avatar-btn">修改头像</el-button>
            </div>

            <el-divider border-style="double" />

            <div class="profile-details">
              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">姓名</el-text>
                </div>
                <div class="item-value">{{ userInfo.name }}</div>
                <el-button plain class="edit-btn">编辑</el-button>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">用户ID</el-text>
                </div>
                <div class="item-value">{{ userInfo.id }}</div>
                <el-button plain class="copy-btn">复制</el-button>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">手机号</el-text>
                </div>
                <div class="item-value">{{ userInfo.phoneNumber }}</div>
                <el-button plain class="bind1-btn">立即绑定</el-button>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">微信</el-text>
                </div>
                <div class="item-value">{{ userInfo.weChatStatus }}</div>
                <div class="we-chat-actions">
                  <el-button plain class="change-btn">更换</el-button>
                  <el-button plain class="unbind-btn">解绑</el-button>
                </div>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">邮箱</el-text>
                </div>
                <div class="item-value">{{ userInfo.email }}</div>
                <el-button plain class="bind2-btn">立即绑定</el-button>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">实名认证</el-text>
                </div>
                <div class="item-value">{{ userInfo.authentication }}</div>
                <el-button plain class="authen-btn">认证</el-button>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">密码</el-text>
                </div>
                <div class="item-value">{{ userInfo.passward }}</div>
                <el-button plain class="passward-btn">设置密码</el-button>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">账号注销</el-text>
                </div>
                <div class="item-value">{{ userInfo.cancellation }}</div>
                <el-button type="danger" plain class="cancel-btn">注销</el-button>
              </div>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInfo: {
        name: "微信用户OquH2t8F",
        id: "29988916",
        phoneNumber: "666666",
        weChatStatus: "已绑定",
        email: "1234679",
        authentication: "未认证",
        passward: "464464",
        cancellation: "注销后不可恢复，请谨慎操作"
      }
    };
  },
  methods: {
    closeSidebar() {
      // Implement logic to close the sidebar or navigate away.
    }
  }
};
</script>

<style scoped>
.common-layout {
  min-height: 100vh;
}

.el-container {
  height: 100%;
}

.el-aside {
  background-color: #fff;
}

.el-header {
  background-color: #fff;
  line-height: 60px;
  text-align: center;
  font-weight: bold;
  font-size: 20px;
}

.el-main {
  padding: 20px;
}

.profile-section {
  max-width: 600px;
  margin: 0 auto;
}

.avatar-container {
  text-align: center;
  margin-bottom: 20px;
}

.edit-avatar-btn {
  float: right;
  margin-top: 10px;
}

.item-title {
  font-weight: bold;
}

.item-value {
  display: inline-block; /* 让值和按钮在同一行 */
  margin-right: 10px; /* 增加间距 */
}

.edit-btn{
  float: right;
}
.copy-btn {
  float: right;
}


.bind1-btn {
  float : right;
}

.we-chat-actions {
  display: flex;
  justify-content: flex-end; /* 将按钮移到最右边 */
  gap: 3px; /* 按钮之间的间距 */
}

/* .bind1-btn, .change-btn {
  
} */

.bind2-btn {
  float: right;
}

.authen-btn {
  float: right;
}

.passward-btn {
  float: right;
}

.cancel-btn {
  float: right;
} 

.unbind-btn {
  margin-left: 10px;
}


</style>