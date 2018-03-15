package com.google.code.wmlynar.sonar.svn.replay.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.google.code.wmlynar.sonar.svn.replay.utils.CommandExecutor;

/**
 * All operations for controlling sonar.
 * 
 * @author Wojciech Mlynarczyk (wojciech.mlynarczyk@gmail.com)
 * 
 */
public class MavenControl {

	/**
	 * Compiles code at given folder.
	 */
	public static void compileCode(File folder, String buildDefinitions, boolean skipTestsWhenBuilding, boolean dontClean) throws IOException,
			InterruptedException {
		ArrayList<String> params = new ArrayList<String>();
		params.add("mvn");
		params.add("--batch-mode");
		if(!skipTestsWhenBuilding) {
			params.add("-Dmaven.test.skip=true");
		}
		if (buildDefinitions != null) {
			StringTokenizer st = new StringTokenizer(buildDefinitions, " ");
			while(st.hasMoreElements()) {
				params.add(st.nextToken());
			}
		}
		if(!dontClean) {
			params.add("clean");
		}
		params.add("install");
		CommandExecutor.executeCommand(folder,
				params.toArray(new String[params.size()]));
	}
	
	/**
	 * Runs Sonar at given folder given the date and options data structure.
	 */
	public static void runSonar(File folder, String dateString,
			String sonarDefinitions, String sonarVersion) throws IOException, InterruptedException {
		ArrayList<String> params = new ArrayList<String>();
		params.add("mvn");
		params.add("--batch-mode");
		params.add("-Dsonar.projectDate=" + dateString);
		if (sonarDefinitions != null) {
			StringTokenizer st = new StringTokenizer(sonarDefinitions, " ");
			while(st.hasMoreElements()) {
				params.add(st.nextToken());
			}
		}
		if (sonarVersion != null) {
			params.add("org.codehaus.mojo:sonar-maven-plugin:"
					+ sonarVersion + ":sonar");
		} else {
			params.add("sonar:sonar");
		}
		CommandExecutor.executeCommand(folder,
				params.toArray(new String[params.size()]));
	}


}
