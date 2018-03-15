package com.google.code.wmlynar.sonar.svn.replay;

import java.io.File;
import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kohsuke.args4j.CmdLineException;

import com.google.code.wmlynar.sonar.svn.replay.logic.ParsingControl;
import com.google.code.wmlynar.sonar.svn.replay.logic.MavenControl;
import com.google.code.wmlynar.sonar.svn.replay.logic.SubversionControl;

/**
 * Class with main function.
 * 
 * @author Wojciech Mlynarczyk (wojciech.mlynarczyk@gmail.com)
 *
 */
public class Main extends ParsingControl {

	static DateTimeFormatter dateFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");

	public static void main(String[] args) throws CmdLineException,
			IOException, InterruptedException {

		// parse the arguments structure
		OptionsData options = ParsingControl.parseArgs(args);

		// parse dates
		DateTime startDate = ParsingControl.parseStartDate(options,
				dateFormatter);
		DateTime endDate = ParsingControl.parseEndDate(options, dateFormatter);
		
		// parse folder
		File tmpDirecory = ParsingControl.createTmpDirectory(options);

		// initialize the date
		DateTime date = startDate;

		// checkout code
		SubversionControl.checkoutCode(tmpDirecory, dateFormatter.print(date),
				options.repo);

		// loop between the dates
		while (date.isBefore(endDate)) {

			// format date string
			String dateString = dateFormatter.print(date);

			// update the code
			SubversionControl.updateCode(tmpDirecory, dateString);

			// run sonar
			MavenControl.compileCode(tmpDirecory, options.buildDefinitions, options.skipTestsWhenBuilding, options.dontClean);
			MavenControl.runSonar(tmpDirecory, dateString, options.sonarDefinitions, options.sonarVersion);

			// move the date
			date = date.plusDays(options.intervalDays);
		}
	}

}
