package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;
import java.io.*;

public class SimplePromptFilter extends SequentialFilter {
	private String targetFileName;
	private File targetFile;
	private FileWriter fw;
	public SimplePromptFilter (String fileName) {
		super();
		String[] fileNameSplit = fileName.trim().split("\\s+");
		if (fileNameSplit.length > 0) {
			targetFileName =  fileNameSplit[1].trim();
		} else {
			System.out.printf(Message.REQUIRES_PARAMETER.toString(), ">");
		}
		this.targetFile = new File(targetFileName);
		//To get if the file with given name already exists
		//If not, create a new file. If already existed, rewrite with a new file.
		if (targetFile.exists()) {
			targetFile.delete();
		} 
		this.targetFile = new File(SequentialREPL.currentWorkingDirectory+File.separator+targetFileName);
	}
	
	public void process() {
		while(!isDone()) {
			processLine(input.poll());
		}
	}
	
	protected String processLine(String line) {
//		File f = new File(SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + targetFileName);
		try {
			//orginal f
			fw = new FileWriter(targetFile, true);
	        BufferedWriter bw = new BufferedWriter(fw);
			try {
				bw.write(line);
				bw.newLine();
				bw.flush();
				bw.close();
			} catch(IOException e) {
				System.out.printf(Message.FILE_NOT_FOUND.toString(), targetFileName);
			}
		} catch (IOException e) {
			System.out.printf(Message.FILE_NOT_FOUND.toString(), targetFileName);
		}
		return null;
	}
	
	public String toString() {
		return ">";
	}
}
