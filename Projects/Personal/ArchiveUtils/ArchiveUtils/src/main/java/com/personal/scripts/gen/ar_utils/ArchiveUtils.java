package com.personal.scripts.gen.ar_utils;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.utils.log.Logger;

final class ArchiveUtils {

	private ArchiveUtils() {
	}

	static String createProcessedSrcPathString(
            final String srcPathString,
            final boolean filesOnly) {

		final String processedSrcPathString;
		if (filesOnly) {
			processedSrcPathString = srcPathString + File.separator + "*";
		} else {
			processedSrcPathString = srcPathString;
		}
		return processedSrcPathString;
	}

	static void printCommand(
			final List<String> commandPartList) {

		Logger.printProgress("executing command:");
		Logger.printLine(StringUtils.join(commandPartList, ' '));
	}
}
