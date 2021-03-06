package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;

public class GrepFilter extends SequentialFilter {
	private String target;
	
	public GrepFilter (String input) {
		super();
		grabTarget(input);
	}
	
	public String toString() {
		return "grep";
	}
	
	private void grabTarget (String input) {
		//to deal with the case that the target string is a phrase (as described in the link given in instruction)
		if (input.indexOf('\'') != -1) {
			int start = input.indexOf('\'');
			if (input.indexOf('\'',input.indexOf('\'')) != -1) {
				int end = input.indexOf('\'',input.indexOf('\''));
				target = input.substring(start, end+1);
			}
		}
		//to deal with the case that the target string is a single word
		if (target == null) {
			String[] inputSplit = input.split("\\s+");
			if(inputSplit.length > 1) {
				target = inputSplit[1];
			}
		}
		//to deal with invalid input
		if (target == null) {
			System.out.printf(Message.REQUIRES_PARAMETER.toString(), input);
		}
	}
	protected String processLine (String line) {
		if (target !=null && (line.contains(target))) {
			return line;
		}
		return null;
	}
	
	public void process() {
		//To handle that the prev filter is cat, and an error has occurred
		if (input.contains("File not found error") && (next instanceof WcFilter || next instanceof UniqFilter) ) {
			output.add("File not found error");
		} else {
			super.process();
			if(output.isEmpty() && next instanceof WcFilter) {
				output.add("original empty file");
			}
		}
	}
}
