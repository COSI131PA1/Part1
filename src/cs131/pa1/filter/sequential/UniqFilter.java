package cs131.pa1.filter.sequential;

import java.util.HashSet;
import java.util.Set;

public class UniqFilter extends SequentialFilter{
	private Set<String> inputSet = new HashSet<String>();
	
	public void process() {
		if (input.contains("File not found error") && (next instanceof WcFilter || next instanceof UniqFilter) ) {
			output.add("File not found error");
		} else {
			while(!this.input.isEmpty()) {
				String element = this.input.poll();
				if(processLine(element) != null) {
					this.output.add(processLine(element));
				}
				inputSet.add(element);
			}
			if(output.isEmpty() && next instanceof WcFilter) {
				output.add("original empty file");
			}
		}
	}
	
	public String toString() {
		return "uniq";
	}

	@Override
	protected String processLine(String line) {
		if (line !=null && !inputSet.contains(line)) {
			return line;
		}
		return null;
	}
	
}
