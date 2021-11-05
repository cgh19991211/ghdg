# 主题
> 一个专门为开发者服务的社区，以“思否”的文章专栏、问答专栏、用户主页等为前端页面原型。
社区的发表文章宗旨应当是只针对前沿的新知识、新理论，让开发者们想要学习新知识的时候就会想到来这个社区上面寻求指导或者教程。所以专栏推荐的文章不完全根据热度来进行展示，主要看技术的新颖程度。

# 项目架构
- 前端使用MVVM架构 
- 后端使用分模块开发、分层架构(dao,dto,controller,service)
  - 基础服务模块(common)
  -	权限管理模块(admin)
  -	UI模块(前端页面)
  -	请求接口服务模块(restful)：采用前后端分离的机制实现；前台展示模块通过http协议访问RESTFulAPI请求数据
  -	聊天服务模块
  -	搜索模块（搜索文章、用户、问答。。。）

# 技术选型
前端：js框架采用Vue，UI框架采用bootstrap。
后端：使用SpringBoot框架进行开发，ORM采用SpringDataJpa，权限认证使用Shiro+Jwt；使用Tomcat部署服务。
搜索引擎：elastic search
数据库：mysql，mongodb，服务器缓存使用redis；

系统后台管理一律采用mysql：管理员用户、权限、角色、菜单等
前台使用一律MongoDB：用户、文章、评论、标签等

# 模块
- Ghdh-manage：后台管理系统站点
    - 业务管理
    - 专栏管理
    - 文章管理
    - 聊天管理
  - 系统管理
    - 用户管理
    - 角色管理
    - 菜单管理
  - 运维管理
    - 监控管理
    - 接口文档
    - 登陆日志
    - 业务日志
  - 消息管理
    - 消息模板
    - 历史消息
    - 消息发送器
- Ghdg-UI：网页端站点
  - 首页
  - 个人中心
  - 专栏
- Ghdg-api：接口
  - Controller
  - Interceptor
- Ghdg-core：业务核心代码
  - bussinessMgr：客户服务
  - cmsMgr：内容信息管理业务
  - common：公共业务
  - oprMgr：运维业务
  - sysMgr：系统管理业务

