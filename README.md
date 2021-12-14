# 主题
> 一个博客、交流社区，以“思否”、“掘金”等网页为前端页面原型。

# 项目架构
- 前端使用MVVM架构 
- 后端使用分模块开发、分层架构(dao,dto,controller,service)
  - 基础服务模块(common)
  -	权限管理模块(admin)
  -	UI模块(前端页面)
  -	请求接口服务模块(restful)：采用前后端分离的机制实现；前台展示模块通过http协议访问RESTFulAPI请求数据
  -	聊天服务模块
  -	搜索模块（搜索文章、用户、标签、专栏）

# 技术选型
前端：js框架采用Vue，UI框架采用buefy、element ui。
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

