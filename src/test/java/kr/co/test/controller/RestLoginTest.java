package kr.co.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/application-config.xml", 
		"classpath:spring/security-config.xml",
		"file:src/main/webapp/WEB-INF/config/mvc-config.xml"
})
@WebAppConfiguration
public class RestLoginTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mvc;
	
	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void test() throws Exception {
		MvcResult result = this.mvc.perform(
				post("/cache/members/cache")
				)
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		System.out.println(result);
	}
	
}
