# spring-demo

## 启动方式
#### 1、运行Application这个类的main方法

#### 2、mvn spring-boot:run

#### 3、先到项目根目录
##### mvn install -Dmaven.test.skip=true
##### cd target
##### java -jar   xxxx.jar

##注意事项
* 需要把建表的Engine设置为InnoDB格式
* 自动建表的格式为MyISAM不支持事务管理。

### swagger打开地址
#### http://localhost:8080/swagger-ui.html