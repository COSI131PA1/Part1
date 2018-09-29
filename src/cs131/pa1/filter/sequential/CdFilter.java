package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.ArrayList;

public class CdFilter extends SequentialFilter{
	private String path;
	
	public CdFilter(String input) {
		this.path = input;
	}
	
	public void process(){
		File f = new File(path);
		if (f.isAbsolute()) {
			System.out.print("Does Not Support Absolute Path");
		} 
		if (!path.isEmpty()) {
		String currentDir = SequentialREPL.currentWorkingDirectory;
		String[] currentDirStrings = currentDir.split("/");
		String[] pathString = path.split("\\s+");
		for (String d: pathString) {
			if (d.equals(".")){
				currentDir = SequentialREPL.currentWorkingDirectory;
			}
			if (d.equals("..")) { 
				currentDir = currentDir.substring(0, currentDirStrings.length - 2);
			} 
			File dir = new File(d);
			for (int i = 0; i<currentDirStrings.length; i++) {
				int finalDir = 1;
				if (currentDirStrings[i].equals(d)) {
					finalDir = i;
					if (dir.isDirectory() || dir.isFile()) {
						currentDir = currentDirStrings[finalDir];
						SequentialREPL.currentWorkingDirectory = currentDir;
					}
				}
			}
		}
		}
	}
	
	protected String processLine(String line) {
		return null;
	}
}
