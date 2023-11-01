package com.personal.scripts.gen.ar_utils;

import java.util.ArrayList;
import java.util.List;

import com.utils.io.IoUtils;
import com.utils.io.PathUtils;
import com.utils.io.file_deleters.FactoryFileDeleter;
import com.utils.log.Logger;

final class ExtractPasswordProtectedArchiveWorker {

	private ExtractPasswordProtectedArchiveWorker() {
	}

	static void work(
			final String sevenZipPathString,
			final String srcPathString,
			final String dstPathString,
			final String password) {

		String tmpPathString = null;
		try {
			Logger.printProgress("extracting password protected archive");

			extractTmpArchive(sevenZipPathString, srcPathString, dstPathString, password);

			tmpPathString = PathUtils.computePath(PathUtils.computeParentPath(srcPathString),
					"_" + PathUtils.computeFileName(srcPathString));
			if (!IoUtils.fileExists(tmpPathString)) {
				Logger.printError("tmp archive does not exist:" + System.lineSeparator() + tmpPathString);

			} else {
				final List<String> commandPartList = new ArrayList<>();
				commandPartList.add(sevenZipPathString);
				commandPartList.add("x");
				commandPartList.add("-y");
				commandPartList.add(tmpPathString);
				commandPartList.add("-o" + dstPathString);

				ArchiveUtils.printCommand(commandPartList);

				final Process process = new ProcessBuilder()
						.command(commandPartList)
						.inheritIO()
						.start();
				process.waitFor();
			}

		} catch (final Exception exc) {
			Logger.printError("failed to extract password protected archive");
			Logger.printException(exc);

		} finally {
			FactoryFileDeleter.getInstance().deleteFile(tmpPathString, true, true);
		}
	}

	private static void extractTmpArchive(
			final String sevenZipPathString,
			final String srcPathString,
			final String dstPathString,
			final String password) throws Exception {

		final List<String> commandPartList = new ArrayList<>();
		commandPartList.add(sevenZipPathString);
		commandPartList.add("x");
		commandPartList.add("-p" + password);
		commandPartList.add("-y");
		commandPartList.add(srcPathString);
		commandPartList.add("-o" + dstPathString);

		ArchiveUtils.printCommand(commandPartList);

		final Process process = new ProcessBuilder()
				.command(commandPartList)
				.inheritIO()
				.start();
		process.waitFor();
	}
}
