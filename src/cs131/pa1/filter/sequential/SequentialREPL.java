package cs131.pa1.filter.sequential;
import java.util.LinkedList;
import java.util.Scanner;
import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.*;

public class SequentialREPL {

	static String currentWorkingDirectory = System.getProperty("user.dir");
	
	public static void main(String[] args) {
		System.out.println(Message.NEWCOMMAND);
		System.out.println(Message.WELCOME);
		Scanner console = new Scanner(System.in);
		String command = "";
		while (console.hasNextLine()){
			command = console.nextLine();
			if (command.equals("EXIT")){
				System.out.print(Message.GOODBYE);
				console.close();
				break;
			} else {
				LinkedList<SequentialFilter> commandFilter = (LinkedList<SequentialFilter>) SequentialCommandBuilder.createFiltersFromCommand(command);
				for (SequentialFilter sf: commandFilter) {
					sf.process();
				}
			}
			System.out.println(Message.NEWCOMMAND);
		} 
		console.close();
	}
}
