package io.github.olgamaciaszek.cardservice.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Olga Maciaszek-Sharma
 */
@RestController
@RequestMapping("/application")
class CardApplicationController {

	private final CardApplicationService cardApplicationService;

	public CardApplicationController(CardApplicationService cardApplicationService) {
		this.cardApplicationService = cardApplicationService;
	}

	@PostMapping
	public ResponseEntity<ApplicationResult> apply(@RequestBody CardApplicationDto applicationDTO,
			UriComponentsBuilder uriComponentsBuilder) {
		CardApplication cardApplication = cardApplicationService
				.registerApplication(applicationDTO);
		UriComponents uriComponents = uriComponentsBuilder.path("/applications/{uuid}")
				.buildAndExpand(cardApplication.getUuid());
		return ResponseEntity.created(uriComponents.toUri())
				.body(cardApplication.getApplicationResult());
	}
}
