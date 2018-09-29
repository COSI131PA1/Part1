package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CatFilter extends SequentialFilter {
	private String command;
	public CatFilter(String command) {
		this.command = command;
	}
	
	public void output(ArrayList<String> filesRead) {
		for (String fileName: filesRead) {
			try { 
				File f = new File(fileName);
				Scanner console = new Scanner(f);
				if (console.hasNextLine()) {
					String line = console.nextLine();
					String processLine = processLine(line);
					if (processLine != null ) {
						this.output.add(processLine);
					}
				}
			} catch (FileNotFoundException e) {
			    System.out.println(e.getMessage());
			}
		}
	}
	
	public void process() {
		ArrayList<String> filesRead =  new ArrayList<String>();
		String[] inputArray = command.split("\\s+");
		for (int i = 0; i <  inputArray.length; i++) {
			if (!inputArray[i].equals("cat")) {
				filesRead.add(inputArray[i]);
			}
		}
		output(filesRead);
	}

	@Override
	protected String processLine(String line) {
		if (line != null) {
			return line;
		} 
		return null;
	}
}
