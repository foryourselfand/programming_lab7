package SourseReaders;

import Errors.IOErrors.FileNotExistError;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SourceReaderFile extends SourceReader {
	private final String path;
	
	public SourceReaderFile(String path) {
		this.path = path;
		try {
			this.scanner = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			throw new FileNotExistError();
		}
	}
	
	@Override
	public boolean notHasSomethingToRead() {
		return ! this.scanner.hasNextLine();
	}
	
	@Override
	public void printPostfix(String lineRead) {
		System.out.println(lineRead);
	}
	
	@Override
	public String getSource() {
		return path;
	}
}
