package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

import cs131.pa1.filter.Message;

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
		String currentDir = SequentialREPL.currentWorkingDirectory;
		if (filesRead.size() == 0) {
			System.out.printf(Message.REQUIRES_PARAMETER.toString(), "cat");
		} else {
			Boolean allExist = true;
			for (String fileName: this.filesRead) {
				File f = new File(fileName);
				if (!f.exists()) {
					allExist = false;
					System.out.printf(Message.FILE_NOT_FOUND.toString(), command);
				}
			}
			if (allExist) {
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
			
		}
	}
	
	public String toString() {
		return "cat";
	}
	public boolean isDone() {
		return processedFile >= filesRead.size();
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
}
