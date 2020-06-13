package Utils;

import Errors.InputErrors.InputErrorFull;
import Errors.ScriptAlreadyExecutedError;
import Expectations.Argument;
import Input.Variable;
import SourseReaders.SourceReader;
import SourseReaders.SourceReaderTerminal;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class LineReader {
	private final Set<String> sourceReaderSources;
	private final Stack<SourceReader> sourceReaderStack;
	
	private SourceReader sourceReaderActive;
	private boolean repeatOnException;
	
	public LineReader() {
		this.sourceReaderSources = new HashSet<>();
		this.sourceReaderStack = new Stack<>();
		this.addSourceReader(new SourceReaderTerminal());
	}
	
	public String readLine(SourceReader sourceReader, String prefix, Argument argument) {
		while (this.hasSomethingToRead()) {
			if (sourceReader.notHasSomethingToRead())
				sourceReader = getSourceReaderActive();
			
//			System.out.print(prefix);
			
			try {
				String lineRead = sourceReader.getLineReadPrintPrefixAndPostfix(prefix);
				argument.checkArgument(lineRead);
				
				return lineRead;
			} catch (InputErrorFull inputErrorFull) {
				if (! this.repeatOnException)
					throw inputErrorFull;
				System.out.println(inputErrorFull.getMessage());
			}
		}
		
		return "";
	}
	
	public String readLine(String prefix) {
		return readLine(this.sourceReaderActive, prefix, new Argument(""));
	}
	
	public String readLine(SourceReader sourceReader, Variable variable) {
		Argument argument = Variable.variableToArgument.get(variable);
		return this.readLine(sourceReader, variable.getVariableNameWithExpectationsMessage(argument), argument);
	}
	
	public boolean hasSomethingToRead() {
		while (this.sourceReaderActive.notHasSomethingToRead()) {
			SourceReader sourceReader = this.sourceReaderStack.pop();
			String sourceReaderFilePath = sourceReader.getSource();
			this.sourceReaderSources.remove(sourceReaderFilePath);
			
			this.sourceReaderActive = sourceReaderStack.peek();
		}
		
		return true;
	}
	
	public void addSourceReader(SourceReader sourceReaderToAdd) {
		String sourceReaderSource = sourceReaderToAdd.getSource();
		
		if (this.sourceReaderSources.contains(sourceReaderSource))
			throw new ScriptAlreadyExecutedError();
		this.sourceReaderSources.add(sourceReaderSource);
		
		this.sourceReaderStack.add(sourceReaderToAdd);
		this.sourceReaderActive = sourceReaderStack.peek();
	}
	
	public SourceReader getSourceReaderActive() {
		return sourceReaderActive;
	}
	
	public void setRepeatOnException(boolean repeatOnException) {
		this.repeatOnException = repeatOnException;
	}
}
