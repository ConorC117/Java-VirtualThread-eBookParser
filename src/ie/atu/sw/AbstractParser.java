package ie.atu.sw;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * The {@code AbstractParser} class is an abstract implementation of the {@code Parser} interface that provides
 * a skeletal implementation of the {@code parse} and {@code addToIndex} methods. It also includes getters for 
 * the index map and stop words set.
 * 
 * The purpose of this class is to allow concrete parser classes to reuse common code and to provide a consistent 
 * interface for interacting with the index map and stopWords set.
 */
public abstract class AbstractParser implements Parser {

	private ConcurrentMap<String, WordDetail> index = new ConcurrentHashMap<>();
	private Set<String> stopWords = new ConcurrentSkipListSet<>();

	/**
	 * Creates a new {@code AbstractParser} instance with the specified index and stop words set.
	 * 
	 * @param index the index map to add the words, definitions, and page locations to
	 * @param stopWords the set of stop words
	 */
	public AbstractParser(ConcurrentMap<String, WordDetail> index, Set<String> stopWords) {
		this.index = index;
		this.stopWords = stopWords;
	}

	/**
	 * Creates a new {@code AbstractParser} instance with the specified stop words set.
	 * 
	 * @param stopWords the set of stop words
	 */
	public AbstractParser(Set<String> stopWords) {
		this.stopWords = stopWords;
	}
	
	/**
	 * Adds the specified word, definition, and page number to the index.
	 * 
	 * @param word the word to add to the index
	 * @param definition the definition of the word
	 * @param page the page number where the word appears
	 * @return the previous {@code WordDetail} associated with the word, or {@code null} if there was none
	 */
	@Override
	public abstract void parse(String file);

	@Override
	public WordDetail addToIndex(String word, String definition, Integer page) {
		return this.index.put(word, new WordDetail(word, definition, page));
	}

	/**
	 * Returns the index map.
	 * 
	 * @return the index map
	 */
	@Override
	public ConcurrentMap<String, WordDetail> getIndex() {
		return index;
	}

	/**
	 * Returns the stop words set.
	 * 
	 * @return the stop words set
	 */
	@Override
	public Set<String> getStopWords() {
		return stopWords;
	}

}
