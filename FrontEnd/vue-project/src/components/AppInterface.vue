<template>
  <div class="app-interface">
    <div class="header">
      SD-Project
    </div>
    <div class="body">
      <div class="container">
        <div class="upper">
          <div class="prompt">
            <input
            type="text"
            v-model="prompt"
            placeholder="input your prompt"
            class="text-input"
            />
            <input
            type="text"
            v-model="negative_prompt"
            placeholder="input your negative prompt"
            class="text-input"
            />
          </div>
          <div class="submit-button">
            <button @click="submitData">Generate</button>
          </div>
        </div>

        <div class="below">
          <div class="configuration-panel">
            配置参数的面板
          </div>
          <div class="result-and-action">
            <div class="result">
              <img :src="imageUrl" alt="随机抓取的1024*1024图片">
            </div>
            <button>button</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  data() {
    return {
      prompt: '',
      negative_prompt: '',
       imageUrl: 'https://picsum.photos/1024/1024', // 初始图片 URL
    };
  },
  methods: {
    submitData() {
      this.imageUrl =  `https://picsum.photos/1024/1024?random=${Math.random()}`;
    },
  },
};
</script>

<style scoped>
.app-interface {
  padding: 0px;
  display: flex;
  flex-direction: column;
  height: 100vh;
}


.header {
  background: #f8fafc;
  height: 64px;
  font-size: 32px;
  display: flex;
  align-items: center;
  font-weight: 700;
  padding: 20px;
  margin-bottom: 10px;
}

.body {
  /* display: flex; */
  flex: 1;
}

.upper {
  display: flex;
  /* justify-content: space-between; */
  gap: 10px;
  margin-bottom: 10px;
}

.prompt {
  display: flex;
  flex: 1;
  flex-direction: column;
  gap: 10px;
}

.text-input {
  box-sizing: content-box;
  /* margin: 0 30px; */
  width: 100%;
  height: 40px;
}

.submit-button button{
  height: 40px;
}

.below {
  display: flex;
  gap:10px;
}

.configuration-panel {
  width: 800px; 
  background-color: #f0f0f0; 
  border: 1px solid #ccc; 
  border-radius: 8px; 
  padding: 20px; 
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
}

.result-and-action {
  display: flex;
  flex-flow: column;
}

.result {
  flex: 1; 
  display: flex;
  justify-content: center; 
  align-items: center; 
  border: 2px solid #ccc; 
  border-radius: 8px; 
  overflow: hidden; 
  width: 800px; 
  height: 500px; 
}

.result img {
  max-width: 100%;
  max-height: 100%; 
  width: auto; 
  height: auto; 
}


</style>
