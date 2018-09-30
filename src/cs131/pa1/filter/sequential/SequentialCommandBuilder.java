package cs131.pa1.filter.sequential;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import cs131.pa1.filter.Message;

public class SequentialCommandBuilder {
	public static List<SequentialFilter> createFiltersFromCommand(String command){
		List<SequentialFilter> subCommandList = new LinkedList<SequentialFilter>();
		int size = 0;
		if (!command.isEmpty()) {
			String adjustedCom = adjustCommandToRemoveFinalFilter(command);
			if (adjustedCom == null) {
				return null;
			} 
			String[] subCommand = adjustedCom.split("\\|");
			for (String subCom : subCommand) {
				subCom = subCom.trim();
				SequentialFilter sequentialFilter = constructFilterFromSubCommand(subCom);
				if (sequentialFilter == null) {
					System.out.printf(Message.COMMAND_NOT_FOUND.toString(), subCom);
				}
//				if(size !=0 ) {
//					sequentialFilter.setPrevFilter(subCommandList.get(size-1));
//				}
				if(sequentialFilter != null) {
					subCommandList.add(sequentialFilter);
					size++;
				}
			}
			if (command.length() == adjustedCom.length()) {
				subCommandList.add(new PrintFilter());
			} else {
				String lastSubCom = command.substring(adjustedCom.length());
				SequentialFilter lastSequentialFilter = determineFinalFilter(lastSubCom);
				subCommandList.add(lastSequentialFilter);
			}
			if (linkFilters(subCommandList,command)) {
				return subCommandList;
			}
		} else {
			System.out.printf(Message.COMMAND_NOT_FOUND.toString(), command);
		}
		return null;
	}
	
	private static SequentialFilter determineFinalFilter(String command){
		//The output of the final filter can either be printed on the screen or write into a file
		//If the final subcommand contains ">", meaning it should output to a file
		if (command.contains(">")) {
			String[] output = command.split(">");
				//For the case that there is a destination file
				if (output.length > 1) {
					return new SimplePromptFilter(command);
				} else {
					System.out.printf(Message.REQUIRES_PARAMETER.toString(), command);
					return null;
				}
		} else {
			return new PrintFilter();
		}
	}
	
	private static String adjustCommandToRemoveFinalFilter(String command){
//		command = command.trim();
		//For the case that no need to output to a file, simply remove the last subcommand
		if (!command.contains(">")) {
			return command;
		} else {
			//For the case that need to output to a file, there are 4 cases with error
			//1. The command has more than one ">"
			//2. The command has exactly one ">", but ">" is not in the last subcommand
			//3. The command has exactly one ">", but ">" is the first subcommand
			//4. The command has exactly one ">", but ">" is the end of the command with no output file name
			//The correct format would be, ">" is in the last subcommand, as well as an output file name is given
			String[] formatCheck = command.split(">");
			String errorCom;
			if(formatCheck.length == 0) {
				System.out.printf(Message.REQUIRES_PARAMETER.toString(), ">");
				return null;
			}
			if (formatCheck.length>2 || (formatCheck.length == 2 && formatCheck[1].contains("|"))) {
				if (formatCheck[0].contains("|")) {
					errorCom = formatCheck[0].substring(formatCheck[0].lastIndexOf('|')).trim();
				} else {
					errorCom = formatCheck[0].trim();
				}
				System.out.printf(Message.CANNOT_HAVE_OUTPUT.toString(), errorCom);
				return null;
			} else if (formatCheck.length == 2){
				if (formatCheck[1].trim().lastIndexOf('>') == 0) {
					System.out.printf(Message.REQUIRES_INPUT.toString(), "> "+formatCheck[1].substring(0, formatCheck[1].indexOf('|')).trim());
					return null;
				} else if (formatCheck[1].trim().lastIndexOf('>') == (formatCheck[1].length()-1)) {
					System.out.printf(Message.REQUIRES_PARAMETER.toString(), formatCheck[1].substring(formatCheck[1].lastIndexOf('|')).trim()+" >");
					return null;
				} else {
					return command;
				}
			} else {
				return command;
			}
		}
	}
	
	private static SequentialFilter constructFilterFromSubCommand(String subCommand){
		String[] commandName =  subCommand.split("\\s+");
		SequentialFilter sequentialFilter = null;
		if (commandName[0].equals("grep")) {
			sequentialFilter = new GrepFilter(subCommand);
		} else if (commandName[0].equals(">")) {
			sequentialFilter = new SimplePromptFilter(subCommand);
		} else if (commandName[0].equals("pwd")) {
			sequentialFilter = new PwdFilter();
		} else if (commandName[0].equals("ls")) {
			sequentialFilter = new LsFilter();
		} else if (commandName[0].equals("cd")) {
			sequentialFilter = new CdFilter(subCommand);
		} else if (commandName[0].equals("cat")) {
			sequentialFilter = new CatFilter(subCommand);
		} else if (commandName[0].equals("wc")) {
			sequentialFilter = new WcFilter();
		} else if (commandName[0].equals("uniq")) {
			sequentialFilter = new UniqFilter();
		} 
		return sequentialFilter;
	}

	private static boolean linkFilters(List<SequentialFilter> filters, String command){
		String[] splitCom = command.split("\\|");
		if (command.contains(">")) {
			String lastCom = splitCom[splitCom.length-1];
			if(lastCom.indexOf(">") != 0) {
				String lastCom1 = lastCom.substring(0,lastCom.indexOf(">"));
				splitCom[splitCom.length-1] = lastCom1;
				String lastCom2 = lastCom.substring(lastCom.indexOf(">"));
				String[] splitFinal = new String[splitCom.length+1];
				for(int i = 0; i<splitCom.length; i++) {
					splitFinal[i] = splitCom[i];
				}
				splitFinal[splitCom.length] = lastCom2;
				splitCom = splitFinal;
			}
		}
		Iterator<SequentialFilter> litr = filters.iterator();
		SequentialFilter curr;
		SequentialFilter next;
		int index = 0;
		curr = litr.next();
		//Check if the first subcommand is a command that requires input
		if (curr instanceof GrepFilter || curr instanceof SimplePromptFilter || curr instanceof WcFilter || curr instanceof UniqFilter) {
			System.out.printf(Message.REQUIRES_INPUT.toString(), splitCom[0].trim());
			return false;
		}
		while (litr.hasNext()) {
			next = litr.next();
			//There are in total two conditions that can cause errors
			//1. Current filter gives output while the next filter cannot have input
			//2. Current filter gives no output while the next filter requires input
			if ((next instanceof PwdFilter || next instanceof LsFilter || next instanceof CdFilter || next instanceof CatFilter) && !(curr instanceof CdFilter)) {
				System.out.printf(Message.CANNOT_HAVE_INPUT.toString(), splitCom[index+1].trim());
				return false;
			} else if ((next instanceof GrepFilter || next instanceof WcFilter || next instanceof SimplePromptFilter || next instanceof UniqFilter) && (curr instanceof CdFilter)) {
				System.out.printf(Message.CANNOT_HAVE_OUTPUT.toString(), splitCom[index].trim());
				return false;
			} 
			curr.setNextFilter(next);
			curr = next;
			index++;
		}
		return true;
	}
}
