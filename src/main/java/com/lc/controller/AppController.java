package com.lc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestController // @RestController 是 @Controller 和 @ResponseBody 两个注解的结合体
@Controller
@RequestMapping("/api")
public class AppController {

	@RequestMapping("/app")
	@ResponseBody
	public String app() {
		return "Hello World! JustDDNS app!";
	}
}
