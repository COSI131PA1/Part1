package cs131.pa1.filter.sequential;

public class PrintFilter extends SequentialFilter {
	public PrintFilter () {
		super();
	}
	protected String processLine(String line) {
		System.out.println(line);
		return null;
	}

}
