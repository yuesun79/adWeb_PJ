# Community API文档
[toc]
## 1	环境变量

### 默认环境1
| 参数名 | 字段值 |
| ------ | ------ |
|baseUrl|http://localhost:8081/api|


## 2	Community API文档

##### 说明
> 



##### 联系方式
- **联系人：**
- **邮箱：**
- **网址：**//

##### 文档版本
```
1.0
```


## 3	PostTaskController

## 3.1	管理员审核团队任务完成

> PUT  /admin/checkCompletion/groupTask
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| groupId|int32||false||
| taskId|int32||false||
| userId|int32||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> update v_group table




## 3.2	管理员审核个人任务完成

> PUT  /admin/checkCompletion/personalTask
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| taskId|int32||false||
| userId|int32||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> update accept table




## 3.3	管理员审核自由任务

> PUT  /admin/checkFreeTask
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|taskId||taskId|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> update task table




## 3.4	管理员发布团队任务

> POST  /admin/createGroupTask
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|ddl.date|||
|ddl.day|||
|ddl.hours|||
|ddl.minutes|||
|ddl.month|||
|ddl.nanos|||
|ddl.seconds|||
|ddl.time|||
|ddl.timezoneOffset|||
|ddl.year|||
|description|||
|ev|||
|id|||
|name|||
|optional|||
|publisherId|||
|teamSize|||
|validity|||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> insert task table




## 3.5	管理员发布个人任务

> POST  /admin/createPersonalTask
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|ddl.date|||
|ddl.day|||
|ddl.hours|||
|ddl.minutes|||
|ddl.month|||
|ddl.nanos|||
|ddl.seconds|||
|ddl.time|||
|ddl.timezoneOffset|||
|ddl.year|||
|description|||
|ev|||
|id|||
|name|||
|optional|||
|publisherId|||
|teamSize|||
|validity|||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> insert task table




## 3.6	user审核自由任务完成

> PUT  /checkCompletion/freeTask
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| taskId|int32||false||
| userId|int32||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> update accept table




## 3.7	发布自由任务

> PUT  /createFreeTask
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|ddl.date|||
|ddl.day|||
|ddl.hours|||
|ddl.minutes|||
|ddl.month|||
|ddl.nanos|||
|ddl.seconds|||
|ddl.time|||
|ddl.timezoneOffset|||
|ddl.year|||
|description|||
|ev|||
|id|||
|name|||
|optional|||
|publisherId|||
|teamSize|||
|validity|||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> insert task table




## 4	RetrieveInfoController

## 4.1	管理员获取未审核的所有的任务

> GET  /admin/retrieveTasks/uncheck
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> select task




## 4.2	管理员获取未审核完成度的团队任务

> GET  /admin/retrieveTasks/unfinishedGroup
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> select task + group + accept + user




## 4.3	管理员获取未审核完成度的个人任务

> GET  /admin/retrieveTasks/unfinishedPersonal
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> select task + accept + user




## 4.4	获取当前组的组员信息（个人贡献）

> GET  /retrieveGroupUsers
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|groupId||groupId|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> select user table join in_group




## 4.5	获取taskId对应的任务

> GET  /retrieveTask/{id}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| ddl|object||false||
|⇥ date|int32||false||
|⇥ day|int32||false||
|⇥ hours|int32||false||
|⇥ minutes|int32||false||
|⇥ month|int32||false||
|⇥ nanos|int32||false||
|⇥ seconds|int32||false||
|⇥ time|int32||false||
|⇥ timezoneOffset|int32||false||
|⇥ year|int32||false||
| description|string||false||
| ev|int32||false||
| id|int32||false||
| name|string||false||
| optional|int32||false||
| publisherEmail|string||false||
| publisherId|int32||false||
| publisherName|string||false||
| publisherPhone|string||false||
| teamSize|int32||false||
| validity|int32||false||

##### 接口描述
> select task table




## 4.6	获取该课堂所有的任务

> GET  /retrieveTasks/class
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|classId||classId|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| result|array[object]||false||
|⇥ ddl|object||false||
|⇥⇥ date|int32||false||
|⇥⇥ day|int32||false||
|⇥⇥ hours|int32||false||
|⇥⇥ minutes|int32||false||
|⇥⇥ month|int32||false||
|⇥⇥ nanos|int32||false||
|⇥⇥ seconds|int32||false||
|⇥⇥ time|int32||false||
|⇥⇥ timezoneOffset|int32||false||
|⇥⇥ year|int32||false||
|⇥ description|string||false||
|⇥ ev|int32||false||
|⇥ id|int32||false||
|⇥ name|string||false||
|⇥ optional|int32||false||
|⇥ publisherEmail|string||false||
|⇥ publisherId|int32||false||
|⇥ publisherName|string||false||
|⇥ publisherPhone|string||false||
|⇥ teamSize|int32||false||
|⇥ validity|int32||false||

##### 接口描述
> select task table join class_task join v_class




## 4.7	user获取未审核完成度的自由任务

> GET  /retrieveTasks/unfinishedFree
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|userId||userId|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> select task + accept + user




## 4.8	获取某用户所有的任务

> GET  /retrieveTasks/user
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|userId||userId|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| group|array[object]||false||
|⇥ ddl|object||false||
|⇥⇥ date|int32||false||
|⇥⇥ day|int32||false||
|⇥⇥ hours|int32||false||
|⇥⇥ minutes|int32||false||
|⇥⇥ month|int32||false||
|⇥⇥ nanos|int32||false||
|⇥⇥ seconds|int32||false||
|⇥⇥ time|int32||false||
|⇥⇥ timezoneOffset|int32||false||
|⇥⇥ year|int32||false||
|⇥ description|string||false||
|⇥ ev|int32||false||
|⇥ file|object||false||
|⇥⇥ binaryStream|object||false||
|⇥ groupId|int32||false||
|⇥ groupLeader|int32||false||
|⇥ groupName|string||false||
|⇥ groupProcess|int32||false||
|⇥ id|int32||false||
|⇥ name|string||false||
|⇥ optional|int32||false||
|⇥ publisherEmail|string||false||
|⇥ publisherId|int32||false||
|⇥ publisherName|string||false||
|⇥ publisherPhone|string||false||
|⇥ teamSize|int32||false||
|⇥ validity|int32||false||
| personal|array[object]||false||
|⇥ ddl|object||false||
|⇥⇥ date|int32||false||
|⇥⇥ day|int32||false||
|⇥⇥ hours|int32||false||
|⇥⇥ minutes|int32||false||
|⇥⇥ month|int32||false||
|⇥⇥ nanos|int32||false||
|⇥⇥ seconds|int32||false||
|⇥⇥ time|int32||false||
|⇥⇥ timezoneOffset|int32||false||
|⇥⇥ year|int32||false||
|⇥ description|string||false||
|⇥ ev|int32||false||
|⇥ id|int32||false||
|⇥ name|string||false||
|⇥ optional|int32||false||
|⇥ publisherEmail|string||false||
|⇥ publisherId|int32||false||
|⇥ publisherName|string||false||
|⇥ publisherPhone|string||false||
|⇥ teamSize|int32||false||
|⇥ validity|int32||false||

##### 接口描述
> select task table join accept join user




## 4.9	获取某用户信息

> GET  /retrieveUserInfo
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|userId||userId|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| ev|int32||false||
| id|int32||false||
| password|string||false||
| phoneNum|string||false||
| registerDate|object||false||
|⇥ date|int32||false||
|⇥ day|int32||false||
|⇥ hours|int32||false||
|⇥ minutes|int32||false||
|⇥ month|int32||false||
|⇥ nanos|int32||false||
|⇥ seconds|int32||false||
|⇥ time|int32||false||
|⇥ timezoneOffset|int32||false||
|⇥ year|int32||false||
| username|string||false||

##### 接口描述
> select user table




## 5	User Controller

## 5.1	login

> GET  /login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| password|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.2	login

> POST  /login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| password|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.3	login

> DELETE  /login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| password|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.4	login

> PUT  /login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| password|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.5	login

> PATCH  /login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| password|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.6	login

> OPTIONS  /login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| password|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.7	login

> HEAD  /login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| password|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.8	register

> GET  /register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.9	register

> POST  /register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.10	register

> DELETE  /register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.11	register

> PUT  /register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.12	register

> PATCH  /register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.13	register

> OPTIONS  /register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.14	register

> HEAD  /register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.15	username

> GET  /username
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.16	username

> POST  /username
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.17	username

> DELETE  /username
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.18	username

> PUT  /username
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.19	username

> PATCH  /username
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.20	username

> OPTIONS  /username
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 5.21	username

> HEAD  /username
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false||
| password|string||false||
| phoneNum|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|string||false||
| message|string||false||
| obj|object||false||

##### 接口描述
> 




## 6	AcceptTaskController

## 6.1	设置组相关信息 组名/组长

> PUT  /assignGroupInfo
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| groupId|int32||false||
| groupLeader|int32||false||
| name|string||false||
| taskId|int32||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> update name/group_leader in v_group table




## 6.2	用户加入团队任务，等待人数凑齐

> POST  /groupTaskOn
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| taskId|int32||false||
| userId|int32||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> insert v_group, in_group table




## 6.3	用户接受个人任务

> POST  /personalTaskOn
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| taskId|int32||false||
| userId|int32||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> insert accept table




## 6.4	提交团队任务

> PUT  /submitGroupTask
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|groupId||groupId|
|userId||userId|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| file|string||false|file|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> update process in group table(group_id)




## 6.5	组长分配经验值

> PUT  /submitGroupTask/exp
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| groupId|int32||false||
| score|object||false||
| userId|int32||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> update process in group table(group_id), update EV in user table




## 6.6	提交个人任务

> PUT  /submitPersonalTask
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|taskId||taskId|
|userId||userId|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| file|string||false|file|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> update process in accept table(task_id)



