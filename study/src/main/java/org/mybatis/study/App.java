/**
 * Copyright ${license.git.copyrightYears} the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.study;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.study.entity.User;

import java.io.IOException;
import java.io.Reader;

import org.mybatis.study.mapper.UserMapper;

/**
 * mybatis 自我学习模块
 *
 * @Author ggq
 * @Date 2020/11/16 13:45
 */
public class App {

  public static void main(String[] args) {
    String resource = "mybatis-config.xml";
    Reader reader;
    try {
      //将XML配置文件构建为Configuration配置类
      reader = Resources.getResourceAsReader(resource);
      // 通过加载配置文件流构建一个SqlSessionFactory  DefaultSqlSessionFactory
      SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
      // 数据源 执行器  DefaultSqlSession
      SqlSession session = sqlMapper.openSession();
      try {
        // 方式一、 mybatis3 之前方式(ibatis)  namespace + selectById
//        User user = (User) session.selectOne("org.mybatis.study.mapper.UserMapper.selectById", 1L );

        // 方式二、 mybatis3 之后 接口绑定方式
        // (resource 下面的路径要和 接口包 路径一致 org.mybatis.study.mapper 这个路径不能直接使用.要一个文件夹一个文件夹的创建)
        UserMapper mapper = session.getMapper(UserMapper.class);
        System.out.println(mapper.getClass());
        User user = mapper.selectById(1L);
        session.commit();
        System.out.println(user.getUserName());
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        session.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
