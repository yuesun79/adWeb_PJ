# API initial version

[toc]

### 一、用户操作

#### 注册

##### 提交表单，接口：http://localhost:8181/register/submit

```
data...（个人信息与虚拟形象信息）
```

注册验证信息接口balabala？

#### 登录

##### 提交表单，接口：http://localhost:8181/login



#### 修改个人资料与虚拟形象

##### 接口：http://localhost:8181/userinfo

```
data...
```



### 二、虚拟课堂操作

开启课堂？（课堂是人为分配还是？？）开启课程？（课程没啥用感觉）


### 学习任务操作

#### 学生接受个人学习任务

##### 接口：http://localhost:8181/personaltaskon

```
data...（任务ID，学生ID，任务状态等）
```

#### 学生加入团队任务

##### 接口：http://localhost:8181/joingrouptask

```
data...（任务ID，学生ID，任务人数等）
```

#### 人数已满，开始团队任务

##### 接口：http://localhost:8181/grouptaskon

```
data...
```

#### 团队任务指定组长

##### 接口：http://localhost:8181/groupleader

```
data...（学生ID，任务ID，团队ID等）
```

#### 学生提交个人任务

##### 接口：http://localhost:8181/personalsubmit

```
data...（学生ID，任务ID）
```

#### 团队提交团队任务

##### 接口：http://localhost:8181/groupsubmit

```
data...
```

#### 组长分配贡献经验值

##### 接口：http://localhost:8181/groupsubmit/expval

```
data...（组长ID，任务ID，分配比率等）
```

#### 任务完成关闭房间

##### 接口：http://localhost:8181/roomoff

#### 学生自由发布学习任务

##### 接口：http://localhost:8181/freetask

```
data...
```

#### 学生接受自由任务

##### 接口：http://localhost:8181/freetaskon

```
data...
```

#### 学生提交自由任务

##### 接口：http://localhost:8181/freesubmit

```
data...
```



### 三、老师与助教操作

#### 发布学习任务

##### 发布个人学习任务，接口：http://localhost:8181/personaltask

```
data...
```

##### 发布团队学习任务，需要分配虚拟房间，接口：http://localhost:8181/grouptask

```
data...
```

#### 审核自由发布的任务

##### 接口：http://localhost:8181/taskcheck

```
data...
```
#### 审核完成的任务

##### 接口：http://localhost:8181/finishedtaskcheck

```
data...
```

#### 学习社区日常管理



### 四、学习经验值商城
#### 经验商城交换资料
##### 接口：http://localhost:8181/exchange