## Introduction

**Linklyze is mean Link & Analyze**

产品原型参考 https://xiaomark.com/

分布式短链生成

[原后端仓库](https://github.com/novohit/plato) 已迁移至 [linklyze](https://github.com/novohit/linklyze)，旧仓库同步更新

前端仓库 https://github.com/Su-u-un/plato-admin



## Architecture

![短链系统架构图.drawio](https://zwx-images-1305338888.cos.ap-guangzhou.myqcloud.com/typora/%E7%9F%AD%E9%93%BE%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84%E5%9B%BE.drawio.png)



## Features



- [x] 账号系统
- [x] 短链分组
- [x] 生成短链
- [x] 流量包系统



## Quick Start



## 账户模块




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
