package ie.atu.sw;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * The {@code Parser} interface defines methods for parsing a file and adding
 * words to an index.
 */
// Parser interface which contains the methods parse and addToIndex, as well as getters for the index map and stopWords set. 
// This allows the methods to be called from several classes, while also defining their code per class. 
public interface Parser {

	/**
	 * Parses the specified file.
	 *
	 * @param file the file to parse
	 */
	public void parse(String file);

	/**
	 * Adds the specified word, definition, and page number to the index.
	 *
	 * @param word       the word to add to the index
	 * @param definition the definition of the word
	 * @param pageNumber the page number where the word appears
	 * @return the {@code WordDetail} object containing the word, definition, and
	 *         pages where the word appears
	 */
	// Defining this here allows one to carry the index between
	// concrete parser classes and adjust the contents gradually
	public WordDetail addToIndex(String word, String definition, Integer pageNumber);

	/**
	 * Returns the index map.
	 *
	 * @return the index map
	 */
	public ConcurrentMap<String, WordDetail> getIndex();

	/**
	 * Returns the set of stop words.
	 *
	 * @return the set of stop words
	 */
	public Set<String> getStopWords();

}
