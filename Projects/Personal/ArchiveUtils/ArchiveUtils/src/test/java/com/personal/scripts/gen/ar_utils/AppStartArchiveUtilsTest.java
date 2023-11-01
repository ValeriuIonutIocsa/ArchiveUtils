package com.personal.scripts.gen.ar_utils;

import org.junit.jupiter.api.Test;

class AppStartArchiveUtilsTest {

	@Test
	void testMain() {

		final String[] args;
		final int input = Integer.parseInt("1");
		if (input == 1) {
			args = new String[] {};
		} else {
			throw new RuntimeException();
		}

		AppStartArchiveUtils.main(args);
	}
}
