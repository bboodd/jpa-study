package com.spring.jpastudy.system;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CrashControllerTests {

	final CrashController testee = new CrashController();

	@Test
	void testTriggerException() {
		assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> testee.triggerException())
			.withMessageContaining("Expected: controller used to showcase what happens when an exception is thrown");
	}
}
