package kr.co.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.spring.resolver.ParamCollector;
import kr.co.test.junit.db.service.MyBatisService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/application-config.xml", 
		"classpath:spring/security-config.xml"
})
public class MyBatisCrudTest {

	@Autowired
	private MyBatisService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Test
	public void test() {
		// SELECT All
		userService.listUser();

		ParamCollector paramCollector = new ParamCollector();

		// INERT
		paramCollector.put("username", "test");
		paramCollector.put("password", passwordEncoder.encode("test1234"));
		userService.addUser(paramCollector);

		// SELECT ONE
		paramCollector.getMap().clear();
		paramCollector.put("id", 2);
		userService.getUser(paramCollector);

		// UPDATE
		paramCollector.getMap().clear();
		paramCollector.put("id", 2);
		paramCollector.put("username", "test2");
		paramCollector.put("password", null);
		userService.modifyUser(paramCollector);

		// SELECT ONE
		paramCollector.getMap().clear();
		paramCollector.put("id", 2);
		userService.getUser(paramCollector);

		// REMOVE
		paramCollector.getMap().clear();
		paramCollector.put("id", 2);
		userService.removeUser(paramCollector);

		// SELECT All
		userService.listUser();
	}
}
