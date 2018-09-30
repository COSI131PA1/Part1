package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.ArrayList;

import cs131.pa1.filter.Message;

public class CdFilter extends SequentialFilter{
	private String path;
	
	public CdFilter(String input) {
		this.path = input;
	}
	
	public String toString() {
		return "cd";
	}
	
	
	public void process(){
		String currentDir = SequentialREPL.currentWorkingDirectory;
		String[] currentDirStrings = currentDir.split("/");
		String[] pathString = path.split("\\s+");
		String finalDir = pathString[pathString.length-1];
		if (finalDir.equals("cd")) {
			System.out.printf(Message.REQUIRES_PARAMETER.toString(), "cd");
		} else {
			File f = new File(finalDir);
			if (f.isDirectory() && f.exists()) {
				if (finalDir.equals(".")) {
					currentDir = SequentialREPL.currentWorkingDirectory;
				} else if (finalDir.equals("..")){
					currentDir = "";
					for (int m = 0; m < currentDirStrings.length-1; m++) {
						if (m == 0) {
							currentDir = currentDir + currentDirStrings[m];
						} else {
							currentDir = currentDir + "/" + currentDirStrings[m];
						}
					}
				} else {
					File currentFiles = new File(currentDir);
					if (f.isDirectory()) {
						File[] allFiles = currentFiles.listFiles();
						for (File n: allFiles) {
							if (finalDir.equals(n.getName())) {
								currentDir = n.toString();
							}
						}
					}
				}
				SequentialREPL.currentWorkingDirectory = currentDir;
			} else {
				System.out.printf(Message.DIRECTORY_NOT_FOUND.toString(), "cd " + f.getName());
			} 
		}
	}
	
	protected String processLine(String line) {
		return null;
	}
}
