import tkinter as tk
from tkinter import messagebox
from PIL import Image, ImageTk

class ImageColorPicker(tk.Tk):
    def __init__(self, image_path):
        super().__init__()
        
        # 加载图片
        self.image = Image.open(image_path)
        self.photo = ImageTk.PhotoImage(self.image)

        # 创建画布来显示图片
        self.canvas = tk.Canvas(self, width=self.photo.width(), height=self.photo.height())
        self.canvas.pack()

        # 在画布上绘制图片
        self.canvas.create_image(0, 0, anchor=tk.NW, image=self.photo)

        # 绑定鼠标点击事件
        self.canvas.bind("<Button-1>", self.on_click)

        # 设置窗口属性
        self.title("Image Color Picker")
        self.geometry(f"{self.photo.width()}x{self.photo.height()}")

        # 创建用于显示颜色的框
        self.color_frame = tk.Frame(self, width=100, height=100)
        self.color_frame.pack(side=tk.BOTTOM, pady=10)  # 放置在窗口底部

    def on_click(self, event):
        x, y = event.x, event.y
        
        # 确保点击位置在图片范围内
        if 0 <= x < self.image.width and 0 <= y < self.image.height:
            # 获取 RGB 值, 对于 RGBA图像则会返回 4个整数元组
            rgb = self.image.getpixel((x, y))
            print(rgb)
            color = f"rgb: {rgb}"
            self.show_color_dialog(rgb, color)

    def show_color_dialog(self, rgb, color):
        # # 创建一个提示框
        # color_dialog = tk.Toplevel(self)
        # color_dialog.title(f"Color at ({rgb[0]}, {rgb[1]},{rgb[2]})")
        # color_dialog.geometry("200x100")

        # # 设置背景颜色
        # color_dialog.configure(bg=f"#{rgb[0]:02x}{rgb[1]:02x}{rgb[2]:02x}")

        # # 显示 RGB 值
        # label = tk.Label(color_dialog, text=color, bg=color_dialog['bg'], fg='white', font=('Arial', 12))
        # label.pack(pady=20)

        # # 关闭按钮
        # button = tk.Button(color_dialog, text="Close", command=color_dialog.destroy)
        # button.pack()
        def show_color_dialog(self, rgb, color):
        # 更新颜色框的背景色
            hex_color = f"#{rgb[0]:02x}{rgb[1]:02x}{rgb[2]:02x}"
            self.color_frame.configure(bg=hex_color)

            # 显示 RGB 值的消息框
            messagebox.showinfo("Color Info", color)

if __name__ == "__main__":
    # 替换为你的图片路径
    image_path = "Myproject\\imgs\\test.png"
    app = ImageColorPicker(image_path)
    app.mainloop()
