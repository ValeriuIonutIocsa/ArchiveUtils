package com.personal.scripts.gen.ar_utils;

import java.util.ArrayList;
import java.util.List;

import com.utils.log.Logger;

final class CreateArchiveWorker {

	private CreateArchiveWorker() {
	}

	static void work(
			final String sevenZipPathString,
			final String srcPathString,
			final String dstPathString,
			final String compressionLevel,
			final boolean filesOnly) {

		try {
			Logger.printProgress("creating archive");

			final String processedSrcPathString =
					ArchiveUtils.createProcessedSrcPathString(srcPathString, filesOnly);

			final List<String> commandPartList = new ArrayList<>();
			commandPartList.add(sevenZipPathString);
			commandPartList.add("a");
			commandPartList.add("-mx=" + compressionLevel);
			commandPartList.add(dstPathString);
			commandPartList.add(processedSrcPathString);

			ArchiveUtils.printCommand(commandPartList);

			final Process process = new ProcessBuilder()
					.command(commandPartList)
					.inheritIO()
					.start();
			process.waitFor();

		} catch (final Exception exc) {
			Logger.printError("failed to create archive");
			Logger.printException(exc);
		}
	}
}
