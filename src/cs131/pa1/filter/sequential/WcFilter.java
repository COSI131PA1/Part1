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
		String[] words = line.split("\\s+");
		for (String word : words) {
			numWords++;
			numChars+=word.length();
		}
		if (isDone()) {
			return numLines+" "+numWords+" "+numChars;
		}
		return null;
	}
	
	public String toString() {
		return "wc";
	}
	
}
