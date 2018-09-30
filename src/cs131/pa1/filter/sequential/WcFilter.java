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
			System.out.println(numLines);
		}
		String[] words = line.split("\\s+");
		for (String word : words) {
			numWords++;
			System.out.println(numWords);
			numChars+=word.length();
			System.out.println(numChars);
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
