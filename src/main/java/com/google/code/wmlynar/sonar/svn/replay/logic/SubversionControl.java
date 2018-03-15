package com.google.code.wmlynar.sonar.svn.replay.logic;

import java.io.File;
import java.io.IOException;

import com.google.code.wmlynar.sonar.svn.replay.utils.CommandExecutor;

/**
 * Collects all methods that checkout code and run sonar.
 * 
 * @author Wojciech Mlynarczyk (wojciech.mlynarczyk@gmail.com)
 * 
 */
public class SubversionControl {

	/**
	 * Checkouts code from repository given the date and repository address into
	 * given folder.
	 */
	public static void checkoutCode(File directory, String dateString,
			String repository) throws IOException, InterruptedException {
		CommandExecutor.executeCommand(directory, "svn", "co", repository, ".",
				"-r", "{" + dateString + "}");
	}

	/**
	 * Updates code from repository for given date into given folder.
	 */
	public static void updateCode(File directory, String dateString)
			throws IOException, InterruptedException {
		CommandExecutor.executeCommand(directory, "svn", "up", "-r", "{"
				+ dateString + "}");
	}

}
