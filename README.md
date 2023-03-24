## 短链系统

## 功能

- [x] 账号系统
- [x] 短链分组
- [x] 生成短链
- [ ] 流量包系统

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
docker run -d --hostname my-rabbit --name plato_rabbitmq -p 15672:1-e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3-management
```



## 账户模块

- 索引规范



```
CREATE TABLE `account` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`account_no` BIGINT DEFAULT NULL,
	`avatar` VARCHAR ( 255 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
	`phone` VARCHAR ( 128 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
	`password` VARCHAR ( 128 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
	`secret` VARCHAR ( 64 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '盐，用于个人敏感信息处理',
	`mail` VARCHAR ( 128 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
	`username` VARCHAR ( 255 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
	`auth` VARCHAR ( 32 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '认证级别，DEFAULT，REALNAME，ENTERPRISE，访问次数不一样',
	`create_time` datetime DEFAULT CURRENT_TIMESTAMP,
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`delete_time` datetime DEFAULT NULL,
	PRIMARY KEY ( `id` ),
	UNIQUE KEY `uk_phone` ( `phone` ) USING BTREE,
UNIQUE KEY `uk_account` ( `account_no` ) USING BTREE 
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;
```



```
CREATE TABLE `traffic` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	`day_limit` INT DEFAULT NULL COMMENT '每天限制多少条短链',
	`day_used` INT DEFAULT NULL COMMENT '当天用了多少条短链',
	`total_limit` INT DEFAULT NULL COMMENT '总次数，活码才用',
	`account_no` BIGINT DEFAULT NULL COMMENT '账户',
	`out_trade_no` VARCHAR ( 64 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单号',
	`level` VARCHAR ( 64 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '产品层级：FIRST青铜 SECOND黄金THIRD砖石',
	`expired_date` date DEFAULT NULL COMMENT '过期时间',
	`plugin_type` VARCHAR ( 64 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '插件类型',
	`product_id` BIGINT DEFAULT NULL COMMENT '商品主键',
	`create_time` datetime DEFAULT CURRENT_TIMESTAMP,
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`delete_time` datetime DEFAULT NULL,
	PRIMARY KEY ( `id` ),
	UNIQUE KEY `uk_trade_no` ( `out_trade_no`, `account_no` ) USING BTREE,
KEY `idx_account_no` ( `account_no` ) USING BTREE 
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;
```



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

```
CREATE TABLE `link_group` (
	`id` BIGINT UNSIGNED NOT NULL,
	`title` VARCHAR ( 255 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链分组名',
	`account_no` BIGINT DEFAULT NULL COMMENT '账户唯一标识',
	`create_time` datetime DEFAULT CURRENT_TIMESTAMP,
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`delete_time` datetime DEFAULT NULL,
PRIMARY KEY ( `id` ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;
```



```
CREATE TABLE `short_link` (
	`id` BIGINT UNSIGNED NOT NULL,
	`group_id` BIGINT DEFAULT NULL COMMENT '分组id',
	`title` VARCHAR ( 128 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
	`original_url` VARCHAR ( 1024 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原url地址',
	`domain` VARCHAR ( 128 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
	`code` VARCHAR ( 16 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '短链码',
	`long_hash` VARCHAR ( 64 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '长链的hash码 方便查找',
	`expired` datetime DEFAULT NULL COMMENT '过期时间 永久为-1',
	`account_no` BIGINT DEFAULT NULL COMMENT '账户唯一标识',
	`state` VARCHAR ( 16 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链状态 lock：锁定 active：可用',
	`link_level` VARCHAR ( 16 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '产品level FIRST青铜SECOND黄金THIRD钻石',
	`create_time` datetime DEFAULT CURRENT_TIMESTAMP,
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`delete_time` datetime DEFAULT NULL,
	PRIMARY KEY ( `id` ),
UNIQUE KEY `uk_code` ( `code` ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;
```



```
CREATE TABLE `domain` (
	`id` BIGINT UNSIGNED NOT NULL,
	`account_no` BIGINT DEFAULT NULL COMMENT '用户自己绑定的域名',
	`domain_type` VARCHAR ( 11 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '域名类型，自建custom, 官方offical',
	`value` VARCHAR ( 255 ) CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
	`create_time` datetime DEFAULT CURRENT_TIMESTAMP,
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`delete_time` datetime DEFAULT NULL,
PRIMARY KEY ( `id` ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;
```



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


