# plato-visual

exported at 2023-04-10 00:35:46

## 访问统计接口

访问统计接口


---
### 分页查询实时访问记录

> BASIC

**Path:** /visual/v1/page

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | string | 短链码 |
| page | integer | 页码 |
| size | integer | 条数 |

**Request Demo:**

```json
{
  "code": "",
  "page": 0,
  "size": 0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| &ensp;&ensp;&#124;─page | integer | 页码 |
| &ensp;&ensp;&#124;─size | integer | 条数 |
| &ensp;&ensp;&#124;─total | integer | 总条数 |
| &ensp;&ensp;&#124;─totalPage | integer | 总页数 |
| &ensp;&ensp;&#124;─items | array | 数据 |
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─id | string |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─code | string | 业务ID |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─referer | string |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ip | string |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─country | string | 地理位置=================================== |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─province | string |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─city | string |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─isp | string |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─deviceType | string | 设备类型 Computer/Mobile |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─os | string | 操作系统 WINDOWS/Android/IOS |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─browserType | string | 浏览器类型 Chrome |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─deviceManufacturer | string | 设备厂商 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─start | integer |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─end | integer |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─uv | integer |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─pv | integer |  |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": {
    "page": 0,
    "size": 0,
    "total": 0,
    "totalPage": 0,
    "items": [
      {
        "id": "",
        "code": "",
        "referer": "",
        "ip": "",
        "country": "",
        "province": "",
        "city": "",
        "isp": "",
        "deviceType": "",
        "os": "",
        "browserType": "",
        "deviceManufacturer": "",
        "start": 0,
        "end": 0,
        "uv": 0,
        "pv": 0
      }
    ]
  },
  "msg": ""
}
```




---
### 区域PV、UV统计

> BASIC

**Path:** /visual/v1/region

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | string | 短链码 |
| start | string | 开始区间(毫秒级) |
| end | string | 结束区间(毫秒级) |
| n | integer | 非必传 默认值5 referer接口传 TopN |

**Request Demo:**

```json
{
  "code": "",
  "start": "",
  "end": "",
  "n": 0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | array | 数据 |
| &ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─country | string | 国家 |
| &ensp;&ensp;&ensp;&ensp;&#124;─province | string | 省份 |
| &ensp;&ensp;&ensp;&ensp;&#124;─city | string | 城市 |
| &ensp;&ensp;&ensp;&ensp;&#124;─pv | integer | 浏览量 |
| &ensp;&ensp;&ensp;&ensp;&#124;─uv | integer | 访客数 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": [
    {
      "country": "",
      "province": "",
      "city": "",
      "pv": 0,
      "uv": 0
    }
  ],
  "msg": ""
}
```




---
### Device/OS/Browser类型 PV、UV统计

> BASIC

**Path:** /visual/v1/type

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | string | 短链码 |
| start | string | 开始区间(毫秒级) |
| end | string | 结束区间(毫秒级) |
| n | integer | 非必传 默认值5 referer接口传 TopN |

**Request Demo:**

```json
{
  "code": "",
  "start": "",
  "end": "",
  "n": 0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| &ensp;&ensp;&#124;─browserStats | array | 浏览器统计 |
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─browser | string | 浏览器类型 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─pv | integer | 浏览量 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─uv | integer | 访客数 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─pvRatio | number | 浏览量占比 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─uvRatio | number | 访客数占比 |
| &ensp;&ensp;&#124;─osStats | array | 操作系统统计 |
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─os | string |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─pv | integer | 浏览量 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─uv | integer | 访客数 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─pvRatio | number | 浏览量占比 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─uvRatio | number | 访客数占比 |
| &ensp;&ensp;&#124;─deviceStats | array | 设备类型统计 |
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─deviceType | string |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─pv | integer | 浏览量 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─uv | integer | 访客数 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─pvRatio | number | 浏览量占比 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─uvRatio | number | 访客数占比 |
| &ensp;&ensp;&#124;─pvSum | integer | 总浏览量 |
| &ensp;&ensp;&#124;─uvSum | integer | 总访客数 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": {
    "browserStats": [
      {
        "browser": "",
        "pv": 0,
        "uv": 0,
        "pvRatio": 0.0,
        "uvRatio": 0.0
      }
    ],
    "osStats": [
      {
        "os": "",
        "pv": 0,
        "uv": 0,
        "pvRatio": 0.0,
        "uvRatio": 0.0
      }
    ],
    "deviceStats": [
      {
        "deviceType": "",
        "pv": 0,
        "uv": 0,
        "pvRatio": 0.0,
        "uvRatio": 0.0
      }
    ],
    "pvSum": 0,
    "uvSum": 0
  },
  "msg": ""
}
```




---
### 访问趋势图(天/小时)

> BASIC

**Path:** /visual/v1/trend

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | string | 短链码 |
| start | string | 开始区间(毫秒级) |
| end | string | 结束区间(毫秒级) |
| n | integer | 非必传 默认值5 referer接口传 TopN |

**Request Demo:**

```json
{
  "code": "",
  "start": "",
  "end": "",
  "n": 0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | array | 数据 |
| &ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─interval | string |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─pv | integer |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─uv | integer |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─ip | integer |  |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": [
    {
      "interval": "",
      "pv": 0,
      "uv": 0,
      "ip": 0
    }
  ],
  "msg": ""
}
```




---
### 访问来源TopN统计

> BASIC

**Path:** /visual/v1/referer

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | string | 短链码 |
| start | string | 开始区间(毫秒级) |
| end | string | 结束区间(毫秒级) |
| n | integer | 非必传 默认值5 referer接口传 TopN |

**Request Demo:**

```json
{
  "code": "",
  "start": "",
  "end": "",
  "n": 0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | array | 数据 |
| &ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─referer | string |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─pv | integer |  |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": [
    {
      "referer": "",
      "pv": 0
    }
  ],
  "msg": ""
}
```







# plato-account

exported at 2023-04-10 00:35:46

## 用户接口

用户接口


---
### 上传头像

> BASIC

**Path:** /account/v1/upload

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | multipart/form-data | YES |  |

**Form:**

| name | value | required | type | desc |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| file |  | NO | file |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": {},
  "msg": ""
}
```




---
### 用户登录

> BASIC

**Path:** /account/v1/login

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| phone | string |  |
| password | string |  |

**Request Demo:**

```json
{
  "phone": "",
  "password": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | string | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": "",
  "msg": ""
}
```




---
### 用户注册

> BASIC

**Path:** /account/v1/register

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| phone | string |  |
| password | string |  |
| rePassword | string |  |
| code | string | 短信验证码 |

**Request Demo:**

```json
{
  "phone": "",
  "password": "",
  "rePassword": "",
  "code": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": null,
  "msg": ""
}
```




---
### findByAccountNo

> BASIC

**Path:** /account/v1/{account_no}

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| account_no |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/x-www-form-urlencoded | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| &ensp;&ensp;&#124;─accountNo | integer |  |
| &ensp;&ensp;&#124;─avatar | string | 头像 |
| &ensp;&ensp;&#124;─phone | string | 手机号 |
| &ensp;&ensp;&#124;─password | string | 密码 |
| &ensp;&ensp;&#124;─secret | string | 盐，用于个人敏感信息处理 |
| &ensp;&ensp;&#124;─mail | string | 邮箱 |
| &ensp;&ensp;&#124;─username | string | 用户名 |
| &ensp;&ensp;&#124;─auth | string | 认证级别，DEFAULT，REALNAME，ENTERPRISE，访问次数不一样 |
| &ensp;&ensp;&#124;─id | integer | id<br>@TableId(value = "id", type = IdType.AUTO) |
| &ensp;&ensp;&#124;─createTime | string | 创建时间<br>@JsonIgnore |
| &ensp;&ensp;&#124;─updateTime | string | 更新时间<br>@JsonIgnore |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": {
    "accountNo": 0,
    "avatar": "",
    "phone": "",
    "password": "",
    "secret": "",
    "mail": "",
    "username": "",
    "auth": "",
    "id": 0,
    "createTime": "",
    "updateTime": ""
  },
  "msg": ""
}
```





## 验证接口

验证接口


---
### 发送短信验证码

> BASIC

**Path:** /notify/v1/send-code

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| type | string | 发送业务验证码类型<br>USER_REGISTER_PHONE :USER_REGISTER_PHONE<br>USER_LOGIN_PHONE :USER_LOGIN_PHONE<br>USER_REGISTER_EMAIl :USER_REGISTER_EMAIl<br>USER_LOGIN_EMAIl :USER_LOGIN_EMAIl |
| captcha | string |  |
| captchaId | string |  |
| to | string | 接收方，邮箱或手机号 |

**Request Demo:**

```json
{
  "type": "",
  "captcha": "",
  "captchaId": "",
  "to": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": null,
  "msg": ""
}
```




---
### 获取图形验证码

> BASIC

**Path:** /notify/v1/captcha

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| &ensp;&ensp;&#124;─key | object |  |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": {
    "": {}
  },
  "msg": ""
}
```




---
### test

> BASIC

**Path:** /notify/v1/test

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| name |  | NO |  |
| age |  | NO |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": null,
  "msg": ""
}
```







# plato-link

exported at 2023-04-10 00:35:46

## C端短链解析接口

C端短链解析接口


---
### 短链码跳转

> BASIC

**Path:** /{code}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| code |  |  |





## 短链接口

短链接口


---
### 短链创建

> BASIC

**Path:** /link/v1

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| groupId | integer | 分组id |
| title | string | 短链标题 |
| originalUrl | string | 原生url |
| domainId | integer | 域名id |
| domain | string | 域名 前端不需要传 |
| expired | string | 过期时间 永久则不用传 |

**Request Demo:**

```json
{
  "groupId": 0,
  "title": "",
  "originalUrl": "",
  "domainId": 0,
  "domain": "",
  "expired": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": null,
  "msg": ""
}
```




---
### 分页查询

> BASIC

**Path:** /link/v1/page

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| groupId | integer | 分组id |
| page | integer | 页码 |
| size | integer | 条数 |

**Request Demo:**

```json
{
  "groupId": 0,
  "page": 0,
  "size": 0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| &ensp;&ensp;&#124;─page | integer | 页码 |
| &ensp;&ensp;&#124;─size | integer | 条数 |
| &ensp;&ensp;&#124;─total | integer | 总条数 |
| &ensp;&ensp;&#124;─totalPage | integer | 总页数 |
| &ensp;&ensp;&#124;─items | array | 数据 |
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─groupId | integer | 分组id |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─title | string | 短链标题 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─originalUrl | string | 原url地址 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─domain | string | 短链域名 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─code | string | 短链码 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─longHash | string | 长链的hash码 方便查找 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─expired | string | 过期时间 永久为null |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─accountNo | integer | 账户唯一标识 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─state | string | 短链状态 lock：锁定 active：可用 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─linkLevel | string | 产品level FIRST青铜SECOND黄金THIRD钻石 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─logo | string | logo |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─id | integer | id<br>@TableId(value = "id", type = IdType.AUTO) |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─createTime | string | 创建时间<br>@JsonIgnore |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─updateTime | string | 更新时间<br>@JsonIgnore |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": {
    "page": 0,
    "size": 0,
    "total": 0,
    "totalPage": 0,
    "items": [
      {
        "groupId": 0,
        "title": "",
        "originalUrl": "",
        "domain": "",
        "code": "",
        "longHash": "",
        "expired": "",
        "accountNo": 0,
        "state": "",
        "linkLevel": "",
        "logo": "",
        "id": 0,
        "createTime": "",
        "updateTime": ""
      }
    ]
  },
  "msg": ""
}
```




---
### 短链更新

> BASIC

**Path:** /link/v1

**Method:** PUT

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| mappingId | integer | 短链id |
| code | string | 短链码<br>C端的partition key 要传 |
| groupId | integer | 分组id<br>暂时不允许更改，partition key的更改涉及到数据迁移 |
| title | string | 短链标题 |
| domainId | integer | 域名id |
| domain | string | 域名 前端不需要传 |
| expired | string | 过期时间 永久则不用传 |

**Request Demo:**

```json
{
  "mappingId": 0,
  "code": "",
  "groupId": 0,
  "title": "",
  "domainId": 0,
  "domain": "",
  "expired": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": null,
  "msg": ""
}
```




---
### 短链删除

> BASIC

**Path:** /link/v1

**Method:** DELETE

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| mappingId | integer | 短链id |
| code | string | 短链码 |
| groupId | integer | 分组id<br>因为B端的partition key是account_no和group_id 所以group_id要传 |

**Request Demo:**

```json
{
  "mappingId": 0,
  "code": "",
  "groupId": 0
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": null,
  "msg": ""
}
```




---
### 分布式可重入锁测试

> BASIC

**Path:** /link/v1/test-lock

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| code |  | YES |  |
| account_no |  | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | integer | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": 0,
  "msg": ""
}
```





## 域名接口

域名接口


---
### 可用域名列表

> BASIC

**Path:** /domain/v1/list

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | array | 数据 |
| &ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─accountNo | integer | 用户自己绑定的域名 |
| &ensp;&ensp;&ensp;&ensp;&#124;─domainType | string | 域名类型，自定义custom, 内置offical |
| &ensp;&ensp;&ensp;&ensp;&#124;─value | string |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | id<br>@TableId(value = "id", type = IdType.AUTO) |
| &ensp;&ensp;&ensp;&ensp;&#124;─createTime | string | 创建时间<br>@JsonIgnore |
| &ensp;&ensp;&ensp;&ensp;&#124;─updateTime | string | 更新时间<br>@JsonIgnore |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": [
    {
      "accountNo": 0,
      "domainType": "",
      "value": "",
      "id": 0,
      "createTime": "",
      "updateTime": ""
    }
  ],
  "msg": ""
}
```





## 分组接口

分组接口


---
### 创建分组

> BASIC

**Path:** /group/v1

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| title | string | 分组标题 |

**Request Demo:**

```json
{
  "title": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": null,
  "msg": ""
}
```




---
### 删除分组

> BASIC

**Path:** /group/v1/{group_id}

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| group_id |  |  |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/x-www-form-urlencoded | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": null,
  "msg": ""
}
```




---
### 更新分组

> BASIC

**Path:** /group/v1

**Method:** PUT

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**Request Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| id | integer | 分组id |
| title | string | 分组标题 |

**Request Demo:**

```json
{
  "id": 0,
  "title": ""
}
```



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": null,
  "msg": ""
}
```




---
### 分组详情

> BASIC

**Path:** /group/v1/{group_id}

**Method:** GET

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| group_id |  |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | object | 数据 |
| &ensp;&ensp;&#124;─title | string | 短链分组名 |
| &ensp;&ensp;&#124;─accountNo | integer | 账户唯一标识 |
| &ensp;&ensp;&#124;─linkSum | integer | 组内短链数 |
| &ensp;&ensp;&#124;─id | integer | id<br>@TableId(value = "id", type = IdType.AUTO) |
| &ensp;&ensp;&#124;─createTime | string | 创建时间<br>@JsonIgnore |
| &ensp;&ensp;&#124;─updateTime | string | 更新时间<br>@JsonIgnore |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": {
    "title": "",
    "accountNo": 0,
    "linkSum": 0,
    "id": 0,
    "createTime": "",
    "updateTime": ""
  },
  "msg": ""
}
```




---
### 分组列表

> BASIC

**Path:** /group/v1/list

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| code | integer | 状态码 0 表示成功 |
| data | array | 数据 |
| &ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&#124;─title | string | 短链分组名 |
| &ensp;&ensp;&ensp;&ensp;&#124;─accountNo | integer | 账户唯一标识 |
| &ensp;&ensp;&ensp;&ensp;&#124;─linkSum | integer | 组内短链数 |
| &ensp;&ensp;&ensp;&ensp;&#124;─id | integer | id<br>@TableId(value = "id", type = IdType.AUTO) |
| &ensp;&ensp;&ensp;&ensp;&#124;─createTime | string | 创建时间<br>@JsonIgnore |
| &ensp;&ensp;&ensp;&ensp;&#124;─updateTime | string | 更新时间<br>@JsonIgnore |
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": [
    {
      "title": "",
      "accountNo": 0,
      "linkSum": 0,
      "id": 0,
      "createTime": "",
      "updateTime": ""
    }
  ],
  "msg": ""
}
```







