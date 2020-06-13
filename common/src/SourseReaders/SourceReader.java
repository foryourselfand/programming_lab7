package SourseReaders;

import java.util.Scanner;

abstract public class SourceReader {
	protected Scanner scanner;
	
	public String getLineReadPrintPrefixAndPostfix(String prefix) {
		this.printPrefix(prefix);
		
		String lineRead = scanner.nextLine();
		
		this.printPostfix(lineRead);
		
		return lineRead;
	}
	
	abstract public boolean notHasSomethingToRead();
	
	public void printPrefix(String prefix) {
		System.out.print(prefix);
	}
	
	abstract public void printPostfix(String lineRead);
	
	public abstract String getSource();
}
