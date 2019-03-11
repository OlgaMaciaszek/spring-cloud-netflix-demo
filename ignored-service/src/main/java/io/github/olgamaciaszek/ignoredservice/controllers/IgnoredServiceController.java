package io.github.olgamaciaszek.ignoredservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Olga Maciaszek-Sharma
 */
@RestController
@RequestMapping("/test")
public class IgnoredServiceController {

	@GetMapping
	public String test() {
		return "Ignored service called";
	}

	@GetMapping("/allowed")
	public String allowed() {
		return "Allowed endpoint called";
	}

}
