package com.example.demo;

import com.example.demo.pojo.User;
import com.example.demo.pojo.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@MapperScan("com.example.demo.pojo.mapper")
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private UserMapper userMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testSql(){
		String sql = "SELECT id,user_name,pass_word,nick_name,email,reg_time,mark FROM USER";
		List<User> userList =(List<User>) jdbcTemplate.query(sql, new RowMapper<User>(){
			@Override
			public User mapRow(ResultSet resultSet, int i) throws SQLException {
				User user = new User();
				user.setMark(resultSet.getDouble("mark"));
				user.setNickName(resultSet.getString("nick_Name"));
				user.setRegTime(resultSet.getDate("reg_Time"));
				user.setEmail(resultSet.getString("email"));
				user.setId(resultSet.getInt("id"));
				user.setPassWord(resultSet.getString("pass_Word"));
				user.setUserName(resultSet.getString("user_Name"));
				return user;
			}
		});
		for(User user:userList){
			System.out.println(user.toString());
		}
	}

	@Test
	public void testMybatisPlub(){
		System.out.println(("----- selectAll method test ------"));
		List<User> userList = userMapper.selectList(null);
		Assert.assertEquals(3, userList.size());
		userList.forEach(System.out::println);
	}

}
