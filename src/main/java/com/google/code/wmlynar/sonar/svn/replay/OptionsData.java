package com.google.code.wmlynar.sonar.svn.replay;

import org.kohsuke.args4j.Option;

/**
 * All command line options.
 * 
 * @author Wojciech Mlynarczyk (wojciech.mlynarczyk@gmail.com)
 *
 */
public class OptionsData {

	@Option(name = "--repo", required = true, usage = "Repository address")
	public String repo;

	@Option(name = "--startDate", required = true, usage = "Start date YYYY-MM-DD")
	public String startDate;

	@Option(name = "--endDate", usage = "End date YYYY-MM-DD")
	public String endDate;

	@Option(name = "--intervalDays", usage = "Interval in days.")
	public int intervalDays = 5;

	@Option(name = "--tmpDir", usage = "Temporary folder name")
	public String tmpDirName;

	@Option(name = "--dontDeleteTmpDir", usage = "Flag that means do not delete tmp directory")
	public boolean dontDeleteTemp;

	@Option(name = "--sonarVersion", usage = "Version of sonar plugin")
	public String sonarVersion;

	@Option(name = "--sonarDefinitions", usage = "Sonar definitions")
	public String sonarDefinitions;

	@Option(name = "--buildDefinitions", usage = "Build Definitions")
	public String buildDefinitions;

	@Option(name = "--skipTestsWhenBuilding", usage = "Skip tests when doing mvn install")
	public boolean skipTestsWhenBuilding;
	
	@Option(name = "--dontClean", usage = "Dont do mvn clean before mvn install")
	public boolean dontClean;
}