package ie.atu.sw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * The {@code DictionaryParser} class extends {@code AbstractParser} and is
 * responsible for parsing the dictionary file and adding the words and their
 * definitions to the index.
 */
public class DictionaryParser extends AbstractParser {
	private ConcurrentMap<String, WordDetail> index;

	private int page;

	/**
	 * Creates a new {@code DictionaryParser} instance with the specified index,
	 * dictionary directory, and stop words.
	 * 
	 * @param index               the index map to add the words and definitions to
	 * @param dictionaryDirectory the directory of the dictionary file
	 * @param stopWords           the set of stop words
	 */
	public DictionaryParser(ConcurrentMap<String, WordDetail> index, String dictionaryDirectory,
			Set<String> stopWords) {
		super(index, stopWords);
		this.index = index;
	}

	/**
	 * Parses the dictionary file and adds the words and definitions to the index.
	 * 
	 * @param file the dictionary file to parse
	 * 
	 * @throws IOException if an I/O error occurs reading the dictionary file
	 */
	// O(n), where n is the number of lines that are parsed
	@Override
	public void parse(String file) {
		// Read the dictionary.csv file line by line and process each line using a
		// virtual thread
		try {
			Files.lines(Path.of(file)).forEach(line -> Thread.startVirtualThread(() -> {

				// Split the line into word and definition
				String[] parts = line.split(",");
				String word = parts[0];
				String definition = parts[1];

				// Convert the word to lower case
				word = word.toLowerCase();

				// Add definition of word to index
				addToIndex(word, definition, page);

			}));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds the specified word and definition to the index. If the word is already
	 * in the index, updates the definition if available.
	 * 
	 * @param word       the word to add to the index
	 * @param definition the definition of the word
	 * @param pageNumber the page number of the word
	 * 
	 * @return the {@code WordDetail} object for the word
	 */
	// O(1) because it performs a single get operation O(1). Setting the definition
	// is also a constant time operation
	@Override
	public WordDetail addToIndex(String word, String definition, Integer pageNumber) {

		// Check if the word is already in the index
		WordDetail wordDetail = index.get(word);

		if (wordDetail != null) {
			// If the word is already in the index, update the definition if available and
			// add the page number to the list
			wordDetail.setDefinition(definition);
		}
		return wordDetail;

	}
}