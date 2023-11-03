package com.personal.scripts.gen.ar_utils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.utils.cli.CliUtils;
import com.utils.io.IoUtils;
import com.utils.io.PathUtils;
import com.utils.log.Logger;

final class AppStartArchiveUtils {

	private AppStartArchiveUtils() {
	}

	public static void main(
			final String[] args) {

		final Instant start = Instant.now();

		Logger.printProgress("starting ArchiveUtils");

		final Map<String, String> cliArgsByNameMap = new HashMap<>();
		CliUtils.fillCliArgsByNameMap(args, cliArgsByNameMap);

		final String sevenZipPathString = cliArgsByNameMap.get("sevenZipPath");
		if (!IoUtils.fileExists(sevenZipPathString)) {
			Logger.printError("7zip executable path is missing or blank");

		} else {
			String srcPathString = cliArgsByNameMap.get("srcPath");
			boolean filesOnly = false;
			if (srcPathString != null && srcPathString.endsWith("*")) {

				srcPathString = srcPathString.substring(0, srcPathString.length() - 1);
				filesOnly = true;
			}
			srcPathString = PathUtils.computeNormalizedPath(null, srcPathString);
			if (!IoUtils.fileExists(srcPathString)) {
				Logger.printError("source path is missing, blank, or file does not exist");

			} else {
				String dstPathString = cliArgsByNameMap.get("dstPath");
				dstPathString = PathUtils.computeNormalizedPath(null, dstPathString);

				final String password = cliArgsByNameMap.get("pw");

				final String mode = cliArgsByNameMap.get("mode");
				if ("archive".equals(mode)) {

					if (StringUtils.isBlank(dstPathString)) {
						dstPathString = srcPathString + ".7z";
					}

					final String compressionLevel = cliArgsByNameMap.get("compressionLevel");
					if (StringUtils.isBlank(password)) {
						CreateArchiveWorker.work(sevenZipPathString,
								srcPathString, dstPathString, compressionLevel, filesOnly);
					} else {
						CreatePasswordProtectedArchiveWorker.work(sevenZipPathString,
								srcPathString, dstPathString, compressionLevel, filesOnly, password);
					}

				} else if ("extract".equals(mode)) {

					if (StringUtils.isBlank(dstPathString)) {
						dstPathString = PathUtils.computeParentPath(srcPathString);
					}

					if (StringUtils.isBlank(password)) {
						ExtractArchiveWorker.work(sevenZipPathString,
								srcPathString, dstPathString);
					} else {
						ExtractPasswordProtectedArchiveWorker.work(sevenZipPathString,
								srcPathString, dstPathString, password);
					}

				} else if ("update".equals(mode)) {

					if (StringUtils.isBlank(dstPathString)) {
						dstPathString = srcPathString + ".7z";
					}

					UpdateArchiveWorker.work(sevenZipPathString,
							srcPathString, dstPathString);

				} else {
					Logger.printError("unsupported mode: " + mode);
				}
			}
		}
		Logger.printFinishMessage(start);
	}
}
