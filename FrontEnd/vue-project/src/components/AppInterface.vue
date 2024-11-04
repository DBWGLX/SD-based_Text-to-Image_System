<template>
  <div class="app-interface">
    <div class="header">
      SD-Project
    </div>
    <div class="body">
      <div class="container">
        <div class="upper">
          <div class="prompt">
            <label for="prompt" class="input-label">Prompt:</label>
            <input
              id="prompt"
              type="text"
              v-model="prompt"
              placeholder="Input your prompt"
              class="text-input"
            />
            <label for="negative_prompt" class="input-label">Negative Prompt:</label>
            <input
              id="negative_prompt"
              type="text"
              v-model="negative_prompt"
              placeholder="Input your negative prompt"
              class="text-input"
            />
            <label for="num_inference_steps" class="input-label">Number of Inference Steps:</label>
            <input
              id="num_inference_steps"
              type="number"
              v-model="num_inference_steps"
              placeholder="Number of Inference Steps"
              class="text-input"
              min="1"
            />
            <label for="width" class="input-label">Width:</label>
            <input
              id="width"
              type="number"
              v-model="width"
              placeholder="Width"
              class="text-input"
              min="1"
            />
            <label for="height" class="input-label">Height:</label>
            <input
              id="height"
              type="number"
              v-model="height"
              placeholder="Height"
              class="text-input"
              min="1"
            />
            <label for="seed" class="input-label">Seed (leave blank for random):</label>
            <input
              id="seed"
              type="number"
              v-model="seed"
              placeholder="Seed (leave blank for random)"
              class="text-input"
              min="0"
            />
            <label for="guidance_scale" class="input-label">Guidance Scale:</label>
            <input
              id="guidance_scale"
              type="number"
              v-model="guidance_scale"
              placeholder="Guidance Scale"
              class="text-input"
              min="1"
            />
          </div>
          <div class="submit-button">
            <button @click="submitData" class="generate-button">
              {{ isGenerating ? 'Generating...' : 'Generate' }}
              <!-- 动态按钮 -->    
            </button>
          </div>
        </div>

        <div class="below">
          <div class="result-and-action">
            <div class="result">
              <img :src="imageUrl" alt="生成的图像" />
            </div>
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
      num_inference_steps: 25, // 默认值
      width: 512, // 默认值
      height: 512, // 默认值
      seed: null, // 默认值
      guidance_scale: 7.5, // 默认值
      imageUrl: 'https://webcnstatic.yostar.net/ba_cn_web/prod/upload/wallpaper/dMIq1HzJ.jpeg', // 初始图片 URL
      isGenerating: false, // 用于控制按钮显示
    };
  },
  methods: {
    async submitData() {
      this.isGenerating = true; // 设置为正在生成

      try {
        const response = await fetch('http://172.30.207.108:5000/generate', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            prompt: this.prompt,
            negative_prompt: this.negative_prompt,
            num_inference_steps: this.num_inference_steps,
            width: this.width,
            height: this.height,
            seed: this.seed !== '' ? this.seed : undefined, // 如果 seed 为空则不传递
            guidance_scale: this.guidance_scale,
          }),
        });

        if (!response.ok) {
          throw new Error('网络响应不是一个 200: ${errorText}');
        }

        const imageBlob = await response.blob(); // 将响应解析为 Blob 对象
        this.imageUrl = URL.createObjectURL(imageBlob); // 生成可用于 img 标签的 URL
      } catch (error) {
        console.error('请求失败:', error);
        alert('生成图像失败，请重试' + error.message);
      }finally {
        this.isGenerating = false; // 请求完成后恢复按钮文本
      }
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
  flex: 1;
  display: flex; /* 让 body 使用 flex 布局 */
}

.container {
  display: flex;
  flex: 1;
}

.upper {
  display: flex;
  flex: 1; /* 让上部分占满可用空间 */
}

.prompt {
  display: flex;
  flex: 1; /* 输入框占左半边 */
  flex-direction: column;
  gap: 10px;
}

.input-label {
  font-weight: bold; /* 让标题加粗 */
  margin-bottom: 5px; /* 增加标题和输入框之间的间距 */
}

.text-input {
  box-sizing: content-box;
  width: 100%;
  height: 40px;
}

.submit-button {
  display: flex;
  align-items: center;
  justify-content: center; /* 让按钮居中 */
  padding: 0 20px; /* 给按钮两侧增加一些内边距 */
}

.generate-button {
  height: 80px; /* 增加按钮高度 */
  width: 100%; /* 按钮占满右边 */
  font-size: 24px; /* 增加字体大小 */
}

.below {
  display: flex;
  flex-direction: column; /* 让下半部分竖向排列 */
  flex: 1; /* 让下半部分占满剩余空间 */
}

.result-and-action {
  flex: 1; /* 让结果部分占满剩余空间 */
  display: flex;
  flex-direction: column; /* 让下半部分竖向排列 */
  align-items: center; /* 让子元素居中对齐 */
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
