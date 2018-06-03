# Dramatic-tickets Architecture Document

## 架构问题及解决方案说明
__问题：支付成功检测与退款————服务器与用户端验证支付是否成功__

__解决方案__

在服务器端检测是否有资金入账，并为每一个订单独绑定一个支付数据结构，在收款成功、未收款、需要退款等情况下，均通过支付数据结构的数据来判断状态，客户端通过后端传来的数据进行处理得出是否成功的问题，在需要退款时，后端优先，前端必须通过互联网接收到后端的数据才能判定是否退款成功。

__问题：可靠性————从远程服务失败的恢复__

__解决方案__

使用ServicesFactory中的适配器来实现关于服务位置的防止变异，并在服务器端实现数据的长久化。在发生失败时，根据自己设定的返回报错来定位错误，并尝试重新进行远程服务，如果不能成功，在多次失败后根据定位维护远程服务器，从而减少了软件维护的成本。

## 逻辑视图
![物理视图](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/逻辑视图.png)
## 物理视图
![物理视图](https://raw.githubusercontent.com/dramaticTickets/dramatic-tickets/master/pictures/14_物理视图.png)
