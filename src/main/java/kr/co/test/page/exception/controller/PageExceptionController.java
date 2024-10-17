package kr.co.test.page.exception.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import common.LogDeclare;

@Controller
@RequestMapping("/page_exception")
public class PageExceptionController extends LogDeclare {

	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public String handleRequest1() throws Exception {
		String msg = String.format("Test exception from class: %s",
                this.getClass().getSimpleName());

		throw new Exception(msg);
	}
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public String handleRequest2() throws Exception {
		String msg = String.format("Test exception from class: %s",
				this.getClass().getSimpleName());
		
		throw new RuntimeException(msg);
	}
	
}
