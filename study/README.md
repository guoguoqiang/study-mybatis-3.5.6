# mybatis学习模块
学习资料
https://mybatis.org/mybatis-3/zh/configuration.html#mappers


## 1、如果自学新建模块 引用自己下载mybatis 源码作为父模块，需要注释
```
<!-- <optional>true</optional>-->
```

```
<dependency>
  <groupId>ognl</groupId>
  <artifactId>ognl</artifactId>
  <version>3.2.15</version>
  <scope>compile</scope>
 <!-- 当其他项目继承本项目时，如果dependences中加上了<optional>true</optional>，表示当前依赖不向下传递。-->
 <!-- <optional>true</optional>-->
</dependency>
<dependency>
  <groupId>org.javassist</groupId>
  <artifactId>javassist</artifactId>
  <version>3.27.0-GA</version>
  <scope>compile</scope>
<!--  <optional>true</optional>-->
</dependency>
```





## 2、注释插件 代码
```
<!-- <argLine>${argLine} -Xmx2048m</argLine>-->
```

```
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <configuration>
    <!-- <argLine>${argLine} -Xmx2048m</argLine>-->
    <systemProperties>
      <property>
        <name>derby.stream.error.file</name>
        <value>${project.build.directory}/derby.log</value>
      </property>
      <property>
        <name>derby.system.home</name>
        <value>${project.build.directory}</value>
      </property>
    </systemProperties>
    <!--新加的-->
    <skip>true</skip>
    <skipTests>true</skipTests>
  </configuration>
</plugin>
```



```
<!--
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pdf-plugin</artifactId>
  </plugin>
  -->
```


## 3、mybatis 搭建步骤：

1、添加pom依赖（mybatis的核心jar包和数据库驱动对应的驱动jar包）

2、新建数据库和表

3、添加mybatis全局配置文件（可以从官网中复制）

4、修改全局配置文件中的数据源配置信息

5、添加数据库表对应的POJO对象（相当于我们以前的实体类）

6、添加对应的PojoMapper.xml（里面维护了所有的sql）
    
    修改namespace：如果是StatementId没有特殊的要求
                   如果是接口绑定方式必须等于接口的完整限定名
    修改对应的id（唯一）和resultType(对应返回的类型，如果是POJO需要指定完整限定名)

7、修改mybatis的全局配置文件：修改Mapper


## 4、2种方式获取数据方式
```
        // 方式一、 mybatis3 之前方式(ibatis)  namespace + selectById
//        User user = (User) session.selectOne("org.mybatis.study.mapper.UserMapper.selectById", 1L );

        // 方式二、 mybatis3 之后 接口绑定方式  （这个坑比较深，目录要一个一个创建）
        // (resource 下面的路径要和 接口包 路径一致 org.mybatis.study.mapper 这个路径不能直接使用.要一个文件夹一个文件夹的创建)
```

## 5、核心配置文件mybatis-config.xml中配置mappers的几种方式
```
  <mappers>
    <!--方式1  mybatis3之前（ibatis）mapper resource=""
                                    不用保证同接口同包同名-->
    <!-- <mapper resource="mapper/UserMapper.xml"/>-->

    <!--方式2 mybatis3及以后接口绑定方式 mapper class="接口路径"
                                  保证接口名（例如IUserDao）和xml名（IUserDao.xml）相同，还必须在同一个包中 -->
    <!-- <mapper class="org.mybatis.study.mapper.UserMapper"></mapper>-->

    <!--方式3 package name="映射文件所在包名"
                  必须保证接口名（例如IUserDao）和xml名（IUserDao.xml）相同，还必须在同一个包中 -->
    <package name="org.mybatis.study.mapper"/>
    
 <!--方式4 mapper url="文件路径名" 不推荐
             引用网路路径或者磁盘路径下的sql映射文件 file:///var/mappers/AuthorMapper.xml -->
  <mapper url="file:D:/my_project/mybatis-3-mybatis-3.5.6/study/src/main/resources/org/mybatis/study/mapper/UserMapper.xml"/>
  </mappers>
```
