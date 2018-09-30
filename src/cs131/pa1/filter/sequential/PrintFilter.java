package cs131.pa1.filter.sequential;

public class PrintFilter extends SequentialFilter {
	
	protected String processLine(String line) {
		System.out.println(line);
		return null;
	}
	
	public String toString() {
		return "print";
	}

	public SequentialFilter getNext(SequentialFilter next) {
		return next;
	}
	
}
