<script setup>

</script>

<template>
  <div class="container">
    <header>
      <nav class="navbar">
        <div class="logo">
          <h2>SD Project</h2>
        </div>
        <ul class="menu">
          <li><a href="./">Home</a></li>
          <li><a href="app">App</a></li>
          <li><a href="#About">About</a></li>
          <li><a href="#contact">Contact</a></li>
        </ul>
        <div class="auth-buttons">
          <button class='button-primary' @click="signIn">Sign in</button>
          <button class='button-secondary' @click="login">Log in</button>
        </div>
      </nav>
    </header>
    
    <main>
      <div>
        <h1>Welcome to SD project</h1>
        <p>Stable Diffusion is a latent text-to-image diffusion model capable of generating photo-realistic 
          images given any text input, cultivates autonomous freedom to produce incredible imagery, empowers
          billions of people to create stunning art within seconds.Create beautiful art using stable diffusion ONLINE for free.
        </p>
        <TheWelcome />
      </div>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const signIn = () => {
  router.push('/signup'); // 跳转到注册页面
};

const login = () => {
  router.push('/login'); // 跳转到登录页面
};
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  /* justify-content: space-between; */
  height: 100vh;
}

.container header {
  display: flex;

}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  /* justify-content: center; */
  padding: 0;
  height: 100vh;
  text-align: center;
}

header {
  width: 100%;
  /* background-color: #f8f9fa; */
  padding: 10px 0;
  display: flex;
  /* justify-content: center; */
  /* border-bottom: 1px solid #ddd; */
}

.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 25px 50px;
  /* max-width: 1200px; */
}

.logo h2 {
  margin: 0;
  color: #333;
}

.menu {
  list-style-type: none;
  display: flex;
  gap: 20px;
  margin-left: 16px;
  padding: 0;
}

.menu li a {
  text-decoration: none;
  color: #007bff;
  font-weight: bold;
}

.auth-buttons {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-left: auto;
}

.auth-buttons button {
  margin-left: 10px;
  padding: 5px 10px;
  /* border: none; */
  /* background-color: #007bff;
  color: #fff; */
  cursor: pointer;
  border-radius: 5px;
  font-size: 1rem;
}

.button-primary {
  background-color: #007bff;
  color: #fff;
  border: 1px solid #fff;
}

.button-secondary {
  background-color: #fff;
  color: #007bff;
  border: 1px solid #007bff ;
}

.auth-buttons button:hover {
  background-color: 1px solid #0056b3;
}

main {
  margin: auto;
}

main div {
  margin: 0px 480px;
  display: flex;
  flex-direction: column;
  gap: 30px;
}

h1 {
  flex-basis: 100%;
  font-size: 72px;
}

p {
  flex-basis: 100%;
}
</style>