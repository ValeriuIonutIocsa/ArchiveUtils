package com.personal.scripts.gen.ar_utils;

import java.util.ArrayList;
import java.util.List;

import com.utils.io.PathUtils;
import com.utils.io.file_deleters.FactoryFileDeleter;
import com.utils.log.Logger;

final class CreatePasswordProtectedArchiveWorker {

	private CreatePasswordProtectedArchiveWorker() {
	}

	static void work(
			final String sevenZipPathString,
			final String srcPathString,
			final String dstPathString,
			final String compressionLevel,
			final String password) {

		String tmpPathString = null;
		try {
			Logger.printProgress("creating password protected archive");

			tmpPathString = PathUtils.computePath(PathUtils.computeParentPath(srcPathString),
					"_" + PathUtils.computeFileName(dstPathString));
			createTmpArchive(sevenZipPathString,
					srcPathString, tmpPathString, compressionLevel);

			final List<String> commandPartList = new ArrayList<>();
			commandPartList.add(sevenZipPathString);
			commandPartList.add("a");
			commandPartList.add("-mx=0");
			commandPartList.add("-p" + password);
			commandPartList.add(dstPathString);
			commandPartList.add(tmpPathString);

			ArchiveUtils.printCommand(commandPartList);

			final Process process = new ProcessBuilder()
					.command(commandPartList)
					.inheritIO()
					.start();
			process.waitFor();

		} catch (final Exception exc) {
			Logger.printError("failed to create password protected archive");
			Logger.printException(exc);

		} finally {
			FactoryFileDeleter.getInstance().deleteFile(tmpPathString, true, true);
		}
	}

	private static void createTmpArchive(
			final String sevenZipPathString,
			final String srcPathString,
			final String tmpPathString,
			final String compressionLevel) throws Exception {

		final List<String> commandPartList = new ArrayList<>();
		commandPartList.add(sevenZipPathString);
		commandPartList.add("a");
		commandPartList.add("-mx=" + compressionLevel);
		commandPartList.add(tmpPathString);
		commandPartList.add(srcPathString);

		ArchiveUtils.printCommand(commandPartList);

		final Process process = new ProcessBuilder()
				.command(commandPartList)
				.inheritIO()
				.start();
		process.waitFor();
	}
}
