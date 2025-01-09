package com.spring.jpastudy.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 예외가 발생했을 때 어떤 일이 발생하는지 보여주기 위해 사용되는 컨트롤러
 */
@Controller
public class CrashController {

	@GetMapping("/oups")
	public String triggerException() {
		throw new RuntimeException(
			"Expected: controller used to showcase what " + "happens when an exception is thrown");
	}
}
