package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class CatFilter extends SequentialFilter {
	private String command;
	private ArrayList<String> filesRead =  new ArrayList<String>();
	private int processedFile = 0;
	
	public CatFilter(String command) {
		this.command = command;
	}
	
	public void getFiles() {
		ArrayList<String> filesRead =  new ArrayList<String>();
		String[] inputArray = command.split("\\s+");
		for (int i = 0; i <  inputArray.length; i++) {
			if (!inputArray[i].equals("cat")) {
				this.filesRead.add(inputArray[i]);
			}
		}

	}
	
	
	public void process() {
		getFiles();
//		output.add("aaaa");
//		System.out.println("!!!! " + output);
//		System.out.println(this.output.isEmpty());
//		System.out.print("new output: "+ output.size());
		String currentDir = SequentialREPL.currentWorkingDirectory;
		for (String fileName: this.filesRead) {
			File f = new File(fileName);
			Scanner console;
			try {
				console = new Scanner (f);
				while (console.hasNextLine()) {
					this.output.add(console.nextLine());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			processedFile++;
		}
	}
	public boolean isDone() {
		return processedFile >= filesRead.size();
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
}
