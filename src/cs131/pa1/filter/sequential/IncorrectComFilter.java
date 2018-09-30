package cs131.pa1.filter.sequential;

import java.util.LinkedList;

import cs131.pa1.filter.Filter;

public class IncorrectComFilter extends SequentialFilter{
	public IncorrectComFilter() {
		super();
	}
	
	@Override 
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof SequentialFilter){
			SequentialFilter sequentialNext = (SequentialFilter) nextFilter;
			if(this.prev != null) {
				this.prev.setNextFilter(sequentialNext);
			}
			if (this.output == null){
				this.output = new LinkedList<String>();
			}
			if (!(nextFilter instanceof PrintFilter)) {
				sequentialNext.input = this.input;
			} else {
				sequentialNext.input =  new LinkedList<String>();
			}
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
		
	}
	
	public String toString() {
		return "incorrectCommand";
	}
	
	@Override
	public void process() {
		processLine("");
	}
	
	protected String processLine(String line) {
		return null;
	}
}
