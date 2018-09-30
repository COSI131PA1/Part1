package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;
import java.io.*;

public class SimplePromptFilter extends SequentialFilter {
	private String targetFileName;
	private File targetFile;
	public SimplePromptFilter (String fileName) {
		super();
		String[] fileNameSplit = fileName.split("\\s+");
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
		try {
			targetFile.createNewFile();
		} catch (IOException ioe) {
			System.out.printf(Message.FILE_NOT_FOUND.toString(), targetFileName);
		}
	}
	protected String processLine(String line) {
		FileWriter fw;
		try {
			fw = new FileWriter(targetFile.getAbsoluteFile());
		} catch (IOException ioe) {
			System.out.printf("Message.FILE_NOT_FOUND.toString()", targetFileName);
			fw = null;
		}
		try {
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(line);
		} catch (IOException ioe) {
			System.out.printf("Message.FILE_NOT_FOUND.toString()", targetFileName);
		}
		return null;
	}
	
	public String toString() {
		return ">";
	}
}
