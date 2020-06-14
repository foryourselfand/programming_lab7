package Utils;

import Input.Flat;
import SourseReaders.SourceReader;

/**
 * Создает квартиры
 */
public class FlatCreator {
	/**
	 * @param lineReader источник ввода
	 * @return созданную квартиру считанную из терминала
	 */
	public static Flat getCreatedFlatFromTerminal(LineReader lineReader) {
		lineReader.setRepeatOnException(true);
		SourceReader sourceReaderActive = lineReader.getSourceReaderActive();
		
		Flat flat = new Flat();
		
		flat.generateId();
		flat.setFlatName(lineReader, sourceReaderActive);
		
		flat.createCoordinates();
		flat.setX(lineReader, sourceReaderActive);
		flat.setY(lineReader, sourceReaderActive);
		
		flat.generateCreationDate();
		flat.setArea(lineReader, sourceReaderActive);
		flat.setNumberOfRooms(lineReader, sourceReaderActive);
		flat.setHeight(lineReader, sourceReaderActive);
		flat.setIsNew(lineReader, sourceReaderActive);
		flat.setTransport(lineReader, sourceReaderActive);
		
		flat.createHouse();
		flat.setHouseName(lineReader, sourceReaderActive);
		flat.setYear(lineReader, sourceReaderActive);
		flat.setNumberOfFloors(lineReader, sourceReaderActive);
		flat.setNumberOfLifts(lineReader, sourceReaderActive);
		
		lineReader.setRepeatOnException(false);
		
		return flat;
	}
	
}
