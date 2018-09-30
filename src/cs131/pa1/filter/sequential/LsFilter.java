package cs131.pa1.filter.sequential;

import java.io.File;

public class LsFilter extends SequentialFilter {
	private boolean processDone = false;
	
	public String toString() {
		return "ls";
	}
	
	public void process() {
		String currentDirectory = SequentialREPL.currentWorkingDirectory;
		File currentDir = new File(currentDirectory);
		File[] allFiles = currentDir.listFiles();
		for (File f: allFiles) {
			if (f.isDirectory() || f.isFile()) {
				if (f.getName() != null && !f.getName().equals(".DS_Store")) {
					this.output.add(f.getName());
				}
			}
		}
		processDone = true;
	}
	
	public boolean isDone(){
		return processDone;
	}

	@Override
	protected String processLine(String line) {
		return null;
	}
}
