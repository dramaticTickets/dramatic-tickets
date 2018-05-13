---
title: SpringMVC学习报告
date: 2018-04-14 12:34:17
tags: [homework, System's Analysis and Design,dramatic tickets]
---
### SpringMVC 学习报告

1. Spring: DI / AOP
   Spring对应的关键词是DI（依赖注入）与AOP（面向切面编程），可以认为是一个以DI和AOP为核心Java Web一站式的集成（粘合）框架。
   其中DI也称为IoC（控制发转）用于管理Java对象之间的依赖关系，AOP用于解耦业务代码和公共服务代码。DI和AOP能够让代码更加简单，具有良好的松耦合特性和可测试性，极大地简化开发。理解这两者是使用Spring框架的基础。
   Spring框架对Java企业应用开发中的各类通用问题都进行了良好的抽象，因此也能够把应用各个层次所涉及的特定的功能和开发框架。Spring是一个极其优秀的一站式的Full-Stack集成框架，因此基于Spring核心，对Java应用开发中的各类通用问题几乎都提供了针对性的开发框架，比如你耳熟能详的Spring MVC，Spring Data。
   
2. Struct: MVC
   Struct 是一个Java Web MVC开发框架。
   Model，View和Controller的分开，是一种典型的关注点分离的思想，不仅使得代码复用性和组织性更好，使得Web应用的配置性和灵活性更好。
   - 模型 Model：封装与业务逻辑相关的数据和数据处理方法。
   - 视图 View：数据的逻辑层展现。
   - 控制器 Controller：响应请求，协调 Model 与 View。

3. Hibernate：ORM
   ORM是Object Relation Mapping的缩写，顾名思义，即对象关系映射。
   ORM是一种以面向对象的方式来进行数据库操作的技术。Web开发中常用的语言，都会有对应的ORM框架。而Hibernate就是Java开发中一种常用ORM框架，另一个现在流行的ORM框架是Mybatis。
   简单地理解，通过Java进行数据库访问的正常流程可以分为以下几步：
   1. 准备好SQL语句
   2. 调用JDBC的API传入SQL语句，设置参数
   3. 解析JDBC返回的结果
   
   这个过程实际上非常麻烦，比如：
   - 在Java代码中拼接SQL非常麻烦，而且易于出错
   - JDBC的代码调用有很多重复性的代码
   - 从JDBC返回的结果转换成领域模型的Java对象很繁琐
  
   而使用ORM框架，则可以让我们用面向对象的方式来操作数据库，比如通过一个简单的函数调用就完成上面整个流程，直接返回映射为Java对象的结果。这个流程中很大一部分工作其实可以交给ORM自动化地帮我们执行。

经典架构图如下：
![经典架构图](https://pic3.zhimg.com/80/v2-4536e692de5413bf41a4a5c030f393bb_hd.jpg)
SpringMVC 架构图如下：
![经典架构图](https://pic4.zhimg.com/80/v2-13c8c23b5e6953563f49f876e3bb1cc9_hd.jpg)
Spring MVC提供了一个DispacherServlet（可以Spring MVC是以Servlet技术为基础的），这个Servlet把Web应用中Servlet中经常要实现的功能封装起来并提供一层公共抽象，想象对应于一个Web请求后端要做的事情。
有了Spring MVC，你只需写简单的POJO代码，就能完成这些繁琐的功能。
POJO是Plain Old Java Object的缩写，是软件开发大师Martin Fowler提出的一个概念，指的是一个普通Java类。也就说，你随便编写一个Java类，就可以称之为POJO。
而ORM的作用是提供与数据库操作的一层中间抽象，这样Model的代码自然会更加简单。
Spring MVC基本可以帮你屏蔽Servlet的API，ORM则可以帮你屏蔽JDBC的API了，也就是说你在更高的抽象层次上写程序了，更高的抽象层次一般意味着以更符合我们思维的方式来思考，自然效率更高。这事实上是软件技术发展的一个重要驱动力之一。你想想，从汇编语言发展出高级语言，从文件系统发展出数据库，其实本质规律是类似的。 
Java类之间存在大量复杂的依赖关系，而Spring的核心功能依赖注入，正是用于管理Java对象之间的依赖关系，所以第一部分我们说Spring是一个一站式的粘合框架。

