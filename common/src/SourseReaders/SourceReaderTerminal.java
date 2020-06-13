package SourseReaders;

import java.util.Scanner;


public class SourceReaderTerminal extends SourceReader {
	public SourceReaderTerminal() {
		this.scanner = new Scanner(System.in);
	}
	
	@Override
	public boolean notHasSomethingToRead() {
		return false;
	}
	
	public void printPostfix(String lineRead) {
	}
	
	@Override
	public String getSource() {
		return "Терминал";
	}
}
