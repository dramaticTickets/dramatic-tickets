# 软件需求规格说明书

---


**团队名称：** Drama Team

**团队成员：**

|    学号    |  姓名  |     分工      |
| :------: | :--: | :---------: |
| 15331034 |      | UI设计、项目网站维护 |
| 15331048 |      |    开发工程师    |
| 15331009 |      |    产品经理     |
| 15331010 |      |    UI设计     |
| 15331026 |      | 项目经理、开发工程师  |
| 15331027 |      |    开发工程师    |
| 15331296 |      |    开发工程师    |

---

## 引言

### 目的
本文档旨在介绍 DramaticTickets 项目的产品需求与功能结构，阐明该产品的实用背景及范围，让本产品的用户与开发维护人员更加了解本产品，为使用与开发提供便利。

### 项目背景
随着计算机技术、硬件和软件技术、宽带技术的快速发展，智能手机平台上的移动应用开发以及成为目前的热点，具有强劲的发展势头和市场潜力。 Android 系统凭借功能丰富、更适用于触摸屏设备，占据了全球智能手机操作系统的市场份额的近 70%。

我国经济的快速发展促进了娱乐服务行业的快速发展，人们在闲暇之余的娱乐活动也越来越丰富而看电影已经成为人们闲时重要的娱乐方式。

在国内外基于 Android 智能手机平台的开发在各个领域受到了广泛欢迎，基于Android平台的订票软件开发已经成为较为成熟的技术，具有技术上的可行性。

电影票订票系统具有非常重大的实践意义和经济效益，并且具有很好的商业投资价值。一方面，电影票订票系统能够极大的方便顾客购票，用户足不出户便可方便的进行购票，不仅能够减少顾客的购票时间，避免了排队等候的尴尬，而且能够提高影院的出票效率，减小人为错误。另一方面，电影票订票系统能够在一定程度代替人工售票，从而减少电影院的人力及物力成本投入。 


### 参考资料
[1] 潘茂林 《分布式计算课程内容》《系统设计分析与设计课程内容》
[2] GB-T8567-2006，《计算机软件文档编制规范》[S]

---

## 项目概述

### 产品描述
DramaticTickets 是一个 Android 平台下的一个手机电影购票应用程序，用户可以在此应用程序上获取附近的影院，并获取影院里正在上映或即将上映的影片简介主演等信息。

不同的影院有对各电影不同的场次安排，用户选择影院的影片场次后可以购买自己想要座位的电影票，支付后获得取票序列号及验证码，凭号码可在影院取票处取得所购买电影票。

用户在登录注册后可以查看自己看过的和收藏的电影，并查看账号订单信息等。且提供可更换账号头像，设置账号昵称等服务。

### 产品功能
![产品用例图](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/7_use_case_1.png)

由用例图可以看出，DramaticTickets 具有以下功能：

1. 注册及登录功能
   用户需要通过设定昵称，头像，账号及密码，并提供个人手机号完成注册，在注册并登录后可以查看账号所收藏的电影及购买的电影票等信息。
   
2. 搜索电影功能
   用户可以通过电影名称搜索的方式搜索影片，并获得电影评分和简介等信息。
   
3. 搜索影院功能
   用户可以通过影院名称搜索的方式搜索影片，并获得影院地址及上映影片等信息。
   
4. 影片推荐功能
   DramaticTickets 会在首页为用户推荐正在上映或即将上映的影片，用户可以更方便快捷的了解感兴趣的电影。

5. 影院推荐功能
   DramaticTickets 会在为用户推荐附近影院，用户可以更方便快捷的选择影院。

6. 购票功能
   DramaticTickets 为用户提供在线选座购票服务，在购票后用户通过序列号和验证码到影院取票。

### 产品模型设计
 - 产品领域模型
![domain model](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/model.png)

 - 产品E-R模型
![ER图](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/修改ER图.png)

 - 产品状态模型
    - 订单状态模型
     ![订单状态模型](https://raw.githubusercontent.com/zichang06/markdownPics/master/lesson8_team1.png)
    - 座位订单模型
      ![座位订单模型](https://raw.githubusercontent.com/zichang06/markdownPics/master/lesson8_team2.png)

 - 功能模型
   根据用例设计，可以确定以下几个ECB类：
    - Boundary/UI 类：
       - SearchMoviePage: 搜索正在上映的电影界面
       - ShowMoviesPage: 显示正在上映的电影的界面
       - ChooseCinemaPage: 选择影院界面
       - ChooseSeatPage: 选择电影的场次和座位
       - ReservationInfoPage: 支付界面
    - Controller 类：
       - SearchCtrl: 查询系统
       - ReservationCtrl: 订单系统
    - Entity 类：
       - Movie: 正在上映的影片
       - Cinema: 上映电影的影院
       - Seat: 该影院上映电影的场次、座位。
   ![ECB类图](https://raw.githubusercontent.com/zichang06/markdownPics/master/project_类图.png)

    - ECB顺序图
      ![ECB顺序图](https://raw.githubusercontent.com/zichang06/markdownPics/master/project_顺序图.png)
    - 用户注册
      ![用户注册](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/SystemSequenceDiagram1.png)
    - 选择影片购票
      ![选择影片购票](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/System%20Sequence%20Diagrams%202.png)
    - 选择影院购票
      ![选择影院购票](https://raw.githubusercontent.com/Cicicigar/markdown_pics/master/顺序图3.PNG)
    - 同场次座位更换
      ![同场次座位更换](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/system_sequence_diagram_更换座位.png)
    - 历史购票记录
      ![历史购票记录](https://raw.githubusercontent.com/zichang06/markdownPics/master/week10.png)
    - 即将开场影票查询
      ![即将开场影票查询](https://raw.githubusercontent.com/czjcssy/PictureStore/master/pic/取票顺序图.png)
    - 用户资料更改
      ![用户资料更改](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/modifyPersonalInformation_ssd.png)

### 产品视图设计
 - 逻辑视图
   ![逻辑视图](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/逻辑视图.png)
 - 物理视图
   ![物理视图](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/14_物理视图.png)

---

## 具体要求

### 功能需求
|Belong| Name | Imp | Por | Est (week) | How to preview | Note |
|--|------|:---:|:---:|:----------:|----------------|:------:|
|用户|查询电影信息|10|10|4|我能从app中知道正在上映、即将上映的电影信息。|sprint 1|
|用户|影院与选座|9|9|2|我能从app中知道不同影院、不同影片的座位信息。|sprint 2|
|用户|购票功能|8|8|2|我能从app中购票、查票和取票（二维码等）。|sprint 2|
|用户|用户查询系统|4|7|2|我能在用户客户端中注册与登录，并且查询我的购票历史。|sprint 3|
|商家|影院入驻|7|6|3|我能在商家app中申请账号并入驻我的电影院。|sprint 3|
|商家|上下架电影与电影排期|6|5|2|我能在商家app中更新的我影讯。|sprint 3|

### 外部接口需求
 - 重要硬件和接口 
    - 手机
    - 平板电脑
    - 电影票取票机
 - 软件接口 
    - 登陆接口（微信、QQ等）
    - 支付接口
    - 数据库接口

### 性能需求
 - DramaticTickets的主要研发人员坚持采用Java技术的解决方案，因为Java技术除了易于开发外，还能够提高远期的移植和可支持性能力。

 - 支付系统：我们调用现有的主要支付平台API

 - 平台：安卓系统和ios系统，至少需要50MB的硬盘空间和32MB 的RAM。

### 属性
 - 功能性
    - 日志和错误处理，在持久性存储中记录所有错误。
    - 为用户提供帮助手册和功能提示功能。
   
 - 安全性
    - 任何使用都需要经过用户认证。
   
 - 可用性
    - 适用于绝大多数的安卓系统和ios系统。
    - 用户可以向开发团队反应遇到的问题。
   
 - 可靠性
    - 软件被切换到后台运行，暂时保持不退出，并保持用户信息。
    - 90%的情况下，数据库响应等业务操作能够在1分钟内完成。
    - 可恢复性。如果在使用外部服务（如支付授权等）出现错误，可以回滚和正确修复。

 - 可支持性
    - 可配置性：根据不同的手机系统性能，用户可以选择安装不同版本的软件，以更好地兼容。
    - 可适应性：不同用户可能会选择不同的支付方式，DramaticTickets调用了绝大多数主流的支付方式，以适应用户的需求。

### 故障处理
 - 正常使用时不应出错，对于用户的输入错误给出适当的改正提示。
 - 若运行时遇到不可恢复的系统错误，也必须保证数据库完好无损。

