package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.ArrayList;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class CdFilter extends SequentialFilter{
	private String path;
	private String currentDir;
	
	
	public CdFilter(String input) {
		this.path = input;
		this.currentDir = SequentialREPL.currentWorkingDirectory;
	}
	
	public String toString() {
		return "cd";
	}
	
	public void process(){
		String[] pathString = path.split("\\s+");
		String finalDir = pathString[pathString.length-1].trim();
		if (finalDir.equals("cd")) {
			System.out.printf(Message.REQUIRES_PARAMETER.toString(), "cd");
		} else {
				if (finalDir.equals(".")) {
					return;
				} 
				if (finalDir.equals("..")){
					int sIndex = currentDir.lastIndexOf(Filter.FILE_SEPARATOR);	
					if ((sIndex == 1) || (sIndex==2)) {  
				    	System.out.printf(Message.INVALID_PARAMETER.toString(), "..");
				     }
				    SequentialREPL.currentWorkingDirectory = currentDir.substring(0, sIndex);
				} else {
					String changeDir = currentDir + Filter.FILE_SEPARATOR + finalDir;
					File f = new File(changeDir);
					if (f.isDirectory() && f.exists()) {
						SequentialREPL.currentWorkingDirectory = changeDir;
					} else {
						System.out.printf(Message.DIRECTORY_NOT_FOUND.toString(), "cd " + f.getName());
					}
			} 
		}
	}
	
	protected String processLine(String line) {
		return null;
	}
}
