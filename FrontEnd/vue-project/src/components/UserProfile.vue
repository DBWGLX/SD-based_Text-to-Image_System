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
        <!-- <el-header>
          用户中心
        </el-header> -->

        <el-main>
          <div class="profile-section">
            <div class="avatar-container">
              <!-- 头像 -->
              <img src="https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png"
                style="width: 150px; height: 150px;">
              <el-button plain class="edit-avatar-btn" @click="showEditAvatarModal">修改头像</el-button>
            </div>

            <el-divider border-style="double" />

            <div class="profile-details">
              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">姓名</el-text>
                </div>
                <div class="item-value">{{ editingName ? '' : userInfo.username }}</div>
                <el-button plain class="edit-btn" @click="toggleEditName">编辑</el-button>
                <el-input v-if="editingName" v-model="newName" placeholder="请输入新姓名" class="edit-input"
                  @keyup.enter="saveName"></el-input>
                <el-button v-if="editingName" type="primary" @click="saveName">保存</el-button>
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
                <el-button plain class="bind1-btn" @click="bindPhoneNumber">立即绑定</el-button>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">邮箱</el-text>
                </div>
                <div class="item-value">{{ userInfo.email }}</div>
                <el-button plain class="bind2-btn" @click="bindEmail">立即绑定</el-button>
              </div>

              <el-divider border-style="double" />

              <div class="profile-item">
                <div class="item-title">
                  <el-text class="mx-1">密码</el-text>
                </div>
                <div class="item-value">{{ userInfo.password }}</div>
                <el-button plain class="passward-btn" @click="setPassword">设置密码</el-button>
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
import axios from 'axios';
import { useRouter } from 'vue-router';

export default {
  data() {
    return {
      userInfo: {
        username: "",
        id: "",
        phoneNumber: "",
        email: "",
        password: "",
        cancellation: "注销后不可恢复，请谨慎操作"
      },
      editingName: false,
      newName: ''
    };
  },
  created() {
  this.getUserInfo();
},
methods: {
  getUserInfo() {
    const token = localStorage.getItem('token');
    if (token) {
      // 通过解码 token 获取用户信息
      try {
        const decoded = jwt_decode(token); // 使用 jwt-decode 解码 token
        // 如果 token 解码成功，就将用户信息保存到 `userInfo`
        this.userInfo = {
          id: decoded.id,
          username: decoded.username,
          email: decoded.email,
          password: decoded.password
        };
      } catch (error) {
        console.error("无效的 token", error);
        this.$message.error("无效的 token，请重新登录");
        this.$router.push('/login'); // 跳转到登录页
      }
    } else {
      // this.$router.push('/login'); // 如果没有 token，跳转到登录页
    }
  },
    showEditAvatarModal() {
      console.log("显示修改头像模态框");
    },
    toggleEditName() {
      this.editingName = !this.editingName;
    },
    saveName() {
      this.userInfo.name = this.newName;
      this.editingName = false;
      this.newName = '';
      this.saveUserInfo();
    },
    copyToClipboard(text) {
      navigator.clipboard.writeText(text).then(() => {
        this.$message.success("已复制到剪贴板");
      }).catch(err => {
        this.$message.error("复制失败，请重试");
      });
    },
    bindPhoneNumber() {
      this.$prompt('请输入手机号', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^1[3-9]\d{9}$/,
        inputErrorMessage: '手机号格式不正确'
      }).then(({ value }) => {
        axios.post('/api/bind-phone-number', { phoneNumber: value })
          .then(response => {
            this.userInfo.phoneNumber = value;
            this.$message.success('手机号绑定成功');
          })
          .catch(error => {
            this.$message.error('手机号绑定失败，请重试');
          });
      }).catch(() => {
        this.$message.info('已取消绑定');
      });
    },
    bindEmail() {
      this.$prompt('请输入邮箱地址', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
        inputErrorMessage: '邮箱格式不正确'
      }).then(({ value }) => {
        axios.post('/api/bind-email', { email: value })
          .then(response => {
            this.userInfo.email = value;
            this.$message.success('邮箱绑定成功');
          })
          .catch(error => {
            this.$message.error('邮箱绑定失败，请重试');
          });
      }).catch(() => {
        this.$message.info('已取消绑定');
      });
    },
    setPassword() {
      this.$prompt('请输入新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/,
        inputErrorMessage: '密码格式不正确（至少8位，包含大小写字母和数字）'
      }).then(({ value }) => {
        axios.post('/api/set-password', { password: value })
          .then(response => {
            this.userInfo.password = value;
            this.$message.success('密码设置成功');
          })
          .catch(error => {
            this.$message.error('密码设置失败，请重试');
          });
      }).catch(() => {
        this.$message.info('已取消设置');
      });
    },
    confirmCancellation() {
      this.$confirm('确定要注销账号吗？此操作不可逆。', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post('/api/cancel-account')
          .then(response => {
            this.userInfo.cancellation = "已注销";
            this.$message.success('账号已注销');
          })
          .catch(error => {
            this.$message.error('账号注销失败，请重试');
          });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消注销'
        });
      });
    },
    saveUserInfo() {
      axios.post('/api/save-user-info', this.userInfo)
        .then(response => {
          this.$message.success('用户信息保存成功');
        })
        .catch(error => {
          this.$message.error('用户信息保存失败，请重试');
        });
    },
    viewUserImages() {
      axios.get('/api/history', {
  headers: {
    'Authorization': 'Bearer ' + localStorage.getItem('token')
  }
}).then(response => {
  console.log(response.data); // 输出返回的数据
  const images = response.data.data;
  if (Array.isArray(images)) {
    this.$router.push({
      name: 'UserImages',
      query: { images: encodeURIComponent(JSON.stringify(images)) }
    });
  } else {
    this.$router.push({ name: 'UserImages' });
    this.$message.error('历史记录数据格式不正确');
  }
}).catch(error => {
  console.error(error); // 输出错误
  this.$router.push({ name: 'UserImages' });
  this.$message.error('获取历史记录失败，请重试');
});

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
  display: inline-block;
  margin-right: 10px;
}

.edit-btn {
  float: right;
}

.copy-btn {
  float: right;
}

.bind1-btn {
  float: right;
}

.bind2-btn {
  float: right;
}

.passward-btn {
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