# / 数据库的使用：<br>

> *Contributed By **Yilin Xu***<br>

5个表（user，images，history，colors，admins）<br>
主要关系：<br>
user通过user_id连接images实现一对多<br>
user通过user_id连接history再通过history的image_id连接图像<br>
images和colors通过image_id连接,colors可能用得上，是保存image色系的表。<br>
使用方法：拉取下来文件后放在英文无空格的目录下，在你的数据库：mysql -u username -p dbname < path/to/your/file.sql
