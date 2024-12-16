<template>
  <div class="common-layout">
    <el-container>
      <!-- 左侧菜单 -->
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

      <!-- 主内容区 -->
      <el-container>
        <el-main>
          <div class="profile-section">
            <!-- 头像区域 -->
            <div class="avatar-container">
              <img
                src="https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png"
                style="width: 150px; height: 150px;"
                alt="用户头像"
              />
            </div>

            <el-divider border-style="double" />

            <!-- 用户信息 -->
            <div class="profile-details">
              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">姓名</el-text>
                </div>
                <div class="item-value">{{ userInfo.username }}</div>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">用户ID</el-text>
                </div>
                <div class="item-value">{{ userInfo.id }}</div>
                <el-button plain class="copy-btn" @click="copyToClipboard(userInfo.id)">复制</el-button>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">手机号</el-text>
                </div>
                <div class="item-value">{{ userInfo.phoneNumber }}</div>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">邮箱</el-text>
                </div>
                <div class="item-value">{{ userInfo.email }}</div>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">查看历史记录</el-text>
                </div>
                <div class="item-value"></div>
                <el-button plain class="view-history-btn" @click="viewUserImages">查看</el-button>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">账号注销</el-text>
                </div>
                <div class="item-value">{{ userInfo.cancellation }}</div>
                <el-button type="danger" plain class="cancel-btn" @click="confirmCancellation">注销</el-button>
              </div>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      userInfo: {
        username: "",
        userId: "",
        phone: "",
        email: "",
        cancellation: "注销后不可恢复，请谨慎操作",
      },
    };
  },
  created() {
    // 页面加载时获取用户信息
    this.getUserInfo();
  },
  methods: {
    // 获取用户信息
    getUserInfo() {
      const userId = localStorage.getItem("userId");
      if (!userId) {
        this.$message.error("Please login first！");
        return;
      }
      axios
        .get("http://localhost:8080/api/user/" + userId)
        .then((response) => {
          const data = response.data;
          console.log("用户信息：", data);
          if (data.code) {
            this.userInfo = {
              id: data.data.userId,
              username: data.data.username,
              email: data.data.email,
              phoneNumber: data.data.phone,
            };
          } else {
            console.error("获取用户信息失败：", data.msg);
            this.$message.error(data.msg || "获取用户信息失败！");
          }
        })
        .catch((error) => {

          console.error("获取用户信息失败：", error);
          this.$message.error("获取用户信息失败，请稍后重试！");
        });
    },

    // 复制用户ID
    copyToClipboard(value) {
      navigator.clipboard
        .writeText(value)
        .then(() => {
          this.$message.success("已复制到剪贴板！");
        })
        .catch(() => {
          this.$message.error("复制失败，请手动复制！");
        });
    },

    // 查看历史记录
    viewUserImages() {
      const userId = localStorage.getItem("userId");
      axios
        .get("http://localhost:8080/api/history/" + userId + "?page=1&size=10", {
          headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
        })
        .then((response) => {
          const images = response.data.data;
          console.log("历史记录：", images);
          if (Array.isArray(images)) {
            this.$router.push({
              name: "UserImages",
              query: { images: encodeURIComponent(JSON.stringify(images)) },
            });
          } else {
            this.$message.error("历史记录数据格式不正确！");
          }
        })
        .catch((error) => {
          console.error("获取历史记录失败：", error);
          this.$message.error("获取历史记录失败，请稍后重试！");
        });
    },

    // 确认注销账号
    confirmCancellation() {
      this.$confirm("确定要注销账号吗？此操作不可逆！", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios
            .post("/api/cancel-account", {}, {
              headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
            })
            .then(() => {
              this.userInfo.cancellation = "已注销";
              this.$message.success("账号已注销！");
              this.$router.push("/login");
            })
            .catch(() => {
              this.$message.error("注销失败，请稍后重试！");
            });
        })
        .catch(() => {
          this.$message.info("已取消注销操作！");
        });
    },
  },
};
</script>
<style scoped>
.common-layout {
  min-height: 100vh;
  padding : 100px;
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
  display: inline-block;
  margin-right: 10px;
}


.copy-btn {
  float: right;
}


.cancel-btn {
  float: right;
}

.edit-input {
  margin-right: 10px;
  width: 200px;
}

.view-history-btn {
  float: right;
}
</style>