# mybatis学习模块
学习资料
https://mybatis.org/mybatis-3/zh/configuration.html#mappers


1、如果自学新建模块 引用自己下载mybatis 源码作为父模块，需要注释
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





2、注释插件 代码
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


