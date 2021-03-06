# 分布式锁starter
>1.一个注解即可搞定的分布式锁，使用超级简单  
>2.基于redis实现的非公平锁
---
#### 使用步骤：
+ 第一步 引入依赖的starter（已上传maven中央仓库）
   + gradle：
   ```
   compile ("com.github.boylong12:dlock-spring-boot-starter:${dlcok.version}")
   ```
   + maven:
   ```
  <dependency>
       <groupId>com.github.boylong12</groupId>
       <artifactId>dlock-spring-boot-starter</artifactId>
       <version>${dlcok.version}</version>
   </dependency>
   ```
+ 第二步 启动dlock（两种方式任选）
  + 通过Enable注解，将```@EnableDlock```注解标注在启动类上
  + 通过配置文件，```com.ldcr.dlock.enable=true```写在配置文件中
+ 第三步 在需要使用分布式锁的业务方法上标注```@Dlock```注解
```
@Dlock(keys = "#userId", maxRetry = 1)
@Override
public String sayHello(String userId) {
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return userId;
}
```
+ 第四步 配置redis。
### 使用demo  [dlock-demo](dlock-demo)
### 关机时优雅释放锁的说明
+ 开启方式（两种方式任选）
  + 通过注解，将```@EnableDlock(gracefulRelease = true)```注解标注在启动类上
  + 通过配置文件，```com.ldcr.dlock.gracefulRelease=true```写在配置文件中
+ 使用建议
  + 建议在不支持优雅关机的情况下打开
  + 在支持优雅关机的情况下慎重打开，因为有些优雅关机的实现方式会导致提前释放锁。
  + springboot从2.3.0.RELEASE开始支持优雅关机
---
@Dlock配置说明：

|字段|类型|默认值|说明|
|:---|:---:|:---:|:---|
|keyPreType|KeyPreTypeEnum|KeyPreTypeEnum#PACKAGE|锁名称前缀类型
|keyPre|String|""|锁名称的自定义前缀<br>只有keyPreType()=KeyPreTypeEnum#CUSTOM时才有效
|keys|String[]|""|锁名称 <br>默认以全类名为前缀<br>支持spel表达式<br>eg #user.id|
|expire|long|30000|过期时间 单位：毫秒 <br>注意：过期时间一定要大于业务的执行时间|
|timeout|long|3000|获取锁的超时时间 单位：毫秒 <br>根据业务确定。由于会阻塞程序执行，不宜设置过长，尤其是在高并发场景下<br>和maxRetry互斥，两者只会有一个生效 <br>优先级：maxRetry大于timeout|
|maxRetry|int|0|获取锁最大重试次数 <br>大于0才生效 <br>根据业务确定。由于会阻塞程序执行，不宜设置过大，尤其是在高并发场景下 <br>和timeout互斥，两者只会有一个生效 <br>优先级：maxRetry大于timeout|

@EnableDlock配置说明：

|字段|类型|默认值|说明|
|:---|:---:|:---:|:---|
|gracefulRelease|boolean|false|关机时优雅释放锁标识<br>建议在不支持优雅关机的情况下打开<br>在支持优雅关机的情况下慎重打开。因为有可能会提前释放锁<br>springboot从2.3.0.RELEASE开始支持优雅关机
### future
+ 引入其他的实现形式，比如zk(公平锁)
+ 多种实现形式插件化，支持自主选择