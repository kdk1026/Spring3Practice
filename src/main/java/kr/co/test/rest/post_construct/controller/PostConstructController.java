package kr.co.test.rest.post_construct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.LogDeclare;
import kr.co.test.common.spring.component.InitializerComponent;

@Controller
@RequestMapping("/post_cons")
public class PostConstructController extends LogDeclare {

	@Autowired
	InitializerComponent initializerComponent;
	
	@ResponseBody
	@RequestMapping("/get/{idx}")
	public String test(@PathVariable int idx) {
		return initializerComponent.getsNumber(idx);
	}
	
}
