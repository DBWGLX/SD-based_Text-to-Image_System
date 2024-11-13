<template>
    <div class="register">
        <h2>用户注册</h2>
        <form @submit.prevent="onRegister">
            <div>
                <label for="username">用户名:</label>
                <input type="text" v-model="username" required />
            </div>
            <div>
                <label for="password">密码:</label>
                <input type="password" v-model="password" required />
            </div>
            <div>
                <label for="confirmPassword">确认密码:</label>
                <input type="password" v-model="confirmPassword" required />
            </div>
            <div>
                <label for="email">邮箱:</label>
                <input type="text" v-model="email" required />
            </div>
            <div style="display: flex;justify-content: space-between;">
                <button style="width: 100%;margin-right: 4px;" @click="() => { goLogin() }">取消</button>
                <button style="width: 100%;margin-left: 4px;" type="submit">注册</button>
            </div>
        </form>
        <p v-if="errorMessage">{{ errorMessage }}</p>
    </div>
</template>

<script>
import { registerApi } from "@/api/auth";
export default {
    data() {
        return {
            username: '',
            password: '',
            confirmPassword: '',
            email: '',
            errorMessage: '',
        };
    },
    methods: {
        onRegister() {
            if (this.username !== '' && this.password !== '' && this.confirmPassword !== '' && this.email !== '') {
                if (this.password !== this.confirmPassword) {
                    this.errorMessage = '两次密码不一致';
                } else {
                    registerApi({
                        username: this.username,
                        password: this.password,
                        email: this.email,
                    })
                        .then((response) => {
                            this.errorMessage = '注册成功';
                        })
                        .catch((error) => {
                            this.errorMessage = error;
                        });
                }
            }
        },
        goLogin() {
            this.$router.push('/login');
        }
    },
}
</script>

<style scoped>
.register {
    max-width: 400px;
    margin: auto;
    text-align: center;

    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    background-color: #f9f9f9;
}

form {
    display: flex;
    flex-direction: column;
    gap: 10px;
}


input {
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 16px;
    transition: border-color 0.3s;
}

input:focus {
    border-color: royalblue;
    outline: none;
}

button {
    padding: 10px;
    background-color: royalblue;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
}

button:hover {
    background-color: #0074b5;
}
</style>