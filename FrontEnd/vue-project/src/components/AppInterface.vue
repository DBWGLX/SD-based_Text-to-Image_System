<template>
  <div class="app-interface">
    <div class="header">
      SD-Project
    </div>
    <div class="body">
      <div class="container">
        <div class="left">
          <div class="prompt">
            <label for="prompt" class="input-label">Prompt:</label>
            <!-- image的prompt -->
            <input
              id="prompt"
              type="text"
              v-model="prompt"
              placeholder="Input your prompt, like: masterpiece, best quality,"
              class="text-input"
            />
            <!-- image的negative_prompt -->
            <label for="negative_prompt" class="input-label">Negative Prompt:</label>
            <input
              id="negative_prompt"
              type="text"
              v-model="negative_prompt"
              placeholder="Input your negative prompt, like: lowres, bad anatomy, bad hands, text, error, mssing fingers,extra digits, fewer digits, cropped, worst quality,low quality,normal quality,jpeg artifacts, signature, watermark,username, blurry"
              class="text-input"
            />
            <!-- image的num_inference_steps -->
            <div class="input-group">
              <label for="num_inference_steps" class="input-label">Number of Inference Steps:</label>
              <input
                type="number"
                v-model="num_inference_steps"
                min="1"
                max="50"
                step="1"
                class="control-input"
              />    
              </div>
            <input
              id="num_inference_steps"
              type="range"
              v-model="num_inference_steps"
              placeholder="Number of Inference Steps"
              class="text-input"
              min="1"
              max="50"
              step="1"
            />
            <!-- image的宽度 -->
            <!-- 确保label和control-input在同一行 -->
            <div class="input-group">
              <label for="width" class="input-label">Width:</label>
              <input
                type="number"
                v-model="width"
                class="control-input"
                min="1"
                max="1920"
              />
            </div>
            <input
              id="width"
              type="range"
              v-model="width"
              class="text-input"
              min="1"
              max="1920"
              step="1"
            />
            <!-- image的高度 -->
            <div class="input-group">
                <label for="height" class="input-label">Height:</label>
                <input
                  id="height"
                  type="number"
                  v-model="height"
                  class="control-input"
                  min="1"
                  max="1920"
                />
            </div>
            <input
                  id="height"
                  type="range"
                  v-model="height"
                  class="text-input"
                  min="1"
                  max="1920"
            />
            <!-- img 的seed -->
            <label for="seed" class="input-label">Seed (leave blank for random):</label>
            <div class="seed-group">
              <input
                id="seed"
                type="number"
                v-model="seed"
                class="text-input"
                min="0"
                max="999999"
              />
              <button class="input-button" @click="generateRandomSeed"><img :src="rollingDiceIconPath" alt="" class="rollingDiceIcon"/></button>
            </div>

            <!-- img的guidance_scale -->
            <div class="input-group">
              <label for="guidance_scale" class="input-label">Guidance Scale:</label>
              <input
                type="number"
                v-model="guidance_scale"
                class="control-input"
                min="1"
                max="15"
                />
            </div>
            <input
              id="guidance_scale"
              type="range"
              v-model="guidance_scale"
              class="text-input"
              min="1"
              max="15"
            />
          </div>
          <div class="submit-button">
              <button @click="submitData" class="generate-button">
                {{ isGenerating ? 'Generating...' : 'Generate' }}
                <!-- 动态按钮 -->    
              </button>
            </div>
        </div>

        <div class="right">
          <div class="input-group">
              <label for="img-brightness" class="input-label">brightness:</label>
              <input
                type="number"
                v-model="brightness"
                min="-50"
                max="50"
                step="1"
                class="control-input"
                @input="editImage"
              />    
              </div>
            <input
              id="img-brightness"
              type="range"
              v-model="brightness"
              class="text-input no-progress"
              min="-50"
              max="50"
              step="1"
              @input="editImage"
            />
              
          <div class="input-group">
            <label for="img-contrast" class="input-label">contrast:</label>
            <input
              type="number"
              v-model="contrast"
              min="-50"
              max="50"
              step="1"
              class="control-input"
              @input="editImage"
            />    
          </div>
            <input
              id="img-contrast"
              type="range"
              v-model="contrast"
              class="text-input no-progress"
              min="-50"
              max="50"
              step="1"
              @input="editImage"
            />

          <div class="result-and-action">
            <div class="image-container">
                <img ref="image" :src="imageUrl" alt="生成的图像" />
            </div>
            <button @click="downloadImage">Download</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import rollingDiceIcon from '@/assets/rollingDiceIcon.svg'; // 使用 import 引入 SVG 文件
export default {
  mounted() {
    // 在 mounted 钩子中检查 $refs.image 是否已经渲染
    if (this.$refs.image) {
      console.log('Image has been rendered!');
    } else {
      console.log('Image is not rendered yet!');
    }
  },
  data() {
    return {
      prompt: '',
      negative_prompt: '',
      num_inference_steps: 25, // 默认值
      width: 512, // 默认值
      height: 512, // 默认值
      seed: null, // 默认值
      guidance_scale: 7.5, // 默认值
      rollingDiceIconPath: rollingDiceIcon,
      imageUrl: 'https://webcnstatic.yostar.net/ba_cn_web/prod/upload/wallpaper/dMIq1HzJ.jpeg', // 初始图片 URL
      isGenerating: false, // 用于控制按钮显示
      brightness: 0,//图片亮度
      contrast: 0,//图片对比度
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

    generateRandomSeed(){
      this.seed = Math.floor(Math.random()*999999) + 1;
    },

    downloadImage() {
      const targetUrl = this.imageUrl;
      fetch(targetUrl)
        .then((response) =>{
          if(!response.ok){
            throw new Error('图片下载失败');
          }
          return response.blob();
        })
        .then((blob) => {
          const url = URL.createObjectURL(blob); // 创建 Blob 对象 URL
          const link = document.createElement('a');
          link.href = url;
          link.download = 'downloaded-image.jpg'; // 下载文件的名称
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          URL.revokeObjectURL(url); // 释放 URL 对象
        })
        .catch(console.error);
    },

    editImage() {
      //在前端更改图像的亮度和对比度，但图片的实际数据并没有更改，这里我们仅在下载的时候才进行更改
      console.log("in")
      const imgElement = this.$refs.image;
      if(imgElement) {
        console.log(`brightness: ${this.brightness}`)
        console.log(`contrast: ${this.contrast}`)
        // imgElement.style.filter = `brightness(${100 + parseInt(this.brightness)}%)`;
        // imgElement.style.filter = `contrast(${100 + parseInt(this.contrast)}%)`;
        imgElement.style.filter = `brightness(${100 + parseInt(this.brightness)}%)
          contrast(${100 + parseInt(this.contrast)}%)`;
      }

    },
    
    async applyEditImage() {
       try {
        const response = await fetch("http://localhost:8080/api/edit/image", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            imagePath: this.imageUrl, // 服务器上的图片路径
            edits: {
              brightness: this.brightness,
              contrast: this.contrast,
            },
          }),
        });

        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json(); // 假设返回的是一个包含图片路径的 JSON
        this.imageUrl = `http://localhost:8080/${data.image_url}`;
      } catch (error) {
        console.error("编辑图片时出错:", error);
        alert("编辑图片失败，请检查后端服务是否正常。");
      }
    }


  },
};
</script>

<style scoped>
.body {
  padding: 20px;
}

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
  gap: 20px;
}

.left {
  display: flex;
  flex-direction: column;
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
  padding: 0; /* 给按钮两侧增加一些内边距 */
}

.generate-button {
  height: 80px; /* 增加按钮高度 */
  width: 100%; /* 按钮占满右边 */
  font-size: 24px; /* 增加字体大小 */
}

.right {
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

.image-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 800px; /* 固定框宽度 */
  height: 500px; /* 固定框高度 */
  overflow: visible; 
  border: 0px solid #ccc; /* 边框样式 */
}

.image-container img {
  max-width: 800px;
  max-height: 500px;;
  height: auto;
  width: auto;
  object-fit: contain; /* 图片自适应填充 */
  border-radius: 20px;
}

/*这个class确保属性输入框的label和control-input在同一行*/
.input-group {
  display: flex;
}

.control-input {
  text-align: center;
  margin-left: auto;
  width: 80px;
  height: 25px;
  border: 1px solid #ccc;
  border-radius: 5px;
  border-color: #ccc;
}

.input-button {
  background-color: #ccc;
  border: none;
  padding: 12px;
  cursor: pointer;
  border-radius: 5px; /* 圆角 */
  display: flex;
  align-items: center;
  justify-content: center;
}

.seed-group {
  display: flex;
  gap: 10px;
}

/* 图标样式 */
.rollingDiceIcon {
  width: 24px;  /* 根据需求调整图标大小 */
  height: 24px;
}

.input-button:hover {
  background-color: #f2f2f2;
}


/* 不管是否悬浮在control-input上都展示大小调节的按钮 */
/* WebKit 浏览器内核的调整按钮 */
input[type=number]::-webkit-inner-spin-button, 
input[type=number]::-webkit-outer-spin-button {
  opacity: 1; /* 设置不透明 */
  display: block; /* 确保按钮显示 */
}




</style>
