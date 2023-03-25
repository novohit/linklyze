# plato-account

exported at 2023-03-25 16:45:14

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
### token

> BASIC

**Path:** /account/v1/test-token

**Method:** POST

> REQUEST

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
  "data": {},
  "msg": ""
}
```




---
### tokenVerify

> BASIC

**Path:** /account/v1/test-token-verify

**Method:** POST

> REQUEST

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/x-www-form-urlencoded | YES |  |

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| token |  | NO |  |



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
  "data": {},
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
| msg | string | 描述 |

**Response Demo:**

```json
{
  "code": 0,
  "data": {},
  "msg": ""
}
```





## 验证接口

验证接口


---
### 发送短信验证码

> BASIC

**Path:** /notify/v1/send-code

**Method:** GET

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
  "data": {},
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
  "data": {},
  "msg": ""
}
```







# plato-link

exported at 2023-03-25 16:45:14

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
### 创建短链

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
  "data": {},
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
| page | integer | 页码 |
| size | integer | 条数 |
| groupId | integer | 分组id |

**Request Demo:**

```json
{
  "page": 0,
  "size": 0,
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
  "data": {},
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
  "data": {},
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
  "data": {},
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
  "data": {},
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







