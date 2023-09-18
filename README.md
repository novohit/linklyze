## Linklyze

## Link & Analyze

分布式短链生成

[原项目](https://github.com/novohit/linklyze) 已迁移至 [linklyze](https://github.com/novohit/linklyze)，旧仓库不在同步更新



## 架构图

![短链系统架构图.drawio](https://zwx-images-1305338888.cos.ap-guangzhou.myqcloud.com/typora/%E7%9F%AD%E9%93%BE%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84%E5%9B%BE.drawio.png)



## 功能

- [x] 账户服务
- [x] 短链服务
- [ ] 流量包服务

## 配置搭建
```
docker run -d \
-e NACOS_AUTH_ENABLE=true \
-e MODE=standalone \
-e JVM_XMS=128m \
-e JVM_XMX=128m \
-e JVM_XMN=128m \
-p 8848:8848 \
-p 9848:9848 \
-p 9849:9849 \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=xxx \
-e MYSQL_SERVICE_PORT=13307 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=root \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e MYSQL_SERVICE_DB_PARAM='characterEncoding=utf8&connectTimeout=10000&socketTimeout=30000&autoReconnect=true&useSSL=false' \
--restart=always \
--privileged=true \
-v /home/data/nacos/logs:/home/nacos/logs \
--name nacos_auth \
nacos/nacos-server:v2.0.4
```

```
docker run -d --hostname my-rabbit --name plato_rabbitmq -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3-management
```



- 不支持直接挂载文件，只能挂载文件夹
- 想要挂载文件，必须宿主机也要有对应的同名文件

```
sudo docker run --privileged --name nginx -d -p 8088:80 \
-v /data/nginx/html:/usr/share/nginx/html \
-v /data/nginx/conf/nginx.conf:/etc/nginx/nginx.conf \
-v /data/nginx/conf.d/default.conf:/etc/nginx/conf.d/default.conf \
-v /data/nginx/logs:/var/log/nginx nginx
```



```
docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper
```

```
docker run -d --name plato_kafka \
-p 9092:9092 \
--link zookeeper \
-e KAFKA_BROKER_ID=0 \
-e KAFKA_HEAP_OPTS=-Xmx256M \
-e KAFKA_HEAP_OPTS=-Xms128M \
-e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://公网ip:9092 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
wurstmeister/kafka:2.13-2.7.0
```



```
docker run -d --name kafka -p 9092:9092 --link zookeeper --env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 --env KAFKA_ADVERTISED_HOST_NAME=localhost --env KAFKA_ADVERTISED_PORT=9092 wurstmeister/kafka:2.13-2.7.0
```



```
docker run -d --name kafka-map -p 8049:8080 -e DEFAULT_USERNAME=admin -e DEFAULT_PASSWORD=admin  dushixiang/kafka-map:latest
```



## 账户模块

- 索引规范



### 短信验证码

短信验证码防刷：

- 前端防抖

- 添加图像验证码

- 基于滑动窗口算法对调用方法进行全局限制

- 在方法中进行两次Redis存储，一次存储验证码code并设置过期时间10min，一次存储额外的key并设置过期时间60s用于判断是否重复发送

  ```
  缺点：
  两次redis操作为非原子操作，存在不一致性
  增加的额外的key-value存储，浪费空间
  ```

- 将两次Redis存储压缩成一次

  ```
  code拼装时间戳存储
  key:phone/captchaId value:code_timestamp
  只需要将value中的timestamp相减即可知道两次调用时间间隔
  优点:
  满足了当前节点内的原子性，也满足业务需求
  ```




## 分库分表



分库分表后的查询问题

C端用户可根据短链码的库表位路由到对应的库表

B端用户如何查看自己创建的所有短链？

多维度查询解决方案：

- 额外表字段解析配置
- NOSQL冗余
- 冗余双写





## 流量包模块



## 短链模块




## 分布式锁





## 压测

简介：目前用的常用测试工具对比

- LoadRunner

    - 性能稳定，压测结果及细粒度大，可以自定义脚本进行压测，但是太过于重大，功能比较繁多

- Apache AB(单接口压测最方便)
    - 模拟多线程并发请求,ab命令对发出负载的计算机要求很低，既不会占用很多CPU，也不会占用太多的内存，但却会给目标服务器造成巨大的负载, 简单DDOS攻击等

- Webbench
    - webbench首先fork出多个子进程，每个子进程都循环做web访问测试。子进程把访问的结果通过pipe告诉父进程，父进程做最终的统计结果。
- Jmeter (GUI )
    - 开源免费，功能强大，在互联网公司普遍使用
    - 压测不同的协议和应用
        - Web - HTTP, HTTPS (Java, NodeJS, PHP, ASP.NET, ...)
        - SOAP / REST Webservices
        - FTP
        - Database via JDBC
        - LDAP 轻量目录访问协议
        - Message-oriented middleware (MOM) via JMS
        - Mail - SMTP(S), POP3(S) and IMAP(S)
        - TCP等等
    - 使用场景及优点
        - 功能测试
        - 压力测试
        - 分布式压力测试
        - 纯java开发
        - 上手容易，高性能
        - 提供测试数据分析
        - 各种报表数据图形展示



压测工具本地快速安装Jmeter5.x





1. 图像数据的网络爬取

本实验选择了一个公开的动物分类数据集作为实验样本，数据集包含了10种不同类别的动物图像。在进行数据爬取时，我们采用了Python中的requests和beautifulsoup4库来实现对图片URL的抓取，并将其保存到本地文件夹中。由于网络爬取的过程中可能会遇到访问限制、网络波动等问题，因此在实际操作中需要具备一定的技巧和经验。

1. 图像数据的整理与批量标注

在数据爬取完成后，我们需要对其进行整理和批量标注。具体来说，我们首先需要将图片按照类别分别存储到不同的文件夹中。然后，我们需要手动为每一张图片打上标签，标签可以采用数字、英文或中文等形式，具体取决于实验需要和个人喜好。



## 开发遇到的问题

### Gson.toJson()导致OOM

原因：序列化的对象ResponseFacade/ResquestFacade中出现了循环引用





Q:在Java中为什么ResponseFacade类中有Response属性，而Response类中又还有ResponseFacade属性

A:
在Java Servlet API中，ResponseFacade是一个包装器类，用于封装Servlet响应(Response)对象，以便于在Servlet容器内部使用。它实现了ServletResponse接口，并持有一个ServletResponse类型的成员变量response。

而在ServletResponse接口的实现类Response中，也有一个ResponseFacade类型的成员变量facade，它用于存储ResponseFacade对象。这是因为在Servlet容器内部，实际处理请求和响应的是Servlet容器，而不是Servlet本身。当Servlet调用ServletResponse的方法时，容器会将这些方法调用传递给ResponseFacade对象，然后再由ResponseFacade对象转发给真正的ServletResponse对象。

这种设计模式称为外观模式(Facade Pattern)，它提供了一种简单的方式来访问复杂子系统的一部分，而无需了解其内部实现。在Servlet API中，ResponseFacade对象充当了Servlet容器与Servlet响应之间的中间层，封装了Servlet响应对象的实现细节，使得Servlet可以更方便地进行响应处理。

Q:这不是循环引用了吗

A:
确实，从代码结构上看，ResponseFacade类中持有了Response对象，而Response类中又持有了ResponseFacade对象，因此存在循环引用的情况。

但是，这种循环引用并不会导致程序出错或内存泄漏，因为它们所持有的对象并不是相互依赖的。在Servlet容器内部，实际处理请求和响应的是Servlet容器，而不是Servlet本身。当Servlet调用ServletResponse的方法时，容器会将这些方法调用传递给ResponseFacade对象，然后再由ResponseFacade对象转发给真正的ServletResponse对象。因此，ResponseFacade对象只是作为一个中间层进行了一些简单的封装，而不会对Response对象产生任何影响。

在Java语言中，循环引用不一定会导致内存泄漏，因为Java的垃圾回收器会自动识别和处理这种情况。当对象之间存在循环引用时，只要这些对象不再被程序所引用，它们就会被垃圾回收器识别并回收。因此，从内存管理的角度来看，循环引用并不是一个严重的问题。



## 面试问题

### 短链码的生成方案

考虑：

生成性能

避免冲突

避免猜测业务量

避免爬取



方式一：

直接通过自增ID转为62进制

缺点：生成的短链码长度不一致，业务增长量容易被猜测，解决：自增ID可以从指定数值开始增长，但是仍然是有序递增的，存在业务数据安全问题。



方式二：

长链接+时间戳 md5加密，取最后几位作为短链码

```
27A4239B76A5589CDC825EC40EDED72E
```

缺点：冲突概率高，需要解决冲突，加密hash性能不高



方式三：

非加密hash：murmurhash，crc32

murmurhash得到10进制的值，再转为62进制

长度为6位的短链码，最多可以记录62^6，568亿种可能

但是 MurmurHash2（产⽣32位或64位值）,MurmurHash3（产⽣32位或128位值),MurmurHash的 32 bit 能表示的最⼤值近 43 亿的10进制



### 短链码的分库分表策略

方式一：

16个库，每个库64个表，总共1024个表

直接对短链码Code进行hash取模

库ID：Code的hash值 % 库数量

表ID：Code的hash值 / 库数量 % 表数量



优点：

简单

保证数据较均匀的分布在不同的库表中，可以有效的避免热点数据集中的问题。

缺点：

扩容不方便，需要数据迁移

需要一次性建立16个库 64个表，初期浪费资源



方式二：

短链码中设置库表位，比如首尾位为库表位，通过库表位路由到对应的库表

1位库位，1位表位，那么最大可以有64*64个表

因为短链码中的库表位是固定的，所以扩容时的旧数据不需要进行数据迁移



### 分库分表多维度查询

有partition key：

普通访客可以通过短链码的库表规则直接路由到对应的库表中进行数据访问

无partition key：

那么创建短链的用户如何查看自己创建的所有短链？

因为此时我们只能拿到用户的id，但是没有短链码



类似的像电商中的业务

订单分库分表策略：根据用户id取模

用户可以根据自己的id查看自己的所有订单

但是商家没有用户id，如何查询出自己的所有的订单



招聘网站业务

应聘者查看自己的全部面试记录

企业查看自己某个岗位的面试记录



解决方案：

字段解析配置

NoSQL冗余

双写方案
