package com.personal.scripts.gen.ar_utils;

import java.util.ArrayList;
import java.util.List;

import com.utils.log.Logger;

final class UpdateArchiveWorker {

	private UpdateArchiveWorker() {
	}

	static void work(
			final String sevenZipPathString,
			final String srcPathString,
			final String dstPathString) {

		try {
			Logger.printProgress("updating archive");

			final List<String> commandPartList = new ArrayList<>();
			commandPartList.add(sevenZipPathString);
			commandPartList.add("u");
			commandPartList.add(dstPathString);
			commandPartList.add(srcPathString);

			ArchiveUtils.printCommand(commandPartList);

			final Process process = new ProcessBuilder()
					.command(commandPartList)
					.inheritIO()
					.start();
			process.waitFor();

		} catch (final Exception exc) {
			Logger.printError("failed to update archive");
			Logger.printException(exc);
		}
	}
}
