package com.personal.scripts.gen.ar_utils;

import java.util.ArrayList;
import java.util.List;

import com.utils.log.Logger;

final class ExtractArchiveWorker {

	private ExtractArchiveWorker() {
	}

	static void work(
			final String sevenZipPathString,
			final String srcPathString,
			final String dstPathString) {

		try {
			Logger.printProgress("extracting archive");

			final List<String> commandPartList = new ArrayList<>();
			commandPartList.add(sevenZipPathString);
			commandPartList.add("x");
			commandPartList.add("-y");
			commandPartList.add(srcPathString);
			commandPartList.add("-o" + dstPathString);

			ArchiveUtils.printCommand(commandPartList);

			final Process process = new ProcessBuilder()
					.command(commandPartList)
					.inheritIO()
					.start();
			process.waitFor();

		} catch (final Exception exc) {
			Logger.printError("failed to extract archive");
			Logger.printException(exc);
		}
	}
}
