# 16
## 一、用例设计
![](https://github.com/dramaticTickets/dramatic-tickets/raw/master/pictures/7_use_case_1.png?raw=true)
根据用例设计，可以确定以下几个ECB类：

Boundary/UI 类：
SearchMoviePage:  搜索正在上映的电影界面
ShowMoviesPage:  显示正在上映的电影的界面
ChooseCinemaPage: 选择影院界面
ChooseSeatPage: 选择电影的场次和座位
ReservationInfoPage: 支付界面

Controller 类：
SearchCtrl: 查询系统
ReservationCtrl: 订单系统

Entity 类：
Movie: 正在上映的影片
Cinema: 上映电影的影院
Seat: 该影院上映电影的场次、座位。

## 二、ECB
- 顺序图
![](https://github.com/zichang06/markdownPics/blob/master/project_%E9%A1%BA%E5%BA%8F%E5%9B%BE.png?raw=true)

- 类图
![](https://github.com/zichang06/markdownPics/blob/master/project_%E7%B1%BB%E5%9B%BE.png?raw=true)