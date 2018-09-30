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
					if(!(commandFilter.get(0) instanceof PrintFilter)) {
						for (SequentialFilter sf: commandFilter) {
//							System.out.println(sf.toString());
//							if(sf.getInput()!=null) {
//								for (String s : sf.getInput()) {
//									System.out.println("input"+s);
//								}
//							}
//	
							sf.process();
//							if(sf.getOutput() !=null) {
//								for (String s : sf.getOutput()) {
//									System.out.println("output"+s);
//								}
//							}
							
						}
					}
				}
			}
			System.out.print(Message.NEWCOMMAND);
		} 
		console.close();
	}
}
