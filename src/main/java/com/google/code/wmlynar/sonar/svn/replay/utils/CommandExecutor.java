package com.google.code.wmlynar.sonar.svn.replay.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Executes commands.
 * 
 * @author Wojciech Mlynarczyk (wojciech.mlynarczyk@gmail.com)
 * 
 */
public class CommandExecutor {

	/**
	 * Executes command in a given directory.
	 */
	public static void executeCommand(File directory, String... params)
			throws IOException, InterruptedException {
		ProcessBuilder b = new ProcessBuilder().command(params).directory(
				directory);
		Process p = b.start();
		new Thread(new StreamRedirector(p.getErrorStream(), true)).start();
		new Thread(new StreamRedirector(p.getInputStream(), false)).start();
		p.waitFor();
	}

	/**
	 * Redirects stream to standard output and error output.
	 */
	private static class StreamRedirector implements Runnable {
		private InputStream in;
		private boolean error;
		private byte[] buffer = new byte[1024];

		public StreamRedirector(InputStream in, boolean error) {
			this.in = in;
			this.error = error;
		}

		public void run() {
			try {
				int read;
				while ((read = in.read(buffer)) != -1) {
					if (error) {
						System.err.print(new String(buffer, 0, read));
					} else {
						System.out.print(new String(buffer, 0, read));
					}
				}
			} catch (IOException e) {
				// System.err.println(e.toString());
				e.printStackTrace();
			}
		}
	}

}
