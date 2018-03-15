package com.google.code.wmlynar.sonar.svn.replay.logic;

import java.io.File;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.kohsuke.args4j.CmdLineParser;

import com.google.code.wmlynar.sonar.svn.replay.OptionsData;

/**
 * Embeds all parsing logic.
 * 
 * @author Wojciech Mlynarczyk (wojciech.mlynarczyk@gmail.com)
 * 
 */
public class ParsingControl {

	/**
	 * Parses argument array into Options structure.
	 */
	public static OptionsData parseArgs(String[] args) {
		OptionsData options = new OptionsData();
		CmdLineParser parser = new CmdLineParser(options);
		try {
			parser.parseArgument(args);
		} catch (Exception e) {
			System.out.print("Usage: sonar-svn-replay ");
			parser.printSingleLineUsage(System.out);
			System.out.println();
			parser.printUsage(System.out);
			System.out.println();
			System.out.println("Error: " + e.getMessage());
			System.exit(1);
		}
		return options;
	}

	/**
	 * Parses start date from options.
	 */
	public static DateTime parseStartDate(OptionsData options,
			DateTimeFormatter dateFormatter) {
		return dateFormatter.parseDateTime(options.startDate);
	}

	/**
	 * Parses end date from options.
	 */
	public static DateTime parseEndDate(OptionsData options,
			DateTimeFormatter dateFormatter) {
		if (options.endDate == null) {
			return new DateTime();
		} else {
			return dateFormatter.parseDateTime(options.endDate);
		}
	}

	/**
	 * Creates temporary directory from options.
	 */
	public static File createTmpDirectory(OptionsData options) {
		String tmpDirName;

		if (options.tmpDirName == null) {
			tmpDirName = System.getProperty("java.io.tmpdir");
		} else {
			tmpDirName = options.tmpDirName;
		}

		File tmpDir = new File(new File(tmpDirName), "sonar-svn-replay");

		if (!options.dontDeleteTemp) {
			try {
				deleteDir(tmpDir);
			} catch (Exception e) {
			}
		}

		tmpDir.mkdirs();

		return tmpDir;
	}

	/**
	 * Deletes all files and subdirectories under dir. Returns true if all
	 * deletions were successful. If a deletion fails, the method stops
	 * attempting to delete and returns false.
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

}