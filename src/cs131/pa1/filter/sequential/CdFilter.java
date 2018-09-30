package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.ArrayList;

public class CdFilter extends SequentialFilter{
	private String path;
	
	public CdFilter(String input) {
		this.path = input;
	}
	
	public String toString() {
		return "cd";
	}
	
	public void process(){
		File f = new File(path);
		if (f.isAbsolute()) {
			System.out.print("Does Not Support Absolute Path");
		} 
		String currentDir = SequentialREPL.currentWorkingDirectory;
		String[] currentDirStrings = currentDir.split("/");
		String[] pathString = path.split("\\s+");
		String finalDir = pathString[pathString.length-1];
		if (finalDir.equals(".")){
			currentDir = SequentialREPL.currentWorkingDirectory;
		} else if (finalDir.equals("..")) { 
			currentDir = "";
			for (int m = 0; m < currentDirStrings.length-1; m++) {
				if (m == 0) {
					currentDir = currentDir + currentDirStrings[m];
				} else {
					currentDir = currentDir + "/" + currentDirStrings[m];
				}
			}
		} else {
			if (!finalDir.isEmpty()) {
				File currentFile = new File(currentDir);
				File[] allFiles = currentFile.listFiles();
				for (File n: allFiles) {
					if (finalDir.equals(n.getName())) {
//						System.out.println("equals: " + n);
						currentDir = n.toString();
						SequentialREPL.currentWorkingDirectory = n.toString();
					}
				}
			}
		}
	}
	
	protected String processLine(String line) {
		return null;
	}
}
