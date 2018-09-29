package cs131.pa1.filter.sequential;

import java.util.Scanner;
public class WcFilter extends SequentialFilter{
	private int numLines = 0;
	private int numWords = 0;
	private int numChars = 0;
	public WcFilter () {
		
	}
	
	public void addInput(String s) {
		this.input.add(s);
	}
	
	protected String processLine(String line) {
		if(line.length() != 0) {
			numLines++;
		}
		Scanner s = new Scanner(line);
		do {
			numWords++;
		} while (s.hasNext());
		String[] words = line.split(" ");
		for (String word : words) {
			numChars+=word.length();
		}
		s.close();
		if (isDone()) {
			return numLines+" "+numWords+" "+numChars;
		}
		return null;
	}
	
}
