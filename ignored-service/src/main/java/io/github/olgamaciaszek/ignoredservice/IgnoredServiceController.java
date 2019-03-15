package io.github.olgamaciaszek.ignoredservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Olga Maciaszek-Sharma
 */
@RestController
@RequestMapping("/test")
class IgnoredServiceController {

	@GetMapping
	String test() {
		return "Ignored service called";
	}

	@GetMapping("/allowed")
	String allowed() {
		return "Allowed endpoint called";
	}

}

@RestController
@RequestMapping("/ignored/test")
class IgnoredServiceAllowedController {

	@GetMapping("/allowed")
	String allowed() {
		return "Allowed endpoint called";
	}
}
