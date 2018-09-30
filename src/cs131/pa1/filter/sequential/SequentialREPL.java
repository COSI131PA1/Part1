package cs131.pa1.filter.sequential;
import java.util.LinkedList;
import java.util.Scanner;
import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.*;

public class SequentialREPL {

	static String currentWorkingDirectory = System.getProperty("user.dir");
	
	public static void main(String[] args) {
		System.out.print(Message.WELCOME);
		System.out.print(Message.NEWCOMMAND);
		Scanner console = new Scanner(System.in);
		String command = "";
		while (console.hasNextLine()){
			command = console.nextLine();
			if (command.equals("EXIT") || command.equals("exit")){
				System.out.print(Message.GOODBYE);
				console.close();
				break;
			} else {
				LinkedList<SequentialFilter> commandFilter = (LinkedList<SequentialFilter>) SequentialCommandBuilder.createFiltersFromCommand(command);
				if (commandFilter != null) {
					for (SequentialFilter sf: commandFilter) {
						sf.process();
					}
				}
			}
			System.out.print(Message.NEWCOMMAND);
		} 
		console.close();
	}
}
