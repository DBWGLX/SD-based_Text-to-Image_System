<script setup>

</script>

<template>
  <div class="container">
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