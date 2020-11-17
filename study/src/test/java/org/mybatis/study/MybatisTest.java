package org.mybatis.study;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.study.entity.User;
import org.mybatis.study.mapper.UserMapper;

import java.io.IOException;
import java.io.Reader;

/**
 * mybatis 测试
 *
 * @Author ggq
 * @Date 2020/11/17 17:01
 */
@Slf4j
public class MybatisTest {
  SqlSession session;

  @Before
  public void before() {
    String resource = "mybatis-config.xml";
    Reader reader;
    try {
      //将XML配置文件构建为Configuration配置类
      reader = Resources.getResourceAsReader(resource);
      // 通过加载配置文件流构建一个SqlSessionFactory  DefaultSqlSessionFactory
      SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
      // 数据源 执行器  DefaultSqlSession
      session = sqlMapper.openSession();
      log.info("开启session");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @After
  public void after() {
    session.commit();
    session.close();
    log.info("关闭session");
  }

  /**
   * mybatis3 之前
   */
  @Test
  public void test1() {
    User user = (User) session.selectOne("org.mybatis.study.mapper.UserMapper.selectById", 1L);
    log.info(user.getUserName());
  }

  /**
   * mybatis3及以后 基于接口方式
   */
  @Test
  public void test2() {
    UserMapper mapper = session.getMapper(UserMapper.class);
    User user = mapper.selectById(1L);
    log.info(user.getUserName());
  }


  /**
   * mybatis3及以后 基于注解方式
   *
   * 基于注解需要 注释掉 xml文件
   * <!--  <select id="selectById" resultMap="result">
   *     select id,user_name,create_time from t_user where id=${param1}
   *   </select>-->
   */
  @Test
  public void test3() {
    UserMapper mapper = session.getMapper(UserMapper.class);
    User user = mapper.selectById(1L);
    log.info(user.getUserName());
  }
}
