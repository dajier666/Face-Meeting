会议管理系统项目介绍
一、项目概述
本会议管理系统是一个基于 Spring Boot 构建的 Web 应用程序，提供了管理员和普通用户两种角色的功能。管理员可以进行用户管理和会议管理，普通用户可以报名会议、管理自己创建的会议以及加入会议。系统还支持人脸签到功能，采用 MyBatis 进行数据库操作，使用 Druid 作为数据库连接池，并集成了 Swagger 进行 API 文档管理，人脸模块可以选择自己实现或者调用成熟的外部api，本项目给出的调用百度人脸，比原来自实现的人脸识别效果更佳。
二、技术栈
后端
Spring Boot 3.4.3：快速搭建项目框架，简化开发流程。
MyBatis 3.0.4：持久层框架，方便进行数据库操作。
Druid 1.2.18：高性能数据库连接池，提高数据库访问效率。
MySQL：关系型数据库，存储系统数据。
Spring Data JPA 3.2.2：简化数据库操作，提供更便捷的 CRUD 功能。
JJWT 0.11.5：用于生成和验证 JSON Web Token，实现用户认证和授权。
OkHttp 4.11.0：用于发送 HTTP 请求。
Swagger：API 文档管理工具，方便开发和测试。
前端
HTML/CSS：构建页面结构和样式。
Tailwind CSS：实用类优先的 CSS 框架，快速实现页面布局和样式设计。
jQuery 2.1.0：简化 DOM 操作和事件处理。
Axios 0.18.0：基于 Promise 的 HTTP 客户端，用于发送异步请求。
三、项目结构
plaintext
Face-Meeting/
├── pom.xml                      # Maven项目配置文件
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── ourwork/
│   │   │           └── meetingsystem/
│   │   │               ├── Controller/  # 控制器层，处理HTTP请求
│   │   │               ├── Config/      # 配置类，如Swagger配置
│   │   │               ├── Utils/       # 工具类，如文件读取工具
│   │   │               └── MeetingSystemApplication.java # 项目启动类
│   │   ├── resources/
│   │   │   ├── application.properties   # 项目配置文件
│   │   │   └── static/
│   │   │       ├── html/    # 前端页面文件
│   │   │       ├── js/      # 前端JavaScript文件
│   │   │       └── baiduUtils/ # 文件读取工具类
│   └── test/
│       └── java/             # 测试代码
└── target/                   # 编译后的文件输出目录
四、环境搭建
1. 安装 JDK 17
确保你的系统已经安装了 JDK 17，并配置好环境变量。
2. 安装 MySQL
安装 MySQL 数据库，并创建名为myAIsystem的数据库。修改src/main/resources/application.properties文件中的数据库连接信息：
properties
spring.datasource.url=jdbc:mysql://localhost:3306/myAIsystem
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
3. 导入项目
使用 IntelliJ IDEA 或 Eclipse 等 IDE 导入项目，等待 Maven 自动下载依赖。
4. 启动项目
运行com.ourwork.meetingsystem.MeetingSystemApplication类的main方法，启动 Spring Boot 应用程序。
五、功能介绍
1. 登录和注册
用户可以通过登录页面进行登录和注册操作，登录成功后会生成 JWT 令牌并保存到 Cookie 中。
2. 管理员界面
用户管理：管理员可以查看用户列表，支持按用户名和 ID 搜索用户。
会议管理：管理员可以查看会议列表，支持按会议 ID 搜索会议。
3. 用户界面
报名会议：用户可以查看可报名的会议列表，支持搜索会议并进行报名操作。
管理会议：用户可以查看自己创建的会议列表，支持搜索会议和创建新会议。
加入会议：用户可以加入指定的会议。
4. 人脸签到
用户可以通过上传照片进行人脸签到。
六、API 文档
项目集成了 Swagger，启动项目后，访问http://localhost:8080/swagger-ui.html可以查看和测试 API 文档。
七、注意事项
确保数据库服务正常运行，并且数据库连接信息正确。
项目中使用的 JWT 令牌需要妥善保管，避免泄露。
人脸签到功能需要进一步完善，确保照片的准确性和安全性。
八、贡献
如果你对本项目有任何建议或改进意见，欢迎提交 Pull Request 或 Issue。
