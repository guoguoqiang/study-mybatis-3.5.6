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
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectById(1L);
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
  <!--<mapper url="file:D:/my_project/mybatis-3-mybatis-3.5.6/study/src/main/resources/org/mybatis/study/mapper/UserMapper.xml"/>-->
  </mappers>
```


## 6、XML配置

### 6.1、属性（properties）

- 如果一个属性在不只一个地方进行了配置，那么，MyBatis 将按照下面的顺序来加载：

- 首先读取在 properties 元素体内指定的属性。
- 然后根据 properties 元素中的 resource 属性读取类路径下属性文件，或根据 url 属性指定的路径读取属性文件，并覆盖之前读取过的同名属性。
- 最后读取作为方法参数传递的属性，并覆盖之前读取过的同名属性。
- 因此，通过方法参数传递的属性具有最高优先级，resource/url 属性中指定的配置文件次之，最低优先级的则是 properties 元素中指定的属性。

### 6.2、设置（settings）
https://mybatis.org/mybatis-3/zh/configuration.html

### 6.3、插件（plugins）
MyBatis 允许你在映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

- Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
- ParameterHandler (getParameterObject, setParameters)
- ResultSetHandler (handleResultSets, handleOutputParameters)
- StatementHandler (prepare, parameterize, batch, update, query)

这些类中方法的细节可以通过查看每个方法的签名来发现，或者直接查看 MyBatis 发行包中的源代码。 如果你想做的不仅仅是监控方法的调用，那么你最好相当了解要重写的方法的行为。 因为在试图修改或重写已有方法的行为时，很可能会破坏 MyBatis 的核心模块。 这些都是更底层的类和方法，所以使用插件的时候要特别当心。


通过 MyBatis 提供的强大机制，使用插件是非常简单的，只需实现 Interceptor 接口，并指定想要拦截的方法签名即可。

```
// ExamplePlugin.java
@Intercepts({@Signature(
  type= Executor.class,
  method = "update",
  args = {MappedStatement.class,Object.class})})
public class ExamplePlugin implements Interceptor {
  private Properties properties = new Properties();
  public Object intercept(Invocation invocation) throws Throwable {
    // implement pre processing if need
    Object returnObject = invocation.proceed();
    // implement post processing if need
    return returnObject;
  }
  public void setProperties(Properties properties) {
    this.properties = properties;
  }
}
```

```
<!-- mybatis-config.xml -->
<plugins>
  <plugin interceptor="org.mybatis.example.ExamplePlugin">
    <property name="someProperty" value="100"/>
  </plugin>
</plugins>
```

上面的插件将会拦截在 Executor 实例中所有的 “update” 方法调用， 这里的 Executor 是负责执行底层映射语句的内部对象.

### 6.4、环境配置（environments）

MyBatis 可以配置成适应多种环境，这种机制有助于将 SQL 映射应用于多种数据库之中， 现实情况下有多种理由需要这么做。例如，开发、测试和生产环境需要有不同的配置；或者想在具有相同 Schema 的多个生产数据库中使用相同的 SQL 映射。还有许多类似的使用场景。

不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。

所以，如果你想连接两个数据库，就需要创建两个 SqlSessionFactory 实例，每个数据库对应一个。而如果是三个数据库，就需要三个实例，依此类推，记起来很简单：

每个数据库对应一个 SqlSessionFactory 实例

为了指定创建哪种环境，只要将它作为可选的参数传递给 SqlSessionFactoryBuilder 即可。可以接受环境配置的两个方法签名是：

```
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment);
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment, properties);
```

environments 元素定义了如何配置环境。

```
<environments default="development">
  <environment id="development">
    <transactionManager type="JDBC">
      <property name="..." value="..."/>
    </transactionManager>
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
</environments>
```

注意一些关键点:

- 默认使用的环境 ID（比如：default="development"）。
- 每个 environment 元素定义的环境 ID（比如：id="development"）。
- 事务管理器的配置（比如：type="JDBC"）。
- 数据源的配置（比如：type="POOLED"）。

默认环境和环境 ID 顾名思义。 环境可以随意命名，但务必保证默认的环境 ID 要匹配其中一个环境 ID。

#### 事务管理器（transactionManager）
在 MyBatis 中有两种类型的事务管理器（也就是 type="[JDBC|MANAGED]"）：

- JDBC – 这个配置直接使用了 JDBC 的提交和回滚设施，它依赖从数据源获得的连接来管理事务作用域。
- MANAGED – 这个配置几乎没做什么。它从不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 默认情况下它会关闭连接。然而一些容器并不希望连接被关闭，因此需要将 closeConnection 属性设置为 false 来阻止默认的关闭行为。例如:

```
<transactionManager type="MANAGED">
  <property name="closeConnection" value="false"/>
</transactionManager>
```

#### 数据源（dataSource）
dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源。

大多数 MyBatis 应用程序会按示例中的例子来配置数据源。虽然数据源配置是可选的，但如果要启用延迟加载特性，就必须配置数据源。
有三种内建的数据源类型（也就是 type="[UNPOOLED|POOLED|JNDI]"）：

POOLED– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。 这种处理方式很流行，能使并发 Web 应用快速响应请求。

除了上述提到 UNPOOLED 下的属性外，还有更多属性用来配置 POOLED 的数据源：

driver – 这是 JDBC 驱动的 Java 类全限定名（并不是 JDBC 驱动中可能包含的数据源类）。

url – 这是数据库的 JDBC URL 地址。

username – 登录数据库的用户名。

password – 登录数据库的密码。

defaultTransactionIsolationLevel – 默认的连接事务隔离级别。

defaultNetworkTimeout – 等待数据库操作完成的默认网络超时时间（单位：毫秒）。查看 java.sql.Connection#setNetworkTimeout() 的 API 文档以获取更多信息。

作为可选项，你也可以传递属性给数据库驱动。只需在属性名加上“driver.”前缀即可，例如：
driver.encoding=UTF8

这将通过 DriverManager.getConnection(url, driverProperties) 方法传递值为 UTF8 的 encoding 属性给数据库驱动。

poolMaximumActiveConnections – 在任意时间可存在的活动（正在使用）连接数量，默认值：10

poolMaximumIdleConnections – 任意时间可能存在的空闲连接数。

poolMaximumCheckoutTime – 在被强制返回之前，池中连接被检出（checked out）时间，默认值：20000 毫秒（即 20 秒）

poolTimeToWait – 这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直失败且不打印日志），默认值：20000 毫秒（即 20 秒）。

poolMaximumLocalBadConnectionTolerance – 这是一个关于坏连接容忍度的底层设置， 作用于每一个尝试从缓存池获取连接的线程。 如果这个线程获取到的是一个坏的连接，那么这个数据源允许这个线程尝试重新获取一个新的连接，但是这个重新尝试的次数不应该超过 poolMaximumIdleConnections 与 poolMaximumLocalBadConnectionTolerance 之和。 默认值：3（新增于 3.4.5）

poolPingQuery – 发送到数据库的侦测查询，用来检验连接是否正常工作并准备接受请求。默认是“NO PING QUERY SET”，这会导致多数数据库驱动出错时返回恰当的错误消息。

poolPingEnabled – 是否启用侦测查询。若开启，需要设置 poolPingQuery 属性为一个可执行的 SQL 语句（最好是一个速度非常快的 SQL 语句），默认值：false。

poolPingConnectionsNotUsedFor – 配置 poolPingQuery 的频率。可以被设置为和数据库连接超时时间一样，来避免不必要的侦测，默认值：0（即所有连接每一时刻都被侦测 — 当然仅当 poolPingEnabled 为 true 时适用）。

### 6.5、数据库厂商标识（databaseIdProvider）
MyBatis 可以根据不同的数据库厂商执行不同的语句，这种多厂商的支持是基于映射语句中的 databaseId 属性。 MyBatis 会加载带有匹配当前数据库 databaseId 属性和所有不带 databaseId 属性的语句。 如果同时找到带有 databaseId 和不带 databaseId 的相同语句，则后者会被舍弃。
 为支持多厂商特性，只要像下面这样在 mybatis-config.xml 文件中加入 databaseIdProvider 即可：
```
<databaseIdProvider type="DB_VENDOR" />
```
databaseIdProvider 对应的 DB_VENDOR 实现会将 databaseId 设置为 DatabaseMetaData#getDatabaseProductName() 返回的字符串。 
由于通常情况下这些字符串都非常长，而且相同产品的不同版本会返回不同的值，你可能想通过设置属性别名来使其变短

```
<databaseIdProvider type="DB_VENDOR">
  <property name="SQL Server" value="sqlserver"/>
  <property name="DB2" value="db2"/>
  <property name="Oracle" value="oracle" />
</databaseIdProvider>
```

### 6.6、映射器（mappers）
既然 MyBatis 的行为已经由上述元素配置完了，我们现在就要来定义 SQL 映射语句了。 但首先，我们需要告诉 MyBatis 到哪里去找到这些语句。 在自动查找资源方面，Java 并没有提供一个很好的解决方案，所以最好的办法是直接告诉 MyBatis 到哪里去找映射文件。
 你可以使用相对于类路径的资源引用，或完全限定资源定位符（包括 file:/// 形式的 URL），或类名和包名等。例如：
 
```
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
```

```
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>

```
```
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
```

```
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```