package cs131.pa1.filter.sequential;

import java.util.Scanner;
public class WcFilter extends SequentialFilter{
	private int numLines = 0;
	private int numWords = 0;
	private int numChars = 0;
	
	public void addInput(String s) {
		this.input.add(s);
	}
	
	protected String processLine(String line) {
		if(line.length() != 0) {
			numLines++;
		}
		if(line.length()==1) {
			if(line.charAt(0) == ' ') {
				numChars++;
			} else {
				numWords++;
				numChars++;
			}
		} else {
			String[] words = line.split("\\s+");
			for (String word : words) {
				numWords++;
				numChars += word.length();
			}
		}
		
		if (isDone()) {
			return numLines+" "+numWords+" "+numChars;
		}
		return null;
	}
	
	public void process() {
		if(input == null || input.isEmpty()) {
			output.add("0 0 0");
		} else {
			while (!input.isEmpty()){
				String line = input.poll();
				String processedLine = processLine(line);
				if (processedLine != null){
					output.add(processedLine);
				}
			}	
		}
	}
	
	public String toString() {
		return "wc";
	}
	
}
