package com.personal.scripts.gen.ar_utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.utils.log.Logger;

final class ArchiveUtils {

	private ArchiveUtils() {
	}

	static void printCommand(
			final List<String> commandPartList) {

		Logger.printProgress("executing command:");
		Logger.printLine(StringUtils.join(commandPartList, ' '));
	}
}
