package ie.atu.sw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

/**
 * 
 * The {@code StopWordsParser} class extends {@code AbstractParser} and is
 * 
 * responsible for parsing the stop words file and adding the words to the stop
 * 
 * words set.
 */
public class StopWordsParser extends AbstractParser {

	/**
	 * 
	 * Creates a new {@code StopWordsParser} instance with the specified google1000
	 * directory and stop words set.
	 * 
	 * @param google1000Directory the directory of the google1000 file
	 * @param stopWords           the set of stop words
	 */
	// Constructor for access to google1000Directory and stopWords
	public StopWordsParser(String google1000Directory, Set<String> stopWords) {
		super(stopWords);
	}

	/**
	 * 
	 * Parses the stop words file and adds the words to the stop words set.
	 * 
	 * @param file the stop words file to parse
	 * @throws IOException if an I/O error occurs reading the stop words file
	 */
	// Parsing the stop words file line by line, and calling the addToStopWordsSet
	// method to populate the set.
	// O(n), where n is the number of lines that are parsed
	@Override
	public void parse(String file) {
		try {
			Files.lines(Path.of(file)).forEach(line -> Thread.startVirtualThread(() -> {
				String word = line.toLowerCase();
				addtoStopWordsSet(word);
			}));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Populating the stopWords set with the words contained within the file. O(1),
	// but called per line parsed in the parse function above
	private void addtoStopWordsSet(String word) {
		getStopWords().add(word);

	}

}
