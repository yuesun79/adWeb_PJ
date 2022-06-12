# Community API文档


<a name="overview"></a>
## 概览
web3d virtual community


### 版本信息
*版本* : 1.0


### 许可信息
*许可证* : The Apache License  
*许可网址* : http://www.baidu.com  
*服务条款* : null


### URI scheme
*域名* : localhost:8081  
*基础路径* : /


### 标签

* AcceptTaskController : Accept Task Controller
* PostTaskController : Post Task Controller
* RetrieveInfoController : Retrieve Info Controller




<a name="paths"></a>
## 资源

<a name="accepttaskcontroller_resource"></a>
### AcceptTaskController
Accept Task Controller


<a name="assigngroupinfousingput"></a>
#### 设置组相关信息 组名/组长
```
PUT /assignGroupInfo
```


##### 说明
update name/group_leader in v_group table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**assignGroupInfoRequest**  <br>*必填*|assignGroupInfoRequest|[AssignGroupInfoRequest](#assigngroupinforequest)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|组名/组长设置成功|object|
|**201**|Created|无内容|
|**400**|该组不存在|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/assignGroupInfo
```


###### 请求 body
```
json :
{
  "groupId" : 0,
  "groupLeader" : 0,
  "name" : "string",
  "taskId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="acceptgrouptaskusingpost"></a>
#### 用户加入团队任务，等待人数凑齐
```
POST /groupTaskOn
```


##### 说明
insert accept, v_group, in_group table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**acceptTaskRequest**  <br>*必填*|acceptTaskRequest|[AcceptTaskRequest](#accepttaskrequest)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|加入任务成功|[AcceptTaskResponse](#accepttaskresponse)|
|**201**|接受任务成功|[AcceptTaskResponse](#accepttaskresponse)|
|**400**|已经接受过该任务|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/groupTaskOn
```


###### 请求 body
```
json :
{
  "taskId" : 0,
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "message" : "string",
  "process" : "string"
}
```


###### 响应 201
```
json :
{
  "message" : "string",
  "process" : "string"
}
```


<a name="acceptpersonaltaskusingpost"></a>
#### 用户接受个人任务
```
POST /personalTaskOn
```


##### 说明
insert accept table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**acceptTaskRequest**  <br>*可选*|acceptTaskRequest|[AcceptTaskRequest](#accepttaskrequest)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**201**|接受任务成功|object|
|**400**|已经接受过该任务|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/personalTaskOn
```


###### 请求 body
```
json :
{
  "taskId" : 0,
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


###### 响应 201
```
json :
"object"
```


<a name="submitgrouptaskusingput"></a>
#### 提交团队任务
```
PUT /submitGroupTask
```


##### 说明
update process in group table(group_id)


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**submitGTaskRequest**  <br>*必填*|submitGTaskRequest|[SubmitGTaskRequest](#submitgtaskrequest)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|已提交|object|
|**201**|Created|无内容|
|**400**|该任务不存在|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/submitGroupTask
```


###### 请求 body
```
json :
{
  "file" : "string",
  "groupId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="assignevusingput"></a>
#### 组长分配经验值
```
PUT /submitGroupTask/exp
```


##### 说明
update process in group table(group_id), update EV in user table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**assignEVRequest**  <br>*必填*|assignEVRequest|[AssignEVRequest](#assignevrequest)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|已提交|object|
|**201**|Created|无内容|
|**400**|组员信息有误|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/submitGroupTask/exp
```


###### 请求 body
```
json :
{
  "groupId" : 0,
  "score" : {
    "string" : 0
  },
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="submitpersonaltaskusingput"></a>
#### 提交个人任务
```
PUT /submitPersonalTask
```


##### 说明
update process in accept table(task_id)


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**submitPTaskRequest**  <br>*必填*|submitPTaskRequest|[SubmitPTaskRequest](#submitptaskrequest)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|已提交|object|
|**201**|Created|无内容|
|**400**|该任务不存在|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/submitPersonalTask
```


###### 请求 body
```
json :
{
  "file" : "string",
  "taskId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="posttaskcontroller_resource"></a>
### PostTaskController
Post Task Controller


<a name="checkcompletion_groupusingput"></a>
#### 管理员审核团队任务完成
```
PUT /admin/checkCompletion/groupTask
```


##### 说明
update v_group table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**checkGTaskCompleteRequest**  <br>*必填*|checkGTaskCompleteRequest|[CheckGTaskCompleteRequest](#checkgtaskcompleterequest)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**201**|Created|无内容|
|**400**|信息不对balabala|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/admin/checkCompletion/groupTask
```


###### 请求 body
```
json :
{
  "groupId" : 0,
  "taskId" : 0,
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="checkcompletion_personalusingput"></a>
#### 管理员审核个人任务完成
```
PUT /admin/checkCompletion/personalTask
```


##### 说明
update accept table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**checkPTaskCompleteRequest**  <br>*必填*|checkPTaskCompleteRequest|[CheckPTaskCompleteRequest](#checkptaskcompleterequest)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**201**|Created|无内容|
|**400**|信息不对balabala|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/admin/checkCompletion/personalTask
```


###### 请求 body
```
json :
{
  "taskId" : 0,
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="checkfreetaskusingput"></a>
#### 管理员审核自由任务
```
PUT /admin/checkFreeTask
```


##### 说明
update task table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**taskId**  <br>*必填*|taskId|integer (int64)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**201**|Created|无内容|
|**400**|信息不对balabala|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/admin/checkFreeTask
```


###### 请求 query
```
json :
{
  "taskId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="creategrouptaskusingpost"></a>
#### 管理员发布团队任务
```
POST /admin/createGroupTask
```


##### 说明
insert task table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**task**  <br>*必填*|task|string|
|**Query**|**userId**  <br>*必填*|userId|integer (int64)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**201**|Created|无内容|
|**400**|userId不对/信息不全等|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/admin/createGroupTask
```


###### 请求 query
```
json :
{
  "task" : "string",
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="createpersonaltaskusingpost"></a>
#### 管理员发布个人任务
```
POST /admin/createPersonalTask
```


##### 说明
insert task table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**task**  <br>*必填*|task|string|
|**Query**|**userId**  <br>*必填*|userId|integer (int64)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**201**|Created|无内容|
|**400**|userId不对/信息不全等|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/admin/createPersonalTask
```


###### 请求 query
```
json :
{
  "task" : "string",
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="checkcompletion_freeusingput"></a>
#### user审核自由任务完成
```
PUT /checkCompletion/freeTask
```


##### 说明
update accept table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**checkPTaskCompleteRequest**  <br>*必填*|checkPTaskCompleteRequest|[CheckPTaskCompleteRequest](#checkptaskcompleterequest)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**201**|Created|无内容|
|**400**|信息不对balabala|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/checkCompletion/freeTask
```


###### 请求 body
```
json :
{
  "taskId" : 0,
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="createfreetaskusingpost"></a>
#### 发布自由任务
```
POST /createFreeTask
```


##### 说明
insert task table


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**task**  <br>*必填*|task|string|
|**Query**|**userId**  <br>*必填*|userId|integer (int64)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**201**|Created|无内容|
|**400**|userId不对/信息不全等|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/createFreeTask
```


###### 请求 query
```
json :
{
  "task" : "string",
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="retrieveinfocontroller_resource"></a>
### RetrieveInfoController
Retrieve Info Controller


<a name="retrievetasks_uncheckedusingget"></a>
#### 管理员获取未审核的所有的任务
```
GET /admin/retrieveTasks/uncheck
```


##### 说明
select task


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< string, object > map|
|**400**|userId不对|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/admin/retrieveTasks/uncheck
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="retrievetasks_unfinishedgroupusingget"></a>
#### 管理员获取未审核完成度的团队任务
```
GET /admin/retrieveTasks/unfinishedGroup
```


##### 说明
select task + group + accept + user


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< string, object > map|
|**400**|userId不对|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/admin/retrieveTasks/unfinishedGroup
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="retrievetasks_unfinishedpersonalusingget"></a>
#### 管理员获取未审核完成度的个人任务
```
GET /admin/retrieveTasks/unfinishedPersonal
```


##### 说明
select task + accept + user


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< string, object > map|
|**400**|userId不对|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/admin/retrieveTasks/unfinishedPersonal
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="retrievegroupusersusingget"></a>
#### 获取当前组的组员信息（个人贡献）
```
GET /retrieveGroupUsers
```


##### 说明
select user table join in_group


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**groupId**  <br>*必填*|groupId|integer (int32)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< string, object > map|
|**400**|userId不对|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/retrieveGroupUsers
```


###### 请求 query
```
json :
{
  "groupId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="retrievetasks_classusingget"></a>
#### 获取该课堂所有的任务
```
GET /retrieveTasks/class
```


##### 说明
select task table join class_task join v_class


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**classId**  <br>*必填*|classId|integer (int32)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< string, object > map|
|**400**|Class(classId=xxx) doesn't exist|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/retrieveTasks/class
```


###### 请求 query
```
json :
{
  "classId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="retrievetasks_unfinishedfreeusingget"></a>
#### user获取未审核完成度的自由任务
```
GET /retrieveTasks/unfinishedFree
```


##### 说明
select task + accept + user


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**userId**  <br>*必填*|userId|integer (int32)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< string, object > map|
|**400**|userId不对|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/retrieveTasks/unfinishedFree
```


###### 请求 query
```
json :
{
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```


<a name="retrievetasks_userusingget"></a>
#### 获取某用户所有的任务
```
GET /retrieveTasks/user
```


##### 说明
select task table join accept join user


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**userId**  <br>*必填*|userId|integer (int32)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< string, object > map|
|**400**|userId不对|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/retrieveTasks/user
```


###### 请求 query
```
json :
{
  "userId" : 0
}
```


##### HTTP响应示例

###### 响应 200
```
json :
"object"
```




<a name="definitions"></a>
## 定义

<a name="accepttaskrequest"></a>
### AcceptTaskRequest

|名称|说明|类型|
|---|---|---|
|**taskId**  <br>*可选*|**样例** : `0`|integer (int32)|
|**userId**  <br>*可选*|**样例** : `0`|integer (int32)|


<a name="accepttaskresponse"></a>
### AcceptTaskResponse

|名称|说明|类型|
|---|---|---|
|**message**  <br>*可选*|**样例** : `"string"`|string|
|**process**  <br>*可选*|**样例** : `"string"`|string|


<a name="assignevrequest"></a>
### AssignEVRequest

|名称|说明|类型|
|---|---|---|
|**groupId**  <br>*可选*|**样例** : `0`|integer (int64)|
|**score**  <br>*可选*|**样例** : `{<br>  "string" : 0<br>}`|< string, integer (int32) > map|
|**userId**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="assigngroupinforequest"></a>
### AssignGroupInfoRequest

|名称|说明|类型|
|---|---|---|
|**groupId**  <br>*可选*|**样例** : `0`|integer (int64)|
|**groupLeader**  <br>*可选*|**样例** : `0`|integer (int64)|
|**name**  <br>*可选*|**样例** : `"string"`|string|
|**taskId**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="checkgtaskcompleterequest"></a>
### CheckGTaskCompleteRequest

|名称|说明|类型|
|---|---|---|
|**groupId**  <br>*可选*|**样例** : `0`|integer (int64)|
|**taskId**  <br>*可选*|**样例** : `0`|integer (int64)|
|**userId**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="checkptaskcompleterequest"></a>
### CheckPTaskCompleteRequest

|名称|说明|类型|
|---|---|---|
|**taskId**  <br>*可选*|**样例** : `0`|integer (int64)|
|**userId**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="submitgtaskrequest"></a>
### SubmitGTaskRequest

|名称|说明|类型|
|---|---|---|
|**file**  <br>*可选*|**模式** : `"^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==\|[A-Za-z0-9+/]{3}=)?$"`**样例** : `"string"`|string (byte)|
|**groupId**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="submitptaskrequest"></a>
### SubmitPTaskRequest

|名称|说明|类型|
|---|---|---|
|**file**  <br>*可选*|**模式** : `"^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==\|[A-Za-z0-9+/]{3}=)?$"`**样例** : `"string"`|string (byte)|
|**taskId**  <br>*可选*|**样例** : `0`|integer (int64)|

### login
|名称|说明|类型| 
|---|---|---|
|**username**  <br>*必要*|**样例** : `Test"`|string|
|**password**  <br>*必要*|**样例** : `123456`|string|

##### HTTP响应示例

###### 响应 200
```
json :
"token"
```
###### 响应 500
```
json :
"message"
```
### register
|名称|说明|类型| 
|---|---|---|
|**username**  <br>*必要*|**样例** : `Test"`|string|
|**password**  <br>*必要*|**样例** : `123456`|string|
|**email**  <br>*必要*|**样例** : `Test@qq.com"`|string|
|**phone_num**  <br>*必要*|**样例** : `123456789`|string|
|**gender**  <br>*必要*|**样例** : `man or woman"`|string|
##### HTTP响应示例

###### 响应 200
```
json :
"注册成功"
```
###### 响应 500
```
json :
"错误信息"
```
### 拦截器机制
##### HTTP响应示例

###### 响应 200
```
json :
"token success"
```
###### 响应 401
```
json :
"token verify fail"
```



