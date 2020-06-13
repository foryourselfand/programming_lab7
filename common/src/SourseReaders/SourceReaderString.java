package SourseReaders;

import Commands.CommandLoad;

import java.util.Scanner;

public class SourceReaderString extends SourceReader {
	private final String source;
	
	public SourceReaderString(String value) {
		this.source = value;
		this.scanner = new Scanner(value);
	}
	
	@Override
	public boolean notHasSomethingToRead() {
		return ! scanner.hasNextLine();
	}
	
	@Override
	public void printPrefix(String prefix) {
		CommandLoad.stringBuilderLoad.append(prefix);
	}
	
	@Override
	public void printPostfix(String lineRead) {
		CommandLoad.stringBuilderLoad.append(lineRead).append("\n");
	}
	
	@Override
	public String getSource() {
		return source;
	}
}
