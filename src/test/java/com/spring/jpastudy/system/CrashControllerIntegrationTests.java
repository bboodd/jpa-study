package com.spring.jpastudy.system;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"server.error.include-message=always"})
class CrashControllerIntegrationTests {

	@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
	static class TestConfiguration {

	}

	@Value(value = "${local.server.port}")
	private int port;

	@Autowired
	private TestRestTemplate rest;

	@Test
	void testTriggerExceptionJson() {
		ResponseEntity<Map<String, Object>> resp = rest.exchange(
			RequestEntity.get("http://localhost:" + port + "/oups").build(),
			new ParameterizedTypeReference<Map<String, Object>>() {
			});
		assertThat(resp).isNotNull();
		assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(resp.getBody()).containsKey("timestamp");
		assertThat(resp.getBody()).containsKey("status");
		assertThat(resp.getBody()).containsKey("error");
		assertThat(resp.getBody()).containsEntry("message",
			"Expected: controller used to showcase what happens when an exception is thrown");
		assertThat(resp.getBody()).containsEntry("path", "/oups");
	}

	@Test
	void testTriggerExceptionHtml() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(List.of(MediaType.TEXT_HTML));
		ResponseEntity<String> resp = rest.exchange("http://localhost:" + port + "/oups", HttpMethod.GET,
			new HttpEntity<>(headers), String.class);
		assertThat(resp).isNotNull();
		assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(resp.getBody()).isNotNull();
		// html
		assertThat(resp.getBody()).containsSubsequence("<body>", "<h2>", "Something happened...", "</h2>", "<p>",
			"Expected:", "controller", "used", "to", "showcase", "what", "happens", "when", "an", "exception", "is",
			"thrown", "</p>", "</body>");

		assertThat(resp.getBody()).doesNotContain("Whitelabel Error Page",
			"This application has no explicit mapping for");
	}
}
