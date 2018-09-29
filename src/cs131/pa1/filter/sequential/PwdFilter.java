package cs131.pa1.filter.sequential;

import java.io.IOException;

public class PwdFilter extends SequentialFilter{
	public boolean isDone = false;
	
	public void process() {
		String currentDir = System.getProperty("user.dir");
		if (currentDir != null ) {
			this.output.add(currentDir);
		}
		isDone = true;
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	

}
